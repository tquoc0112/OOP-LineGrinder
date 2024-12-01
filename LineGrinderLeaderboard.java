import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel; 

public class LineGrinderLeaderboard {

    private List<Player> players;

    // Constructor
    public LineGrinderLeaderboard() {
        this.players = new ArrayList<>(); // Khởi tạo danh sách
    }

    // Phương thức thêm người chơi
    public void addPlayer(String name, int wins, int losses) {
        this.players.add(new Player(name, wins, losses)); // Thêm vào danh sách
    }

    // Lớp Player để lưu thông tin người chơi
    static class Player implements Comparable<Player> {
        String name;
        int wins;
        int losses;
        float winRate;

        public Player(String name, int wins, int losses) {
            this.name = name;
            this.wins = wins;
            this.losses = losses;
            // Calculate win rate here for sorting
            this.winRate = (float) wins / (wins + losses); // Handle division by zero
        }

        @Override
        public int compareTo(Player other) {
            // Sort by win rate (descending)
            return Float.compare(other.winRate, this.winRate);
        }
    }

    // Method to display the leaderboard with Swing
    public void showLeaderboard() {
        // Create JFrame for the leaderboard window
        JFrame frame = new JFrame("LineGrinder Leaderboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define column names
        String[] columnNames = {"Tên", "Thắng", "Thua", "Hiệu Suất"};

        // Extract data from Player objects
        List<Object[]> playerData = new ArrayList<>();
        for (Player player : players) {
            playerData.add(new Object[]{player.name, player.wins, player.losses, player.winRate});
        }

        // Create a DefaultTableModel for the JTable
        DefaultTableModel model = new DefaultTableModel(playerData.toArray(new Object[0][0]), columnNames);

        // Create a JTable with the model
        JTable table = new JTable(model);

        // Add the JTable to a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(table);

        // Set the frame content pane and size
        frame.add(scrollPane);
        frame.pack();

        // Make the frame visible
        frame.setVisible(true);
    }
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players); // Return an unmodifiable copy
    }
   
}