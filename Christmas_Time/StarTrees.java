/** This is for exercise 2 of worksheet 6 in software workshop. The
 *  class presented here is a subclass of Trees (from exercise 1)
 *  and adds stars to the top of all trees that are presented.
 *  
 *  @version 6/1/2018
 *  @author Edmond Wong, 1424078
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class StarTrees extends Trees{
	private final static int WIDTH = 500;
	private final static int HEIGHT = 400;
	private final static int STAR_RADIUS = 2;
	private int numberOfVertices;
	private int steps;
	
	/** Constructor for the class StarTrees.
	 *  
	 *  The constructor makes use of the hcf helper method to check
	 *  whether the numbers given are in the correct format.
	 * 
	 *  @param xTrees
	 *  @param yTrees
	 *  @param scaleTrees
	 *  @param numberOfVertices
	 *  @param steps
	 */
	public StarTrees(int[] xTrees, 
					 int[] yTrees,
					 int[] scaleTrees,
					 int numberOfVertices,
					 int steps) {
		super(xTrees, yTrees, scaleTrees);
		try {
			if (hcf(numberOfVertices,steps)!=1) {
					throw new IllegalArgumentException();
				}
			this.numberOfVertices = numberOfVertices;
				this.steps=steps;
		} catch(Exception e) {
			System.out.println("Please choose numberOfVertices and steps such that"
					+ "they are coprime.");
			System.exit(0);
		}
	}
	
	/** This recursive method checks to see if the numberOfVertices
	 *  and steps are coprime. If the hcf is 1, then a and b are
	 *  coprime, meaning we can use the given numbers for our stars. 
	 * 
	 * @param a
	 * @param b
	 * @return hcf(a,b)
	 */
	public int hcf(int a, int b) {
		   if (b==0)
			   return a;
		   return hcf(b,a%b);
		}

	/** The paintComponent method generates the trees and the stars.
	 *  It calls the super method first (so the trees are printed),
	 *  and then the stars are added afterwards.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int[] xPointsStar = new int[numberOfVertices];
		int[] yPointsStar = new int[numberOfVertices];
		
		/** In order to create the stars, we use trigonometry.
		 * 
		 *  First we find the angle between 1 step (this is theta).
		 *  Then we loop through each tree, and in each tree we loop
		 *   through each vertex. Inside each iteration of each vertex,
		 *   we intialise a new array xPointsStar and yPointsStar to
		 *   keep track of the position and ordering of the polygon we
		 *   wish to draw.
		 *  Once we have looped through a single tree, we draw the star
		 *   and then move onto the next tree, reinitialising every
		 *   element in the xPointsStar and yPointsStar arrays. 
		 */
		double theta = 2*Math.PI/numberOfVertices;
		
		for(int j=0; j<getNumberOfTrees();j++) {
			for(int i=0; i< numberOfVertices; i++) {
				xPointsStar[i] = getXPoint(j) + getScale(j)*getGreenWidth()/2 +
						(int) Math.round(getScale(j) * STAR_RADIUS * Math.cos(theta*i*steps));
				yPointsStar[i] = getYPoint(j) +
						(int) Math.round(getScale(j) * STAR_RADIUS * Math.sin(theta*i*steps));
			}
			g.setColor(Color.black);
			g.drawPolygon(xPointsStar, yPointsStar, numberOfVertices);
		}
	}
	
	/** The main method here creates the frame and presents the trees and stars.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Some Christmas Trees with Stars");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int[] xCoords = {10, 350, 200, 250, 400};
		int[] yCoords = {40, 40, 20, 110, 210};
		int[] scales = {20, 10, 5, 10, 8};
		int numbOfVertices = 11;
		int stepSize = 4;

		StarTrees trees = new StarTrees(xCoords, yCoords, scales, numbOfVertices, stepSize);
		frame.add(trees);
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);
	}
}
