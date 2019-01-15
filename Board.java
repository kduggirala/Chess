
public class Board {
	private Space[][] board;
	public Board() {
		board = new Space[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[j][i] = new Space(i, j, null);
			}
		}
	}
	public Space spaceAt(int x, int y) {
		if (x >= 0 && x < 8 && y >= 0 && y < 8) {
			return board[y][x];
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	public String toString() {
		String border = "  +----+----+----+----+----+----+----+----+";
		StringBuffer board = new StringBuffer();
		board.append("     a    b    c    d    e    f    g    h\n");
 		for (int i = 17; i > 0; i--) {
			if (i % 2 == 1) {
				board.append(border);
			}
			else {
				StringBuffer line = new StringBuffer(i / 2 + " ");
				int index = (i / 2) - 1;
				Space[] row = this.board[index];
				for (int j = 0; j < 8; j++) {	
					StringBuffer space = new StringBuffer("| ");
					if (row[j].pieceHere == null) {
						space.append("  ");
					}
					else {
						space.append(row[j].pieceHere.isWhite ? " ": "*");
						space.append(pieceToStr(row[j].pieceHere));
					}
					space.append(' ');
					line.append(space);
				}
				line.append("| ");
				line.append(i/2);
				board.append(line);
			}
			board.append("\n");
		}
		board.append("     a    b    c    d    e    f    g    h");
		return board.toString();
	}
	private String pieceToStr(ChessPiece piece) {
		switch (piece.getClass().getName()) {
		case "Pawn":
			return "p";
		case "Knight":
			return "N";
		case "Bishop":
			return "B";
		case "Rook":
			return "R";
		case "Queen":
			return "Q";
		}
		return "K";
	}
	public void putPiece(int x, int y, ChessPiece piece) {
		Space space = spaceAt(x, y);
		space.pieceHere = piece;
		piece.here = space;
	}
}
