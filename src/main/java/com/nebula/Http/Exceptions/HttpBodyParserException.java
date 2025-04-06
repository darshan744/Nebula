package com.nebula.Http.Exceptions;

public class HttpBodyParserException extends HttpParserException {

    public HttpBodyParserException(String cause) {
        super(cause ,"INVALID_BODY");
        //TODO Auto-generated constructor stub
    }
    public HttpBodyParserException(String message , Throwable cause , String errorCode) {
        super(message,"INVALID_BODY" , cause);
    }


}
