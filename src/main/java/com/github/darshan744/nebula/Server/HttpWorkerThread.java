package com.github.darshan744.nebula.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import com.github.darshan744.nebula.Http.Constants.ContentType;
import com.github.darshan744.nebula.Http.Constants.HttpStatus;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.github.darshan744.nebula.Http.HttpResponse.Response;
import com.github.darshan744.nebula.Route.RequestDispatcher;

public class HttpWorkerThread extends Thread{
    private Socket socket = null;
    HttpWorkerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
       InputStream ioInputStream = null;
       OutputStream outputStream = null;
        try {
            //input HTTP request as stream 
            ioInputStream = socket.getInputStream();
            //for writing our response 
            outputStream = socket.getOutputStream();
            // Request Handler
            try {
                RequestDispatcher dispatcher = new RequestDispatcher();
                //converts stream to Request Object and handle the registered url
                Response res = dispatcher.handleRequest(ioInputStream);
                // to send our recieved resposne
                outputStream.write(res.getBytes());
            } catch (Exception e) {
                var obj = new Object(){
                    public String errorCode = "SERVER_ERROR";
                    public String errorMessage = e.getMessage();
                    public String timeStamp = LocalDateTime.now().toString();
                };
                
                Response res = new HttpResponseBuilder().setStatusCode(HttpStatus.SERVER_ERROR).addContentType(ContentType.JSON).addBody(obj).build();
                outputStream.write(res.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(ioInputStream != null) {
                try {
                    ioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
