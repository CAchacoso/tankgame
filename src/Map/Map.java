package Map;

import Immoveables.Breakable;
import Immoveables.PowerUps;
import Immoveables.Unbreakable;
import PowerUp.ExtraLife;
import PowerUp.SpeedHack;
import Tank.TRE;

import javax.naming.ldap.ExtendedRequest;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Tank.Tank;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Map {
    private int x;
    private int y;
    BufferedImage img;
    public static ArrayList<GameSetGet> object = new ArrayList<>();
    private BufferedImage breakableWall, unBreakableWall, lifePowerUp, speedPowerUp;
    private ExtraLife sizeExtraLife;
    private SpeedHack sizeSpeedHack;

    public Map(BufferedImage img) {
        this.img = img;
    }

    public void init(){
        try {
            breakableWall = read(new File("resources/wall1.gif"));
            unBreakableWall = read(new File("resources/Wall2.gif"));
            lifePowerUp = read(new File("resources/heart.png"));
            speedPowerUp = read(new File("resources/Rocket.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Top Unbreakable Ext.
        for(int a = 150; a < 300; a += 30){
            object.add(new Unbreakable(unBreakableWall, 840, a));
        }
        //Left Unbreakable Ext.
        for(int b = 350; b < 675; b +=30) {
            object.add(new Unbreakable(unBreakableWall, b, 460));
        }
        //Right Unbreakable Ext.
        for(int c = 1300; c > 1027; c -= 30){
            object.add(new Unbreakable(unBreakableWall, c, 460));
        }
        //Bottom Unbreakable Ext.
        for(int d = 780; d > 650 ; d -= 30){
            object.add(new Unbreakable(unBreakableWall, 840, d));
        }

        //Top Breakable Wall
        for(int e = 660; e < 1050; e += 30){
            object.add(new Breakable(breakableWall, e, 300));
        }
        //Bottom Breakable Wall
        for(int f = 660; f < 1050; f += 30){
            object.add(new Breakable(breakableWall, f, 630));
        }
        //Left Breakable Wall
        for(int g = 330; g < 610; g += 30){
            object.add(new Breakable(breakableWall, 660, g));
        }
        //Right Breakable Wall
        for(int h = 330; h < 610; h += 30){
            object.add(new Breakable(breakableWall, 1020, h));
        }

        //Life Power Up
        object.add(new ExtraLife(lifePowerUp, 40, 890));

        //Speed Power Up
        object.add(new SpeedHack(speedPowerUp, 1640, 50));
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int x = 0; x < TRE.WORLD_WIDTH/img.getWidth() + 1; x++) {
            for (int y = 0; y < TRE.WORLD_HEIGHT/img.getHeight() + 1; y++){
                g2d.drawImage(img, x*img.getWidth(), y*img.getHeight(), null);
            }
        }
        for(int i = 0; i < object.size(); i++){
            object.get(i).drawImage(g2d);
        }
    }
    public String toString() {
        return "x=" + x + ", y=" + y;
    }
}
