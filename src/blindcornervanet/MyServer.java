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
    String myIP ;
    DatagramSocket serverSocket ; 
    byte[] receiveData = new byte[1024]; 
    byte[] sendData  = new byte[1024];
    public MyServer(int port , String myIP){
        try{  
            this.myIP = myIP ;
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
             
              String sentence = new String(receivePacket.getData()); 

              InetAddress IPAddress = receivePacket.getAddress(); 

              int port = receivePacket.getPort(); 

              if(!myIP.equals(IPAddress))
              {
                  System.out.println ("Receive Beacon From: " + IPAddress + ":" + port + " Message: " + sentence);
              }
            }
        } catch (Exception e) {
            System.out.println(e + " at "+ serverSocket.getPort());
            System.exit(1);
        }
    }        
}
