/**
 * [Food.java]
 * extends Materials
 * can be picked up by player
 * can be used to replenish stamina
 */

import java.io.File;
import javax.imageio.ImageIO;

/**
 * [Food.java]
 * features food which the player will eat
 */

public class Food extends Materials {
    /**
     * variables
     */
    private int feed;
    private int quench;
    private boolean rotten = false;
    private int type;
    
    /**
     * constructor
     * @param x x pos
     * @param y y pos
     * @param tileX tileX pos
     * @param tileY tileY pos
     * @param onGround boolean for if the object is on the ground or in Inventory
     * @param amt amount of food contained in one graphic
     * @param type type of Food in integer form
     */
    Food(int x, int y, int tileX, int tileY, boolean onGround, int amt, int type) {
        super(x, y, tileX, tileY, onGround, amt);
        this.type = type;
        switch(this.type) {
            case(0):
                this.feed = 500;
                this.quench = 500;
                break;
            case(1):
                this.feed = 700;
                this.quench = 300;
                break;
            case(2):
                this.feed = 300;
                this.quench = 700;
                break;
            case(3):
                this.feed = 700;
                this.quench = 0;
                break;
            case(4):
                this.feed = 300;
                this.quench = 700;
                break;
            case(5):
                this.feed = 200;
                this.quench = 800;
                break;
            case(6):
                this.feed = 500;
                this.quench = 500;
                break;
            case(7):
                this.feed = 10000;
                this.quench = 10000;
                break;
        }
        this.loadSprites();
    }

    /**
     * getName
     * returns name of food object
     * only has fruit for now
     * @return name of Food
     */
    public String getName() {
        switch(this.type) {
            case(0):
                return "Apple";
            case(1):
                return "Banana";
            case(2):
                return "Grapes";
            case(3):
                return "Raw Meat";
            case(4):
                return "Kelp";
            case(5):
                return "Seafood";
            case(6):
                return "Chemical Soup";
            case(7):
                return "Antidote";
        }
        return "Food";
    }

    /**
     * loadSprites
     * loads sprites for type of food
     */
    public void loadSprites() {
        try {
            if (this.isOnGround()) {
                this.setDimensions(32, 32);
                switch(this.type) {
                    case(0):
                        this.setImage(ImageIO.read(new File("./graphics/food/apple_ground.png")));
                        break;
                    case(1):
                        this.setImage(ImageIO.read(new File("./graphics/food/banana_ground.png")));
                        break;
                    case(2):
                        this.setImage(ImageIO.read(new File("./graphics/food/grapes_ground.png")));
                        break;
                    case(3):
                        this.setImage(ImageIO.read(new File("./graphics/food/rawMeat_ground.png")));
                        break;
                    case(4):
                        this.setImage(ImageIO.read(new File("./graphics/food/kelp_ground.png")));
                        break;
                    case(5):
                        this.setImage(ImageIO.read(new File("./graphics/food/seafood_ground.png")));
                        break;
                    case(6):
                        //chemical soup
                        //only able to be used by cells
                        this.setImage(ImageIO.read(new File("./graphics/food/chemicalSoup_ground.png")));
                        break;
                    case(7):
                        this.setImage(ImageIO.read(new File("./graphics/food/cure_ground.png")));
                        break;
                }
            } else {
                this.setDimensions(128, 128);
                switch(this.type) {
                    case(0):
                        this.setImage(ImageIO.read(new File("./graphics/food/apple_inventory.png")));
                        break;
                    case(1):
                        this.setImage(ImageIO.read(new File("./graphics/food/banana_inventory.png")));
                        break;
                    case(2):
                        this.setImage(ImageIO.read(new File("./graphics/food/grapes_inventory.png")));
                        break;
                    case(3):
                        this.setImage(ImageIO.read(new File("./graphics/food/rawMeat_inventory.png")));
                        break;
                    case(4):
                        this.setImage(ImageIO.read(new File("./graphics/food/kelp_inventory.png")));
                        break;
                    case(5):
                        //chemical soup
                        //only able to be used by cells
                        this.setImage(ImageIO.read(new File("./graphics/food/seafood_inventory.png")));
                        break;
                    case(6):
                    this.setImage(ImageIO.read(new File("./graphics/food/chemicalSoup_inventory.png")));
                        break;
                    case(7):
                        this.setImage(ImageIO.read(new File("./graphics/food/cure_inventory.png")));
                        break;
                }
            }
        } catch (Exception e) {}
        
    }

    /**
     * setRotten
     * sets rotten in game event disease
     * @param bool true if rotten, false if not
     */
    public void setRotten(Boolean bool) {
        this.rotten = bool;
    }

    /**
     * gettype
     * gets the type of food in integer value
     * @return type of fruit in integer value
     */
    public int getType() {
        return this.type;
    }

    /**
     * getQuench
     * gets this food's quench value
     * @return
     */
    public int getQuench() {
        return this.quench;
    }

    /**
     * getFeed
     * gets this food's feed value
     * @return
     */
    public int getFeed() {
        return this.feed;
    }

    /**
     * isRotten
     * checks if food is rotten
     * @return true if rotten, false if not
     */
    public boolean isRotten() {
        return this.rotten;
    }
}
