package com.nebula.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.nebula.Logger.NebulaLogger;
import com.nebula.Logger.NebulaLoggerFactory;

public class ServerListener extends Thread {
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(ServerListener.class);
    private int port;
    private ServerSocket serverSocket = null;
   public ServerListener(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        logger.info("Server Started and Listening in Port " + port);
    }

    @Override
    public void run() {
        Socket socket = null;
        while(serverSocket.isBound() && !serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
                logger.info("Connection Recieved : " + socket.getLocalAddress());
                HttpWorkerThread httpWorkerThread = new HttpWorkerThread(socket);
                httpWorkerThread.start();
            } catch (IOException e) {
                logger.severe(e.getMessage());
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
