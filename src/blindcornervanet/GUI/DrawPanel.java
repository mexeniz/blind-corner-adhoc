/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blindcornervanet.GUI;

import blindcornervanet.Car;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author ASUS
 */
class Alert extends Thread{
    private boolean isAlert ;
    private int alarmInterval = 300;
    public Alert(){
        isAlert = false ;
    }
    
    protected void riseAlert(){
        isAlert = true ;
    }
    @Override
    public void run(){
        while(true){
            try {
                if (isAlert){
                    SFX.notiSound.playNoti2();
                }
                isAlert = false ;    
                this.sleep(alarmInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(Alert.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
public class DrawPanel extends JPanel implements Runnable{
    
    Map<String,Car> neighborList ;
    Car myCar ;
    Thread thread ; 
    Alert alert ;
   public DrawPanel(Map<String,Car> neighborList,Car myCar){
        super();
        this.neighborList = neighborList;
        this.myCar = myCar ;
        setPreferredSize(new Dimension(500, 500));
        thread = new Thread(this);
        thread.start();
        Thread t = new Thread();
        
        Alert alert = new Alert();
        alert.start();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 500,500);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, 200, 200);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(300, 0, 200, 200);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 300, 200, 200);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(300, 300, 200, 200);
        g2d.setColor(Color.blue);
        g2d.fillOval(myCar.getPositionX(), 500 -myCar.getPositionY(), 10, 10);
        
        //Draw enemy car :)
        neighborList.entrySet().stream().map((entrySet) -> {
            String key = entrySet.getKey();
            return entrySet;
        }).forEach((entrySet) -> {
            Car value = entrySet.getValue();
            //value.printInfo();
            int dx = myCar.getPositionX() - value.getPositionX();
            int dy = myCar.getPositionY() - value.getPositionY();
            double distance = Math.sqrt(dx*dx + dy*dy);
            boolean noti = false;
            if(distance < 80.0){
                //alert.riseAlert();
                noti = true ;
                g2d.setColor(Color.red);
            }
                else{
                g2d.setColor(Color.orange);
            }
            if(noti)SFX.notiSound.playNoti2();
            g2d.fillOval(value.getPositionX(), 500 -value.getPositionY(), 10, 10);
        });
    }
    @Override
    public void run() {
        while(true){
            try {
                repaint();
                thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
