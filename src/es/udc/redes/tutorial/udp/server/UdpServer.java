package es.udc.redes.tutorial.udp.server;

import java.net.*;

/**
 * Implements a UDP echo server.
 */
public class UdpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.udp.server.UdpServer <port_number>");
            System.exit(-1);
        }

        int serverPort = Integer.parseInt(argv[0]);
        DatagramSocket dSocket = null;
        try {
            // Create a server socket
            dSocket = new DatagramSocket(serverPort);

            // Set maximum timeout to 300 secs
            dSocket.setSoTimeout(300000);
            
            byte[] packet = null;
            
            while (true) {
                packet = new byte[1024];

                // Prepare datagram for reception
                DatagramPacket recPacket = new DatagramPacket(packet,packet.length);
                // Receive the message
                dSocket.receive(recPacket);
                System.out.println("SERVER: Received "
                        + new String(recPacket.getData(), 0, recPacket.getLength()) + " from "
                        + recPacket.getAddress().toString() + ":"
                        + recPacket.getPort());

                // Prepare datagram to send response
                DatagramPacket ansPacket = new DatagramPacket(recPacket.getData(),recPacket.getLength(),recPacket.getAddress(),
                                                              recPacket.getPort());
                // Send response
                dSocket.send(ansPacket);
                System.out.println("SERVER: Sending "
                        + new String(ansPacket.getData(), 0, ansPacket.getLength()) + " to "
                        + ansPacket.getAddress().toString() + ":"
                        + ansPacket.getPort());
            }
          
        } catch (SocketTimeoutException e) {
            System.err.println("No requests received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the socket
            if (dSocket!=null){
                dSocket.close();
            }
        }
    }
}
