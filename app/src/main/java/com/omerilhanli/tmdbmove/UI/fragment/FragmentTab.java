package com.omerilhanli.tmdbmove.UI.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.omerilhanli.tmdbmove.R;
import com.omerilhanli.tmdbmove.UI.BaseFragment;
import com.omerilhanli.tmdbmove.UI.activity.DetailActivity;
import com.omerilhanli.tmdbmove.UI.adapter.RecylerAdapter;
import com.omerilhanli.tmdbmove.asistant.tools.NetworkController;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.interactor.genres.InteractorGenres;
import com.omerilhanli.tmdbmove.data.interactor.images.InteractorConfiguration;
import com.omerilhanli.tmdbmove.data.interactor.search.InteractorSearch;
import com.omerilhanli.tmdbmove.data.model.Configuration;
import com.omerilhanli.tmdbmove.data.model.Genre;
import com.omerilhanli.tmdbmove.data.model.Genres;
import com.omerilhanli.tmdbmove.data.model.Images;
import com.omerilhanli.tmdbmove.data.model.Movie;
import com.omerilhanli.tmdbmove.data.model.Movies;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

public class FragmentTab extends BaseFragment implements RecylerAdapter.ItemClickListener {

    private final String tag = getClass().getSimpleName();
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private RecylerAdapter adapter;

    private List<Movie> movieList;
    private List<Genre> genreList;
    private Images images;

    private Interactor interactor;
    private Interactor<Genres> genresInteractor;
    private Interactor<Configuration> configurationInteractor;
    private Interactor<Movies> searchInteractor;

    private int page = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        genresInteractor = new InteractorGenres(tmdbApi);

        configurationInteractor = new InteractorConfiguration(tmdbApi);

        searchInteractor = new InteractorSearch(tmdbApi);
    }

    @Override
    protected int getFragmentViewId() {

        return R.layout.fragment_tab;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initAll();
    }

    void initAll() {

        setupContentView();

        fetchMovies(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query.length() > 3) {

                    if (!checkNetwork()) {

                        return false;
                    }

                    if (!swipeRefreshLayout.isRefreshing()) {

                        swipeRefreshLayout.setRefreshing(true);
                    }

                    //---
                    if (interactor == null) return false;
                    //---

                    searchInteractor
                            .onRequest(query)
                            .subscribe(new Subscriber<Movies>() {

                                @Override
                                public void onCompleted() {
                                    Log.e(tag, "onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(tag, "onError");
                                }

                                @Override
                                public void onNext(Movies movies) {

                                    fetchGenreList(true, movies);
                                }
                            });
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupContentView() {

        swipeRefreshLayout.setOnRefreshListener(this);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);
    }

    void fetchMovies(final boolean isInitial) {

        if (!checkNetwork()) {

            return;
        }

        if (!swipeRefreshLayout.isRefreshing()) {

            swipeRefreshLayout.setRefreshing(true);
        }

        //---
        if (interactor == null) return;
        //---

        interactor.onRequest(page).subscribe(new Subscriber<Movies>() {

            @Override
            public void onCompleted() {
                Log.e(tag, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(tag, "onError");
            }

            @Override
            public void onNext(final Movies movies) {

                Log.e(tag, "onNext");

                fetchGenreList(isInitial, movies);
            }
        });
    }

    void fetchGenreList(final boolean isInitial, final Movies movies) {

        if (checkNetwork())

            genresInteractor.onRequest(page).subscribe(new Subscriber<Genres>() {

                @Override
                public void onCompleted() {
                    Log.e(tag, "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(tag, "onError");
                }

                @Override
                public void onNext(Genres genres) {

                    genreList = genres.genres;

                    fetchImages(isInitial, movies);
                }
            });
    }

    void fetchImages(final boolean isInitial, final Movies movies) {

        if (checkNetwork())

            configurationInteractor.onRequest(page).subscribe(new Subscriber<Configuration>() {

                @Override
                public void onCompleted() {
                    Log.e(tag, "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(tag, "onError");
                }

                @Override
                public void onNext(Configuration configuration) {

                    images = configuration.images;

                    if (isInitial) {

                        movieList = movies.movies;

                        prepareRecycler();

                    } else {

                        adapter.add(movies.movies);
                    }

                    recyclerView.setVisibility(View.VISIBLE);

                    if (swipeRefreshLayout.isRefreshing()) {

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
    }

    void prepareRecycler() {

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        manager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new RecylerAdapter(getContext(), this, this, genreList, images);

        adapter.getMovieList().addAll(movieList);

        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);
    }

    private boolean checkNetwork() {

        boolean isOnline = NetworkController.isOnline(getContext());

        if (!isOnline) {

            Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }

        return isOnline;
    }

    @Override
    public void onRefresh() {

        page = 1; // reset

        recyclerView.setVisibility(View.GONE);

        fetchMovies(true);
    }

    @Override
    public void onScrollToBottom(boolean visible) {

        if (visible) {

            page++;

            fetchMovies(false);
        }
    }

    public FragmentTab setInteractor(Interactor interactor) {

        this.interactor = interactor;

        return this;
    }

    public static FragmentTab newInstance() {

        return new FragmentTab();
    }

    @Override
    public void onItemClick(int movieId, String title) {

        DetailActivity.startActivityFrom(getActivity(), movieId, title);
    }
}
