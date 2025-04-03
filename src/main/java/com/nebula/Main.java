package com.nebula;

import java.io.IOException;

import com.nebula.Server.ServerListener;

public class Main {
    //TODO Implement Logger
    public static void main(String[] args) {
        
        System.out.println("Server running");
        try {
            ServerListener serverListener = new ServerListener(7090);
            serverListener.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}