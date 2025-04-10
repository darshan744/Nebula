package com.github.darshan744.nebula.Route;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.darshan744.nebula.Route.RouteMatcher;

public class RouteMatcherTest {

    static RouteMatcher matcher;

    @BeforeAll
    static void BeforeAll() {
        matcher = new RouteMatcher();
    }

    @Test
    void splitCheck() {
        boolean pathTest1 = matcher.match("/users/EMP123/", "/users/{id}/"); // true
        boolean pathTest2 = matcher.match("/users/EMP123", "/users/{id}/"); // true (trailing slash optional?)
        boolean pathTest3 = matcher.match("/users/EMP123/books/45", "/users/{id}/books/{bookId}"); // true
        boolean pathTest4 = matcher.match("/users//books/45", "/users/{id}/books/{bookId}"); // false (empty id)
        boolean pathTest5 = matcher.match("/users/EMP123/books", "/users/{id}/books/{bookId}"); // false (missing
                                                                                                // bookId)
        boolean pathTest6 = matcher.match("/users/EMP123/books/45/reviews", "/users/{id}/books/{bookId}/reviews"); // true
        boolean pathTest7 = matcher.match("/users/EMP123/books/45/reviews", "/users/{id}/books/{bookId}"); // false
                                                                                                           // (extra
                                                                                                           // segment)
        boolean pathTest8 = matcher.match("/products/99", "/products/{pid}"); // true
        boolean pathTest9 = matcher.match("/products/", "/products/{pid}"); // false (missing pid)
        boolean pathTest10 = matcher.match("/products", "/products/{pid}"); // false
        boolean pathTest11 = matcher.match("/", "/"); // true
        boolean pathTest12 = matcher.match("/anything/goes/here", "/{a}/{b}/{c}"); // true
        boolean pathTest13 = matcher.match("/one/two", "/{first}/{second}/{third}"); // false (missing segment)
        boolean pathTest14 = matcher.match("/users/123/profile", "/users/{id}/profile"); // true
        boolean pathTest15 = matcher.match("/users/123/profile", "/users/{id}"); // false (extra segment)

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
        Map<String, String> paramsTest1 = matcher.extractParams("/users/EMP123/", "/users/{id}/");
        assertEquals(Map.of("id", "EMP123"), paramsTest1);

        Map<String, String> paramsTest2 = matcher.extractParams("/users/EMP123/books/456",
                "/users/{id}/books/{bookId}");
        assertEquals(Map.of("id", "EMP123", "bookId", "456"), paramsTest2);

        Map<String, String> paramsTest3 = matcher.extractParams("/files/docs/report.pdf", "/files/{folder}/{filename}");
        System.out.println(paramsTest3);
        assertEquals(Map.of("folder", "docs", "filename", "report.pdf"), paramsTest3);

        Map<String, String> paramsTest4 = matcher.extractParams("/products/99/reviews/5/",
                "/products/{pid}/reviews/{rid}/");
        System.out.println(paramsTest4);
        assertEquals(Map.of("pid", "99", "rid", "5"), paramsTest4);

        Map<String, String> paramsTest5 = matcher.extractParams("/a/1/b/2/c/3", "/a/{x}/b/{y}/c/{z}");
        System.out.println(paramsTest5);
        assertEquals(Map.of("x", "1", "y", "2", "z", "3"), paramsTest5);

        Map<String, String> paramsTest6 = matcher.extractParams("/only/alpha", "/only/{one}");
        System.out.println(paramsTest6);
        assertEquals(Map.of("one", "alpha"), paramsTest6);

        Map<String, String> paramsTest7 = matcher.extractParams("/foo/bar", "/{first}/{second}");
        System.out.println(paramsTest7);
        assertEquals(Map.of("first", "foo", "second", "bar"), paramsTest7);
    }

    @Test
    void testQueryParams() {
        Map<String, String> q1 = matcher.extractQueryParams("/users?id=EMP123");
        assertEquals(Map.of("id", "EMP123"), q1);

        Map<String, String> q2 = matcher.extractQueryParams("/search?q=java&limit=10");
        assertEquals(Map.of("q", "java", "limit", "10"), q2);

        Map<String, String> q3 = matcher.extractQueryParams("/filter?sort=asc&category=books&type=pdf");
        assertEquals(Map.of("sort", "asc", "category", "books", "type", "pdf"), q3);

        Map<String, String> q4 = matcher.extractQueryParams("/products/99/reviews?user=alex");
        assertEquals(Map.of("user", "alex"), q4);

        Map<String, String> q5 = matcher.extractQueryParams("/path/with/no/query");
        assertEquals(Map.of(), q5); // no query string

        Map<String, String> q6 = matcher.extractQueryParams("/example?keyOnly");
        assertEquals(Map.of("keyOnly", ""), q6); // key with no value

        Map<String, String> q7 = matcher.extractQueryParams("/multi?tag=java&tag=web");
        assertEquals(Map.of("tag", "web"), q7); // if overwriting repeated keys (last one wins)

        Map<String, String> q8 = matcher.extractQueryParams("/encoded?name=John%20Doe&city=New%20York");
        assertEquals(Map.of("name", "John Doe", "city", "New York"), q8); // with URL decoding

        Map<String, String> q9 = matcher.extractQueryParams("/empty?x=&y=1");
        assertEquals(Map.of("x", "", "y", "1"), q9); // x is empty string

        Map<String, String> q10 = matcher.extractQueryParams("/mix?abc=123&flag&x=y");
        assertEquals(Map.of("abc", "123", "flag", "", "x", "y"), q10); // key with no value

    }
}
