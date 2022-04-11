package com.example.proiectpaypal.threads;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.proiectpaypal.randomthings.CurrentUser;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TransferThread extends Thread{
    private String ip;
    private int port;
    private String requestString;

    private Handler handler;
    private View view;

    public TransferThread (String message, View view, Handler handler){
        this.ip = "192.168.56.1";
        this.port = 8000;
        this.requestString = message;
        this.handler = handler;
        this.view = view;
    }

    @Override
    public void run() {
        try{
            Log.i("Thread", "Procces started");
            Socket socket = new Socket(ip, port);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(requestString);

            Thread.sleep(500);

            char[] rawMessage = new char[100];
            reader.read(rawMessage);

            String message = String.valueOf(rawMessage);

            String[] arrayMessage = message.split(" ");

            if(arrayMessage[0].equals("transfer-ok")){
                handler.post(() -> {
                    Snackbar.make(view, "Transfer sucess", Snackbar.LENGTH_SHORT).show();
                    CurrentUser.getInstance().setBalance(Integer.parseInt(arrayMessage[1]));
                });
            }else if(arrayMessage[0].equals("transfer-err")){
                handler.post(() -> {
                    Snackbar.make(view, "Invalid password", Snackbar.LENGTH_SHORT).show();
                });
            }else if(arrayMessage[0].equals("transfer-user-err")){
                handler.post(() -> {
                    Snackbar.make(view, "Username doesn't exist", Snackbar.LENGTH_SHORT).show();
                });
            }

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }finally {
            Log.i("Thread", "Procces stopped");
        }
    }
}
