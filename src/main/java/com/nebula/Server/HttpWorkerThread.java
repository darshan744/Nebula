package com.nebula.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.nebula.Http.HttpResponse.Response;
import com.nebula.Logger.NebulaLogger;
import com.nebula.Logger.NebulaLoggerFactory;
import com.nebula.Route.RequestDispatcher;

public class HttpWorkerThread extends Thread{
    private NebulaLogger logger = NebulaLoggerFactory.getLogger(HttpWorkerThread.class);
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
            logger.info("Request Recieved");
            RequestDispatcher dispatcher = new RequestDispatcher();
            Response res = dispatcher.handleRequest(ioInputStream);
            logger.info("Response Sent");
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
