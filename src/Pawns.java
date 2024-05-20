public class Pawns extends Figure{

	private static int[] doublePawn = {0, 0};
	private int direction;

	
	public Pawns(String color, int x, int y) {
		super(color, x, y);
		
		if (color.equals("white")) {
			direction = 1;
		} else {
			direction = -1;
		}
	}
	
	
	public void draw() {
		
		if (super.getMoving()) {
			StdDraw.picture(super.getX_double(), super.getY_double(), super.getColor() + "_pawn.png", 0.9, 0.8);
		} else {
			StdDraw.picture(super.getX() + 0.5, super.getY() + 0.45, super.getColor() + "_pawn.png", 0.9, 0.8);
		}
		
		
	}

	public boolean checkForCheck(int x_king, int y_king, Figure[][] figures) {
		if (super.getColor().equals("white")) {
			if (super.getY() + 1 == y_king && Math.abs(super.getX() - x_king) == 1) {
				return true;
			}
		} else {
			if (super.getY() - 1 == y_king && Math.abs(super.getX() - x_king) == 1) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static void resetDoublePawn() {
		doublePawn[0] = -1;
		doublePawn[1] = -1;
	}
	
	public boolean doublePawn(int x_start, int y_start, int x_end, int y_end, Figure[][] figures) {
		
		String color = figures[x_start][y_start].getColor();
		
		if (color.equals("white")) {
			if (y_start + 2 == y_end && figures[x_start][y_start + 1] == null && y_start == 1){
				doublePawn[0] = x_end;
				doublePawn[1] = y_end;
				return true;
			}
		} else {
			if (y_start - 2 == y_end && figures[x_start][y_start - 1] == null && y_start == 6){
				doublePawn[0] = x_end;
				doublePawn[1] = y_end;
				return true;
			} 
		}
		
		return false;
	}
	
	public boolean canAttackField(int x_start, int y_start, int x_end, int y_end, Figure[][] figures) {
		if (x_start == x_end && y_start == y_end) {
			return false;
		} else if (Math.abs(y_start - y_end) > 2) {
			return false;
		} else if (Math.abs(y_start - y_end) == 2 && Math.abs(x_start - x_end) > 0) {
			return false;
		}
		
		String color = figures[x_start][y_start].getColor();
		
		if (color.equals("white")) {
			if (y_start + 1 == y_end) {
				
				if (Math.abs(x_start - x_end) == 1) {
					if (figures[x_end][y_end] != null && !figures[x_end][y_end].getColor().equals(color)) {
						return true;
					} else if (figures[x_end][y_end - 1] instanceof Pawns && y_end == 5) {
						if (doublePawn[0] == x_end && doublePawn[1] == y_end - 1) {
							figures[x_end][y_end - 1] = null;
							
							return true;
						} else {
							return false;
						}
					}
					else {
						return false;
					}
				} else {
					if (figures[x_end][y_end] == null) {
						return true;
					} else {
						return false;
					}
				}
				
			} else if (y_start + 2 == y_end && figures[x_start][y_start + 1] == null && y_start == 1 && figures[x_start][y_end] == null){
				return true;
			} else {
				return false;
			}
		} else {
			if (y_start - 1 == y_end) {
				
				if (Math.abs(x_start - x_end) == 1) {
					if (figures[x_end][y_end] != null && !figures[x_end][y_end].getColor().equals(color)) {
						return true;
					} else if (figures[x_end][y_end + 1] instanceof Pawns && y_end == 2) {
						if (doublePawn[0] == x_end && doublePawn[1] == y_end + 1) {
							figures[x_end][y_end + 1] = null;
							
							return true;
						} else {
							return false;
						}
					}else {
						return false;
					}
				} else {
					if (figures[x_end][y_end] == null) {
						return true;
					} else {
						return false;
					}
				}
				
			} else if (y_start - 2 == y_end && figures[x_start][y_start - 1] == null && y_start == 6 && figures[x_start][y_end] == null){

				return true;
			} else {
				return false;
			}
		}
		
		
		
	}
	

	public boolean canMove(Figure[][] figures) {
		
		int x = super.getX();
		int y = super.getY();
		
		if (figures[x][y + direction] == null) {
			return true;
		} 
		
		if (x != 0) {
			if (figures[x - 1][y + direction] != null && !figures[x - 1][y + direction].getColor().equals(super.getColor())) {
				if(Driver.theoryMove(x, y, x - 1, y + direction)) {
					return true;
				}
				
				return true;
			}
		}
		
		if (x != 7){
			if (figures[x + 1][y + direction] != null && !figures[x + 1][y + direction].getColor().equals(super.getColor())) {
				return true;
			}
		}
		
		return false;
	}
	
	public String toString() {
		return super.getColor() + " pawn";
	}
}
