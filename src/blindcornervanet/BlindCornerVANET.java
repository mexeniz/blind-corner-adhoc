/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blindcornervanet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author munix
 */
public class BlindCornerVANET {
    
    
    protected static final Queue<String> rebQueue = new LinkedList();
    private static final Map<String,Car> neighborList = new HashMap() ;
    private static Car myCar ;
    private static String myIP ;
    private static int port ;
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        //Initiation
        init() ;
        
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
    private static String getBroadcastAddress(String myIP){
        int i = myIP.lastIndexOf('.');
        String bAddress = myIP.substring(0,i+1)+"255";
        return bAddress;
    }
    private static void init(){
        System.out.println("Initiate VANET \n >> Simple Flooding <<");
        Scanner kb = new Scanner(System.in);
        
        // Initiate Network Parameter
        System.out.print("Insert my IP :");
        myIP = kb.nextLine();
        System.out.print("Insert Port :");
        port = kb.nextInt();
        
        //Initiate myCar Parameter
        System.out.print("Insert your name :");
        String name = kb.next() ;
        int i = myIP.lastIndexOf('.');
        String id = myIP.substring(i+1);
        
        myCar = new Car(name ,id , 0);
    }
    protected static void updateNeighborPosition (String IPAddress , String message){
        //Extract Message
        String[] s = message.split(" ");
        String carID = s[0] ;
        String carName = s[1] ;
        int seqNumber = Integer.parseInt(s[2]) ;
        double positionX = Double.parseDouble(s[3]) ;
        double positionY = Double.parseDouble(s[4]) ;
        
        if(neighborList.containsKey(carID)){
            Car car = neighborList.get(carID);
            if (car.isNewer(seqNumber)){
                //Update position
                car.updatePosition(positionX, positionY,seqNumber);
                //Push rebroadcastable message
                rebQueue.add(message);
            }
            else {
                //Detect older message!
                System.out.println("Drop older message " + seqNumber +" from "+IPAddress );
            }
        }else{
            //Found new car!
            Car car = new Car(carName, carID, positionX, positionY, seqNumber);
            neighborList.put(carID, car);
        }
    }
    protected static String sendBeacon(){
        return myCar.sendBeacon();
    }
}
