package lab5;

import java.net.*;
import java.util.Scanner;

public class EchoServer {
    public static void main(String[] args) throws Exception {

       //Step A: Create a socket to listen on port 5000
        try (DatagramSocket socket = new DatagramSocket(5000)) {
            byte[] buffer = new byte[1024];

            System.out.println("Quote proxy server is running on port 5000...");  
            
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
              
                // Use the socket to 'receive' the packet here
                System.out.println("waiting for a packet...");
                socket.receive(packet);
                
                 

                String webData = "";
                // Use the socket to 'send' the packet back to the client

                try { 
                     Scanner s = new Scanner(URI.create("http://api.quotable.io/random?tags=technology").toURL().openStream());
                    
                     webData = s.nextLine();
                     s.close();                     
                }catch (Exception e) {
                    System.out.println("Error fetching quote: " + e.getMessage());
            }
            byte[] responseData = webData.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                responseData, 
                responseData.length, 
                packet.getAddress(), 
                packet.getPort()
            );
            socket.send(responsePacket);
            System.out.println("Sent quote back to client: " + webData);
            }
        }
    }
}
