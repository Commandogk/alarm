package com.example.alarm.ui.main;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.alarm.R;
import com.example.alarm.alarm;
import com.example.alarm.stopwatch;
import com.example.alarm.timer;
import com.google.android.material.tabs.TabLayout;

import static com.example.alarm.R.*;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{string.tab_text_1, string.tab_text_2, string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                alarm a = new alarm();
                return a;
            }
            case 1: {
                stopwatch b = new stopwatch();
                return b;
            }
            case 2: {
                timer c = new timer();
                return c;
            }
            default: return  null;
        }
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