
public abstract class ChessPiece {
	Space here;
	boolean isWhite;
	abstract boolean canMove(Space there, Board b);
	/**
	 * Moves the piece on the board to the given space
	 * Precondition: the move is a legal move for this piece, if not IllegalArgumentException thrown
	 * @param there space the piece wishes to move to
	 * @param b the board one which it moves, important for determining whether it can move at all
	 * @return the captured piece if a piece captured, otherwise null
	 */
	public ChessPiece move(Space there, Board b) {
		if (canMove(there, b)) {
			here.pieceHere = null;
			ChessPiece pieceThere = there.pieceHere;
			there.pieceHere = this;
			here = there;
			if (pieceThere != null) {
				pieceThere.here = null;//removes the dead piece from the board
			}
			return pieceThere; //return captured piece if one was captured
		}
		else {
			throw new IllegalArgumentException("This piece can't move there.");
		}
	}
	/*
	 * Since (almost) every chess move is a diagonal, horizontal, or vertical move, every chess piece 
	 * inherits these methods and uses them to determine whether it can move to the given space
	 */
	protected boolean isHorizontalMove(Space there, Board b) {
		int[] relative = here.relativeTo(there);
		if (relative[1] == 0) { //No vertical movement
			Space cur;
			for (int i = 1; i < Math.abs(relative[0]); i++) {
				cur = relative[0] > 0 ? b.spaceAt(here.x + i, here.y) : b.spaceAt(here.x - i, here.y);
				if (cur.pieceHere != null) { //if there's a piece between the start and end location
					return false;
				}
			}
			return true;

		}
		return false;
	}
	protected boolean isVerticalMove(Space there, Board b) {
		int[] relative = here.relativeTo(there);
		if (relative[0] == 0) { //No horizontal movement
			Space cur;
			for (int i = 1; i < Math.abs(relative[1]); i++) {
				cur = relative[1] > 0 ? b.spaceAt(here.x, here.y + i) : b.spaceAt(here.x, here.y - i);
				if (cur.pieceHere != null) { //if there's a piece between the start and end locations
					return false;
				}
			}
			return true;

		}
		return false;
	}
	protected boolean isDiagonalMove(Space there, Board b) {
		int[] relative = here.relativeTo(there);
		if (Math.abs(relative[0]) == Math.abs(relative[1])) {
			Space cur;
			int dx = relative[0] > 0 ? 1 : -1; //Whether moving left or right
			int dy = relative[1] > 0 ? 1 : -1; //Whether moving forward or backwards
			for (int i = 1; i < Math.abs(relative[0]); i++) {
				cur = b.spaceAt(here.x + (dx * i), here.y + (dy * i));
				if (cur.pieceHere != null) { //if there's a piece between starting and ending locations
					return false;
				}
			}
			return true;
		}
		return false;
	}
}