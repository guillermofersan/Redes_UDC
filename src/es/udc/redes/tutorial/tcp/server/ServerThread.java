package es.udc.redes.tutorial.tcp.server;
import java.net.*;
import java.io.*;

/** Thread that processes an echo server connection. */

public class ServerThread extends Thread {

    private Socket socket;

    public ServerThread(Socket s) {
       socket = s;
    }

    public void run() {

        BufferedReader sInput = null;
        PrintWriter sOutput = null;

        try {
            // Set the input channel
            sInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Set the output channel
            sOutput = new PrintWriter(socket.getOutputStream(), true);

            // Receive the message from the client
            String message = sInput.readLine();
            System.out.println("SERVER: Received " + message
                    + " from " + socket.getInetAddress().toString()
                    + ":" + socket.getPort());

            // Sent the echo message to the client
            sOutput.println(message);
            System.out.println("SERVER: Sending " + message +
                    " to " + socket.getInetAddress().toString() +
                    ":" + socket.getPort());

            // Close the streams
            if (sInput!=null){
                sInput.close();
            }
            if (sOutput!=null){
                sOutput.close();
            }

        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
              System.err.println("Error: " + e.getMessage());
            } finally {
  	            // Close the socket
                try{
                    if (socket!=null){
                       socket.close();
                    }
                } catch (IOException e){
                    throw new RuntimeException();
                }
        }
    }
}
