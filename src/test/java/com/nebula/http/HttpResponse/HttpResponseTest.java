package com.nebula.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void sampleHeaders() {
        
    }
    @Test
    public void responseBody() {
       
    }
}
