import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class LineGrinderPanel extends JPanel {
    private int boardSize;
    private int winningCondition; // Winning condition
    private ImageIcon player1Avatar;
    private ImageIcon player2Avatar;
    private int player1Time;
    private int player2Time;

    private JLabel player1TimerLabel;
    private JLabel player2TimerLabel;

    private Timer player1Timer;
    private Timer player2Timer;

    private boolean isPlayer1Turn = true;

    private int[][] board;
    private int[][] winningCoordinates; // To store winning line coordinates

    private String player1Name = "Player 1"; // Placeholder names, can be replaced
    private String player2Name = "Player 2";

    public LineGrinderPanel(int boardSize, int winningCondition, ImageIcon player1Avatar, ImageIcon player2Avatar,
            int player1Time,
            int player2Time) {
        this.boardSize = boardSize;
        this.winningCondition = winningCondition;
        this.player1Avatar = player1Avatar;
        this.player2Avatar = player2Avatar;
        this.player1Time = player1Time;
        this.player2Time = player2Time;

        this.board = new int[boardSize][boardSize]; // Initialize the board
        this.winningCoordinates = new int[winningCondition][2]; // Adjust size based on winning condition

        setLayout(new BorderLayout());

        // Left Panel for Player 1
        JPanel player1Panel = createPlayerPanel(player1Name, player1Avatar, true);

        // Right Panel for Player 2
        JPanel player2Panel = createPlayerPanel(player2Name, player2Avatar, false);

        // Add player panels
        add(player1Panel, BorderLayout.WEST);
        add(player2Panel, BorderLayout.EAST);

        // Board Panel
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
        add(boardPanel, BorderLayout.CENTER);

        // Initialize timers
        startTimers();
    }

    private JPanel createPlayerPanel(String playerName, ImageIcon playerAvatar, boolean isPlayer1) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel(playerName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel avatarLabel = new JLabel();
        avatarLabel.setIcon(new ImageIcon(playerAvatar.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel timerLabel = new JLabel(formatTime(isPlayer1 ? player1Time : player2Time));
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (isPlayer1) {
            player1TimerLabel = timerLabel;
        } else {
            player2TimerLabel = timerLabel;
        }

        playerPanel.add(nameLabel);
        playerPanel.add(Box.createVerticalStrut(10)); // Spacer
        playerPanel.add(avatarLabel);
        playerPanel.add(Box.createVerticalStrut(10)); // Spacer
        playerPanel.add(timerLabel);

        return playerPanel;
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
        for (int i = -winningCondition + 1; i < winningCondition; i++) { // Check in both directions
            int newRow = row + i * rowDir;
            int newCol = col + i * colDir;
            if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize
                    && board[newRow][newCol] == player) {
                if (count < winningCondition) {
                    winningCoordinates[count][0] = newRow;
                    winningCoordinates[count][1] = newCol;
                }
                count++;
                if (count == winningCondition) {
                    return true; // Found the required line
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
                    player1TimerLabel.setText(formatTime(player1Time));
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
                    player2TimerLabel.setText(formatTime(player2Time));
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
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
            }
        }
        player1Time = 300; // Reset timer
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

    private void drawBoard(Graphics g) {
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
