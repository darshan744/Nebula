package com.nebula;

import java.io.IOException;

import com.nebula.Server.ServerListener;

public class Nebula {
    public static void start() {
        try {
            ServerListener serverListener = new ServerListener(7090);
            serverListener.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}