/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.HighScore;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author bencepozsi
 */
public class LabirintusGUI {

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem highScoresItem;
    private JMenuItem newGame;
    private GameEngine gameEngine;
    private JMenu menu;

    public LabirintusGUI() throws IOException {
        frame = new JFrame("Labirintus");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        frame.setJMenuBar(menuBar);
        newGame = new JMenuItem("New Game");
        highScoresItem = new JMenuItem("Leaderboard");
        menuBar.add(menu);
        
        menu.add(newGame);
        newGame.addActionListener((ActionEvent ae) ->{
            gameEngine.setLevelCount(1);
            gameEngine.reset();
        });
        
        menu.add(highScoresItem);
        highScoresItem.addActionListener((ActionEvent ae) -> {
            ArrayList<HighScore> highscores = gameEngine.getHighScores();
            ArrayList<ArrayList<String>> leaders = new ArrayList<>();
            
            int i = 0;
            while (i < highscores.size()) {
                leaders.add(new ArrayList<>());
                leaders.get(i).add(highscores.get(i).getName());
                leaders.get(i).add(Integer.toString(highscores.get(i).getLevelCount()));
                leaders.get(i).add(Long.toString(highscores.get(i).getTime()));
                i++;
            }
            String[][] data = leaders.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
            String column[] = {"NAME", "COMPLETED LEVELS", "TIME IN SECONDS"};
            
            JTable jt = new JTable(data, column);
            jt.setFont(new Font("Serif", Font.PLAIN, 16));
            jt.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(jt);
            JOptionPane.showMessageDialog(null,scrollPane,"Leaderboard",JOptionPane.PLAIN_MESSAGE);
        });

        gameEngine = new GameEngine();
        frame.getContentPane().add(gameEngine);

        frame.setPreferredSize(new Dimension(800, 820));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

}
