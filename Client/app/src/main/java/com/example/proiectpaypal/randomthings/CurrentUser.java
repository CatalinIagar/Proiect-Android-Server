package com.example.proiectpaypal.randomthings;

public class CurrentUser {
    private static CurrentUser instance = null;

    private String username;
    private String email;
    private String CNP;
    private String phoneNumber;
    private int balance;

    public static void setInstance(CurrentUser instance) {
        CurrentUser.instance = instance;
    }

    private CurrentUser(){

    }

    public static CurrentUser getInstance() {
        if(instance == null) instance = new CurrentUser();

        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
