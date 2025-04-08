package com.github.darshan744.nebula.Http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.darshan744.nebula.Http.Constants.HttpStatus;
import com.github.darshan744.nebula.Http.Constants.HttpVersion;
import com.github.darshan744.nebula.Http.HttpRequest.Parser.NebulaJsonParser;

/**
 * <ul>
 * <li>This is the final response Body
 * <li>The building and managing of the response body is done using HttpResponseBuilder Class
 * <li>The dev can also use this class if they wish to have a fine grained control
 * <li>Then its the Dev's duty to handle the headers as whole
 * </ul>
 */
public final class Response {

    private final String CRLF = "\r\n";
    private HttpStatus status;
    private HashMap<String, String> headers = new HashMap<>();
    private Object contentBody = null;
    private String serializedBody = null;
    private final NebulaJsonParser jsonParser = new NebulaJsonParser();

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public Object getContentBody() {
        return contentBody;
    }

    public int setContentBody(Object contentBody) throws JsonProcessingException {
        serializedBody = jsonParser.serializeObject(contentBody);
        this.contentBody = contentBody;
        return serializedBody.getBytes(StandardCharsets.US_ASCII).length;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public byte[] getBytes() throws JsonProcessingException {
       
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
        StringBuilder response = new StringBuilder();
        response.append(statusLine).append(headers);
        if(serializedBody != null) {
           response.append(serializedBody);
        }
        response.append(CRLF);
        return response.toString().getBytes(StandardCharsets.US_ASCII);
    }
}
