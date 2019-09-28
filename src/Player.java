import java.util.*;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.Graphics;

public class Player {

	private String nickName;
	private List<pokemon> pokemon = new ArrayList<pokemon>();
	// 0 index of <items > is # of pokeballs
	// 1 index of<items> is # of potion
	private final int POKEBALL_INDEX = 0, POTION_INDEX = 1, POKECOINS_INDEX = 2;
	private List<Integer> items = new ArrayList<Integer>();
	private int fullHeals = 2;
	private int playerOffset; // makes it so player stands in middle of tile, not on edge
	private int checkPointRectX, checkPointRectY;
	private boolean survivedBattle = false;

	private int checkPointxCo, checkPointyCo;
	public int camxOffset, camyOffset; // for smooth camera
	private int xCo, yCo; // based on 2d array
	private int playerSize; // AKA tileSize
	private Rectangle rect; // for drawing purposes
	private Image image;
	ArrayList<String> trainersDefeated = new ArrayList<String>(); // if u beat a trainer, the trainer's name gets added
	// to here so u can check if u already beat em
	private int age;
	private int motion;
	private String direction;
	private String movingDirection = "Up";
	private int walkIncrement;
	private int walkTime = 9; // this determines how long it takes 2 go back to standing stance
	private String gender;
	private int walkTimer = walkTime;
	private boolean currentlyWalking;

	public Player(String n, int x, int y, int ps, String gen, int age) {
		this.age = age;
		checkPointxCo = 16;
		checkPointyCo = 58;
		playerOffset = ps / 5;
		walkIncrement = ps / walkTime;
		nickName = n;
		xCo = x;
		yCo = y;
		playerSize = ps;
		rect = new Rectangle(x * ps, y * ps, ps * 34 / 46, ps);
		checkPointRectX = x * ps;
		checkPointRectY = y * ps;
		image = getImage("playerPNG/characterUp0.png");
		gender = gen;
		items.add(this.POKEBALL_INDEX, 5);
		items.add(this.POTION_INDEX, 2);
		items.add(this.POKECOINS_INDEX, 10);
	}

	//new methods below
	public void removePokemon(int index) {
		if (pokemon.size() > 1)
			pokemon.remove(index);
		else
			System.out.println("Can't remove your only pokemon!");
	}
	public int getItemsIndex(String s) {
		if(s.equals("pokeballs")||s.equals("pokeball"))
			return 0;
		else if(s.equals("potions")||s.equals("potion"))
			return 1;
		else if(s.equals("pokecoins")||s.equals("pokecoin"))
			return 2;
		return -1;
	}
	public void changeCoins(int num) {
		this.items.set(this.POKECOINS_INDEX, this.getNumItems(this.POKECOINS_INDEX)+num);
	}
	public void shiftPokemonOrder() {
		for(int i = 0; i < pokemon.size() ; i++)
			System.out.print(pokemon.get(i).getName() + " , ");
			System.out.println();
		pokemon.add(pokemon.remove(0));
		for(int i = 0; i < pokemon.size() ; i++)
			System.out.print(pokemon.get(i).getName()+ " , ");
		System.out.println();

	}
	//new methods above
	
	public void healAll() {
		for (pokemon pok : this.getPokemon())
			pok.healFull();
	}

	public void healPokemon(int ind) {
		if (pokemon.get(ind).getBattleHP() != pokemon.get(ind).getMaxHP()) {
			if(this.items.get(1)>0) {
			pokemon.get(ind).healFull();

			this.items.set(this.POTION_INDEX, this.getNumItems(POTION_INDEX) - 1);
			}
			else {
				
			}
		}
		else {
			
		}
	}

	public List<Integer> getItems(){
		return items;
	}
	public int getHeals() {
		return fullHeals;
	}

	public int getAge() {
		return age;
	}

	public void playerSurvivedBattle(boolean i) {
		survivedBattle = i;
	}

	public boolean isPlayerSurvived() {
		return survivedBattle;
	}

	public boolean hasFainted() {
		int numP = pokemon.size();
		int total = 0;
		for (int i = 0; i < numP; i++) {
			total += pokemon.get(i).getBattleHP();
		}

		if (total <= 0) {
			return true;
		}
		return false;
	}
//	public boolean hasWon() {
//		if ()
//	}

	public pokemon getNextAvailablePokemon() {
		for (int i = 0; i < pokemon.size(); i++) {
			if (pokemon.get(i).getBattleHP() > 0) {
				return pokemon.get(i);
			}
		}
		return null;
	}

	public ArrayList<String> getTrainersDefeated() {
		return trainersDefeated;
	}

	public void addDefeatedTrainer(String s) {
		trainersDefeated.add(s);
	}

	public pokemon getPokemon(String pkmnNm) {
		for (int i = 0; i < pokemon.size(); i++) {
			if (pokemon.get(i).getName().equals(pkmnNm))
				return pokemon.get(i);
		}
		return null;
	}

	public String getGender() {
		return gender;
	}

	public pokemon getAlivePokemon() {
		for (int i = 0; i < pokemon.size(); i++) {
			if (pokemon.get(i).getBattleHP() > 0)
				return pokemon.get(i);
		}
		return null;
	}

	public int getCheckPointRectX() {
		return checkPointRectX;
	}

	public void saveCheckPointRectX() {
		this.checkPointRectX = rect.x;
	}

	public int getCheckPointRectY() {
		return checkPointRectY;
	}

	public void saveCheckPointRectY() {
		this.checkPointRectY = rect.y;
	}

	public void addPokemon(pokemon pkmn) {
		pokemon.add(pkmn);
	}

	public void setRectX(int i) {
		rect.x = i;
	}

	public void setRectY(int i) {
		rect.y = i;
	}

	public String getNickName() {
		return nickName;
	}

	public List<pokemon> getPokemon() {
		return pokemon;
	}

	public int getNumItems(int index) {
		return items.get(index);
	}

//	public void setPotion(int i) {
//		this.items.set(this.POTION_INDEX,i);
//	}

	public void throwPokeBall() {
		items.set(POKEBALL_INDEX, getNumItems(this.POKEBALL_INDEX) - 1);
	}

	public int getCheckPointxCo() {
		return checkPointxCo;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPokemon(List<pokemon> pokemon) {
		this.pokemon = pokemon;
	}

	public void setItems(int num, int index) {
		this.items.set(index, num);
	}

	public void setCheckPointxCo(int checkPointxCo) {
		this.checkPointxCo = checkPointxCo;
	}

	public void setCheckPointyCo(int checkPointyCo) {
		this.checkPointyCo = checkPointyCo;
	}

	public void setxCo(int xCo) {
		this.xCo = xCo;
	}

	public void setyCo(int yCo) {
		this.yCo = yCo;
	}

	public int getCheckPointyCo() {
		return checkPointyCo;
	}

	public int getxCo() {
		return xCo;
	}

	public int getyCo() {
		return yCo;
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

	public int getRectY() {
		return rect.y;
	}

	public int getRectX() {
		return rect.x;
	}

	public void move(String s) {
		if (s.equals("left")) {
			movingDirection = "Left";
			rect.translate(-playerSize, 0);
			xCo--;
		}
		if (s.equals("right")) {
			movingDirection = "Right";
			rect.translate(playerSize, 0);
			xCo++;
		}
		if (s.equals("up")) {
			movingDirection = "Up";
			rect.translate(0, -playerSize);
			yCo--;
		}
		if (s.equals("down")) {
			movingDirection = "Down";
			yCo++;
			rect.translate(0, playerSize);
		}
		currentlyWalking = true;

		System.out.println("xCo: " + xCo + "yCo: " + yCo);
	}

	public void updateDirection(String s) {
		walkTimer = walkTime;
		String which;
		if (motion == 1)
			motion = 2;
		else
			motion = 1;
		if (s.equals("left")) {
			direction = "Left";
			which = "playerPNG/" + "character" + direction + motion + ".png";
			image = getImage(which);
		} else if (s.equals("right")) {
			direction = "Right";
			which = "playerPNG/" + "character" + direction + motion + ".png";
			image = getImage(which);
		} else if (s.equals("up")) {
			direction = "Up";
			which = "playerPNG/" + "character" + direction + motion + ".png";
			image = getImage(which);
		} else if (s.equals("down")) {
			direction = "Down";
			which = "playerPNG/" + "character" + direction + motion + ".png";
			image = getImage(which);
		}
	}

	public void updateStance() {
		if (currentlyWalking) {
			walkTimer--;
			if (walkTimer == 0) {
				image = getImage("playerPNG/" + "character" + direction + 0 + ".png");
				currentlyWalking = false;
				walkTimer = walkTime;
			}
		}
	}

	public void updateAnimation() {
		if (currentlyWalking) {
			if (movingDirection.equals("Left")) {
				camxOffset += walkIncrement;
			}
			if (movingDirection.equals("Right")) {
				camxOffset -= walkIncrement;
			}
			if (movingDirection.equals("Up")) {
				camyOffset += walkIncrement;
			}
			if (movingDirection.equals("Down")) {
				camyOffset -= walkIncrement;
			}
		} else {
			camxOffset = 0;
			camyOffset = 0;
		}
	}

	public int getWalkTime() {
		return walkTime;
	}

	public boolean isCurrentlyWalking() {
		return currentlyWalking;
	}

	public int getWalkTimer() {
		return walkTimer;
	}

	public int getWalkIncrement() {
		return walkIncrement;
	}
	
	public void draw(Graphics g, int xo, int yo) {
		g.drawImage(image, rect.x + xo + playerOffset, rect.y + yo, rect.width, rect.height, null);
	}

}