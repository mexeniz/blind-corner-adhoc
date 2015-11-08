/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blindcornervanet;

import blindcornervanet.GUI.Registration;
import blindcornervanet.GUI.SimulationGUI;
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
    
    
    //protected static final Queue<String> rebQueue = new LinkedList();
    private static final Map<String,Car> neighborList = new HashMap() ;
    protected static Car myCar ;
    private static String myIP ;
    private static int port ;
    private static Registration reg ;
    private static SimulationGUI sim ;
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        //Initiation
        System.out.println("Start VANET App");
        reg = new Registration();
        reg.setVisible(true);
        SFX.notiSound.playStartup();
    }
    private static String getBroadcastAddress(String myIP){
        int i = myIP.lastIndexOf('.');
        String bAddress = myIP.substring(0,i+1)+"255";
        return bAddress;
    }
    public static void init(String setMyIP , int setPort , String setName){
        System.out.println("Initiate VANET \n >> Simple Flooding <<");
        
        // Initiate Network Parameter
        myIP = setMyIP;
        port = setPort;
        
        //Initiate myCar Parameter
        int i = myIP.lastIndexOf('.');
        String id = myIP.substring(i+1);
        
        myCar = new Car(setName ,id ,250,40, 0);
        
        startVANET();
    }
    protected static boolean validateMessage (String IPAddress , String message){
        //Extract Message
        String[] s = message.split(" ");
        String carID = s[0] ;
        String carName = s[1] ;
        int seqNumber = Integer.parseInt(s[2]) ;
        int positionX = Integer.parseInt(s[3].trim()) ;
        int positionY = Integer.parseInt(s[4].trim()) ;
        
        if(neighborList.containsKey(carID)){
            Car car = neighborList.get(carID);
            if (car.isNewer(seqNumber)){
                //Update position
                car.updatePosition(positionX, positionY,seqNumber);
                
                return true ;
            }
            else {
                //Detect older message!
                System.out.println("Drop older message " + seqNumber +" from "+IPAddress );
            }
        }else{
            //Found new car!
            SFX.notiSound.playNoti1();
            Car car = new Car(carName, carID, positionX, positionY, seqNumber);
            neighborList.put(carID, car);
            return true;
        }
        return false;
    }
    public static void startVANET(){
        // Processing Broadcast Address
        String targetIP = getBroadcastAddress(myIP);
        
        // Bring Client and Server UP!
        System.out.println("Initiate Server... Bind IP :" + myIP + " Port :" + port);
        MyServer myServer = new MyServer(targetIP,port , myIP);
        System.out.println("Initiate Client... IP :" + targetIP + " Port :" + port);
        MyClient myClient = new MyClient(targetIP, port);
        
        SFX.notiSound.playVANET();
        
        System.out.println("Start Client...");
        myClient.start();
        System.out.println("Start Server...");
        myServer.start();
        
        
        sim = new SimulationGUI(neighborList,myCar);
        sim.setVisible(false);
        switchToMap();
    }
    private static void switchToMap(){
        reg.setVisible(false);
        sim.setVisible(true);
    }
    protected static String sendBeacon(){
        return myCar.sendBeacon();
    }
}
