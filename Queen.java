
public class Queen extends ChessPiece{
	public Queen(boolean isWhite) {
		this.isWhite = isWhite;
	}
	boolean canMove(Space there, Board b) {
		if (there.equals(here)) {
			return false;
		}
		if (there.pieceHere != null && there.pieceHere.isWhite == isWhite) {
			return false;
		}
		return isHorizontalMove(there, b) || isVerticalMove(there, b) || isDiagonalMove(there, b);
	}
	
}