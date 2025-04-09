# Explanation for com.nebula.Server

## Notes
This package manages the http server listening and spanning threads for each request.The ServerListener.java will listen to the port indefinitely till the port is not boud or closed. For each loop it will wait in `serversocket.accept()` line till getting request. After getting an request it will move on the next line for each request we are creating a thread for handling it.The reason for `HttpWorkerThread.class` is that the serverSocket will listen to multiple request with `Queue` so when a CPU intensive task comes it will get stuck so we offload to a Thread for that request
