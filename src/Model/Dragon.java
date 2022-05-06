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
import java.util.Random;

/**
 *
 * @author bencepozsi
 */
public class Dragon extends SpriteLogic{
    
    private final BufferedImage dragon_right;
    private final BufferedImage dragon_left;
    private final Level level;
    
    private final int SPEED = 1;
    
    private boolean face_right;
    private int movDir;
    
    public Dragon(Level level) throws IOException {
        super(0, 0, 80, 80);
        this.level = level;
        dragon_right = ImageIO.read(new File("assets/dragon.png"));
        dragon_left = ImageIO.read(new File("assets/dragon_mirrored.png"));
        reset();
    }
    
    private void generateSpawn(){
        Random rand = new Random();
        super.setX(rand.nextInt(10)*80);
        super.setY(rand.nextInt(10)*80);
        while (level.collides(this) || (x == 0 && y == 720 )){
            super.setX(rand.nextInt(10)*80);
            super.setY(rand.nextInt(10)*80);
        }
    }
    
    public void reset(){
        generateSpawn();
        movDir = 1;
        face_right = true;
    }
    
    public void move(){
        Random rand = new Random();
        switch (movDir){
            case 0:
                if (!level.collides(this)){
                 super.setY(super.getY()-SPEED);   
                }
                if (level.collides(this)){
                    super.setY(super.getY()+SPEED);
                    movDir = rand.nextInt(4);
                }
                break;
            case 1:
                if (!level.collides(this)){
                    super.setX(super.getX()+SPEED);
                }
                if (level.collides(this)){
                    super.setX(super.getX()-SPEED);
                    movDir = rand.nextInt(4);
                }
                break;
            case 2:
                if (!level.collides(this)){
                    super.setY(super.getY()+SPEED);
                }
                if (level.collides(this)){
                    super.setY(super.getY()-SPEED);
                    movDir = rand.nextInt(4);
                }
                break;
            case 3:
                if (!level.collides(this)){
                    super.setX(super.getX()-SPEED);
                }
                if (level.collides(this)){
                    super.setX(super.getX()+SPEED);
                    movDir = rand.nextInt(4);
                }
                break;
        
        }
        
        if (movDir == 1) face_right = true;
        if (movDir == 3) face_right = false;
    }

    @Override
    public void draw(Graphics g) {
        if (face_right){
            g.drawImage(dragon_right, x, y, null);
        } else {
            g.drawImage(dragon_left, x, y, null);
        }
    }
    
}
