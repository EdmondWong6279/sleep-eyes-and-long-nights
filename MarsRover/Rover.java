/** MarsRover assignment for ThoughtWorks.
 *  Rover class contains all information about a rover, including
 *  methods to move and turn it.
 *  
 *  @author Ed Wong
 *  @version 18/1/2018
 */

public class Rover {
	private static final char[] HEADINGS =  {'N','E','S','W'};
	private int xCoords;
	private int yCoords;
	private int heading;

	/** Constructor takes in the size of the plateau.
	 *  The constructor will throw an exception if it is not initiated in
	 *  a valid form.
	 */
	public Rover(int xCoords, int yCoords, char heading){
		this.xCoords=xCoords;
		this.yCoords=yCoords;
		switch(heading) {
		case 'N': this.heading = 0;
		break;
		case 'E': this.heading = 1;
		break;
		case 'S': this.heading = 2;
		break;
		case 'W': this.heading = 3;
		break;
		default:
			throw new IllegalArgumentException("Nah you need a valid heading fam.");
		}
	}

	/** Getter for x coordinates of the rover.
	 * 
	 *  @return x coordinates
	 */
	public int getXCoords() {
		return xCoords;
	}

	/** Getter for y coordinates of the rover.
	 * 
	 *  @return y coordinates
	 */	
	public int getYCoords() {
		return yCoords;
	}

	/** Getter for the current heading of the rover.
	 * 
	 *  @return char heading
	 */
	public char getHeading() {
		return HEADINGS[heading];
	}

	/** Setter for the x coordinates.
	 * 
	 *  @param xCoords
	 */
	public void setXCoords(int xCoords) {
		this.xCoords=xCoords;
	}

	/** Setter for the x coordinates.
	 * 
	 *  @param xCoords
	 */
	public void setYCoords(int yCoords) {
		this.yCoords=yCoords;
	}

	/** Precondition- the rover is in a valid heading such that it can move 1
	 *  unit in that direction.
	 *  
	 *  Sets the coordinates to the new coordinates.
	 */
	public void moveRover() {
		switch(getHeading()){
		case 'N':
			setYCoords(getYCoords()+1);
			break;
		case 'E':
			setXCoords(getXCoords()+1);
			break;
		case 'S':
			setYCoords(getYCoords()-1);
			break;
		case 'W':
			setXCoords(getXCoords()-1);
			break;
		}
	}

	/** Precondition- The parameter a is either L or R.
	 *  
	 *  The rover is rotated with left or right and it's
	 *  heading is updated accordingly.
	 *    
	 *  @param a- either L or R
	 */
	public void turnRover(char a) {
		if(a=='L')
			heading = (heading+3)%4;
		else if(a=='R')
			heading = (heading+1)%4;
	}

	/** The toString method prints information on the rover
	 *  in the following format:
	 *  "This rover is at position X, Y with heading N."
	 */
	public String toString() {
		return "This rover is at position "
				+getXCoords()+", "+getYCoords()
				+" with heading "+getHeading()
				+".";
	}

	/* Main method to test the class is working as expected.
	public static void main(String[] args) {
		Rover r = new Rover(1, 1, 'N');
		Rover s = new Rover(1, 2, 'E');
		Rover t = new Rover(0, 3423, 'W');
		Rover u = new Rover(3123, 0, 'S');
		System.out.println(r);
		System.out.println(s);
		System.out.println(t);
		System.out.println(u);

		r.moveRover();
		System.out.println(r);
		r.turnRover('L');
		System.out.println(r);
		r.turnRover('R');
		r.turnRover('R');
		r.turnRover('R');
		System.out.println(r);
		r.moveRover();
		System.out.println(r);	
	}
	 */
}