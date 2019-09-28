import java.util.ArrayList;

public class Movedex {

	ArrayList<Move> moves = new ArrayList<Move>();
	ArrayList<Move> normalMoves = new ArrayList<Move>();
	ArrayList<Move> waterMoves = new ArrayList<Move>();
	ArrayList<Move> fireMoves = new ArrayList<Move>();
	ArrayList<Move> electricMoves = new ArrayList<Move>();
	ArrayList<Move> poisonMoves = new ArrayList<Move>();
	ArrayList<Move> bugMoves = new ArrayList<Move>();
	ArrayList<Move> grassMoves = new ArrayList<Move>();
	
	public Movedex() {
		 Move Tackle = new Move("Assault", "Normal", 35, 100, 35);
		 Move Cut = new Move("Affirmative Action","Normal",35,95,30);
		 Move Slam = new Move("Choke","Normal",40,75,20);
		 Move Strength= new Move("Cut","Normal",48,100,15);
//		 Move Pound = new Move("Pound", "Normal", 40, 100, 35);
//		 Move Scratch = new Move("Scratch", "Normal", 40, 100, 35);
//		 Move BodySlam = new Move("Body Slam", "Normal", 15, 100, 85);
//		 Move Headbutt = new Move("Headbutt", "Normal", 15, 100, 70);
//		 Move CometPunch = new Move("Comet Punch", "Normal", 15, 85, 80);
		 normalMoves.add(Tackle);
		 normalMoves.add(Cut);
		 normalMoves.add(Slam);
		 normalMoves.add(Strength);
		 //water moves
		 Move Aquajet = new Move("Squirt", "Water", 45, 100, 40); 
		 Move AquaTail = new Move("Wet Tornado", "Water", 40, 90, 90);
		 Move Bubble = new Move("Bubble", "Water", 35, 100, 30);
		 Move Waterfall = new Move("Waterfall", "Water", 50, 100, 80);
		 waterMoves.add(Aquajet);
		 waterMoves.add(AquaTail);
		 waterMoves.add(Bubble);
		 waterMoves.add(Waterfall);
		 //fire moves
		 Move FlameWheel = new Move("FLAME THROWA", "Fire", 45, 100, 60);
		 Move Flamethrower = new Move("Big Fire", "Fire", 50, 100, 90);
		 Move FirePunch = new Move("Fire Punch", "Fire", 45, 100, 75);
		 Move Ember = new Move("Fire Wheel", "Fire", 42, 100, 40);
		 fireMoves.add(Ember);
		 fireMoves.add(FlameWheel);
		 fireMoves.add(Flamethrower);
		 fireMoves.add(FirePunch);
		 //electric moves
		 Move ThunderShock = new Move("Big Shook", "Electric", 45,100,30);
		 Move ThunderPunch = new Move("Shook Punch", "Electric", 40,100,15);
		 Move ThunderFang= new Move("Shook Tooth", "Electric", 40,95,15);
		 Move Discharge = new Move("Discharge", "Electric", 48,100,15);
		 electricMoves.add(ThunderShock);
		 electricMoves.add(ThunderPunch);
		 electricMoves.add(ThunderFang);
		 electricMoves.add(Discharge);
		 //bug moves, i added these in case yall want a bug type
		 Move BugBite = new Move("Itchy Bite","Bug",40,100,20);
		 Move BugBuzz = new Move("Bug Buzz", "Bug", 45,100,10);
		 Move StruggleBug = new Move("Struggle Bug", "Bug",45,100,20);
		 Move LethalBite = new Move("Lethal Bite", "Bug",50,100,20);
		 bugMoves.add(BugBite);
		 bugMoves.add(BugBuzz);
		 bugMoves.add(StruggleBug);
		 bugMoves.add(LethalBite);
		 //poison moves
		 Move Acid = new Move("Fart", "Poison", 40,100,30);
		 Move PoisonSting = new Move("Toxic Waste", "Poison", 45,100,10);
		 Move Toxic = new Move("Intoxication","Poison", 45,50,15);
		 Move Pollution = new Move("Pollution","Poison", 50,50,15);
		 poisonMoves.add(Acid);
		 poisonMoves.add(PoisonSting);
		 poisonMoves.add(Toxic);
		 poisonMoves.add(Pollution);
		 
		 //grass moves
		 Move VineWhip = new Move("Allergy Season", "Grass", 45, 100, 10);
		 Move NastyGrass = new Move("Nasty Grass", "Grass", 50, 100, 10);
		 Move GrassSpank = new Move("GrassSpank", "Grass", 40, 100, 10);
		 Move AllergySeason = new Move("Chuck Grass", "Grass", 45, 100, 10);
		 grassMoves.add(VineWhip);
		 grassMoves.add(NastyGrass);
		 grassMoves.add(GrassSpank);
		 grassMoves.add(AllergySeason);
		 
	}
//	public Move getMove(int index) {
//		return moves.get(index);
//	}
	public Move getFireMove(int index) {
		return fireMoves.get(index);
	}
	public Move getWaterMove(int index) {
		return waterMoves.get(index);
	}
	public Move getNormalMove(int index) {
		return normalMoves.get(index);
	}
	public Move getElectricMove(int index) {
		return electricMoves.get(index);
	}
	public Move getPoisonMove(int index) {
		return poisonMoves.get(index);
	}
	public Move getBugMove(int index) {
		return bugMoves.get(index);
	}
	public Move getGrassMove(int index) {
		return grassMoves.get(index);
	}
	
	public Move getMoveByType(int typeAsNum, int index) {
		switch (typeAsNum) {
		case 0:
			return normalMoves.get(index);
		case 1:
			return fireMoves.get(index);
		case 2:
			return waterMoves.get(index);
		case 3:
			return electricMoves.get(index);
		case 4:
			return grassMoves.get(index);
		case 5:
			return poisonMoves.get(index);
		case 6:
			return bugMoves.get(index);
		}
		return normalMoves.get(0);
		
	}
//	normal = 0
//	fire = 1
//	water = 2
//	electric = 3
//	grass = 4
//	poison = 5
//	bug = 6

}
