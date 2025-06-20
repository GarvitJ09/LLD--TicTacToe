import java.util.*;

import models.PieceType;
import models.Player;

public class Main {
    public static void main(String[] args) { 
        System.out.println("Welcome to TicTacToe game!!");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board size: ");
        int size = scanner.nextInt();

        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // consume newline
        

        Deque<Player> players = new LinkedList<>();
        PieceType[] pieceTypes = PieceType.values();
        if(size<=1){
            System.out.println("Board size is not valid");
            return;
        }else if(numPlayers<=1){
            System.out.println("Not enough players. Please add more.");
            return;
        }
        else if(numPlayers>pieceTypes.length){
            System.out.println("Not enough PieceTypes for players. Please add more to PieceTypes.");
            return;
        }
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for player " + (i + 1) + ": ");
            String name = scanner.nextLine(); 
            players.add(new Player(name, pieceTypes[i]));
             
        }

        TicTacToe ticTacToe = new TicTacToe(size, players);
        System.out.println("Winner of game is: "+ticTacToe.startGame());
    }
}