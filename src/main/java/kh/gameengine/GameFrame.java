package kh.gameengine;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import kh.gameengine.ui.GameCanvas;

/**
 * Main game frame. To run your game as a desktop app, extend this class.
 * 
 * @author kevinhooke
 */
public abstract class GameFrame extends JFrame {

	protected GameCanvas gameCanvas;

	/**
	 * Creates a new instance of Engine
	 */
	public GameFrame() {
	}

	/**
	 * Call this subclass from a subclass to start the app running.
	 */
	protected void startUp(GameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;

		// no layout
		this.setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		startUI();

	}

	private void startUI() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel panel = (JPanel) getContentPane();
				panel.setPreferredSize(new Dimension(550, 400));
				panel.setLayout(null);
				setBounds(0, 0, 550, 400);

				gameCanvas.setBounds(0, 0, 550, 400);
				panel.add(gameCanvas);

				pack();
				setResizable(false);
				setVisible(true);

				gameCanvas.runGame();
			}

		});
	}
}
