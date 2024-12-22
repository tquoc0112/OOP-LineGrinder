import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class LineGrinderPanel extends JPanel {
    private int boardSize;
    private int winningCondition;
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
    private int[][] winningCoordinates;
    private String player1Name;
    private String player2Name;

    public LineGrinderPanel(int boardSize, int winningCondition, ImageIcon player1Avatar, ImageIcon player2Avatar,
            int player1Time, int player2Time, String player1Name, String player2Name) {
        this.boardSize = boardSize;
        this.winningCondition = winningCondition;
        this.player1Avatar = player1Avatar;
        this.player2Avatar = player2Avatar;
        this.player1Time = player1Time;
        this.player2Time = player2Time;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.board = new int[boardSize][boardSize];
        this.winningCoordinates = new int[winningCondition][2];

        setLayout(new BorderLayout());

        // Create a side panel for player information
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add player panels to the side panel
        sidePanel.add(createPlayerPanel(player1Name, player1Avatar, true));
        sidePanel.add(Box.createVerticalStrut(30)); // Spacer between players
        sidePanel.add(createPlayerPanel(player2Name, player2Avatar, false));

        add(sidePanel, BorderLayout.WEST);

        // Create the board panel
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
        startTimers();
    }

    private JPanel createPlayerPanel(String playerName, ImageIcon playerAvatar, boolean isPlayer1) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(playerName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel avatarLabel = new JLabel();
        avatarLabel.setIcon(new ImageIcon(playerAvatar.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel timerLabel = new JLabel(formatTime(isPlayer1 ? player1Time : player2Time));
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (isPlayer1) {
            player1TimerLabel = timerLabel;
        } else {
            player2TimerLabel = timerLabel;
        }

        playerPanel.add(nameLabel);
        playerPanel.add(Box.createVerticalStrut(10));
        playerPanel.add(avatarLabel);
        playerPanel.add(Box.createVerticalStrut(10));
        playerPanel.add(timerLabel);

        return playerPanel;
    }

    private void handleMouseClick(int x, int y) {
        int cellSize = calculateCellSize();
        int boardStartX = (getWidth() - cellSize * boardSize) / 2;
        int boardStartY = (getHeight() - cellSize * boardSize) / 2;

        int col = (x - boardStartX) / cellSize;
        int row = (y - boardStartY) / cellSize;

        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == 0) {
            board[row][col] = isPlayer1Turn ? 1 : 2;

            // Check for a winner
            if (checkWinner(row, col)) {
                repaint();
                Timer endGameDelay = new Timer();
                endGameDelay.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String winnerName = isPlayer1Turn ? player1Name : player2Name;
                        endGame(winnerName + " wins!");
                    }
                }, 500);
                return;
            }

            // Check for a draw
            if (isBoardFull()) {
                repaint();
                Timer endGameDelay = new Timer();
                endGameDelay.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        endGame("YOU ARE BOTH TIED");
                    }
                }, 500);
                return;
            }

            isPlayer1Turn = !isPlayer1Turn; // Switch turns
            repaint();
        }
    }

    private boolean checkWinner(int row, int col) {
        int player = board[row][col];
        for (int[] dir : new int[][] { { 1, 0 }, { 0, 1 }, { 1, 1 }, { 1, -1 } }) {
            if (checkDirection(row, col, dir[0], dir[1], player)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir, int player) {
        int count = 0;
        for (int i = -winningCondition + 1; i < winningCondition; i++) {
            int newRow = row + i * rowDir;
            int newCol = col + i * colDir;
            if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize &&
                    board[newRow][newCol] == player) {
                winningCoordinates[count][0] = newRow;
                winningCoordinates[count][1] = newCol;
                count++;
                if (count == winningCondition) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
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
            resetBoard();
        } else {
            // Add logic to return to main menu, if applicable
            System.exit(0);
        }
    }

    private void resetBoard() {
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

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    private int calculateCellSize() {
        int availableWidth = getWidth();
        int availableHeight = getHeight();
        return Math.min(availableWidth, availableHeight) / boardSize;
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int cellSize = calculateCellSize();
        int boardStartX = (getWidth() - cellSize * boardSize) / 2;
        int boardStartY = (getHeight() - cellSize * boardSize) / 2;

        g2.setColor(Color.BLACK);
        for (int i = 0; i <= boardSize; i++) {
            int pos = i * cellSize;
            g2.drawLine(boardStartX, boardStartY + pos, boardStartX + boardSize * cellSize, boardStartY + pos); // Horizontal
            g2.drawLine(boardStartX + pos, boardStartY, boardStartX + pos, boardStartY + boardSize * cellSize); // Vertical
        }

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int piece = board[row][col];
                if (piece != 0) {
                    ImageIcon avatar = (piece == 1) ? player1Avatar : player2Avatar;
                    g2.drawImage(avatar.getImage(), boardStartX + col * cellSize, boardStartY + row * cellSize,
                            cellSize, cellSize, null);
                }
            }
        }

        // Highlight winning line
        if (winningCoordinates[0][0] >= 0) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            for (int[] coordinate : winningCoordinates) {
                if (coordinate[0] >= 0 && coordinate[1] >= 0) {
                    int x = boardStartX + coordinate[1] * cellSize;
                    int y = boardStartY + coordinate[0] * cellSize;
                    g2.drawRect(x, y, cellSize, cellSize);
                }
            }
        }
    }
}
