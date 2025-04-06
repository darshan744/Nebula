package com.nebula.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.nebula.Http.Parser.HttpParser;
import com.nebula.Http.Request;

public class HttpWorkerThread extends Thread{
    
    private Socket socket = null;
    HttpWorkerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
       final String CRLF = "\n\r";
       InputStream ioInputStream = null;
       OutputStream outputStream = null;
        try {
            
            ioInputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            HttpParser parser = new HttpParser();
           Request request = parser.parseHttpRequest(ioInputStream);

            String html = "<html><head><title>Java Server</title></head><body>Hi this is java server page written code</body></html>";
             
            String response = "HTTP/1.1 200 OK" + CRLF
                + "Content-Length: "+ html.getBytes().length + CRLF + CRLF + html + CRLF + CRLF;
            outputStream.write(response.getBytes());
            
            System.out.println("Response sent");
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
