package com.nebula.Http.HttpResponse;

import java.util.HashMap;

import com.nebula.Http.Constants.HttpStatus;
import com.nebula.Http.Constants.HttpVersion;

/**
 * This is the final response Body
 * The building and managing of the response body is done using
 * HttpResponseBuilder Class
 * The dev can also use this class if they wish to have a fine grained control
 * Then its the Dev's duty to handle the headers as whole
 * 
 */
public final class Response {

    private final String CRLF = "\r\n";
    private HttpStatus status;
    private HashMap<String, String> headers = new HashMap<>();
    private Object contentBody = null;

    public String getCRLF() {
        return CRLF;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public Object getContentBody() {
        return contentBody;
    }

    public void setContentBody(Object contentBody) {
        this.contentBody = contentBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public byte[] getBytes() {
        StringBuilder statusLine = new StringBuilder();
        statusLine.append(HttpVersion.V1.getVersion())
                .append(" ")
                .append(getStatus().getStatusCode())
                .append(" ")
                .append(getStatus().getMessage())
                .append(CRLF);
        
        // Headers as a string 
        StringBuilder headers = new StringBuilder();
        getHeaders().forEach(
            (key , value) -> headers.append(key).append(": ").append(value).append(CRLF)
        );
        headers.append(CRLF);
        headers.append(CRLF);
        StringBuilder response = new StringBuilder();
        response.append(statusLine).append(headers);
        if(contentBody != null) {
            //TODO JSONify the contentBody 
            //and then add

            response.append(contentBody.toString());
        }
        return response.toString().getBytes();
    }
}
