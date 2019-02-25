package com.omerilhanli.tmdbmove.data.interactor.movies;

import com.omerilhanli.tmdbmove.BuildConfig;
import com.omerilhanli.tmdbmove.api.TmdbApi;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.model.Movies;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InteractorNowPlaying implements Interactor<Movies> {

    private TmdbApi tmdbApi;

    public InteractorNowPlaying(TmdbApi tmdbApi) {

        this.tmdbApi = tmdbApi;
    }

    @Override
    public Observable<Movies> onRequest(Object value) {

        return tmdbApi.getMoviesNowPlaying(BuildConfig.TMDB_API_KEY, (Integer) value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
