package es.udc.redes.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class WebServer {

    public static void main(String argv[]) {

        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.TcpServer <port>");
            System.exit(-1);
        }

        int serverPort = Integer.parseInt(argv[0]);
        ServerSocket serverSocket=null;
        Socket socket = null;

        try {
            serverSocket= new ServerSocket(serverPort);
            serverSocket.setSoTimeout(300000);

            while (true) {
                socket = serverSocket.accept();
                ServerThread sThread = new ServerThread(socket);
                sThread.start();
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally{

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
