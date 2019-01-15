import java.util.Scanner;
public class Play {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Game chessGame = new Game();
		System.out.println("Welcome to Chess!\n");
		intro();
		String input = in.nextLine();
		while (!input.equalsIgnoreCase("p")) {
			if (input.equals("?")) {
				displayControls();
			}
			else if (input.equalsIgnoreCase("r")) {
				displayRules();
			}
			else {
				System.out.println("That is not one of the options. Try again");
			}
			intro();
			input = in.nextLine();
		}
		while (!(chessGame.checkmated() || chessGame.stalemated())) {
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
					
					break;
					
				}
				catch(IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		System.out.println(chessGame.displayBoard());
		System.out.println("Checkmate!" + (chessGame.whoseTurn() ? "Black" : "White") + " wins!");
		in.close();
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
		String output = "In chess, there are two sides, the white side and the black side (pieces denoted with an asterisk). Each side starts with 16 pieces: 8 pawns, who can only move forward one space (except on their first move when they can move two spaces forward) and can only capture enemy pieces in a diagonal-forwards movement, 2 bishops who can move diagonally in any direction, 2 rooks who can move forwards, backwards, left or right, 1 queen who can move in any direction, and 1 king who can move one space in all direction. The king can also \"castle\" if it is ";
		System.out.println(output);
	}
	private static void displayControls() {
		System.out.println("Use the chess coordinates along the side of the board to locate spaces: first the letter then number as one word.");
		System.out.println("First enter the space of the piece you want to move, then the space to which you would like it to"); 
		System.out.println("move with a space between the two. Black pieces are denoted with a star.\n\n");
	}
	private static void intro() {
		System.out.println("To see the rules, press \"r\".\nTo see the controls, press \"?\".\nTo play, press \"p\"");
		System.out.println();
	}
	public static Board sampleBoards(int n) {
		switch(n) {
		case 1:
			return sampleBoard1();
		default:
			throw new IllegalArgumentException("That is not an option");
		}
	}
	private static Board sampleBoard1() {
		Board sampleBoard = new Board();
		sampleBoard.putPiece(, y, new Pawn(true));
		return sampleBoard;
	}
}