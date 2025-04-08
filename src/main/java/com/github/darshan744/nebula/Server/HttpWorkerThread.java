package com.github.darshan744.nebula.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            RequestDispatcher dispatcher = new RequestDispatcher();
            //converts stream to Request Object and handle the registered url
            Response res = dispatcher.handleRequest(ioInputStream);
            // to send our recieved resposne
            outputStream.write(res.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
