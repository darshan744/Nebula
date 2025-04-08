package com.github.darshan744.nebula.Http.HttpRequest.Exceptions;

public class RequestLineParserException extends HttpParserException{

    public RequestLineParserException(String cause) {
        super(cause ,"INVALID_REQUEST");
    }

    public RequestLineParserException(String message , Throwable cause) {
        super(message,"INVALID_REQUEST", cause);
    }

}
