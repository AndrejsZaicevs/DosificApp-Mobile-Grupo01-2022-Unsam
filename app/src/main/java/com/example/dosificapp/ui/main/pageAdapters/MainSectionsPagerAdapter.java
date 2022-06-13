package com.example.dosificapp.ui.main.pageAdapters;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.dosificapp.R;
import com.example.dosificapp.data.model.LoggedInUser;
import com.example.dosificapp.ui.main.fragments.AbstractFragment;
import com.example.dosificapp.ui.main.fragments.AcompaFragment;
import com.example.dosificapp.ui.main.fragments.TomasFragment;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.prox_tomas, R.string.acomp};
    private final Context mContext;
    private LoggedInUser user;
    private  ArrayList<AbstractFragment> fragments;

    public MainSectionsPagerAdapter(Context context, FragmentManager fm, LoggedInUser _user) {
        super(fm);
        mContext = context;
        user = _user;
        initFragments(user);
    }

    private ArrayList<AbstractFragment> initFragments(LoggedInUser user){
        fragments = new ArrayList<AbstractFragment>();
        Integer index = 1;
        if(user.getType().contains("P")){ fragments.add(TomasFragment.newInstance(index++)); }
        if(user.getType().contains("A")){ fragments.add(AcompaFragment.newInstance(index++)); }
        return fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}