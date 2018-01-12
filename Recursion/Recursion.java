/** Recursion.java makes a pretty tree thing.
 *
 *  @author Ed Wong
 *  @version 12/1/2018
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Recursion extends JPanel{
	private static final int WIDTH = 400;
	private static final int HEIGHT= 400;
	private double angle = Math.PI/6.0;
	private int length;

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

	//Use translate instead
	public void branch(int length, Graphics2D g2d){
		g2d.drawLine(0, 0, 0, -length);
		if(length>10) {
			AffineTransform transform = g2d.getTransform();
			length = (int) Math.round(length*0.67);
			g2d.translate(0,-length);
			g2d.rotate(angle);
			branch(length, g2d);
			g2d.setTransform(transform);
			g2d.rotate(-2*angle);
			branch(length, g2d);
		}
	}

	public static void main(String[] args){
		JFrame frame = new JFrame("Dragon Curve in Java");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Recursion fractel = new Recursion(HEIGHT/3);
		frame.add(fractel);
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);
	}
}