import javax.swing.*;

public class LineGrinder {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeMenu.main(new String[] {});
        });
    }

    public static void startGame(int size, ImageIcon player1Avatar, ImageIcon player2Avatar) {
        JFrame frame = new JFrame("LineGrinder");
        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        LineGrinderPanel panel = new LineGrinderPanel(size, "square", player1Avatar, player2Avatar);
        frame.add(panel);

        frame.setVisible(true);
    }
}
