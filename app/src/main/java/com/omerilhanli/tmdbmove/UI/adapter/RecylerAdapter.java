package com.omerilhanli.tmdbmove.UI.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omerilhanli.tmdbmove.R;
import com.omerilhanli.tmdbmove.asistant.common.Constants;
import com.omerilhanli.tmdbmove.asistant.tools.StringFormatter;
import com.omerilhanli.tmdbmove.data.model.Genre;
import com.omerilhanli.tmdbmove.data.model.Images;
import com.omerilhanli.tmdbmove.data.model.Movie;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.VH> {

    private Context context;

    private StringFormatter stringFormatter;

    private OnScroolToBottom onScroolToBottom;

    private ItemClickListener itemClickListener;

    private List<Movie> movieList;

    private List<Genre> genreList;

    private Images images;

    public RecylerAdapter(Context context,
                          OnScroolToBottom onScroolToBottom,
                          ItemClickListener itemClickListener,
                          List<Genre> genreList,
                          Images images) {

        this.context = context;

        this.onScroolToBottom = onScroolToBottom;

        this.itemClickListener = itemClickListener;

        this.genreList = genreList;

        this.images = images;

        movieList = new ArrayList<>();

        stringFormatter = new StringFormatter(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_view, viewGroup, false);

        VH vh = new VH(view);

        return vh;
    }

    public List<Movie> getMovieList() {

        return movieList;
    }

    public void add(List<Movie> movies) {

        movieList.addAll(movies);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        final Movie movie = movieList.get(position);

        String fullImageUrl = getFullImageUrl(movie);

        if (!fullImageUrl.isEmpty()) {

            Glide.with(context)
                    .load(fullImageUrl)
                    .centerCrop()
                    .crossFade()
                    .into(holder.imageView);
        }


        holder.releaseTextView.setText(stringFormatter.getReleaseDate(movie));

        holder.titleTextView.setText(movie.title);

        holder.genreTextView.setText(getMainGenres(movie));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickListener.onItemClick(movie.id, movie.title);
            }
        });

        if (position == movieList.size() - 1) {

            onScroolToBottom.onScrollToBottom(true);

        } else {

            onScroolToBottom.onScrollToBottom(false);
        }
    }

    private String getMainGenres(Movie movie) {

        if (movie.genresId.isEmpty()) {

            return context.getResources().getString(R.string.genres_error);
        }

        List<String> genresString = new ArrayList<>();

        for (Genre genre : genreList) {

            for (Integer id : movie.genresId) {

                if (genre.id.equals(id)) {

                    genresString.add(genre.name);
                }
            }
        }

        return StringUtils.join(genresString, ", ");
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

    public void clear() {
        movieList.clear();
    }

    public void addAll(List<Movie> movies) {
        this.movieList.addAll(movies);
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public void setGenres(List<Genre> genres) {
        this.genreList = genres;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.releaseTextView)
        TextView releaseTextView;
        @BindView(R.id.popularityContainer)
        FrameLayout popularityContainer;
        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.genreTextView)
        TextView genreTextView;

        VH(View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnScroolToBottom {

        void onScrollToBottom(boolean visible);
    }

    public interface ItemClickListener {

        void onItemClick(int movieId, String title);

    }
}
