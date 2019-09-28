import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class EventObject {
	private Rectangle rect;
	private ArrayList<String> eo;
	private int indexOfCurrentImage;
	private Image image;
	private Image[] sendOutAni;
	private String displayText = "";
	private Font customFont;
	private int fontSize;
	private int textX;
	private int textY;
	private static final int spriteScale = 3;
	
	public EventObject(String img, int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
		image = getImage(img);
		
	}
	
	public EventObject(Image img, int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
		image = img;
		
	}
	
	public EventObject(ArrayList<String> eo, int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
		this.eo = new ArrayList<String>();
		this.eo = eo;
		indexOfCurrentImage = 0;
		image = getImage(this.eo.get(0));
	}
	
	public EventObject(String img, int x, int y, int width, int height, String text, int txtX, int txtY, int fntSz) {
		rect = new Rectangle(x, y, width, height);
		image = getImage(img);
		displayText = text;
		textX = txtX;
		textY = txtY;
		fontSize = fntSz;
	}
	
	
	public void updateEventObject(String img, int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
		image = getImage(img);
	}
	
	public String getSpecificImage(int i) {
		return eo.get(i);
	}
	
	public void setImage(String s) {
		image = getImage(s);
	}

	public Image getCurrentImage() {
		return image;
	}
	
	public ArrayList getList() {
		return eo;
	}
	public void setLoc(int x, int y) {
		rect.setLocation(x, y);
	}
	public void updateSprite() {
		if (indexOfCurrentImage==eo.size()-1) {
			image = getImage(eo.get(0));
			indexOfCurrentImage = 0;
		}
		else {
			image = getImage(eo.get(indexOfCurrentImage+1));
			indexOfCurrentImage++;
		}
	}

	public void translate(int x, int y) {
		rect.translate(x, y);
		textX+= x;
		textY+=y;
	}
	
	public void resize(int percent) {
		this.rect.height = (int)(this.rect.height * ((double)percent/100));
		this.rect.width = (int)(this.rect.width * ((double)percent/100));
	}
	
	public void setSize(int w, int h) {
		this.rect.width = w;
		this.rect.height = h;
	}
	protected Image getImage(String fn) {
		Image img = null;
		try {
			
			img = ImageIO.read(this.getClass().getResource(fn));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	public int getX() {
		return rect.x;
	}
	
	public int getY() {
		return rect.y;
	}
	
	public void updateText(String s) {
		displayText = s;
	}
	public void draw(Graphics g){
		g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
		if (customFont ==null) {
			try {
			    //create the font to use. Specify the size!
			    customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(65f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(customFont);
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		}
		
		if (displayText.length()>0) {
			g.setFont(customFont);
			g.setColor(Color.gray);
			g.drawString(displayText, textX, textY);
			
			g.setColor(Color.DARK_GRAY);
			g.drawString(displayText, textX-2, textY-2);
		}
	}
}
