import java.util.Scanner;

public class SudokuGame {

    private int[][] board = {
        {0, 2, 0, 0},
        {4, 0, 0, 2},
        {0, 3, 4, 0},
        {1, 0, 0, 3}
    };


    private final int SIZE = 4;
    private Scanner input = new Scanner(System.in);

    // 📌 Menampilkan board
    public void printBoard() {
        System.out.println("\n--- SUDOKU 4x4 ---");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0)
                    System.out.print(". ");
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 📌 Cek apakah angka valid
    public boolean isValid(int row, int col, int num) {

        // cek baris
        for (int j = 0; j < SIZE; j++) {
            if (board[row][j] == num) return false;
        }

        // cek kolom
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num) return false;
        }

        return true;
    }

    // 📌 Input user
    public void play() {

        while (true) {
            printBoard();

            System.out.print("\nMasukkan baris (0-3): ");
            int row = input.nextInt();

            System.out.print("Masukkan kolom (0-3): ");
            int col = input.nextInt();

            System.out.print("Masukkan angka (1-4): ");
            int num = input.nextInt();

            if (row < 0 || col < 0 || row >= SIZE || col >= SIZE) {
                System.out.println(" Posisi tidak valid!");
                continue;
            }

            if (board[row][col] != 0) {
                System.out.println(" Kotak sudah terisi!");
                continue;
            }

            if (isValid(row, col, num)) {
                board[row][col] = num;
                System.out.println(" Berhasil!");
            } else {
                System.out.println(" Tidak valid (duplikat baris/kolom)");
            }

            if (isWin()) {
                printBoard();
                System.out.println("\n SELAMAT! Kamu menang!");
                break;
            }
        }
    }

    // 📌 Cek kemenangan
    public boolean isWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    // 📌 Main program
    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.play();
    }
}