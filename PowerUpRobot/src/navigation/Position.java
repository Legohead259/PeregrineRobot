package navigation;

public class Position {
	public double xPos; //Inches from side of robot (x-axis)
	public double xBound; //Deadzone 
	public double yPos;
	public double yBound;
	public String name;
	
	public Position(String n, double x, double y, double dx, double dy) {
		xPos = x;
		yPos = y;
		xBound = dx;
		yBound = dy;
		name = n;
	}
	
	
}
