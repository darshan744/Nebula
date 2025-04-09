package io.github.darshan744.nebula.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;

public class ServerListener extends Thread {
    //custom logger (wrapper for JUL)
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(ServerListener.class);
    //listening port
    private int port;
    // listens to the incoming connections
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
                // waits for new incoming request
                socket = serverSocket.accept();
                //after incoming request we get a seperate socket for that request
                logger.info("Connection Recieved : " + socket.getLocalAddress());
                //using threads we can manage the new incoming socket
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
