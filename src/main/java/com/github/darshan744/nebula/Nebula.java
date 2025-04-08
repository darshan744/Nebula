package com.nebula;

import java.io.IOException;

import com.nebula.Logger.NebulaLogger;
import com.nebula.Logger.NebulaLoggerFactory;
import com.nebula.Middleware.Handler.MiddlewareRegistry;
import com.nebula.Middleware.core.LoggerMiddlware;
import com.nebula.Server.ServerListener;

public class Nebula {
    private static final NebulaLogger logger = NebulaLoggerFactory.getLogger(Nebula.class);
    public static void start() {
        start(7090);
    }

    public static void start(int port) {
        try {
            configureApplication();
            //server listener is another class that extends thread
            // incase in future if new configuration is to be done
            // we can spinn up the server while running the main thread for
            // setting up configurations ex : configuring built-in support for DB
            ServerListener serverListener = new ServerListener(port);
            
            // starts the thread;
            serverListener.start();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
    private static void configureApplication() {
        MiddlewareRegistry registry = MiddlewareRegistry.getRegistry();
        LoggerMiddlware loggerMiddlware = new LoggerMiddlware();
        registry.registerMiddleware(loggerMiddlware);
    }
}