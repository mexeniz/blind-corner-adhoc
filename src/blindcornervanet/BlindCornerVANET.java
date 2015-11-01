/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blindcornervanet;

import java.util.Scanner;

/**
 *
 * @author munix
 */
public class BlindCornerVANET {

    /**
     * @param args the command line arguments
     */
    public static String getBroadcastAddress(String myIP){
        String bAddress  = "";
        int i = myIP.lastIndexOf('.');
        bAddress = myIP.substring(0,i+1)+"255";
        return bAddress;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Initiate VANET \n >> Simple Flooding <<");
        Scanner kb = new Scanner(System.in);
        
        // Initiate Parameter
        System.out.print("Insert my IP :");
        String myIP = kb.nextLine();
        System.out.print("\nInsert Port :");
        int port = kb.nextInt();
        
        // Processing Broadcast Address
        String targetIP = getBroadcastAddress(myIP);
        
        // Bring Client and Server UP!
        System.out.println("Initiate Server... Bind IP :" + myIP + " Port :" + port);
        MyServer myServer = new MyServer(port , myIP);
        System.out.println("Initiate Client... IP :" + targetIP + " Port :" + port);
        MyClient myClient = new MyClient(targetIP, port);
        
        
        System.out.println("Start Client...");
        myClient.start();
        System.out.println("Start Server...");
        myServer.start();
    }
    
}
