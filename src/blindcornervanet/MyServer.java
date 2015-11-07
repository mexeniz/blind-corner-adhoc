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
    InetAddress targetIP ;
    int port ;
    public MyServer(String targetIP,int port , String myIP){
        try{  
            this.targetIP = InetAddress.getByName(targetIP) ;
            this.myIP = InetAddress.getByName(myIP); 
            this.port = port ;
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
              
              if(!BlindCornerVANET.myCar.carID.equals(message.split(" ")[0] ) && !myIP.toString().equals(IPAddress.toString()))
              {
                  //Message is not mine!
                  System.out.println ("Receive Beacon From: " + IPAddress + ":" + port + " Message: " + message);
                  boolean mustReb = BlindCornerVANET.validateMessage(IPAddress.toString(),message);
                  if(mustReb){
                    String rebMessage = message ;
                    sendData = rebMessage.getBytes();
                    System.out.println ("Rebroadcast Beacon " + sendData.length + " bytes Message: " + rebMessage);
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, targetIP, this.port); 
                    serverSocket.send(sendPacket);
                  }
              }
            }
        } catch (Exception e) {
            System.out.println(e + " at "+ serverSocket.getPort());
            System.exit(1);
        }
    }        
}
