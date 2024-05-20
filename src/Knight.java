public class Knight extends Figure{

	public Knight(String color, int x, int y) {
		super(color, x, y);
	}
	
	
	
	public void draw() {
		
		if (super.getMoving()) {
			StdDraw.picture(super.getX_double(), super.getY_double(), super.getColor() + "_knight.png", 0.9, 0.8);
		} else {
			StdDraw.picture(super.getX() + 0.5, super.getY() + 0.45, super.getColor() + "_knight.png", 0.9, 0.8);
		}
		
		
	}
	
	public boolean canMove(Figure[][] figures) {
		
		int x = super.getX();
		int y = super.getY();
		int x_add = 1;
		int y_add = 2;
		
		if (x == 7) {
			x_add = -1;
		}
		
		if (y >= 6) {
			y_add = -2;
		}
		
		if (Driver.theoryMove(x, y, x + x_add, y + y_add)) {
			return true;
		} else {
			return false;
		}
		
		
	}

	
	public boolean canAttackField(int x_start, int y_start, int x_end, int y_end, Figure[][] figures) {
		if (x_start == x_end && y_start == y_end) {
			return false;
		} else if (Math.abs(y_start - y_end) * Math.abs(x_start - x_end) == 2) {
			return true;
		} else {
			return false;
		}
			
	}
	

	public String toString() {
		return super.getColor() + " knight";
	}

}
