package com.nebula.Http.Constants;

public enum HttpVersion {
    
    HTTP_V1("HTTP/1.1") , HTTP_V2("HTTP/2");
    
    private final String version;
    
    HttpVersion(String version) {
        this.version = version;
    }
    public String getVersion() {
        return version;
    }

}
