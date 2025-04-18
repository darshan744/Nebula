package io.github.darshan744.nebula.Http.HttpRequest.Exceptions;

public class BodyParsingException extends RuntimeException{

    public BodyParsingException(String message , Throwable cause){ 
        super(message , cause);
    }
    public BodyParsingException(String message) {super(message);}
}
