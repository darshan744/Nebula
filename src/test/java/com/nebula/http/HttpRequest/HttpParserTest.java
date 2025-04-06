package com.nebula.http.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Http.Constants.HttpVersion;
import com.nebula.Http.HttpRequest.Exceptions.RequestLineParserException;
import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpRequest.Parser.HttpParser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HttpParserTest {
    private static HttpParser httpParser;
    
    @BeforeAll
    public static void beforeAll() {
        httpParser = new HttpParser();
    }
    /**
     * PARSING TEST FOR REQUEST LINE
     */
    @Test
    public void parseHttpRequestLine() {
       Request request = httpParser.parseHttpRequest(testCase());
       assertEquals(HttpMethod.GET , request.getMethod());
       assertEquals(HttpVersion.HTTP_V1 , request.getHttpVersion());
       assertNotNull(request.getUrl());
    }
    @Test
    public void parseHttpRequestLineWithBody() {
        Request request = httpParser.parseHttpRequest(testCaseWithBody());
        assertEquals(HttpMethod.POST , request.getMethod());
        assertEquals(HttpVersion.HTTP_V1 , request.getHttpVersion());
        assertNotNull(request.getUrl());
    }
    /**
     * BAD REQUEST TEST CASE
     */
    @Test
    public void parseHttRequestWithBadTest() {
       assertThrows(RequestLineParserException.class , ()->httpParser.parseHttpRequestLine(badTestCaseWithCRNoLF() , new Request()));
    }

    @Test
    public void parseHttpRequestWithBody() {
        Request request = httpParser.parseHttpRequest(testCaseWithActualBody());
        assertEquals(HttpVersion.HTTP_V1, request.getHttpVersion());
        assertEquals(HttpMethod.POST , request.getMethod());
        int contentLength = Integer.parseInt(request.getHeader("content-length"));
        assertEquals(contentLength, request.getBody().length());
        System.out.println(request.getBody());
    }

    static InputStream badTestCaseWithCRNoLF() {
        String request = "GET / HTTP/1.1\r";
        return new ByteArrayInputStream(request.getBytes());
    }

    static InputStream testCase() {
        String httpRequest = "GET / HTTP/1.1\r\n" +
                        "Host: localhost:7090\r\n" +
                        "Connection: keep-alive\r\n" +
                        "sec-ch-ua: \"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Brave\";v=\"134\"\r\n" + //
                        "sec-ch-ua-mobile: ?0\r\n" +
                        "sec-ch-ua-platform: \"Windows\"\r\n" +
                        "Upgrade-Insecure-Requests: 1\r\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36\r\n" + //
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8\r\n" + //
                        "Sec-GPC: 1\r\n" +
                        "Accept-Language: en-US,en;q=0.5\r\n" +
                        "Sec-Fetch-Site: none\r\n" +
                        "Sec-Fetch-Mode: navigate\r\n" +
                        "Sec-Fetch-User: ?1\r\n" +
                        "Sec-Fetch-Dest: document\r\n" +
                        "Content-Length: 5\r\n"+
                        "Accept-Encoding: gzip, deflate, br, zstd";

        return new ByteArrayInputStream(
            httpRequest.getBytes()
        );
    }

    static InputStream testCaseWithBody(){
        String request =
                "POST /api/users HTTP/1.1\r\n" +
                "Host: example.com\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: 48\r\n" +
                "Authorization: Bearer abc123xyz\r\n" +
                "Accept: application/json\r\n" +
                "\r\n" + "\r\n" +
                "{\r\n" +
                "\"name\":\"John Doe\",\r\n" +
                "\"email\":\"john@example.com\r\n" +
                "\"}";

        return new ByteArrayInputStream(request.getBytes());
    }

    static InputStream testCaseWithActualBody() {
        String str = "POST / HTTP/1.1\r\n" + //
                        "content-length: 24\r\n" + //
                        "accept-encoding: gzip, deflate, br\r\n" + //
                        "User-Agent: Thunder Client (https://www.thunderclient.com)\r\n" + //
                        "Accept: application/json\r\n" + //
                        "Content-Type: application/json\r\n" + //
                        "Host: localhost:7090\r\n" + //
                        "Connection: close\r\n" + //
                        "\r\n" + //
                        "{\r\n" + //
                        "  \"user\" : \"darshan\"\r\n" + //
                        "}";
        return new ByteArrayInputStream(str.getBytes());
    }

}
