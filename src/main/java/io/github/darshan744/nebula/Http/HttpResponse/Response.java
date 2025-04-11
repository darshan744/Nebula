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

public final class Response {
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(getClass());
    private final String CRLF = "\r\n";
    private HttpStatus status;
    private HashMap<String, String> headers = new HashMap<>();
    private Object contentBody = null;
    private String serializedBody = null;
    private final NebulaJsonParser jsonParser = new NebulaJsonParser();

    public Response(){
        ok();
        defaultHeaders();
    }
    /**
     * return HashMap of Headers
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public Object getContentBody() {
        return contentBody;
    }

    /**
     *  param contentBody - Object of the body
     *  return the number of bytes of the characters (Charset-US_ASCII)
     *  throws JsonProcessingException
     */
    private int setContentBody(Object contentBody) throws JsonProcessingException {
        serializedBody = jsonParser.serializeObject(contentBody);
        this.contentBody = contentBody;
        return serializedBody.getBytes(StandardCharsets.US_ASCII).length;
    }

    public Response setContentType(ContentType contentType) {
        HashMap<String, String> headers = getHeaders();
        headers.put(Headers.CONTENT_TYPE.getHeader(), contentType.getContentType());
        return this;
    }

    /**
     * HttpStatus Code setters and getters
     */
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Response setStatusCode(HttpStatus status) {
        setStatus(status);
        return this;
    }

    /**
     * Overloaded addHeader for ENUM as well as Custom headers
     *  param header
     *  param value
     *  return
     */
    public Response addHeader(Headers header, String value) {
        addHeader(header.getHeader(), value);
        return this;
    }

    public Response addHeader(String header, String value) {
        getHeaders().put(header, value);
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

    /**
     * Util method for default headers
     * Default headers that must be set in every response
     */
    private void defaultHeaders() {
        addHeader(Headers.CONTENT_TYPE, ContentType.JSON.getContentType());
        addHeader(Headers.SERVER, "Nebula/0.1");
        addHeader(Headers.CONNECTION, "close");
    }

    /**
     * return new Status Line
     * example HTTP/1.1 200 OK \r\n
     */
    private StringBuilder statusLineBuilder() {
       return new StringBuilder()
                .append(HttpVersion.V1.getVersion())
                .append(" ")
                .append(getStatus().getStatusCode())
                .append(" ")
                .append(getStatus().getMessage())
                .append(CRLF);
    }
    /**
     * Converts the whole Response Object to String and then convert it to Bytes
     * return byte[] of the whole response 
     * throws JsonProcessingException when converting object to json 
     */
    public byte[] getBytes() throws JsonProcessingException {
       
        StringBuilder statusLine = statusLineBuilder();
        
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

    /**
     * Sets body type to JSON
     * */
    public Response json() {
        setContentType(ContentType.JSON);
        return this;
    }

    public Response text() {
        setContentType(ContentType.HTML);
        return this;
    }
    /**
     * Helpers for setting status code
     *  return
     */
    public Response ok() {
        return setStatusCode(HttpStatus.OK);
    }

    public Response notFound() {
        return setStatusCode(HttpStatus.NOT_FOUND);
    }

    public Response serverError() {
        return setStatusCode(HttpStatus.SERVER_ERROR);
    }

    public Response unAuthorized() {
        setStatus(HttpStatus.UNAUTHORIZED);
        return this;
    }
    
}
