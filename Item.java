/**
 * [Item.java]
 * the Item class for player
 * includes weapons the player can craft and equip
 */

import java.io.File;
import javax.imageio.ImageIO;

/**
 * [Item.java]
 * features items equippable by the player
 * to give stat bonuses
 */

public class Item extends InteractiveObject{
    /**
     * variables
     */
    private int atk; 
    private int range;
    private boolean isEquipped;
    private int type;

    /**
     * constructor
     * @param x x pos
     * @param y y pos
     * @param tileX tileX pos
     * @param tileY tileY pos
     * @param onGround if can be picked up
     * @param atk bonus attack stat
     * @param range bonus range stat
     * @param type type of weapon
     */
    Item(int x, int y, int tileX, int tileY, boolean onGround, int atk, int range, int type) {
        super(x, y, tileX, tileY, onGround);
        this.atk = atk;
        this.range = range;
        this.type = type;
        this.loadSprites();
    }

    /**
     * loadSprites
     * loads sprites for 
     */
    public void loadSprites() {
        try {
            this.setDimensions(32, 32);
            switch(this.type) {
                case(0):
                    this.setImage(ImageIO.read(new File ("./graphics/items/greatsword_inventory.png")));
                    break;
                case(1):
                    this.setImage(ImageIO.read(new File("./graphics/items/stick_inventory.png")));
                    break;
                case(2):
                    this.setImage(ImageIO.read(new File("./graphics/items/gun_inventory.png")));
                    break;
                case(3):
                    this.setImage(ImageIO.read(new File("./graphics/items/fishingrod_inventory.png")));
                    break;
            } 
        } catch (Exception e) {}
    }

    /**
     * gatName
     * gets the name of weapon
     * more will be added
     * @return name of Item
     */
    public String getName() {
        switch(this.type) {
            case(0):
                return "Greatsword";
            case(1):
                return "Divine Stick";
            case(2):
                return "Big Gun";
            case(3):
                return "Fishing Rod";
        }
        return "Item";
    }

    /**
     * getRange
     * gets the bonus range of weapon
     * @return bonus range of weapon
     */
    public int getRange() {
        return this.range;
    }

    /**
     * getAtk
     * gets the bonus attack of weapon
     * @return bonus atk stat of weapon
     */
    public int getAtk() {
        return this.atk;
    }

    /**
     * equipItem
     * equips item
     * sets isEqipped to true
     */
    public void equipItem() {
        this.isEquipped = true;
    }

    /**
     * eqipped
     * checks if equipped
     * @return true if equipped, false if not
     */
    public boolean equipped() {
        return this.isEquipped;
    }

    /**
     * getType
     * gets the type of weapon
     * @return type of weapon in integer form
     */
    public int getType() {
        return this.type;
    }
}
