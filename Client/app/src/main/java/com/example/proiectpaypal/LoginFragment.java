package com.example.proiectpaypal;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proiectpaypal.databinding.FragmentLoginBinding;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FragmentLoginBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    boolean isUsernameGood = false;
    boolean isPasswordGood = false;

    String currentUsername;
    String currnetPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

        binding = FragmentLoginBinding.inflate(getLayoutInflater());


        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.usernameTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == 0) {
                    isUsernameGood = false;
                    binding.usernameInput.setHelperText("Required*");
                    binding.usernameInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
                if (charSequence.length() < 8 && charSequence.length() > 0) {
                    isUsernameGood = false;
                    binding.usernameInput.setHelperText("Username must be at least 8 characters");
                    binding.usernameInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
                if (charSequence.length() > 8) {
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
                if (charSequence.length() == 0) {
                    isPasswordGood = false;
                    binding.passwordInput.setHelperText("Required*");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
                if (charSequence.length() > 0) {
                    isPasswordGood = true;
                    currnetPassword = charSequence.toString();
                    binding.passwordInput.setHelperText("");
                    binding.passwordInput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUsernameGood == false){
                    Snackbar.make(view, "Username incorrect", Snackbar.LENGTH_SHORT).show();
                }else if(isPasswordGood == false){
                    Snackbar.make(view, "Password incorrect", Snackbar.LENGTH_SHORT).show();
                }else{
                    String loginRequest = "login " + currentUsername + " " + currnetPassword;
                    //Do stuff with server and wait for server response
                }
            }
        });
    }
}