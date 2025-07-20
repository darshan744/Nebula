package io.github.darshan744.nebula;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.github.darshan744.nebula.Http.Constants.HttpMethod;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.core.Middleware;
import io.github.darshan744.nebula.Middleware.core.MiddlewareRegistry;
import io.github.darshan744.nebula.Route.RequestHandler;
import io.github.darshan744.nebula.Route.Router;
import io.github.darshan744.nebula.Server.HttpWorkerThread;

public class Nebula {
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(Nebula.class);
    private static Integer port = null;
    private final Router router = Router.getRouter();
    private final HttpMethod GET = HttpMethod.GET;
    private final HttpMethod POST = HttpMethod.POST;
    private final HttpMethod PUT = HttpMethod.PUT;
    private final HttpMethod DELETE = HttpMethod.DELETE;
    private final MiddlewareRegistry middlewareRegistry = MiddlewareRegistry.getRegistry();
    private ServerSocket serverSocket = null;
    public void start() {
        start(7090);
    }

    /**
     * @param port to listen on
     */
    public void start(int port) {
        // if already started the server
        // throw error
        // TODO : Have multiple instance support for Servers
        if (Nebula.port != null) {
            logger.warn("Server Already Started");
            return;
        }
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        configureApplication();
            Nebula.port = port;
            handleIncomingRequest();
            logger.info("Nebula stopped");
            logger.info("Exiting server");
    }
    private void handleIncomingRequest() {
        Socket socket = null;
        while(serverSocket.isBound() && !serverSocket.isClosed()) {
            try {
                // waits for new incoming request
                socket = serverSocket.accept();
                System.out.println(socket);
                //after incoming request we get a separate socket for that request
                logger.info("Connection Received : " + socket.getLocalAddress());
                //using threads we can manage the new incoming socket
                HttpWorkerThread httpWorkerThread = new HttpWorkerThread(socket);
                httpWorkerThread.start();
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }
    }
    private void configureApplication() {
        logger.config("Configuring Application");
        configureMiddleware();
    }

    // configures default middlewares
    private void configureMiddleware() {
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
