package com.omerilhanli.tmdbmove.data.interactor.genres;

import com.omerilhanli.tmdbmove.BuildConfig;
import com.omerilhanli.tmdbmove.api.TmdbApi;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.model.Genres;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InteractorGenres implements Interactor<Genres> {

    private TmdbApi tmdbApi;

    public InteractorGenres(TmdbApi tmdbApi){

        this.tmdbApi=tmdbApi;
    }

    @Override
    public Observable<Genres> onRequest(Object value) {

        return tmdbApi.getGenres(BuildConfig.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
