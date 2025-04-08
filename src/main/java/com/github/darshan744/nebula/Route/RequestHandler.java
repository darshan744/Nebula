package com.github.darshan744.nebula.Route;

import com.github.darshan744.nebula.Http.HttpRequest.Request;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;

@FunctionalInterface
public interface RequestHandler {
    /**
     *@apiNote
     * <ul>
     * <li>This method helps in hanlding request 
     * <li>The response to be sent can be built via builder
     * <li>The Nebula will handle creating response object serialization and sending
     *<ul>
     */
    void handleRequest(Request request , HttpResponseBuilder builder);
}
