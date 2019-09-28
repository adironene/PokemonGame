import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public  abstract class Tile {
	public boolean eventRunning = false;
	private String type = "safe";
	int xCo;
	int yCo;
	
	Player player = null;

	public Tile(int x, int y) {
		xCo = x;
		yCo = y;
	}
	public Tile() {
		xCo = 0;
		yCo = 0;
	}
	
	public void addPlayer(Player plr) {
		player = plr;
	}
	public boolean isEventRunning() {
		return eventRunning;
	}
	public void setEventRunning(boolean eventRunning) {
		this.eventRunning = eventRunning;
	}
	
	public boolean isActive() {
		System.out.println("This tile is OBSOLETE! Make sure you DID NOT initialize a Tile and instead initialized a battle/grass/checkpoint tile!");
		return false;
	}
	
	public boolean isActive(ArrayList<String> s) {
		System.out.println("This tile is OBSOLETE! Make sure you DID NOT initialize a Tile and instead initialized a battle/grass/checkpoint tile!");
		return false;
	}
	
	
	protected abstract void triggerEvent();
	public void removePlayer() {
		player = null;;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getType() {
		return type;
	}
	public void draw(Graphics g) {
		
	}
	protected abstract void setIntroInFinished(boolean b);


}