import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MessageTile extends Tile {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int WIDTH = (int) (screenSize.getWidth()), HEIGHT = (int) (screenSize.getHeight());
	private int textBoxHeight = WIDTH / 2 * 46 / 252;
	
	Image textBox = getImage("TextBox.png");
	String message = "";
	private Font customFont;
	public MessageTile() {
		// TODO Auto-generated constructor stub
	}

	public MessageTile(String s) {
		message = s;
	}
	@Override
	protected void triggerEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setIntroInFinished(boolean b) {
		// TODO Auto-generated method stub

	}
	
	public void draw(Graphics g) {
		if (customFont ==null) {
			try {
			    //create the font to use. Specify the size!
			    customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(44f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(customFont);
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		}
		g.setFont(customFont);
		g.drawImage(getImage("TextBox.png"), 0, (HEIGHT - WIDTH / 2 * 46 / 252) - 40, WIDTH,
				WIDTH / 2 * 46 / 252, null);

//		g.setColor(Color.GRAY);
//		g.drawString(message, 140, HEIGHT - textBoxHeight + 30);

		g.setColor(Color.DARK_GRAY);
		g.drawString(message, 139, HEIGHT - textBoxHeight + 29);
	}


	protected Image getImage(String fn) {
		Image img = null;
		try {

			img = ImageIO.read(this.getClass().getResource(fn));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
