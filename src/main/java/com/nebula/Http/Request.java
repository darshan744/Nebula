package com.nebula.Http;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Http.Constants.HttpStatus;
import com.nebula.Http.Constants.HttpVersion;

/**
 * @apiNote
 * REQUEST BODY FOR HTTP
 *
 * ->Contains -> HTTP Method , HTTP Version , Route , Body , Headers (As map) 
 */
public class Request {


   
    //TODO : Implement for Request Body;
    private String body;
    private HttpMethod method;
    private HttpStatus status;
    private String url;
    private HttpVersion httpVersion;
    private HashMap<String , String> headers;

    
    public Request() {
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
