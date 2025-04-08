package com.nebula;

import java.io.IOException;

import com.nebula.Logger.NebulaLogger;
import com.nebula.Logger.NebulaLoggerFactory;
import com.nebula.Server.ServerListener;

public class Nebula {
    private static final NebulaLogger logger = NebulaLoggerFactory.getLogger(Nebula.class);
    public static void start() {
        try {
            ServerListener serverListener = new ServerListener(7090);
            serverListener.start();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}