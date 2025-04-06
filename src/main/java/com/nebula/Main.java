package com.nebula;

import java.io.IOException;

import com.nebula.Server.ServerListener;

public class Main {
    //TODO Implement Logger
    public static void main(String[] args) {
        try {
            ServerListener serverListener = new ServerListener(7090);
            serverListener.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}