package com.example.proiectpaypal.randomthings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proiectpaypal.fragments.LoginFragment;
import com.example.proiectpaypal.fragments.SignupFragment;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    private String[] titles = new String[] {"Login", "Signup"};

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new SignupFragment();
        }
        return  new LoginFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
