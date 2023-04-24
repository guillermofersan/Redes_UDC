package es.udc.redes.tutorial.tcp.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

/** Multithread TCP echo server. */

public class TcpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.TcpServer <port>");
            System.exit(-1);
        }

        int serverPort = Integer.parseInt(argv[0]);
        ServerSocket serverSocket=null;
        Socket socket = null;


        try {
            // Create a server socket
            serverSocket= new ServerSocket(serverPort);
            // Set a timeout of 300 secs
            serverSocket.setSoTimeout(300000);

            while (true) {
              // Wait for connections
              socket = serverSocket.accept();

              // Create a ServerThread object, with the new connection as parameter
              ServerThread sThread = new ServerThread(socket);

              // Initiate thread using the start() method
              sThread.start();
          }
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally{
  	        //Close the socket
            try{
                if (serverSocket!=null){
                   serverSocket.close();
                }
            } catch (IOException e){
                throw new RuntimeException();
            }
        }
    }
}
