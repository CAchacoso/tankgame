package Map;

import javax.swing.*;
import java.awt.*;

/*
Source: https://stackoverflow.com/questions/8611268/java-creating-a-2d-tile-map-in-a-panel-using-graphics2d-rectangles
*/
public class Map extends JPanel{

    public static final int WIDTH = 2000;
    public static final int LENGTH = 2000;

    //private final Color[][] battleGrid;

    public Map(){
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run () {
                JFrame game = new JFrame("Tank Game");
                Map map = new Map();
                game.add(map);
                game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                game.pack();
                game.setVisible(true);
            }
        });
    }
}
