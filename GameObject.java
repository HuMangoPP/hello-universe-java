
/**
 * [GameObject.java]
 * superclass for all game objects
 * implements GameObjectInterface
 */

abstract public class GameObject implements GameObjectInterface{
    /**
     * variables
     */
    private int x, y;
    private int tileX, tileY;
    private int w, h;

    /**
     * constructor 
     * @param x x position
     * @param y y position
     * @param tileX x tile on map
     * @param tileY y tile on map
     */
    GameObject(int x, int y, int tileX, int tileY) {
        this.x = x;
        this.y = y;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    /**
     * getX
     * gets the x value
     * @return x value
     */
    public int getX() {
        return this.x;
    }

    /**
     * getY
     * gets the y value
     * @return y value
     */
    public int getY() {
        return this.y;
    }

    /**
     * setPos
     * sets the x and y positions
     * @param x new x position
     * @param y new y position
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * setTile
     * sets the new tile position
     * @param tileX new x tile position
     * @param tileY new y tile position
     */
    public void setTile(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    /**
     * getTileX
     * gets the x tile value
     * @return x tile value
     */
    public int getTileX() {
        return this.tileX;
    }

    /**
     * getTileY
     * gets the y tile value
     * @return y tile value
     */
    public int getTileY() {
        return this.tileY;
    }

    /**
     * setDimensions
     * sets the dimensions of the sprite
     * @param w width
     * @param h height
     */
    public void setDimensions(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /**
     * getWidth
     * gets the width of sprite
     * @return width of sprite
     */
    public int getWidth() {
        return this.w;
    }

    /**
     * returnHeight
     * gets the height of the sprite
     * @return height of sprite
     */
    public int getHeight() {
        return this.h;
    }
}
