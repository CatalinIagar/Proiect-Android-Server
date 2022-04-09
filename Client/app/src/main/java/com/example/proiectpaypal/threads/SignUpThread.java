package com.example.proiectpaypal.threads;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SignUpThread extends Thread{
    private String ip;
    private int port;
    private String requestString;

    private Handler handler;
    private View view;

    public SignUpThread (String message, View view, Handler handler){
        this.ip = "192.168.56.1";
        this.port = 8000;
        this.requestString = message;
        this.handler = handler;
        this.view = view;
    }

    public void run(){
        try{
            Log.i("Thread", "Procces started");
            Socket socket = new Socket(ip, port);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(requestString);

            Thread.sleep(2500);

            char[] rawMessage = new char[100];
            reader.read(rawMessage);

            String message = String.valueOf(rawMessage);

            String[] arrayMessage = message.split(" ");

            Log.i("Signup", arrayMessage[0]);

            if(arrayMessage[0].equals("signup-ok")){
                handler.post(() -> {
                    Snackbar.make(view, "Account created", Snackbar.LENGTH_SHORT).show();
                });
            }else if(arrayMessage[0].equals("signup-user-err")){
                handler.post(() -> {
                    Snackbar.make(view, "User already exists", Snackbar.LENGTH_SHORT).show();
                });
            }else if(arrayMessage[0].equals("signup-email-err")){
                handler.post(() -> {
                    Snackbar.make(view, "Email already exists", Snackbar.LENGTH_SHORT).show();
                });
            }

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }finally {
            Log.i("Thread", "Procces stopped");
        }
    }
}
