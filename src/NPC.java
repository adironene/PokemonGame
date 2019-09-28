import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class NPC {
	private List<pokemon> pokemon = new ArrayList<pokemon>();
	String name;
	Image img;
	public final static String HANSON = "Take out UUUUUHH sheet of paper.";
	public final static String FBOY= "yo gurl lemme holler at ya";
	public final static String DESPERATE = "If I beat you will I get an A in this class?";
	public final static String INSECURE = "I don't know what I'm doing";
	public final static String MEAN = "You're ugly";
	public final static String VAPEGOD = "Don't pee in the JUUL room!";
	public final static String BROKE= "I need your money";
	public final static String RICH = "I have airpods and I can't hear broke";
	public final static String CREEP = "I like the way you talk in your sleep";
	public final static String SOUL = "A soul for a soul";
	public final static String EX = "How could you break my heart and leave me like this??";
	public final static String HERO = "I traveled back in time to prevent you from taking over the world";
	public final static String THANOS = "Dread it.. Run from it.. Destiny still arives.";
	private String line = "Who are you?";
	
	
	public NPC(String n,  List<pokemon> pkmn) {
		name = n;
		pokemon = pkmn;

		
	}
	public NPC(String n,String nickname,  List<pokemon> pkmn) {
		name = n;
		pokemon = pkmn;
		setLine(nickname);
	
		
		
	}
	
	public void setLine(String string){
		String str = string.toLowerCase();
		if (str.equals("hanson"))
			line = HANSON;
		if (str.equals("fboy"))
			line = FBOY;
		if (str.equals("desperate"))
			line = DESPERATE;
		if (str.equals("insecure"))
			line = INSECURE;
		if (str.equals("mean"))
			line = MEAN;
		if (str.equals("vapegod"))
			line = VAPEGOD;
		if (str.equals("broke"))
			line = BROKE;
		if (str.equals("rich"))
			line = RICH;
		if (str.equals("creep"))
			line = CREEP;
		if (str.equals("soul"))
			line = SOUL;
		if (str.equals("ex"))
			line = EX;
		if (str.equals("hero"))
			line = HERO;
		if (str.equals("thanos"))
			line = THANOS;
	}
	public List<pokemon> getPokemon() {
		return pokemon;
	}

	public String getName() {
		return name;
	}
	public String getLine(){
		return line;
	}

	public void setPokemon(List<pokemon> pokemon) {
		this.pokemon = pokemon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void healPokemon() {
		for (int i = 0; i < pokemon.size();i++) {
			pokemon.get(i).healFull();
		}
	}

	public pokemon getNextAvailablePokemon() {
		for (int i = 0;i<pokemon.size();i++) {
			if (pokemon.get(i).getBattleHP() > 0){
				return pokemon.get(i);
			}
		}
		return null;
	}
	public void setPersonality(int p){
		
	}
}
