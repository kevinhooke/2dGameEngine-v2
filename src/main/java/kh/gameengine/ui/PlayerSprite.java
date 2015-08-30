package kh.gameengine.ui;

/**
 * Main sprite for the player's onscreen character.
 * 
 * @author kevinhooke
 */
public class PlayerSprite extends Sprite{
    
    protected boolean playerMovingLeft;
    protected boolean playerMovingRight;
    protected boolean playerMovingUp;
    protected boolean playerMovingDown;

    private int currentPosition;

    private int animateFrameX = 0;
    private int animateFrameY = 0;

    private static final int ANIMATE_X_PIXELS = 10;
    private static final int ANIMATE_Y_PIXELS = 10;
    private static final int ANIMATE_TOTAL_FRAMES_HORIZONTAL = 4;
    private static final int ANIMATE_TOTAL_FRAMES_VERTICAL = 5;
    private static final int ANIMATE_VERTICAL_FRAME_MIDPOINT = 2;
    
    public PlayerSprite(String fileName1, String fileName2) throws Exception {
        super(fileName1, fileName2);
        this.setVisible(true);
    }
    
    public  void moveUp() {
        y--;
    }
    
    public  void moveDown() {
        y++;
    }
    
    public  void moveLeft() {
        if(animateFrameX < ANIMATE_TOTAL_FRAMES_HORIZONTAL) {
            x-= ANIMATE_X_PIXELS;
            animateFrameX++;
            if(animateFrameX == ANIMATE_TOTAL_FRAMES_HORIZONTAL)
                animateFrameX = 0;
        }
        
        if(animateFrameY < ANIMATE_TOTAL_FRAMES_VERTICAL) {
            
            //check midpoint of animation
            if( animateFrameY < ANIMATE_VERTICAL_FRAME_MIDPOINT ) {
                y-= ANIMATE_Y_PIXELS;
            } else if( animateFrameY == ANIMATE_VERTICAL_FRAME_MIDPOINT ) {
                //no vertical change at midpoint
            } else {
                y+= ANIMATE_Y_PIXELS;
            }
            
            
            animateFrameY++;
            if(animateFrameY == ANIMATE_TOTAL_FRAMES_VERTICAL) {
                animateFrameY = 0;
                setPlayerMovingLeft(false);
                currentPosition--;
            }
        }
        
    }
    
    public  void moveRight() {
        if(animateFrameX < ANIMATE_TOTAL_FRAMES_HORIZONTAL ) //&& animateFrameX % 2 == 0
        {
            x+= ANIMATE_X_PIXELS;
            animateFrameX++;
            if(animateFrameX == ANIMATE_TOTAL_FRAMES_HORIZONTAL)
                animateFrameX = 0;
        }
        
        if(animateFrameY < ANIMATE_TOTAL_FRAMES_VERTICAL ) //&& animateFrameY % 2 == 0
        {
            
            //check midpoint of animation
            if( animateFrameY < ANIMATE_VERTICAL_FRAME_MIDPOINT ) {
                y-= ANIMATE_Y_PIXELS;
            } else if( animateFrameY == ANIMATE_VERTICAL_FRAME_MIDPOINT ) {
                //no vertical change at midpoint
            } else {
                y+= ANIMATE_Y_PIXELS;
            }
            
            
            animateFrameY++;
            if(animateFrameY == ANIMATE_TOTAL_FRAMES_VERTICAL) {
                animateFrameY = 0;
                setPlayerMovingRight(false);
                currentPosition++;
            }
        }
        
    }

    
    public boolean isPlayerMovingLeft() {
        return playerMovingLeft;
    }
    
    public void setPlayerMovingLeft(boolean playerMovingLeft) {
        this.playerMovingLeft = playerMovingLeft;
    }
    
    public boolean isPlayerMovingRight() {
        return playerMovingRight;
    }
    
    public boolean isPlayerMovingUp() {
        return playerMovingUp;
    }
    
    public boolean isPlayerMovingDown() {
        return playerMovingDown;
    }
    
    public void setPlayerMovingRight(boolean playerMovingRight) {
        this.playerMovingRight = playerMovingRight;
    }
    
    public void setPlayerMovingUp(boolean playerMovingUp) {
        this.playerMovingUp = playerMovingUp;
    }
    
    public void setPlayerMovingDown(boolean playerMovingDown) {
        this.playerMovingDown = playerMovingDown;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

}
