
public class Knight extends ChessPiece {
	public Knight(boolean isWhite) {
		this.isWhite = isWhite;
	}
	public boolean canMove(Space there, Board b) {
		int[] r = here.relativeTo(there);
		for (int i = 0; i < 2; i++) {
			r[i] = Math.abs(r[i]);
		}
		ChessPiece pieceThere = there.pieceHere;
		if (pieceThere == null || pieceThere.isWhite != isWhite) {
			return ((r[0] == 2 && r[1] == 1) ||( r[0] == 1 && r[1] == 2)); //if L-shape movement
		}
		return false;
	}
}
