/** This is the first exercise on worksheet 6 of software
 *  workshop. Here, we present a window with several trees
 *  on it. The position and size of the trees can be altered.
 *  Also, we can keep adding/removing trees so the number of
 *  them can also change. 
 *  
 *  @version 6/1/2018
 *  @author Edmond Wong, 1424078
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Trees extends JPanel{
	private int[] xTrees;
	private int[] yTrees;
	private int[] scaleTrees;

	/** These final values are needed in order to create
	 *  trees of the requested dimensions.
	 *  
	 *  Note: BROWN_GAP refers to the space between the left
	 *  side of the whole tree and the left side of the trunk.
	 */
	private final static int WIDTH = 500;
	private final static int HEIGHT = 400;
	private final static int GREEN_HEIGHT = 12;
	private final static int GREEN_WIDTH = 12;
	private final static int BROWN_HEIGHT = 4;
	private final static int BROWN_WIDTH = 2;
	private final static int BROWN_GAP = 5;

	/** Constructor for the Trees class. 
	 * 
	 *  @param xTrees
	 *  @param yTrees
	 *  @param scaleTrees
	 */
	public Trees (int[] xTrees,
			int[] yTrees,
			int[] scaleTrees) {
		try {
			if (xTrees.length!=yTrees.length ||
					xTrees.length!=scaleTrees.length) {
				throw new IllegalArgumentException();
			}
			this.xTrees=xTrees;
			this.yTrees=yTrees;
			this.scaleTrees=scaleTrees;
		} catch (Exception e) {
			System.out.println("Please make sure that there are equal number"
					+ "of x and y coordinates, and scales too.");
			System.exit(0);
		}
	}

	/** Getter for the ith x point. This is needed for
	 *  the subclass StarTrees.
	 * 
	 *  @param index i
	 *  @return an integer of the ith x point.
	 */
	public int getXPoint(int i) {
		return xTrees[i];
	}

	/** Getter for the ith y point. This is needed for
	 *  the subclass StarTrees.
	 * 
	 *  @param index i
	 *  @return an integer of the ith y point.
	 */
	public int getYPoint(int i) {
		return yTrees[i];
	}

	/** Getter for the ith scale. This is needed for
	 *  the subclass StarTrees.
	 * 
	 *  @param index i
	 *  @return an integer of the ith scale.
	 */
	public int getScale(int i) {
		return scaleTrees[i];
	}

	/** Getter for the width of the green section.
	 *  This is needed for the subclass StarTrees.
	 * 
	 *  @return the final int GREEN_WIDTH.
	 */
	public int getGreenWidth() {
		return GREEN_WIDTH;
	}

	/** Getter for the number of trees. This is needed
	 *  for the subclass StarTrees.
	 *  
	 *  @return an integer for the number of trees.
	 */
	public int getNumberOfTrees() {
		return xTrees.length;
	}	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		/** Create 4 arrays, 2 for each coloured shape we require
		 *  (i.e. 2 for the brown part and 2 for the green part)
		 */
		int[] xPointsGreen = new int[xTrees.length*3];
		int[] yPointsGreen = new int[yTrees.length*3];
		int[] xPointsBrown = new int[xTrees.length*4];
		int[] yPointsBrown = new int[yTrees.length*4];

		/** This for loop creates the arrays and draws the trees
		 *  as it goes along.
		 */
		for(int i=0; i<xTrees.length; i++) {
			
			/** Green part x's
			 */
			xPointsGreen[0]=xTrees[i]+scaleTrees[i]*GREEN_WIDTH/2;
			xPointsGreen[1]=xTrees[i];
			xPointsGreen[2]=xTrees[i]+scaleTrees[i]*GREEN_WIDTH;
			/** Green part y's
			 */
			yPointsGreen[0]=yTrees[i];
			yPointsGreen[1]=yTrees[i]+scaleTrees[i]*GREEN_HEIGHT;
			yPointsGreen[2]=yTrees[i]+scaleTrees[i]*GREEN_HEIGHT;
			/** Brown part x's
			 */
			xPointsBrown[0]=xTrees[i]+scaleTrees[i]*BROWN_GAP;
			xPointsBrown[1]=xTrees[i]+scaleTrees[i]*(BROWN_GAP+BROWN_WIDTH);
			xPointsBrown[2]=xTrees[i]+scaleTrees[i]*(BROWN_GAP+BROWN_WIDTH);
			xPointsBrown[3]=xTrees[i]+scaleTrees[i]*BROWN_GAP;
			/** Brown part y's
			 */
			yPointsBrown[0]=yTrees[i]+scaleTrees[i]*(GREEN_HEIGHT);
			yPointsBrown[1]=yTrees[i]+scaleTrees[i]*(GREEN_HEIGHT);
			yPointsBrown[2]=yTrees[i]+scaleTrees[i]*(GREEN_HEIGHT+BROWN_HEIGHT);
			yPointsBrown[3]=yTrees[i]+scaleTrees[i]*(GREEN_HEIGHT+BROWN_HEIGHT);

			/** Draw the green part.
			 */
			g.setColor(Color.GREEN);
			g.fillPolygon(xPointsGreen, yPointsGreen, 3);

			/** Draw the brown part.
			 */
			g.setColor(new Color(205,127,50));
			g.fillPolygon(xPointsBrown, yPointsBrown, 4); 
		}
	}

	/** The main method here presents the trees.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Some Christmas Trees");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int[] xCoords = {10, 350, 200, 250, 400, 10};
		int[] yCoords = {40, 40, 20, 110, 210, 10};
		int[] scales = {20, 10, 5, 10, 8, 1};

		Trees trees = new Trees(xCoords, yCoords, scales);
		frame.add(trees);
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);
	}
}