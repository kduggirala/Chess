
public class Space {
	ChessPiece pieceHere;
	final int x;
	final int y;
	public Space(int x, int y, ChessPiece p) {
		this.x = x;
		this.y = y;
		pieceHere = p;
	}
	public boolean equals(Object o) {
		if (o instanceof Space) {
			Space s = (Space)o;
			return (s.x == x && s.y == y);
		}
		return false;
	}
	public int[] relativeTo(Space there) {
		int[] relative = {there.x - x, there.y - y};
		return relative;
	}
}
