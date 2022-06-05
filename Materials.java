/**
 * [Materials.java]
 * super class of the ItemDrops and Food class
 * inherits InteractiveObject class
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Font;

/**
 * [Materials]
 * superclass for Food and item drops
 */

abstract public class Materials extends InteractiveObject{
    /**
     * variables
     */
    private int amt;

    /**
     * constructor
     * @param x x pos
     * @param y y pos
     * @param tileX tileX pos
     * @param tileY tileY pos
     * @param type type of InteractiveObject
     * @param onGround boolean if on ground
     * @param amt amount in one graphic
     */
    Materials(int x, int y, int tileX, int tileY, boolean onGround, int amt) {
        super(x, y, tileX, tileY, onGround);
        this.amt = amt;
    }

    /**
     * subtractAmt
     * subtracts amount from item in Inventory when used
     * @param amt amount used
     */
    public void subtractAmt(int amt) {
        this.amt-=amt;
    }

    /**
     * addAmt 
     * adds amount from item in Invdentory when picked up
     * @param amt amount picked up
     */
    public void addAmt(int amt) {
        this.amt+=amt;
    }

    /**
     * getAmt
     * gets amount in Inventory
     * @return amount in Inventory
     */
    public int getAmt() {
        return this.amt;
    }

    /**
     * drawAmt
     * draws the amount of material
     * @param g Graphics to draw with
     * @param x x pos to draw
     * @param y y pos to draw
     */
    public void drawAmt(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y+112, 128, 24);
        g.setColor(Color.BLACK);
        Font font = new Font("Comic Sans MS", Font.BOLD, 20);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setFont(font);
        String amount = "x"+this.amt;
        g.drawString(amount, x+64-metrics.stringWidth(amount)/2, y+112+metrics.getHeight()/2);
    }

    /**
     * drawMatsInInventory
     * d5raws the materials in inventory menu
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     * @param x x pos to draw
     * @param y y pos to draw
     */
    public void drawMatsInInventory(Graphics g, Panel panel, int x, int y) {
        super.drawInInventory(g, panel, x, y);
        this.drawAmt(g, x, y);
    }
}
