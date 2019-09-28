
import java.awt.List;
import java.util.ArrayList;

public class Pokedex {
	private ArrayList<pokemon> poke;
	private ArrayList<pokemon> grass;
	private ArrayList<pokemon> water;
	private ArrayList<pokemon> electric;
	private ArrayList<pokemon> fire;
	private ArrayList<pokemon> normal;

	public Pokedex() {
		poke = new ArrayList<pokemon>();
		// just an example
		// pokedex is the collection of all pokemons
		// we have to hard code everything in lol
		// but then we get to just choose any pokemon randomly
		// there are many arraylists in this class to make choosing easy like different
		// types or like different level
		
		poke.add(new pokemon("Hanson", 0, "normal", 2, 0, 100, 100, 100, 100));
		
		poke.add(new pokemon("Bulbasaur", 1, "grass", 1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Ivysaur", 2, "grass",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Venusaur", 3, "grass", 11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Charmander", 4, "fire", 1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Charmeleon", 5, "fire",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Charizard", 6, "fire", 11, 0, 150, 150, 150, 150));
		
//		poke.add(new pokemon("charizard", 6, "fire", 3));
		poke.add(new pokemon("Squirtle", 7, "water",1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Wartortle", 8, "water",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Blastoise", 9, "water",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Caterpie", 10, "bug",1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Metapod", 11, "bug",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Butterfree", 12, "bug",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Weedle", 13, "bug",1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Kakuna", 14, "bug",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Beedrill", 15, "bug",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Bird", 16, "normal",1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Pidgeotto", 17, "normal",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Pidgeot", 18, "normal",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Rattata", 19, "normal",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Raticate", 20, "normal",6, 0, 125, 125, 125, 125));	
		
		poke.add(new pokemon("Spearow", 21, "normal",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Fearow", 22, "normal",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Ekans", 23, "poison",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Arbok", 24, "posion",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Pikachu", 25, "electric",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Raichu", 26, "electric",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Sandshrew", 27, "normal",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Sandslash", 28, "normal",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Growlithe", 58, "fire",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Aracanine", 59, "fire",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Machop", 66, "normal",1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Machoke", 67, "normal",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Machamp", 68, "normal",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Tentacool", 72, "water",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Tentacruel", 73, "water",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Ponyta", 77, "fire",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Rapidash", 78, "fire",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Slowpoke", 79, "water",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Slowbro", 80, "water",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Magikarp", 129, "water",1, 1, 100, 100, 100, 100));
		poke.add(new pokemon("Gyarados", 130, "water",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Lapras", 131, "water",6, 0, 125, 125, 125, 125));
		
		poke.add(new pokemon("Porygon", 137, "normal",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Snorlax", 143, "normal",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Dratini", 147, "fire",1, 2, 100, 100, 100, 100));
		poke.add(new pokemon("Dragonair", 148, "fire",6, 1, 125, 125, 125, 125));
		poke.add(new pokemon("Dragonite", 149, "fire",11, 0, 150, 150, 150, 150));
		
		poke.add(new pokemon("Mewtwo", 150, "electric",11, 0, 150, 150, 150, 150));
		
		
		for (pokemon p : poke) {
//			if (p.typeToNum() == 0)
//				normal.add(p);
//			if (p.typeToNum() == 1)
//				fire.add(p);
//			if (p.typeToNum() == 2)
//				water.add(p);
//			if (p.typeToNum() == 3)
//				electric.add(p);
//			if (p.typeToNum() == 4)
//				grass.add(p);
		}
	}
	public ArrayList<pokemon> getPokeList()
	{
		return poke;
	}
		
	public pokemon get(String s) {
		for (int i = 0; i <poke.size();i++) {
			if (poke.get(i).getName().equals(s)) {
//				System.out.println("pokedex "+poke.get(i));
//				System.out.println("pokedex "+poke.get(i));
//				System.out.println("pokedex "+poke.get(i));
				pokemon temp = new pokemon(poke.get(i));
//				System.out.println("instance "+temp);
				return temp;
			}
		
		}
		return null;
	}
	
	public pokemon get(int pokeNum) {
		for (int i = 0; i < poke.size();i++) {
			if(poke.get(i).getPokeNum()==pokeNum) {
				return new pokemon(poke.get(i));
			}
		}
		return null;
	}

	public pokemon getRandPoke() {
		return poke.get((int) ((Math.random()) * (poke.size())));
	}

	public pokemon getRandWater() {
		return poke.get((int) ((Math.random()) * (water.size())));
	}

	public pokemon getRandNormal() {
		return poke.get((int) ((Math.random()) * (normal.size())));
	}

	public pokemon getRandFire() {
		return poke.get((int) ((Math.random()) * (fire.size())));
	}

	public pokemon getRandElectric() {
		return poke.get((int) ((Math.random()) * (electric.size())));
	}

	public pokemon getRandGrass() {
		return poke.get((int) ((Math.random()) * (grass.size())));
	}
	public ArrayList<pokemon> getPokedex() {
		
		return poke;
	}
	
	public ArrayList<pokemon> getRandomPokemonBatch() {
		ArrayList<pokemon> randMon = new ArrayList<pokemon>();
		for (int i = 0 ; i < 8; i ++) {
			int rand = (int) (Math.random() * poke.size());
			randMon.add(new pokemon(poke.get(rand)));
		}
		return randMon;
	}
	
	public ArrayList<pokemon> getRandomLevelBatch(int level) {
		ArrayList<pokemon> specifiedLevel = new ArrayList<pokemon>();
		for (int i = 0 ; i < poke.size(); i ++) {
			if (poke.get(i).getLevel()==level) {
				specifiedLevel.add(poke.get(i));
			}
		}
		return specifiedLevel;
	}

}
