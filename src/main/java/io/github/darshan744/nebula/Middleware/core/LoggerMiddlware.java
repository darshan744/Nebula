package io.github.darshan744.nebula.Middleware.core;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.Middleware;
import io.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;

public class LoggerMiddlware implements Middleware{
    private NebulaLogger logger = NebulaLoggerFactory.getLogger(LoggerMiddlware.class);
    @Override
    public void middlewareHandler(Request req, Response res, MiddlewareChain next) {
        logger.info("Forwarding Request to Route : " + req.getUrl() + " Method : " + req.getMethod());
        next.next(req, res, next);
    }
}
