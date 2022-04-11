package com.example.proiectpaypal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.proiectpaypal.R;
import com.example.proiectpaypal.randomthings.ViewPagerFragmentAdapter2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {

    ViewPagerFragmentAdapter2 viewPagerFragmentAdapter2;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    private String[] titles = new String[] {"Home", "Add balance", "Transfer", "Change info", "Info"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getSupportActionBar().hide();

        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPagerFragmentAdapter2 = new ViewPagerFragmentAdapter2(this);

        viewPager2.setAdapter(viewPagerFragmentAdapter2);

        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(titles[position]))).attach();
    }
}