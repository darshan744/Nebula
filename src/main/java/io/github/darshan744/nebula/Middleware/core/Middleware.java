package io.github.darshan744.nebula.Middleware.core;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;

@FunctionalInterface
public interface Middleware {
  void handle(Request req, Response res, NextFn next);
}
