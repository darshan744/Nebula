package io.github.darshan744.nebula.Route;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RouteMatcher {

    /**
     * path - request path ex : users/EMP123
     * pattern - Defined by developer  ex : users/{id}
     * return if the path matches or not ?
     */
    public boolean match(String path , String pattern) {
        // splits users/EMP123 to [users , EMP123] 
        // replaceAll is for handling leading and trailing slashes
        String[] pathParts = splitPath(path);
        String[] patternParts = splitPath(pattern);

        // if no same division is found then its not that route
        if(pathParts.length != patternParts.length) return false;
        
        System.out.println(Arrays.toString(pathParts) + " Length " + pathParts.length);
        System.out.println(Arrays.toString(patternParts) + " Length " + patternParts.length);
        for(int i = 0 ; i < pathParts.length;i++) {
            String pathSegment = pathParts[i];
            String patternSegment = patternParts[i];

            boolean match = patternSegment.equals(pathSegment) || patternSegment.startsWith("{")&& !pathSegment.isEmpty();
            if(!match)return false;
        }

        return true;
    }

    /**
     * Returns a Map of key and value pair
     * Ex : {id=123} for route /user/{id} -> /user/123
     */
    public Map<String , String> extractParams(String path , String pathPattern) {
        Map<String , String> params = new HashMap<>();

        String[] pathParts = splitPath(path);
        String[] patternParts = splitPath(pathPattern);

        System.out.println(Arrays.toString(pathParts));
        System.out.println(Arrays.toString(patternParts));

        for(int i = 0 ; i < pathParts.length;i++) {
            String pathPart = pathParts[i];
            String patternPart = patternParts[i];

            if(patternPart.startsWith("{")) {
                patternPart = patternPart.replaceAll("[{}]","");
                params.put(patternPart , pathPart);
            }
        }
        return params;
    }
    public String[] splitPath(String path) {
        return path.replaceAll("^/|/$", "").split("/");
    }

    /**
     * Parses the incoming route
     * Returns map of query params 
     * Ex : /user?id=123 -> map : {id=123} 
     */
    public Map<String ,String> extractQueryParams(String routePath) {
        HashMap<String ,String> queryHashMap = new HashMap<>();
        // to get string after mark
        int idx = routePath.indexOf('?');
        // if no q'mark no query params
        if(idx == -1)return queryHashMap;
        String paramsAfterMark = routePath.substring(idx + 1);
        String[] params = paramsAfterMark.split("&");
        for(String param : params) {
            String[] keyValue = param.split("=" , 2);
            //decode incase of %20
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            if(keyValue.length < 2) {
                queryHashMap.put(key, "");
            }
            else {
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                queryHashMap.put(key, value);
            }
            
        }
        return queryHashMap;
    }

}
