/** Recursion.java makes a pretty tree thing.
 *
 *  @author Ed Wong
 *  @version 12/1/2018
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Recursion extends JPanel{
	private static final int WIDTH = 800;
	private static final int HEIGHT= 600;
	private double angle = Math.PI/2.0;
	private int length;
	private int counter=0;

	public Recursion(int length){
		this.length=length;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(WIDTH/2,HEIGHT);
		branch(length, g2d);
	}

	public void branch(int length, Graphics2D g2d){
		g2d.drawLine(0, 0, 0, -length);
		if(counter<10) {
			g2d.translate(0,-length);
			g2d.rotate(angle);
			branch(length, g2d);
			g2d.rotate(-2.0*angle);
			branch(length, g2d);
			counter++;
		}
	}

	public static void main(String[] args){
		JFrame frame = new JFrame("Fractal Tree in Java");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Recursion fractel = new Recursion(HEIGHT/2);
		frame.add(fractel);
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);
	}
}