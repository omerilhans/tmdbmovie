package com.omerilhanli.tmdbmove.UI.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.omerilhanli.tmdbmove.UI.fragment.FragmentTab;

import java.util.List;

public class TmdbPagerAdapter extends FragmentPagerAdapter {

    private List<FragmentTab> fragmentTabList;

    public TmdbPagerAdapter(FragmentManager fragmentManager, List<FragmentTab> fragmentTabList) {

        super(fragmentManager);

        this.fragmentTabList = fragmentTabList;
    }

    @Override
    public int getCount() {
        return fragmentTabList.size();
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentTabList.get(position);
    }
}
