package com.omerilhanli.tmdbmove;

import android.app.Application;

import com.omerilhanli.tmdbmove.DI.component.AppComponent;

import com.omerilhanli.tmdbmove.DI.component.DaggerAppComponent;
import com.omerilhanli.tmdbmove.DI.module.ApiModule;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {

        super.onCreate();

        component = initDaggerComponent();
    }

    private AppComponent initDaggerComponent() {

        return DaggerAppComponent
                .builder().apiModule(new ApiModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }
}