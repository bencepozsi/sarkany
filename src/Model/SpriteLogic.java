/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author bencepozsi
 */
public abstract class SpriteLogic {
    /**
     * The coordinates of the top left corner of the sprite
     */
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public SpriteLogic(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Returns true if this sprite collides with the other sprite
     * @param other
     * @return 
     */
    public boolean collides(SpriteLogic other) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
        if (x<0) return true;
        if (x+width>800) return true;
        if (y<0) return true;
        if (y+height>800) return true;
        return rect.intersects(otherRect);
    }
    
    public abstract void draw(Graphics g);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}