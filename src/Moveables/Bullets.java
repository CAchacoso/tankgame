package Moveables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Map.GameSetGet;
import Map.Map;
import Tank.TRE;
import Tank.Tank;
import Map.GameSetGet;
import Immoveables.Breakable;
import Immoveables.Walls;

import static javax.imageio.ImageIO.read;

public class Bullets extends GameSetGet{

    Tank tank;
    private int R = 5;
    private int x;
    private int y;
    private int vx;
    private int vy;
    protected int bx;
    protected int by;
    private int angle;
    boolean active = true;
    private static BufferedImage bullet;
    private Rectangle bound = new Rectangle(this.x, this.y, this.bullet.getWidth(), this.bullet.getHeight());

    static{
        try {
            bullet = read(new File("resources/Shell.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Bullets(Tank t, int x, int y, int angle){
        super(x, y, bullet);
        this.x = x;
        this.y = y;
        this.angle = angle;
        active = true;
        this.tank = t;
    }

    public boolean isActive(){
        return active;
    }

    public void update() {
        bx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        by = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += bx;
        y += by;
        checkCollision(this);
        updateBounds();
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bullet.getWidth() / 2.0, this.bullet.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bullet, rotation, null);

    }

    public void checkCollision(Bullets bullet){
        GameSetGet obj;
        Rectangle tbound = bullet.getBounds();
        for (int i =0; i< Map.object.size();i++){
            obj = Map.object.get(i);
            if (tbound.intersects(obj.getBounds()) && obj != tank){
                if(obj instanceof Breakable) {
                    Map.object.remove(obj);
                }
                if(obj instanceof Tank){
                    ((Tank) obj).takeDamage();
                }
                active = false;
            }
        }
    }

    public Rectangle getBounds(){
        return this.bound;
    }

    public void updateBounds(){
        this.bound = new Rectangle(this.x, this.y, bullet.getWidth(), bullet.getHeight());
    }
}
