package PowerUp;

import Map.GameSetGet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedHack extends GameSetGet{

    private Rectangle bound;

    public SpeedHack(BufferedImage img, int x, int y){
        super(x,y,img);
        this.bound = new Rectangle(x,y, this.img.getWidth(), this.img.getHeight());
        objects.add(this);
    }
}