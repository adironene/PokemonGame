import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BattleTile extends Tile {
	NPC trainer;
	static ArrayList<NPC> undefeatedTrainers = new ArrayList<NPC>(); 
	private BattleEvent battleEvent;

	
	public BattleTile(NPC t) {
		super();
		trainer = t;

	}
	
	public boolean isActive(ArrayList<String> names) {
		if(names.indexOf(trainer.getName()) == -1) {
			return true;
		}
		this.setEventRunning(false);
		return false;
	}
	
	public void triggerEvent() {
		Movedex md = new Movedex();
		for (int i = 0 ; i < trainer.getPokemon().size();i++) {
			pokemon curPokemon = trainer.getPokemon().get(i);
			curPokemon.learnMove(md.getMoveByType(curPokemon.typeToNum(), 0));
			curPokemon.learnMove(md.getMoveByType(curPokemon.typeToNum(), 1));
		}
		battleEvent = new BattleEvent(this.player, this.trainer);
		battleEvent.initializeMove();
		eventRunning = true;
	}
	public void moveEventObjects() {
		battleEvent.moveObjects();
	}
	public void updateBattle() {
		battleEvent.updateBattle();
		if (battleEvent.battleFinished){
		//	System.out.println("BATTLE FINISHED");
			
			this.eventRunning = false;
		}
	}
	public void updateMove() {
		battleEvent.updateMove();
//		battleEvent.updateEnemyMove();
	}
	
		public NPC getTrainer() {
			return trainer;
	}
		
		public void setIntroInFinished(boolean b) {
			battleEvent.setIntroInFinished(b);
		}
	
		
		public void draw(Graphics g) {
			battleEvent.draw(g);
		}

		public void chooseOption(KeyEvent e) {
			battleEvent.keyPressed(e);
			
		}


}

