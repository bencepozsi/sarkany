/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author bencepozsi
 */
public class Player extends SpriteLogic {
    private final BufferedImage player_right;
    private final BufferedImage player_left;
    private final BufferedImage fog;
    
    private int velX;
    private int velY;
    private boolean face_right;
    
    public Player() throws IOException{
        super(14,720,50,80);
        velX = 0;
        velY = 0;
        player_right = ImageIO.read(new File("assets/character.png"));
        player_left = ImageIO.read(new File("assets/character_mirrored.png"));
        fog = ImageIO.read(new File("assets/fog-effect.png"));
        reset();
    }
    
    public void reset(){
        super.setX(14);
        super.setY(720);
        face_right = true;
    }
    
    public void draw(Graphics g){
        if (face_right){
            g.drawImage(player_right, x, y, null);
        } else {
            g.drawImage(player_left, x, y, null);
        }
        g.drawImage(fog,x-776,y-760,null);
    }
    
    public boolean finished(){
        if (x>720 && y < 80) return true; else return false;
    }
    
    public boolean caught(Dragon dragon){
        return this.collides(dragon);
    }
    
    public void changeFacing(){
        face_right = !face_right;
    }

    /**
     * @return the x
     */
    public int getVelX() {
        return velX;
    }

    /**
     * @param x the x to set
     */
    public void setVelX(int x) {
        velX = x;
    }

    /**
     * @return the y
     */
    public int getVelY() {
        return velY;
    }

    /**
     * @param y the y to set
     */
    public void setVelY(int y) {
        velY = y;
    }
    
    public boolean getFacing(){
        return face_right;
    }
    
}
