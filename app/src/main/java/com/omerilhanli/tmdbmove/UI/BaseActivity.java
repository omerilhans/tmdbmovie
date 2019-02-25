package com.omerilhanli.tmdbmove.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.omerilhanli.tmdbmove.App;
import com.omerilhanli.tmdbmove.api.TmdbApi;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Inject
    protected TmdbApi tmdbApi;

    protected abstract int getActivityLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initAll();
    }

    private void initAll() {

        setContentView(getActivityLayoutId());

        unbinder = ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        unbinder.unbind();
    }
}
