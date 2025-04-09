package io.github.darshan744.nebula.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import io.github.darshan744.nebula.Http.Constants.ContentType;
import io.github.darshan744.nebula.Http.Constants.HttpStatus;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Route.RequestDispatcher;

/**
 * This package manages the http server listening and spanning threads for each request.
 * The ServerListener.java will listen to the port indefinitely till the port is not boud or closed. 
 * For each loop it will wait in `serversocket.accept()` line till getting request.
 * After getting an request it will move on the next line for each request we are creating a thread for handling it.
 * The reason for `HttpWorkerThread.class` is that the serverSocket will listen to multiple request with `Queue` so when a CPU intensive task comes it will get stuck so we offload to a Thread for that request
 */
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
                byte[] bytes = res.getBytes();
                outputStream.write(bytes);
            } catch (Exception e) {
                var obj = new Object(){
                    public String errorCode = "SERVER_ERROR";
                    public String errorMessage = e.getMessage();
                    public String timeStamp = LocalDateTime.now().toString();
                };
                
                Response res = new Response().setStatusCode(HttpStatus.SERVER_ERROR).setContentType(ContentType.JSON).addBody(obj);
                outputStream.write(res.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
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
