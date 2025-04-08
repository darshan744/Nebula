package com.nebula.Http.HttpRequest.Exceptions;

public class HttpHeadersParserException extends HttpParserException {

    public HttpHeadersParserException(String cause) {
        super(cause , "INVALID_HEADER");
    }
    
    public HttpHeadersParserException(String message , Throwable cause) {
        super(message,"INVALID_HEADER" , cause);
    }
    

}
