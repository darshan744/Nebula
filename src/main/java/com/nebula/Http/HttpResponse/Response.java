package com.nebula.Http.HttpResponse;

import com.nebula.Http.Constants.ContentType;
import com.nebula.Http.Constants.HttpStatus;
import com.nebula.Http.Constants.HttpVersion;

public class Response {
    
    private final String CRLF = "\r\n";

    private HttpVersion httpVersion;

    private HttpStatus statusCode;

    private ContentType contentType;

    private int contentLength;

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public void createHttpResponse() {
       
    }

    public String createDefaultHeaders() {
        String contentType = String.format("Content-Type: %s",ContentType.JSON.getContentType());
        String contentLength = "";
        if(getContentLength() > 0) {
            contentLength = String.format("Content-Length: %s",getContentLength());
        }
        String server = "Server: Nebula";
        String connection = "Connection: close";
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(contentType).append(CRLF);
        if(!contentLength.isBlank()) {
            responseBuilder.append(contentLength);
            responseBuilder.append(CRLF);
        }
        responseBuilder.append(server);
        responseBuilder.append(CRLF);
        responseBuilder.append(connection);
        responseBuilder.append(CRLF);
        responseBuilder.append(CRLF);
        return responseBuilder.toString();
    }

    public String createResponseLine() {
        String requestLine = String.format("%s %d %s \r\n",getHttpVersion().getVersion() , getStatusCode().getStatusCode() , getStatusCode().getMessage());
        return requestLine;
    }

}
