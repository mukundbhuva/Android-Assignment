package com.assignment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private int TabCount;

    public MyPagerAdapter(Context context, FragmentManager fm, int tabCount)
    {
        super(fm);
        this.TabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0: return new Chat();
            case 1: return new Dialog();
            case 2: return new TripSurvey();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return this.TabCount;
    }
}
