package models;

public class PlayingPiece {
    private PieceType pieceType;

    public PlayingPiece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceType getPieceType(){
        return pieceType;
    }

}
