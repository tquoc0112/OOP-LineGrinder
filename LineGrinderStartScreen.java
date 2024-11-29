import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LineGrinderStartScreen extends JFrame {
    public LineGrinderStartScreen() {
        setTitle("LineGrinder - Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("LineGrinder", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton startButton = new JButton("Start");
        JButton optionButton = new JButton("Options");
        JButton creditsButton = new JButton("Credits");

        buttonPanel.add(startButton);
        buttonPanel.add(optionButton);
        buttonPanel.add(creditsButton);

        add(buttonPanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> startGame());
        optionButton.addActionListener(e -> showOptions());
        creditsButton.addActionListener(e -> showCredits());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        dispose(); // Close the starting screen
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            final int FRAME_WIDTH = 600;
            final int FRAME_HEIGHT = 650;
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setTitle("LineGrinder");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            HomeMenu.main(new String[]{});// Start the game
            LineGrinderPanel panel = new LineGrinderPanel(19); // Default grid size
            frame.add(panel);

            frame.setVisible(true);
        });
    }

    private void showOptions() {
        HomeMenu.main(new String[]{});// Start the game
    }

    private void showCredits() {
        JOptionPane.showMessageDialog(this, "Created by: Your Name\nThanks for playing!", "Credits", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new LineGrinderStartScreen();
    }
}
