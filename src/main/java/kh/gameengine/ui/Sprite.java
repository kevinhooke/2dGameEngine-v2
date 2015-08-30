package kh.gameengine.ui;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Base sprite class.
 * 
 * @author kevinhooke
 */
public abstract class Sprite {

    private Image image;
    private Image image2;
    private Rectangle perimeter = new Rectangle();
    protected int x = 0;
    protected int y = 0;
    protected boolean visible;
    private boolean displayAlternativeImage;
    
    /**
     * Represents whether this sprite is a solid object, or whether the player can collide with is. If solid
     * the user's Sprite can touch this Sprite but not pass through it, and will not die if touching it.
     */
    private boolean solid;

    /** Creates a new instance of Sprite */
    public Sprite(String fileName, String fileName2) throws Exception {
        BufferedImage sourceImage1 = createSourceImage(fileName);
        // create an accelerated image of the right size to store our sprite in
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        image = gc.createCompatibleImage(sourceImage1.getWidth(), sourceImage1.getHeight(), Transparency.BITMASK);
        // draw our source image into the accelerated image
        image.getGraphics().drawImage(sourceImage1, 0, 0, null);

        BufferedImage sourceImage2 = null;
        if (fileName2 != null) {
            sourceImage2 = createSourceImage(fileName2);

            image2 = gc.createCompatibleImage(sourceImage2.getWidth(), sourceImage2.getHeight(), Transparency.BITMASK);
            // draw our source image into the accelerated image
            image2.getGraphics().drawImage(sourceImage2, 0, 0, null);
        }
    }

    public boolean getDisplayAlternativeImage()
    {
        return this.displayAlternativeImage;
    }

    public void setDisplayAlternativeImage(boolean newValue)
    {
            this.displayAlternativeImage = newValue;
    }

    public int getWidth() {
        return getImage().getWidth(null);
    }

    /**
     * Get the height of the drawn sprite
     *
     * @return The height in pixels of this sprite
     */
    public int getHeight() {
        return getImage().getHeight(null);
    }

    public void draw(Graphics g) {
        if (isVisible() && !this.displayAlternativeImage) {
            g.drawImage(getImage(), getX(), getY(), null);
        }
        if (isVisible() && this.displayAlternativeImage) {
            g.drawImage(getImage2(), getX(), getY(), null);
        }

    }

    public abstract void moveUp();

    public abstract void moveDown();

    public abstract void moveLeft();

    public abstract void moveRight();

    /**
     * Calculates whether the other Sprite collides or overlaps with this Sprite.
     */
    public boolean collidesWith(Sprite other) {
        return this.perimeter.intersects(other.getPerimeter());
    }

    /**
     * Calculates whether the two Recatangles overlap. Use to determine whether moving the Sprite
     * to a new position would cause a collision.
     */
    public boolean collidesWith(Rectangle thisRectangle, Rectangle otherRectangle) {
        return thisRectangle.intersects(otherRectangle);
    }

    /**
     * Draw the sprite onto the graphics context provided
     *
     * @param g The graphics context on which to draw the sprite
     * @param x The x location at which to draw the sprite
     * @param y The y location at which to draw the sprite
     */
    public void draw(Graphics g, int x, int y) {
        if(displayAlternativeImage)
        {
            g.drawImage(getImage2(), x, y, null);
        }
        else
        {
            g.drawImage(getImage(), x, y, null);
        }
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        this.x = newX;

        //update the position on our perimeter
        this.getPerimeter().setLocation(this.x, this.y);
    }

    public void setY(int newY) {
        this.y = newY;

        //update the position on our perimeter
        this.getPerimeter().setLocation(this.x, this.y);

    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public Rectangle getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Rectangle perimeter) {
        this.perimeter = perimeter;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the image2
     */
    public Image getImage2() {
        return image2;
    }

    private BufferedImage createSourceImage(String fileName) throws Exception {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            if (url == null) {
                throw new Exception("Can't find file url: " + fileName);
            }
            // use ImageIO to read the image in
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            throw new Exception("Can't find file url: " + fileName);
        }
        //set bounds of this image
        this.getPerimeter().setBounds(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        return sourceImage;
    }
}