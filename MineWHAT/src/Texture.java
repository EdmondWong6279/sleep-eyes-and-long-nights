/** Texture class for the images on each type of surface 
 * 
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
	
	public static Texture wood = new Texture("res/wood.png", 64);
	public static Texture brick = new Texture("res/redbrick.png", 64);
	public static Texture bluestone = new Texture("res/bluestone.png", 64);
	public static Texture stone = new Texture("res/greystone.png", 64);
	
	//The array pixels is used to hold the data for all the pixels in the image of the texture.
	public int[] pixels;
	//Loc is used to indicate to the computer where the image file for the texture can be found.
	private String loc;
	//SIZE is how big the texture is on one side (a 64x64 image would have size 64),
	//and all textures will be perfectly square.
	public final int SIZE;
	
	/** Constructor initializes the loc and SIZE variables and call the a method to load the
	 *  image data into pixels.
	 *  
	 *  @param location
	 *  @param size
	 */
	public Texture(String location, int size) {
		loc = location;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	/** Now all that's left for the Texture class is to add a load method to get data from images
	 *  and store them in an array of pixel data.
	 */
	private void load() {
		try {
			BufferedImage image = ImageIO.read(new File(loc));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
