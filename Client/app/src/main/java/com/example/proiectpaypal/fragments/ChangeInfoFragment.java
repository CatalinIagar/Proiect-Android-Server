package com.example.proiectpaypal.fragments;

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
import com.example.proiectpaypal.databinding.FragmentChangeInfoBinding;
import com.example.proiectpaypal.databinding.FragmentTransferBinding;
import com.example.proiectpaypal.randomthings.CurrentUser;
import com.example.proiectpaypal.threads.TransferThread;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentChangeInfoBinding binding;

    private boolean isOldPasswordGood = false;
    private boolean isNewPasswordGood = false;
    private boolean isOldEmailGood = false;
    private boolean isNewEmailGood = false;

    private String oldPassword;
    private String newPassword;
    private String oldEmail;
    private String newEmail;

    public ChangeInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeInfoFragment newInstance(String param1, String param2) {
        ChangeInfoFragment fragment = new ChangeInfoFragment();
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
        //return inflater.inflate(R.layout.fragment_change_info, container, false);

        binding = FragmentChangeInfoBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.changePaswordTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    isOldPasswordGood = false;
                    binding.changePaswordInput.setHelperText("Required*");
                    binding.changePaswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
                if (charSequence.length() > 0) {
                    isOldPasswordGood = true;
                    oldPassword = charSequence.toString();
                    binding.changePaswordInput.setHelperText("");
                    binding.changePaswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.newPasswordTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();

                if (charSequence.length() == 0) {
                    isNewPasswordGood = false;
                    binding.newPasswordInput.setHelperText("Required*");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if (charSequence.length() < 8 && charSequence.length() > 0) {
                    isNewPasswordGood = false;
                    binding.newPasswordInput.setHelperText("Password must be at least 8 characters");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(!hasUpperCase(password)){
                    isNewPasswordGood = false;
                    binding.newPasswordInput.setHelperText("Password must contain at least 1 uppercase letter");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
                else if(!hasLowerCase(password)){
                    isNewPasswordGood = false;
                    binding.newPasswordInput.setHelperText("Password must contain at least 1 lowercase letter");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(!hasNumber(password)){
                    isNewPasswordGood = false;
                    binding.newPasswordInput.setHelperText("Password must contain at least 1 number");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(!hasSpecialChar(password)){
                    isNewPasswordGood = false;
                    binding.newPasswordInput.setHelperText("Password must contain at least 1 special character");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(charSequence.toString().equals(oldPassword)){
                    isNewEmailGood = false;
                    binding.newPasswordInput.setHelperText("New password can't be the old password");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else{
                    isNewPasswordGood = true;
                    newPassword = charSequence.toString();
                    binding.newPasswordInput.setHelperText("");
                    binding.newPasswordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.changeEmailTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmailValid(charSequence.toString())){
                    isOldEmailGood = false;
                    binding.changeEmailInput.setHelperText("Email is not valid");
                    binding.changeEmailInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else{
                    isOldEmailGood = true;
                    oldEmail = charSequence.toString();
                    binding.changeEmailInput.setHelperText("");
                    binding.changeEmailInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.newEmailTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmailValid(charSequence.toString())){
                    isNewEmailGood = false;
                    binding.newEmailInput.setHelperText("Email is not valid");
                    binding.newEmailInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(charSequence.toString().equals(oldEmail)){
                    isNewEmailGood = false;
                    binding.newEmailInput.setHelperText("New email can't be the old email");
                    binding.newEmailInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else{
                    isNewEmailGood = true;
                    newEmail = charSequence.toString();
                    binding.newEmailInput.setHelperText("");
                    binding.newEmailInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.changePasswordButton.setOnClickListener(view1 -> {
            if(isOldPasswordGood == false){
                Snackbar.make(view, "Inavlid old password", Snackbar.LENGTH_SHORT).show();
            }else if(isNewPasswordGood == false){
                Snackbar.make(view, "Inavlid new password", Snackbar.LENGTH_SHORT).show();
            }else if (isOldPasswordGood && isNewPasswordGood) {
                String changePassRequest = "changePass " + CurrentUser.getInstance().getUsername() + " " + oldPassword + " " + newPassword + " ";
                Handler handler = new Handler();
                new TransferThread(changePassRequest, view, handler).start();
            }
        });

        binding.changeEmailButton.setOnClickListener(view1 -> {
            if(isOldEmailGood == false){
                Snackbar.make(view, "Inavlid old password", Snackbar.LENGTH_SHORT).show();
            }else if(isNewEmailGood == false){
                Snackbar.make(view, "Inavlid new password", Snackbar.LENGTH_SHORT).show();
            }else if (isOldEmailGood && isNewEmailGood) {
                String changeEmailRequest = "changeEmail " + CurrentUser.getInstance().getUsername() + " " + oldEmail + " " + newEmail + " ";
                Handler handler = new Handler();
                new TransferThread(changeEmailRequest, view, handler).start();
            }
        });
    }

    boolean hasUpperCase(String str){
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(str);
        boolean b = m.find();

        return b;
    }

    boolean hasLowerCase(String str){
        Pattern p = Pattern.compile("[a-z]");
        Matcher m = p.matcher(str);
        boolean b = m.find();

        return b;
    }

    boolean hasNumber(String str){
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(str);
        boolean b = m.find();

        return b;
    }

    boolean hasSpecialChar(String str){
        Pattern p = Pattern.compile("[.@!#$%&'*+/=?^_`{|}~-]");
        Matcher m = p.matcher(str);
        boolean b = m.find();

        return b;
    }

    boolean isEmailValid(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);

        return m.matches();
    }
}