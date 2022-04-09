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
import com.example.proiectpaypal.databinding.FragmentSignup2Binding;
import com.example.proiectpaypal.threads.SignUpThread;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    boolean isUsernameGood = false;
    boolean isPasswordGood = false;
    boolean isConfirmPasswordGood = false;
    boolean isEmailGood = false;
    boolean isCNPGood = false;
    boolean isPhoneNumberGood = false;

    String currentUsername;
    String currentPassword;
    String currentEmail;
    String currentCNP;
    String currentPhoneNumber;

    FragmentSignup2Binding binding;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        binding = FragmentSignup2Binding.inflate(getLayoutInflater());

        return binding.getRoot();

        //return inflater.inflate(R.layout.fragment_signup2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.usernameTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    isUsernameGood = false;
                    binding.usernameInput.setHelperText("Required*");
                    binding.usernameInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if (charSequence.length() < 8 && charSequence.length() > 0) {
                    isUsernameGood = false;
                    binding.usernameInput.setHelperText("Username must be at least 8 characters");
                    binding.usernameInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if (charSequence.length() >= 8) {
                    isUsernameGood = true;
                    currentUsername = charSequence.toString();
                    binding.usernameInput.setHelperText("");
                    binding.usernameInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
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

                String password = charSequence.toString();

                if (charSequence.length() == 0) {
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Required*");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if (charSequence.length() < 8 && charSequence.length() > 0) {
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Password must be at least 8 characters");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(!hasUpperCase(password)){
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Password must contain at least 1 uppercase letter");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
                else if(!hasLowerCase(password)){
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Password must contain at least 1 lowercase letter");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(!hasNumber(password)){
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Password must contain at least 1 number");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(!hasSpecialChar(password)){
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Password must contain at least 1 special character");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else{
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

        binding.passwordConfimTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isPasswordGood){
                    if(!charSequence.toString().equals(currentPassword)){
                        isConfirmPasswordGood = false;
                        binding.passwordConfirmInput.setHelperText("Passwords don't match");
                        binding.passwordConfirmInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else{
                        isConfirmPasswordGood = true;
                        binding.passwordConfirmInput.setHelperText("");
                        binding.passwordConfirmInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.EmailTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmailValid(charSequence.toString())){
                    isEmailGood = false;
                    binding.EmailInput.setHelperText("Email is not valid");
                    binding.EmailInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else{
                    isEmailGood = true;
                    currentEmail = charSequence.toString();
                    binding.EmailInput.setHelperText("");
                    binding.EmailInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.PhoneNumberTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() < 10){
                    isPhoneNumberGood = false;
                    binding.PhoneNumberInput.setHelperText("Number must have 10 digits");
                    binding.PhoneNumberInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                } else if(!isNumberValid(charSequence.toString())){
                    isPhoneNumberGood = false;
                    binding.PhoneNumberInput.setHelperText("Number is not valid");
                    binding.PhoneNumberInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else{
                    isPhoneNumberGood = true;
                    currentPhoneNumber = charSequence.toString();
                    binding.PhoneNumberInput.setHelperText("");
                    binding.PhoneNumberInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.CNPTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() < 13){
                    isCNPGood = false;
                    binding.CNPInput.setHelperText("CNP must have 13 digits");
                    binding.CNPInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else if(!isCNPValid(charSequence.toString())){
                    isCNPGood = false;
                    binding.CNPInput.setHelperText("CNP is not valid");
                    binding.CNPInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }else{
                    isCNPGood = true;
                    currentCNP = charSequence.toString();
                    binding.CNPInput.setHelperText("");
                    binding.CNPInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUsernameGood == false){
                    Snackbar.make(view, "Username incorrect", Snackbar.LENGTH_SHORT).show();
                }else if(isPasswordGood == false){
                    Snackbar.make(view, "Password incorrect", Snackbar.LENGTH_SHORT).show();
                }else if(isEmailGood == false){
                    Snackbar.make(view, "Email incorrect", Snackbar.LENGTH_SHORT).show();
                }else if(isCNPGood == false){
                    Snackbar.make(view, "CNP incorrect", Snackbar.LENGTH_SHORT).show();
                }else if(isPhoneNumberGood == false){
                    Snackbar.make(view, "Phone number incorrect", Snackbar.LENGTH_SHORT).show();
                }else{
                    String requestSignup = "signup " + currentUsername + " " + currentPassword + " " + currentEmail + " " + currentCNP + " " + currentPhoneNumber + " ";
                    //Do something with server request and wait for response

                }
            }
        });

        binding.singupButton.setOnClickListener(view1 -> {
            if(isUsernameGood == false){
                Snackbar.make(view, "Username incorrect", Snackbar.LENGTH_SHORT).show();
            }else if(isPasswordGood == false){
                Snackbar.make(view, "Password incorrect", Snackbar.LENGTH_SHORT).show();
            }else if(isEmailGood == false){
                Snackbar.make(view, "Email incorrect", Snackbar.LENGTH_SHORT).show();
            }else if(isCNPGood == false){
                Snackbar.make(view, "CNP incorrect", Snackbar.LENGTH_SHORT).show();
            }else if(isPhoneNumberGood == false){
                Snackbar.make(view, "Phone number incorrect", Snackbar.LENGTH_SHORT).show();
            }else if(isUsernameGood && isConfirmPasswordGood && isPasswordGood && isEmailGood && isCNPGood && isPhoneNumberGood){
                String requestSignup = "signup " + currentUsername + " " + currentPassword + " " + currentEmail + " " + currentCNP + " " + currentPhoneNumber + " ";
                Handler handler = new Handler();
                    new SignUpThread(requestSignup, view, handler).start();

                }
        });
    }

    public boolean isCNPValid(String CNP) {
        String constCNP = "279146358279";
        int c = 0;

        for (int i = 0; i < 12; i++){
            String digit = String.valueOf(CNP.charAt(i));
            String testDigit = String.valueOf(constCNP.charAt(i));
            c += (Integer.parseInt(digit) * Integer.parseInt(testDigit));
        }

        int res = c % 11;

        if(res == 10) res = 1;

        if (res != Integer.parseInt(String.valueOf(CNP.charAt((12))))) return false;

        return true;
    }

    boolean isNumberValid(String s)
    {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(s);

        return (m.matches());
    }

    boolean isEmailValid(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);

        return m.matches();
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
        Pattern p = Pattern.compile("[.!#$%&'*+/=?^_`{|}~-]");
        Matcher m = p.matcher(str);
        boolean b = m.find();

        return b;
    }
}