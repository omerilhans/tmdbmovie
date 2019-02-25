package com.omerilhanli.tmdbmove.UI.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.omerilhanli.tmdbmove.R;
import com.omerilhanli.tmdbmove.UI.BaseActivity;
import com.omerilhanli.tmdbmove.asistant.common.Constants;
import com.omerilhanli.tmdbmove.asistant.tools.NetworkController;
import com.omerilhanli.tmdbmove.asistant.tools.StringFormatter;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.interactor.images.InteractorConfiguration;
import com.omerilhanli.tmdbmove.data.interactor.movie.InteractorMovie;
import com.omerilhanli.tmdbmove.data.model.Configuration;
import com.omerilhanli.tmdbmove.data.model.Images;
import com.omerilhanli.tmdbmove.data.model.Movie;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.genresTextView)
    TextView genresTextView;
    @BindView(R.id.durationTextView)
    TextView durationTextView;
    @BindView(R.id.languageTextView)
    TextView languageTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.overviewHeader)
    TextView overviewHeader;
    @BindView(R.id.overviewTextView)
    TextView overviewTextView;
    @BindView(R.id.moreInfoButton)
    Button moreInfoButton;
    @BindView(R.id.errorTextView)
    TextView errorTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Interactor<Configuration> configurationInteractor;

    private Interactor<Movie> movieInteractor;

    private Images images;

    private StringFormatter stringFormatter;

    private int movieId = -1;

    @Override
    protected int getActivityLayoutId() {

        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initAll();
    }

    @OnClick(R.id.moreInfoButton)
    public void onMoreInfoButtonClick() {

        String url = getString(R.string.web_url) + movieId;

        if (Build.VERSION.SDK_INT >= 16) {

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

            builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));

            CustomTabsIntent customTabsIntent = builder.build();

            customTabsIntent.launchUrl(this, Uri.parse(url));

        } else {

            Intent i = new Intent(Intent.ACTION_VIEW);

            i.setData(Uri.parse(url));

            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    private void initAll() {

        stringFormatter = new StringFormatter(this);

        movieInteractor = new InteractorMovie(tmdbApi);

        configurationInteractor = new InteractorConfiguration(tmdbApi);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            movieId = extras.getInt(Constants.MOVIE_ID);

            String movieTitle = extras.getString(Constants.MOVIE_TITLE);

            setTitle(movieTitle);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setDisplayShowHomeEnabled(true);

            fetchImages();
        }
    }

    private void fetchImages() {

        if (!checkNetwork()) {

            return;
        }

        showLoading();

        configurationInteractor.onRequest(0).subscribe(new Subscriber<Configuration>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                showError();
            }

            @Override
            public void onNext(Configuration configuration) {

                images = configuration.images;

                fetchMovies();
            }
        });
    }

    private void fetchMovies() {

        if (checkNetwork())

            movieInteractor
                    .onRequest(movieId)
                    .subscribe(new Subscriber<Movie>() {

                        @Override
                        public void onCompleted() {

                            hideLoading();
                        }

                        @Override
                        public void onError(Throwable e) {

                            showError();
                        }

                        @Override
                        public void onNext(Movie movie) {

                            String fullImageUrl = getFullImageUrl(movie);

                            if (!fullImageUrl.isEmpty()) {

                                loadPoster(fullImageUrl);
                            }

                            genresTextView.setText(stringFormatter.getGenres(movie.genres));

                            durationTextView.setText(stringFormatter.getDuration(movie.runtime));

                            languageTextView.setText(stringFormatter.getLanguages(movie.spokenLanguages));

                            dateTextView.setText(stringFormatter.getReleaseDate(movie));

                            nameTextView.setText(movie.title);

                            overviewTextView.setText(stringFormatter.getOverview(movie.overview));
                        }
                    });
    }

    private void loadPoster(String fullImageUrl) {

        Glide.with(this)
                .load(fullImageUrl)
                .centerCrop()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {

                        progress.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {

                        progress.setVisibility(View.GONE);

                        return false;
                    }
                })
                .into(imageView);
    }

    @NonNull
    private String getFullImageUrl(Movie movie) {

        String imagePath;

        if (movie.posterPath != null && !movie.posterPath.isEmpty()) {

            imagePath = movie.posterPath;

        } else {

            imagePath = movie.backdropPath;
        }

        if (images != null && images.baseUrl != null && !images.baseUrl.isEmpty()) {

            if (images.posterSizes != null) {

                if (images.posterSizes.size() > 4) {

                    return images.baseUrl + images.posterSizes.get(4) + imagePath;

                } else {

                    return images.baseUrl + Constants.KEY_SIZE_OF_IMAGE + imagePath;
                }
            }
        }

        return "";
    }

    void hideLoading() {

        progress.setVisibility(View.GONE);

        progressBar.setVisibility(View.GONE);

        errorTextView.setVisibility(View.GONE);
    }

    void showLoading() {

        progress.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.VISIBLE);

        errorTextView.setVisibility(View.GONE);
    }

    void showError() {

        progressBar.setVisibility(View.GONE);

        errorTextView.setVisibility(View.VISIBLE);
    }

    private void showContent(boolean show) {

        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        container.setVisibility(visibility);

        overviewHeader.setVisibility(visibility);

        overviewTextView.setVisibility(visibility);

        moreInfoButton.setVisibility(visibility);
    }

    private boolean checkNetwork() {

        boolean isOnline = NetworkController.isOnline(this);

        if (!isOnline) {

            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();

            showContent(false);

            showError();
        }

        return isOnline;
    }

    public static void startActivityFrom(Activity activity, int movieId, String title) {

        Intent intent = new Intent(activity, DetailActivity.class);

        intent.putExtra(Constants.MOVIE_ID, movieId);

        intent.putExtra(Constants.MOVIE_TITLE, title);

        activity.startActivity(intent);
    }

}
