import java.util.Random;
import java.util.Scanner;

public class SudokuGame {

    private final int SIZE = 4;
    private int[][] board = new int[SIZE][SIZE];

    // template solusi
    private int[][] solution = {
        {1, 2, 3, 4},
        {3, 4, 1, 2},
        {2, 3, 4, 1},
        {4, 1, 2, 3}
    };

    private Scanner input = new Scanner(System.in);

    // =============================
    // 📌 Constructor
    // =============================
    public SudokuGame() {
        generatePuzzle();
    }

    private boolean isSafe(int[][] tempBoard, int row, int col, int num) {

    for (int j = 0; j < SIZE; j++) {
        if (tempBoard[row][j] == num) return false;
    }

    for (int i = 0; i < SIZE; i++) {
        if (tempBoard[i][col] == num) return false;
    }

    int startRow = (row / 2) * 2;
    int startCol = (col / 2) * 2;

    for (int i = startRow; i < startRow + 2; i++) {
        for (int j = startCol; j < startCol + 2; j++) {
            if (tempBoard[i][j] == num) return false;
        }
    }

    return true;
}

    // =============================
    // 📌 Shuffle solusi (BIAR RANDOM)
    // =============================
    public void shuffleSolution() {
    Random rand = new Random();

        // tukar baris hanya dalam blok (0-1, 2-3)
        for (int block = 0; block < SIZE; block += 2) {
            int r1 = block + rand.nextInt(2);
            int r2 = block + rand.nextInt(2);

            int[] temp = solution[r1];
            solution[r1] = solution[r2];
            solution[r2] = temp;
        }

        // tukar kolom hanya dalam blok
        for (int block = 0; block < SIZE; block += 2) {
            int c1 = block + rand.nextInt(2);
            int c2 = block + rand.nextInt(2);

            for (int i = 0; i < SIZE; i++) {
                int temp = solution[i][c1];
                solution[i][c1] = solution[i][c2];
                solution[i][c2] = temp;
            }
        }
    }

    private int countSolutions(int[][] tempBoard) {
    return solveAndCount(tempBoard, 0);
}

private int solveAndCount(int[][] tempBoard, int count) {

    if (count > 1) return count; // lebih dari 1 langsung stop

    for (int row = 0; row < SIZE; row++) {
        for (int col = 0; col < SIZE; col++) {

            if (tempBoard[row][col] == 0) {

                for (int num = 1; num <= SIZE; num++) {

                    if (isSafe(tempBoard, row, col, num)) {
                        tempBoard[row][col] = num;

                        count = solveAndCount(tempBoard, count);

                        tempBoard[row][col] = 0;
                    }
                }

                return count;
            }
        }
    }

    return count + 1; // solusi ditemukan
}
    // =============================
    // 📌 Generate Puzzle
    // =============================
  public void generatePuzzle() {
    Random rand = new Random();

    shuffleSolution();

    // copy solusi ke board
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            board[i][j] = solution[i][j];
        }
    }

    int attempts = 10; // jumlah percobaan hapus

    while (attempts > 0) {

        int row = rand.nextInt(SIZE);
        int col = rand.nextInt(SIZE);

        if (board[row][col] == 0) continue;

        int backup = board[row][col];
        board[row][col] = 0;

        // copy board ke temp
        int[][] tempBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            tempBoard[i] = board[i].clone();
        }

        int solutions = countSolutions(tempBoard);

        if (solutions != 1) {
            // batal hapus
            board[row][col] = backup;
            attempts--;
        }
    }
}
    // =============================
    // 📌 Tampilan Board
    // =============================
    public void printBoard() {
        System.out.println("\n=== SUDOKU 4x4 ===");

        System.out.print("    ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();

        System.out.println("  -------------");

        for (int i = 0; i < SIZE; i++) {

            System.out.print(i + " | ");

            for (int j = 0; j < SIZE; j++) {

                if (board[i][j] == 0)
                    System.out.print(". ");
                else
                    System.out.print(board[i][j] + " ");

                if (j == 1) System.out.print("| ");
            }

            System.out.println("|");

            if (i == 1) {
                System.out.println("  -------------");
            }
        }

        System.out.println("  -------------");
    }

    // =============================
    // 📌 Validasi (FULL RULE)
    // =============================
    public boolean isValid(int row, int col, int num) {

        // cek baris
        for (int j = 0; j < SIZE; j++) {
            if (board[row][j] == num) return false;
        }

        // cek kolom
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num) return false;
        }

        // cek blok 2x2
        int startRow = (row / 2) * 2;
        int startCol = (col / 2) * 2;

        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 2; j++) {
                if (board[i][j] == num) return false;
            }
        }

        return true;
    }

  public void play() {

    while (true) {

        printBoard();

        System.out.println("\nFormat input: baris kolom angka");
        System.out.println("\n=== INPUT ===");
        System.out.print("Masukkan baris (0-3): ");
        int row = input.nextInt();

        System.out.print("Masukkan kolom (0-3): ");
        int col = input.nextInt();

        System.out.print("Masukkan angka (1-4): ");
        int num = input.nextInt();

        // validasi posisi
        if (row < 0 || col < 0 || row >= SIZE || col >= SIZE) {
            System.out.println("[ERROR] Goblok!");
            continue;
        }

        // cek apakah sudah terisi
        if (board[row][col] != 0) {
            System.out.println("[ERROR] Kotak (" + row + "," + col + ") sudah terisi!");
            continue;
        }

        // preview aksi
        System.out.println("➡ Mengisi posisi (" + row + "," + col + ") dengan angka " + num);

        // validasi angka
        if (isValid(row, col, num)) {
            board[row][col] = num;

            System.out.println("[OK] Berhasil!");
            
            // tampilkan board langsung setelah update
            printBoard();

        } else {
            System.out.println("[ERROR] Angka melanggar aturan Sudoku!");
        }

        // cek menang
        if (isWin()) {
            printBoard();
            System.out.println("\n[WIN] SELAMAT! Kamu menang!");
            break;
        }
    }
}

    // =============================
    // 📌 Cek Menang
    // =============================
    public boolean isWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    // =============================
    // 📌 Main
    // =============================
    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.play();
    }
}