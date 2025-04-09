package io.github.darshan744.nebula.Http.HttpResponse;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.darshan744.nebula.Http.Constants.ContentType;
import io.github.darshan744.nebula.Http.Constants.HttpStatus;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;

public class HttpResponseBuilder {
    private Response response;
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(getClass());
   public HttpResponseBuilder() {
        response = new Response();
        addDefaultHeaders();
    }

    public HttpResponseBuilder setStatusCode(HttpStatus status) {
        response.setStatus(status);
        return this;
    }

    public HttpResponseBuilder addHeader(Headers header, String value) {
        addHeader(header.getHeader(), value);
        return this;
    }

    public HttpResponseBuilder addHeader(String header, String value) {
        response.getHeaders().put(header, value);
        return this;
    }

    public HttpResponseBuilder addContentType(ContentType contentType) {
        HashMap<String, String> headers = response.getHeaders();
        headers.put(Headers.CONTENT_TYPE.getHeader(), contentType.getContentType());
        return this;
    }

    public HttpResponseBuilder addBody(Object body){
        try {
           int contentLength = response.setContentBody(body);
           addHeader(Headers.CONTENT_LENGTH, String.valueOf(contentLength));
        } catch (JsonProcessingException e) {
            logger.severe(e.getMessage());
        }
        return this;
    }

    public Response build() {
        return this.response;
    }

    private void addDefaultHeaders() {
        addHeader(Headers.CONTENT_TYPE, ContentType.JSON.getContentType());
        addHeader(Headers.SERVER, "Nebula/0.1");
        addHeader(Headers.CONNECTION, "close");
    }

    public HttpResponseBuilder ok() {
        return setStatusCode(HttpStatus.OK);
    }

    public HttpResponseBuilder notFound() {
        return setStatusCode(HttpStatus.NOT_FOUND);
    }

    public HttpResponseBuilder serverError() {
        return setStatusCode(HttpStatus.SERVER_ERROR);
    }
    
}
