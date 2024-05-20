/*
 * Chess game using StdDraw
 * 
 * by Jari Polm
 * Summer Break 2021
 */

import java.awt.Color;
import java.util.Arrays;

public class Driver {
	
	/*
	 * Static variables
	 */
	private static String turn = "white"; // representing which colors turn it is
	private static Figure[][] figures = new Figure[8][8]; // 2-D array for the figures representing each cell for a field on the board
	private static int winner; // keeping track if result is a draw, white or black winner
	
	// kings of each team
	private static King king_black = new King("black", 4, 7);
	private static King king_white = new King("white", 4, 0);
	
	
	//-----------------------------------------------------------------------------------------------------------------------
	
	/*
	 * Set up the figures in the 2-D array and arraylists for white and black
	 */
	private static void setUpFigures() {
		
		// black set up
		figures[4][7] = king_black;
		figures[3][7] = new Queen("black", 3, 7);
		figures[0][7] = new Rook("black", 0, 7);
		figures[7][7] = new Rook("black", 7, 7);
		figures[2][7] = new Bishop("black", 2, 7);
		figures[5][7] = new Bishop("black", 5, 7);
		figures[1][7] = new Knight("black", 1, 7);
		figures[6][7] = new Knight("black", 6, 7);
		
		figures[0][6] = new Pawns("black", 0, 6);
		figures[1][6] = new Pawns("black", 1, 6);
		figures[2][6] = new Pawns("black", 2, 6);
		figures[3][6] = new Pawns("black", 3, 6);
		figures[4][6] = new Pawns("black", 4, 6);
		figures[5][6] = new Pawns("black", 5, 6);
		figures[6][6] = new Pawns("black", 6, 6);
		figures[7][6] = new Pawns("black", 7, 6);
		
		// white set up
		figures[4][0] = king_white;
		figures[3][0] = new Queen("white", 3, 0);
		figures[0][0] = new Rook("white", 0, 0);
		figures[7][0] = new Rook("white", 7, 0);
		figures[2][0] = new Bishop("white", 2, 0);
		figures[5][0] = new Bishop("white", 5, 0);
		figures[1][0] = new Knight("white", 1, 0);
		figures[6][0] = new Knight("white", 6, 0);
		
		figures[0][1] = new Pawns("white", 0, 1);
		figures[1][1] = new Pawns("white", 1, 1);
		figures[2][1] = new Pawns("white", 2, 1);
		figures[3][1] = new Pawns("white", 3, 1);
		figures[4][1] = new Pawns("white", 4, 1);
		figures[5][1] = new Pawns("white", 5, 1);
		figures[6][1] = new Pawns("white", 6, 1);
		figures[7][1] = new Pawns("white", 7, 1);
	}
	
	
	
	/*
	 * Draw the field and all active figures
	 */
	private static void drawField() {
		
		StdDraw.clear(StdDraw.DARK_GRAY); // background color
		StdDraw.setScale(0, 8); // set the scale in a way that each field has height and width of 1
		
		boolean switch_color = false; // switch between white and black to create a chess board
		
		// loop over each row of the chess board
		for (int i = 0; i < 8; i++) {
			
			if (switch_color) {
				switch_color = false;
			} else {
				switch_color = true;
			}
			
			// loop over each column of chess board
			for (int j = 0; j < 8; j++) {
				if (switch_color) {
					StdDraw.setPenColor(StdDraw.LIGHT_SQUARES);
					switch_color = false;
				} else {
					switch_color = true;
					StdDraw.setPenColor(StdDraw.DARK_SQUARES);
				}
				StdDraw.filledSquare(j + 0.5, i + 0.5, 0.5);
			}
		}

		
		// loop over all rows of the board
		for (int row = 0; row <= 7; row++) {
			
			// loop over all columns of the board
			for (int col = 0; col <= 7; col++) {
				
				// draw all figures on the board
				if (figures[row][col] != null) {
					figures[row][col].draw();
				}
			}
		}
	}

	
	/*
	 * Test if coordinates are inside the field
	 */
	private static boolean inField(int x, int y) {
		if (x <= 7 && x >= 0 && y <= 7 & y >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Move a figure while dragged by the mouse 
	 */
	private static void movingActiveFigure(Figure activeFigure) {
		while (StdDraw.mousePressed()) {
			activeFigure.move(StdDraw.mouseX(), StdDraw.mouseY());	
			drawField();
			StdDraw.show(20);
		}
	}
	
	/*
	 * validate that a figure can move from its position to a new position
	 */
	private static boolean validateEndPos(int x_start, int y_start, int x_end, int y_end, Figure activeFigure) {
		
		// move cannot be made if a figure of the same team is already on the field
		// the active figure wants to move to
		if (figures[x_end][y_end] != null) {
			if (figures[x_start][y_start].getColor().equals(figures[x_end][y_end].getColor())) {
				return false;
			}
		}
		
		// check if the figure can move to its new requested position ...
		// 1. by its rules
		if (activeFigure.canAttackField(x_start, y_start, x_end, y_end, figures)) {
			// 2. without leaving its own king open to check
			return theoryMove(x_start, y_start, x_end, y_end);
		} else {
			return false;
		}
	}

	/*
	 * return true if a move can be made without leaving the own king in check
	 */
	public static boolean theoryMove(int x_start, int y_start, int x_end, int y_end) {
		Figure[][] copy = Arrays.stream(figures).map(Figure[]::clone).toArray(Figure[][]::new);
		
		
		String color = copy[x_start][y_start].getColor();
		
		copy[x_end][y_end] = figures[x_start][y_start];
		copy[x_start][y_start] = null;
		
		figures[x_start][y_start].move(x_end, y_end);
				
		if (color.equals("white")) {
			if (!king_white.squareIsAttacked(copy, king_white.getX(), king_white.getY())) {
				figures[x_start][y_start].move(x_start, y_start);
				return true;
			} 
		} else {
			if (!king_black.squareIsAttacked(copy, king_black.getX(), king_black.getY())) {
				figures[x_start][y_start].move(x_start, y_start);
				return true;
			}
		}
		
		figures[x_start][y_start].move(x_start, y_start);	
		return false;
	}
	
	

	
	// ----------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Main 
	 */
	public static void main(String[] args) {
		
		// set up the figures before the game starts
		setUpFigures();
		
		// a Linked List is used to keep track of all appearing positions on the board
		// if the same position appears three times in the same game, the game is called a draw
		NodePointer head = new NodePointer(figures);
		LinkedList list = new LinkedList(head);
		
		boolean playing = true; // boolean used to determine if the game has ended
		
		drawField(); // draw the field one time
		
		// while loop until the game has ended
		while(playing) {
			
			
			
			
			// check if the mouse is pressed
			if (StdDraw.mousePressed()) {
				
				
				// get the coordinate of the mouse in case a move is about to made
				int x_start = (int) StdDraw.mouseX();
				int y_start = (int) StdDraw.mouseY();
				
				// check if a move can be made with the coordinates the mouse is on when pressed
				// that is including, is the mouse on the board, is it on a figure and has the figure the correct color
				if (inField(x_start, y_start) && figures[x_start][y_start] != null && figures[x_start][y_start].getColor().equals(turn)) {
					
					// get the active figure using the coordinates and the 2D-Figure array
					Figure activeFigure = figures[x_start][y_start];
					
					// move the active figure along the field while being dragged with the mouse
					// this does not validate any move yet
					// method ends when the mouse is not being pressed anymore!!!!!
					movingActiveFigure(activeFigure);
					
					// get the coordinates where the player requests to 
					// move his/her figure
					int x_end = (int) StdDraw.mouseX();
					int y_end = (int) StdDraw.mouseY();
				
					// validate the end position
					// including is end position on board, can figure move there and does own king not end up in check
					if (inField(x_end, y_end) && validateEndPos(x_start, y_start, x_end, y_end, activeFigure)) {
						
						// Pawns can make a special move 
						// if a Pawn was not moved yet, it can move 2 steps ahead if both fields in front of it
						// are empty
						if (activeFigure instanceof Pawns && (((Pawns)activeFigure).doublePawn(x_start, y_start, x_end, y_end, figures))) {
							System.out.println("Pawn moved two steps");
						} else {
							Pawns.resetDoublePawn();
						}
												
						// move the figure to its new place
						activeFigure.move(x_end, y_end);
						
						
						// update that the old spot of the figure is now empty
						figures[x_start][y_start] = null;
						
						
						// check the special case of a pawn turning into a queen
						// when reaching the end of the board
						if ((y_end == 7 || y_end == 0) && activeFigure instanceof Pawns) {
							// when y_end == 0 black achieved making the pawn into a queen
							if (y_end == 0) {
								Queen q = new Queen("black", x_end, y_end);
								figures[x_end][y_end] = q;
							} else {
								Queen q = new Queen("white", x_end, y_end);
								figures[x_end][y_end] = q;
							}
						} else {

							// update the figures position in the array
							figures[x_end][y_end] = activeFigure;
						}
						
						// set king to moved to disable castling 
						if (activeFigure instanceof King) {
							((King)activeFigure).setMoved(true);
						} 
						
		
	
						
						
						// confirm if check for the opposing team was created
						// and switch turns
						if (turn.equals("white")) {
							
							// the own king is after completed move
							// not in check anymore
							king_white.setSchach(false);
							
							// see if the opposite king is now attacked
							if (king_black.squareIsAttacked(figures, king_black.getX(), king_black.getY())) {
								
								// set schach to true; consequently showing square in red
								king_black.setSchach(true);
								
								// check for checkmate
								if (king_black.isCheckMate(figures)) {
									playing = false;
									winner = 1;
								}
							}else {
								
								// check for stale mate if check mate is denied
								if(king_black.isStaleMate(figures)) {
									playing = false;
									winner = 3;
								} 
							}
							
							
							
							// switch turns
							turn = "black";
							
						// do the same for black 
						} else {
							
							// the own king is after completed move
							// not in check anymore
							king_black.setSchach(false);
							
							if (king_white.squareIsAttacked(figures, king_white.getX(), king_white.getY())) {
								king_white.setSchach(true);
								if (king_white.isCheckMate(figures)) {
									playing = false;
									winner = 2;
								}
							} else {
								if(king_white.isStaleMate(figures)) {
									playing = false;
									winner = 3;
								} 
							}
						
							
							turn = "white";
							
						}
						
						
						
						
						
						// check for draw by repetition
						if (list.countPositions(figures) >= 3) {
							playing = false;
							winner = 3;
						} else {
							// if it has not ended 
							// add the new position of the board to the linked list
							NodePointer node = new NodePointer(figures);
							list.addToEnd(node);
						}
						
						
						
					} else {
						// moving back to original position because move was not possible
						activeFigure.move(x_start, y_start); 
					}
				} 
				
				drawField();
			}
			
			
			
			
			
			// show the new field for 20ms
			StdDraw.show(20);
		}	
		
		// end of the game
		
		// set up a text to announce the winner
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledRectangle(4, 4, 2, 1);
		StdDraw.setPenColor(Color.WHITE);
		
		// decide for the winner
		if (winner == 1) {
			StdDraw.text(4, 4, "White wins.");
		} else if (winner == 2) {
			StdDraw.text(4, 4, "Black wins.");
		} else {
			StdDraw.text(4, 4, "It is a draw.");
		}
		
		// show the text over the field
		StdDraw.show(2000);
		
		StdDraw.clear();
		drawField();
		StdDraw.show();
		
	}
}
