/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author bli
 */
public class HighScore {
    
    private final String name;
    private final int levelCount;
    private final long time ;

    public HighScore(String name, long time, int levelCount) {
        this.name = name;
        this.time = time;
        this.levelCount = levelCount;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }
    
    public int getLevelCount(){
        return levelCount;
    }
}
