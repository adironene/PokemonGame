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

public class shopTile extends Tile {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private static final int WIDTH = (int) (screenSize.getWidth()), HEIGHT = (int) (screenSize.getHeight());
	private int textBoxHeight = WIDTH / 2 * 46 / 252;
	private Font customFont;
	Image textBox = getImage("TextBox.png");

	public shopTile() {

	}

	public void draw(Graphics g) {
		g.drawImage(getImage("shop/shop.png"), 0, 0, WIDTH, HEIGHT, null);
		g.drawImage(getImage("menu/potion.png"), (int) ((0.4) * WIDTH), (int) ((0.3) * HEIGHT), 80, 80, null);
		g.drawImage(getImage("shop/pokeballsUltra.png"), (int) ((0.54) * WIDTH), (int) ((0.3) * HEIGHT), 80, 80, null);
		g.drawImage(getImage("playerPNG/characterUp0.png"), (this.WIDTH - 100) / 2, (this.HEIGHT - 100) / 2, 100, 100,
				null);
		if (customFont == null) {
			try {
				// create the font to use. Specify the size!
				customFont = Font.createFont(Font.TRUETYPE_FONT,
						getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(44f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				// register the font
				ge.registerFont(customFont);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
		}
		g.setFont(customFont);
		g.drawImage(getImage("TextBox.png"), 0, (HEIGHT - WIDTH / 2 * 46 / 252) - 40, WIDTH, WIDTH / 2 * 46 / 252,
				null);

//		g.setColor(Color.GRAY);
//		g.drawString(message, 140, HEIGHT - textBoxHeight + 30);

			g.setColor(Color.DARK_GRAY);
			g.drawString("You have entered shop! press 1 to buy more potion, press 2 for more pokeballs.", 139,
					HEIGHT - textBoxHeight + 10);
			g.drawString("everything is 5 pokecoins, press s to exit ", 139, HEIGHT - textBoxHeight + 65);

	}

	@Override
	protected void triggerEvent() {
		// TODO Auto-generated method stub

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

	@Override
	protected void setIntroInFinished(boolean b) {
		// TODO Auto-generated method stub

	}

}
