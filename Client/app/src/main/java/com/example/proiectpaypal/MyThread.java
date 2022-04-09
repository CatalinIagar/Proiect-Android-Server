package com.example.proiectpaypal;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyThread extends Thread{
    String ip;
    int port;
    String requestString;

    public MyThread (String message){
        /*try{
            this.ip = InetAddress.getLocalHost();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }*/
        this.ip = "192.168.56.1";
        //this.ip = "localhost";
        this.port = 8000;
        this.requestString = message;
    }

    public void run(){
        try{
            Log.i("Thread", "Procces started");
            Socket socket = new Socket(ip, port);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(requestString);

            Thread.sleep(1000);

            char[] rawMessage = new char[100];
            reader.read(rawMessage);

            String message = String.valueOf(rawMessage);

            String[] arrayMessage = message.split(" ");

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }finally {
            Log.i("Thread", "Procces stopped");
        }
    }
}
