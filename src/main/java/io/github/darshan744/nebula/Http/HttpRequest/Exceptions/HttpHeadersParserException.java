package io.github.darshan744.nebula.Http.HttpRequest.Exceptions;

public class HttpHeadersParserException extends HttpParserException {
    public HttpHeadersParserException(String cause) {
        super(cause , "INVALID_HEADER");
    }
}
