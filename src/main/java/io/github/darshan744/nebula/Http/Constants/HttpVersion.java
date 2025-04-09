package io.github.darshan744.nebula.Http.Constants;

public enum HttpVersion {
    
    V1("HTTP/1.1") , V2("HTTP/2");
    
    private final String version;
    
    HttpVersion(String version) {
        this.version = version;
    }
    public String getVersion() {
        return version;
    }

}
