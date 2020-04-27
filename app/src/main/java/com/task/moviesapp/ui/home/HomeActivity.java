package com.task.moviesapp.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.tabs.TabLayout;
import com.task.moviesapp.R;
import com.task.moviesapp.databinding.ActivityHomeBinding;
import com.task.moviesapp.ui.search.SearchActivity;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        }

        initView();
    }

    private void initView() {

        HomePagerAdapter pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), this);


        binding.viewPager.setAdapter(pagerAdapter);

        binding.tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        binding.tabLayout.setTabIndicatorFullWidth(false);
        binding.tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.colorAccent));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);

        binding.searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });
    }
}
