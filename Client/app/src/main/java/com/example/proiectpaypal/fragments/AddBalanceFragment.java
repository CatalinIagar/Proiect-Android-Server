package com.example.proiectpaypal.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proiectpaypal.R;
import com.example.proiectpaypal.activities.LoginActivity;
import com.example.proiectpaypal.databinding.FragmentAddBalanceBinding;
import com.example.proiectpaypal.databinding.FragmentSignup2Binding;
import com.example.proiectpaypal.randomthings.CurrentUser;
import com.example.proiectpaypal.threads.AddBalanceThread;
import com.example.proiectpaypal.threads.LogInThread;
import com.example.proiectpaypal.threads.SignUpThread;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBalanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBalanceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean isBalanceGood = false;
    private boolean isPasswordGood = false;

    private String currentValue;
    private String currentPassword;

    FragmentAddBalanceBinding binding;

    public AddBalanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBalanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBalanceFragment newInstance(String param1, String param2) {
        AddBalanceFragment fragment = new AddBalanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_balance, container, false);

        binding = FragmentAddBalanceBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.balanceTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int value = 0;
                if(!charSequence.toString().equals(""))
                    value = Integer.parseInt(charSequence.toString());
                if(value <= 0){
                    isPasswordGood = false;
                    binding.balanceInput.setHelperText("Invalid Value");
                    binding.balanceInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(value > 0){
                    isBalanceGood = true;
                    currentValue = charSequence.toString();
                    binding.balanceInput.setHelperText("");
                    binding.balanceInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.passwordTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Required*");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
                if (charSequence.length() > 0) {
                    isPasswordGood = true;
                    currentPassword = charSequence.toString();
                    binding.passwordInput.setHelperText("");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.addBalanceButton.setOnClickListener(view1 -> {
            if(isBalanceGood == false){
                Snackbar.make(view1, "Invalid value", Snackbar.LENGTH_SHORT).show();
            }else if(isPasswordGood == false){
                Snackbar.make(view1, "Invalid password", Snackbar.LENGTH_SHORT).show();
            }else if (isBalanceGood && isPasswordGood) {
                String addBalanceRequest = "addBalance " + CurrentUser.getInstance().getUsername() + " " + currentPassword + " " + currentValue + " ";
                Handler handler = new Handler();
                new AddBalanceThread(addBalanceRequest, view, handler).start();
            }
        });
    }
}