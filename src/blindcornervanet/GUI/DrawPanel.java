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
        //Get myCar Position
        int x = myCar.getPositionX() ;
        int y = myCar.getPositionY() ;
        int r1 = 15 ;
        int r2 = 10 ;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(-200, -200, 1000,1000);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0+250-x, -300-250+y, 200, 500);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(300+250-x, -300-250+y, 200, 500);
        //Down-right corner
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0+250-x, 300-250+y, 200, 500);
        //Down-right corner
        g2d.setColor(Color.GRAY);
        g2d.fillRect(300+250-x, 300-250+y, 200, 500);
        g2d.setColor(Color.blue);
        g2d.fillOval(250-r1/2, 250-r1/2, r1, r1);
        
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
            if(distance < 150.0){
                //alert.riseAlert();
                if(!value.isAlert()){
                    noti = true ;
                    value.riseAlert();
                }
                g2d.setColor(Color.red);
            }
                else{
                value.stopAlert();
                g2d.setColor(Color.orange);
            }
            if(noti)SFX.notiSound.playNoti2();
            g2d.fillOval(value.getPositionX()+250-x-r2/2, 500 -value.getPositionY()-250+y-r2/2, r2, r2);
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
