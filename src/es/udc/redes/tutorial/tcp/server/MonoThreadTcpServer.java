package es.udc.redes.tutorial.tcp.server;

import java.net.*;
import java.io.*;

/**
 * MonoThread TCP echo server.
 */
public class MonoThreadTcpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.MonoThreadTcpServer <port>");
            System.exit(-1);
        }

        int serverPort = Integer.parseInt(argv[0]);
        ServerSocket serverSocket=null;
        Socket socket = null;
        BufferedReader sInput = null;
        PrintWriter sOutput = null;

        try {
            // Create a server socket
            serverSocket= new ServerSocket(serverPort);
            // Set a timeout of 300 secs
            serverSocket.setSoTimeout(300000);

            while (true) {
                // Wait for connections
                socket = serverSocket.accept();

                // Set the input channel
                sInput = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                
                // Set the output channel
                sOutput = new PrintWriter(socket.getOutputStream(), true);


                // Receive the client message
                String message = sInput.readLine();
                System.out.println("SERVER: Received " + message
                        + " from " + socket.getInetAddress().toString()
                        + ":" + socket.getPort());
                
                // Send response to the client
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
            }
        } catch (SocketTimeoutException e) {
           System.err.println("Nothing received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
	        //Close the socket
            try{
                if (socket!=null){
                    socket.close();
                }
                if (serverSocket!=null){
                    serverSocket.close();
                }
            } catch (IOException e){
                throw new RuntimeException();
            }

        }
    }
}
