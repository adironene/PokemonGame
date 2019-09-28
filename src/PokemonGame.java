import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PokemonGame {
	ArrayList<Clip> musicFiles = new ArrayList<Clip>();
	Player player;
	Map m;
int mapL = 79, mapW = 79;	
	int spawnPointX = 16, spawnPointY = 58;
	static int tileSize = 60;
	static int xOffset = 0;
	String currentMusicFile = "";
	Clip currentClip;
	
	static int yOffset = -50*tileSize; //these initial numbers are so that players spawn bottom left
	
	
	public PokemonGame() {
		player = new Player("reddesigns", spawnPointX, spawnPointY, tileSize,"male",16);
		m = new Map(player, tileSize, this.mapW, this.mapL, spawnPointX, spawnPointY, xOffset, yOffset);
		
		addSoundTrack("TwinLeafTown.wav");
		addSoundTrack("BattleMusic.wav");
		addSoundTrack("EncounterMusic.wav");
		addSoundTrack("Route210.wav");
	}

	public void addSoundTrack(String file) {
//		File audioInput = new File(file);
//		AudioInputStream EncounterMusic = AudioSystem.getAudioInputStream(audioInput);
//		Clip clip = AudioSystem.getClip();
//		musicFiles.add(clip);
		try {
			File musicPath = new File(file);
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
			} else {
				//System.out.println("No music found!! You messed up");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void keyHit(String s) {
		m.movePlayer(s);
	}

	public void chooseOption(KeyEvent e) {
		m.chooseOption(e);
		
	}
	public void updateMap() {
		m.updateEvent();
		m.updateMapAnimation();
	}
	public void draw(Graphics g,int ticks) {
		m.draw(g);
	}
	public void updateMusic() {

		if(currentMusicFile != m.getSoundTrack()) {
			//System.out.println("detected");
			if(currentClip!=null) {
				currentClip.stop();
				currentClip.close();
				System.out.println("stopped last sound track");
			}
			currentMusicFile = m.getSoundTrack();
			//System.out.println("new music file is " + currentMusicFile);
			currentClip = getMusic(currentMusicFile);
			currentClip.start();
			currentClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public Clip getMusic(String file) {
		try {
			File musicPath = new File(file);
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				return clip;
				
//				clip.start();
//				clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			} else {
				System.out.println("No music found!! You messed up");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}


}

