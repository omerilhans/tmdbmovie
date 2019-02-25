package com.omerilhanli.tmdbmove.data.interactor.search;
import com.omerilhanli.tmdbmove.BuildConfig;
import com.omerilhanli.tmdbmove.api.TmdbApi;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.model.Movies;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InteractorSearch implements Interactor<Movies> {

    private TmdbApi tmdbApi;

    public InteractorSearch(TmdbApi tmdbApi) {

        this.tmdbApi = tmdbApi;
    }

    @Override
    public Observable<Movies> onRequest(Object value) {

        return tmdbApi.getSearchResult(BuildConfig.TMDB_API_KEY, (String) value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
