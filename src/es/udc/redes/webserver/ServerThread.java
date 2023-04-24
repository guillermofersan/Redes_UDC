package es.udc.redes.webserver;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Date;


public class ServerThread extends Thread {

    private Socket socket;

    public ServerThread(Socket s) {
        // Store the socket s
        this.socket = s;
    }

    public void run() {
        BufferedReader sInput = null;
        PrintWriter sOutput = null;

        BufferedOutputStream outStream = null;

        try {

            sInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sOutput = new PrintWriter(socket.getOutputStream(), true);
            outStream = new BufferedOutputStream(socket.getOutputStream());

            StringBuilder message = new StringBuilder();
            String line = sInput.readLine();

            while(line!=null && !line.isBlank()){
                message.append(line);
                message.append("\n");
                line=sInput.readLine();
            }

            if (!message.toString().isBlank()){

                String[] messages = message.toString().split("\n");
                String[] methodline = messages[0].split(" ");
                String   method =  methodline[0];
                String   path   =  methodline[1];

                Date if_mod_since = AuxFunctions.get_ifmodsince(messages);

                Method m;
                switch (method){
                    case "GET":  m = new GET(AuxFunctions.getpath(path),outStream,if_mod_since); break;
                    case "HEAD": m = new HEAD(AuxFunctions.getpath(path),outStream); break;
                    default:     m = new Method(AuxFunctions.getpath(path),outStream); break;
                }
                System.out.println(Arrays.toString(messages));
                m.method();
            }

            sInput.close();
            sOutput.close();
            //System.out.println(message);



        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Close the client socket
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
