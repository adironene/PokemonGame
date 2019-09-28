import java.awt.*;
import java.io.IOException;
public abstract class Events {
	public Events() {
		// TODO Auto-generated constructor stub
	}

	public abstract void moveObjects();
	//public abstract void animateMove(Move x);
	public void draw(Graphics g) {

	}
	
	public abstract void setIntroInFinished(boolean b);
	
	public abstract void drawFade(Graphics g);
	
	public abstract void continueBattle();
	
	public abstract void endBattle();
	

}
