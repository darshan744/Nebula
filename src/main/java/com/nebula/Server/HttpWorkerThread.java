package com.nebula.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpWorkerThread extends Thread{
    
    private Socket socket = null;
    HttpWorkerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
       final String CRLF = "\n\r";
        try {
            
            InputStream ioInputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            
            String html = "<html><head><title>Java Server</title></head><body>Hi this is java server page written code</body></html>";
             
            String response = "HTTP/1.1 200 OK" + CRLF 
                + "Content-Length: "+html.getBytes().length + CRLF + CRLF + html + CRLF + CRLF;
            outputStream.write(response.getBytes());
            
            ioInputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            //TODO Handle IO Exception
            System.out.println(e.getLocalizedMessage());
        }
    }
}
