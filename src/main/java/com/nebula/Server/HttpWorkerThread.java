package com.nebula.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpRequest.Parser.HttpParser;

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
            
            ioInputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            HttpParser parser = new HttpParser();
            Request req = parser.parseHttpRequest(ioInputStream);
            
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
