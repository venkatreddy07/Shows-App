package com.task.moviesapp.ui.home;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.task.moviesapp.R;
import com.task.moviesapp.ui.home.movieFragment.MovieFragment;
import com.task.moviesapp.ui.home.showFragment.ShowFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public HomePagerAdapter(FragmentManager supportFragmentManager, Context context) {
        super(supportFragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieFragment.getInstance();

            case 1:
                return ShowFragment.getInstance();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.movies);
            case 1:
                return context.getString(R.string.shows);
            default:
                return null;
        }
    }
}
