package com.omerilhanli.tmdbmove.data.interactor.movie;
import com.omerilhanli.tmdbmove.BuildConfig;
import com.omerilhanli.tmdbmove.api.TmdbApi;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.model.Movie;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InteractorMovie implements Interactor<Movie> {

    private TmdbApi tmdbApi;

    public InteractorMovie(TmdbApi tmdbApi) {

        this.tmdbApi = tmdbApi;
    }

    @Override
    public Observable<Movie> onRequest(Object value) {

        return tmdbApi.getMovie((Integer) value, BuildConfig.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
