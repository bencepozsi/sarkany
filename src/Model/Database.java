/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
//STEP 1. Import required packages

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

public class Database {

    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    private Connection connection;

    public Database() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "bead");
        connectionProps.put("password", "password");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/Bead";
        connection = DriverManager.getConnection(dbURL, connectionProps);
        
        String insertQuery = "INSERT INTO Bead.HighScores (name, score, level) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
    }
    
    public void setScore(String name, long time, int level) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Bead.HighScores (name, score, level) VALUES ('" + name + "', " + Long.toString(time) + ", " + Integer.toString(level)  +");" );
    }
    
    public ArrayList<HighScore> getHighScores() throws SQLException{
        String query = "SELECT * FROM Bead.HighScores";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("name");
            long score = results.getLong("score");
            int levelCount = results.getInt("level");
            highScores.add(new HighScore(name, score, levelCount));
        }
        sortHighScores(highScores);
        return highScores;
    }
    
    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                if (t1.getLevelCount() != t.getLevelCount()) return t1.getLevelCount() - t.getLevelCount();
                if (t1.getTime() != t.getTime()) return (int) ((-1)*(t1.getTime()-t.getTime()));
                return 0;
            }
        });
    }
}
