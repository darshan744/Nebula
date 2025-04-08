package com.github.darshan744.nebula.Middleware.core;

import com.github.darshan744.nebula.Http.HttpRequest.Request;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.github.darshan744.nebula.Logger.NebulaLogger;
import com.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import com.github.darshan744.nebula.Middleware.Middleware;
import com.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;

public class LoggerMiddlware implements Middleware{
    private NebulaLogger logger = NebulaLoggerFactory.getLogger(LoggerMiddlware.class);
    @Override
    public void middlewareHandler(Request req, HttpResponseBuilder resBuilder, MiddlewareChain next) {
        logger.info("Forwarding Request to Route : " + req.getUrl() + " Method : " + req.getMethod());
        next.next(req, resBuilder, next);
    }
}
