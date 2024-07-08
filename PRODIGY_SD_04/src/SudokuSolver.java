public class SudokuSolver {
    private static final int UNASSIGNED = 0;
    private static final int N = 9;

    public boolean solveSudoku(int[][] board) {
        int[] rowCol = findUnassignedLocation(board);
        if (rowCol == null) {
            return true;
        }

        int row = rowCol[0];
        int col = rowCol[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) {
                    return true;
                }
                board[row][col] = UNASSIGNED;
            }
        }
        return false;
    }

    private int[] findUnassignedLocation(int[][] board) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (board[row][col] == UNASSIGNED) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        for (int x = 0; x < N; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
