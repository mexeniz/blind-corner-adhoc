package SFX;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author munix
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.easyogg.OggClip;
public class notiSound {
    
    private static void playSound(String file) {
        try {
            OggClip ogg = new OggClip(file);
            ogg.play();
//            ogg.loop();
//            ogg.setBalance(-1.0f);
//            ogg.pause();
//            ogg.resume();
        } catch (IOException ex) {
            Logger.getLogger(notiSound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void playNoti1(){
        playSound("SFX/alert1.ogg");
    }
    public static void playNoti2(){
        playSound("SFX/alert2.ogg");
    }
    public static void playStartup(){
        playSound("SFX/Carme.ogg");
    }
    public static void playVANET(){
        playSound("SFX/startup2.ogg");
    }
}
