package com.nebula;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Main {
    public static void main(String[] args) {
        final String CRLF = "\n\r";
        try {
            System.out.println("Server running");
            ServerSocket serverSocket = new ServerSocket(7000);
            Socket socket = serverSocket.accept();
            System.out.print("Server running in");
            System.out.println(socket.getPort());
            InputStream ioInputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            
            String html = "<html><head><title>Java Server</title></head><body>Hi this is java server page written code</body></html>";
             
            String response = "HTTP/1.1 200 OK" + CRLF 
                + "Content-Length: "+html.getBytes().length + CRLF + CRLF + html + CRLF + CRLF;
            outputStream.write(response.getBytes());
            
            ioInputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}