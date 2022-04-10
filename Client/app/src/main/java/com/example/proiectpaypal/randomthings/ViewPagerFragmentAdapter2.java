package com.example.proiectpaypal.randomthings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proiectpaypal.fragments.HomeFragment;
import com.example.proiectpaypal.fragments.LoginFragment;
import com.example.proiectpaypal.fragments.SignupFragment;

public class ViewPagerFragmentAdapter2 extends FragmentStateAdapter {

    private String[] titles = new String[] {"Home", "Add balance", "Transfer"};

    public ViewPagerFragmentAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            /*case 1:
                return new HomeFragment();*/
        }
        return  new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
