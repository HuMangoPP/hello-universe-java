/**
 * [Overlay.java]
 * the overlay graphics which goes on top of the game screen
 */

import java.io.File;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;



public class Overlay {
    /** variables */
    private int currentOverlay = 1;
    private BufferedImage overlay;
    private BufferedImage itemSprite;
    private ArrayList<String> itemDetails = new ArrayList<String>();
    private InteractiveObject currentObj;
    private int currentCraftingItem = 0;

    /** constructor */
    Overlay() {}

    /**
     * setOverlay
     * sets current overlay value
     * @param overlay new overlay value
     */
    public void setOverlay(int overlay) {
        this.currentOverlay = overlay;
    }

    /**
     * loadSprites
     * loads overlay sprites
     * @param menu menu for gamestate
     * @param mouseX mouseX for hover
     * @param mouseY mouseY for hover
     */
    public void loadSprites(GameMenu menu, int mouseX, int mouseY) {
        try{
            switch(this.currentOverlay) {
                case(0):
                    //no overlay
                    this.overlay = ImageIO.read(new File("./graphics/overlays/blank.png"));
                    break;
                case(1):
                    //tutorial overlay
                    this.overlay = ImageIO.read(new File("./graphics/overlays/tutorial.png"));
                    break;
                case(2):
                    //pause tab
                    if ((mouseX>300) && (mouseX<500) && (mouseY>300) && (mouseY<500)) {
                        this.overlay = ImageIO.read(new File("./graphics/overlays/tab/character_hover.png"));
                    } else if ((mouseX>300) && (mouseX<500) && (mouseY>500) && (mouseY<700)) {
                        this.overlay = ImageIO.read(new File("./graphics/overlays/tab/craft_hover.png"));
                    } else if ((mouseX>500) && (mouseX<700) && (mouseY>300) && (mouseY<500)) {
                        this.overlay = ImageIO.read(new File("./graphics/overlays/tab/settings_hover.png"));
                    } else if ((mouseX>500) && (mouseX<700) && (mouseY>500) && (mouseY<700)) {
                        this.overlay = ImageIO.read(new File("./graphics/overlays/tab/inventory_hover.png"));
                    } else {
                        this.overlay = ImageIO.read(new File("./graphics/overlays/tab/tab.png"));
                    }
                    break;
                case(3):
                    //inventory details
                    break;
                case(4):
                    //crafting details
                    break;
                case(5):
                    //event indicator
                    break;
                    
            }
        } catch (Exception e) {}
    }

    /**
     * detailoverlay
     * loads graphics for the detail overlay
     * @param i InteraciveObject i to get details
     */
    public void detailOverlay(InteractiveObject i)  {
        this.currentObj = i;
        ArrayList<String> details = new ArrayList<String>();
        try{
            if (i instanceof Food) {
                this.overlay = ImageIO.read(new File("./graphics/overlays/details/detail_food.png"));
                switch(((Food) i).getType()) {
                    case(0):
                    this.itemSprite = ImageIO.read(new File("./graphics/food/apple_inventory.png"));
                    break;
                case(1):
                    this.itemSprite = ImageIO.read(new File("./graphics/food/banana_inventory.png"));
                    break;
                case(2):
                    this.itemSprite = ImageIO.read(new File("./graphics/food/grapes_inventory.png"));
                    break;
                case(3):
                    this.itemSprite = ImageIO.read(new File("./graphics/food/rawMeat_inventory.png"));
                    break;
                case(4):
                    this.itemSprite = ImageIO.read(new File("./graphics/food/kelp_inventory.png"));
                    break;
                case(5):
                    this.itemSprite = ImageIO.read(new File("./graphics/food/cookedMeat_inventory.png"));
                    break;
                case(6):;
                    //chemical soup
                    //only able to be used by cells
                    this.itemSprite = (ImageIO.read(new File("./graphics/food/chemicalSoup_inventory.png")));
                    break;
                case(7):
                    this.itemSprite = (ImageIO.read(new File("./graphics/food/cure_inventory.png")));
                    break;
                }
            } else if (i instanceof ItemDrops) {
                this.overlay = ImageIO.read(new File("./graphics/overlays/details/detail_material.png"));
                switch(((ItemDrops) i).getType()) {
                    case(0):
                        this.itemSprite = ImageIO.read(new File ("./graphics/mats/rock_inventory.png"));
                        break;
                    case(1):
                        this.itemSprite = ImageIO.read(new File("./graphics/mats/string_inventory.png"));
                        break;
                    case(2):
                        this.itemSprite = ImageIO.read(new File("./graphics/mats/wood_inventory.png"));
                        break;
                 }
            } else if (i instanceof Item) {
                this.overlay = ImageIO.read(new File("./graphics/overlays/details/detail_item.png"));
                switch(((Item) i).getType()) {
                    case(0):
                        this.itemSprite = (ImageIO.read(new File ("./graphics/items/greatsword_ground.png")));
                        break;
                    case(1):
                        this.itemSprite = (ImageIO.read(new File("./graphics/items/stick_ground.png")));
                        break;
                    case(2):
                        this.itemSprite = (ImageIO.read(new File("./graphics/items/gun_ground.png")));
                        break;
                    case(3):
                        this.itemSprite = (ImageIO.read(new File("./graphics/items/fishingrod_ground.png")));
                        break;
                 }
            }
            //load inventory graphics
            if (i instanceof Food) {
                details.add(((Food) i).getName());
                details.add("Replenishes "+((Food) i).getFeed()+" hunger");
                details.add("Replenishes "+((Food) i).getQuench()+" thirst");
                details.add("Eat "+((Food) i).getName()+"?");
            } else if (i instanceof ItemDrops) {
                details.add(((ItemDrops) i).getName());
                details.add("Use from crafting menu");
            } else if (i instanceof Item) {
                details.add(((Item) i).getName());
                details.add("Attack: "+((Item) i).getAtk());
                details.add("Range: "+((Item) i).getRange());
                details.add("Equip "+((Item) i).getName()+"?");
            }
 
        } catch(Exception e) {}

        this.itemDetails = details;
    }

    /**
     * craftingOverlay
     * @param menu Menu 
     * @param item integer value of Item
     * @param p Player to check stats
     */
    public void craftingOverlay(int item, Player p) { 
        ArrayList<String> details = new ArrayList<String>();
        try{
            this.overlay = ImageIO.read(new File("./graphics/overlays/details/detail_crafting.png"));
            switch(item) {
                case(0):
                    this.itemSprite = ImageIO.read(new File("./graphics/items/greatsword.png"));
                    details.add("Greatsword");
                    details.add(String.format("Attack: %d - %d", p.getIntelligence(), 5*p.getIntelligence()));
                    details.add(String.format("Range: %d - %d", p.getIntelligence()/2, p.getIntelligence()));
                    details.add(String.format("Rocks x 10"));
                    details.add(String.format("Wood x 10"));
                    break;
                case(1):
                    this.itemSprite = ImageIO.read(new File("./graphics/items/stick.png"));
                    details.add("Divine Stick");
                    details.add(String.format("Attack: %d - %d", p.getIntelligence(), 4*p.getIntelligence()));
                    details.add(String.format("Range: %d - %d", p.getIntelligence()/2, 2*p.getIntelligence()));
                    details.add(String.format("Wood x 20"));
                    break;
                case(2):
                    this.itemSprite = ImageIO.read(new File("./graphics/items/gun.png"));
                    details.add("Big Gun");
                    details.add(String.format("Attack: %d - %d", p.getIntelligence(), 3*p.getIntelligence()));
                    details.add(String.format("Range: %d - %d", p.getIntelligence(), 2*p.getIntelligence()));
                    details.add(String.format("Rocks x 20"));
                    break;
                case(3):
                    this.itemSprite = ImageIO.read(new File("./graphics/items/fishingrod.png"));
                    details.add("Fishign Rod");
                    details.add(String.format("Attack: %d - %d", p.getIntelligence(), 5*p.getIntelligence()));
                    details.add(String.format("Range: %d - %d", p.getIntelligence()/2, p.getIntelligence()));
                    details.add(String.format("Wool x 10"));
                    details.add(String.format("Wood x 10"));
                    break;
            }
        } catch(Exception e) {}

        this.itemDetails = details;
        this.currentCraftingItem = item;
    }

    /**
     * getCurrentCraftign
     * @return gets the current item being crafted
     */
    public int getCurrentCrafting() {
        return this.currentCraftingItem;
    }

    /**
     * drawOverlay
     * draws the overlay onto the screen
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     */
    public void drawOverlay(Graphics g, Panel panel) {
        g.drawImage(this.overlay, 0, 0, panel);
        if ((this.currentOverlay==3) || (this.currentOverlay == 4)){
            this.drawDetailOverlay(g, panel);
        }
    }

    /**
     * drawDetailOverlay
     * draws the detail overlay
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     */
    public void drawDetailOverlay(Graphics g, Panel panel) {
        g.drawImage(this.itemSprite, 436, 236, panel);
        Font font = new Font("Comic Sans MS", Font.BOLD, 30);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setFont(font);
        g.setColor(Color.WHITE);
        for (int j=0; j<this.itemDetails.size(); j++) {
            g.drawString(this.itemDetails.get(j), 500-metrics.stringWidth(this.itemDetails.get(j))/2, 400+j*metrics.getHeight());
        }
    }

    /**
     * getCurrentOverlay
     * gets the current overlay state
     * @return currnet overlay state
     */
    public int getCurrentOverlay() {
        return this.currentOverlay;
    }

    /**
     * getCurrentObjType
     * gets the current type we are looking at in inventory
     * @return int type of obj
     */
    public int getCurrentObjType() {
        if (this.currentObj instanceof Food) {
            return 0;
        } else if (this.currentObj instanceof Item) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * getCurrentObj
     * gets the current Obj we are looking at in inventory
     * @return InteractiveObject we are looking at
     */
    public InteractiveObject getCurrentObj() {
        return this.currentObj;
    }

    /**
     * loadNewEventIndicator
     * loads the new event indicator if events change
     * @param event GameEvents to check which event
     */
    public void loadNewEventIndicator(GameEvents event) {
        try {
            if (event.currentEvent()==0) {
                this.overlay = ImageIO.read(new File("./graphics/overlays/eventIndicators/iceage_indicator.png"));
            } else if (event.currentEvent()==1) {
                this.overlay = ImageIO.read(new File("./graphics/overlays/eventIndicators/extinction_indicator.png"));
            } else if (event.currentEvent()==2) {
                this.overlay = ImageIO.read(new File("./graphics/overlays/eventIndicators/disease_indicator.png"));
            } else if (event.currentEvent()==3) {
                this.overlay = ImageIO.read(new File("./graphics/overlays/eventIndicators/war_indicator.png"));
            } else {
                this.overlay = ImageIO.read(new File("./graphics/overlays/eventIndicators/null_indicator.png"));
            }
        } catch(Exception e) {}
    }
}
