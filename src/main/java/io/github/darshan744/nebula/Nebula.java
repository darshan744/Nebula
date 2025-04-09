package io.github.darshan744.nebula;

import java.io.IOException;

import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.Handler.MiddlewareRegistry;
import io.github.darshan744.nebula.Middleware.core.LoggerMiddlware;
import io.github.darshan744.nebula.Server.ServerListener;

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
    /**
     * Configues application
     */
    private static void configureApplication() {
        logger.config("Configuring Application");
        configureMiddleware();
    }
    //configures default middlewares
    private static void configureMiddleware() {
        logger.config("Registering Default Middlewares");
        MiddlewareRegistry registry = MiddlewareRegistry.getRegistry();
        LoggerMiddlware loggerMiddlware = new LoggerMiddlware();
        registry.registerMiddleware(loggerMiddlware);
    }
}