public class Rook extends Figure{
	
	private boolean moved = false;


 	public Rook(String color, int x, int y) {
		super(color, x, y);
	}
	
 	public boolean getMoved() {
 		return this.moved;
 	}
	
 	public boolean canMove(Figure[][] figures) {
 		int x = super.getX();
 		int y = super.getY();
 		String color = super.getColor();
 		
 		if (y > 0 && (figures[x][y - 1] == null || !figures[x][y - 1].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x, y - 1)) {
 				return true;
 			}
 		}
 		
 		if (y < 7 && (figures[x][y + 1] == null || !figures[x][y + 1].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x, y + 1)) {
 				return true;
 			}
 		}
 		
 		if (x < 7 && (figures[x + 1][y] == null || !figures[x + 1][y].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x + 1, y)) {
 				return true;
 			}
 		}
 		
 		if (x > 0 && (figures[x - 1][y] == null || !figures[x - 1][y].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x - 1, y)) {
 				return true;
 			}
 		}
 		return false;
 		
 	}
 	
	public void draw() {
			
		if (super.getMoving()) {
			StdDraw.picture(super.getX_double(), super.getY_double(), super.getColor() + "_rook.png", 0.9, 0.8);
		} else {
			StdDraw.picture(super.getX() + 0.5, super.getY() + 0.45, super.getColor() + "_rook.png", 0.9, 0.8);
		}
			
			
		
		
	}

	
	public boolean canAttackField(int x_start, int y_start, int x_end, int y_end, Figure[][] figures) {
		if (x_start == x_end && y_start == y_end) {
			return false;
		} else {
			if (x_start == x_end || y_start == y_end) {
				
				if (x_start < x_end) {
					for(int i = x_start + 1; i < x_end; i++) {
						if (figures[i][y_start] != null) {
							return false;
						}
					}
				} else {
					for(int i = x_start - 1; i > x_end; i--) {
						if (figures[i][y_start] != null) {
							return false;
						}
					}
				}
				
				if (y_start < y_end) {
					for(int i = y_start + 1; i < y_end; i++) {
						if (figures[x_start][i] != null) {
							System.out.println(3);
							System.out.println(x_start + " " + i);
							return false;
						}
					}
				} else {
					for(int i = y_start - 1; i > y_end; i--) {
						if (figures[x_start][i] != null) {
							return false;
						}
					}
					
				}
			}
			else {
				return false;
			}
			
			
			this.moved = true;
			return true;
		}
		
	}
	
	
	public String toString() {
		return super.getColor() + " rook";
	}

}
