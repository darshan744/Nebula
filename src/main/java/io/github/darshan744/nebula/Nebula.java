package io.github.darshan744.nebula;

import java.io.IOException;

import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.core.MiddlewareRegistry;
import io.github.darshan744.nebula.Server.ServerListener;

public class Nebula {
    private static final NebulaLogger logger = NebulaLoggerFactory.getLogger(Nebula.class);
    private static Integer port = null;

    public static void start() {
        start(7090);
    }

    /**
     * @param port to listen on
     */
    public static void start(int port) {
        // if already started the server
        // throw error
        // TODO : Have multiple instance support for Servers
        if (Nebula.port != null) {
            logger.warn("Server Already Started");
            return;
        }
        try {

            configureApplication();
            ServerListener serverListener = new ServerListener(port);
            Nebula.port = port;
            // starts the thread;
            serverListener.start();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    private static void configureApplication() {
        logger.config("Configuring Application");
        configureMiddleware();
        // Other configuration methods goes here
    }

    // configures default middlewares
    private static void configureMiddleware() {
        logger.config("Registering Default Middlewares");
        MiddlewareRegistry registry = MiddlewareRegistry.getRegistry();
    }
}
