/**
 * [Enemy.java]
 * Enemy class creates enemies
 * Enemy class allows enemies to interact with player
 * Enemy class gives enemies their own movement and gameplay patterns
 */

import java.awt.Graphics;
import java.util.Random;

public class Enemy extends CharacterObject {
    /**
     * variables
     */
    private int aggression;
    private long t1 = 0;
    private long t2 = 0;
    private Random rand = new Random();
    private int atkCounter = 0;
    private int reproductionRate;
    private boolean carrier = false;
    private int randDirection;

    /**
     * constructor
     * @param x x value
     * @param y y value
     * @param tileX tileX vlaue
     * @param tileY tileY value
     * @param aggression aggro range
     */
    Enemy(int x, int y, int tileX, int tileY, int aggression) {
        super(x, y, tileX, tileY);
        this.aggression = aggression;
        this.loadSprites();
    }

    /**
     * pathfinding
     * enemy will approach player when within aggro range
     * enemy will idle move when player not within aggro range
     * @param p Player class
     * @param t Tile class for movement
     */
    public void pathfinding(Player p, Tiles t) {
        int dist = (int) Math.floor(Math.sqrt(Math.pow(this.getX()-p.getX(), 2)+Math.pow(this.getY()-p.getY(), 2)));
        if ((dist<this.isEnraged()*this.aggression) && (this.getTileX()==p.getTileX()) && (this.getTileY()==p.getTileY())) {
            if (this.inRangeEnemy(p)) {
                this.setNotMoving();
            } else {
                if (p.getX()>this.getX()) {
                    this.moveRight(t);
                }
                if (p.getX()<this.getX()) {
                    this.moveLeft(t);
                }
                if (p.getY()>this.getY()) {
                    this.moveDown(t);
                }
                if (p.getY()<this.getY()) {
                    this.moveUp(t);
                }
            }
        } else {
            this.t2 = System.nanoTime();
            if ((this.t2-this.t1)/1000000000>=2) {
                this.randDirection = this.rand.nextInt(5);
                this.t1 = this.t2;
            }

            switch (this.randDirection) {
                case(0):
                    // stand still
                    this.setNotMoving();
                    break;
                case(1):
                    this.moveUp(t);
                    break;
                case(2):
                    this.moveDown(t);
                    break;
                case(3):
                    this.moveLeft(t);
                    break;
                case(4):
                    this.moveRight(t);
                    break;
            }
        }
    }

    /**
     * run
     * runs the Enemy class
     * @param g Graphics to draw
     * @param p Player to interact with 
     * @param t2 final time 
     * @param t1 initial time for movement
     * @param panel panel to draw onto
     * @param t Tile for movement
     */
    public void run(Graphics g, Player p, long t2, long t1, Panel panel, Tiles t) {
        this.draw(g, panel, t1, t2);
        if ((t2-t1)/1000000>=RATE) {
            this.pathfinding(p, t);
            this.atkCounter++;
            this.reproductionRate++;
        }
        if (this.atkCounter>=this.getAtkSpd()) {
            this.attack(p);
            this.atkCounter=0;
        }

        if (this.reproductionRate>=600) {
            this.reproduce();
            this.reproductionRate = 0;
        }
    }

    /**
     * attack
     * attacks player
     * @param p Player to attack
     */
    public void attack(Player p) {
        if (this.inRangeEnemy(p)) {
            this.playAttackAnimation();
            super.attack(p);
        }
    }

    /**
     * ascend
     * allow Enemy to ascend when conditions are met
     * this will be an rng system
     */
    public void ascend() {
        if (super.isAscended()) {
            int choice = this.rand.nextInt(12)+1;
            this.setSpecies(choice);
        } 
        //do not allow ascension if already done
    }

    /**
     * reproduce
     * randomly reproduces a certain number of times every gameTick
     */
    public void reproduce() {
        int temp = this.rand.nextInt(3)+1;

        for (int i=0; i<temp; i++) {
            super.reproduce();
        }
    }

    /**
     * setCarrier
     * in disease game event, set this as a carrier
     * @param bool true if carrier, false if not carrier
     */
    public void setCarrier(boolean bool) {
        this.carrier = bool;
    }

    /**
     * isCarrier
     * checks if this is a carrier
     * @return true if carrier, false if not
     */
    public boolean isCarrier() {
        return this.carrier;
    }

    /**
     * useSkillPts
     * use skill points when get skill poitns
     */
    public void useSkillPts() {
        int branch = this.rand.nextInt(4);
        int selectedStat = this.rand.nextInt(2);
        switch(branch) {
            case(0):
                switch(selectedStat) {
                    case(0):
                        //attack up
                        this.addStats(2, 0, 0, 0, 0, 0, 0);
                        break;
                    case(1):
                        //range up
                        this.addStats(0, 0, 0, 0, 2, 0, 0);
                        break;
                }
                break;
            case(1):
                switch(selectedStat) {
                    case(0):
                        //speed up
                        this.addStats(0, 0, 2, 0, 0, 0, 0);
                        break;
                    case(1):
                        //evasion up
                        this.addStats(0, 0, 0, 0, 0, 0, 2);
                        break;
                }
                break;
            case(2):
                switch(selectedStat) {
                    case(0):
                        //intelligence up
                        this.addStats(0, 0, 0, 2, 0, 0, 0);
                        break;
                    case(1):
                        //seduction up
                        this.addStats(0, 0, 0, 0, 0, 2, 0);
                        break;
                }
                break;
            case(3):
                //defense up
                //enemy does not need stamina stat
                this.addStats(0, 2, 0, 0, 0, 0, 0);
                break;
        }
    }

    /**
     * addSkillPts
     * adds kill points
     * then uses skill points
     */
    public void addSkillPts() {
        super.addSkillPts();
        this.useSkillPts();
    }
}
