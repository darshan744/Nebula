package com.github.darshan744.nebula.Route;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.darshan744.nebula.Route.RouteMatcher;

public class RouteMatcherTest {

    static RouteMatcher matcher;

    @Test
    void splitCheck() {
        boolean pathTest1 = RouteMatcher.match("/users/EMP123/", "/users/{id}/"); // true
        boolean pathTest2 = RouteMatcher.match("/users/EMP123", "/users/{id}/"); // true (trailing slash optional?)
        boolean pathTest3 = RouteMatcher.match("/users/EMP123/books/45", "/users/{id}/books/{bookId}"); // true
        boolean pathTest4 = RouteMatcher.match("/users//books/45", "/users/{id}/books/{bookId}"); // false (empty id)
        boolean pathTest5 = RouteMatcher.match("/users/EMP123/books", "/users/{id}/books/{bookId}"); // false (missing
                                                                                                     // bookId)
        boolean pathTest6 = RouteMatcher.match("/users/EMP123/books/45/reviews", "/users/{id}/books/{bookId}/reviews"); // true
        boolean pathTest7 = RouteMatcher.match("/users/EMP123/books/45/reviews", "/users/{id}/books/{bookId}"); // false
                                                                                                                // (extra
                                                                                                                // segment)
        boolean pathTest8 = RouteMatcher.match("/products/99", "/products/{pid}"); // true
        boolean pathTest9 = RouteMatcher.match("/products/", "/products/{pid}"); // false (missing pid)
        boolean pathTest10 = RouteMatcher.match("/products", "/products/{pid}"); // false
        boolean pathTest11 = RouteMatcher.match("/", "/"); // true
        boolean pathTest12 = RouteMatcher.match("/anything/goes/here", "/{a}/{b}/{c}"); // true
        boolean pathTest13 = RouteMatcher.match("/one/two", "/{first}/{second}/{third}"); // false (missing segment)
        boolean pathTest14 = RouteMatcher.match("/users/123/profile", "/users/{id}/profile"); // true
        boolean pathTest15 = RouteMatcher.match("/users/123/profile", "/users/{id}"); // false (extra segment)

        assertTrue(pathTest1, "Path 1 Failed");
        assertTrue(pathTest2, "Path 2 Failed");
        assertTrue(pathTest3, "Path 3 Failed");
        assertTrue(pathTest6, "Path 6 Failed");
        assertTrue(pathTest8, "Path 8 Failed");
        assertTrue(pathTest11, "Path 11 Failed");
        assertTrue(pathTest12, "Path 12 Failed");
        assertTrue(pathTest14, "Path 1 4Failed");
        assertFalse(pathTest4, "Path 4 Failed");
        assertFalse(pathTest5, "Path 5 Failed");
        assertFalse(pathTest7, "Path 7 Failed");
        assertFalse(pathTest10, "Path 10 Failed");
        assertFalse(pathTest9, "Path 9 Failed");
        assertFalse(pathTest13, "Path 13 Failed");
        assertFalse(pathTest15, "Path 15 Failed");
    }

    @Test
    void extractParams() {
        HashMap<String, String> paramsTest1 = RouteMatcher.extractParams("/users/EMP123/", "/users/{id}/");
        assertEquals(Map.of("id", "EMP123"), paramsTest1);

        HashMap<String, String> paramsTest2 = RouteMatcher.extractParams("/users/EMP123/books/456",
                "/users/{id}/books/{bookId}");
        assertEquals(Map.of("id", "EMP123", "bookId", "456"), paramsTest2);

        HashMap<String, String> paramsTest3 = RouteMatcher.extractParams("/files/docs/report.pdf",
                "/files/{folder}/{filename}");
        System.out.println(paramsTest3);
        assertEquals(Map.of("folder", "docs", "filename", "report.pdf"), paramsTest3);

        HashMap<String, String> paramsTest4 = RouteMatcher.extractParams("/products/99/reviews/5/",
                "/products/{pid}/reviews/{rid}/");
        System.out.println(paramsTest4);
        assertEquals(Map.of("pid", "99", "rid", "5"), paramsTest4);

        HashMap<String, String> paramsTest5 = RouteMatcher.extractParams("/a/1/b/2/c/3", "/a/{x}/b/{y}/c/{z}");
        System.out.println(paramsTest5);
        assertEquals(Map.of("x", "1", "y", "2", "z", "3"), paramsTest5);

        HashMap<String, String> paramsTest6 = RouteMatcher.extractParams("/only/alpha", "/only/{one}");
        System.out.println(paramsTest6);
        assertEquals(Map.of("one", "alpha"), paramsTest6);

        HashMap<String, String> paramsTest7 = RouteMatcher.extractParams("/foo/bar", "/{first}/{second}");
        System.out.println(paramsTest7);
        assertEquals(Map.of("first", "foo", "second", "bar"), paramsTest7);
    }

    @Test
    void testQueryParams() {
        Map<String, String> q1 = RouteMatcher.extractQueryParams("/users?id=EMP123");
        assertEquals(Map.of("id", "EMP123"), q1);

        Map<String, String> q2 = RouteMatcher.extractQueryParams("/search?q=java&limit=10");
        assertEquals(Map.of("q", "java", "limit", "10"), q2);

        Map<String, String> q3 = RouteMatcher.extractQueryParams("/filter?sort=asc&category=books&type=pdf");
        assertEquals(Map.of("sort", "asc", "category", "books", "type", "pdf"), q3);

        Map<String, String> q4 = RouteMatcher.extractQueryParams("/products/99/reviews?user=alex");
        assertEquals(Map.of("user", "alex"), q4);

        Map<String, String> q5 = RouteMatcher.extractQueryParams("/path/with/no/query");
        assertEquals(Map.of(), q5); // no query string

        Map<String, String> q6 = RouteMatcher.extractQueryParams("/example?keyOnly");
        assertEquals(Map.of("keyOnly", ""), q6); // key with no value

        Map<String, String> q7 = RouteMatcher.extractQueryParams("/multi?tag=java&tag=web");
        assertEquals(Map.of("tag", "web"), q7); // if overwriting repeated keys (last one wins)

        Map<String, String> q8 = RouteMatcher.extractQueryParams("/encoded?name=John%20Doe&city=New%20York");
        assertEquals(Map.of("name", "John Doe", "city", "New York"), q8); // with URL decoding

        Map<String, String> q9 = RouteMatcher.extractQueryParams("/empty?x=&y=1");
        assertEquals(Map.of("x", "", "y", "1"), q9); // x is empty string

        Map<String, String> q10 = RouteMatcher.extractQueryParams("/mix?abc=123&flag&x=y");
        assertEquals(Map.of("abc", "123", "flag", "", "x", "y"), q10); // key with no value
    }

    @Test
    void testBothPathParamAndQueryParam() {
        String pathPattern = "/users/{id}/";
        String actualPath = "/users/EMP123/";
        String fullUrl = "/users/EMP123/?active=true&sort=desc";

        Map<String, String> pathParams = RouteMatcher.extractParams(actualPath, pathPattern);
        Map<String, String> queryParams = RouteMatcher.extractQueryParams(fullUrl);

        assertEquals(Map.of("id", "EMP123"), pathParams);
        assertEquals(Map.of("active", "true", "sort", "desc"), queryParams);
    }

    @Test
    void testRouteMatchingWithPathParamAndQueryParam() {
        String routePath = "/users/{id}";
        String pathParam = "/users/123";
        String queryParam = "/users/123?id=456&name=Jhon";


        assertTrue(RouteMatcher.match(pathParam, routePath));
        assertTrue(RouteMatcher.match(queryParam, routePath));
    }
}
