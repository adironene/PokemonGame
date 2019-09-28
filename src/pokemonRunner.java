import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.*;

public class pokemonRunner {
	private Font customFont;
	private JPanel panel;
	private PokemonGame game;
	private boolean startGame;
	private Timer timer;
	private int ticks;

	// Notice this intuitive method for finding the screen size
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) (screenSize.getWidth()), HEIGHT = (int) (screenSize.getHeight());
	private static final int REFRESH_RATE = 10;

	public pokemonRunner() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					start();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}
			}

		});
	}

	public static void main(String[] args) {
		new pokemonRunner();
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	private void start() {
		JFrame frame = new JFrame("Albany Amogh Emily and Phoebe are pretty cool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				game.draw(g, ticks);

			}
		};
		game = new PokemonGame();

		// random color to the background
		panel.setBackground(new Color(255, 255, 255));

		panel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				e.consume();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				game.chooseOption(e);
			}
		});

		panel.setFocusable(true);
		panel.requestFocusInWindow();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame.setLocation(WIDTH / 10, HEIGHT / 10);
		mapKeyStrokesToActions(panel);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.requestFocusInWindow();

		timer = new Timer(REFRESH_RATE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateGame();
				panel.repaint();

			}
		});
		timer.start();
	}


	protected void updateGame() {
		ticks++;// keeps track of the number of times the timer has gone off

		if (ticks % 2 == 0) {
			game.updateMap();
			game.updateMusic();
		}

	}

	private void mapKeyStrokesToActions(JPanel panel) {

		ActionMap map = panel.getActionMap();
		InputMap inMap = panel.getInputMap();

		inMap.put(KeyStroke.getKeyStroke("S"), "down");
//		inMap.put(KeyStroke.getKeyStroke("pressed DOWN"), "down");
		map.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hit("down");
			}

		});

		inMap.put(KeyStroke.getKeyStroke("pressed D"), "right");
//		inMap.put(KeyStroke.getKeyStroke("pressed RIGHT"), "right");
		map.put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hit("right");
			}

		});

		inMap.put(KeyStroke.getKeyStroke("pressed W"), "up");
		// inMap.put(KeyStroke.getKeyStroke("pressed UP"), "up");
		map.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hit("up");
			}

		});

		panel.getInputMap().put(KeyStroke.getKeyStroke("A"), "left");
		// inMap.put(KeyStroke.getKeyStroke("pressed LEFT"), "left");
		panel.getActionMap().put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("left");
			}
		});

	}

	public void hit(String s) {
		((PokemonGame) game).keyHit(s);

	}

//	protected void drawGame(Graphics g) {
//		game.draw(g, ticks);
//	}

}
