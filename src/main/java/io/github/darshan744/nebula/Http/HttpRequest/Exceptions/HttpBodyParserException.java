package io.github.darshan744.nebula.Http.HttpRequest.Exceptions;

public class HttpBodyParserException extends HttpParserException {

    public HttpBodyParserException(String cause) {
        super(cause ,"INVALID_BODY");
    }
    public HttpBodyParserException(String message , Throwable cause , String errorCode) {
        super(message,"INVALID_BODY" , cause);
    }


}
