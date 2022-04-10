package com.example.proiectpaypal.threads;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.proiectpaypal.randomthings.Action;
import com.example.proiectpaypal.randomthings.CurrentUser;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LogInThread extends Thread{
    private String ip;
    private int port;
    private String requestString;

    private Handler handler;
    private View view;
    private Action callback;

    public LogInThread (String message, View view, Handler handler, Action callback){
        this.ip = "192.168.56.1";
        this.port = 8000;
        this.requestString = message;
        this.handler = handler;
        this.view = view;
        this.callback = callback;
    }

    public void run(){
        try{
            Log.i("Thread", "Procces started");
            Socket socket = new Socket(ip, port);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(requestString);

            Thread.sleep(1000);

            char[] rawMessage = new char[1000];
            reader.read(rawMessage);

            String message = String.valueOf(rawMessage);

            String[] arrayMessage = message.split(" ");

            Log.i("Signup", arrayMessage[0]);

            if(arrayMessage[0].equals("login-ok")){
                CurrentUser.getInstance().setUsername(arrayMessage[1]);
                CurrentUser.getInstance().setEmail(arrayMessage[2]);
                CurrentUser.getInstance().setCNP(arrayMessage[3]);
                CurrentUser.getInstance().setPhoneNumber(arrayMessage[4]);
                CurrentUser.getInstance().setBalance(Integer.parseInt(arrayMessage[5]));
                callback.exec();
            }else if(arrayMessage[0].equals("login-err")){
                handler.post(() -> {
                    Snackbar.make(view, "Username or password incorrect", Snackbar.LENGTH_SHORT).show();
                });
            }

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }finally {
            Log.i("Thread", "Procces stopped");
        }
    }
}
