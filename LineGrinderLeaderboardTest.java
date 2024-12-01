import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineGrinderLeaderboardTest {
    public static void main(String[] args) {
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
