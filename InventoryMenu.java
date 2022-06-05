/**
 * [InventoryMenu.java]
 * displays the inventory of the player to the screen
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

public class InventoryMenu {
    /**
     * variables
     */
    private Player p;
    private BufferedImage menu;
    private BufferedImage box;

    /**
     * constructor
     * @param p Player class to get inventory
     */
    InventoryMenu(Player p) {
        this.p = p;
    }

    /**
     * loadSprites
     * loads sprites
     */
    public void loadSprites() {
        try {
            this.menu = ImageIO.read(new File("./graphics/menus/inventory/inventoryMenu.png"));
            this.box = ImageIO.read(new File("./graphics/menus/inventory/box.png"));
        } catch (Exception e) {}
    }

    /**
     * draw
     * draws onto screen
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     */
    public void draw(Graphics g, Panel panel) {
        this.loadSprites();
        g.drawImage(this.menu, 0, 0, panel);
        for (int i=0; i<this.p.displayInventory().size(); i++) {
            g.drawImage(this.box, 50+64*3*(i%5), 186+64*3*(i/5), panel);
            this.p.displayInventory().get(i).loadSprites();
            if ((this.p.displayInventory().get(i) instanceof Food) || (this.p.displayInventory().get(i) instanceof ItemDrops)) {
                ((Materials) this.p.displayInventory().get(i)).drawMatsInInventory(g, panel, 50+64*3*(i%5), 186+64*3*(i/5));
            } else {
                this.p.displayInventory().get(i).drawInInventory(g, panel, 50+64*3*(i%5), 186+64*3*(i/5));
            }
        }
    }
}
