	import java.awt.*;
	import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
public class EncounterEvent extends Events {
	
		int screenWidth = (int) pokemonRunner.screenSize.getWidth(),
		screenHeight = (int) pokemonRunner.screenSize.getHeight();
		public Player player;
		private static final int playerPokemonScale = 2, enemyPokemonScale = 2;
		private int textBoxX = 0, textBoxY = (screenHeight - screenWidth / 2 * 46 / 252) - 40;
		public final int HPWIDTH = 300, HPHEIGHT = 20;
		public double throwImgNum = 0.0;
		double moveNum = 1.8;
		public int textBoxHeight = screenWidth / 2 * 46 / 252;
		Font customFont;
		Font smallFont;
		private Font mediumFont;
		float alpha = 0.0f;
		
		pokemon playerPokemon, enemyPokemon;
		Move[] myMoves;
		String[] options = new String[] {"Pokeball", "Run"};
		String[] pointAtOpt = new String[] {"", ""};
		int selectedOptNum = -1;
		Move selectedMove;
		int selectedMoveIndex;
		String[] pointAtSM = new String[] { "-> ", "", "", "" };

		Move enemySelectedMove;
		int enemyMoveNum = -1;
		int selectedNextPokemon = 0;

		boolean alignedMove = false;

		private String text; // for updating text
		boolean specialText = false;
		int specialTextCount = 80;
		
		Pokedex pdex = new Pokedex();
		// draw these eventobjects
		Image background = getImage("battlebgField.png");
		EventObject healthBoxPlayer = new EventObject("res/images/playerbar.png", screenWidth, screenHeight - 400, 700, 202, "", screenWidth + 125, screenHeight - 320, 30);
		EventObject healthBoxOpponent = new EventObject("res/images/oppbar.png", -700, 100, 700, 202, "", -600, 200, 30);
		EventObject playerbaseGrass = new EventObject("playerbaseForestGrass.png", screenWidth, screenHeight - 140 - textBoxHeight, 850, 107);
		EventObject enemybaseGrass = new EventObject("enemybaseMountainGrass.png", 0, 450, 700, 202);
		EventObject sendRedToThrow = new EventObject("RedSend/RedSendOut1.png", -246, screenHeight - 294, 100, 100);
		EventObject fireMove, waterMove, normalMove, grassMove, electricMove, poisonMove, bugMove, pokeBallSprite;
		EventObject playerPokemonSprite, enemyPokemonSprite;
		private int enemyWidth, enemyHeight; //storage
		// one-time storage (from now on, if an eventobject has multiple images, then
		// use the new constructor that takes in an arraylist).
		int scale = 6;
		Image[] sendOutAni = { getImage("RedSend/RedSendOut1.png").getScaledInstance(41 * scale, 49 * scale, 1),
				getImage("RedSend/RedSendOut2.png").getScaledInstance(65 * scale, 45 * scale, 1),
				getImage("RedSend/RedSendOut3.png").getScaledInstance(52 * scale, 48 * scale, 1),
				getImage("RedSend/RedSendOut4.png").getScaledInstance(64 * scale, 50 * scale, 1),
				getImage("RedSend/RedSendOut5.png").getScaledInstance(55 * scale, 44 * scale, 1) };
		ArrayList<EventObject> eventObjects = new ArrayList<EventObject>();
		ArrayList<EventObject> eventMoves = new ArrayList<EventObject>();
		// intro and outro rectangles
		Rectangle rect = new Rectangle(-2000, 0, 2000, 150), rect2 = new Rectangle(-2000, 150, 2000, 150),
				rect3 = new Rectangle(-2000, 300, 2000, 150), rect4 = new Rectangle(-2000, 450, 2000, 150),
				rect5 = new Rectangle(-2000, 600, 2000, 150), rect6 = new Rectangle(-2000, 750, 2000, 150),
				rect7 = new Rectangle(-2000, 900, 2000, 150);

		// flags (checkpoints that signify when animation is done)
		boolean introInFinished = false;
		boolean introFullyFinished = false;
		boolean allowBattle = false;
		boolean goBack = false;
		boolean pullOut = false;
		boolean initialMoveSelection = false;
		boolean moveAnimsInitialized = false;
		boolean playerPokemonDied = false;
		boolean playerPokemonWithdrawn = false;

		// logical flags
		boolean moveSelection = false; // waits for player to choose move
		boolean moveChosen = false; // oh, player has chosen move, let's see which pokemon is faster to determine
		boolean caughtPokemon = false;
		boolean checkedIfCaught = false;
		boolean ran = false;
		boolean chooseNextPokemon = false;
		boolean nextPokemonChosen = false;
		boolean pokemonSwapped = false;
		int pokemonHealCount = 0;
		// who goes first
		boolean playerMoveAnimationFinished = false;
		boolean enemyMoveAnimationFinished = false;
		boolean playerMoveFinished = false; // if im false, i need to do player's move
		boolean enemyMoveFinished = false; // if i'm false, i need to do enemy's move
		boolean pokeBallThrown = false;
		boolean pokeBallAnimationFinished = false; 
		boolean battleFinished = false;

		Random randomGen = new Random(); 
		
		public EncounterEvent() {
			super();
		}
		public EncounterEvent(Player p, pokemon y) {
			player = p;
			text = "A wild " + y.getName() + " appeared!";
			playerPokemon = player.getAlivePokemon();
			playerPokemonSprite = new EventObject(playerPokemon.getImage("back"), -320,
					textBoxY - playerPokemonScale * playerPokemon.getImage("back").getWidth(null),
					playerPokemonScale * playerPokemon.getImage("back").getWidth(null),
					playerPokemonScale * playerPokemon.getImage("back").getHeight(null));
			enemyPokemon = y;
			enemyPokemonSprite = new EventObject(enemyPokemon.getStringImage("front"), 180, enemybaseGrass.getY() - 150,
					enemyPokemon.getImage("front").getWidth(null) * enemyPokemonScale,
					enemyPokemon.getImage("front").getHeight(null) * enemyPokemonScale);
			
			enemyWidth =enemyPokemon.getImage("front").getWidth(null) * enemyPokemonScale;
			enemyHeight = enemyPokemon.getImage("front").getHeight(null) * enemyPokemonScale;
			healthBoxOpponent.updateText(enemyPokemon.getName() + "  Lv." + enemyPokemon.getLevel());
			myMoves = playerPokemon.getMoves();
			selectedMove = myMoves[0];
			selectedMoveIndex = 0;
			enemySelectedMove = enemyPokemon.getRandomMove();

			eventObjects.add(playerbaseGrass);
			eventObjects.add(enemybaseGrass);
			
			try {
			    //create the font to use. Specify the size!
				this.smallFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(15f);
				this.mediumFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(35f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(smallFont);
			    ge.registerFont(mediumFont);
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
		
			
		}

		public void initializeMove() {
			int widscale = playerPokemonScale * playerPokemon.getImage("back").getWidth(null);
			int lenScale = playerPokemonScale * playerPokemon.getImage("back").getHeight(null);
			int xEnemyLoc = this.enemyPokemonSprite.getX(), yEnemyLoc = this.enemyPokemonSprite.getY();

			ArrayList<String> grassMoveList = new ArrayList<>(Arrays.asList("Moves/Grass/1grass.png",
					"Moves/Grass/2grass.png", "Moves/Grass/3grass.png", "Moves/Grass/4grass.png", "Moves/Grass/5grass.png",
					"Moves/Grass/6grass.png", "Moves/Grass/7grass.png"));

			grassMove = new EventObject(grassMoveList, xEnemyLoc, yEnemyLoc, widscale + 100, lenScale + 100);
			ArrayList<String> normalMoveList = new ArrayList<>(Arrays.asList("Moves/Normal/1normal.png",
					"Moves/Normal/2normal.png", "Moves/Normal/3normal.png", "Moves/Normal/4normal.png",
					"Moves/Normal/5normal.png", "Moves/Normal/6normal.png", "Moves/Normal/7normal.png"));

			normalMove = new EventObject(normalMoveList, xEnemyLoc, yEnemyLoc - 350, widscale, lenScale);

			ArrayList<String> fireMoveList = new ArrayList<>(Arrays.asList("MovesFlameThrower/fireThrow1.png",
					"MovesFlameThrower/fireThrow2.png", "MovesFlameThrower/fireThrow3.png",
					"MovesFlameThrower/fireThrow4.png", "MovesFlameThrower/fireThrow5.png",
					"MovesFlameThrower/fireThrow6.png", "Moves/Fire/1fire.png", "Moves/Fire/2fire.png",
					"Moves/Fire/3fire.png", "Moves/Fire/4fire.png", "Moves/Fire/5fire.png"));

			fireMove = new EventObject(fireMoveList, this.playerPokemonSprite.getX(), this.playerPokemonSprite.getY(),
					widscale, lenScale);
			ArrayList<String> waterMoveList = new ArrayList<>(Arrays.asList("WaterMove/water .png", "WaterMove/water 2.png",
					"WaterMove/water 3.png", "WaterMove/water 4.png", "WaterMove/water 5.png", "WaterMove/water 6.png",
					"WaterMove/water 7.png", "WaterMove/water 8.png", "WaterMove/water 9.png", "WaterMove/water 10.png",
					"WaterMove/water 11.png"));
			waterMove = new EventObject(waterMoveList, xEnemyLoc, yEnemyLoc - 750, widscale, lenScale);
			ArrayList<String> electricMoveList = new ArrayList<>(Arrays.asList("Moves/Electric/1Electric.png",
					"Moves/Electric/2Electric.png", "Moves/Electric/3Electric.png", "Moves/Electric/4Electric.png",
					"Moves/Electric/5Electric.png", "Moves/Electric/6Electric.png"));

			electricMove = new EventObject(electricMoveList, xEnemyLoc, yEnemyLoc - 350, widscale, lenScale);
			ArrayList<String> bugMoveList = new ArrayList<>(Arrays.asList("BugMove/Crunch 1.png", "BugMove/Crunch 2.png",
					"BugMove/Crunch 3.png", "BugMove/Crunch 4.png", "BugMove/Crunch 5.png", "BugMove/Crunch 6.png",
					"BugMove/Crunch 7.png", "BugMove/Crunch 8.png", "BugMove/Crunch 9.png", "BugMove/Crunch 10.png"));
			bugMove = new EventObject(bugMoveList, xEnemyLoc, yEnemyLoc, widscale, lenScale);
			ArrayList<String> poisonMoveList = new ArrayList<>(Arrays.asList("PoisonMove/poison 1.png","PoisonMove/poison 2.png","PoisonMove/poison 3.png","PoisonMove/poison 4.png","PoisonMove/poison 5.png","PoisonMove/poison 6.png","PoisonMove/poison 7.png","PoisonMove/poison 8.png","PoisonMove/poison 9.png"));
			poisonMove = new EventObject(poisonMoveList, xEnemyLoc, yEnemyLoc , widscale, lenScale);
			ArrayList<String> pokeBallList = new ArrayList<>(Arrays.asList("Pokeball/ball1.png", "Pokeball/ball2.png"));
			pokeBallSprite = new EventObject(pokeBallList, 0, screenHeight, 32*playerPokemonScale, 64*playerPokemonScale); 
		}

		public void moveObjects() {
			if (!battleFinished) {
				if (!introFullyFinished)
					animateStart();
				if (playerPokemonDied|| pokemonSwapped)
					pullPlayerPokemonBack();
			}
		}


		private void pullPlayerPokemonBack() {
			if (!playerPokemonWithdrawn && playerPokemonSprite.getX() >= -300) {
				playerPokemonSprite.translate(-10, 0);
				text = "You withdrew " + playerPokemon.getName() + "!";
				
			}
			else if (!playerPokemonWithdrawn){
				if(!nextPokemonChosen)
					chooseNextPokemon = true;
				if (nextPokemonChosen) {
					playerPokemon = player.getPokemon().get(selectedNextPokemon);
					text = playerPokemon.getName() + " was drawn!";
					playerPokemonSprite.setImage(playerPokemon.getStringImage("back"));
					myMoves = playerPokemon.getMoves();
					selectedMove = myMoves[0];
					healthBoxPlayer.updateText(playerPokemon.getName() + "  Lv." + playerPokemon.getLevel());
					playerPokemonWithdrawn = true;
					chooseNextPokemon = false;
					nextPokemonChosen = false;
				}
			}
			if (playerPokemonWithdrawn && playerPokemonSprite.getX() <= 100)
				playerPokemonSprite.translate(10, 0);
			else if (playerPokemonWithdrawn && playerPokemonSprite.getX() > 100) {
				pokeBallSprite.setLoc(0, screenHeight);
				playerPokemonDied = false;
				playerPokemonWithdrawn = false;
				if(pokemonSwapped) {
					playerMoveFinished = true;
					playerMoveAnimationFinished = true;
					pokemonSwapped = false;
				}
			}

		}

		public void updateBattle() {
			if(specialText)
				specialTextCount--;
			if (specialTextCount == 0) {
				specialText = false;
				specialTextCount = 80;
			}
			
			if (!battleFinished) {
					if (moveSelection && introFullyFinished && !specialText)
						text = "What will " + playerPokemon.getName() + " do?";
					if (moveChosen) {
						if(pokemonHealCount > 0) {
							pokemonHealCount--;
							if (pokemonHealCount==0) {
								playerMoveAnimationFinished = true;
								alignedMove = false;
								
							}
						}
						else if(selectedOptNum == 1) {
							text = "You ran!";
							player.playerSurvivedBattle(true);
							enemyPokemon.healFull();
							ran = true;
							endBattle();
						} else if (selectedOptNum == 0) {
							if(player.getNumItems(0)>0 && !pokeBallThrown) {
								player.throwPokeBall();
								pokeBallThrown = true;
								pokeBallAnimationFinished = false;
							}
						} else if (!pokemonSwapped &&
								((playerPokemon.getSpeed() >= enemyPokemon.getSpeed() && !playerPokemonDied && !pokeBallThrown) ||
						(enemyMoveAnimationFinished && enemyMoveFinished && !playerPokemonDied && !pokeBallThrown))) {
							if (!playerMoveFinished) {
								if (playerPokemon.getBattleHP()> 0 && selectedMove != null) {
											System.out.println("enemy's hp before attack: "+enemyPokemon.getBattleHP());
									text = playerPokemon.getName() + " used " + selectedMove.getName() + "!";
									playerPokemon.attack(selectedMove, enemyPokemon);
									if (enemyPokemon.getBattleHP()<= 0) {
										playerPokemon.gainExp((enemyPokemon.getLevel()+playerPokemon.getLevel())*((enemyPokemon.getMaxHP()+playerPokemon.getMaxHP())/2));
										System.out.println("EXP NEEDED: "+playerPokemon.getExp());
									}
									playerMoveAnimationFinished = false;
									playerMoveFinished = true;
											System.out.println("enemy's hp after attack: "+enemyPokemon.getBattleHP());
								}
							}

					}  
						 if (!pokemonSwapped && !ran && ((pokeBallThrown && pokeBallAnimationFinished)||
						(playerMoveFinished && playerMoveAnimationFinished) ||
						(pokeBallThrown && pokeBallAnimationFinished && enemyPokemon.getSpeed() > playerPokemon.getSpeed()||
							(!pokeBallThrown && enemyPokemon.getSpeed() > playerPokemon.getSpeed())
							))) {
								if (!enemyMoveFinished) {
	//								System.out.println("I JUST FARTED");
									if (enemyPokemon.getBattleHP()>0 && (!enemyMoveFinished || playerMoveFinished)) {
											System.out.println("pokemon's hp before attack: "+playerPokemon.getBattleHP());
											enemySelectedMove = enemyPokemon.getRandomMove();
											System.out.println("RANDOM MOVE: " + enemySelectedMove.getName());
											if(!playerPokemonDied && !playerPokemonWithdrawn)
												text = enemyPokemon.getName() + " used " + enemySelectedMove.getName() + "!";
											
											enemyPokemon.attack(enemySelectedMove, playerPokemon);
													System.out.println("pokemon's hp after attack: "+playerPokemon.getBattleHP());
											enemyMoveAnimationFinished = false;
											enemyMoveFinished = true;
											
										}
									}

					}
					if (	(enemyMoveFinished && enemyMoveAnimationFinished && pokeBallThrown && pokeBallAnimationFinished) || 
							(enemyMoveFinished && enemyMoveAnimationFinished && playerPokemonDied) || 
							(enemyMoveFinished && playerMoveFinished && enemyMoveAnimationFinished && playerMoveAnimationFinished)
							) {
//								System.out.println("IM DONE!!!!!!!!!!!!!!!!!!!!!");
						for (int i = 0; i < pointAtSM.length;i++) {
							pointAtSM[i] = "";
							if(i<2)
								pointAtOpt[i] = "";
						}
						pokeBallSprite.setLoc(0, screenHeight);
						moveNum = 1.8;
						selectedOptNum = -1;
						selectedMoveIndex = 0;
						pointAtSM[selectedMoveIndex] = "-> ";
						selectedMove = myMoves[selectedMoveIndex];
						pokeBallThrown = false;
						pokeBallAnimationFinished = false;
						playerMoveAnimationFinished = false;
						enemyMoveAnimationFinished = false;
						enemyMoveFinished = false;
						playerMoveFinished = false;
						moveChosen = false;
						moveSelection = true;
					}

				}
			}
		}

		private void animatePoisonMove() {
			if (moveNum >= 2.04 && moveNum < 2.08) {
				setImg(poisonMove, 0);
			} else if (moveNum >= 2.08 && moveNum < 2.12) {
				setImg(poisonMove, 1);
			} else if (moveNum >= 2.12 && moveNum < 2.16) {
				setImg(poisonMove, 2);
			} else if (moveNum >= 2.16 && moveNum < 2.20) {
				setImg(poisonMove, 3);
			} else if (moveNum >= 2.20 && moveNum < 2.24) {
				setImg(poisonMove, 4);
			} else if (moveNum >= 2.24 && moveNum < 2.28) {
				setImg(poisonMove, 5);
			} else if (moveNum >= 2.28 && moveNum < 2.32) {
				setImg(poisonMove, 6);
			} else if (moveNum >= 2.32 && moveNum < 2.36) {
				setImg(poisonMove, 7);
			} else if (moveNum >= 2.36 && moveNum < 2.4) {
				setImg(poisonMove, 8);
			} 
			 else if (moveNum > 2.4) {
					if (!battleDone()) {
						continueBattle();
					} else {
						endBattle();
					}
			}
		}

		private void animateBugMove() {
			if (moveNum >= 2.04 && moveNum < 2.08) {
				setImg(bugMove, 0);
			} else if (moveNum >= 2.08 && moveNum < 2.12) {
				setImg(bugMove, 1);
			} else if (moveNum >= 2.12 && moveNum < 2.16) {
				setImg(bugMove, 2);
			} else if (moveNum >= 2.16 && moveNum < 2.20) {
				setImg(bugMove, 3);
			} else if (moveNum >= 2.20 && moveNum < 2.24) {
				setImg(bugMove, 4);
			} else if (moveNum >= 2.24 && moveNum < 2.28) {
				setImg(bugMove, 5);
			} else if (moveNum >= 2.28 && moveNum < 2.32) {
				setImg(bugMove, 6);
			} else if (moveNum >= 2.32 && moveNum < 2.36) {
				setImg(bugMove, 7);
			} else if (moveNum >= 2.36 && moveNum < 2.40) {
				setImg(bugMove, 8);
			} else if (moveNum >= 2.4 && moveNum < 2.44) {
				setImg(bugMove, 9);
			}else if (moveNum > 2.48) {
				if (!battleDone()) {
					continueBattle();
				} else {
					endBattle();
				}
			}
		}

		public void animateWaterMove() {

//			if(which ==0)
//				waterMove.setLoc(this.enemyPokemonSprite.getX(), this.enemyPokemonSprite.getY());
//			if(which ==1)
//				waterMove.setLoc(this.playerPokemonSprite.getX(), this.playerPokemonSprite.getY());
//					
	//System.out.println(waterMove.getX());
	//System.out.println(waterMove.getY());
			// if which is 0; player is using the move
			// if which is 1, enemy is using the move;
//			System.out.println(waterMove.getX() + " , " + waterMove.getY());
//			System.out.println("Enemy's pokemon: " + enemyPokemonSprite.getX() + " , " + enemyPokemonSprite.getY());
			if (moveNum >= 2.04 && moveNum < 2.08) {
				setImg(waterMove, 0);
			} else if (moveNum >= 2.08 && moveNum < 2.12) {
				setImg(waterMove, 1);
			} else if (moveNum >= 2.12 && moveNum < 2.16) {
				setImg(waterMove, 2);
			} else if (moveNum >= 2.16 && moveNum < 2.20) {
				setImg(waterMove, 3);
			} else if (moveNum >= 2.20 && moveNum < 2.24) {
				setImg(waterMove, 4);
			} else if (moveNum >= 2.24 && moveNum < 2.28) {
				setImg(waterMove, 5);
			} else if (moveNum >= 2.28 && moveNum < 2.32) {
				setImg(waterMove, 6);
			} else if (moveNum >= 2.32 && moveNum < 2.36) {
				setImg(waterMove, 7);
			} else if (moveNum >= 2.36 && moveNum < 2.40) {
				setImg(waterMove, 8);
			} else if (moveNum >= 2.4 && moveNum < 2.44) {
				setImg(waterMove, 9);
			}else if (moveNum > 2.48) {
				if (!battleDone()) {
					continueBattle();
				} else {
					endBattle();
				}
			}
		}

		public void animateElectricMove() {
			System.out.println(moveNum);
			if (moveNum >= 2.05 && moveNum < 2.09) {
				setImg(electricMove, 0);
			} else if (moveNum >= 2.09 && moveNum < 2.13) {
				setImg(electricMove, 1);
			} else if (moveNum >= 2.13 && moveNum < 2.17) {
				setImg(electricMove, 2);
			} else if (moveNum >= 2.17 && moveNum < 2.21) {
				setImg(electricMove, 3);
			} else if (moveNum >= 2.21 && moveNum < 2.25) {
				setImg(electricMove, 4);
			} else if (moveNum >= 2.25 && moveNum < 2.29) {
				setImg(electricMove, 5);
			} else if (moveNum>=2.29)
				if (!battleDone()) {
					continueBattle();
				} else {
					endBattle();
				}
		}

		public void animateGrassMove() {
			if (moveNum >= 2 && moveNum < 2.05) {
				setImg(grassMove, 0);
			} else if (moveNum >= 2.05 && moveNum < 2.10) {
				setImg(grassMove, 1);
			} else if (moveNum >= 2.10 && moveNum < 2.15) {
				setImg(grassMove, 2);
			} else if (moveNum >= 2.15 && moveNum < 2.20) {
				setImg(grassMove, 3);
			} else if (moveNum >= 2.20 && moveNum < 2.25) {
				setImg(grassMove, 4);
			} else if (moveNum >= 2.25 && moveNum < 2.30) {
				setImg(grassMove, 5);
			} else if (moveNum >= 2.30 && moveNum < 2.35) {
				setImg(grassMove, 6);
			} else if (moveNum >= 2.35) {
				if (!battleDone()) {
					continueBattle();
				} else {
					endBattle();
				}
			}
		}

		public void animateNormalMove() {
			// if which is 0; player is using the move
			// if which is 1, enemy is using the move;
			if (moveNum >= 2.0 && moveNum < 2.04) {
				setImg(normalMove, 0);
			} else if (moveNum >= 2.04 && moveNum < 2.08) {
				setImg(normalMove, 1);
			} else if (moveNum >= 2.08 && moveNum < 2.12) {
				setImg(normalMove, 2);
			} else if (moveNum >= 2.12 && moveNum < 2.16) {
				setImg(normalMove, 3);
			} else if (moveNum >= 2.16 && moveNum < 2.20) {
				setImg(normalMove, 4);
			} else if (moveNum >= 2.20 && moveNum < 2.24) {
				setImg(normalMove, 5);
			}else if (moveNum >= 2.24 && moveNum < 2.28) {
				setImg(normalMove, 6);
			} else if (moveNum >= 2.28) {
				if (!battleDone()) {
					continueBattle();
				} else {
					endBattle();
				}
			}
		}

		public void animateFireMove() {
			if (moveNum >= 2 && moveNum < 2.04) {
				setImg(fireMove, 0);
				fireMove.translate(5, -1);
			} else if (moveNum >= 2.04 && moveNum < 2.08) {
				setImg(fireMove, 1);
				fireMove.translate(5, -1);
			} else if (moveNum >= 2.08 && moveNum < 2.12) {
				setImg(fireMove, 2);
				fireMove.translate(5, -1);
			} else if (moveNum >= 2.12 && moveNum < 2.16) {
				setImg(fireMove, 3);
				fireMove.translate(5, -1);
			} else if (moveNum >= 2.16 && moveNum < 2.20) {
				setImg(fireMove, 4);
				fireMove.translate(5, -1);
			} else if (moveNum >= 2.20 && moveNum < 2.24) {
				setImg(fireMove, 5);
				fireMove.translate(5, -1);
			} else if (moveNum >= 2.24 && moveNum < 2.28) {
				setImg(fireMove, 6);
			} else if (moveNum >= 2.28 && moveNum < 2.32) {
				setImg(fireMove, 7);
			} else if (moveNum >= 2.32 && moveNum < 2.36) {
				setImg(fireMove, 8);
			} else if (moveNum >= 2.36 && moveNum < 2.40) {
				setImg(fireMove, 9);
			} else if (moveNum >= 2.40 && moveNum < 2.44) {
				setImg(fireMove, 10);
			} else if (moveNum >= 2.29) {
				System.out.println("BattleDone " + battleDone());
				if (!battleDone()) {
					continueBattle();
				} else {
					endBattle();
				}
			}
		}
		public void animatePokeBall() {
//			System.out.println(pokeBallSprite.getX() + " , " + pokeBallSprite.getY());
			int interval = (enemyPokemonSprite.getX() + enemyPokemonSprite.getCurrentImage().getWidth(null))/30;
			if (moveNum >= 2 && moveNum < 2.04) {
				setImg(pokeBallSprite, 0);
				pokeBallSprite.translate(interval, -65);
			} else if (moveNum >= 2.04 && moveNum < 2.08) {
				pokeBallSprite.translate(interval, -50);
			} else if (moveNum >= 2.08 && moveNum < 2.12) {
				pokeBallSprite.translate(interval, -35);
			} else if (moveNum >= 2.12 && moveNum < 2.16) {
				pokeBallSprite.translate(interval, -20);
			} else if (moveNum >= 2.16 && moveNum < 2.20) {
				pokeBallSprite.translate(interval, -15);
			} else if (moveNum >= 2.20 && moveNum < 2.24) {
				pokeBallSprite.translate(interval, -10);
			} else if (moveNum >= 2.24 && moveNum < 2.28) {
				pokeBallSprite.translate(interval, -5);
			} else if (moveNum >= 2.28 && moveNum < 2.32) {
				setImg(pokeBallSprite, 1);
				pokeBallSprite.translate(interval, -2);
			} else if (moveNum >= 2.32 && moveNum < 2.36) {
				setImg(pokeBallSprite, 1);
				pokeBallSprite.translate(interval, 0);
			} else if (moveNum >= 2.36 && moveNum < 2.40) {
				pokeBallSprite.setLoc(enemyPokemonSprite.getX()+ enemyPokemonSprite.getCurrentImage().getWidth(null), enemyPokemonSprite.getY() + enemyPokemonSprite.getCurrentImage().getHeight(null));
				setImg(pokeBallSprite, 1);
				enemyPokemonSprite.setSize(0, 0);
			} else if (moveNum >= 2.40 && moveNum < 2.44) {
				setImg(pokeBallSprite, 0);
				pokeBallSprite.translate(0, 7);
			} else if (moveNum >= 2.44 && moveNum < 3.40) {
				text = "Did I catch it!?";
			}else if (moveNum >= 3.4 && moveNum < 4.40) {
				text = "Don't you DARE move!";
			}else if (moveNum >= 4.40) {
				if (!checkedIfCaught) {
					caughtPokemon = pokemonCaught();
					checkedIfCaught = true;
				}
				if (caughtPokemon) {
					text = "You caught "+enemyPokemon.getName()+" and found 3 pokeCoins!";
					alignedMove = false;
					checkedIfCaught = true;
					if (alpha<1)
						alpha+=0.02f;
					if(alpha >= 1.0f && moveNum>= 5.0)
						alpha = 1.0f;
					if (moveNum>= 5.4) {
						pokeBallAnimationFinished = true;
						enemyPokemon.healFull();
						player.addPokemon(new pokemon(enemyPokemon));
						player.playerSurvivedBattle(true);
						player.changeCoins(3);
						battleFinished = true;
					}
				} else {
					text = enemyPokemon.getName() + " broke free!";
					if (moveNum >= 4.40 && moveNum < 4.80) {
						setImg(pokeBallSprite, 1);
						enemyPokemonSprite.setSize(enemyWidth, enemyHeight);
						pokeBallSprite.resize(92);
//						pokeBallSprite.translate(0, 15);
					}
					else if (moveNum>= 4.8) {
						pokeBallSprite.setLoc(0, screenHeight);
						pokeBallSprite.setSize(32 * playerPokemonScale , 64 * playerPokemonScale);
						enemyPokemonSprite.setSize(enemyWidth, enemyHeight);
						eventMoves.remove(0);
						moveNum = 2.0;
						pokeBallAnimationFinished = true;
						alignedMove = false;
						checkedIfCaught = false;
					}
				}
			}
		}
		
		public void endBattle() {
			eventMoves.clear();
			if (player.hasFainted())
				text = "You died!";
			else if (!text.equals("You ran!")) {
				text = "You killed " + enemyPokemon.getName()+" and found 1 pokeCoin!";
			}
			if (alpha<1.0f)
				alpha+=0.02f;
			else if (alpha >= 1.0f) {
				enemyPokemon.healFull();
				player.playerSurvivedBattle(true);
				player.changeCoins(1);
				battleFinished=true;
			}
//			System.out.println("ENDING " + alpha);
			
		}
		public void continueBattle() {
//			System.out.println("CONTINUE THE BATTLE");
			if (playerPokemon.getBattleHP()<=0) {
				playerPokemonDied = true;
			} else {
				if (moveChosen && !enemyMoveAnimationFinished && enemyMoveFinished) {
					enemyMoveAnimationFinished = true;
				} else if (moveChosen && !playerMoveAnimationFinished && playerMoveFinished) {
					playerMoveAnimationFinished = true;
				}
				moveNum = 1.8;
				alignedMove = false;
			}
			eventMoves.remove(0);
		}
		private boolean pokemonCaught() {
			int x = 100*enemyPokemon.getBattleHP()/enemyPokemon.getMaxHP();
			System.out.println("lucky number must be above this num: "+x);
			int rand = randomGen.nextInt(101);
			System.out.println("lucky number: " + rand);
			if (rand>=x) 
				return true;
			return false;
		}
		private void setImg(EventObject x, int i) {
			x.setImage((String) x.getList().get(i));
		}

		public void updateMove() {
			// if(moveIsCalled)
			if ((moveChosen && !playerMoveAnimationFinished && playerMoveFinished)
					|| (moveChosen && !enemyMoveAnimationFinished && enemyMoveFinished)
					|| (pokeBallThrown && !pokeBallAnimationFinished)) {
				if (!alignedMove && moveChosen && !playerMoveAnimationFinished && playerMoveFinished) {
					grassMove.setLoc(enemyPokemonSprite.getX() - 100, enemyPokemonSprite.getY());
					fireMove.setLoc(enemyPokemonSprite.getX() - 100, enemyPokemonSprite.getY());
					waterMove.setLoc(enemyPokemonSprite.getX(), enemyPokemonSprite.getY());
					normalMove.setLoc(enemyPokemonSprite.getX(), enemyPokemonSprite.getY());
					electricMove.setLoc(enemyPokemonSprite.getX(), enemyPokemonSprite.getY());
					bugMove.setLoc(enemyPokemonSprite.getX(), enemyPokemonSprite.getY());
					poisonMove.setLoc(enemyPokemonSprite.getX(), enemyPokemonSprite.getY());
					alignedMove = true;
					System.out.println("PLAYER GOING!");
				}
				if (!alignedMove && moveChosen && !enemyMoveAnimationFinished && enemyMoveFinished) {
					grassMove.setLoc(playerPokemonSprite.getX() - 100, playerPokemonSprite.getY());
					fireMove.setLoc(playerPokemonSprite.getX() - 100, playerPokemonSprite.getY());
					waterMove.setLoc(playerPokemonSprite.getX(), playerPokemonSprite.getY());
					normalMove.setLoc(playerPokemonSprite.getX(), playerPokemonSprite.getY());
					electricMove.setLoc(playerPokemonSprite.getX(), playerPokemonSprite.getY());
					bugMove.setLoc(playerPokemonSprite.getX(), playerPokemonSprite.getY());
					poisonMove.setLoc(playerPokemonSprite.getX(), playerPokemonSprite.getY());
					alignedMove = true;
					System.out.println("ENEMY GOING!");
				}
	 
				if (pokeBallThrown && !pokeBallAnimationFinished) {
					moveNum+= 0.012;
					if(eventMoves.size()==0)
						eventMoves.add(pokeBallSprite);
					animatePokeBall();
				}
				if (( selectedMove != null && selectedMove.getType().equalsIgnoreCase("fire") && !playerMoveAnimationFinished && playerMoveFinished)
						|| (enemySelectedMove.getType().equalsIgnoreCase("fire") && !enemyMoveAnimationFinished
								&& enemyMoveFinished)) {
					moveNum += 0.009;
					if (eventMoves.size() == 0)
						eventMoves.add(fireMove);
					animateFireMove();
				} else if ((selectedMove != null && selectedMove.getType().equalsIgnoreCase("water") && !playerMoveAnimationFinished
						&& playerMoveFinished)
						|| (enemySelectedMove.getType().equalsIgnoreCase("water") && !enemyMoveAnimationFinished
								&& enemyMoveFinished)) {
					moveNum += 0.009;
					if (eventMoves.size() == 0)
						eventMoves.add(waterMove);
					animateWaterMove();

				} else if ((selectedMove != null && selectedMove.getType().equalsIgnoreCase("normal") && !playerMoveAnimationFinished
						&& playerMoveFinished)
						|| (enemySelectedMove.getType().equalsIgnoreCase("normal") && !enemyMoveAnimationFinished
								&& enemyMoveFinished)) {
					moveNum += 0.009;
					if (eventMoves.size() == 0)
						eventMoves.add(normalMove);
					animateNormalMove();
				} else if ((selectedMove != null && selectedMove.getType().equalsIgnoreCase("electric") && !playerMoveAnimationFinished
						&& playerMoveFinished)
						|| (enemySelectedMove.getType().equalsIgnoreCase("electric") && !enemyMoveAnimationFinished
								&& enemyMoveFinished)) {
					moveNum += 0.009;
					if (eventMoves.size() == 0)
						eventMoves.add(electricMove);
					animateElectricMove();
				} else if ((selectedMove != null && selectedMove.getType().equalsIgnoreCase("grass") && !playerMoveAnimationFinished
						&& playerMoveFinished)
						|| (enemySelectedMove.getType().equalsIgnoreCase("grass") && !enemyMoveAnimationFinished
								&& enemyMoveFinished)) {
					moveNum += 0.009;
					if (eventMoves.size() == 0)
						eventMoves.add(grassMove);
					animateGrassMove();
				} else if ((selectedMove != null && selectedMove.getType().equalsIgnoreCase("bug") && !playerMoveAnimationFinished
						&& playerMoveFinished)
						|| (enemySelectedMove.getType().equalsIgnoreCase("bug") && !enemyMoveAnimationFinished
								&& enemyMoveFinished)) {
					moveNum += 0.009;
					if (eventMoves.size() == 0)
						eventMoves.add(bugMove);
					animateBugMove();
				}else if ((selectedMove != null && selectedMove.getType().equalsIgnoreCase("poison") && !playerMoveAnimationFinished
						&& playerMoveFinished)
						|| (enemySelectedMove.getType().equalsIgnoreCase("poison") && !enemyMoveAnimationFinished
								&& enemyMoveFinished)) {
					moveNum += 0.009;
					if (eventMoves.size() == 0)
						eventMoves.add(poisonMove);
					animatePoisonMove();
				}

			}
//			if (!moveChosen && playerMoveAnimationFinished && !enemyMoveAnimationFinished) {
//				
//			if(enemyMoveNum==0)
//				animateNormalMove(1);
//			if(enemyMoveNum==1)
//				animateFireMove();
//			if(enemyMoveNum==2)
//				this.eventMoves.add(waterMove);
//				animateWaterMove(1);
//			if(enemyMoveNum==3)
//				animateElectricMove();
//			if(enemyMoveNum==4)
//				animateGrassMove();
//			if(enemyMoveNum==5)
//				animatePoisonMove();
//			if(enemyMoveNum==6)
//				animateBugMove();
//			}

		}

		public void animateStart() {
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

				}
			} else if (introInFinished && !introFullyFinished) {
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

				if (healthBoxOpponent.getX() != 0)
					healthBoxOpponent.translate(10, 0);
				if (healthBoxPlayer.getX() != screenWidth - 700)
					healthBoxPlayer.translate(-10, 0);
				if (playerbaseGrass.getX() != screenWidth - 1800)
					playerbaseGrass.translate(-20, 0);
				if (enemybaseGrass.getX() != screenWidth - 900) {
					enemybaseGrass.translate(10, 0);
					enemyPokemonSprite.translate(10, 0);
				}

				if (enemybaseGrass.getX() == screenWidth - 900 && playerbaseGrass.getX() == screenWidth - 1800) {
					allowBattle = true;
					if (sendRedToThrow.getX() < 0) {
						sendRedToThrow.translate(10, 0);
						text = ("GET EM " + playerPokemon.getName() + "!");

					}
					if (sendRedToThrow.getX() == -6 && throwImgNum < 5) {
						throwImgNum++;
					} else if (goBack == true && sendRedToThrow.getX() > -246) {
						sendRedToThrow.translate(-30, 0);
						pullOut = true;
						healthBoxPlayer.updateText(playerPokemon.getName() + "  Lv." + playerPokemon.getLevel());

					}
				}
				if (pullOut && playerPokemonSprite.getX() <= 100) {
					playerPokemonSprite.translate(10, 0);
				} else if (playerPokemonSprite.getX() >= 100) {
					if (introFullyFinished == false) {
						introFullyFinished = true;
						moveSelection = true;
						initialMoveSelection = true;
					}
				}

			}
		}

		public void updateHpBarColor(pokemon p, Graphics g) {
			g.setColor(new Color(50, 186, 88));
			if (p.getBattleHP() <= p.getMaxHP()/2)
				g.setColor(new Color(224, 221, 78));
			if (p.getBattleHP() <= p.getMaxHP()/4)
				g.setColor(new Color(214, 74, 74));
		}
		public void drawHpBar(Graphics g) {
			if (enemyPokemon != null && enemyPokemon.getBattleHP() > 0 && !battleFinished) {
				updateHpBarColor(enemyPokemon, g);
				g.fillRect(healthBoxOpponent.getX()+286, healthBoxOpponent.getY()+139, 278 * enemyPokemon.getBattleHP() / enemyPokemon.getMaxHP(), 21);
			}
			if (playerPokemon != null && playerPokemon.getBattleHP() > 0) {
				updateHpBarColor(playerPokemon, g);
				g.fillRect(healthBoxPlayer.getX()+398, healthBoxPlayer.getY()+122, 265 * playerPokemon.getBattleHP() / playerPokemon.getMaxHP(), 14);
			}
		}

		public void draw(Graphics g) { // THE MAIN DRAWER, IF U WANNA DRAW SOMETHING, PUT IT HERE
			if (customFont ==null) {
				try {
				    //create the font to use. Specify the size!
				    customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonBW.ttf")).deriveFont(44f);
				    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				    //register the font
				    ge.registerFont(customFont);
				} catch (IOException e) {
				    e.printStackTrace();
				} catch(FontFormatException e) {
				    e.printStackTrace();
				}
			}
			g.setFont(customFont);
			g.setColor(Color.BLACK);
			if (introInFinished == true) {
				g.drawImage(background, 0, 0, (int) pokemonRunner.screenSize.getWidth(),
						(int) pokemonRunner.screenSize.getHeight(), null);
				for (EventObject e : eventObjects) 
						e.draw(g);

				
				playerPokemonSprite.draw(g);
				enemyPokemonSprite.draw(g);
				g.setColor(Color.green);
				if ((pokeBallThrown && pokeBallAnimationFinished) || (!playerMoveAnimationFinished || !enemyMoveAnimationFinished) && moveNum >= 2) {
					// original condition moveNum>= 2
					for (EventObject e : eventMoves) 
							e.draw(g);
			
				}
				healthBoxPlayer.draw(g);
				healthBoxOpponent.draw(g);
				healthBoxPlayer.updateText(playerPokemon.getName() + "  Lv." + playerPokemon.getLevel());
				
				if(chooseNextPokemon&& !pokemonSwapped) {
					g.setColor(Color.BLACK);
					g.fillRect(screenWidth * 3/8, (screenHeight - screenWidth / 2 * 46 / 252) - 160, 80 * 6, 120);
					g.setColor(Color.WHITE);
					g.fillRect(screenWidth * 3/8 + selectedNextPokemon * 80, (screenHeight - screenWidth / 2 * 46 / 252) - 160, 80, 120);
					for (int i = 0; i < player.getPokemon().size();i++) {
						g.drawImage(player.getPokemon().get(i).getImage("front"), (screenWidth * 3/8) + (i*80), (screenHeight - screenWidth / 2 * 46 / 252) - 125 , 80, 80, null);
					}
					g.setColor(Color.GRAY);
					g.fillRect(screenWidth * 3/8, (screenHeight - screenWidth / 2 * 46 / 252) - 160, 80 * 6, 14);
					updateHpBarColor(player.getPokemon().get(selectedNextPokemon), g);
					pokemon temp = player.getPokemon().get(selectedNextPokemon);
					g.fillRect(screenWidth * 3/8, (screenHeight - screenWidth / 2 * 46 / 252) - 160, 80 * 6 * temp.getBattleHP() / temp.getMaxHP(), 14);
					
					g.setFont(smallFont);
					g.setColor(Color.white);
					g.drawString("HP: " +  temp.getBattleHP() + "/" + temp.getMaxHP(), screenWidth * 3/8, (screenHeight - screenWidth / 2 * 46 / 252) - 148);
				}
				
				drawHpBar(g);
				drawPlayer(g);
				drawText(g);

			}
			if (moveSelection && !playerPokemonWithdrawn) {
				g.drawString(pointAtOpt[0]+options[0] + " (" + player.getNumItems(0) + "x)", (int)((0.85/2)*screenWidth), textBoxY + 60);
				g.drawString(pointAtOpt[1] + options[1], (int)((0.85/2)*screenWidth), textBoxY + 110);
				for (int i = 0; i < 2; i++) {
					if (myMoves[i] == null) {
						g.drawString(pointAtSM[i] + "-----", screenWidth - 600, textBoxY + 60 + (50 * i));

					} else
						g.drawString(pointAtSM[i] + myMoves[i].getName(), (int)((1.2/2)*screenWidth), textBoxY + 60 + (50 * i));
				}
				for (int i = 0; i < 2; i++) {
					if (myMoves[i + 2] == null)
						g.drawString(pointAtSM[i + 2] + "-----", screenWidth - 400, textBoxY + 60 + (50 * i));
					else
						g.drawString(pointAtSM[i + 2] + myMoves[i + 2].getName(), (int)((1.53/2)*screenWidth),
								textBoxY + 60 + (50 * i));

				}
				g.setFont(mediumFont);
				g.drawString("View Pokemon: P", 0 , 30);
			}
			if(chooseNextPokemon) {
				g.setFont(mediumFont);
				g.drawString("Close Pokemon: P", 0 , 30);
				g.drawString("Navigate Pokemon: Arrow Keys", 230 , 30);
				g.drawString("Heal Pokemon: E", 630 , 30);
				g.drawString("Switch out Pokemon: Enter", 880 , 30);
			}
			if(introFullyFinished)
				drawFade(g);
//			drawTransition(g);
		}

		public void drawText(Graphics g) {
			g.setFont(customFont);
			g.drawImage(getImage("TextBox.png"), 0, (screenHeight - screenWidth / 2 * 46 / 252) - 40, screenWidth,
					screenWidth / 2 * 46 / 252, null);

			g.setColor(Color.GRAY);
			g.drawString(text, 140, screenHeight - textBoxHeight + 30);

			g.setColor(Color.DARK_GRAY);
			g.drawString(text, 139, screenHeight - textBoxHeight + 29);
			
		}


		private boolean battleDone() { // idk how to implement it into the actual event
			int numP = player.getPokemon().size();
			int total = 0;
			for (int i = 0; i < numP; i++) {
				pokemon check = player.getPokemon().get(i);
				if (check.getBattleHP() >= 0) {
					total += check.getBattleHP();
				}
			}
			if (total <= 0) {
				enemyPokemon.healFull();
				return true;
			}
			if(enemyPokemon.getBattleHP()<= 0) {
				return true;
			}
			System.out.println("ENEMY TOTAL HP: " + total);
			return false;

		}

		public void drawPlayer(Graphics g) {
			if (allowBattle == true) {
				if (throwImgNum != 0 && throwImgNum <= 4 && goBack == false) {
					g.drawImage(sendOutAni[(int) throwImgNum], 0,
							screenHeight - sendOutAni[(int) throwImgNum].getHeight(null) - textBoxHeight, null);
					throwImgNum += 0.125;
				} else if (throwImgNum == 0) {
					g.drawImage(sendOutAni[0], sendRedToThrow.getX(), screenHeight - 294 - textBoxHeight, null);

				}
				if (throwImgNum == 4.125) {
					goBack = true;
				}
				if (goBack == true && throwImgNum > 0) {
					g.drawImage(sendOutAni[(int) throwImgNum], 0,
							screenHeight - sendOutAni[(int) throwImgNum].getHeight(null) - textBoxHeight, null);
					throwImgNum -= 0.125;
				}

			}
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

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_P && !pokeBallThrown && !pokemonSwapped && introFullyFinished &&!pokemonSwapped &&!playerPokemonWithdrawn && !(playerMoveFinished || enemyMoveFinished)) {
				chooseNextPokemon = !chooseNextPokemon;
				moveSelection = !moveSelection;
				if(moveSelection) {
					selectedMoveIndex = 0;
					selectedMove = myMoves[selectedMoveIndex];
					for(int i = 0 ; i < 4; i ++)
						pointAtSM[i] = "";
					pointAtSM[0] = "->";
				} else {
					selectedMove = null;
					if(selectedOptNum!=-1)
						pointAtOpt[selectedOptNum] = "";
					selectedOptNum = -1;
					
				}
			}
			else if (moveSelection && !playerPokemonWithdrawn) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if(selectedOptNum == 1) {
						pointAtOpt[selectedOptNum] = "";
						selectedOptNum = 0;
						pointAtOpt[selectedOptNum] = "->";
					}
					else if (selectedMoveIndex == 1 || selectedMoveIndex == 3) {
						pointAtSM[selectedMoveIndex] = "";
						selectedMoveIndex--;
						pointAtSM[selectedMoveIndex] = "-> ";
						selectedMove = myMoves[selectedMoveIndex];
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if(selectedOptNum == 0) {
						pointAtOpt[selectedOptNum] = "";
						selectedOptNum = 1;
						pointAtOpt[selectedOptNum] = "->";
					}
					else if (selectedMoveIndex == 0 || selectedMoveIndex == 2) {
						pointAtSM[selectedMoveIndex] = "";
						selectedMoveIndex++;
						pointAtSM[selectedMoveIndex] = "-> ";
						selectedMove = myMoves[selectedMoveIndex];
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if(selectedMoveIndex == 0) {
						pointAtSM[selectedMoveIndex] = "";
						selectedMoveIndex = -1;
						selectedMove = null;
						selectedOptNum = 0;
						pointAtOpt[selectedOptNum] = "->";
					} else if (selectedMoveIndex == 1) {
						pointAtSM[selectedMoveIndex] = "";
						selectedMoveIndex = -1;
						selectedMove = null;
						selectedOptNum = 1;
						pointAtOpt[selectedOptNum] = "->";
					} else if (selectedMoveIndex == 2 || selectedMoveIndex == 3) {
						pointAtSM[selectedMoveIndex] = "";
						selectedMoveIndex -= 2;
						pointAtSM[selectedMoveIndex] = "-> ";
						selectedMove = myMoves[selectedMoveIndex];
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (selectedOptNum == 0) {
						selectedMoveIndex = 0;
						pointAtSM[selectedMoveIndex] = "-> ";
						selectedMove = myMoves[selectedMoveIndex];
						pointAtOpt[selectedOptNum] = "";
						selectedOptNum = -1;
					} 
					else if (selectedOptNum == 1) {
						selectedMoveIndex = 1;
						pointAtSM[selectedMoveIndex] = "-> ";
						selectedMove = myMoves[selectedMoveIndex];
						pointAtOpt[selectedOptNum] = "";
						selectedOptNum = -1;
					}
					else if (selectedMoveIndex == 0 || selectedMoveIndex == 1) {
						pointAtSM[selectedMoveIndex] = "";
						selectedMoveIndex += 2;
						pointAtSM[selectedMoveIndex] = "-> ";
						selectedMove = myMoves[selectedMoveIndex];
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (selectedMove != null) {
						if (!moveAnimsInitialized) {
							initializeMove();
							moveAnimsInitialized = true;
						}
						moveSelection = false;
						moveChosen = true;
					} else if ((selectedOptNum==0 && player.getNumItems(0) > 0 && player.getPokemon().size()< 6 && player.getNumItems(player.getItemsIndex("pokeball"))>0) || selectedOptNum == 1) {
						System.out.println("I'm doing a special option: " +selectedOptNum);
						if (!moveAnimsInitialized) {
							initializeMove();
							moveAnimsInitialized = true;
						}
						moveSelection = false;
						moveChosen = true;
					}
					if (selectedOptNum==0 && player.getPokemon().size() == 6) {
						text = "You can't have more than six Pokemon!";
						specialText = true;
					} 
					if (selectedOptNum==0 && player.getNumItems(player.getItemsIndex("pokeball"))<=0) {
						text = "You ran out of pokeballs!";
						specialText = true;
					} 
				}
				System.out.println("selectedOptNum "+selectedOptNum);
//				System.out.println(selectedOptNum);
//				System.out.println(selectedMove);
			} 	else if (chooseNextPokemon) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (selectedNextPokemon>0)
						selectedNextPokemon--;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (selectedNextPokemon<player.getPokemon().size()-1)
						selectedNextPokemon++;
				} else if (e.getKeyCode() == KeyEvent.VK_E && !playerPokemonDied) {
					pokemon temp = player.getPokemon().get(selectedNextPokemon);
					if (temp.getBattleHP() > 0 && temp.getBattleHP() != temp.getMaxHP() && player.getNumItems(player.getItemsIndex("potion"))>0) {
						temp.healFull();
						player.getItems().set(1, player.getNumItems(1) - 1);
						pokemonHealCount = 50;
						text = "You healed " + temp.getName() + "!";
						playerMoveFinished = true;
						chooseNextPokemon = false;
						moveChosen = true;
					} else if (player.getNumItems(player.getItemsIndex("potion"))<=0){
						text = "You don't have enough potions!";
						specialTextCount = 80;
					} else {
			
						text = "You can't heal this pokemon!";
						specialTextCount = 80;
					}
				} 	else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(player.getPokemon().get(selectedNextPokemon).getBattleHP()>0 && player.getPokemon().get(selectedNextPokemon) != playerPokemon) {
						nextPokemonChosen = true;
						if(!moveChosen) {
							moveChosen = true;
							pokemonSwapped = true;
							chooseNextPokemon = false;
						}
					} 	else  {
						text = "You can't choose this pokemon!";
						specialTextCount = 80;
					}
				}
			}
		}
//			if (selectedMove!=null)
//				System.out.println(selectedMove.getName());
//			else
//				System.out.println("no");
//		}
		@Override
		public void setIntroInFinished(boolean b) {
			introInFinished = b;
			
		}
		@Override
		public void drawFade(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			if (alpha>1.0f)
				alpha = 1.0f;
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
			g2d.setComposite(ac);
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, screenWidth, screenHeight);
			
		}
	}



