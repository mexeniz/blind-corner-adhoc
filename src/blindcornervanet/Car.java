/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blindcornervanet;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Car {
    protected String name ;
    protected String carID ;
    protected double positionX ;
    protected double positionY ;
    protected int seqNumber;
    
    public Car(String name , String carID ,int seqNumber){
        this.name = name ;
        this.carID = carID ;
        this.positionX = 0.0 ;
        this.positionY = 0.0 ;
        this.seqNumber = 0 ;
    }
    public Car(String name , String carID , double positionX , double positionY , int seqNumber){
        this.name = name ;
        this.carID = carID ;
        this.positionX = positionX  ;
        this.positionY = positionY ;
        this.seqNumber = seqNumber ;
    }
    public boolean isNewer(int seqNumber){
        return seqNumber > this.seqNumber ;
    }
    public void updatePosition(double positionX , double positionY,int newSeqNumber){
        this.positionX = positionX;
        this.positionY = positionY;
        this.seqNumber = newSeqNumber ;
    }
    public String sendBeacon(){
        String message = carID+" "+name+" "+seqNumber+" "+positionX+" "+positionY;
        seqNumber++;
        return message ;
    }
}
