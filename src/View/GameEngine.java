/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Database;
import Model.Dragon;
import Model.HighScore;
import Model.Level;
import Model.Player;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author bencepozsi
 */
public class GameEngine extends JPanel {

    // Konstansok
    private final int FPS = 60;
    private final int TILE_SIZE = 80;
    private final int MOVEMENT_SPEED = 8;

    private boolean paused;
    private int levelCount;
    private Level level;
    private Player player;
    private Dragon dragon;
    private Timer gameTimer;
    private long startTime;

    private Database db;

    public GameEngine() {
        super();
        paused = false;
        levelCount = 1;
        reset();
        startTime = System.currentTimeMillis();
        try {
            db = new Database();
        } catch (SQLException e) {
            System.err.println(e);
        }

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!paused) {
                    if (player.getFacing()) {
                        player.changeFacing();
                    }
                    if (!level.collides(player)) {
                        player.setX(player.getX() - MOVEMENT_SPEED);
                    }
                    if (level.collides(player)) {
                        player.setX(player.getX() + MOVEMENT_SPEED);
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!paused) {
                    if (!player.getFacing()) {
                        player.changeFacing();
                    }
                    if (!level.collides(player)) {
                        player.setX(player.getX() + MOVEMENT_SPEED);
                    }
                    if (level.collides(player)) {
                        player.setX(player.getX() - MOVEMENT_SPEED);
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!paused) {
                    if (!level.collides(player)) {
                        player.setY(player.getY() - MOVEMENT_SPEED);
                    }
                    if (level.collides(player)) {
                        player.setY(player.getY() + MOVEMENT_SPEED);
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!paused) {
                    if (!level.collides(player)) {
                        player.setY(player.getY() + MOVEMENT_SPEED);
                    }
                    if (level.collides(player)) {
                        player.setY(player.getY() - MOVEMENT_SPEED);
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        gameTimer = new Timer(1000 / FPS, new NewGameListener());
        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        level.draw(g);
        dragon.draw(g);
        player.draw(g);

    }

    public void reset() {
        try {
            level = new Level("assets/levels/level" + Integer.toString(levelCount) + ".txt");
        } catch (IOException e) {
            System.err.println("Failed to load level: " + Integer.toString(levelCount));
        }
        try {
            player = new Player();
            dragon = new Dragon(level);
        } catch (IOException e) {
            System.err.print("Error in loading an asset.");
        }
    }

    public ArrayList<HighScore> getHighScores() {
        ArrayList<HighScore> result = new ArrayList<>();

        try {
            result = db.getHighScores();
            return result;
        } catch (SQLException e) {
            System.err.println("Failed to get highscores.");
        }

        return result;
    }

    private void endGame() {
        String highscoreName = JOptionPane.showInputDialog("Enter your name for your highscore:");
        //System.out.println((System.currentTimeMillis() - startTime) / 1000);
        if (highscoreName != null) {
            try {
                db.setScore(highscoreName, ((System.currentTimeMillis() - startTime) / 1000), levelCount);
            } catch (SQLException ex) {
                System.err.println("Couldn't set highscore.");
                System.err.println(ex.getMessage());
            }
            setLevelCount(1);
            startTime = System.currentTimeMillis();
        }
    }

    class NewGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                //System.out.println((System.currentTimeMillis() - startTime)/1000);
                repaint();
                dragon.move();

                if (player.caught(dragon)) {
                    endGame();
                    reset();
                }

                if (player.finished()) {
                    levelCount++;
                    if (levelCount > 10) { // Ha megnyeri a játékot
                        endGame();
                    }
                    reset();
                }
            }

        }

    }

    /**
     * @param levelCount the levelCount to set
     */
    public void setLevelCount(int levelCount) {
        this.levelCount = levelCount;
    }
}
