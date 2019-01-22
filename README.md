Description:
	This is two-player Chess run from the terminal or command line. It uses the Sjeng chess 
	engine aesthetic to display the board and uses a similar input scheme to move the pieces. 
	 
Compilation and Running Instructions: 
	Save the files to a directory on your computer, navigate to this directory in the 
	terminal and use the javac compiler on Play.java and run it. Then follow the 
	instructions that appear onscreen to use the program.
 
 Development Log:
 	12/22 - Created ChessPiece interface, changed to abstract class with abstract method canMove() to allow pieces to implement independent movement logic, started implementing Pawn movement.
		   
 	12/23 - Scrapped original code, re-implemented similar code for ChessPiece but added new Space class to describe spaces on the board.
	
 	12/29 - Created Board class as one-dimensional array of Spaces, started implementing toString() method for board and started implementing Pawn and Knight classes.
	
 	1/1 - Started King class, started thinking of ways to implement check for Kings, thought of another abstract class for pieces that aren't Kings - scrapped this idea quickly.
	
 	1/2 -  Started Rook, Queen, and Bishop classes - realized I needed ChessPiece methods for detecting vertical, horizontal, and diagonal moves and that the pieces needed a board to determine if they could move.
	
 	1/3 - Continued work from 1/2 - decided to make method relativeTo(Space there) for spaces to have a convenient place to keep their relative x and y distances. Also created spaceAt method for the board to have a place to return specific places on the board.
 	
	1/4 - Created board with all the pieces on it and tested toString() method - got many NullPointerExceptions - started debugging code to find the exceptions.
	
 	1/7 - Continued debugging to fix NullPointerExceptions - decided to switch one-dimensional array implementation of Board to two-dimensional array.
	
 	1/8 - Fixed existing bugs, found logic error regarding conversion from board xy-coordinates to two-dimensional array implementationand other such movement errors with logic - began implementing check logic in new Game class.
	
 	1/9 - Made move(String input) method for Game class to start making the bridge between the program and the user. This implements check logic - gave NullPointerExceptions when run.
	
 	1/10 - Fixed all existing bugs, starting making Play class to develop UI.
 	
	1/11 - Made rudimentary UI to test program, continued debugging and developing checkmate logic.  
 	
	1/14 - Started adding castling to the game, continued testing and debugging movement logic.
 	
	1/15 - Continued fixing checkmate, stalemate, check, and castling logic (pieces disappeared after moves), continued developing UI.
 	1/16 - Added pawn promotion and started adding en passant. 
	
 	1/17 - See 1/15. Started adding comments to aid readability.
	
 	1/18 - Started adding sample boards, changed implementation of en passant and castling to more sensibly fit into the class hierarchy (moved it from Game class to Pawn and King class respectively).
	
 	1/21 - Finished by adding sample boards and fixing the UI and comments.
