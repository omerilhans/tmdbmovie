package com.omerilhanli.tmdbmove.data.interactor.movies;

import com.omerilhanli.tmdbmove.BuildConfig;
import com.omerilhanli.tmdbmove.api.TmdbApi;
import com.omerilhanli.tmdbmove.asistant.tools.DateFormatter;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.model.Movies;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InteractorAll implements Interactor<Movies> {

    private TmdbApi tmdbApi;

    public InteractorAll(TmdbApi tmdbApi) {
        this.tmdbApi = tmdbApi;
    }

    @Override
    public Observable<Movies> onRequest(Object value) {

        String releaseDate = DateFormatter.getReleaseDate();

        String sortBy = "primary_release_date.asc";

        return tmdbApi.getMovies(BuildConfig.TMDB_API_KEY, releaseDate, sortBy, (Integer) value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
