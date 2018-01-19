/** MarsRover assignment for ThoughtWorks.
 *  Plateau class makes the plateau and initiates a rover until 'q' is hit to quit the session.
 *  
 *  @author Ed Wong
 *  @version 18/1/2018
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class Plateau {
	private int xDim;
	private int yDim;
	private static Scanner s;

	/** Constructor for the plateau takes in the two dimensions. An exception is thrown if anything
	 *  equal to or below 0 is given. 
	 * 
	 *  @param xDim
	 *  @param yDim
	 */
	public Plateau(int xDim, int yDim) {
		if(xDim>0 && yDim>0) {
			this.xDim=xDim;
			this.yDim=yDim;
		} else
			throw new IllegalArgumentException("The dimensions of the plateau must be positive integers.");
	}

	/** Getter returns the x dimension of the plateau.
	 * 
	 *  @return xDim
	 */
	public int getXDim() {
		return xDim;
	}

	/** Getter returns the y dimension of the plateau.
	 * 
	 *  @return yDim
	 */
	public int getYDim() {
		return yDim;
	}

	public void explorePlateau() {
		s = new Scanner(System.in);
		// We loop here indefinitely so we can play around on our plateau for as long as we want until
		// an invalid input is entered.
		while (true) {
			try{
				System.out.println("Please enter the position and heading of a rover in the following format\n"+
					"10 10 E for position (10,10) facing east.");
				// Grab position and heading of a rover.
				int xPos = s.nextInt();
				int yPos = s.nextInt();
				char heading = s.next().charAt(0);

				// We check if the position given is inside the plateau.
				if(xPos>getXDim()||xPos<0||yPos>getYDim()||yPos<0)
					throw new InputMismatchException();

				// Create a rover.
				Rover r = new Rover(xPos, yPos, heading);
				System.out.println(r);
				System.out.println("Please enter some instructions for this rover to follow in the form of\n"
						+ "L, R and M for turn left, right and move respectively.");

				// Grab instructions and apply the instructions if valid.
				s = new Scanner(System.in);
				String instructions = (String) s.nextLine();
				String executed = "";

				// for loop for each instruction
				for(char a : instructions.toCharArray()){ 
					if(a=='L'|| a=='R')
						r.turnRover(a);
					else if (a =='M')
						/* The moveRover method checks to see if the rover will
						 *  go outside of the stated area. If so, an exception is thrown.
						 */
						validMove(r);
					else {
						System.out.println("The instructions reached up to the following steps: "+executed+
								"\n"+a+" is not a valid instruction");
						break;
					}
					executed += a;
				}
				System.out.println("Final position and heading of the rover: "+r.getXCoords()+" "+r.getYCoords()+" "+r.getHeading());
			} catch(InputMismatchException e) {
				System.out.println("Warning: Rover cannot start outside of the plateau.");
				s = new Scanner(System.in);
			} catch(IllegalArgumentException e) {
				System.out.println("Warning: Enter only valid inputs.");
				s = new Scanner(System.in);
			} catch(Exception e) {
				System.out.println("Quitting");
				System.exit(0);
			}
		}
	}

	/** This method checks that a given rover can move 1 unit in it's current heading.
	 *  If it can, then the rover is moved.
	 *  Otherwise, the rover remains and an error message is printed.
	 * 
	 *  @param A Rover r.
	 */
	public void validMove(Rover r){
		if (r.getHeading()=='N' && r.getYCoords()==getYDim() ||
				r.getHeading()=='E' && r.getXCoords()==getXDim() ||
				r.getHeading()=='S' && r.getYCoords()==0 ||
				r.getHeading()=='W' && r.getYCoords()==0)
			System.out.println("Warning: Cannot traverse outside of the plateau.");
		else
			r.moveRover();
	}

	/** Prints out the dimensions of the plateau in the following format:
	 *  "This plateau is of dimensions X by Y."
	 * 
	 * 	@return String of the above format
	 */
	public String toString() {
		return "This plateau is of dimensions "
				+getXDim()+" by "+getYDim()+".";
	}

	/** Welcome method introduces the plateau and initiates explorePlateau.
	 *	
	 *
	 *
	 */
	public static void Welcome(){
		System.out.println("Welcome to the my MarsRover program.\nPlease enter the dimensions"
				+ " of the plateau you would like to explore.");
		s = new Scanner(System.in);
		while(true){
			try {
				int xInput = s.nextInt();
				int yInput = s.nextInt();
				Plateau p = new Plateau(xInput, yInput);
				p.explorePlateau();
			} catch(Exception e) {
				System.out.println("Warning: Enter a positive integer for the dimension of the plateau.");
				s = new Scanner(System.in);
			}
		}
	}

	/** The main method where the execution takes place.
	 *  
	 *  @param args
	 */
	public static void main(String[] args) {
		Welcome();
	}
}