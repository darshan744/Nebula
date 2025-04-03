package com.nebula.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {

    private int port;
    private ServerSocket serverSocket = null;
   public ServerListener(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while(serverSocket.isBound() && !serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println(" Connection accepted" + serverSocket.getInetAddress());

                HttpWorkerThread httpWorkerThread = new HttpWorkerThread(socket);
                httpWorkerThread.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
