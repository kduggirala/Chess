import java.util.ArrayList;
public class Game {
	private Board board;
	private boolean isWhiteTurn;
	private ArrayList<ChessPiece> whitePieces;
	private ArrayList<ChessPiece> blackPieces;
	private King whiteKing;
	private King blackKing;
	
	public Game() {
		board = new Board();
		isWhiteTurn = true;
		whitePieces = new ArrayList<ChessPiece>();
		blackPieces = new ArrayList<ChessPiece>();
		setupDefaultGame();
	}
	//starting a game from a pre-given board
	public Game(Board b) {
		if (b == null) {
			new Game();
		}
		else {
			board = b;
			isWhiteTurn = true;
			whitePieces = new ArrayList<ChessPiece>();
			blackPieces = new ArrayList<ChessPiece>();
			Space cur;

			//add pieces from the board to the ArrayLists
			for (int i = 0; i < 8; i++) { 
				for (int j = 0; j < 8; j++) {
					cur = b.spaceAt(i, j);
					if (cur.pieceHere != null) {
						ChessPiece thisPiece = cur.pieceHere;
						if (thisPiece instanceof King) {
							if (thisPiece.isWhite) {
								whiteKing = (King) thisPiece;
							}
							else {
								blackKing = (King) thisPiece;
							}
						}
						if (thisPiece.isWhite) {
							whitePieces.add(thisPiece);
						}
						else {
							blackPieces.add(thisPiece);
						}
					}
				}
			}
			
		}
	}
	public void move(String input) {
		input = input.trim().toLowerCase();
		
		if (input.length() != 5) {
			throw new IllegalArgumentException("Invaild input");
		}
		input = input.substring(0, 2) + input.substring(3);
		char[] moves = input.toCharArray();
		//converting Chess coordinates to Board coordinates
		int[] formatted = new int[4]; 
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				switch(moves[i]) {
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
					formatted[i] = (int)moves[i] - 97;
					break;
				default:
					throw new IllegalArgumentException("Invalid input");
				}
			}
			else {
				switch(moves[i]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
					formatted[i] = (int)moves[i] - 49;
					break;
				default:
					throw new IllegalArgumentException("Invalid input");
				}
			}
		}

		Space from = board.spaceAt(formatted[0], formatted[1]);
		Space to = board.spaceAt(formatted[2], formatted[3]);
		ChessPiece piece = from.pieceHere;
		if (piece == null) {
			throw new IllegalArgumentException("There's no piece at the starting location");
		}
		if (piece.isWhite != isWhiteTurn) {
			throw new IllegalArgumentException("Choose one of your pieces");
		}
		ChessPiece captured = null;
		Space capturedSpace = to;
		try {
			captured = piece.move(to, board);
		}
		catch(IllegalArgumentException e) {
			throw e;
		}

		//if the king is in check after a move, the move is undone 
		if (inCheck(isWhiteTurn ? whiteKing : blackKing )) {
			piece.here = from;
			from.pieceHere = piece;
			capturedSpace.pieceHere = captured;
			if (captured != null) {	
				captured.here = capturedSpace;
			}
			throw new IllegalArgumentException("You cannot endanger the king!");
		}
		takeOutYourDead();
		isWhiteTurn = !isWhiteTurn;
	}

	//Returns whether the requested side is in check: for client program's use since clients cannot directly access the kings
	public boolean sideInCheck(boolean isWhiteSide) {
		King king = isWhiteSide ? whiteKing : blackKing;
		return inCheck(king);
	}

	//Returns whether the given King is in check: for use by only this program
	private boolean inCheck(King king) {
		Space kingAt = king.here;
		ArrayList<ChessPiece> otherSide = king.isWhite ? blackPieces : whitePieces;
		for (int i = 0; i < otherSide.size(); i++) {
			try {	
				if (otherSide.get(i).canMove(kingAt, board)) {
					return true;
				}
			}
			/*
			 * A null pointer exception would occur if a piece has been captured and the program tries to find if it checks the king. 
			 * Since we want to consider whether the king would be in check in the scenario where the piece is captured, 
			 *  and captured pieces cannot check the king, the null pointer exception is forgiven 
			 */
			catch (NullPointerException e) { 
				continue;
			}
		}
		return false;
	}
	private void takeOutYourDead() {
		ArrayList<ChessPiece> pieces = isWhiteTurn ? blackPieces : whitePieces; 
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).here == null) {
				pieces.remove(i);
			}
		}
	}
	public void setupDefaultGame() {
		whitePieces.add(new Rook(true));
		whitePieces.add(new Knight(true));
		whitePieces.add(new Bishop(true));
		whitePieces.add(new Queen(true));
		whitePieces.add(new King(true));
		whiteKing = (King) whitePieces.get(4);
		whitePieces.add(new Bishop(true));
		whitePieces.add(new Knight(true));
		whitePieces.add(new Rook(true));
		for (int i = 0; i < 8; i++) {
			whitePieces.add(new Pawn(true));
		}

		blackPieces.add(new Rook(false));
		blackPieces.add(new Knight(false));
		blackPieces.add(new Bishop(false));
		blackPieces.add(new Queen(false));
		blackPieces.add(new King(false));
		blackKing = (King) blackPieces.get(4);
		blackPieces.add(new Bishop(false));
		blackPieces.add(new Knight(false));
		blackPieces.add(new Rook(false));
		for (int i = 0; i < 8; i++) {
			blackPieces.add(new Pawn(false));
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				board.putPiece(j, i, whitePieces.get(j + (8 * i)));
				board.putPiece(j, 7-i, blackPieces.get(j + (8 * i)));
			}
		}
	}
	public void resetGame() {
		board = new Board();
		isWhiteTurn = true;
		whitePieces = new ArrayList<ChessPiece>();
		blackPieces = new ArrayList<ChessPiece>();
	}
	public String displayBoard() {
		String boardString = board.toString();
		if (!isWhiteTurn) {	//if black turn, this flips the board 	
			String[] boardLines = boardString.split("\n");
			StringBuffer newBoard = new StringBuffer();
			newBoard.append("     h    g    f    e    d    c    b    a\n");
			for (int i = boardLines.length - 2; i > 0; i--) {
				if (i % 2 == 1) {
					newBoard.append("  ");
				}
				char[] boardLine = boardLines[i].toCharArray();
				for (int j = boardLine.length - 1; j >= 0; j--) {
					if (Character.isAlphabetic(boardLine[j])) {
						newBoard.append(boardLine[j-1]);
						newBoard.append(boardLine[j]);
						j--;
					}
					else {
						newBoard.append(boardLine[j]);
					}
				}
				newBoard.append("\n");
			}
			newBoard.append("     h    g    f    e    d    c    b    a");
			boardString = newBoard.toString();
		}
		return boardString;
	}
	private boolean stuck() {
		King king = isWhiteTurn ? whiteKing : blackKing;
		Space here = king.here;
		Space cur;
		boolean stuck = true;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				try {
					cur = board.spaceAt(here.x + i, here.y + j);
				}
				catch(IllegalArgumentException e) {
					continue;
				}
				if (king.canMove(cur, board)) {
					Space kingAt = king.here;
					ChessPiece pieceThere = cur.pieceHere;
					//king moves to new spot and checks if he's in check there, then undoes his move
					king.move(cur, board);
					boolean wasInCheck = inCheck(king);
					
					cur.pieceHere = pieceThere;
					kingAt.pieceHere = king;
					king.here = kingAt;
					if(pieceThere != null) {
						pieceThere.here = cur;
					}
					
					if(!wasInCheck) {
						stuck = false;
						break;
					}
				}
			}
		}
		return stuck;
	}
	public boolean checkmated() {
		King king = isWhiteTurn ? whiteKing : blackKing;
		ArrayList<ChessPiece> pieces = isWhiteTurn ? whitePieces : blackPieces;
		if (inCheck(king) && stuck()) {
			/* if the king is checked and cannot move, the program checks if any move can be made
			 * to stop the king being in check
			 */
			Space cur;
			for(ChessPiece p: pieces) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						cur = board.spaceAt(i, j); 
						if (p.canMove(cur, board)) {
							Space pieceAt = p.here;
							ChessPiece pieceThere = p.move(cur, board);
							boolean wasInCheck = inCheck(king);
							//resets the pieces to their original positions

							cur.pieceHere = pieceThere;
							pieceAt.pieceHere = p;
							p.here = pieceAt;
							if(pieceThere != null) {
								pieceThere.here = cur;
							}
							
							if(!wasInCheck) { //if there is a safe move the king, returns false
								return false;
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	public boolean whoseTurn() {
		return isWhiteTurn;
	}
	public boolean stalemated() {
		if (whitePieces.size() == 1 && blackPieces.size() == 1) { //if only the kings are left
			return true;
		}
		boolean stalemated = true;
		ArrayList<ChessPiece> pieces = isWhiteTurn ? whitePieces : blackPieces;
		for(ChessPiece p: pieces) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (p.canMove(board.spaceAt(i, j), board)) {
						stalemated = false;
						break;
					}
				}
			}
		}
		
		return stalemated;
	}
	/*Finds whether any of the pawns on the current player's side can promote into a Bishop, Rook, Knight
	 * or Queen. If so, returns this pawn. Otherwise, returns null.
	 */
	public Pawn findPromotablePawn() {
		ArrayList<ChessPiece> pieces = isWhiteTurn ? blackPieces : whitePieces;
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i) instanceof Pawn) {
				Pawn pawn = (Pawn) pieces.get(i);
				if (pawn.here.y == (pawn.isWhite ? 7 : 0)) {
					return pawn;
				}
			}
		}
		return null;
	}
	//Removes pawn from the board and replaces it with the promoted unit
	public void promote(Pawn pawn, ChessPiece newPiece) {
		Space pawnAt = pawn.here;
		ArrayList<ChessPiece> pieces = pawn.isWhite ? whitePieces : blackPieces;
		pieces.remove(pawn);
		pawnAt.pieceHere = newPiece;
		newPiece.here = pawnAt;
		pieces.add(newPiece);
	}
}
