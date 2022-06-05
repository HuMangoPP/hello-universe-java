/**
 * [Player.java]
 * this is the player class
 * extends CharacterObject
 */

import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class Player extends CharacterObject {
    /**
     * variables
     */
    private int hunger = 1000, thirst = 1000, staminaCap = 1000;
    private static int tileBound = 20;
    private static ArrayList<InteractiveObject> inventory = new ArrayList<InteractiveObject>(); 
    private static long t = 0;
    private boolean infected = false;
    private Double dotCounter = 0.0;
    private Double staminaDOTCounter = 0.0;
    private BufferedImage playerIndicator;
    private BufferedImage healthBar, hungerBar, thirstBar, skillPtsBar;
    private Random rand = new Random();
    private boolean starving = false;


    /**
     * constructor
     * @param x x pos
     * @param y y pos
     * @param tileX tileX pos
     * @param tileY tileY pos
     */
    Player(int x, int y, int tileX, int tileY) {
        super(x, y, tileX, tileY);
        super.setDimensions(32, 32);
        try{
            this.playerIndicator = ImageIO.read(new File("./graphics/character/playerIndicator.png"));
        } catch (Exception e) {}
        this.loadSprites();
    }

    /**
     * loadsSprites
     * loads sprites for the player
     */
    public void loadSprites() {
        super.loadSprites();
        try{
            this.healthBar = ImageIO.read(new File("./graphics/character/heart.png"));
            this.hungerBar = ImageIO.read(new File("./graphics/character/hunger.png"));
            this.thirstBar = ImageIO.read(new File("./graphics/character/thirst.png"));
            this.skillPtsBar = ImageIO.read(new File("./graphics/character/arrow.png"));
        } catch(Exception e) {}
    }

    /**
     * moveRight
     * allows character to move right
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveRight(Tiles t) {
        super.moveRight(t);
        if (this.getX()<xBound) {
            this.hunger-=this.getSpd()/2;
            this.thirst-=this.getSpd()/2;
        }
    }

    /**
     * moveLeft
     * allows character to move left
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveLeft(Tiles t) {
        super.moveLeft(t);
        if (this.getX()>0) {
            this.hunger-=this.getSpd()/2;
            this.thirst-=this.getSpd()/2;
        }
    }

    /**
     * moveUp
     * allows character to move up
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveUp(Tiles t) {
        super.moveUp(t);
        if (this.getY()>0) {
            this.hunger-=this.getSpd()/2;
            this.thirst-=this.getSpd()/2;
        }
    }

    /**
     * moveUp
     * allows character to move up
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveDown(Tiles t) {
        super.moveDown(t);
        if (this.getY()<yBound) {
            this.hunger-=this.getSpd()/2;
            this.thirst-=this.getSpd()/2;
        }
    }
    /**
     * atBounds
     * checks if player has reached the edge of screen
     * @return an integer value based on which bounds it is touching
     */
    public int atBounds() {
        if (super.getX()<=0) {
            return 1;
        }
        if (super.getX()>=xBound) {
            return 2;
        }
        if (super.getY()<=0) {
            return 3;
        }
        if (super.getY()>=yBound) {
            return 4;
        }
        return 0;
    }

    /**
     * run
     * runs the player class
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     * @param t1 initial time
     * @param t2 final time for animation
     */
    public void run(Graphics g, Panel panel, long t1, long t2) {
        this.checkStamina();
        this.draw(g, panel, t1, t2);
    }

    /**
     * draw
     * draws the character sprite onto the screen
     * @param g Graphics to draw with
     * @param panel Panel to draw to
     * @param t1 initial time for animation
     * @param t2 final time for animation
     */
    public void draw(Graphics g, Panel panel, long t1, long t2) {
        Font font = new Font("Comic Sans Ms", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics();
        super.draw(g, panel, t1, t2);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawImage(this.healthBar, this.getX()-this.getWidth()/2-this.healthBar.getWidth(), this.getY()-this.getHeight()/2-this.healthBar.getHeight(), panel);
        g.drawString(""+this.getPopulation(), this.getX()-this.getWidth()/2-this.healthBar.getWidth()/2-metrics.stringWidth(""+this.getPopulation())/2, this.getY()-this.getHeight()/2-this.healthBar.getHeight()/2+metrics.getHeight()/3);
        g.drawImage(this.hungerBar, this.getX()+this.getWidth()/2, this.getY()-this.hungerBar.getHeight(), panel);
        g.drawString(""+this.getHunger(), this.getX()+this.getWidth()/2+this.hungerBar.getWidth()/2-metrics.stringWidth(""+this.getHunger())/2, this.getY()-this.hungerBar.getHeight()/2+metrics.getHeight()/2);
        g.drawImage(this.thirstBar, this.getX()+this.getWidth()/2, this.getY(), panel);
        g.drawString(""+this.getThirst(), this.getX()+this.getWidth()/2+this.thirstBar.getWidth()/2-metrics.stringWidth(""+this.getThirst())/2, this.getY()+this.thirstBar.getHeight()/2+metrics.getHeight()/2);
        g.drawImage(this.playerIndicator, this.getX()-this.playerIndicator.getWidth()/2, this.getY()-this.getHeight()/2-this.playerIndicator.getHeight(), panel);
        g.drawImage(this.skillPtsBar, this.getX()-this.getWidth()/2-this.skillPtsBar.getWidth(), this.getY()-this.skillPtsBar.getHeight()/2, panel);
        g.drawString(""+this.getSkillPts(), this.getX()-this.getWidth()/2-this.skillPtsBar.getWidth()/2-metrics.stringWidth(""+this.getSkillPts())/2, this.getY()-this.skillPtsBar.getHeight()/2+2*metrics.getHeight()/3);
    }
    /**
     * pickUp
     * picks up InteractiveObject off ground if in range
     * adds item to inventory
     * @param i InteractiveObject on ground
     */
    public void pickUp(InteractiveObject i) {
        if (this.inRangeInteractive(i)) {
            i.setOnGround();
            if (this.isScavenger()) {
                ((Materials) i).addAmt(rand.nextInt(5));
            }
            if (this.hasInventory(i)==-1) {
                inventory.add(i);
            } else {
                ((Materials) inventory.get(this.hasInventory(i))).addAmt(((Materials) i).getAmt());
            }
        } 
    }

    public void addToInventory(InteractiveObject i) {
        inventory.add(i);
    }

    /**
     * hasInventory
     * checks if item is already in inventory
     * @param i InteractiveObject to check in Inventory
     * @return the index of the item in Inventory if already in Inventory
     * @return -1 if not in inventory
     */
    public int hasInventory(InteractiveObject i) {
        if (i instanceof Materials) {
            for (int j=0; j<inventory.size(); j++) {
                if (inventory.get(j).getName()==i.getName()) {
                    return j;
                }
            }
        }
        return -1;
    }

    /**
     * displayInventory
     * gets this inventory
     * @return this inventory
     */
    public ArrayList<InteractiveObject> displayInventory() {
        return inventory;
    }

    /**
     * detectItemInRange
     * checks if item in arraylist is in range to be picked up
     * @param i InteractiveObject arraylist to check
     */
    public void detectItemInRange(ArrayList<InteractiveObject> i) {
        for (int j=0; j<i.size(); j++) {
            if (this.inRangeInteractive(i.get(j))) {
                pickUp(i.get(j));
                i.remove(j);
            }
        }
    }

    /**
     * detectEnemyInRange
     * checks if enemy in arraylist is in range to be attacked
     * @param e Enemy arraylist to check
     */
    public void detectEnemyInRange(ArrayList<Enemy> e, long t1) {
        Enemy enemy = null;
        for (int i=0; i<e.size(); i++) {
            if (this.inRangeEnemy(e.get(i))) {
                enemy = e.get(i);
                break;
            }
        }
        this.setAttacking(true);
        this.playAttackAnimation();
        if (enemy!=null) {
            this.attack(enemy, t1, System.nanoTime());
        }
    }

    /**
     * getHunger
     * get current hunger stat
     * @return hunger stat
     */
    public int getHunger() {
        return this.hunger;
    }

    /**
     * eat
     * replenish hunger stat
     * @param hunger hunger pts from food eaten
     */
    public void eat(int hunger) {
        this.hunger += hunger;
        if (this.hunger>this.staminaCap) {
            this.hunger = this.staminaCap;
        }
    }

    /**
     * getThrist
     * gets current dehydration stat
     * @return currrent dehydration stat
     */
    public int getThirst() {
        return this.thirst;
    }

    /**
     * drink
     * replenish dehydration stat
     * @param thirst thirst pts from food
     */
    public void drink(int thirst) {
        this.thirst += thirst;
        if (this.thirst>this.staminaCap) {
            this.thirst = this.staminaCap;
        }
    } 

    /**
     * checkStamina
     * checks if the stamina is below or above 0
     */
    public void checkStamina() {
        if ((this.hunger<=0) || (this.thirst<=0)) {
            this.starving = true;
        } else {
            this.starving = false;
        }
    }

    /**
     * checks if this is starving
     * @return true if starving, fa;se if not
     */
    public boolean isStarving() {
        return this.starving;
    }

    /**
     * reproduce
     * get more hp
     * decrease stamina when reproduce
     */
    public void reproduce() {
        if ((this.hunger-50>0) && (this.thirst-50>0)) {
            super.reproduce();
            this.hunger-=20*this.getSeduction();
            this.thirst-=20*this.getSeduction();
        }
    }

    /**
     * attack
     * attacks Enemy if conditions are met
     * @param e Enemy to attack
     * @param t1 initial time
     * @param t2 final time, prevent player from spam attacking
     */
    public void attack(Enemy e, long t1, long t2) {
        if (this.inRangeEnemy(e)) {
            if ((t2-t)/1000000>=super.getAtkSpd()*RATE) {
                super.attack(e);
                if (e.isCarrier()) {
                    this.infected = true;
                }
                t = System.nanoTime();
            }
        }
    }

    /**
     * takeDOTDmg
     * takes damage over time if conditions are met
     */
    public void takeDOTDmg() {
        if ((this.infected) || (this.starving)) {
            this.dotCounter+=0.05;
            if (this.dotCounter>=1) {
                this.takeDmg(this.getDef()+1);
                this.dotCounter = 0.0;
            }
        }
    }


    /**
     * moveMap
     * allows character to move from tile to tile 
     * on the map if at bounds
     */
    public void moveMap() {
        switch(this.atBounds()) {
            case(1):
                //if can move left tile, then allow
                if (this.getTileX()>0) {
                    this.setTile(this.getTileX()-1, this.getTileY());
                    super.setPos(xBound, this.getY());
                }
                break;
            case(2):
                //if can move right tile, then allow
                if (this.getTileX()<tileBound) {
                    this.setTile(this.getTileX()+1, this.getTileY());
                    super.setPos(0, this.getY());
                }
                break;
            case(3):
                //if can move up tile, then allow
                if (this.getTileY()>0) {
                    this.setTile(this.getTileX(), this.getTileY()-1);
                    super.setPos(this.getX(), yBound);
                }
                break;
            case(4):
                //if can move down tile, then allow
                if (this.getTileY()<tileBound) {
                    this.setTile(this.getTileX(), this.getTileY()+1);
                    super.setPos(this.getX(), 0);
                }
                break;
        }
    }

    /**
     * ascend
     * allows player to ascend
     * @param gridX x pos selected from menu
     * @param gridY y pos selected from menu
     */
    public void ascend(int gridX, int gridY) {
        if (!super.isAscended()) {
            //allows player to choose
            //sets species to the correct number

            this.setSpecies(3*gridX+gridY+1);
            switch(3*gridX+gridY+1) {
                case(1):
                    //llama
                    this.addStats(0, 3, 0, 0, 3, 0, 0);
                    break;
                case(2):
                    //human
                    this.addStats(0, 0, 0, 2, 0, 1, 0);
                    break;
                case(3):
                    //raccoon
                    this.addStats(3, 0, 3, 0, 0, 0, 0);
                    break;
                case(4):
                    //eagle
                    this.addStats(3, 0, 0, 0, 0, 0, 2);
                    break;
                case(5):
                    //duck
                    this.addStats(5, 0, 2, 0, 0, 0, 0);
                    break;
                case(6):
                    //mosquito
                    this.addStats(50, 0, 2, 0, 0, 0, 0);
                    break;
                case(7):
                    //turtle
                    this.addStats(0, 5, 0, 1, 0, 0, 0);
                    break;
                case(8):
                    //snake
                    this.addStats(3, 0, 3, 0, 0, 0, 0);
                    break;
                case(9):
                    //frog
                    this.addStats(0, 0, 2, 0, 0, 0, 2);
                    break;
                case(10):
                    //shark
                    this.addStats(5, 0, 2, 0, 0, 0, 0);
                    break;
                case(11):
                    //cod
                    this.addStats(0, 0, 3, 0, 0, 0, 1);
                    break;
                case(12):
                    //jellyfish
                    this.addStats(8, 0, 0, 0, 0, 0, 0);
                    break;
            }
        }
        //do not allow ascension if already ascended
        //players can only ascend once
    }

    /**
     * deleteItem
     * delete item from inventory
     * @param i obj to delete
     */
    public void deleteItem(InteractiveObject i) {
        inventory.remove(i);
    }

    /**
     * equipItem
     * equips Item
     * @param i Item to equip
     */
    public void equipItem(Item i) {
        this.setWeapon(i);
    }

    /**
     * useFood
     * eat food
     * @param f Food to eat
     */
    public void useFood(Food f) {
        if (f.isRotten()) {
            this.infected = true;
        } 

        if (f.getType()==7) {
            this.infected = false;
        }

        if (this.getSpecies()==0) {
            if (f.getType()==6) {
                this.eat(f.getFeed());
                this.drink(f.getQuench());
                f.subtractAmt(1);
                if (f.getAmt()==0) {
                    inventory.remove(f);
                }
            }
        } else {
            if (f.getType()!=6) {
                this.eat(f.getFeed());
                this.drink(f.getQuench());
                f.subtractAmt(1);
                if (f.getAmt()==0) {
                    inventory.remove(f);
                }
            }
        }
    }

    /**
     * craftItem
     * craft item from craft menu
     * @param item Item to craft in integer value
     */
    public void craftItem(int item) {
        boolean requirementsAreMet = false;

        switch(item) {
            case(0):
                for (int i=0; i<inventory.size(); i++) {
                    if ((inventory.get(i) instanceof ItemDrops) && ((((ItemDrops)inventory.get(i)).getType()==0) || (((ItemDrops)inventory.get(i)).getType()==2))) {
                        if (((Materials) inventory.get(i)).getAmt()>=10) {
                            requirementsAreMet = true;
                        } else {
                            requirementsAreMet = false;
                            break;
                        }
                    }
                }
                if (requirementsAreMet) {
                    inventory.add(new Item(this.getX(), this.getY(), this.getTileX(), this.getTileY(), false, this.rand.nextInt(this.getIntelligence()*4)+this.getIntelligence(), this.rand.nextInt(this.getIntelligence()/2)+this.getIntelligence()/2, item));
                }
                break;
            case(1):
                for (int i=0; i<inventory.size(); i++) {
                    if ((inventory.get(i) instanceof ItemDrops) && (((ItemDrops)inventory.get(i)).getType()==2)) {
                        if (((Materials) inventory.get(i)).getAmt()>=20) {
                            requirementsAreMet = true;
                        } else {
                            requirementsAreMet = false;
                            break;
                        }
                    }
                }
                if (requirementsAreMet) {
                    inventory.add(new Item(this.getX(), this.getY(), this.getTileX(), this.getTileY(), false, this.rand.nextInt(this.getIntelligence()*3)+this.getIntelligence(), this.rand.nextInt(this.getIntelligence()*3/2)+this.getIntelligence()/2, item));
                }
                break;
            case(2):
                for (int i=0; i<inventory.size(); i++) {
                    if ((inventory.get(i) instanceof ItemDrops) && (((ItemDrops)inventory.get(i)).getType()==0)) {
                        if (((Materials) inventory.get(i)).getAmt()>=20) {
                            requirementsAreMet = true;
                        } else {
                            requirementsAreMet = false;
                            break;
                        }
                    }
                }
                if (requirementsAreMet) {
                    inventory.add(new Item(this.getX(), this.getY(), this.getTileX(), this.getTileY(), false, this.rand.nextInt(this.getIntelligence()*2)+this.getIntelligence(), this.rand.nextInt(this.getIntelligence())+this.getIntelligence(), item));
                }
                break;
            case(3):
                for (int i=0; i<inventory.size(); i++) {
                    if ((inventory.get(i) instanceof ItemDrops) && ((((ItemDrops)inventory.get(i)).getType()==1) || (((ItemDrops)inventory.get(i)).getType()==2))) {
                        if (((Materials) inventory.get(i)).getAmt()>=10) {
                            requirementsAreMet = true;
                        } else {
                            requirementsAreMet = false;
                            break;
                        }
                    }
                }
                if (requirementsAreMet) {
                    inventory.add(new Item(this.getX(), this.getY(), this.getTileX(), this.getTileY(), false, this.rand.nextInt(4*this.getIntelligence())+this.getIntelligence(), this.rand.nextInt(this.getIntelligence()/2)+this.getIntelligence()/2, item));
                }
                break;
        }
    }

    /**
     * addStats
     * add stats when level up skill tree
     * @param atk atk stat
     * @param def def stat
     * @param spd spd stat
     * @param iq intelligence stat
     * @param range range stat
     * @param seduction seduction stat
     * @param evasion evasion stat
     * @param stamina max stamina stat
     */
    public void addStats(int atk, int def, int spd, int iq, int range, int seduction, int evasion, int stamina) {
        this.addStats(atk, def, spd, iq, range, seduction, evasion);
        this.staminaCap += stamina;
    }

    /**
     * getName
     * gets the  name of species
     * @return name of species
     */
    public String getName() {
        if (this.getSpecies()==0) {
            return "Cell";
        }
        if (this.getSpecies()==1) {
            return "Llama";
        }
        if (this.getSpecies()==2) {
            return "Human";
        }
        if (this.getSpecies()==3) {
            return "raccoon";
        }
        if (this.getSpecies()==4) {
            return "eagle";
        }
        if (this.getSpecies()==5) {
            return "duck";
        }
        if (this.getSpecies()==6) {
            return "mosquito";
        }
        if (this.getSpecies()==7) {
            return "turtle";
        }
        if (this.getSpecies()==8) {
            return "snake";
        }
        if (this.getSpecies()==9) {
            return "frog";
        }
        if (this.getSpecies()==10) {
                return "shark";
        }
        if (this.getSpecies()==11) {
            return "cod";
        }
        if (this.getSpecies()==12) {
            return "jellyfish";
        } else {
            return "";
        }

    }

    /**
     * loseStaminaOverTime
     * lose stmaina over time to prevent idle gameplay
     */
    public void loseStaminaOverTime() {
        this.staminaDOTCounter+=0.01;
        if (this.staminaDOTCounter>=1) {
            this.hunger-=this.getSeduction()/2;
            this.thirst-=this.getSeduction()/2;
            this.staminaDOTCounter = 0.0;
        }
    }

}
