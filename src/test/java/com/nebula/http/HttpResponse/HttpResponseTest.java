package com.nebula.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nebula.Http.Constants.ContentType;
import com.nebula.Http.Constants.HttpStatus;
import com.nebula.Http.Constants.HttpVersion;
import com.nebula.Http.HttpResponse.Response;

public class HttpResponseTest {

    private static Response response;

    @BeforeAll
    static void beforeAll() {
        response= new Response();
        
    }

    /**
     * Test for checking whether the http response string is 
     * valid or not 
     */
    @Test
    public void responseString() {
        response.setHttpVersion(HttpVersion.HTTP_V1);
        response.setStatusCode(HttpStatus.OK);
        
        String str = response.createResponseLine();
        String sampleResponse = "HTTP/1.1 200 OK \r\n";
        assertEquals(str, sampleResponse);
    }

    @Test
    public void sampleHeaders() {
        response.setContentType(ContentType.JSON);
        System.out.println(response.createDefaultHeaders());
    }
}
