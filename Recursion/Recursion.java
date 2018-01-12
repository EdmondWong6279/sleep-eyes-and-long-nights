import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Recursion extends JPanel{
	private int length;
	private static final int WIDTH = 400;
	private static final int HEIGHT= 400;
	private int xcoord=WIDTH/2;
	private int ycoord=HEIGHT/2;

	public Recursion(int length){
		this.length=length;
	}

	public String rule1(char char1){
		if (char1 == 'b')
			return "ab";
		return Character.toString(char1);
	}

	public String rule2(char char2){
		if (char2 == 'a')
			return "ab";
		return Character.toString(char2);
	}

	public void generate() {
		for (int i=0; i<sentence.length; i++){
			
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		branch(length, g);
	}

	public static void branch(int length, Graphics g){
		//start
		g.drawLine(0,400,10,12);
		if (length>0){
			//TODO rotate coords
		}
	}

	public static void main(String[] args){
		JFrame frame = new JFrame("Dragon Curve in Java");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Recursion fractel = new Recursion(5);
		frame.add(fractel);
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);
	}
}