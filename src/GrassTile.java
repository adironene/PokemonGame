import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GrassTile extends Tile {
	List<pokemon> potentialPokemon = new ArrayList<pokemon>();
	private int PROB_POKEMON = 10;
	private EncounterEvent encounterEvent;
	private pokemon wildPokemon;
	
	public GrassTile (List<pokemon> pp) {
		super();
		potentialPokemon = pp;
	}
	
	public GrassTile (List<pokemon> pp, int probability) {
		super();
		potentialPokemon = pp;
		PROB_POKEMON = probability;
	}
	
	
	public boolean isActive() {
		int rand =  (int) (Math.random() * 100);
		System.out.println("lucky number "+rand);
		if(rand<=PROB_POKEMON) {
			int randPokemon = (int) (Math.random()*potentialPokemon.size());
			wildPokemon = potentialPokemon.get(randPokemon);
			Movedex md = new Movedex();
			wildPokemon.learnMove(md.getMoveByType(wildPokemon.typeToNum(), 0));
			wildPokemon.learnMove(md.getMoveByType(wildPokemon.typeToNum(), 1));
			
			System.out.println("START EVENT");
			return true;
		}
		this.setEventRunning(false);
		return false;
	}

	
	public void moveEventObjects() {
		encounterEvent.moveObjects();
	}
	public void updateBattle() {
		encounterEvent.updateBattle();
		if (encounterEvent.battleFinished){
			System.out.println("BATTLE FINISHED");
			this.eventRunning = false;
		}
	}
	
	public void updateMove() {
		encounterEvent.updateMove();
//		battleEvent.updateEnemyMove();
	}
	
		
		public void draw(Graphics g) {
			encounterEvent.draw(g);
		}

		public void chooseOption(KeyEvent e) {
			encounterEvent.keyPressed(e);
			
		}


		@Override
		protected void triggerEvent() {
			encounterEvent = new EncounterEvent(this.player, wildPokemon);
			encounterEvent.initializeMove();
			eventRunning = true;
			
		}


		@Override
		protected void setIntroInFinished(boolean b) {
			encounterEvent.setIntroInFinished(b);
			
		}
}


