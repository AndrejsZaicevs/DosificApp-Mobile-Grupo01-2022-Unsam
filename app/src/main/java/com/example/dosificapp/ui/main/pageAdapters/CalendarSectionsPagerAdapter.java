package com.example.dosificapp.ui.main.pageAdapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dosificapp.R;
import com.example.dosificapp.dominio.CalendarDay;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.ui.main.fragments.CalendarFragment;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class CalendarSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.hoy, R.string.manana, R.string.pasado};
    private final Context mContext;
    private ArrayList<Dosis> dosis;

    public CalendarSectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<Dosis> dosis) {
        super(fm);
        mContext = context;
        this.dosis = dosis;
    }

    @Override
    public Fragment getItem(int position) {
        CalendarDay calendar = new CalendarDay(position);
        calendar.addListDosis(dosis);
        return CalendarFragment.newInstance(calendar);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}