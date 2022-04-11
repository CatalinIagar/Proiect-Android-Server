package com.example.proiectpaypal.randomthings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proiectpaypal.fragments.AddBalanceFragment;
import com.example.proiectpaypal.fragments.ChangeInfoFragment;
import com.example.proiectpaypal.fragments.HomeFragment;
import com.example.proiectpaypal.fragments.InfoFragment;
import com.example.proiectpaypal.fragments.LoginFragment;
import com.example.proiectpaypal.fragments.SignupFragment;
import com.example.proiectpaypal.fragments.TransferFragment;

public class ViewPagerFragmentAdapter2 extends FragmentStateAdapter {

    private String[] titles = new String[] {"Home", "Add balance", "Transfer", "Change info", "info"};

    public ViewPagerFragmentAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new AddBalanceFragment();
            case 2:
                return new TransferFragment();
            case 3:
                return new ChangeInfoFragment();
            case 4:
                return new InfoFragment();
        }
        return  new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }


}
