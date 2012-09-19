package mazes;

public class Cell {
	
	/*
	 * The Cell class is just used to store x and y co-ordinates as a pair.
	 */
	
	private int x;
	private int y;
	
	public Cell(int pX, int pY) {
		
		x = pX;
		y = pY;
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
