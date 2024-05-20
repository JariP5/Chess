import java.awt.Color;

public class King extends Figure{

	private boolean schach = false; // used to identify check visually with a red square
	private boolean moved = false; // cannot castle after being moved
	
	// array of all the figures that attack the king
	// it only matters if the king is attacked by 0, 1 or 2 figures 
	// for 2 or more than 2 attackers the king has to move
	private Figure[] attackedBy = new Figure[2]; 
	
	/*
	 * Constructor
	 */
	public King(String color, int x, int y) {
		super(color, x, y);
	}
	
	/*
	 * is the King already moved
	 */
	public void setMoved (boolean m) {
		this.moved = m;
	}
	
	public boolean getMoved() {
		return this.moved;
	}
	
	public void setSchach(boolean s) {
		this.schach = s;
	}
	
	public boolean getSchach() {
		return this.schach;
	}

	
	
	/*
	 * Stale Mate is ending the game in a draw
	 * It happens when a team cannot move a figure anymore 
	 * without ending up putting the own king into check
	 */
	public boolean isStaleMate(Figure[][] figures) {
		
		
		String color = super.getColor();
		
		// loop over all rows of the board
		for (int row = 0; row <= 7; row++) {
			
			// loop over all columns of the board
			for (int col = 0; col <= 7; col++) {
				
				// check if a figure a figure of the own team can still move
				if (figures[row][col] != null && figures[row][col].getColor().equals(color)) {
					
					Figure f = figures[row][col];
					
					// no stale mate if any figure can move
					if (f.canMove(figures)) {
						return false;
					}
				}
			}
		}
		
		return true;
		
	}
	

	// necessarity for check checking after turn of opposing team
	public boolean squareIsAttacked(Figure[][] figures, int x, int y) {
		
		String color = super.getColor();

		// loop over all rows of the board
		for (int row = 0; row <= 7; row++) {
			
			// loop over all columns of the board
			for (int col = 0; col <= 7; col++) {
				
				
				// check if a figure is of the opposing team 
				if (figures[row][col] != null && !figures[row][col].getColor().equals(color)) {
					
					Figure f = figures[row][col];
					
					// check if the king is attacked by the move made by the opposing team
					if (f instanceof King == false) {
						if (f instanceof Pawns) {
							if (((Pawns)f).checkForCheck(x, y, figures)) {
								return true;
							}
							
						}
						else if (f.canAttackField(f.getX(), f.getY(), x, y, figures)) {
							return true;
						}
					} 
				}
			}
		}
		return false;
	}

	
	public boolean canPreventCheckMate(Figure[][] figures, int x, int y) {
		
		
		// loop over all rows of the board
		for (int row = 0; row <= 7; row++) {
			
			// loop over all columns of the board
			for (int col = 0; col <= 7; col++) {
				
				
				// check if a figure is of the same team
				if (figures[row][col] != null && figures[row][col].getColor().equals(super.getColor())) {
					
					Figure f = figures[row][col];
					
					System.out.print("row:" + col + " col: " + row + "  ");
					System.out.println(f);
					
					
					if (f.canAttackField(f.getX(), f.getY(), x, y, figures) && f instanceof King == false) {
						
						System.out.println("Can attack field to prevent mate");
						if (Driver.theoryMove(f.getX(), f.getY(), x, y)) {
							return true;
						}
						
					}
				}
			}
		}
		return false;
	}
		
		
	private void whoAttacksKing(Figure[][] figures) {
		
		attackedBy[0] = null;
		attackedBy[1] = null;
		
		String color = super.getColor();

		// loop over all rows of the board
		for (int row = 0; row <= 7; row++) {
			
			// loop over all columns of the board
			for (int col = 0; col <= 7; col++) {
				
				
				// check if a figure is of the opposing team 
				if (figures[row][col] != null && !figures[row][col].getColor().equals(color)) {
					
					Figure f = figures[row][col];
					
					// check if the king is attacked by the move made by the opposing team
					if (f instanceof King == false) {
						if (f instanceof Pawns) {
							if (((Pawns)f).checkForCheck(super.getX(), super.getY(), figures)) {
								
								// if the square is attacked
								// where the king is right now
								// add the figure to the array
								if (attackedBy[0] == null) {
									attackedBy[0] = f;
								} else {
									attackedBy[1] = f;
								}
							}
							
						}
						else if (f.canAttackField(f.getX(), f.getY(), super.getX(), super.getY(), figures)) {
							if (attackedBy[0] == null) {
								attackedBy[0] = f;
							} else {
								attackedBy[1] = f;
							}
						}
					} 
				}
			}
		}
		
	}
	
	
	// necessary
	public boolean isCheckMate(Figure[][] figures) {
		
		whoAttacksKing(figures);
		
		// king can still move so no checkMate
		if (canMove(figures)) {
			System.out.println("Check for mate but king can move");
			return false;
		} else {
			System.out.println("Check and king cannot move");
			// king cannot move and is attacked by more than one figure
			// therefore cannot block the check either and is mate
			if (attackedBy[1] != null) {
				System.out.println("Attacked by at least 2 figures and cannot move therefore looses.");
				return true;
			} else {
				Figure f = attackedBy[0];
				
				if (f instanceof Pawns || f instanceof Knight) {
					if (canPreventCheckMate(figures, f.getX(), f.getY())) {
						return false;
					} else {
						return true;
					}					
				} else {
					
					System.out.println("CheckMate Check");
					int ver_distance = Math.abs(f.getY() - super.getY());
					int hor_distance = Math.abs(f.getX() - super.getX());
					System.out.println("ver distance" + ver_distance);
					System.out.println("hor distance" + hor_distance);
					
					
					int x_direction = 0;
					int y_direction = 0;
					
					if (hor_distance != 0) {
						x_direction = (super.getX() - f.getX()) / hor_distance;
					}
					
					if (ver_distance != 0) {
						y_direction = (super.getY() - f.getY()) / ver_distance;
					}
				
					System.out.println("y direction" + y_direction);
					System.out.println("x direction" + x_direction);
					
					
					
					// rook or queen check
					// check lines
					if (ver_distance == 0) {
						
						for (int i = 0; i < hor_distance; i++) {
							if (canPreventCheckMate(figures, f.getX() + i * x_direction, f.getY())) {
								return false;
							}
						}
					
					} else if (hor_distance == 0) {
						for (int i = 0; i < ver_distance; i++) {
							if (canPreventCheckMate(figures, f.getX(), f.getY() + i * y_direction)) {
								System.out.println("Able to prevent vertical check mate");
								return false;
							}
						}
					// bishop or queen check
					// check diagonals
					} else {
						
						for (int i = 0; i < ver_distance; i++) {
							if (canPreventCheckMate(figures, f.getX() + i * x_direction, f.getY() + i * y_direction)) {
								return false;
							}
						}
						
					}
				}
			}
		}
		
		
		return true;
	}
	
	
	

	/*
	 * Draw the king
	 */
	public void draw() {
		
			
		
		
		
		if (super.getMoving()) {
			StdDraw.picture(super.getX_double(), super.getY_double(), super.getColor() + "_king.png", 0.9, 0.8);
		} else {
			if (schach) {
				StdDraw.setPenColor(Color.RED);
				StdDraw.filledSquare(super.getX() + 0.5, super.getY() + 0.5, 0.5);
			} 
			StdDraw.picture(super.getX() + 0.5, super.getY() + 0.45, super.getColor() + "_king.png", 0.9, 0.8);
		}		
	}

	/*
	 * Can the King still move without ending up in check
	 */
	public boolean canMove(Figure[][] figures) {
		
		// getting the basic attributes of the king
		String color = super.getColor();
		int x = super.getX();
		int y = super.getY();
		
		// | | | |
		// | |K| |
		// |X| | |
		if (x > 0 && y > 0) {
			if ((figures[x - 1][y - 1] == null || !figures[x - 1][y - 1].getColor().equals(color)) && Driver.theoryMove(x, y, x - 1, y - 1)) {
				System.out.println("x: " + (x - 1) + " & y: " + (y - 1));
				return true;
			}
		}
		
		// | | | |
		// |X|K| |
		// | | | |
		if (x > 0) {
			if ((figures[x - 1][y] == null || !figures[x - 1][y].getColor().equals(color)) && Driver.theoryMove(x, y, x - 1, y)) {
				System.out.println("x: " + (x - 1) + " & y: " + (y));
				return true;
			}
		}
		
		// |X| | |
		// | |K| |
		// | | | |
		if (x > 0 && y < 7) {
			if ((figures[x - 1][y + 1] == null || !figures[x - 1][y + 1].getColor().equals(color)) && Driver.theoryMove(x, y, x - 1, y + 1)) {
				System.out.println("x: " + (x - 1) + " & y: " + (y + 1));
				return true;
			}
		}
			
			
		// | | | |
		// | |K| |
		// | |X| |
		if (y > 0) {
			if ((figures[x][y - 1] == null || !figures[x][y - 1].getColor().equals(color)) && Driver.theoryMove(x, y, x, y - 1)) {
				System.out.println("x: " + (x) + " & y: " + (y - 1));
				return true;
			}
		}
		
		
		// | |X| |
		// | |K| |
		// | | | |
		if (y < 7) {
			if ((figures[x][y + 1] == null || !figures[x][y + 1].getColor().equals(color)) && Driver.theoryMove(x, y, x, y + 1)) {
				System.out.println("x: " + (x) + " & y: " + (y + 1));
				return true;
			}
		}
		
		
		// | | | |
		// | |K| |
		// | | |X|
		if (x < 7 && y > 0) {
			if ((figures[x + 1][y - 1] == null || !figures[x + 1][y - 1].getColor().equals(color)) && Driver.theoryMove(x, y, x + 1, y - 1)) {
				System.out.println("x: " + (x + 1) + " & y: " + (y - 1));
				return true;
			}
		}
		
		// | | |X|
		// | |K| |
		// | | | |
		if (x < 7 && y < 7) {
			if ((figures[x + 1][y + 1] == null || !figures[x + 1][y + 1].getColor().equals(color)) && Driver.theoryMove(x, y, x + 1, y + 1)) {
				System.out.println("x: " + (x + 1) + " & y: " + (y + 1));
				return true;
			}
		}
			
		// | | | |
		// | |K|X|
		// | | | |
		if (x < 7) {
			if ((figures[x + 1][y] == null || !figures[x + 1][y].getColor().equals(color)) && Driver.theoryMove(x, y, x + 1, y)) {
				System.out.println("x: " + (x + 1) + " & y: " + (y));
				return true;
			}
		}
		
		
		return false;
	}
	
	/*
	 * check if the king can go the from the player ordered square
	 */
	public boolean canAttackField(int x_start, int y_start, int x_end, int y_end, Figure[][] figures) {
		if (Math.abs(x_start - x_end) < 2 && Math.abs(y_start - y_end) < 2) {
			return true;
			
			// check for castle
		} else if (Math.abs(x_start - x_end) == 2 && Math.abs(y_start - y_end) == 0){
			
			// cannot castle when the king has already moved
			if (this.moved) {
				return false;
			}
			
			// the rook necessary for the castle
			Rook r;
			
			
			// queen side castle
			if (x_end < x_start) {
								
				if (figures[0][y_start] != null && figures[0][y_start] instanceof Rook) {
					
					// rook is still on the corner position
					r = ((Rook)figures[0][y_start]);
					
					// cannot castle when rook already moved
					if (r.getMoved()) {
						return false;
					}
				} else {
					return false;
				}
				
				// loop over the squares from king to rook
				for (int i = x_start; i >= 0; i--) {
					
					// check if king has to move through check
					if (Math.abs(x_start - i) <= 2 && squareIsAttacked(figures, i, super.getY())) {
						return false;
						
					// cannot castle when any figure is in between rook and king	
					} else if (figures[i][y_start] != null) {
						if (i != x_start && i != r.getX()) {
							return false;
						}
					}
				}
				
				// move the rook
				// since the move is validated 
				// the king is going to be moved in the driver
				figures[0][y_start] = null;
				figures[x_start - 1][y_start] = r;
				r.move(x_start - 1, y_start);
				
				return true;
				
				
			} else {
				
				if (figures[7][y_start] != null) {
										
					if (figures[7][y_start] instanceof Rook) {
						r = ((Rook)figures[7][y_start]);
						if (r.getMoved()) {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
				
				
				for (int i = x_start; i <= 7; i++) {

					if (Math.abs(x_start - i) <= 2 && squareIsAttacked(figures, i, super.getY())) {
						return false;
					} else if (figures[i][y_start] != null) {
						if (i != x_start && i != r.getX()) {
							return false;
						}
					}
				}
				
				figures[7][y_start] = null;
				figures[x_start + 1][y_start] = r;
				r.move(x_start + 1, y_start);
								
				return true;
				
			}
		} else {
			return false;
		}
	}
	

	public String toString() {
		return super.getColor() + " King";
	}

}
