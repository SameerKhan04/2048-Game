package game;


import java.util.Random;

public class Game2048 {
    protected int[][] board;
    protected int size;
    private Random rand;
    protected boolean gameOver;

    public Game2048(int size) {
        setSize(size);
        board = new int[size][size];
        rand = new Random();
        gameOver = false;
        spawnTile();
        spawnTile();
    }

    public void setSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.size = size;
    }

    protected void spawnTile() {
        int row, col;
        do {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        } while (board[row][col] != 0);

        board[row][col] = rand.nextInt(10) < 9 ? 2 : 4;
    }

    protected boolean moveUp() {
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

    protected boolean moveDown() {
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

    protected boolean moveLeft() {
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

    protected boolean moveRight() {
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

    protected boolean canMove() {
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
}
