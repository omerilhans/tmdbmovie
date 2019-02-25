package com.omerilhanli.tmdbmove.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omerilhanli.tmdbmove.App;
import com.omerilhanli.tmdbmove.UI.adapter.RecylerAdapter;
import com.omerilhanli.tmdbmove.api.TmdbApi;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends
        Fragment implements SwipeRefreshLayout.OnRefreshListener, RecylerAdapter.OnScroolToBottom {

    @Inject
    protected TmdbApi tmdbApi;

    protected abstract int getFragmentViewId();


    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((App) getActivity().getApplication()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getFragmentViewId(), container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        unbinder.unbind();
    }
}
