public class Bishop extends Figure{

	public Bishop(String color, int x, int y) {
		super(color, x, y);
	}
	
	
	
	public void draw() {
		
		if (super.getMoving()) {
			StdDraw.picture(super.getX_double(), super.getY_double(), super.getColor() + "_bishop.png", 0.9, 0.8);
		} else {
			StdDraw.picture(super.getX() + 0.5, super.getY() + 0.45, super.getColor() + "_bishop.png", 0.9, 0.8);
		}
		
		
	}

	
	public boolean canAttackField(int x_start, int y_start, int x_end, int y_end, Figure[][] figures) {
		if (x_start == x_end && y_start == y_end) {
			return false;
		} else if (Math.abs(x_start - x_end) != Math.abs(y_start - y_end)){
			return false;
		} else {
			
			int distance = Math.abs(x_start - x_end);
			
			if(x_start > x_end) {
				if(y_start > y_end) {
					for (int i = 1; i < distance; i++) {
						if (figures[x_start - i][y_start - i] != null) {
							return false;
						} 
					}
				} else {
					for (int i = 1; i < distance; i++) {
						if (figures[x_start - i][y_start + i] != null) {
							return false;
						} 
					}
				}
			} else {
				if(y_start > y_end) {
					for (int i = 1; i < distance; i++) {
						if (figures[x_start + i][y_start - i] != null) {
							return false;
						} 
					}
				} else {
					for (int i = 1; i < distance; i++) {
						if (figures[x_start + i][y_start + i] != null) {
							return false;
						} 
					}
				}
			}
			
			return true;
		}
	}
	

	public boolean canMove(Figure[][] figures) {
 		int x = super.getX();
 		int y = super.getY();
 		String color = super.getColor();
 		
 		if (y > 0 && x > 0 && (figures[x - 1][y - 1] == null || !figures[x - 1][y - 1].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x - 1, y - 1)) {
 				return true;
 			}
 		}
 		
 		if (y < 7 && x > 0 && (figures[x - 1][y + 1] == null || !figures[x - 1][y + 1].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x - 1, y + 1)) {
 				return true;
 			}
 		}
 		
 		if (y > 0 && x < 7 && (figures[x + 1][y - 1] == null || !figures[x + 1][y - 1].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x + 1, y - 1)) {
 				return true;
 			}
 		}
 		
 		if (y < 0 && x < 7 && (figures[x + 1][y + 1] == null || !figures[x + 1][y + 1].getColor().equals(color))) {
 			if (Driver.theoryMove(x, y, x + 1, y + 1)) {
 				return true;
 			}
 		}

 		return false;
 		
 	}
	
	public String toString() {
		return super.getColor() + " bishop";
	}
}
