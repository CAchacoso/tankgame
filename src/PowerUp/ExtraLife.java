package PowerUp;

import Map.GameSetGet;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ExtraLife extends GameSetGet{

    private Rectangle bound;

    public ExtraLife(BufferedImage img, int x, int y){
        super(x, y, img);
        this.bound = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        objects.add(this);
    }
}

