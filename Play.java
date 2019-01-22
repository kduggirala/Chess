import java.util.Scanner;
public class Play {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to Two-Player Terminal Chess!\n");
		boolean isQuitting = false; // programs runs while this is true.
		while (!isQuitting) {
			intro();
			String input = in.nextLine();
			while (!input.equalsIgnoreCase("p")) {
				if (input.equals("?")) {
					displayControls();
				}
				else if (input.equalsIgnoreCase("r")) {
					displayRules();
				}
				else if (input.equalsIgnoreCase("s")) {
					displaySampleOptions();
					String boardChoice = in.nextLine();
					while (true) {	
						try {
							try {
								playSampleBoard(Integer.parseInt(boardChoice));
								break;
							}
							catch(IllegalArgumentException e) {
								System.out.println(e.getMessage());
							}
						}
						catch (NumberFormatException e) { //if a number was not input
							if (boardChoice.equalsIgnoreCase("q")) { //if user wanted to quit 
								break;
							}
							else {
								System.out.println("That is not an option. Try again.");
							}
						}
					}
				}
				else if (input.equalsIgnoreCase("q")) { //if the user wants to quit the program
					isQuitting = true;
					break;
				}
				else {
					System.out.println("That is not one of the options. Try again");
				}
				System.out.println();
				intro();
				input = in.nextLine();
			}
			boolean playingChess = !isQuitting;
			Game chessGame;
			while(playingChess) {
				chessGame = new Game();
				playChess(chessGame);	
				System.out.println("\nWould you like to play again? If yes, type \"y\". Otherwise enter anything else.");
				String choice = in.nextLine();
				playingChess = choice.equalsIgnoreCase("y");
				if (!playingChess) {
					System.out.println("Are you sure you want to quit? If yes, type \"y\". Otherwise enter anything else.");
					playingChess = !in.nextLine().equalsIgnoreCase("y");
				}
			}
		}
		in.close();
	}
	private static void playChess(Game chessGame) {
		Scanner in = new Scanner(System.in);
		boolean gameOver = false;
		while (!gameOver) {
			System.out.println(chessGame.displayBoard());
			boolean isWhiteTurn = chessGame.whoseTurn();
			if (chessGame.sideInCheck(isWhiteTurn)) {
				System.out.println("\t" + (isWhiteTurn ? "White turn" : "Black turn") + ": Check!");
			}
			else {
				System.out.println("\t\t" + (isWhiteTurn ? "White turn" : "Black turn"));
			}
			while(true) {
				String move = in.nextLine();
				try {
					chessGame.move(move); //tries to make piece move to requested location, displays error message if it cannot

					//If a pawn can promote, it prompts the user to pick a choice
					Pawn pawn = chessGame.findPromotablePawn();
					if (pawn != null) {
						System.out.println("Would you like this pawn to become a Queen, Knight, Rook, or Bishop? Type your answer below.");
						while (true) {
							String choice = in.nextLine();
							try {
								ChessPiece newPiece = getNewPiece(choice, pawn.isWhite);
								chessGame.promote(pawn, newPiece);
								break;
							}
							catch(IllegalArgumentException e) {
								System.out.println(e.getMessage());
							}
						}
					}
					gameOver = (chessGame.checkmated() || chessGame.stalemated());
					break;
				}
				catch(IllegalArgumentException e) {
					if (move.equalsIgnoreCase("q")) {
						gameOver = true;
						break;
					}
					System.out.println(e.getMessage());
				}
			}
		}
		System.out.println(chessGame.displayBoard());
		String winningTeam = chessGame.whoseTurn() ? "Black" : "White";
		if (chessGame.checkmated()) {
			System.out.println("\t  Checkmate! " + winningTeam + " wins!");
		}
		else if (chessGame.stalemated()) {
			System.out.println("Stalemate! It's a draw!");
		}
		else {
			System.out.println((chessGame.whoseTurn() ? "White" : "Black") + " side forfeits. " + winningTeam + " side wins!");
		}
	}
	private static ChessPiece getNewPiece(String choice, boolean isWhite) {
		switch(choice) {
		case "Queen":
			return new Queen(isWhite);
		case "Knight":
			return new Knight(isWhite);
		case "Rook":
			return new Rook(isWhite);
		case "Bishop":
			return new Bishop(isWhite);
		default:
			throw new IllegalArgumentException("That is not an option. Try again.");
		}
	}
	private static void displayRules() {
		System.out.println("In chess, there are two; sides, the white side which starts the game and the black side on the other side of"); 
		System.out.println("the board. Each side consists of a set of 16 pieces. A piece can capture a piece on the other side by landing"); 
		System.out.println("on the space where it is, removing it from the board. If a piece on your side can capture the opposing king,");
		System.out.println("the opposing side is declared in check and must protect their king by moving it away from danger or stopping"); 
		System.out.println("your piece from capturing their king by blocking or capturing it. If the opposing king cannot move or be saved,");
		System.out.println("it is declared in checkmate, meaning you win. If there comes a situation where one side cannot make any legal ");
		System.out.println("moves, stalemate is declared and neither side wins.\n");

		System.out.println("The pieces:");
		System.out.println("1. King: The king can only move 1 space in any direction (forward, backwards, left, right, and diagonal");
		System.out.println("as long as it is not placed in danger or one of its own pieces is not in the way. 1 on the board.");
		System.out.println("2. Queen: The queen can move any number of spaces in any one direction as long as no piece on the");
		System.out.println("queen's side is in the way. 1 on the board.");
		System.out.println("3. Rook: The rook can move any number of spaces forwards, backwards, left, right as long as no pieces");
		System.out.println("on its side are in the way. 2 on the board.");
		System.out.println("4. Bishop: The bishop can move any number of spaces diagonally as long as no pieces on its side are on its");
		System.out.println("side. 2 on the board.");
		System.out.println("5. Knight: The knight can only move in an L-shape (2 spaces in one direction and 1 in the other). It can");
		System.out.println("jump over pieces and only needs to ensure that there is no piece on its side at the destination.");    
		System.out.println("6. Pawn: The pawn's move can be split into three cases.");
		System.out.println("	1) If it is the pawn's first move, it can move 1 or 2 spaces directly forward");
		System.out.println("	2) If there is an opposing piece at a forward diagonal 1 space from the pawn, it can capture the"); 
		System.out.println("       piece; it cannot capture pieces directly in front of it.");
		System.out.println("	3) Otherwise, it can only move one space in front of it.");
		System.out.println("If a pawn reaches the other side of the board, it has the choice of turning into a queen, rook, knight");
		System.out.println("or bishop. 8 on the board.\n");
		System.out.println("Special moves:");
		System.out.println("1. Castling: If neither the king nor one of the rooks have moved, and all spaces between them in the same");
		System.out.println("   row are empty, they can perform a special move called a castle. This involves the king moving two spaces");
		System.out.println("towards the castling side rook and the rook moves to 1 space next to the king on its other side in one ");
		System.out.println("move. This game allows castling out of or through check. See sample board 1 for demonstration.");
		System.out.println("2. En passant: If a pawn on one side just moved two spaces (on its first move) next to a pawn on the other");
		System.out.println("side, the pawn on the other side can, on the next move, move forward diagonally in front of the other");
		System.out.println("pawn and capture it, even if there is no piece at the space it is moving to. See sample board 2.");
	}
	private static void displayControls() {
		System.out.println("Use the chess coordinates along the side of the board to locate spaces: first the letter then number as one word.");
		System.out.println("First enter the space at which the piece you want to move is located, then the space to which you would like it to"); 
		System.out.println("move with a space between the two. Press \"q\" at any time to exit a game. You can test these controls on the sample boards.\n");
	}
	private static void intro() {
		System.out.println("To see the rules, press \"r\".\nTo see the controls, press \"?\".\nTo see the sample boards, press\"s\".\nTo quit and kill the program, press \"q\".\nTo play, press \"p\"");
		System.out.println();
	}
	private static Board sampleBoard1() {
		Board sampleBoard = new Board();
		sampleBoard.putPiece(0, 0, new Rook(true));
		sampleBoard.putPiece(7, 0, new Rook(true));
		sampleBoard.putPiece(1, 0, new Knight(true));
		sampleBoard.putPiece(5, 2, new Knight(true));
		sampleBoard.putPiece(2, 0, new Bishop(true));
		sampleBoard.putPiece(6, 1, new Bishop(true));
		sampleBoard.putPiece(3, 0, new Queen(true));
		sampleBoard.putPiece(4, 0, new King(true));
		sampleBoard.putPiece(0, 1, new Pawn(true));
		sampleBoard.putPiece(1, 1, new Pawn(true));
		sampleBoard.putPiece(2, 1, new Pawn(true));
		sampleBoard.putPiece(3, 1, new Pawn(true));
		sampleBoard.putPiece(4, 1, new Pawn(true));
		sampleBoard.putPiece(5, 1, new Pawn(true));
		sampleBoard.putPiece(6, 2, new Pawn(true));
		sampleBoard.putPiece(7, 1, new Pawn(true));
		
		sampleBoard.putPiece(0, 7, new Rook(false));
		sampleBoard.putPiece(7, 7, new Rook(false));
		sampleBoard.putPiece(0, 5, new Knight(false));
		sampleBoard.putPiece(6, 7, new Knight(false));
		sampleBoard.putPiece(1, 6, new Bishop(false));
		sampleBoard.putPiece(5, 7, new Bishop(false));
		sampleBoard.putPiece(2, 6, new Queen(false));
		sampleBoard.putPiece(4, 7, new King(false));
		sampleBoard.putPiece(0, 6, new Pawn(false));
		sampleBoard.putPiece(1, 5, new Pawn(false));
		sampleBoard.putPiece(2, 5, new Pawn(false));
		sampleBoard.putPiece(3, 6, new Pawn(false));
		sampleBoard.putPiece(4, 6, new Pawn(false));
		sampleBoard.putPiece(5, 6, new Pawn(false));
		sampleBoard.putPiece(6, 6, new Pawn(false));
		sampleBoard.putPiece(7, 6, new Pawn(false));
		return sampleBoard;
	}
	private static Board sampleBoard2() {
		Board sampleBoard = new Board();
		sampleBoard.putPiece(4, 1, new Pawn(true));
		sampleBoard.putPiece(4, 0, new King(true));
		sampleBoard.putPiece(3, 3, new Pawn(false));
		sampleBoard.putPiece(4, 7, new King(false));
		return sampleBoard;
	}
	public static void displaySampleOptions() {
		System.out.println("Welcome to the sample boards. Type in the number of the sample board you would like to play with or press \"q\" to quit:");
		System.out.println("Board 1: Castling Demonstration");
		System.out.println("Board 2: En passant Demonstration");
	}
	public static void playSampleBoard(int boardChoice) {
		Board sampleBoard;
		switch(boardChoice) {
		case 1:
			sampleBoard = sampleBoard1();
			System.out.println("\nHere, the white side can castle by moving its king from e1 to g1 towards the king side");
			System.out.println("rook while the black side can castle moving its king from e8 to c8 towards the queen side.\nTry it yourself!\n");
			break;
		case 2:
			sampleBoard = sampleBoard2();
			System.out.println("\nHere the white pawn at e2 can move forward two spaces to e4, allowing the black pawn at d4 to");
			System.out.println("capture it using en passant by moving to e3. Try it yourself! ");
			break;
		default:
			throw new IllegalArgumentException("That is not an option");
		}
		Game playSampleBoard = new Game(sampleBoard);
		playChess(playSampleBoard);

	}
	
}