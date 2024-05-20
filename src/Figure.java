
public abstract class Figure {
	
	private String color;
	private double x, y;
	private boolean moving;

	public Figure (String color, int x, int y) {
		this.color = color;
		
		this.x = x;
		this.y = y;
		
	}

	public abstract boolean canAttackField(int x_start, int y_start, int x_end, int y_end, Figure[][] color);
	
	public abstract void draw();
	
	public abstract boolean canMove(Figure[][] figures);
	
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
		this.moving = false;
	}
	
	public void move(double x, double y) {
		this.x = x;
		this.y = y;
		this.moving = true;
	}
	
	public int getX() {
		return (int) this.x;
	}
	
	public int getY() {
		return (int) this.y;
	}
	
	public double getX_double() {
		return this.x;
	}
	
	public double getY_double() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public void setMoving(boolean m) {
		this.moving = m;
	}
	
	public boolean getMoving() {
		return this.moving;
	}
}
