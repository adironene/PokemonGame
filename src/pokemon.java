import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.awt.*;
import javax.imageio.ImageIO;

public class pokemon { // all parameters requiring a string should be enetered in lowercase
	private final String PATH_PREFIX = "Battlers/";
	private final int STAT_INCREMENT =5; // might change later if  we decide to make stat changes for each pokemon different
	static final double[][] moveChart = new double[][]{
		{1.0,1.0,1.0,1.0,1.0,1.0,1.0},
		{1.0,0.5,0.5,1.0,2.0,1.0,2.0}, 
		{1.0,2.0,0.5,1.0,0.5,1.0,1.0}, 
		{1.0,1.0,2.0,0.5,0.5,1.0,1.0},
		{1.0,0.5,2.0,1.0,0.5,0.5,0.5}, 
		{1.0,1.0,1.0,1.0,1.0,2.0,0.5},
		{1.0,0.5,1.0,1.0,2.0,0.5,1.0}
	};
	private Move[] learnList = new Move[20];
	private String name;
	private String type;
	private int level;
//	private int stage; // 1 being the base, and 3 being max end stage
	private int exp_Needed;
	private int pokeNumb;
	private ArrayList move = new ArrayList();
//	private Image frontSprite;
//	private Image backSprite;
	// Below are pokemon stats that will be utlized during battle
	private boolean isAlive;
	private int hp;
	private int battleHp;
	private boolean readyToEvolve = false;
	private int evolutionsLeft;


	private int attack;
	private int defense;
	private int speed;
	private Move[] moveList = new Move[4];

	public pokemon(pokemon dummy) {
		this.name = dummy.getName();
		this.pokeNumb = dummy.pokeNumb;
		this.type = dummy.type;
		this.level = dummy.level;
		this.evolutionsLeft = dummy.getEvolutionsLeft();
		this.hp = dummy.getMaxHP();
		this.battleHp= dummy.getMaxHP();
		this.attack = dummy.getAttack();
		this.defense = dummy.getDefense();
		this.speed = dummy.getSpeed();
		this.isAlive = true;
		
		// images might need scaling - if so do it after testing the class
//		this.frontSprite = getImage("Front");
//		this.backSprite  = getImage("Back");
		this.exp_Needed = level *((attack + defense + speed)/3);
		this.moveList = dummy.getMoves();
	}
	// constructor
	public pokemon(String Name, int POKENUMB, String Type, int evoL) {
		name = Name;
		pokeNumb = POKENUMB;
		type = Type;
		evolutionsLeft = evoL;
		level =1;
		attack = 100;
		defense = 100;
		battleHp = 100;
		hp = 100;
		speed = 100;
		exp_Needed = level *((attack + defense + speed)/3);
	}

	public pokemon(String Name, int POKENUMB, String Type, int Level, int evoL, int HP, int Attack, int Defense, int Speed) {
		name = Name;
		pokeNumb = POKENUMB;
		type = Type;
		level = Level;
		evolutionsLeft = evoL;
		hp = HP;
		battleHp= HP;
		attack = Attack;
		defense = Defense;
		speed = Speed;
		// images might need scaling - if so do it after testing the class
//		frontSprite = getImage("Front");
//		backSprite  = getImage("Back");
		exp_Needed = level *((Attack + Defense + Speed)/3);
	}
	public pokemon(String Name, int POKENUMB, String Type, int Level, int evoL, int HP, int Attack, int Defense, int Speed, Move[] makeList) {
		name = Name;
		pokeNumb = POKENUMB;
		type = Type;
		level = Level;
		evolutionsLeft = evoL;
		hp = HP;
		battleHp= HP;
		attack = Attack;
		defense = Defense;
		speed = Speed;
		isAlive = true;
		moveList= makeList;
		// images might need scaling - if so do it after testing the class
//		frontSprite = getImage("Front");
//		backSprite  = getImage("Back");
		exp_Needed = level *((Attack + Defense + Speed)/3);
	}

	public Move getMove(Move move) {
		for (int i = 0; i < 4;i++) {
			if (move.getName().equals(moveList[i].getName()))
				return moveList[i];
		}
		return null;
	}

	public void clearMoves() {
		this.moveList = new Move[4];
	}
	public Move getMove(int i) {
		return moveList[i];
	}

	public Move getRandomMove() {
		int numMoves = 0;
		for (int i = 0; i < moveList.length;i++) {
			if (moveList[i]!=null)
				numMoves++;
		}
		int rand = (int) (Math.random() * numMoves);
		return moveList[rand];
	}

	//	public int getRandom() {
	//		return Math.random();
	//	}readyToEvolve = false;
	public Move[] getMoves() {
		return moveList;
	}
	public String getName() {
		return name;
	}

	//	public void attack(int i, pokemon other){
	//		moveList[i].decreasePP();
	//		if(moveList[i].ifHit()){
	//			other.battleHp-=moveList[i].totalPower(other);
	//		}
	//	}
	public void attack(Move move, pokemon other){
		for (int i = 0; i < 4;i++) {
			if(moveList[i]!=null) {
				if (move.getName().equals(moveList[i].getName())) {
//					moveList[i].decreasePP();
//					if(moveList[i].ifHit()) {
//						other.battleHp-= moveList[i].totalPower(other)+attack-other.defense;
						other.battleHp-= Math.abs((((2 * this.level)/5 + 2) * moveList[i].totalPower(other) * 100 / other.defense)/4 + 2);
						if(other.battleHp < 0 )
							other.battleHp = 0;
//					}
				}
			}
		}
	}
	public int getBattleHP(){
		return this.battleHp;
	}

	public int getExp() {
		return exp_Needed;
	}
	public int getMaxHP() {
		return this.hp;
	}

	public int getSpeed() {
		return speed;
	}

	public void  updateEvolveStatus(boolean a)
	{
		readyToEvolve = a;
	}
	public int getLevel() {
		return level;
	}
	public String getStringImage(String side) {
		String path;
		String numZ="";
		if(pokeNumb<10)
			numZ = "00";
		else if(pokeNumb>=10&&pokeNumb<100)
			numZ = "0";

		if(side.equals("back")) {

			path = PATH_PREFIX+numZ+pokeNumb+"b.png";
		}
		else
			path = PATH_PREFIX+numZ+pokeNumb+".png";

		return path;
	}
	protected  Image getImage(String side) {
		Image img = null;
		String path ="";
		String numZ="";

		if(pokeNumb<10)
			numZ = "00";
		else if(pokeNumb>=10&&pokeNumb<100)
			numZ = "0";


		if(side.equals("back")) {
			path = PATH_PREFIX+numZ+pokeNumb+"b.png";
		}
		else
			path = PATH_PREFIX+numZ+pokeNumb+".png";


		try {

			img = ImageIO.read(this.getClass().getResource(path));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}


	public void drawPokemon(Graphics g ,String side, int x, int y)
	{
		g.drawImage(getImage(side), x, y, null);
	}
	public void drawPokemon(Graphics g ,String side,int size,int x, int y)
	{
		g.drawImage(getImage(side), x, y,size,size, null);
	}
	public void getHurt(int damage)
	{
		battleHp = battleHp - damage;
		if(battleHp<= 0)
		{
			isAlive = false;
		}
	}
	public void healFull()
	{
		battleHp = hp;
		isAlive = true;

	}
	public void healNum(int healPoints)
	{
		if(isAlive == true)
		{
			battleHp = battleHp + healPoints;
			if(battleHp>hp)
			{
				battleHp = hp;
			}
		}
	}
	
	public int calcExpNeeded()
	{
		return exp_Needed = level*((attack + defense + speed)/3);
	}
	public void gainExp(int expGain)
	{
		int leftOver = -1*(exp_Needed - expGain);// will only use if the pokemon levels up
		exp_Needed = exp_Needed - expGain;
		if(exp_Needed <=0)
		{
			levelUp();
			exp_Needed = level*((attack+defense+speed+hp)/4); 
		}
	}
	
	public boolean pokemonHasMove(Move m) {
		for (int i = 0 ; i <  moveList.length;i++) {
			if (m.getName().equals(m.getName()))
				return true;
		}
		return false;
	}
	public void levelUp()
	{
		level++;
		attack  += STAT_INCREMENT;
		defense += STAT_INCREMENT;
		speed   += STAT_INCREMENT;
		hp += STAT_INCREMENT;
		battleHp += STAT_INCREMENT;
		
		exp_Needed = calcExpNeeded();
		if(level== 5)
		{
			updateEvolveStatus(true);
		}
		else if(level== 10)
		{
			updateEvolveStatus(true);
		}
	}
	
	public int getEvolutionsLeft() {
		return evolutionsLeft;
	}
	public void decreaseEvolutionsLeft() {
		evolutionsLeft--;
	}
	public int getPokeNum() {
		return pokeNumb;
	}
	public void evolvePokemonName(String newName) {
		name = newName;
	}
	
	public void evolvePokemonNum() {
		pokeNumb++;
	}
	public boolean checkEvolveStatus() {
		return readyToEvolve;
	}
//	public void evolveMon()
//	{
//		this.name = "";
//		this.pokeNumb++;
//
//		this.stage++;
//		this.hp +=this.level;
//		this.battleHp=hp;
//		this.attack ++;
//		this.defense++;
//		this.speed++;
//		this.isAlive = true;
//		// images might need scaling - if so do it after testing the class
////		this.frontSprite = getImage("Front");
////		this.backSprite  = getImage("Back");
//		this.exp_Needed = level *((attack + defense + speed)/3);
//		//change moves?
//	}
//	public pokemon evolveInto(int pokeNum, int stage)
//	{
//		for(int i = 0; i<list.getPokeList().size(); i++)
//		{
//			if(list.getPokeList().get(i).pokeNumb == pokeNum && list.getPokeList().get(i).stage == stage )
//			{
//				return list.getPokeList().get(i);
//			}
//		}
//		
//		return null;
//	}
	
	public int returnEmptyIndex()
	{
		for(int i =0 ; i<moveList.length; i++)
		{
			if(moveList[i] == null)
			{
				return i;
			}
		}
		return -1;
	}
	public int getRemoverMove()
	{
		int x = 1; // change to let user input
		// some graphcial implementation that works with mouse to allow the user to the the boxes
		return x;
	}
	public void removeMove(int moveIndex)
	{
		moveList[moveIndex] = null;
	}

	public void learnMove(Move Learnable)
	{
		if(returnEmptyIndex()!=-1)
		{
			moveList[returnEmptyIndex()] = Learnable;
			return;
		}
//		else
//		{
//			removeMove(getRemoverMove()); // Again needs a graphical user involved function to make complete sense
//		}
		//		learnMove(Learnable);
	}



	//this is to access the damn type chart shit for advantages/disadvantages
	public int typeToNum() {
		int typeNum = -1;
		String tempType = type.toLowerCase();
		if (tempType.equals("normal")){
			typeNum = 0;
		}
		else if (tempType.equals("fire")){
			typeNum = 1;
		}
		else if (tempType.equals("water")){
			typeNum = 2;
		}
		else if (tempType.equals("electric")){
			typeNum = 3;
		}
		else if (tempType.equals("grass")){
			typeNum = 4;
		}
		else if (tempType.equals("poison")){
			typeNum = 5;
		}
		else if (type.equals("bug")) {
			typeNum = 6;
		} 
		else{
			return 0;//returns -1 if none if the type is invalid
		}
		return typeNum;

	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	// I don't thing we really need getters/setters for these stats as the only time they change are withing Pokemon class functions
	// However if we need them then they should be placed below this statement


}
