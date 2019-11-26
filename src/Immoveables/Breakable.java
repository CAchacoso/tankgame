package Immoveables;

import Map.GameSetGet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Breakable extends GameSetGet{

    private Rectangle bounds;

    public Breakable(BufferedImage img, int x, int y) {
        super(x, y, img);
        this.bounds = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        objects.add(this);
        }

    }
