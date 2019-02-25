package com.omerilhanli.tmdbmove.DI.component;

import com.omerilhanli.tmdbmove.DI.module.ApiModule;
import com.omerilhanli.tmdbmove.UI.BaseActivity;
import com.omerilhanli.tmdbmove.UI.BaseFragment;
import com.omerilhanli.tmdbmove.api.TmdbApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApiModule.class)
public interface AppComponent {

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

    TmdbApi apiService();
}
