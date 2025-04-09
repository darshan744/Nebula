package io.github.darshan744.nebula.Http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.darshan744.nebula.Http.Constants.ContentType;
import io.github.darshan744.nebula.Http.Constants.HttpStatus;
import io.github.darshan744.nebula.Http.Constants.HttpVersion;
import io.github.darshan744.nebula.Http.HttpRequest.Parser.NebulaJsonParser;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;

/**
 * <ul>
 * <li>This is the final response Body
 * <li>The building and managing of the response body is done using Response Class
 * <li>The dev can also use this class if they wish to have a fine grained control
 * <li>Then its the Dev's duty to handle the headers as whole
 * </ul>
 */
public final class Response {

    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(getClass());
    private final String CRLF = "\r\n";
    private HttpStatus status;
    private HashMap<String, String> headers = new HashMap<>();
    private Object contentBody = null;
    private String serializedBody = null;
    private final NebulaJsonParser jsonParser = new NebulaJsonParser();

    public Response(){}
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public Object getContentBody() {
        return contentBody;
    }

    int setContentBody(Object contentBody) throws JsonProcessingException {
        serializedBody = jsonParser.serializeObject(contentBody);
        this.contentBody = contentBody;
        return serializedBody.getBytes(StandardCharsets.US_ASCII).length;
    }

    public HttpStatus getStatus() {
        return status;
    }

    void setStatus(HttpStatus status) {
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
    

    public Response setStatusCode(HttpStatus status) {
        setStatus(status);
        return this;
    }

    public Response addHeader(Headers header, String value) {
        addHeader(header.getHeader(), value);
        return this;
    }

    public Response addHeader(String header, String value) {
        getHeaders().put(header, value);
        return this;
    }

    public Response addContentType(ContentType contentType) {
        HashMap<String, String> headers = getHeaders();
        headers.put(Headers.CONTENT_TYPE.getHeader(), contentType.getContentType());
        return this;
    }

    public Response addBody(Object body){
        try {
           int contentLength = setContentBody(body);
           addHeader(Headers.CONTENT_LENGTH, String.valueOf(contentLength));
        } catch (JsonProcessingException e) {
            logger.severe(e.getMessage());
        }
        return this;
    }

    
    private void addDefaultHeaders() {
        addHeader(Headers.CONTENT_TYPE, ContentType.JSON.getContentType());
        addHeader(Headers.SERVER, "Nebula/0.1");
        addHeader(Headers.CONNECTION, "close");
    }

    public Response ok() {
        return setStatusCode(HttpStatus.OK);
    }

    public Response notFound() {
        return setStatusCode(HttpStatus.NOT_FOUND);
    }

    public Response serverError() {
        return setStatusCode(HttpStatus.SERVER_ERROR);
    }
}
