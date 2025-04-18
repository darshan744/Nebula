package io.github.darshan744.nebula.Http.HttpRequest;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.darshan744.nebula.Http.Constants.HttpMethod;
import io.github.darshan744.nebula.Http.Constants.HttpStatus;
import io.github.darshan744.nebula.Http.Constants.HttpVersion;
import io.github.darshan744.nebula.Http.HttpRequest.Exceptions.BodyParsingException;
import io.github.darshan744.nebula.Http.HttpRequest.Parser.NebulaJsonParser;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;

/**
 * REQUEST BODY FOR HTTP
 * Contains HTTP Method HTTP Version  Route Body  Headers (As map) 
 */
public class Request {
   
    private final NebulaJsonParser jsonParser = new NebulaJsonParser();
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(Request.class);
    
    private String body;
    private HttpMethod method;
    private HttpStatus status;
    private String url;
    private HttpVersion httpVersion;
    private HashMap<String , String> headers;
    private HashMap<String ,String> params;
    private HashMap<String , String> queryParams;
    
    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
    
    public void setQueryParams(HashMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }
    /**
     * Returns the param stored from the URL
     */
    public String getParam(String key) {
        return params.get(key);
    }

    /**
     * Returns the query Parameter
     */
    public String getQueryParam(String key) {
        return queryParams.get(key);
    }

    public Request() {
        params = new HashMap<>();
        queryParams = new HashMap<>();
        this.headers = new LinkedHashMap<>();
    }
    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public  void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void putInHeaders(String key , String value) {
        if(key.isEmpty() || value.isEmpty()) {
            return;
        }
        this.headers.put(key.toLowerCase() , value);
    }

    public String getBody() {
        return body;
    }

    /**
     *  param clazz mapp object to that class
     *  return either the parsed object or null if error occured;
     */
    public <T> T getBody(Class<T> clazz) {
        try {
            return jsonParser.getObjectOfTheBody(getBody(), clazz);
        } catch (JsonProcessingException e) {
            throw new BodyParsingException("Failed To parse Request Body with CLASS: "+clazz.getSimpleName() , e);
        }
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader(String header) {
        return this.headers.get(header.toLowerCase());
    }
    
    @Override
    public String toString() {
        return "Request{" +
                "\nmethod=" + method +
                "\nstatus=" + status +
                "\nurl='" + url + '\'' +
                "\nhttpVersion=" + httpVersion +
                "\nheaders=" + headers +
                '}';
    }
    
}
