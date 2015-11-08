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
public class DrawPanel extends JPanel implements Runnable{
    
    Map<String,Car> neighborList ;
    Car myCar ;
    Thread thread ; 
   public DrawPanel(Map<String,Car> neighborList,Car myCar){
        super();
        this.neighborList = neighborList;
        this.myCar = myCar ;
        setPreferredSize(new Dimension(500, 500));
        thread = new Thread(this);
        thread.start();
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
        g2d.setColor(Color.red);
        g2d.fillOval(myCar.getPositionX(), 500 -myCar.getPositionY(), 10, 10);
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
