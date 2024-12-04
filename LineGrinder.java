import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineGrinder {
    public static void main(String[] args) {
        int size = 19;
        if (args.length > 0)
            size = Integer.parseInt(args[0]);

        JFrame frame = new JFrame();
        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGHT = 650;
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("LineGrinder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LineGrinderPanel panel = new LineGrinderPanel(size);
        frame.add(panel);

        frame.setVisible(true);
        LineGrinderLeaderboard leaderboard = new LineGrinderLeaderboard();

        // Add players
        leaderboard.addPlayer("Player 1", 10, 5);
        leaderboard.addPlayer("Player 2", 8, 7);
        leaderboard.addPlayer("Player 3", 15, 2);

        // Create a modifiable copy of the list and sort it
        List<LineGrinderLeaderboard.Player> players = new ArrayList<>(leaderboard.getPlayers());
        Collections.sort(players); // Sort the copy

        // Display sorted players
        for (LineGrinderLeaderboard.Player player : players) {
            System.out.println("Name: " + player.name + ", Wins: " + player.wins +
                    ", Losses: " + player.losses + ", Win Rate: " + player.winRate);
        }

        // Display the leaderboard using Swing
        leaderboard.showLeaderboard();
    }
}