import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

import java.awt.AlphaComposite;

public class Map {
	Movedex movedex = new Movedex();
	Player player;
	Pokedex pokedex = new Pokedex();
	// ArrayList<NPC> trainers;
	private Font customFont;
	private String soundTrack = "";
	private float alpha = 1.0f;
	private boolean specialEventRunning = true;
	private boolean evolveAnimationDone = false;
	private boolean runEvolution = false;
	private boolean showMessage = false;
	private Events specialEvent = new StarterEvent();
	private boolean dueForEvent;
	private boolean eventRunning = false; // if it's running, u call the event's moveObjects method which is the
	// animation, meanwhile the game also calls draw which draws the animation
	private boolean runTransition = false;
	private int pngScale = 32;
	private int pngMapSize = 2528;
	static int mapmovingxOffset, mapmovingyOffset;
	private int checkpointXOffset, checkpointYOffset;
	private int lastXOffset, lastYOffset; // for storage
	static int xOffset, yOffset;
	private int mapSize;
	private int tileSize;
	private int xPokeSelect = 0, yPokeSelect = 0;
	private boolean enterShop, outOfCoins;
	private boolean showMenu, playerSelection, pokemonSelection, selectedPokemon, heal;
	private pokemon p;
	private double counter = 0;

	NPC jack, steven, mohamed, dick, bob, gymLeader;

	private int xCo, yCo;
	private int xSelection = 1, ySelection = 1;
	private int menuHeight = 800, menuWidth = 800;
	private Tile[][] map;
	private Image image;
	private ArrayList<Coordinate> unsteppable, grasstiles;
	private int screenHeight = (int) pokemonRunner.HEIGHT, screenWidth = (int) pokemonRunner.WIDTH;

	private ArrayList<pokemon> evolveList = new ArrayList<pokemon>();
	private ArrayList<Integer> spotLightX = new ArrayList<Integer>(
			Arrays.asList((int) ((1.3 / 1) * this.getXDimens(800)), (int) ((1.05 / 1) * this.getXDimens(150)),
					(int) ((4.0 / 3) * this.getXDimens(80))));
	private ArrayList<Integer> spotLightY = new ArrayList<Integer>(Arrays.asList((int) ((3) * this.getYDimens(750)),
			(int) ((1.05 / 1) * this.getYDimens(150)), (3 * this.getYDimens(80)) / 2));
	private ArrayList<Integer> pokemonLightX = new ArrayList<Integer>(
			Arrays.asList((int) ((1.0 / 5) * this.getXDimens(400)),
					(int) ((1.0 / 5) * this.getXDimens(400) + (this.screenWidth) * (0.3)),
					(int) ((1.0 / 5) * this.getXDimens(400) + (this.screenWidth) * (0.6))));

	private ArrayList<Integer> pokemonLightY = new ArrayList<Integer>(
			Arrays.asList((int) ((1.0 / 5) * this.getYDimens(400)), (int) ((8.0 / 5) * this.getYDimens(400))));

	private boolean introInFinished;

	Rectangle rect = new Rectangle(-2000, 0, 2000, 150), rect2 = new Rectangle(-2000, 150, 2000, 150),
			rect3 = new Rectangle(-2000, 300, 2000, 150), rect4 = new Rectangle(-2000, 450, 2000, 150),
			rect5 = new Rectangle(-2000, 600, 2000, 150), rect6 = new Rectangle(-2000, 750, 2000, 150),
			rect7 = new Rectangle(-2000, 900, 2000, 150);

	// @param scale of each rectrivate double counter2 = 1001;
	// @param how many rectangles for wid and height TESTING
	public Map(Player plr, int ts, int wid, int len, int spx, int spy, int xo, int yo) {
		map = new Tile[wid][len];
		tileSize = ts;
		image = getImage("Pokemon MAP FOSHO.png");
		mapSize = 2528 * tileSize / pngScale;
		yOffset = xo;
		yOffset = yo;
		lastXOffset = xo;
		lastYOffset = yo;
		checkpointXOffset = xo;
		checkpointYOffset = yo;
		unsteppable = new ArrayList<Coordinate>();
		for (int r = 0; r < len; r++) {
			for (int c = 0; c < wid; c++) {
				map[r][c] = new SafeTile();
			}
		}

		makeUnsteppables(spy, spx);
		makeGrassTiles();

		ArrayList<pokemon> jacksPokemon = new ArrayList<pokemon>(
				Arrays.asList(pokedex.get("Hanson"), pokedex.get("Bulbasaur")));
		ArrayList<pokemon> stevensPokemon = new ArrayList<pokemon>(
				Arrays.asList(pokedex.get("Slowpoke"), pokedex.get("Machoke"), pokedex.get("Rattata")));
		ArrayList<pokemon> mohamedsPokemon = new ArrayList<pokemon>(
				Arrays.asList(pokedex.get("Ponyta"), pokedex.get("Aracanine"), pokedex.get("Magikarp")));
		ArrayList<pokemon> dicksPokemon = new ArrayList<pokemon>(
				Arrays.asList(pokedex.get("Arbok"), pokedex.get("Raichu"), pokedex.get("Fearow")));
		ArrayList<pokemon> bobsPokemon = new ArrayList<pokemon>(Arrays.asList(pokedex.get("Butterfree"),
				pokedex.get("Dragonair"), pokedex.get("Beedrill"), pokedex.get("Tentacruel")));

		// ArrayList<pokemon>(Arrays.asList(pokedex.get("Dragonite"),
		// pokedex.get("Snorlax"), pokedex.get("Porygon"), pokedex.get("Gyarados"),
		// pokedex.get("Hanson")));

		ArrayList<pokemon> gymLeaderPokemon = new ArrayList<pokemon>(
				Arrays.asList(pokedex.get("Dragonite"), pokedex.get("Hanson"), pokedex.get("Snorlax"), pokedex.get("Porygon"),
						pokedex.get("Gyarados"), pokedex.get("Machamp")));

		jack = new NPC("Jack", "Thanos", jacksPokemon);
		steven = new NPC("Steven", "ex", stevensPokemon);
		mohamed = new NPC("Mohamed", "hero", mohamedsPokemon);
		dick = new NPC("Dick", "rich", dicksPokemon);
		bob = new NPC("Bob", "broke", bobsPokemon);
		gymLeader = new NPC("Gym Leader Pikachu", "Hanson", gymLeaderPokemon);
		// randomPokemon.get(1).learnMove(movedex.getMove(4));

		makeBattleTiles();
		makesMiscTiles();
		makeUnsteppables(spx,spy);

		map[spy + 1][spx] = new MessageTile("Hey there! Welcome :) Talk to people or read signs for more Tips!");
		map[spy][spx + 1] = new MessageTile("Hey there! Welcome :) Talk to people or read signs for more Tips!");
		map[spy][spx - 1] = new MessageTile("Hey there! Welcome :) Talk to people or read signs for more Tips!");
		map[50][24] = new MessageTile(
				"if you ever run out of items, go to that big, green, square building that says 'card'");

		map[35][70] = new shopTile();
		map[35][67] = new shopTile();
		map[64][30] = new shopTile();
		map[spy][spx].addPlayer(plr);
		player = plr;
	}

	private void makesMiscTiles() {
		// princess one
		map[34][13] = null;
		map[35][13] = new CheckpointTile(13, 35);

		// princess two
		map[41][43] = null;
		map[42][43] = new CheckpointTile(43, 42);

		// tips
		map[58][23] = new MessageTile(
				"Remember! To challenge a pokemon trainer to a pokemon battle, stand in his view!");
		String tip = "Tip: To catch pokemon, walk around in the grass!";
		String tip2 = "Tip: To catch pokemon, walk around in the grass!";
		String tip3 = "Tip: Press esc to navigate through your items!!";
		map[48][25] = new MessageTile(tip);
		map[48][26] = new MessageTile(tip2);
		map[48][27] = new MessageTile(tip3);

		// snorlax's tile!
		GrassTile kingSnorlax = new GrassTile(new ArrayList<pokemon>(Arrays.asList(pokedex.get("Snorlax"))), 100);
		map[28][10] = kingSnorlax;
		map[28][9] = kingSnorlax;
		map[27][10] = null;
		map[27][9] = null;
	}

	public boolean isEventRunning() {
		return eventRunning;
	}

	private void makeBattleTiles() {
		// jack
		for (int c = 6; c <= 12; c++) {
			map[31][c] = new BattleTile(jack);
		}
		map[31][5] = null;

		// steven
		for (int r = 28; r <= 30; r++) {
			map[r][25] = new BattleTile(steven);
		}
		map[27][25] = null;

		// mohamed
		for (int c = 32; c <= 36; c++) {
			map[30][c] = new BattleTile(mohamed);
		}
		map[30][31] = null;

		// dick
		for (int c = 38; c <= 40; c++) {
			map[32][c] = new BattleTile(dick);
		}
		map[37][32] = null;

		// bob
		for (int c = 37; c <= 39; c++) {
			map[35][c] = new BattleTile(bob);
		}
		map[35][36] = null;

		// gym leader
		map[31][51] = new BattleTile(gymLeader);
		map[30][51] = null;
	}

	private void makeGrassTiles() {
		ArrayList<pokemon> randomPokemon1 = new ArrayList<pokemon>(pokedex.getRandomLevelBatch(1));
		ArrayList<pokemon> randomPokemon6 = new ArrayList<pokemon>(pokedex.getRandomLevelBatch(6));
		ArrayList<pokemon> randomPokemon11 = new ArrayList<pokemon>(pokedex.getRandomLevelBatch(11));
		this.grasstiles = new ArrayList<Coordinate>();
		// red start here
		for (int x = 14; x <= 21; x++) {
			for (int y = 44; y <= 47; y++) {
				this.grasstiles.add(new Coordinate(y, x));

			}
		}
		for (int x = 7; x <= 13; x++) {
			for (int y = 44; y <= 45; y++)
				this.grasstiles.add(new Coordinate(y, x));
		}
		for (int x = 11; x <= 13; x++) {
			this.grasstiles.add(new Coordinate(46, x));
		}
		this.grasstiles.add(new Coordinate(44, 6));

		for (int x = 14; x <= 30; x++) {
			for (int y = 34; y <= 37; y++) {
				this.grasstiles.add(new Coordinate(y, x));
			}
		}

		// orange
		for (Coordinate c : grasstiles) {
			map[c.getRow()][c.getCol()] = new GrassTile(randomPokemon1);
		}
		grasstiles.clear();

		this.grasstiles.add(new Coordinate(29, 17));
		this.grasstiles.add(new Coordinate(29, 18));
		this.grasstiles.add(new Coordinate(30, 22));
		for (int x = 23; x <= 24; x++)
			for (int y = 29; y <= 30; y++)
				this.grasstiles.add(new Coordinate(y, x));
		for (int x = 14; x <= 23; x++)
			this.grasstiles.add(new Coordinate(27, x));
		for (int x = 16; x <= 19; x++)
			this.grasstiles.add(new Coordinate(28, x));
		for (Coordinate c : grasstiles) {
			map[c.getRow()][c.getCol()] = new GrassTile(randomPokemon6);
		}
		grasstiles.clear();
		// yellow
		this.grasstiles.add(new Coordinate(46, 43));
		this.grasstiles.add(new Coordinate(43, 40));
		this.grasstiles.add(new Coordinate(46, 37));
		this.grasstiles.add(new Coordinate(27, 37));
		this.grasstiles.add(new Coordinate(30, 40));
		this.grasstiles.add(new Coordinate(41, 37));
		this.grasstiles.add(new Coordinate(39, 40));
		for (int x = 37; x <= 38; x++)
			for (int y = 36; y <= 37; y++)
				this.grasstiles.add(new Coordinate(y, x));
		for (int x = 41; x <= 42; x++)
			for (int y = 39; y <= 40; y++)
				this.grasstiles.add(new Coordinate(y, x));
		for (int x = 38; x <= 40; x++)
			for (int y = 27; y <= 29; y++)
				this.grasstiles.add(new Coordinate(y, x));
		for (int x = 27; x <= 35; x++)
			this.grasstiles.add(new Coordinate(27, x));
		for (int x = 28; x <= 32; x++)
			this.grasstiles.add(new Coordinate(28, x));
		for (Coordinate c : grasstiles) {
			map[c.getRow()][c.getCol()] = new GrassTile(randomPokemon11);
		}
		grasstiles.clear();
	}

	private void makeUnsteppables(int spyy, int spxx) {
		this.unsteppable.add(new Coordinate(34, 13));
		this.unsteppable.add(new Coordinate(58, 24));

		// wooden fence and little trees
		// bikes
		this.unsteppable.add(new Coordinate(13, 33));
		this.unsteppable.add(new Coordinate(65, 18));
		this.unsteppable.add(new Coordinate(65, 24));
		// wooden fence and little trees
		for (int x = 13; x <= 24; x++)
			this.unsteppable.add(new Coordinate(70, x));
		for (int x = 28; x <= 42; x++)
			this.unsteppable.add(new Coordinate(70, x));
		for (int y = 71; y <= 78; y++)
			this.unsteppable.add(new Coordinate(y, 32));
		for (int y = 48; y <= 69; y++)
			this.unsteppable.add(new Coordinate(y, 42));
		for (int x = 28; x <= 41; x++)
			this.unsteppable.add(new Coordinate(48, x));
		for (int x = 28; x <= 31; x++)
			this.unsteppable.add(new Coordinate(71, x));
		for (int x = 14; x <= 24; x++)
			this.unsteppable.add(new Coordinate(71, x));
		for (int y = 48; y <= 51; y++)
			this.unsteppable.add(new Coordinate(y, 13));
		for (int x = 14; x <= 24; x++)
			this.unsteppable.add(new Coordinate(48, x));
		for (int x = 13; x <= 30; x++)
			this.unsteppable.add(new Coordinate(33, x));
		for (int x = 13; x <= 24; x++)
			this.unsteppable.add(new Coordinate(70, x));
		for (int x = 28; x <= 42; x++)
			this.unsteppable.add(new Coordinate(70, x));
		for (int y = 71; y <= 78; y++)
			this.unsteppable.add(new Coordinate(y, 32));
		for (int y = 48; y <= 69; y++)
			this.unsteppable.add(new Coordinate(y, 42));
		for (int x = 28; x <= 31; x++)
			this.unsteppable.add(new Coordinate(71, x));
		for (int x = 14; x <= 24; x++)
			this.unsteppable.add(new Coordinate(71, x));
		for (int y = 48; y <= 51; y++)
			this.unsteppable.add(new Coordinate(y, 13));
		for (int x = 14; x <= 24; x++)
			this.unsteppable.add(new Coordinate(48, x));
		// house red
		for (int r = spyy - 1; r > spyy - 6; r--)
			for (int c = spxx - 1; c < spxx + 4; c++)
				this.unsteppable.add(new Coordinate(r, c));
		// house yellow
		for (int x = 34; x <= 38; x++)
			for (int y = 53; y <= 57; y++)
				this.unsteppable.add(new Coordinate(y, x));
		// house green and blue
		for (int x = 14; x <= 23; x++)
			for (int y = 61; y <= 64; y++)
				this.unsteppable.add(new Coordinate(y, x));
		// flower panel
		for (int x = 15; x <= 17; x++)
			this.unsteppable.add(new Coordinate(49, x));
		for (int x = 19; x <= 21; x++)
			this.unsteppable.add(new Coordinate(49, x));
		for (int x = 29; x <= 31; x++)
			this.unsteppable.add(new Coordinate(49, x));
		// wooden sign
		this.unsteppable.add(new Coordinate(49, 24));
		// trash can
		this.unsteppable.add(new Coordinate(46, 28));
		this.unsteppable.add(new Coordinate(46, 24));
		// light
		for (int i = 55; i <= 57; i++)
			this.unsteppable.add(new Coordinate(i, 23));
		for (int i = 55; i <= 57; i++)
			this.unsteppable.add(new Coordinate(i, 29));
		// lake and trees
		for (int y = 73; y <= 78; y++)
			for (int x = 0; x <= 10; x++)
				this.unsteppable.add(new Coordinate(y, x));
		for (int y = 50; y <= 72; y++)
			for (int x = 0; x <= 12; x++)
				this.unsteppable.add(new Coordinate(y, x));
		this.unsteppable.add(new Coordinate(27, 3));
		this.unsteppable.add(new Coordinate(28, 3));
		// battlers
		this.unsteppable.add(new Coordinate(32, 37));
		this.unsteppable.add(new Coordinate(35, 40));
		// rocks near other town
		this.unsteppable.add(new Coordinate(47, 37));
		this.unsteppable.add(new Coordinate(41, 41));
		this.unsteppable.add(new Coordinate(45, 42));
		this.unsteppable.add(new Coordinate(43, 45));
		this.unsteppable.add(new Coordinate(42, 46));
		this.unsteppable.add(new Coordinate(44, 48));
		this.unsteppable.add(new Coordinate(44, 47));
		this.unsteppable.add(new Coordinate(45, 48));

		this.unsteppable.add(new Coordinate(46, 44));
		for (int x = 43; x <= 47; x++)
			this.unsteppable.add(new Coordinate(40, x));
		for (int x = 42; x <= 46; x++)
			this.unsteppable.add(new Coordinate(47, x));
		for (int y = 75; y <= 78; y++)
			for (int x = 11; x <= 24; x++)
				this.unsteppable.add(new Coordinate(y, x));
		for (int x = 25; x < 28; x++)
			this.unsteppable.add(new Coordinate(78, x));
		for (int y = 75; y <= 78; y++)
			for (int x = 28; x <= 29; x++)
				this.unsteppable.add(new Coordinate(y, x));
		for (int y = 31; y <= 47; y++)
			for (int x = 31; x <= 36; x++)
				this.unsteppable.add(new Coordinate(y, x));
		for (int y = 31; y <= 32; y++)
			for (int x = 13; x <= 30; x++)
				this.unsteppable.add(new Coordinate(y, x));
		for (int x = 3; x <= 44; x++)
			this.unsteppable.add(new Coordinate(26, x));
		for (int x = 34; x <= 44; x++) {
			if (x == 37 || x == 41)
				x++;
			this.unsteppable.add(new Coordinate(49, x));
		}
		for (int x = 43; x <= 46; x++) {
			for (int y = 32; y < 40; y++)
				this.unsteppable.add(new Coordinate(y, x));
		}
		for (int y = 27; y <= 38; y++)
			this.unsteppable.add(new Coordinate(y, 41));
		for (int y = 27; y <= 31; y++)
			this.unsteppable.add(new Coordinate(y, 44));
		// other town
		for (int x = 51; x <= 73; x++) {
			if (x == 54 || x == 55 || x == 59 || x == 60 || x == 64 || x == 65 || x == 69 || x == 70)
				x++;
			this.unsteppable.add(new Coordinate(49, x));
		}
		for (int y = 40; y <= 44; y++)
			for (int x = 65; x <= 72; x++) {
				this.unsteppable.add(new Coordinate(y, x));
			}
		for (int x = 68; x <= 70; x++)
			this.unsteppable.add(new Coordinate(45, x));
		this.unsteppable.add(new Coordinate(44, 61));
		this.unsteppable.add(new Coordinate(44, 62));
		// more houses
		for (int y = 61; y <= 64; y++)
			for (int x = 31; x <= 40; x++) {
				this.unsteppable.add(new Coordinate(y, x));
			}
		for (int y = 61; y <= 64; y++)
			for (int x = 29; x <= 30; x++) {
				if (x != 30 || y != 64)
					this.unsteppable.add(new Coordinate(y, x));
			}
		// logs
		for (int x = 28; x <= 41; x++)
			this.unsteppable.add(new Coordinate(48, x));
		for (int y = 23; y <= 31; y++)
			this.unsteppable.add(new Coordinate(y, 46));
		for (int y = 24; y <= 51; y++)
			this.unsteppable.add(new Coordinate(y, 74));
		for (int x = 47; x < 75; x++)
			this.unsteppable.add(new Coordinate(23, x));
		for (int x = 46; x < 74; x++)
			this.unsteppable.add(new Coordinate(51, x));
		// hugelakething
		for (int x = 55; x <= 60; x++)
			for (int y = 33; y <= 45; y++)
				this.unsteppable.add(new Coordinate(y, x));
		// lamp
		for (int x = 47; x <= 48; x++)
			for (int y = 31; y <= 38; y++) {
				if (y == 33 || y == 36)
					y++;
				this.unsteppable.add(new Coordinate(y, x));
			}
		// rock shit?
		for (int y = 29; y <= 41; y++)
			this.unsteppable.add(new Coordinate(y, 4));
		for (int y = 41; y <= 44; y++)
			this.unsteppable.add(new Coordinate(y, 5));
		this.unsteppable.add(new Coordinate(45, 6));
		for (int x = 7; x <= 10; x++)
			this.unsteppable.add(new Coordinate(46, x));
		for (int x = 11; x <= 13; x++)
			this.unsteppable.add(new Coordinate(47, x));
		for (Coordinate c : unsteppable)
			map[c.getRow()][c.getCol()] = null;
		for (Coordinate c : unsteppable)
			map[c.getRow()][c.getCol()] = null;
	}

	public void movePlayer(String s) {
		if (!eventRunning && !specialEventRunning && !dueForEvent && !player.isCurrentlyWalking()) {
			boolean successfulStep = false;
			player.updateDirection(s);
			int y = player.getyCo();
			int x = player.getxCo();
			if (x == 70 && y == 35)
				this.enterShop = true;
			map[y][x].removePlayer();
			if ((s.equals("left") && map[y][x - 1] != null)) {
				player.move(s);
				lastXOffset = xOffset;
				xOffset += tileSize;
				successfulStep = true;
			} else if (s.equals("right") && map[y][x + 1] != null) {
				player.move(s);
				lastXOffset = xOffset;
				xOffset -= tileSize;
				successfulStep = true;
			} else if (s.equals("up") && map[y - 1][x] != null) {
				player.move(s);
				lastYOffset = yOffset;
				yOffset += tileSize;
				successfulStep = true;
			} else if (s.equals("down") && map[y + 1][x] != null) {
				player.move(s);
				lastYOffset = yOffset;
				yOffset -= tileSize;
				successfulStep = true;
			}
			map[player.getyCo()][player.getxCo()].addPlayer(player);
			if (successfulStep)
				checkForEvent();
		}
	}

	public void animateIntro() { // also triggers/initializes the tile's events
		if (dueForEvent) {
			if (introInFinished == false) {
				if ((int) rect.getX() < 0)
					rect.translate(200, 0);
				else if ((int) rect2.getX() < 0)
					rect2.translate(200, 0);
				else if ((int) rect3.getX() < 0)
					rect3.translate(200, 0);
				else if ((int) rect4.getX() < 0)
					rect4.translate(200, 0);
				else if ((int) rect5.getX() < 0)
					rect5.translate(200, 0);
				else if ((int) rect6.getX() < 0)
					rect6.translate(200, 0);

				else if ((int) rect7.getX() < 0)
					rect7.translate(200, 0);
				else {
					introInFinished = true;
					map[player.getyCo()][player.getxCo()].triggerEvent();
					eventRunning = true;
					map[player.getyCo()][player.getxCo()].setIntroInFinished(true);
					alpha = 1.0f;
				}
			} else if (introInFinished) {
				if ((int) rect.getX() != -2000)
					rect.translate(-200, 0);
				else if ((int) rect2.getX() != -2000)
					rect2.translate(-200, 0);
				else if ((int) rect3.getX() != -2000)
					rect3.translate(-200, 0);
				else if ((int) rect4.getX() != -2000)
					rect4.translate(-200, 0);
				else if ((int) rect5.getX() != -2000)
					rect5.translate(-200, 0);
				else if ((int) rect6.getX() != -2000)
					rect6.translate(-200, 0);
				else if ((int) rect7.getX() != -2000)
					rect7.translate(-200, 0);
				else {
					dueForEvent = false;
					introInFinished = false;
				}
			}
		}
	}

	public void checkForEvent() {

		// if (map[player.getyCo()][player.getxCo()] instanceof BattleTile
		// && ((BattleTile)
		// map[player.getyCo()][player.getxCo()]).isActive(player.getTrainersDefeated()))
		// {
		// eventRunning = true;
		//
		// }
		if (player.getNumItems(player.getItemsIndex("pokecoins")) < 5 && this.enterShop) {
			outOfCoins = true;
		}
		if (map[player.getyCo()][player.getxCo()] instanceof shopTile) {
			this.enterShop = true;
		} else {
			this.enterShop = false;
		}
		if (map[player.getyCo()][player.getxCo()] instanceof BattleTile
				&& ((BattleTile) map[player.getyCo()][player.getxCo()]).isActive(player.getTrainersDefeated())) {
			dueForEvent = true;
			soundTrack = "BattleMusic.wav";
		}
		if (map[player.getyCo()][player.getxCo()] instanceof CheckpointTile) {
			map[player.getyCo()][player.getxCo()].triggerEvent();
			checkpointXOffset = xOffset;
			checkpointYOffset = yOffset;
			player.saveCheckPointRectX();
			player.saveCheckPointRectY();
		}

		if (map[player.getyCo()][player.getxCo()] instanceof GrassTile
				&& ((GrassTile) map[player.getyCo()][player.getxCo()]).isActive()) {
			dueForEvent = true;
			soundTrack = "EncounterMusic.wav";

		}
		if (map[player.getyCo()][player.getxCo()] instanceof MessageTile)
			showMessage = true;
		else
			showMessage = false;
	}

	public void updateEvent() {
		// if(!map[player.getyCo()][player.getxCo()].eventRunning) {
		// map[player.getyCo()][player.getxCo()].removePlayer();
		// map[player.getCheckPointyCo()][player.getCheckPointxCo()].addPlayer(player);
		//
		// eventRunning = false;
		// }
		if (specialEventRunning) {
			specialEvent.moveObjects();
			if (specialEvent instanceof StarterEvent && ((StarterEvent) specialEvent).isEventFinished()) {
				player.addPokemon(new pokemon(pokedex.get(((StarterEvent) specialEvent).getSelectedPokemon())));
				pokemon starterPokemon = player.getPokemon().get(0);
				starterPokemon.clearMoves();
				int pokemonType = starterPokemon.typeToNum();
				if (pokemonType == 1)
					starterPokemon.learnMove(movedex.getFireMove(0));
				else if (pokemonType == 2)
					starterPokemon.learnMove(movedex.getWaterMove(0));
				else
					starterPokemon.learnMove(movedex.getGrassMove(0));
				starterPokemon.learnMove(movedex.getNormalMove(0));
				specialEvent = null;
				specialEventRunning = false;
			}
		}
		animateIntro();
		if (map[player.getyCo()][player.getxCo()].eventRunning) {
			if (map[player.getyCo()][player.getxCo()] instanceof BattleTile) {
				((BattleTile) map[player.getyCo()][player.getxCo()]).moveEventObjects();
				((BattleTile) map[player.getyCo()][player.getxCo()]).updateMove();
				((BattleTile) map[player.getyCo()][player.getxCo()]).updateBattle();
			} else if (map[player.getyCo()][player.getxCo()] instanceof GrassTile) {
				((GrassTile) map[player.getyCo()][player.getxCo()]).moveEventObjects();
				((GrassTile) map[player.getyCo()][player.getxCo()]).updateMove();
				((GrassTile) map[player.getyCo()][player.getxCo()]).updateBattle();
			}
		} else if (player.hasFainted()) {
			map[player.getyCo()][player.getxCo()].removePlayer();
			map[player.getCheckPointyCo()][player.getCheckPointxCo()].addPlayer(player);
			player.setxCo(player.getCheckPointxCo());
			player.setyCo(player.getCheckPointyCo());
			player.setRectX(player.getCheckPointRectX());
			player.setRectY(player.getCheckPointRectY());
			xOffset = checkpointXOffset;
			yOffset = checkpointYOffset;
			player.healAll();

			for (pokemon p : player.getPokemon()) {
				if ((p.getEvolutionsLeft() == 2 && p.getLevel() >= 5)
						|| (p.getEvolutionsLeft() == 1 && p.getLevel() >= 10)) {
					p.evolvePokemonNum();
					p.evolvePokemonName(pokedex.get(p.getPokeNum()).getName());
					p.updateEvolveStatus(false);
					p.decreaseEvolutionsLeft();
				}
			}
			eventRunning = false;
		} else if (player.isPlayerSurvived()) {
			player.playerSurvivedBattle(false);
			eventRunning = false;
			for (pokemon p : player.getPokemon()) {
				if ((p.getEvolutionsLeft() == 2 && p.getLevel() >= 5)
						|| (p.getEvolutionsLeft() == 1 && p.getLevel() >= 10)) {
					p.evolvePokemonNum();
					p.evolvePokemonName(pokedex.get(p.getPokeNum()).getName());
					p.updateEvolveStatus(false);
					p.decreaseEvolutionsLeft();
				}
//				if (p.checkEvolveStatus() && p.getEvolutionsLeft() > 0) {
//					System.out.println("EVOLVED!!");
//					p.evolvePokemonNum();
//					p.evolvePokemonName(pokedex.get(p.getPokeNum()).getName());
//					evolveList.add(p);
//					runEvolution = true;
//					System.out.println(runEvolution);
//					if(evolveAnimationDone == true)
//					{
//						p.updateEvolveStatus(false);
//						p.decreaseEvolutionsLeft();
//					}
//				}
			}
		}
		// if(runEvolution== true)
		// {
		// counter+=0.5;
		// }
		if (alpha > 0.0f && !eventRunning && !specialEventRunning) {
			alpha -= 0.02f;
			if (alpha <= 0.0f)
				alpha = 0;
		}
	}

	public void checkPokemonForEvolution() {
		for (pokemon p : player.getPokemon()) {
			if ((p.getEvolutionsLeft() == 2 && p.getLevel() >= 5)
					|| (p.getEvolutionsLeft() == 1 && p.getLevel() >= 10)) {
				p.evolvePokemonNum();
				p.evolvePokemonName(pokedex.get(p.getPokeNum()).getName());
				teachPokemonNewMove(p);
				p.updateEvolveStatus(false);
				p.decreaseEvolutionsLeft();
			}
		}
	}

	public void teachPokemonNewMove(pokemon p) {
		int typeNum = p.typeToNum();
		boolean backUpMoveNeeded = true;
		for (int i = 0; i < 4; i++) {
			Move potentialMove = movedex.getMoveByType(typeNum, i);
			if (!p.pokemonHasMove(potentialMove)) {
				p.learnMove(potentialMove);
				backUpMoveNeeded = false;
			}
		}
		if (backUpMoveNeeded)
			p.learnMove(movedex.getNormalMove(2));

	}

	public void updateMapAnimation() {
		player.updateStance();
		player.updateAnimation();
		if (!player.isCurrentlyWalking()) {
			lastXOffset = xOffset;
			lastYOffset = yOffset;
			// if (dueForEvent) {
			// ((BattleTile) map[player.getyCo()][player.getxCo()]).triggerEvent();
			// eventRunning = true;
			// dueForEvent = false;
			// }
		}
	}

	public void draw(Graphics g) {
		if (!specialEventRunning) {
			if (player.isCurrentlyWalking()) {
				g.drawImage(image, player.camxOffset + lastXOffset, player.camyOffset + lastYOffset, mapSize, mapSize,
						null);
				showMenu = false;
			} else {
				g.drawImage(image, xCo + xOffset, yCo + yOffset, mapSize, mapSize, null);
			}
			player.draw(g, xOffset, yOffset);
			if (alpha > 0.0f)
				drawFade(g);
			if (map[player.getyCo()][player.getxCo()].eventRunning) {
				map[player.getyCo()][player.getxCo()].draw(g);
			}

			if (!map[player.getyCo()][player.getxCo()].eventRunning)
				drawMenu(g);
		} else {
			specialEvent.draw(g);
		}

		if (!specialEventRunning && !eventRunning && !dueForEvent)
			soundTrack = "TwinLeafTown.wav";

		// if(runEvolution == true)
		// {
		// for(pokemon p: evolveList) {
		// Image evolved = p.getImage("front");
		// Image preEvolved = pokedex.get(p.getPokeNum()-1).getImage("front");
		// if(runTransition == false)
		// {
		// drawTransition( g);
		// runTransition = true;
		// }
		// g.fillRect(0, 0, screenWidth, screenHeight);
		// if((int)counter%2 == 0)
		// {
		// g.drawImage(preEvolved, screenWidth/2, screenHeight/2 - 200, null);
		// }
		// else if((int)counter%2 != 0)
		// {
		// g.drawImage(evolved, screenWidth/2, screenHeight/2 - 200, null);
		// }
		// if((int)counter == 1001)
		// {
		//
		// runEvolution = false;
		// counter = 0;
		//
		// }
		// }
		// }

		if (showMessage)
			map[player.getyCo()][player.getxCo()].draw(g);
		if (enterShop) {
			shopTile c = (shopTile) map[35][70];
			c.draw(g);

			if (outOfCoins) {
				if (customFont == null) {
					try {
						// create the font to use. Specify the size!
						customFont = Font
								.createFont(Font.TRUETYPE_FONT,
										getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf"))
								.deriveFont(44f);
						GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
						// register the font
						ge.registerFont(customFont);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (FontFormatException e) {
						e.printStackTrace();
					}
				}
				g.setFont(customFont);
				g.drawImage(getImage("TextBox.png"), 0, (this.screenHeight - this.screenWidth / 2 * 46 / 252) - 40,
						this.screenWidth, screenWidth / 2 * 46 / 252, null);
				g.setColor(Color.DARK_GRAY);
				g.drawString("You don't have enough money buddy go battle to earn more!!", 139,
						this.screenHeight - (this.screenWidth / 2 * 46 / 252) + 10);

			}
		}
		drawTransition(g);

	}

	private void fadeOut() {
		if (alpha > 0.0f)
			alpha -= 0.01f;
	}

	private void drawFade(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (alpha < 0.0f)
			alpha = 0.0f;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2d.setComposite(ac);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, screenWidth, screenHeight);
		// System.out.println("alpha value: "+alpha);
	}

	public void drawTransition(Graphics g) {
		g.setColor(Color.black);
		g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		g.fillRect((int) rect2.getX(), (int) rect2.getY(), (int) rect2.getWidth(), (int) rect2.getHeight());
		g.fillRect((int) rect3.getX(), (int) rect3.getY(), (int) rect3.getWidth(), (int) rect3.getHeight());
		g.fillRect((int) rect4.getX(), (int) rect4.getY(), (int) rect4.getWidth(), (int) rect4.getHeight());
		g.fillRect((int) rect5.getX(), (int) rect5.getY(), (int) rect5.getWidth(), (int) rect5.getHeight());
		g.fillRect((int) rect6.getX(), (int) rect6.getY(), (int) rect6.getWidth(), (int) rect6.getHeight());
		g.fillRect((int) rect7.getX(), (int) rect7.getY(), (int) rect7.getWidth(), (int) rect7.getHeight());

	}

	private int getXDimens(int x) {
		return (this.screenWidth - x) / 2;
	}

	private int getYDimens(int x) {
		return (this.screenHeight - x) / 2;
	}

	private int getPokeIndex(int x, int y) {
		if (y == 0) {
			return x;
		} else if (y == 1) {
			return x + 3;
		}
		return 0;
	}

	private void drawMenu(Graphics g) {

		g.drawImage(getImage("menu/bag.png"), (int) ((0.90) * this.screenWidth), (int) ((0.90) * this.screenHeight), 80,
				80, null);
		if (this.showMenu) {
			if (this.customFont == null) {
				try {
					// create the font to use. Specify the size!
					this.customFont = Font.createFont(Font.TRUETYPE_FONT,
							getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(34f);
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					// register the font
					ge.registerFont(customFont);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (FontFormatException e) {
					e.printStackTrace();
				}
			}
			g.setFont(customFont);

			g.drawImage(getImage("menu/diamond.png"), this.getXDimens(menuWidth), this.getYDimens(menuHeight),
					menuWidth, menuHeight, null);
			g.drawImage(getImage("menu/light.png"), this.spotLightX.get(this.xSelection),
					this.spotLightY.get(this.ySelection), 80, 80, null);
			g.drawImage(getImage("TextBox.png"), 0, (this.screenHeight - this.screenWidth / 2 * 46 / 252) - 40,
					this.screenWidth, this.screenWidth / 2 * 46 / 252, null);
			g.setColor(Color.DARK_GRAY);
			g.drawString("Switch using arrow keys and press space for selection! If selecting a pokemon, use return",
					100, this.screenHeight - this.screenWidth / 2 * 46 / 252 + 29);
			g.drawImage(getImage("menu/potion.png"), this.getXDimens(80), this.getYDimens(80) / 2, 80, 80, null);
			g.drawImage(getImage("menu/pokeballs.png"), this.getXDimens(80), (3 * this.getYDimens(80)) / 2, 80, 80,
					null);
			g.drawImage(getImage("menu/menuEsp.png"), (int) ((4.0 / 3) * this.getXDimens(80)), (this.getYDimens(80)),
					80, 80, null);
			g.drawImage(getImage("menu/Pokecoin.png"), this.getXDimens(menuWidth) + 100, (this.getYDimens(80)), 80, 80,
					null);
			g.drawImage(getImage("menu/ash.png"), this.getXDimens(150), this.getYDimens(200), 150, 200, null);

			g.setColor(Color.WHITE);
			g.drawString("" + (player.getNumItems(player.getItemsIndex("pokeball"))),
					(int) (0.999 * this.screenWidth / 2), (int) (2.35 * (this.screenHeight / 3)));
			g.drawString("" + (player.getPokemon().size()), (int) ((1.3333) * this.getXDimens(80)) + 50,
					(this.getYDimens(80) + 100));
			g.drawString("" + (player.getNumItems(player.getItemsIndex("potion"))), this.getXDimens(80) + 50,
					(int) (((double) 13 / 18) * (this.getYDimens(80))));
			g.drawString("" + (player.getNumItems(player.getItemsIndex("pokecoin"))), this.getXDimens(menuWidth) + 120,
					(this.getYDimens(80)) + 100);
			g.drawString(player.getNickName(), (int) ((3.07 / 3) * this.getXDimens(150)),
					(int) (this.getYDimens(200) * (4.65 / 3)));

			if (this.playerSelection) {

				g.drawImage(getImage("menu/card.png"), 0, 0, this.screenWidth, this.screenHeight, null);
				g.drawImage(getImage("menu/ash_full.png"), (int) ((0.113) * this.screenWidth),
						(int) ((1.64) * this.getYDimens((int) ((0.75) * this.screenHeight))),
						(int) ((0.15) * this.screenWidth), (int) ((0.7) * this.screenHeight), null);
				for (int i = 0; i < player.getPokemon().size(); i++) {
					if (i >= 3) {
						player.getPokemon().get(i).drawPokemon(g, "front", (int) ((4.05 / 2) * this.getXDimens(200)),
								(int) (this.getYDimens(200) + i % 3 * (this.screenHeight) * (0.2)));

					}
					if (player.getPokemon().get(i) != null)
						player.getPokemon().get(i).drawPokemon(g, "front", (int) ((3.3 / 2) * this.getXDimens(200)),
								(int) (this.getYDimens(200) + i * (this.screenHeight) * (0.2)));
				}
				g.drawString(player.getNickName(), (int) ((3.07 / 3) * this.getXDimens(150)),
						(int) (this.getYDimens(200)));
				g.drawString(player.getGender(), (int) ((3.07 / 3) * this.getXDimens(150)),
						(int) ((0.93) * this.getYDimens(200) * (4.65 / 3)));
				g.drawString("" + player.getAge(), (int) ((3.07 / 3) * this.getXDimens(150)),
						(int) ((1.28) * this.getYDimens(200)));
			}
			if (this.pokemonSelection) {
				g.drawImage(getImage("menu/Box_Forest_E.png"), 0, 0, this.screenWidth, this.screenHeight, null);
				g.drawImage(getImage("menu/light.png"), this.pokemonLightX.get(this.xPokeSelect),
						this.pokemonLightY.get(this.yPokeSelect), 200, 200, null);
				g.drawImage(getImage("TextBox.png"), 0, (this.screenHeight - this.screenWidth / 2 * 46 / 252) - 40,
						this.screenWidth, this.screenWidth / 2 * 46 / 252, null);
				g.setColor(Color.DARK_GRAY);
				g.drawString("press E to shift pokemons and press Q to delete pokemon", 139,
						this.screenHeight - this.screenWidth / 2 * 46 / 252 + 29);

				for (int i = 0; i < player.getPokemon().size(); i++) {
					if (i >= 3) {
						player.getPokemon().get(i).drawPokemon(g, "front", 400,
								(int) ((1.0 / 5) * this.getXDimens(400) + i % 3 * (this.screenWidth) * (0.3)),
								(int) ((8.0 / 5) * this.getYDimens(400)));
					}
					if (player.getPokemon().get(i) != null)
						player.getPokemon().get(i).drawPokemon(g, "front", 400,
								(int) ((1.0 / 5) * this.getXDimens(400) + i * (this.screenWidth) * (0.3)),
								(int) ((1.0 / 5) * this.getYDimens(400)));
				}

			}
			if (this.selectedPokemon) {
				g.drawImage(getImage("menu/template.png"), 0, 0, this.screenWidth, this.screenHeight, null);
				p = player.getPokemon().get(getPokeIndex(this.xPokeSelect, this.yPokeSelect));
				try {
					p.drawPokemon(g, "front", 500, (int) ((0.4) * this.getXDimens(500)),
							(int) ((0.27) * this.getYDimens(500)));
					g.setColor(Color.BLACK);
					g.drawString("POKEMON NAME : " + p.getName(), (int) ((1) * this.getXDimens(80)),
							(int) ((0.9) * this.getYDimens(500)));
					g.drawString("POKEMON LEVEL : " + p.getLevel(), (int) ((1) * this.getXDimens(80)),
							(int) ((1.3) * this.getYDimens(500)));
					g.drawString("POKEMON HEALTH : " + p.getBattleHP(), (int) ((1) * this.getXDimens(80)),
							(int) ((1.7) * this.getYDimens(500)));
					g.drawString("TO HEAL POKEMON, PRESS P", (int) ((0.8) * this.getXDimens(100)),
							(int) ((3.0 / 2) * this.getYDimens(100)));

					g.drawImage(getImage("menu/potion.png"), (int) ((0.5) * this.getXDimens(100)),
							(int) ((3.0 / 2) * this.getYDimens(100)), 100, 100, null);
					g.drawString("" + player.getNumItems(1), (int) ((0.5) * this.getXDimens(100)),
							(int) ((3.3 / 2) * this.getYDimens(100)));
					if (this.heal) {
						heal = false;
						player.healPokemon(getPokeIndex(this.xPokeSelect, this.yPokeSelect));
					}
				} catch (Exception e) {

				}
			}
		}

	}

	public String getSoundTrack() {
		return soundTrack;
	}

	private void selectMenu(Graphics g) {

		g.drawImage(getImage("menu/light.png"), this.spotLightX.get(this.xSelection),
				this.spotLightY.get(this.ySelection), 80, 80, null);

	}

	protected Image getImage(String fn) {
		Image img = null;
		try {

			img = ImageIO.read(this.getClass().getResource(fn));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void chooseOption(KeyEvent e) {
		// TESTING SHIFTING POKEMON ORDER for phoebe

		if (!eventRunning && this.pokemonSelection && e.getKeyCode() == KeyEvent.VK_E)
			player.shiftPokemonOrder();
		// TESTING REMOVING POKEMON
		if (!eventRunning && this.pokemonSelection && e.getKeyCode() == KeyEvent.VK_Q)
			player.removePokemon(this.getPokeIndex(this.xPokeSelect, this.yPokeSelect));
		///////////////////////////////////
		if (specialEventRunning)
			((StarterEvent) specialEvent).chooseOption(e);
		if (map[player.getyCo()][player.getxCo()].eventRunning) {
			if (map[player.getyCo()][player.getxCo()] instanceof BattleTile)
				((BattleTile) map[player.getyCo()][player.getxCo()]).chooseOption(e);
			if (map[player.getyCo()][player.getxCo()] instanceof GrassTile)
				((GrassTile) map[player.getyCo()][player.getxCo()]).chooseOption(e);
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_1 && this.enterShop
				&& player.getNumItems(player.getItemsIndex("pokecoin")) >= 5) {
			player.setItems(player.getNumItems(player.getItemsIndex("potion")) + 10, 1);
			player.changeCoins(-5);

		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_2 && this.enterShop
				&& player.getNumItems(player.getItemsIndex("pokecoin")) >= 5) {
			player.setItems(player.getNumItems(player.getItemsIndex("pokeball")) + 10, 0);
			player.changeCoins(-5);
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (this.showMenu == false)
				this.showMenu = true;
			else {
				this.showMenu = false;
				this.pokemonSelection = false;
				this.playerSelection = false;
				this.selectedPokemon = false;
			}
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_UP) {
			if (this.xSelection == 1 && this.ySelection > 0 && !playerSelection && !pokemonSelection)
				ySelection--;
			if (this.pokemonSelection) {
				if (this.yPokeSelect > 0)
					this.yPokeSelect--;
			}

		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (this.xSelection == 1 && this.ySelection < 2 && !playerSelection && !pokemonSelection)
				ySelection++;
			if (this.pokemonSelection && player.getPokemon().size() > 3) {
				if (this.yPokeSelect < 1) {
					if (this.xPokeSelect == 0 && player.getPokemon().size() > 3) {
						this.yPokeSelect++;
					}
					if (this.xPokeSelect == 1 && player.getPokemon().size() > 4) {
						this.yPokeSelect++;
					}
					if (this.xPokeSelect == 2 && player.getPokemon().size() > 5) {
						this.yPokeSelect++;
					}
				}
			}
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (this.xSelection > 0 && ySelection == 1 && !playerSelection && !pokemonSelection)
				xSelection--;
			if (this.pokemonSelection) {
				if (this.xPokeSelect > 0) {
					this.xPokeSelect--;
				}
			}
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (this.xSelection < 2 && ySelection == 1 && !playerSelection && !pokemonSelection)
				xSelection++;
			if (this.pokemonSelection && this.xPokeSelect < 2) {
				if (yPokeSelect == 0) {
					if (player.getPokemon().size() - 1 > xPokeSelect)
						this.xPokeSelect++;

				} else if (yPokeSelect == 1) {
					if (player.getPokemon().size() - 4 > xPokeSelect)
						this.xPokeSelect++;

				}
			}
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (ySelection == 1 && xSelection == 1 && this.showMenu)
				this.playerSelection = !this.playerSelection;
			if (ySelection == 1 && xSelection == 2 && this.showMenu)
				this.pokemonSelection = !this.pokemonSelection;
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (this.pokemonSelection) {
				this.selectedPokemon = !this.selectedPokemon;
			}
		}
		if (!eventRunning && e.getKeyCode() == KeyEvent.VK_P) {
			if (this.pokemonSelection && this.selectedPokemon) {
				this.heal = true;
			}
		}
	}
}
