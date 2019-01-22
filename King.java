
public class King extends ChessPiece {
	boolean hasMoved;
	public King(boolean isWhite) {
		this.isWhite = isWhite;
		hasMoved = false;
	}
	public boolean canMove(Space there, Board b) {
		if (there.pieceHere != null && there.pieceHere.isWhite == isWhite) {
			return false;
		}
		if (there.equals(here)) {
			return false;
		}
		if (canCastle(there, b)) {
			return true;
		}
		int[] relative = here.relativeTo(there);
		for (int i = 0; i < 2; i++) {
			relative[i] = Math.abs(relative[i]);
		}
		if (relative[0] > 1 || relative[1] > 1) {
			return false;
		}

		return true;
	}
	private boolean canCastle(Space there, Board b) {
		int[] r = here.relativeTo(there);
		if (!hasMoved) {
			if (r[0] == 2) {
				Space rookSpace = isWhite? b.spaceAt(7, 0) : b.spaceAt(7,7);
				ChessPiece rook = rookSpace.pieceHere;
				if (rook instanceof Rook && !((Rook) rook).hasMoved) {
					return isHorizontalMove(there, b);
				}
			}
			else if (r[0] == -2) {
				Space rookSpace = isWhite? b.spaceAt(0, 0) : b.spaceAt(0,7);
				ChessPiece rook = rookSpace.pieceHere;
				if (rook instanceof Rook && !((Rook) rook).hasMoved) {
					return isHorizontalMove(there, b);
				}
			}
		}
		return false;
	}

	@Override
	/*
	 * @see ChessPiece#move(Space, Board)
	 * Overrides the original move method to allow for the castling move between the king and the rook
	 */
	public ChessPiece move(Space there, Board b) {
		ChessPiece captured = null;
		Space from = here;
		boolean canCastle = canCastle(there, b);
		try {
			captured = super.move(there, b);
			hasMoved = true;
		}
		catch(IllegalArgumentException e){
			throw e;
		}
		if (canCastle) {
			//Moves the rook to the required location.
			int[] r = from.relativeTo(there);
			Space rookspace = r[0] == 2 ? (isWhite ? b.spaceAt(7, 0): b.spaceAt(7, 7)) : (isWhite ? b.spaceAt(0,  0) : b.spaceAt(0, 7));
			ChessPiece rook = rookspace.pieceHere;
			rookspace.pieceHere = null;
			Space newRookspace = b.spaceAt(rookspace.x == 0 ? 3 : 5, rookspace.y);
			rook.here = newRookspace; 
			newRookspace.pieceHere = rook;
		}
		return captured;
	}
}