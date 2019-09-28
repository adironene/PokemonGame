import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StarterEvent extends Events {
	Image starter1 = getImage("001d.png");
	Image starter2 = getImage("Battlers/004.png");
	Image starter3 = getImage("007d.png");
	
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) (screenSize.getWidth()), HEIGHT = (int) (screenSize.getHeight());
	private float alpha = 1f;
	private boolean isFinished;
	private int pokemonScale = 2;
	private String selectedPokemon = "Charmander";
	private boolean fadedIn;
	private int selectedMon = 2;
	private String[] selectedMonString = new String[] {"Bulbasaur", "Charmander", "Squirtle"};
	private Font customFont;
	private Font smallFont;
	private boolean fadedOut;
	private boolean fadingOut;
	
	public StarterEvent() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void moveObjects() {
		if(!fadedIn)
			fadeIn();
		if(!fadedOut && fadingOut)
			fadeOut();

	}

	@Override
	public void setIntroInFinished(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawFade(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		if(alpha>1.0f)
			alpha = 1.0f;
		if(alpha<0.0f)
			alpha = 0.0f;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		g2d.setComposite(ac);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 2024, 640 * 2);
//		System.out.println("alpha value: "+alpha);
	
}
	public void fadeIn() {
		alpha-=0.04f;
		if(alpha<0f) {
			alpha = 0f;
			fadedIn = true;
		}
	}
	
	public void fadeOut() {
		alpha+=0.04f;
		if(alpha>1f) {
			alpha = 1f;
			isFinished = true;
		}
	}

	@Override
	public void continueBattle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endBattle() {
		// TODO Auto-generated method stub

	}
	
	public void draw(Graphics g) {
		if (this.customFont == null) {
			try {
				// create the font to use. Specify the size!
				this.customFont = Font.createFont(Font.TRUETYPE_FONT,
						getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(155f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				// register the font
				ge.registerFont(customFont);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
		}
		if (this.smallFont == null) {
			try {
				// create the font to use. Specify the size!
				this.smallFont = Font.createFont(Font.TRUETYPE_FONT,
						getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(35f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				// register the font
				ge.registerFont(smallFont);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
		}
		g.setFont(customFont);
		g.drawImage(getImage("starterBackground.png"), 0, 0, (int) (1021*2), (int) (640*2), null);
		g.drawImage(starter1, WIDTH * 1 / 8 + 80, HEIGHT /2 - 160, 160 * pokemonScale, 160 * pokemonScale, null);
		g.drawImage(starter2, WIDTH * 3 / 8+ 80, HEIGHT /2 - 160, 160 * pokemonScale, 160 * pokemonScale, null);
		g.drawImage(starter3, WIDTH * 5 / 8+ 80, HEIGHT /2 - 160, 160 * pokemonScale, 160 * pokemonScale, null);
		g.drawString(selectedMonString[selectedMon-1], WIDTH/2 - 300 , HEIGHT/2 - 300);

		
		
		//instructions
		g.setFont(smallFont);
		g.drawString("SELECT POKEMON: ARROW KEYS", 0, 30);
		g.drawString("CONFIRM POKEMON: ENTER KEY", 0, 60);
		drawFade(g);
	}
	
	public void starterEventFinished() {
		isFinished = true;
	}
	public boolean isEventFinished() {
		return isFinished;
	}
	public String getSelectedPokemon() {
		return selectedMonString[selectedMon-1];
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
	public void chooseOption(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//			System.out.println("yes");
			if(selectedMon>1) {
				selectedMon--;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(selectedMon<3)
				selectedMon++;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(fadedIn)
				fadingOut = true;
		}
		if(selectedMon ==  1) {
			starter1 = getImage("Battlers/001.png");
			starter2 = getImage("004d.png");
			starter3 = getImage("007d.png");
		}
		else if(selectedMon ==  2) {
			starter1 = getImage("001d.png");
			starter2 = getImage("Battlers/004.png");
			starter3 = getImage("007d.png");
		}
		else if(selectedMon ==  3) {
			starter1 = getImage("001d.png");
			starter2 = getImage("004d.png");
			starter3 = getImage("Battlers/007.png");
		}
		
	}

}
