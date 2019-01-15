
public class Bishop extends ChessPiece{
	public Bishop(boolean isWhite) {
		this.isWhite = isWhite;
	}
	public boolean canMove(Space there, Board b) {
		if (there.equals(here)) {
			return false;
		}
		if (there.pieceHere != null && there.pieceHere.isWhite == isWhite) {
			return false;
		}
		return isDiagonalMove(there, b);
	}
	
}
