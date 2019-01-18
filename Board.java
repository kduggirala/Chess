
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
	/* Returns the space on the board at the x,y  location requested: where spaceAt(0, 0) returns the space
	 * at the bottom left corner and spaceAt(7, 7) at the top-right
	 * Precondition: 0 <= x <= 7, 0 <= y <= 7 on the board to return a space within the 8 x 8 board. If not,
	 * IllegalArgumentException thrown
	 */
	public Space spaceAt(int x, int y){
		if (x >= 0 && x < 8 && y >= 0 && y < 8) {
			return board[y][x];
		}
		else {
			throw new IllegalArgumentException("That space is not on the board");
		}
	}
	//Returns a visual representation of the board using the aesthetic of the Sjeng chess engine
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
	//Returns a one character representation of the ChessPiece for use by the toString method
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
	/**
	 * Places a chess piece on the board
	 * @param x x-coordinate of space on which the piece should be placed
	 * @param y y-coordinate of the space on which the piece should be placed
	 * Precondition: @see spaceAt
	 * @param piece piece to be placed on the board
	 */
	public void putPiece(int x, int y, ChessPiece piece) {
		Space space = null;
		try {	
			space = spaceAt(x, y);
		}
		catch(IllegalArgumentException e) {
			throw e;
		}
		space.pieceHere = piece;
		piece.here = space;
	}
}
