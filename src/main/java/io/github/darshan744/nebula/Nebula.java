package io.github.darshan744.nebula;

import java.io.IOException;

import io.github.darshan744.nebula.Http.Constants.HttpMethod;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.core.Middleware;
import io.github.darshan744.nebula.Middleware.core.MiddlewareRegistry;
import io.github.darshan744.nebula.Route.RequestHandler;
import io.github.darshan744.nebula.Route.Router;
import io.github.darshan744.nebula.Server.ServerListener;

public class Nebula {
    private static final NebulaLogger logger = NebulaLoggerFactory.getLogger(Nebula.class);
    private static Integer port = null;
    private Router router = Router.getRouter();
    private HttpMethod GET = HttpMethod.GET;
    private HttpMethod POST = HttpMethod.POST;
    private HttpMethod PUT = HttpMethod.PUT;
    private HttpMethod DELETE = HttpMethod.DELETE;
    private MiddlewareRegistry middlewareRegistry = MiddlewareRegistry.getRegistry();

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
        System.out.println(registry.getMiddlewares());
    }

    public Nebula get(String path, RequestHandler requestHandler) {
        this.router.registerRoute(GET, path, requestHandler);
        return this;
    }

    public Nebula post(String path, RequestHandler requestHandler) {
        this.router.registerRoute(POST, path, requestHandler);
        return this;
    }

    public Nebula put(String path, RequestHandler requestHandler) {
        this.router.registerRoute(PUT, path, requestHandler);
        return this;
    }

    public Nebula delete(String path, RequestHandler requestHandler) {
        this.router.registerRoute(DELETE, path, requestHandler);
        return this;
    }

    public Nebula use(Middleware middleware) {
        this.middlewareRegistry.registerMiddleware(middleware);
        return this;
    }
}
