package Tank;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Immoveables.Breakable;
import Immoveables.Unbreakable;
import Map.GameSetGet;
import Map.Map;
import Moveables.Bullets;
import PowerUp.ExtraLife;
import PowerUp.SpeedHack;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author cachacoso
 */
public class Tank extends GameSetGet{


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private int health = 100;
    private int lives = 3;
    private int damage = 10;
    private Bullets bullet;
    private int coolDown = 50;
    private int coolDownTime = 0;


    private int R = 3;
    private int ROTATIONSPEED = 8;
    public ArrayList<Bullets> bulletList = new ArrayList<>();

    private BufferedImage img;
    private Rectangle bound = new Rectangle(this.x, this.y);
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.health = 100;
        this.lives = 3;
        Map.object.add(this);
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }


    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
            updateBounds();
        }
        if (this.DownPressed) {
            this.moveBackwards();
            updateBounds();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
            updateBounds();
        }
        if (this.RightPressed) {
            this.rotateRight();
            updateBounds();
        }
        if (this.ShootPressed){
            this.bulletFire(bullet);
            updateBounds();
        }
        if (coolDownTime < coolDown) {
            coolDownTime += 1;
        }
        for (int x = 0; x < bulletList.size(); x++) {
            if (bulletList.get(x).isActive()) {
                bulletList.get(x).update();
            }
        }
        checkCollision(this);
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
        updateBounds();
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
        updateBounds();
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void bulletFire(Bullets bullet){
        if (coolDownTime >= coolDown) {
            bulletList.add(new Bullets(this, x, y, angle));
            coolDownTime = 0;
        }
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TRE.WORLD_WIDTH - 88) {
            x = TRE.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TRE.WORLD_HEIGHT - 80) {
            y = TRE.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public int getHealth(){
        return health;
    }
    public int getLives(){
        return lives;
    }

    public void GameOver(){
        if(lives == 0){
            System.exit(1);
        }
    }

    public void takeDamage() {
        health -= damage;
        if(health <= 0){
            health = 100;
            lives -=1;
            this.x = x;
            this.y = y;
        }
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int x = 0; x < bulletList.size(); x++) {
            if (bulletList.get(x).isActive()) {
                bulletList.get(x).drawImage(g2d);
            }
        }
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2d.drawImage(this.img, rotation, null);
    }

    public void checkCollision(Tank t){
        GameSetGet obj;
        Rectangle tbound = t.getBounds();
        for (int i =0; i< Map.object.size();i++){
            obj = Map.object.get(i);
            if (tbound.intersects(obj.getBounds())){
                handle(obj);
            }
        }
    }

    public Rectangle getBounds(){
        return this.bound;
    }

    public void updateBounds(){
        this.bound = new Rectangle(this.x, this.y, img.getWidth(),img.getHeight());
    }

    public void handle(GameSetGet obj){
        if (obj instanceof Unbreakable){
            if (this.UpPressed){
                this.x -= vx;
                this.y -= vy;
            }
            if (this.DownPressed){
                this.x += vx;
                this.y += vy;
            }
        }
        if (obj instanceof Breakable){
            if (this.UpPressed){
                this.x -= vx;
                this.y -= vy;
            }
            if (this.DownPressed){
                this.x += vx;
                this.y += vy;
            }
        }
        if (obj instanceof SpeedHack){
            R = 5;
            ROTATIONSPEED = 10;
            Map.object.remove(obj);

        }
        if (obj instanceof ExtraLife){
            this.lives += 1;
            Map.object.remove(obj);
        }
    }



}
