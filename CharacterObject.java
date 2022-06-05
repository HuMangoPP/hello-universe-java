/**
 * [CharacterObject.java]
 * super class of all character type objects (player, enemies)
 * extends GameObject
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

abstract public class CharacterObject extends GameObject {
    /**
     * variables
     */
    final int xBound = 1000, yBound = 1000;
    private int hp = 1;
    private int def, atk, spd, iq, range, seduction, atkSpd, evasion, accuracy;
    private boolean agriculture = false, block = false, counterattack = false, leech = false, scavenger = false, swimming = false;
    private int species = 0;
    private int healthCap;
    private int skillPts = 0;
    private Item weapon = null;
    private boolean inWar = false;
    private boolean moving = false;
    private int cols, currentRow;
    private boolean attacking = false;
    private BufferedImage[] sprites;
    private int currentSprite = 0;
    final int RATE = 100;
    private ArrayList<Integer> perks = new ArrayList<Integer>();
    private boolean isLeeched = false;
    private Random rand = new Random();

    /**
     * constructor
     * @param x x pos
     * @param y y pos
     * @param tileX tile x pos
     * @param tileY tile y pos
     */
    CharacterObject(int x, int y, int tileX, int tileY) {
        super(x, y, tileX, tileY);
        this.def = 10;
        this.atk = 10;
        this.spd = 2;
        this.iq = 0;
        this.range = 16;
        this.seduction = 2;
        this.evasion = 0;
        this.accuracy = 0;
        this.atkSpd = 10;
        this.healthCap = (int)Math.pow(this.seduction, this.iq+5);
    }

    /**
     * moveLeft
     * allows character to move left
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveLeft(Tiles t) {
        this.moving = true;
        if ((this.currentRow!=1) && (this.isAscended())) {
            this.currentRow = 1;
            this.setSpriteToFirst();
        }
        if (this.getX()>0) {
            if ((this.swimming) && (t.getType()==2)) {
                this.setPos(this.getX()-this.isEnraged()*(int)Math.floor(this.spd*1.5), this.getY());
            } else {
                this.setPos(this.getX()-this.isEnraged()*this.spd, this.getY());
            }
        }
    }

    /**
     * moveRight
     * allows character to move right
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveRight(Tiles t) {
        this.moving = true;
        if ((this.currentRow!=0) && (this.isAscended())) {
            this.currentRow = 0;
            this.setSpriteToFirst();
        }
        if (this.getX()<xBound) {
            if ((this.swimming) && (t.getType()==2)) {
                this.setPos(this.getX()+this.isEnraged()*(int)Math.floor(this.spd*1.5), this.getY());
            } else {
                this.setPos(this.getX()+this.isEnraged()*this.spd, this.getY());
            }
        }
    }

    /**
     * moveUp
     * allows character to move up
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveUp(Tiles t) {
        this.moving = true;
        if (this.getY()>0) {
            if ((this.swimming) && (t.getType()==2)) {
                this.setPos(this.getX(), this.getY()-this.isEnraged()*(int)Math.floor(this.spd*1.5));
            } else {
                this.setPos(this.getX(), this.getY()-this.isEnraged()*this.spd);
            }
        }
    }
    
    /**
     * moveUp
     * allows character to move up
     * @param t Tiles class, checks if current Tile is water tile
     */
    public void moveDown(Tiles t) {
        this.moving = true;
        if (this.getY()<yBound) {
            if ((this.swimming) && (t.getType()==2)) {
                this.setPos(this.getX(), this.getY()+this.isEnraged()*(int)Math.floor(this.spd*1.5));
            } else {
                this.setPos(this.getX(), this.getY()+this.isEnraged()*this.spd);
            }
        }
    }

    /**
     * attack
     * lets this character attack another character
     * @param c the character being attacked by this character
     */
    public void attack(CharacterObject c) {

        this.attacking = true;
        int dodge = rand.nextInt(100)+1;
        if (dodge>(this.evasion-c.accuracy)) {
            if (c.counterattack) {
                int chance = rand.nextInt();
                if (chance==0) {
                    c.attack(this);
                }
            }
            if (this.isLeeched) {
                if (this.weapon!=null) {
                    c.takeDmg(this.isEnraged()*(this.atk+this.weapon.getAtk())/4);
                } else {
                    c.takeDmg(this.isEnraged()*this.atk/4);
                }
            } else {
                if (this.weapon!=null) {
                    c.takeDmg(this.isEnraged()*this.atk+this.weapon.getAtk());
                } else {
                        c.takeDmg(this.isEnraged()*this.atk);
                }
            }
        }
    }

    /**
     * isAttacking
     * checks if the character is currently attacking
     * for attack animation
     * @return
     */
    public boolean isAttacking() {
        return this.attacking;
    }

    /**
     * takeDmg
     * allows character to take dmg
     * @param dmg dmg dealt by attacker
     */
    public void takeDmg(int dmg) {
        if (this.block) {
            int chance = this.rand.nextInt(10);
            if (chance!=0) {
                if (this.def-dmg<0) {
                    this.hp+=this.def-dmg;
                }
            }
        } else {
            if (this.def-dmg<0) {
                this.hp+=this.def-dmg;
            }
        }
    }

    /**
     * getDef
     * gets the defense of this character
     * @return defense of this character
     */
    public int getDef() {
        return this.def;
    }

    /**
     * getSpecies
     * gets the current species as an integer value
     * @return current species as an integer value
     */
    public int getSpecies() {
        return this.species;
    }

    /**
     * getIntelligence
     * gets the current intelligence stat
     * @return current intelligence stat
     */
    public int getIntelligence() {
        return this.iq;
    }

    /**
     * getPopulation
     * gets remaining hp
     * @return remaining hp
     */
    public int getPopulation() {
        return this.hp;
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
        if ((this.moving) || (this.attacking)) {
            this.playAnimation(t1, t2);
        } else {
            this.setSpriteToFirst();
        }
        g.drawImage(this.getCurrentSprite(), this.getX()-this.getWidth()/2, this.getY()-this.getHeight()/2, panel);
    }

    /**
     * setNumSpriteCols
     * sets the number of columns in the spritesheet
     * @param cols number of columns in the spritesheet
     */
    public void setNumSpriteCols(int cols) {
        this.cols = cols;
    }

    /**
     * setNotMoving
     * sets the character is not moving
     * for animation
     */
    public void setNotMoving() {
        this.moving = false;    
    }

    /**
     * setWeapon
     * equips current weapon
     * @param weapon Item weapon to equip
     */
    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    /**
     * setSpecies
     * sets the current speices
     * @param species species to set to
     */
    public void setSpecies(int species) {
        if (this.requirementsAreMet(species)) {
            this.species = species;
        }
    }

    /**
     * setSprite
     * sets the sprites into the array
     * @param index index of array
     * @param sheet the sprite as a BufferedImage
     */
    public void setSprite(int index, BufferedImage sheet) {
        this.sprites[index] = sheet;
    }

    /**
     * playAnimation
     * plays the animation 
     * @param t1 initial time 
     * @param t2 final time to calculate fps
     */
    public void playAnimation(long t1, long t2) {
        if ((t2-t1)/1000000>=RATE) {
            this.currentSprite++;
        }
        if (this.currentSprite>=(this.currentRow+1)*this.cols) {
            if (this.currentRow==2) {
                this.currentRow = 0;
            } else if (this.currentRow==3) {
                this.currentRow = 1;
            }
            this.currentSprite = (this.currentRow)*this.cols;
            this.attacking = false;
        }
    }

    /**
     * setSpriteToFirst
     * set the sprite index to the first in that row
     */
    public void setSpriteToFirst() {
        this.currentSprite = (this.currentRow)*this.cols;
    }

    /**
     * setAttacking
     * sets the character as attacking
     * @param isAttacking boolean if or if not attacking
     */
    public void setAttacking(boolean isAttacking) {
        this.attacking = isAttacking;
    }

    /**
     * inRangeEnemy
     * checks to see if CharacterObject
     * will need to be modified once weapons come into play
     * @param c
     * if this has weapon, add weapon bonus range; if not, don't
     * @return true if in range, false if not
     */
    public boolean inRangeEnemy(CharacterObject c) {
        int dist;
        dist = (int) Math.floor(Math.sqrt(Math.pow(this.getX()-c.getX(), 2) + Math.pow(this.getY()-c.getY(), 2)));

        if ((this.getTileX()==c.getTileX()) && (this.getTileY()==c.getTileY())) {
            if ((this.weapon!=null)) {
                if (dist<=this.isEnraged()*this.range+this.weapon.getRange()) {
                    if (this.leech) {
                        c.isLeeched = true;
                    }
                    return true;
                }
            } else {
                if (dist<=this.isEnraged()*this.range) {
                    if (this.leech) {
                        c.isLeeched = true;
                    }
                    return true;
                }
            }
        } 
        c.isLeeched = false;
        return false;
    }

    /**
     * inRangeInteractive
     * checks if interactive object is in range
     * @param i InteractiveObject that we are checkin
     * @return true if in range, false if not
     */
    public boolean inRangeInteractive(InteractiveObject i) {
        int dist;
        dist = (int) Math.floor(Math.sqrt(Math.pow(this.getX()-i.getX(), 2) + Math.pow(this.getY()-i.getY(), 2)));

        if ((this.getTileX()==i.getTileX()) && (this.getTileY()==i.getTileY())) {
            if (dist<=this.range) {
                return true;
            }
        }

        return false;
    }

    /**
     * reproduce
     * increase hp
     */
    public void reproduce() {
        this.updateHealthCap();
        //takes hunger and thirst to reproduce
        this.hp*=this.seduction;

        if (this.hp>this.healthCap) {
            this.hp = this.healthCap;
        }
    }

    /**
     * inWar
     * sets Enemy to be in war if conditions are met
     * @param bool true or false depending on conditions
     */
    public void inWar(boolean bool) {
        this.inWar = bool;
    }

    /**
     * isEnraged
     * checks if this is enranged
     * @return 2 if true, 1 if false
     */
    public int isEnraged() {
        if (this.inWar) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * isDead
     * checks if character is dead
     * @return true if hp<=0, false if not
     */
    public boolean isDead() {
        if (this.hp<=0) {
            return true;
        }
        return false;
    }

    /**
     * die
     * set hp to 0
     */
    public void die() {
        this.hp = 0;
    }

    /**
     * addPerks
     * adds perks based on the current species
     */
    public void addPerks() {
        switch(this.species) {
            case(1):
                //llama
                this.perks.add(0);
                this.perks.add(6);
                break;
            case(2):
                //human
                this.perks.add(1);
                this.perks.add(4);
                break;
            case(3):
                //raccoon
                this.perks.add(7);
                this.perks.add(5);
                break;
            case(4):
                //eagle
                this.perks.add(9);
                this.perks.add(0);
                break;
            case(5):
                //duck
                this.perks.add(9);
                this.perks.add(4);
                break;
            case(6):
                //mosquito
                this.perks.add(5);
                this.perks.add(9);
                break;
            case(7):
                //turtle
                this.perks.add(3);
                this.perks.add(4);
                break;
            case(8):
                //snake
                this.perks.add(4);
                this.perks.add(5);
                break;
            case(9):
                //frog
                this.perks.add(4);
                this.perks.add(6);
                break;
            case(10):
                //shark
                this.perks.add(8);
                this.perks.add(0);
                break;
            case(11):
                //cod
                this.perks.add(8);
                this.perks.add(7);
                break;
            case(12):
                //jellyfish
                this.perks.add(8);
                this.perks.add(4);
                break;
        }
    }

    /**
     * procperks
     * procs the perks as they are added to the character
     */
    public void procPerks() {
        for (int i=0; i<this.perks.size(); i++) {
            switch(perks.get(i)) {
                case(0):
                    this.accuracy += 10;
                    break;
                case(1):
                    this.agriculture = true;
                    break;
                case(3):
                    this.block = true;
                    break;
                case(4):
                    this.counterattack = true;
                    break;
                case(5):
                    this.leech = true;
                    break;
                case(6):
                    this.range += 10;
                    break;
                case(7):
                    this.scavenger = true;
                    break;
                case(8):
                    this.swimming = true;
                    break;
                case(9):
                    this.evasion += 10;
                    break;
            }
        }
    }

    /**
     * displayPerks
     * gets a list of the current perks
     * @return ArrayList of perks
     */
    public ArrayList<Integer> displayPerks() {
        return this.perks;
    }

    /**
     * isAscended
     * checks if the character has already evolved into a species
     * @return true if ascneded, false if not
     */
    public boolean isAscended() {
        if (this.species == 0) {
            return false;
        } 
        return true;
    }

    /**
     * requirementsAreMet
     * checks if the requirements for ascending to a species is met
     * @param species species we want to ascend to
     * @return true if requirements are met, false if not
     */
    public boolean requirementsAreMet(int species) {
        switch(species) {
            case(1):
                //llama
                if ((this.iq>=10) && (this.atk>=20) && (this.def>=20) && (this.spd>=15)) {
                    return true;
                } else {
                    return false;
                }
            case(2):
                //human
                if ((this.iq>=30) && (this.atk>=20) && (this.def>=15) && (this.spd>=10)) {
                    return true;
                } else {
                    return false;
                }
            case(3):
                //raccoon
                if ((this.iq>=10) && (this.atk>=20) && (this.def>=10) && (this.spd>=20)) {
                    return true;
                } else {
                    return false;
                }
            case(4):
                //eagle
                if ((this.iq>=10) && (this.atk>=20) && (this.def>=20) && (this.spd>=20)) {
                    return true;
                } else {
                    return false;
                }
            case(5):
                //duck
                if ((this.iq>=10) && (this.atk>=20) && (this.def>=15) && (this.spd>=15)) {
                    return true;
                } else {
                    return false;
                }
            case(6):
                //mosquito
                if ((this.iq>=10) && (this.atk>=20) && (this.def>=10) && (this.spd>=20)) {
                    return true;
                } else {
                    return false;
                }
            case(7):
                //turtle
                if ((this.iq>=10) && (this.atk>=20) && (this.def>=30) && (this.spd>=10)) {
                    return true;
                } else {
                    return false;
                }
            case(8):
                //snake
                if ((this.iq>=10) && (this.atk>=15) && (this.def>=15) && (this.spd>=20)) {
                    return true;
                } else {
                    return false;
                }
            case(9):
                //frog
                if ((this.iq>=10) && (this.atk>=10) && (this.def>=15) && (this.spd>=15)) {
                    return true;
                } else {
                    return false;
                }
            case(10):
                //shark
                if ((this.iq>=10) && (this.atk>=25) && (this.def>=20) && (this.spd>=25)) {
                    return true;
                } else {
                    return false;
                }
            case(11):
                //cod
                if ((this.iq>=10) && (this.atk>=10) && (this.def>=20) && (this.spd>=20)) {
                    return true;
                } else {
                    return false;
                }
            case(12):
                //jellyfish
                if ((this.iq>=10) && (this.atk>=20) && (this.def>=15) && (this.spd>=15)) {
                    return true;
                } else {
                    return false;
                }
        }

        return false;
    }

    /**
     * getSkillPts
     * get amount of skill pts 
     * @return amount of skill pts
     */
    public int getSkillPts() {
        return this.skillPts;
    }

    /**
     * levelTree
     * subtract a skill point when used
     */
    public void levelTree() {
        this.skillPts-=1;
    }

    /**
     * getAtkSpd
     * gets the character's attack speed
     * @return character's attack speed
     */
    public int getAtkSpd() {
        return this.atkSpd;
    }

    /**
     * addSkillPts
     * adds skill points to character
     */
    public void addSkillPts() {
        this.skillPts++;
    }

    /**
     * getCurrentSprite
     * gets the current sprite
     * @return current sprite
     */
    public BufferedImage getCurrentSprite() {
        return this.sprites[this.currentSprite];
    }

    /**
     * getNewSpriteArray
     * creates new sprite array to store spritesheet
     * @param rowcol amount of indices in array (row*col)
     */
    public void getNewSpriteArray(int rowcol) {
        this.sprites = new BufferedImage[rowcol];
    }

    /**
     * getStats
     * gets current stats
     * @param stats ArrayList of current stats
     */
    public void getStats(ArrayList<String> stats) {
        stats.clear();
        stats.add(""+this.hp);
        stats.add(""+this.atk);
        stats.add(""+this.def);
        stats.add(""+this.iq);
        stats.add(""+this.range);
        stats.add(""+this.seduction);
    }

    /**
     * getSeduction
     * gets current seduction stat
     * @return current seduction stat
     */
    public int getSeduction() {
        return this.seduction;
    }

    /**
     * getSpd
     * gets current speed stat
     * @return current speed stat
     */
    public int getSpd() {
        return this.spd;
    }

    /**
     * loadSprites
     * loads sprites based on species
     */
    public void loadSprites() {
        try {
            int rows = 1, cols = 1;
            BufferedImage sheet = ImageIO.read(new File("./graphics/character/blank.png"));
            super.setDimensions(32, 32);
            switch(this.getSpecies()) {
                case(0):
                    if (this instanceof Enemy) {
                        sheet = ImageIO.read(new File("./graphics/character/enemyCell.png"));
                    } else if (this instanceof Player) {
                        sheet = ImageIO.read(new File("./graphics/character/playerCell.png"));
                    }
                    rows = 1;
                    cols = 1;
                    this.setNumSpriteCols(cols);
                    break;
                case(1):
                    //llama
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/llama.png"));
                    break;
                case(2):
                    //human
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/human.png"));
                    break;
                case(3):
                    //raccoon
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/raccoon.png"));
                    break;
                case(4):
                    //eagle
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/eagle.png"));
                    break;
                case(5):
                    //duck
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/duck.png"));
                    break;
                case(6):
                    //mosquito
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/mosquito.png"));
                    break;
                case(7):
                    //turtle
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/turtle.png"));
                    break;
                case(8):
                    //python
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/python.png"));
                    break;
                case(9):
                    //frog
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/frog.png"));
                    break;
                case(10):
                    //shark
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/shark.png"));
                    break;
                case(11):
                    //cod
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/cod.png"));
                    
                    break;
                case(12):
                    //jellyfish
                    rows = 4;
                    cols = 4;
                    this.setNumSpriteCols(cols);
                    sheet = ImageIO.read(new File("./graphics/character/jellyfish.png"));
                    break;
                    
            }

            this.getNewSpriteArray(rows*cols);

            for (int j=0; j<rows; j++) {
                for (int i=0; i<cols; i++) {
                    this.setSprite(j*cols+i, sheet.getSubimage(i*this.getWidth(), j*this.getHeight(), this.getWidth(), this.getHeight()));
                }
            }


        } catch (Exception e) {}
    }

    /**
     * playAttackAnimation
     * plays the attack animation 
     */
    public void playAttackAnimation() {
        if (this.species!=0) {
            if (this.currentRow==0) {
                this.currentRow = 2;
            } else if (this.currentRow==1) {
                this.currentRow = 3;
            }
        }
        this.setSpriteToFirst();
    }

    /**
     * addStats
     * adds stats
     * @param atk atk stat
     * @param def def stat
     * @param spd spd stat
     * @param iq intelligence stat
     * @param range range stat
     * @param seduction seduction stat
     * @param evasion evasion stat
     */
    public void addStats(int atk, int def, int spd, int iq, int range, int seduction, int evasion) {
        if (this.skillPts>0) {
            this.atk += atk;
            this.def += def;
            this.spd += spd;
            this.iq += iq;
            this.range += range;
            this.seduction += seduction;
            this.evasion += evasion;
            this.skillPts--;
        }

    }

    /**
     * hadAgriculture
     * checks if character has the perk agriculture
     * @return true if has, false if not
     */
    public boolean hasAgriculture() {
        return this.agriculture;
    }

    /**
     * isScavenger 
     * checks if character has the perk scavenger
     * @return true if is, false if not
     */
    public boolean isScavenger() {
        return this.scavenger;
    }

    /**
     * updateHealthCap
     * updates the health cap
     */
    public void updateHealthCap() {
        this.healthCap = (int)Math.round(Math.pow(this.seduction, this.iq+5));
    }

    public boolean hasWeapon() {
        if (this.weapon==null) {
            return false;
        } else {
            return true;
        }
    }

    public int currentWeaponType() {
        return this.weapon.getType();
    }
}
