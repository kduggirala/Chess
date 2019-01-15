
public class Rook extends ChessPiece{
	boolean hasMoved;
	public Rook(boolean isWhite) {
		this.isWhite = isWhite;
	}
	public boolean canMove(Space there, Board b) {
		if (there.pieceHere != null && there.pieceHere.isWhite == isWhite) {
			return false;
		}
		if (there.equals(here)) {
			return false;
		}
		return (isHorizontalMove(there, b) || isVerticalMove(there, b));
	}
	public ChessPiece move(Space there, Board b) {
		ChessPiece captured;
		try {
			captured = super.move(there, b);
			hasMoved = true;
		}
		catch(IllegalArgumentException e) {
			throw e;
		}
		return captured;
	}
}