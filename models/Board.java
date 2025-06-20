package models;
import java.util.*;   

public class Board {
    private int size;
    private PieceType[][] board;

    public Board(int size) {
        this.size = size;
        this.board = new PieceType[size][size];
    }
    
    public boolean addPiece(int row,int col,PieceType pieceType) {
        if(board[row][col] != null) {
            return false;
        }
        board[row][col] = pieceType;
        return true;
    }

    public void printBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print((board[i][j]==null?"|":board[i][j])+ "  ");
            }
            System.out.println();
        }
    }

    public List<Map.Entry<Integer,Integer>> getAvailableCells() {
        List<Map.Entry<Integer,Integer>> availableCells = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] == null) {
                    availableCells.add(new AbstractMap.SimpleEntry<>(i,j));
                }
                                
            }
        }
        return availableCells;
    }

    public int getSize() {
        return size;
    }

    public PieceType[][] getBoard() {
        return board;    
    }
    

}