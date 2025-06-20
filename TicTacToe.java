import java.util.*;

import models.Board;
import models.Player; 
public class TicTacToe {
    Deque<Player> players;
    Board board; 

    public TicTacToe(int size, Deque<Player> players) {
        this.board = new Board(size);
        this.players = players; 
    }


    public String startGame() {
        boolean isGameRunning = true;
        while(isGameRunning) {
            Player currentPlayer = players.removeFirst();
            board.printBoard();
            List<Map.Entry<Integer,Integer>> availableCells = board.getAvailableCells();
            if(availableCells.isEmpty()) {
                isGameRunning = false;
                System.out.println("Game is a draw");
                break;
            }
            System.out.println(currentPlayer.getName()+" please Enter your move..format{i,j}");
            Scanner inputScanner=new Scanner(System.in);
            String s=inputScanner.nextLine();
            String[] values=s.split(",");
            int inputRow=Integer.valueOf(values[0]);
            int inputColumn=Integer.valueOf(values[1]);

            boolean pieceAddedSuccessfully=board.addPiece(inputRow,inputColumn,currentPlayer.getPieceType());
            if(!pieceAddedSuccessfully) {
                System.out.println("Incorrect position chosen, try again");
                players.addFirst(currentPlayer);
                continue;
            }

            players.addLast(currentPlayer);
            boolean winner=isThereWinner(currentPlayer,inputRow,inputColumn);
            if(winner) {
                isGameRunning = false;
                return currentPlayer.getName();
            }
            
            

        }
        return "tie";
    }

    private boolean isThereWinner(Player currentPlayer, int row,int col) {
        boolean rowMatch=true;
        boolean colMatch=true;
        boolean diagonalMatch=true;
        boolean reverseDiagonalMatch=true;

        for(int i=0;i<board.getSize();i++) { 
            if(board.getBoard()[i][col]==null||board.getBoard()[i][col]!=currentPlayer.getPieceType()){
                rowMatch=false;
                break;
            }
        }
        for(int j=0;j<board.getSize();j++) { 
            if(board.getBoard()[row][j]==null||board.getBoard()[row][j]!=currentPlayer.getPieceType()){
                colMatch=false;
                break;
            }
        }

        for(int i=0,j=0;j<board.getSize();i++,j++) { 
            if(board.getBoard()[i][j]==null||board.getBoard()[i][j]!=currentPlayer.getPieceType()){
                diagonalMatch=false;
                break;
            }
        }

        for(int i=0,j=board.getSize()-1;i<board.getSize();i++,j--) { 
            if(board.getBoard()[i][j]==null||board.getBoard()[i][j]!=currentPlayer.getPieceType()){
                reverseDiagonalMatch=false;
                break;
            }
        }

        System.out.println(rowMatch==true?"1 ":"0 ");

        System.out.println(colMatch==true?"1 ":"0 ");

        System.out.println(diagonalMatch==true?"1 ":"0 ");

        System.out.println(reverseDiagonalMatch==true?"1 ":"0 ");
        return rowMatch||colMatch||diagonalMatch||reverseDiagonalMatch;
    }
    
    
}
