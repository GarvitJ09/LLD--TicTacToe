import java.util.*;

import models.Board;
import models.PieceType;
import models.Player;
import models.PlayingPiece;

public class TicTacToe {
    Deque<Player> players;
    Board board;
    List<Set<PieceType>> rows;
    List<Set<PieceType>> cols;
    List<Set<PieceType>> mainDia;
    List<Set<PieceType>> secDia;

    public TicTacToe(Integer size, Deque<Player> players) {
        this.board = new Board(size);
        this.players = players;
        this.rows = new ArrayList<>(size);
        this.cols = new ArrayList<>(size);
        this.mainDia = new ArrayList<>(2 * size - 1);
        this.secDia = new ArrayList<>(2 * size - 1);

        // Initialize rows and cols with empty sets
        for (int i = 0; i < size; i++) {
            this.rows.add(new HashSet<>());
            this.cols.add(new HashSet<>());
        }
        // Initialize diagonals with empty sets
        for (int i = 0; i < 2 * size - 1; i++) {
            this.mainDia.add(new HashSet<>());
            this.secDia.add(new HashSet<>());
        }
    }

    public TicTacToe() {
    }

    public String startGame() {
        boolean isGameRunning = true;
        while (isGameRunning) {
            Player currentPlayer = players.removeFirst();
            board.printBoard();
            List<Map.Entry<Integer, Integer>> availableCells = board.getAvailableCells();
            if (availableCells.isEmpty()) {
                isGameRunning = false;
                System.out.println("Game is a draw");
                break;
            }
            System.out.println(currentPlayer.getName() + " please Enter your move..format{i,j}");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            Integer inputRow = Integer.valueOf(values[0]);
            Integer inputColumn = Integer.valueOf(values[1]);

            this.rows.get(inputRow).add(currentPlayer.getPieceType());
            this.cols.get(inputColumn).add(currentPlayer.getPieceType());
            this.mainDia.get(inputRow + inputColumn).add(currentPlayer.getPieceType());
            this.secDia.get(inputRow - inputColumn + board.getSize() -
                    1).add(currentPlayer.getPieceType());

            boolean pieceAddedSuccessfully = board.addPiece(inputRow, inputColumn, currentPlayer.getPieceType());
            if (!pieceAddedSuccessfully) {
                System.out.println("Incorrect position chosen, try again");
                players.addFirst(currentPlayer);
                continue;
            }

            players.addLast(currentPlayer);
            boolean winner = isThereWinner(currentPlayer, inputRow, inputColumn);

            if (winner) {
                isGameRunning = false;
                return currentPlayer.getName();
            }

            boolean isGameValid = isGameValid(board);
            if (!isGameValid) {
                System.out.println("No More winning chances left, so starting a new game!");
                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter 0 for Quit , 1 to start new game: ");
                int playingChoice = scanner.nextInt();
                if (playingChoice == 0)
                    return "Game Ended!";
                else
                    setUpGame();

            }

        }
        return "tie";
    }

    public void setUpGame() {
        System.out.println("Welcome to TicTacToe game!!");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board size: ");
        int size = scanner.nextInt();

        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Deque<Player> players = new LinkedList<>();
        PieceType[] pieceTypes = PieceType.values();
        if (size <= 1) {
            System.out.println("Board size is not valid");
            return;
        } else if (numPlayers <= 1) {
            System.out.println("Not enough players. Please add more.");
            return;
        } else if (numPlayers > size) {
            System.out.println("Not enough PieceTypes for players. Please add more to PieceTypes.");
            return;
        }
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name, pieceTypes[i]));

        }

        TicTacToe ticTacToe = new TicTacToe(size, players);
        System.out.println("Winner of game is: " + ticTacToe.startGame());
    }

    private boolean isGameValid(Board board) {
        for (Set i : rows) {
            if (i.size() < 2)
                return true;
        }
        for (Set i : cols) {
            if (i.size() < 2)
                return true;
        }

        if (mainDia.get(board.getSize() - 1).size() < 2)
            return true;

        if (secDia.get(board.getSize() - 1).size() < 2)
            return true;

        return false;
    }

    private boolean isThereWinner(Player currentPlayer, int row, int col) {
        boolean rowMatch = true;
        boolean colMatch = true;
        boolean diagonalMatch = true;
        boolean reverseDiagonalMatch = true;

        for (int i = 0; i < board.getSize(); i++) {
            if (board.getBoard()[i][col] == null || board.getBoard()[i][col] != currentPlayer.getPieceType()) {
                rowMatch = false;
                break;
            }
        }
        for (int j = 0; j < board.getSize(); j++) {
            if (board.getBoard()[row][j] == null || board.getBoard()[row][j] != currentPlayer.getPieceType()) {
                colMatch = false;
                break;
            }
        }

        for (int i = 0, j = 0; j < board.getSize(); i++, j++) {
            if (board.getBoard()[i][j] == null || board.getBoard()[i][j] != currentPlayer.getPieceType()) {
                diagonalMatch = false;
                break;
            }
        }

        for (int i = 0, j = board.getSize() - 1; i < board.getSize(); i++, j--) {
            if (board.getBoard()[i][j] == null || board.getBoard()[i][j] != currentPlayer.getPieceType()) {
                reverseDiagonalMatch = false;
                break;
            }
        }

        return rowMatch || colMatch || diagonalMatch || reverseDiagonalMatch;
    }

}
