
public class Pawn extends ChessPiece{
	private boolean movedTwo; //used to check en passant condition
	final int forwardDirection; //if white, the piece moves towards the top, if black towards the bottom, thus this field differentiate
	public Pawn(boolean isWhite) {
		this.isWhite = isWhite;
		movedTwo = false;
		forwardDirection = isWhite ? 1 : -1;
	}
	public boolean canMove(Space there, Board b) {
		ChessPiece pieceThere = there.pieceHere;
		if (pieceThere != null) {
			if (pieceThere.isWhite != isWhite) {
				return there.y - here.y == forwardDirection && isDiagonalMove(there, b); //if forward diagonal
			}
			return false;
		}
		if (canEnPassant(there, b)) {
			return true;
		}
		int[] relative = here.relativeTo(there);
		if (isWhite ? relative[1] > 0 : relative[1] < 0) { //moving forward
			if (here.y == (isWhite ? 1: 6)) { //if at start
				if (Math.abs(relative[1]) <= 2) { //can move two spaces or one
					return isVerticalMove(there, b);	
				}
			}
			else {
				if (Math.abs(relative[1]) == 1) { //can only move one space
					return isVerticalMove(there, b);
				}
			}
		}
		return false;
	}
	private boolean canEnPassant(Space there, Board b) {
		Space here = this.here;
		ChessPiece pieceAtSide;
		ChessPiece pieceAtOtherSide;

		try {	
			pieceAtSide = b.spaceAt(here.x + 1, here.y).pieceHere;
		}
		catch(IllegalArgumentException e) { //if the pawn is on the edge of the board; no piece on this side
			pieceAtSide = null;
		}
		try {
			pieceAtOtherSide = b.spaceAt(here.x - 1, here.y).pieceHere;
		}
		catch (IllegalArgumentException e) {
			pieceAtOtherSide = null;
		}
		if (pieceAtSide instanceof Pawn && pieceAtSide.isWhite != isWhite) {
			Pawn otherPawn = (Pawn) pieceAtSide;
			if (otherPawn.movedTwo()) {
				return there.x == otherPawn.here.x && there.y == here.y + forwardDirection; //return true if it's a forward diagonal move
			}

		}
		else if (pieceAtOtherSide instanceof Pawn && pieceAtOtherSide.isWhite != isWhite) {
			Pawn otherPawn = (Pawn) pieceAtOtherSide;
			if (otherPawn.movedTwo()) {
				return there.x == otherPawn.here.x && there.y == here.y + forwardDirection;
			}
		}
		return false;
	}
	
	public boolean movedTwo() {
		return movedTwo;
	}
	
	@Override
	/*
	 * @see ChessPiece#move(Space, Board)
	 * Pawns use their own move method to allow for the en passant move that the original move method 
	 * cannot do.
	 */
	public ChessPiece move(Space there, Board b) {
		Space from = here;
		boolean canEnPassant = canEnPassant(there, b);
		ChessPiece captured = null;
		try {
			captured = super.move(there, b);
		}
		catch(IllegalArgumentException e) {
			throw e;
		}
		if (canEnPassant) {
			//disposes of the pawn that should be captured by this move
			Space capturedPawnSpace = b.spaceAt(there.x, there.y - forwardDirection);
			captured = capturedPawnSpace.pieceHere;
			captured.here = null;
			capturedPawnSpace.pieceHere = null;
		}
		int[] relativexy = from.relativeTo(here);
		movedTwo = relativexy[0] == 0 && relativexy[1] == forwardDirection * 2;
		return captured;
	}
}