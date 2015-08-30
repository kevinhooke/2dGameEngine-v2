package kh.gameengine.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * Main game canvas and game animation logic.
 * 
 * @author kevinhooke
 */
public abstract class GameCanvas extends Canvas {

	protected BufferStrategy strategy;
	private boolean gameRunning = true;
	boolean mouseClicked = false;
	private ClickToStartMouseListener clickToStartMouseListener = new ClickToStartMouseListener();
	private ClickToRestartMouseListener clickToRestartMouseListener = new ClickToRestartMouseListener();

	/**
	 * Initialize player from init() in your subclass.
	 */
	private PlayerSprite player;

	/**
	 * 2d array that represents grid of all valid sprite location on the screen.
	 * Initialize this array in init().
	 */
	protected Object[][] spritesArray;

	protected int score = 0;
	protected int lives = 3;

	/**
	 * Background color - defaults to WHITE if not explicitly set in subclass
	 */
	protected Color backgroundColor = Color.WHITE;
	
	/** 
	 * Creates a new instance of MainWindow 
	 */
	public GameCanvas() {
		// ignore AWT paint requests
		this.setIgnoreRepaint(true);

		init();
	}

	/**
	 * Increment player score. Implement in your subclass.
	 */
	protected abstract void incrementScore();

	/**
	 * Initialize game and initial sprite positions. Implement in your subclass.
	 */
	protected abstract void init();

	/**
	 * Starts game.
	 */
	public void runGame() {
		
		//if we're restarting, remove the click to restart listener
		removeMouseListener(clickToRestartMouseListener);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createBufferStrategy(2);
				strategy = getBufferStrategy();

				Graphics2D g = null;
				g = (Graphics2D) strategy.getDrawGraphics();
				showIntroScreen(g);
				strategy.show();
				addMouseListener(clickToStartMouseListener);
				addKeyListener(new KeyboardInputHandler());
			}
		});
	}

	/**
	 * Main game loop.
	 * 
	 */
	private void gameLoop() {

		// remove the click to start mouse listener since we're already starting
		this.removeMouseListener(this.clickToStartMouseListener);

		long lastLoopTime = 0;
		this.lives = 3;
		this.score = 0;
		Graphics2D g = null;
		g = (Graphics2D) strategy.getDrawGraphics();

		while (gameRunning) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			lastLoopTime = System.currentTimeMillis();

			// Get hold of a graphics context for the accelerated
			// surface and blank it out
			g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(this.getBackgroundColor());
			g.fillRect(0, 0, 800, 600);

			// calculate positions of player and sprites
			calculatePlayerPosition(player);
			calculateSpritePositions();

			checkPlayerPositionOutOfBoundsConditions();

			if (lives == 0) {
				break;
			} else {
				// draw current sprite locations
				for (int row = 0; row < spritesArray.length; row++) {
					for (int column = 0; column < spritesArray[row].length; column++) {
						Sprite sprite = (Sprite) spritesArray[row][column];
						if (sprite != null) {
							sprite.draw(g);
						}
					}
				}

				showScore(g);
				showLives(g);

				// finally, we've completed drawing so clear up the
				// graphics and flip the buffer over
				g.dispose();
				strategy.show();

				// pause
				try {
					Thread.sleep(60);
				} catch (Exception e) {
					// /ignore
				}
			}
		}

		// end of game!
		showEndOfGameScreen(g);
		// add back the click to start listener so user can restart
		addMouseListener(clickToRestartMouseListener);
		g.dispose();
		strategy.show();
	}

	/**
	 * 
	 * @return
	 */
	protected Color getBackgroundColor(){
		return this.backgroundColor;
	}
	
	/**
	 * 
	 * @param color
	 * @return
	 */
	protected void setBackgroundColor(Color color){
		this.backgroundColor = color;
	}
	
	/**
	 * TODO:
	 */
	protected abstract void calculateSpritePositions();

	/**
	 * TODO:
	 */
	protected abstract void checkPlayerPositionOutOfBoundsConditions();

	/**
	 * Player has lost all lives - show end of game message.
	 * 
	 * @param g
	 */
	protected abstract void showEndOfGameScreen(Graphics2D g);

	/**
	 * Displays game intro screen.
	 * @param g
	 */
	protected abstract void showIntroScreen(Graphics2D g);

	protected abstract void calculatePlayerPosition(PlayerSprite player);

	protected abstract boolean ableToMoveLeft(Sprite theSpriteToMove);

	protected abstract boolean ableToMoveRight(Sprite theSpriteToMove);

	protected boolean ableToMoveUp(Sprite theSpriteToMove) {
		int currentY = theSpriteToMove.getY();
		Rectangle newPerimeter = theSpriteToMove.getPerimeter();

		newPerimeter.setLocation(theSpriteToMove.getX(), currentY - 1);

		if (!collidesWithAnotherSprite(newPerimeter, theSpriteToMove)) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean ableToMoveDown(Sprite theSpriteToMove) {
		int currentY = theSpriteToMove.getY();
		Rectangle newPerimeter = theSpriteToMove.getPerimeter();

		newPerimeter.setLocation(theSpriteToMove.getX(), currentY + 1);

		if (!collidesWithAnotherSprite(newPerimeter, theSpriteToMove)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean collidesWithAnotherSprite(Rectangle newPerimeter,
			Sprite theSpriteToMove) {
		boolean result = false;

		for (int row = 0; row < spritesArray.length; row++) {
			for (int col = 0; col < spritesArray[row].length; col++) {
				Sprite currentSprite = (Sprite) spritesArray[row][col];
				if (currentSprite != null) {
					if (!theSpriteToMove.equals(currentSprite)) {
						if (newPerimeter.intersects(currentSprite
								.getPerimeter())) {
							result = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}

	protected abstract void showLives(Graphics2D g);

	protected abstract void showScore(Graphics2D g);

	/**
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * @param lives
	 *            the lives to set
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Handles up/down/left/right key presses.
	 * 
	 * @author kev
	 */
	class KeyboardInputHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		/**
		 * Handle single keypresses op key up.
		 */
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.setPlayerMovingLeft(true);
				player.setPlayerMovingRight(false);
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.setPlayerMovingRight(true);
				player.setPlayerMovingLeft(false);
			}
			
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				player.setPlayerMovingUp(true);
			}
			
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player.setPlayerMovingDown(true);
			}
		}
	}

	protected PlayerSprite getPlayer() {
		return player;
	}

	protected void setPlayer(PlayerSprite player) {
		this.player = player;
	}

	/**
	 * MouseListneer for starting the game.
	 * @author kev
	 *
	 */
	class ClickToStartMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// start the main game loop
			new GameLoopWorkerThread().execute();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	/**
	 * MouseListener for retstarting the game on mouse click.
	 * @author kev
	 *
	 */
	class ClickToRestartMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// start the main game loop
			System.out.println("clicktorestart");
			runGame();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	class GameLoopWorkerThread extends SwingWorker<String, Object>
	{

		@Override
		protected String doInBackground() throws Exception {
			gameLoop();
			return "done";
		}
	}
	
}
