package com.omerilhanli.tmdbmove.UI.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.omerilhanli.tmdbmove.R;
import com.omerilhanli.tmdbmove.UI.BaseActivity;
import com.omerilhanli.tmdbmove.UI.adapter.TmdbPagerAdapter;
import com.omerilhanli.tmdbmove.UI.fragment.FragmentTab;
import com.omerilhanli.tmdbmove.asistant.common.Constants;
import com.omerilhanli.tmdbmove.asistant.widget.PagerIndicator;
import com.omerilhanli.tmdbmove.data.interactor.Interactor;
import com.omerilhanli.tmdbmove.data.interactor.movies.InteractorAll;
import com.omerilhanli.tmdbmove.data.interactor.movies.InteractorNowPlaying;
import com.omerilhanli.tmdbmove.data.interactor.movies.InteractorPopular;
import com.omerilhanli.tmdbmove.data.interactor.movies.InteractorTopRated;
import com.omerilhanli.tmdbmove.data.interactor.movies.InteractorUpComing;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initAll();
    }

    private void initAll() {

        setTitle(Constants.TITLE);

        TmdbPagerAdapter adapter = new TmdbPagerAdapter(getSupportFragmentManager(), createTabList());

        viewPager.setAdapter(adapter);

        pagerIndicator.setViewPager(viewPager);
    }

    List<FragmentTab> createTabList() {

        List<FragmentTab> fragmentTabList = new ArrayList<>();

        fragmentTabList.add(create(new InteractorAll(tmdbApi)));

        fragmentTabList.add(create(new InteractorNowPlaying(tmdbApi)));

        fragmentTabList.add(create(new InteractorPopular(tmdbApi)));

        fragmentTabList.add(create(new InteractorTopRated(tmdbApi)));

        fragmentTabList.add(create(new InteractorUpComing(tmdbApi)));

        return fragmentTabList;
    }

    FragmentTab create(Interactor interactor) {

        return FragmentTab.newInstance().setInteractor(interactor);
    }

    public static void startActivityFrom(Activity activity) {

        Intent intent = new Intent(activity, MainActivity.class);

        activity.startActivity(intent);

        activity.finish();
    }
}
