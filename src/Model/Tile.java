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
public class Tile extends SpriteLogic{
    private final BufferedImage grass;
    private final BufferedImage wall;
    private final boolean isGrass;
    
    public Tile(int x, int y, int width, int height, boolean isGrass) throws IOException {
        super(x, y, width, height);
        grass = ImageIO.read(new File("assets/grass_edited.jpg"));
        wall = ImageIO.read(new File("assets/wall_edited.jpg"));
        this.isGrass = isGrass;
    }
    
    public void draw(Graphics g){
        if (isGrass){
            g.drawImage(grass,super.getX(),super.getY(),super.getWidth(),super.getHeight(),null);
        } else {
            g.drawImage(wall,super.getX(),super.getY(),super.getWidth(),super.getHeight(),null);
        }
    }
    
    public boolean ifGrass(){
        return isGrass;
    }
    
}
