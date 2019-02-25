package com.omerilhanli.tmdbmove.data.interactor.images;

import com.omerilhanli.tmdbmove.BuildConfig;
import com.omerilhanli.tmdbmove.api.TmdbApi;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.model.Configuration;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InteractorConfiguration implements Interactor<Configuration> {

    private TmdbApi tmdbApi;

    public InteractorConfiguration(TmdbApi tmdbApi){

        this.tmdbApi=tmdbApi;
    }

    @Override
    public Observable<Configuration> onRequest(Object value) {

        return tmdbApi.getConfiguration(BuildConfig.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
