import javax.swing.*;

public class LineGrinder {

    public static void startGame(int boardSize, int winningCondition, ImageIcon player1Avatar, ImageIcon player2Avatar,
            String player1Timer,
            String player2Timer) {
        // Parse timer values (convert to seconds if needed)
        int player1Time = parseTimer(player1Timer);
        int player2Time = parseTimer(player2Timer);

        // Create game frame
        JFrame frame = new JFrame("LineGrinder");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Initialize LineGrinderPanel with timer values and winning condition
        LineGrinderPanel panel = new LineGrinderPanel(boardSize, winningCondition, player1Avatar, player2Avatar,
                player1Time,
                player2Time);
        frame.add(panel);

        // Display the frame
        frame.setVisible(true);
    }

    private static int parseTimer(String timerString) {
        // Remove "s" from the timer string and parse to an integer
        return Integer.parseInt(timerString.replace("s", ""));
    }
}
