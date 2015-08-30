package kh.gameengine;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JPanel;

import kh.gameengine.ui.GameCanvas;



/**
 * Base applet class for a game canvas.
 * 
 * @author kevinhooke
 */
public class GameApplet extends JApplet{
    
	
	protected GameCanvas gameCanvas;
	
    /** 
     * Creates a new instance of Engine
     */
    public GameApplet( ) {
    }
    
    /**
     * 
     */
    @Override
    public void start() {
        this.startUp();
        
    }
    
    /**
     * Override this method to initialize gameCanvas with your specific
     * concrete impl class
     */
    protected void initGameCanvas()
    {
    	
    }
    
    public void startUp()
    {
    	initGameCanvas();
        //no layout
        this.setLayout(null);

        startUI();
    }
    
    private void startUI()
    {
        JPanel panel = (JPanel) this.getContentPane();
        panel.setPreferredSize(new Dimension(550,400));
        panel.setLayout(null);
        this.setBounds(0,0,550,400);

        gameCanvas.setBounds(0,0,550,400);
        panel.add(gameCanvas);
        
        this.setVisible(true);

        gameCanvas.runGame();
    }
}
