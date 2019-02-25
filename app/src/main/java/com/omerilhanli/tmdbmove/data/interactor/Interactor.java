package com.omerilhanli.tmdbmove.data.interactor;

import rx.Observable;

public interface Interactor<T> {

    Observable<T> onRequest(Object value);
}
