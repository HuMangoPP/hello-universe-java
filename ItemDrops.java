/**
 * [ItemDrops.java]
 * inherits Materials class
 * includes the materials players will use to craft Items
 */

import java.io.File;
import javax.imageio.ImageIO;

/**
 * [ItemDrops.java]
 * features items dropped from the environment
 * which will be used to craft
 */

public class ItemDrops extends Materials {
    /**
     * variables
     */
    private int source;

    /**
     * constructor
     * @param x x pos
     * @param y y pos
     * @param tileX tileX pos
     * @param tileY tileY pos
     * @param onGround if can be picked up
     * @param amt amount in one graphic
     * @param source source of drop
     */
     ItemDrops(int x, int y, int tileX, int tileY, boolean onGround, int amt, int source) {
        super(x, y, tileX, tileY, onGround, amt);
        this.source=source;
        this.loadSprites();
     }

     /**
      * loadSprites
      * loads sprites based on source
      */
     public void loadSprites() { 
        try {
            if (this.isOnGround()) {
                this.setDimensions(32, 32);
                switch(this.source) {
                    case(0):
                        this.setImage(ImageIO.read(new File ("./graphics/mats/rock_ground.png")));
                        break;
                    case(1):
                        this.setImage(ImageIO.read(new File("./graphics/mats/string_ground.png")));
                        break;
                    case(2):
                        this.setImage(ImageIO.read(new File("./graphics/mats/wood_ground.png")));
                        break;
                } 
            } else {
                this.setDimensions(128, 128);
                switch(this.source) {
                    case(0):
                        this.setImage(ImageIO.read(new File ("./graphics/mats/rock_inventory.png")));
                        break;
                    case(1):
                        this.setImage(ImageIO.read(new File("./graphics/mats/string_inventory.png")));
                        break;
                    case(2):
                        this.setImage(ImageIO.read(new File("./graphics/mats/wood_inventory.png")));
                        break;
                } 
            }
        } catch (Exception e) {}
     }

     /**
      * getName
      * gets the name of the drop
      * @return name of itemdrop
      */
     public String getName() {
         switch(this.source) {
            case(0):
                return "Rock";
            case(1):
                return "String";
            case(2):
                return "Wood";
         }
         return "Crafting Materials";
     }

     /**
      * getType
      * get type of itemdrop
      * @return type of itemdrop
      */
     public int getType() {
         return this.source;
     }
}
