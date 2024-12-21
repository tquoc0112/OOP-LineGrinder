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
    private int[][] winningCoordinates; // To store winning line coordinates

    private String player1Name = "Player 1"; // Placeholder names, can be replaced
    private String player2Name = "Player 2";

    public LineGrinderPanel(int boardSize, ImageIcon player1Avatar, ImageIcon player2Avatar, int player1Time,
            int player2Time) {
        this.boardSize = boardSize;
        this.player1Avatar = player1Avatar;
        this.player2Avatar = player2Avatar;
        this.player1Time = player1Time;
        this.player2Time = player2Time;

        this.board = new int[boardSize][boardSize]; // Initialize the board
        this.winningCoordinates = new int[5][2]; // For a maximum of 5 winning pieces

        setLayout(new BorderLayout());

        // Timer Panel
        JPanel timerPanel = new JPanel(new GridLayout(1, 2));
        timerLabel1 = new JLabel(player1Name + ": " + formatTime(player1Time), SwingConstants.CENTER);
        timerLabel2 = new JLabel(player2Name + ": " + formatTime(player2Time), SwingConstants.CENTER);
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

            // Reset winning coordinates before checking
            for (int i = 0; i < winningCoordinates.length; i++) {
                winningCoordinates[i][0] = -1;
                winningCoordinates[i][1] = -1;
            }

            // Check for a winner after placing a piece
            if (checkWinner(row, col)) {
                repaint(); // Ensure all changes are visible
                Timer endGameDelay = new Timer();
                endGameDelay.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String winnerName = isPlayer1Turn ? player1Name : player2Name;
                        endGame(winnerName + " wins!");
                    }
                }, 500); // 500ms delay to show the last move
                return;
            }

            isPlayer1Turn = !isPlayer1Turn; // Switch turns
            repaint();
        }
    }

    private boolean checkWinner(int row, int col) {
        int player = board[row][col];
        return checkDirection(row, col, 1, 0, player) || // Check horizontally
                checkDirection(row, col, 0, 1, player) || // Check vertically
                checkDirection(row, col, 1, 1, player) || // Check diagonal down-right
                checkDirection(row, col, 1, -1, player); // Check diagonal down-left
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir, int player) {
        int count = 0;
        for (int i = -4; i <= 4; i++) { // Check in both directions
            int newRow = row + i * rowDir;
            int newCol = col + i * colDir;
            if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize
                    && board[newRow][newCol] == player) {
                if (count < 5) {
                    winningCoordinates[count][0] = newRow;
                    winningCoordinates[count][1] = newCol;
                }
                count++;
                if (count == 5) {
                    return true; // Found 5 in a row
                }
            } else {
                count = 0; // Reset count if the sequence breaks
            }
        }
        return false;
    }

    private void startTimers() {
        player1Timer = new Timer();
        player2Timer = new Timer();

        player1Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isPlayer1Turn) {
                    player1Time--;
                    timerLabel1.setText(player1Name + ": " + formatTime(player1Time));
                    if (player1Time <= 0) {
                        endGame(player2Name + " wins! Player 1 ran out of time.");
                    }
                }
            }
        }, 0, 1000);

        player2Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPlayer1Turn) {
                    player2Time--;
                    timerLabel2.setText(player2Name + ": " + formatTime(player2Time));
                    if (player2Time <= 0) {
                        endGame(player1Name + " wins! Player 2 ran out of time.");
                    }
                }
            }
        }, 0, 1000);
    }

    private void endGame(String message) {
        player1Timer.cancel();
        player2Timer.cancel();
        int choice = JOptionPane.showOptionDialog(this, message, "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[] { "Restart", "Main Menu" }, "Restart");

        if (choice == 0) {
            restartGame();
        } else {
            backToMainMenu();
        }
    }

    private void restartGame() {
        // Reset the game board and timers
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
            }
        }
        player1Time = 300; // Example reset timer values
        player2Time = 300;
        isPlayer1Turn = true;
        repaint();
        startTimers();
    }

    private void backToMainMenu() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
        HomeMenu.main(null); // Call main menu
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

        // Highlight winning boxes
        if (winningCoordinates[0][0] >= 0 && winningCoordinates[0][1] >= 0) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            for (int[] coordinate : winningCoordinates) {
                if (coordinate[0] >= 0 && coordinate[1] >= 0) {
                    int x = (int) (coordinate[1] * cellSize);
                    int y = (int) (coordinate[0] * cellSize);
                    g2.drawRect(x, y, (int) cellSize, (int) cellSize);
                }
            }
        }
    }
}
