package Immoveables;

import Map.GameSetGet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Unbreakable extends GameSetGet{
    private Rectangle bound;

    public Unbreakable(BufferedImage img, int x, int y) {
        super(x, y, img);
        this.bound = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        objects.add(this);
    }
}
