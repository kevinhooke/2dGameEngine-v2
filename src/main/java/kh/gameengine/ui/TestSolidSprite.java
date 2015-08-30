package kh.gameengine.ui;

/**
 *
 * @author kevinhooke
 */
public class TestSolidSprite extends Sprite
{
    
    /** 
     * Creates a new instance of TestSolidSprite
     */
    public TestSolidSprite(String name) throws Exception
    {
        super(name, null);
        super.setSolid(true);
        this.setVisible( true );
    }

    public void moveUp() {
    }

    public void moveDown() {
    }

    public void moveLeft() {
    }

    public void moveRight() {
    }
    
}
