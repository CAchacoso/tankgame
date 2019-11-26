package Immoveables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import Tank.TRE;
import Map.Map;

import static javax.imageio.ImageIO.read;

public class Walls {

    BufferedImage img;

    public Walls(BufferedImage img) {
        this.img = img;
    }

    public void drawTopBot(Graphics2D g) {
        Graphics2D g2d = g;
        for (int x = 0; x < TRE.WORLD_WIDTH; x += 32) {
            g2d.drawImage(this.img, x, 0, null);
        }
        for (int w = 0; w < TRE.WORLD_WIDTH; w += 32) {
            g2d.drawImage(this.img, w, TRE.WORLD_HEIGHT - 32, null);
        }
    }
    public void drawSides(Graphics2D g) {
        Graphics2D g2d = g;
        for (int y = 0; y < TRE.WORLD_HEIGHT; y += 32) {
            g2d.drawImage(this.img, 0, y, null);
        }
        for (int z = 0; z < TRE.WORLD_HEIGHT; z += 32) {
            g2d.drawImage(this.img, TRE.WORLD_WIDTH - 32, z, null);
        }
    }
}
