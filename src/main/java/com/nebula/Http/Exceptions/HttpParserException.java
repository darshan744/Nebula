package com.nebula.Http.Exceptions;

public class HttpParserException extends Exception {
    private final String errorCode;
   public HttpParserException(String cause , String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public HttpParserException(String message ,String errorCode, Throwable cause) {
        super(message , cause);
        this.errorCode =errorCode;
    }
    public String getErrorCode() {
       return this.errorCode;
    }

}
