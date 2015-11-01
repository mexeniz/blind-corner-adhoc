/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blindcornervanet;
import java.io.*;  
import java.net.*;  
import java.util.Date;
/**
 *
 * @author munix
 */
public class MyClient extends Thread {
    String serverHostname ;
    BufferedReader inFromUser ;
    DatagramSocket clientSocket ;
    int targetPort ;
    InetAddress IPAddress ;
    

    byte[] sendData = new byte[1024]; 
    byte[] receiveData = new byte[1024];
    public MyClient(String ip , int port){
        try{      
            String serverHostname = new String (ip);
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
            targetPort = port ;
            clientSocket = new DatagramSocket(); 
            IPAddress = InetAddress.getByName(serverHostname);
        }catch(Exception e){
            System.out.println(e);
        }  
    }
    public void run(){
        try {
            System.out.println ("Attemping to connect to " + IPAddress + ") via UDP port " + targetPort);
            while(true){
                //System.out.print("Enter Message: ");
                Date date = new Date();
                String sentence = date.toString();
                sendData = sentence.getBytes();         

                System.out.println ("Send Beacon " + sendData.length + " bytes Message: " + sentence);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, targetPort); 
                
                clientSocket.send(sendPacket);
                Thread.sleep(1000);
//
//                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
//
//                System.out.println ("Waiting for return packet");
//                clientSocket.setSoTimeout(10000);

//                try {
//                     clientSocket.receive(receivePacket); 
//                     String modifiedSentence = new String(receivePacket.getData()); 
//
//                     InetAddress returnIPAddress = receivePacket.getAddress();
//
//                     int port = receivePacket.getPort();
//
//                     System.out.println ("From server at: " + returnIPAddress + ":" + port);
//                     System.out.println("Message: " + modifiedSentence); 
//
//                    }
//                catch (SocketTimeoutException ste)
//                    {
//                     System.out.println ("Timeout Occurred: Packet assumed lost");
//                }

            }
        
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        
    }
}
