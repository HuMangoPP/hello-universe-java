/**
 * [InteractiveObject.java]
 * super class for Items and Materials class
 * extends GameObject class
 * implements InteractiveObjectInterface
 */

import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * [InteractiveObject.java]
 * superclas for the InteractiveObjects subclasses
 */

abstract public class InteractiveObject extends GameObject implements InteractiveObjectInterface{
    /** variables */
    private boolean onGround;
    private BufferedImage image;

    /**
     * contrsuctor
     * @param x x pos
     * @param y y pos
     * @param tileX tileX pos
     * @param tileY tileY pos
     * @param onGround if item is on ground or in inventory
     */
    InteractiveObject(int x, int y, int tileX, int tileY, boolean onGround) {
        super(x, y, tileX, tileY);
        this.onGround = onGround;
        super.setDimensions(10, 10);
    }

    /**
     * setOnGround
     * sets not on ground
     */
    public void setOnGround() {
        if (this.onGround) {
            this.onGround = false;
        }
    }

    /**
     * draw
     * draws the item onto the screen
     * @param g Graphics to draw with
     * @param p Player for inventory info
     * @param panel panel to draw onto
     */
    public void draw(Graphics g, Player p, Panel panel) {
        if ((this.getTileX()==p.getTileX()) && (this.getTileY()==p.getTileY())) {
            if (this.onGround) {
                g.drawImage(this.image, this.getX()-this.getWidth()/2, this.getY()-this.getHeight()/2, this.getWidth(), this.getHeight(), panel);
            }
        }
    }

    /**
     * setImage
     * sets the iamge of the sprite
     * @param image BufferedImage to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * drawInInventory
     * draws for inventory menu
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     * @param x x pos of image
     * @param y y pos of image
     */
    public void drawInInventory(Graphics g, Panel panel, int x, int y) {
        g.drawImage(this.image, x, y, panel);
    }

    /**
     * isOnground
     * checks if this is on ground
     * @return true if on ground, false if not
     */
    public boolean isOnGround() {
        return this.onGround;
    }

}
