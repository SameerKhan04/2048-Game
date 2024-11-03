package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Game2048GUI extends JFrame {
    private int[][] board;
    private int size;
    private boolean gameOver;
    private JPanel boardPanel;
    private JLabel[][] cells;
    private Game2048 game;
    private Random rand;

    public Game2048GUI(int size) {
        game = new Game2048(size);
        this.size = size;
        this.board = new int[size][size];
        this.gameOver = false;
        rand = new Random();

        setTitle("2048 Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        boardPanel = new JPanel(new GridLayout(size, size));
        cells = new JLabel[size][size];

        setupUI();
        spawnTile();
        spawnTile();
        updateUI();
    }

    private void setupUI() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new JLabel("", SwingConstants.CENTER);
                cells[i][j].setOpaque(true);
                cells[i][j].setBackground(Color.LIGHT_GRAY);
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                boardPanel.add(cells[i][j]);
            }
        }

        add(boardPanel);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;

                boolean moved = false;
                switch (e.getKeyChar()) {
                    case 'w':
                        moved = moveUp();
                        break;
                    case 's':
                        moved = moveDown();
                        break;
                    case 'a':
                        moved = moveLeft();
                        break;
                    case 'd':
                        moved = moveRight();
                        break;
                    default:
                        moved = false;
                }

                if (moved) {
                    spawnTile();
                    updateUI();
                    if (!canMove()) {
                        JOptionPane.showMessageDialog(Game2048GUI.this, "Game Over");
                        gameOver = true;
                    }
                }
            }
        });
    }

    private void updateUI() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = board[i][j];
                cells[i][j].setText(value == 0 ? "" : String.valueOf(value));
                cells[i][j].setBackground(getTileColor(value));
            }
        }
    }

    private Color getTileColor(int value) {
        switch (value) {
            case 2:
                return Color.PINK;
            case 4:
                return Color.ORANGE;
            case 8:
                return Color.YELLOW;
            case 16:
                return Color.GREEN;
            case 32:
                return Color.CYAN;
            case 64:
                return Color.BLUE;
            case 128:
                return Color.MAGENTA;
            case 256:
                return Color.RED;
            case 512:
                return Color.LIGHT_GRAY;
            case 1024:
                return Color.DARK_GRAY;
            case 2048:
                return Color.BLACK;
            default:
                return Color.LIGHT_GRAY;
        }
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < size; col++) {
            int[] currentColumn = new int[size];
            int index = 0;
            boolean merged = false;
            for (int row = 0; row < size; row++) {
                if (board[row][col] != 0) {
                    if (index > 0 && currentColumn[index - 1] == board[row][col] && !merged) {
                        currentColumn[index - 1] *= 2;
                        merged = true;
                    } else {
                        currentColumn[index++] = board[row][col];
                        merged = false;
                    }
                    if (row != index - 1) {
                        moved = true;
                    }
                }
            }
            for (int row = 0; row < size; row++) {
                board[row][col] = currentColumn[row];
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < size; col++) {
            int[] currentColumn = new int[size];
            int index = size - 1;
            boolean merged = false;
            for (int row = size - 1; row >= 0; row--) {
                if (board[row][col] != 0) {
                    if (index < size - 1 && currentColumn[index + 1] == board[row][col] && !merged) {
                        currentColumn[index + 1] *= 2;
                        merged = true;
                    } else {
                        currentColumn[index--] = board[row][col];
                        merged = false;
                    }
                    if (row != index + 1) {
                        moved = true;
                    }
                }
            }
            for (int row = 0; row < size; row++) {
                board[row][col] = currentColumn[row];
            }
        }
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int row = 0; row < size; row++) {
            int[] currentRow = new int[size];
            int index = 0;
            boolean merged = false;
            for (int col = 0; col < size; col++) {
                if (board[row][col] != 0) {
                    if (index > 0 && currentRow[index - 1] == board[row][col] && !merged) {
                        currentRow[index - 1] *= 2;
                        merged = true;
                    } else {
                        currentRow[index++] = board[row][col];
                        merged = false;
                    }
                    if (col != index - 1) {
                        moved = true;
                    }
                }
            }
            for (int col = 0; col < size; col++) {
                board[row][col] = currentRow[col];
            }
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < size; row++) {
            int[] currentRow = new int[size];
            int index = size - 1;
            boolean merged = false;
            for (int col = size - 1; col >= 0; col--) {
                if (board[row][col] != 0) {
                    if (index < size - 1 && currentRow[index + 1] == board[row][col] && !merged) {
                        currentRow[index + 1] *= 2;
                        merged = true;
                    } else {
                        currentRow[index--] = board[row][col];
                        merged = false;
                    }
                    if (col != index + 1) {
                        moved = true;
                    }
                }
            }
            for (int col = 0; col < size; col++) {
                board[row][col] = currentRow[col];
            }
        }
        return moved;
    }

    private boolean canMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0)
                    return true;
                if (i < size - 1 && board[i][j] == board[i + 1][j])
                    return true;
                if (j < size - 1 && board[i][j] == board[i][j + 1])
                    return true;
            }
        }
        return false;
    }

    private void spawnTile() {
        int row, col;
        do {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        } while (board[row][col] != 0);

        board[row][col] = rand.nextInt(10) < 9 ? 2 : 4;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game2048GUI game = new Game2048GUI(4);
            game.setVisible(true);
        });
    }
}
