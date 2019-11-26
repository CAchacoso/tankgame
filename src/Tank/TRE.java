/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;
import java.util.ArrayList;
import Map.Map;
import Immoveables.Walls;
import Moveables.Bullets;
import javafx.scene.transform.Affine;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author cachacoso
 */
public class TRE extends JPanel {


    public static final int SCREEN_WIDTH = 1440;
    public static final int SCREEN_HEIGHT = 800;
    public static final int WORLD_WIDTH = SCREEN_WIDTH + SCREEN_WIDTH/5;
    public static final int WORLD_HEIGHT = SCREEN_HEIGHT + SCREEN_HEIGHT/5;
    private BufferedImage world;
    private Graphics2D buffer;
    private BufferedImage tileImage;
    private JFrame jf;
    private Tank t1;
    private Tank t2;
    private Map mini;
    private Map background;
    private Walls walls;
    private Bullets bullet;


    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {

            while (true) {
                trex.t1.update();
                trex.repaint();
                System.out.println(0);
                Thread.sleep(1000 / 144);

                trex.t2.update();
                trex.repaint();
                System.out.println(1);
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
        }
    }

    private void init() {
        this.jf = new JFrame("Tank Wars");
        this.world = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null;
        BufferedImage back = null;
        BufferedImage wall = null;
        BufferedImage bull = null;

        try {
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */

            back =  read(new File("resources/Background.bmp"));
            wall = read(new File("resources/Wall2.gif"));
            t1img = read(new File("resources/tank1.png"));
            t2img = read(new File("resources/tank1.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        walls = new Walls(wall);
        background = new Map(back);
        background.init();
        t1 = new Tank(195, 115, 0, 0, 90, t1img);
        t2 = new Tank(1485, 800, 0, 0, 270, t2img);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);


        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(true);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    public int checkBoundsX(Tank tx){
        int XBound = tx.getX() - SCREEN_WIDTH/4;
        if(XBound < 0){
            XBound = 0;
        }else if(XBound > WORLD_WIDTH - SCREEN_WIDTH/2){
            XBound = WORLD_WIDTH - SCREEN_WIDTH/2;
        }
        return XBound;
    }
    public int checkBoundsY(Tank ty){
        int YBound = ty.getY();
        if(YBound < 0){
            YBound = 0;
        }else if(YBound > WORLD_HEIGHT - (SCREEN_HEIGHT + 10)){
            YBound = WORLD_HEIGHT - (SCREEN_HEIGHT + 10);
        }
        return YBound;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);

        this.background.drawImage(buffer);

        this.walls.drawTopBot(buffer);
        this.walls.drawSides(buffer);

        this.t1.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);

        this.t2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);

        //SPLIT SCREEN
        int subimaget1x = checkBoundsX(t1);
        int subimaget1y = checkBoundsY(t1);
        int subimaget2x = checkBoundsX(t2);
        int subimaget2y = checkBoundsY(t2);
        BufferedImage leftScreen = this.world.getSubimage(subimaget1x, subimaget1y, SCREEN_WIDTH/2, SCREEN_HEIGHT + 10);
        BufferedImage rightScreen = this.world.getSubimage(subimaget2x, subimaget2y, SCREEN_WIDTH/2, SCREEN_HEIGHT + 10);
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, SCREEN_WIDTH/2, 0, null);

        AffineTransform mini = AffineTransform.getTranslateInstance((SCREEN_WIDTH/2) - 105, 620) ;
        mini.scale(0.15, 0.20);
        g2.drawImage(world, mini, null);

        g2.setFont(new Font("CourierNew", Font.BOLD, 25));

        g2.setColor(Color.BLUE);
        g2.drawString("Lives : " + this.t1.getLives(), SCREEN_WIDTH * 27/80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.BLUE);
        g2.drawString("Player 1 Health: " + this.t1.getHealth(), SCREEN_WIDTH * 12 / 80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t1.getHealth() && i < 100; i++) {
            g2.drawRect(SCREEN_WIDTH * 13 / 80 + i, SCREEN_HEIGHT * 36 / 40, 60, 30);
        }
        g2.setColor(Color.BLUE);
        g2.drawString("Lives : " + this.t2.getLives(), SCREEN_WIDTH * 65/80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.BLUE);
        g2.drawString("Player 2 Health: " + this.t2.getHealth(), SCREEN_WIDTH * 50 / 80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t2.getHealth() && i < 100; i++) {
            g2.drawRect(SCREEN_WIDTH * 50 / 80 + i, SCREEN_HEIGHT * 36 / 40, 60, 30);
        }
        this.t1.GameOver();
        this.t2.GameOver();
    }
}

