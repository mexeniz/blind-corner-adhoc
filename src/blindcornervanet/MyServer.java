/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blindcornervanet;
import java.io.*;  
import java.net.*;  
/**
 *
 * @author munix
 */
public class MyServer extends Thread {
    InetAddress myIP ;
    DatagramSocket serverSocket ; 
    byte[] receiveData = new byte[1024]; 
    byte[] sendData  = new byte[1024];
    public MyServer(int port , String myIP){
        try{  
            this.myIP = InetAddress.getByName(myIP); 
            serverSocket = new DatagramSocket(port); 
        }catch(Exception e){
            System.out.println(e);
        }  
    }
    @Override
    public void run(){
        try {
            while(true) 
            { 

              receiveData = new byte[1024]; 

              DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 

              serverSocket.receive(receivePacket); 
             
              String message = new String(receivePacket.getData()); 

              InetAddress IPAddress = receivePacket.getAddress(); 

              int port = receivePacket.getPort(); 
              
              if(!myIP.toString().equals(IPAddress.toString()))
              {
                  System.out.println ("Receive Beacon From: " + IPAddress + ":" + port + " Message: " + message);
                  BlindCornerVANET.updateNeighborPosition(IPAddress.toString(),message);
              }
            }
        } catch (Exception e) {
            System.out.println(e + " at "+ serverSocket.getPort());
            System.exit(1);
        }
    }        
}
