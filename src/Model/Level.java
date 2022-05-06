/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author bencepozsi
 */
public class Level {
    
    private final int boardSize = 10;
    private final int TILE = 80;
    private ArrayList<Tile> tiles;

    public Level(String pathToLevel) throws IOException {
        loadLevel(pathToLevel);
    }

    public void loadLevel(String pathToLevel) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathToLevel));
        tiles = new ArrayList<>();
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;  
            for (char tileType : line.toCharArray()) {
                if (Character.isDigit(tileType)){
                    if (tileType == '0'){
                        tiles.add(new Tile(x*TILE,y*TILE , TILE, TILE, true));
                    } else if (tileType == '1'){
                        tiles.add(new Tile(x*TILE,y*TILE , TILE, TILE, false));
                    }
                }
                x++;
            }
            y++;
        }
        
    }
    
    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public void draw(Graphics g) {
        for (Tile tile : tiles){
            tile.draw(g);
        }
    }

    public boolean collides(SpriteLogic sprite) {
        Tile collidedWith = null;
        for (Tile tile : tiles){
            if(sprite.collides(tile) && !tile.ifGrass()){
                collidedWith = tile;
                break;
            }
        }
        if (collidedWith != null){
            return true;
        } else {
            return false;
        }
    }

}
