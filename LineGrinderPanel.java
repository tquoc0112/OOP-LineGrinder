import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class LineGrinderPanel extends JPanel {
    private int boardSize;
    private ImageIcon player1Avatar;
    private ImageIcon player2Avatar;
    private int player1Time;
    private int player2Time;

    private JLabel timerLabel1;
    private JLabel timerLabel2;

    private Timer player1Timer;
    private Timer player2Timer;

    private boolean isPlayer1Turn = true;

    private int[][] board;

    public LineGrinderPanel(int boardSize, ImageIcon player1Avatar, ImageIcon player2Avatar, int player1Time,
            int player2Time) {
        this.boardSize = boardSize;
        this.player1Avatar = player1Avatar;
        this.player2Avatar = player2Avatar;
        this.player1Time = player1Time;
        this.player2Time = player2Time;

        this.board = new int[boardSize][boardSize]; // Initialize the board

        setLayout(new BorderLayout());

        // Timer Panel
        JPanel timerPanel = new JPanel(new GridLayout(1, 2));
        timerLabel1 = new JLabel("Player 1: " + formatTime(player1Time), SwingConstants.CENTER);
        timerLabel2 = new JLabel("Player 2: " + formatTime(player2Time), SwingConstants.CENTER);
        timerPanel.add(timerLabel1);
        timerPanel.add(timerLabel2);

        add(timerPanel, BorderLayout.NORTH);

        // Add mouse listener for placing pieces
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });

        // Initialize timers
        startTimers();
    }

    private void handleMouseClick(int x, int y) {
        double cellSize = getWidth() / (double) boardSize;
        int row = (int) (y / cellSize);
        int col = (int) (x / cellSize);

        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == 0) {
            board[row][col] = isPlayer1Turn ? 1 : 2;
            isPlayer1Turn = !isPlayer1Turn; // Switch turns
            repaint();
        }
    }

    private void startTimers() {
        player1Timer = new Timer();
        player2Timer = new Timer();

        player1Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isPlayer1Turn) {
                    player1Time--;
                    timerLabel1.setText("Player 1: " + formatTime(player1Time));
                    if (player1Time <= 0) {
                        endGame("Player 2 Wins! Player 1 ran out of time.");
                    }
                }
            }
        }, 0, 1000);

        player2Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPlayer1Turn) {
                    player2Time--;
                    timerLabel2.setText("Player 2: " + formatTime(player2Time));
                    if (player2Time <= 0) {
                        endGame("Player 1 Wins! Player 2 ran out of time.");
                    }
                }
            }
        }, 0, 1000);
    }

    private void endGame(String message) {
        player1Timer.cancel();
        player2Timer.cancel();
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double cellSize = getWidth() / (double) boardSize;

        // Draw grid
        g2.setColor(Color.BLACK);
        for (int i = 0; i <= boardSize; i++) {
            int pos = (int) (i * cellSize);
            g2.drawLine(0, pos, getWidth(), pos); // Horizontal lines
            g2.drawLine(pos, 0, pos, getHeight()); // Vertical lines
        }

        // Draw pieces
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int piece = board[row][col];
                if (piece != 0) {
                    ImageIcon avatar = (piece == 1) ? player1Avatar : player2Avatar;
                    g2.drawImage(avatar.getImage(), (int) (col * cellSize), (int) (row * cellSize), (int) cellSize,
                            (int) cellSize, null);
                }
            }
        }
    }
}
