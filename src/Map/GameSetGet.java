package Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameSetGet {

    protected int x;
    protected int y;
    protected int vx;
    protected int vy;
    protected BufferedImage img;
    private Rectangle bounds;
    public static ArrayList<GameSetGet> objects = new ArrayList<>();

    public GameSetGet(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.bounds = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    public Rectangle getBounds(){
        return this.bounds;
    }

    public void updateBounds(){
        this.bounds = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, x, y, null);
    }

    public int getX(int x){
        return this.x;
    }
    public int getY(int y){
        return this.y;
    }
    public int getWidth(){
        return this.getWidth();
    }
    public int getHeight(){
        return this.getHeight();
    }


}
