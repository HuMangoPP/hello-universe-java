/**
 * [GameEvents.java]
 * accesses timed events for gameplay
 * loads the graphics for different timed events
 */

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

public class GameEvents {
    /**
     * variables
     */
    private int event = 4;
    private long gameTicks = 0;
    private int tickCounter = 0;
    final int RATE = 100;
    final int GAMERATE = 600; //game ticks every 1 minute
    private Map map;
    private int riggedGameTicks = 0;
    private Random rand = new Random();
    private int extinctionTicks = 0;
    private int prevEvent = this.event;

    /**
     * constructor
     * @param map Map class for updating map
     */
    GameEvents(Map map) {
        this.map = map;
    }

    /**
     * newGameTick
     * loads a new gametick
     * @param t1 initial time
     * @param t2 final time to calculate time between game ticks
     * @param i InteractiveObject arraylist to add new objects
     * @param p Player to spawn InteractiveObjects
     * @param e Enemy arraylist array to add new Enemies
     */
    public void newGameTick(long t1, long t2, ArrayList<InteractiveObject> i, Player p, ArrayList<Enemy>[][] e) {
        if ((t2-t1)/1000000 >= RATE) {
            this.tickCounter++;
            
        }

        if (this.tickCounter>=GAMERATE) {
            this.tickCounter = 0;
            this.gameTicks++;
            this.getNewEvent(i, e);
            this.spawnInteractiveObject(i);
            this.giveSkillPts(e, p);
            if (p.hasAgriculture()) {
                p.addToInventory(new Food(p.getX(), p.getY(), p.getTileX(), p.getTileY(), false, rand.nextInt(10)+1, rand.nextInt(5)));
            }
        }
    }

    /**
     * getNewEvent
     * randomly selects new event
     * @param i ArrayList InteractiveObject to add
     * @param e Array of ArrayList Enemies to add
     */
    public void getNewEvent(ArrayList<InteractiveObject> i, ArrayList<Enemy>[][] e) {
        this.event = this.rand.nextInt(6);
        this.detectEvent(i, e);
    }

    /**
     * detectNewEvent
     * detects the randomly selected event
     * @param i ArrayList InteractiveObject to add
     * @param e Array of ArrayList Enemies to add
     */
    public void detectEvent(ArrayList<InteractiveObject> i, ArrayList<Enemy>[][] e) {
        this.resetEvents();
        if (this.riggedGameTicks>3) {
            if (this.event==0) {
                //ice age
                //happens the least frequently
                if (this.rand.nextInt(10)==0) {
                    this.eventIceAge(i);
                } else {
                    this.event = 4;
                    this.eventNull();
                }
            } else if (this.event==1) {
                //extinction event
                //happens infrequently
                if (this.rand.nextInt(5)==0) {
                    this.eventExtinction(i);
                } else {
                    this.event = 4;
                    this.eventNull();
                }
            } else if (this.event==2) {
                //disease
                //happens frequently
                this.eventDisease(e, i);
                for (int j=0; j<5; j++) {
                    this.spawnCure(i);
                }
            } else if (this.event==3) {
                //warzone
                //happens infrequently in the beginning
                //happens as game goes on
                if (this.rand.nextInt(100)>this.gameTicks) {
                    this.eventWar(e);
                } else {
                    this.event = 4;
                    this.eventNull();
                }
            } else {
                //nothing happens
                this.eventNull();
            }
        } else {
            /**
             * this rigs the game events so the first two events will always be ice ages
             */
            if (this.riggedGameTicks>1) {
                this.eventNull();
                this.riggedGameTicks++;
            } else {
                if (this.rand.nextInt(4)==0) {
                    this.event = 0;
                    this.eventIceAge(i);
                    this.riggedGameTicks++;
                } else {
                    this.event = 4;
                    this.eventNull();
                }
            }
        }
    }

    /**
     * CurentEvent
     * gets current event in integer value
     * @return current event in integer value
     */
    public int currentEvent() {
        return this.event;
    }

    /**
     * eventIceAge
     * ice age event
     * @param i InteractiveObject to remove
     */
    public void eventIceAge(ArrayList<InteractiveObject> i) {
        this.map.setIced();
        //all plant-based objects will be removed (fruit, tree, etc)
        //players who are underwater when event starts are unable to get onto land
        //players who are on land can walk on ice, there will be no organisms on ice
        for (int j=i.size()-1; j>=0; j--) {
            if (i.get(j) instanceof Food) {
                i.remove(j);
            }
        }
    }

    /**
     * eventExtinction
     * extinction event
     * @param i InteractiveObject to remove
     */
    public void eventExtinction(ArrayList<InteractiveObject> i) {
        //on select tiles, meteors will fall from the sky
        //player must avoid these
        //if player gets hit, they take dmg
        //plant resources on the map will be removed in select tiles
        int x;
        int y;
        for (int j=0; j<10; j++) {
            x = this.rand.nextInt(20);
            y = this.rand.nextInt(20);
            this.map.setExtinction(y, x);
            for (int k=0; k<i.size(); k++) {
                if ((i.get(k).getTileX()==x) && (i.get(k).getTileY()==y)) {
                    i.remove(k);
                }
            }
        }
    }

    /**
     * eventDisease
     * disease event
     * @param e Enemy to set carrier
     * @param i InteractveObject to set rotten
     */
    public void eventDisease(ArrayList<Enemy>[][] e, ArrayList<InteractiveObject> i) {
        //attacking select organisms will cause player to take DOT dmg
        //consuming certain foods may also cause player to take DOT dmg
        //player can either choose to cure themselves or to wait until the event
        //ends
        int spread = 5;
        for (int k=0; k<5; k++) {
            int x = this.rand.nextInt(20);
            int y = this.rand.nextInt(20);
            for (int j=0; j<e[y][x].size(); j++) {
                e[y][x].get(j).setCarrier(true);
            }

            for (int j=0; j<i.size(); j++) {
                if (i.get(j) instanceof Food) {
                    int temp = this.rand.nextInt(10);
                    if (temp<spread) {
                        ((Food) i.get(j)).setRotten(true);
                    }
                }
            }
        }
    }

    /**
     * eventWar
     * war event
     * @param e Enemy to set enraged
     */
    public void eventWar(ArrayList<Enemy>[][] e) {
        //in select tiles, other organisms will become more aggressive
        //they will get bonus atk, range, agg, and spd
        for (int i=0; i<3; i++) {
            int x = this.rand.nextInt(20);
            int y = this.rand.nextInt(20);
            for (int j=0; j<e[y][x].size(); j++) {
                e[y][x].get(j).inWar(true);
            }
        }
    }

    /**
     * eventNull
     * no event
     */
    public void eventNull() {
        //nothing happens, everything is nortmal
    }

    /**
     * spawnInteractiveObject
     * spawns a new InteractiveObject 
     * @param i arraylist to add to
     * @return arraylist
     */
    public ArrayList<InteractiveObject> spawnInteractiveObject(ArrayList<InteractiveObject> i) {
        if (this.event>1) {
            int tileX = this.rand.nextInt(20);
            int tileY = this.rand.nextInt(20);
            if (this.gameTicks>25) {
                switch(this.rand.nextInt(2)) {
                    case(0):
                        if (this.map.getTile(tileX, tileY).getType() == 2) {
                            i.add(new Food(this.rand.nextInt(1001), this.rand.nextInt(1001), tileX, tileY, true, this.rand.nextInt(3)+1, this.rand.nextInt(2)+4));
                        } else if (this.map.getTile(tileX, tileY).getType() == 2) {
                            i.add(new Food(this.rand.nextInt(1001), this.rand.nextInt(1001), tileX, tileY, true, this.rand.nextInt(3)+1, 3));
                        } else {
                            i.add(new Food(this.rand.nextInt(1001), this.rand.nextInt(1001), tileX, tileY, true, this.rand.nextInt(3)+1, this.rand.nextInt(3)));
                        }
                        break;
                    case(1):
                        if (this.map.getTile(tileX, tileY).getType() == 2) {
                            i.add(new ItemDrops(this.rand.nextInt(1001), this.rand.nextInt(1001), tileX, tileY, true, this.rand.nextInt(2)+1, 2*this.rand.nextInt(2)));
                        } else if (this.map.getTile(tileX, tileY).getType() == 1) {
                            i.add(new ItemDrops(this.rand.nextInt(1001), this.rand.nextInt(1001), tileX, tileY, true, this.rand.nextInt(2)+1, 0));
                        } else {
                            i.add(new ItemDrops(this.rand.nextInt(1001), this.rand.nextInt(1001), tileX, tileY, true, this.rand.nextInt(2)+1, this.rand.nextInt(2)));
                        }
                        break;
                }
            } else {
                if (this.map.getTile(tileX, tileY).getType() == 2) {
                    i.add(new Food(this.rand.nextInt(1001), this.rand.nextInt(1001), tileX, tileY, true, this.rand.nextInt(3)+1, 6));
                }
            }
        }

        return i;
    }

    /**
     * loadFirstFood
     * loads the first set of food into the game
     * @param i arraylist to add to
     * @return arraylist of food
     */
    public ArrayList<InteractiveObject> loadFirstFood(ArrayList<InteractiveObject> i) {
        i.add(new Food(this.rand.nextInt(1001), this.rand.nextInt(1001), this.rand.nextInt(20), this.rand.nextInt(20), true, this.rand.nextInt(2)+1, 6));

        return i;
    }

    /**
     * spawnEnemy
     * spawns enemies
     * @param e arraylist to add to
     * @return arraylist of enemies
     */
    public ArrayList<Enemy>[][] spawnEnemy(ArrayList<Enemy>[][] e) {
        int minTileX = 0;
        int minTileY = 0;
        for (int i=0; i<e.length; i++) {
            for (int j=0; j<e[i].length; j++) {
                if (e[i][j].size()<e[minTileY][minTileX].size()) {
                    i = minTileY;
                    j = minTileX;
                }
            }
        }

        e[minTileY][minTileX].add(new Enemy(this.rand.nextInt(1001), this.rand.nextInt(1001), minTileX, minTileY, this.rand.nextInt(100)+50));
        
        for (long i=0; i<this.gameTicks; i++) {
            e[minTileY][minTileX].get(e[minTileY][minTileX].size()-1).addSkillPts();
        }
        return e;
    }

    /**
     * giveSkillPts
     * gives skillpts to player and enemy
     * @param e Enemy list
     * @param p player
     */
    public void giveSkillPts(ArrayList<Enemy>[][] e, Player p) {
        p.addSkillPts();
        for (int i=0; i<e.length; i++) {
            for (int j=0; j<e[i].length; j++) {
                for (int k=0; k<e[i][j].size(); k++) {
                    e[i][j].get(k).addSkillPts();
                }
            }
        }
    }

    /**
     * getGameTicks
     * gets current game ticks
     * @return current game ticks
     */
    public long getGameTicks() {
        return this.gameTicks;
    }

    /**
     * getRiggedGameTicks
     * gets the rigged game ticks
     * @return rigged game ticks
     */
    public int getRiggedGameTicks() {
        return this.riggedGameTicks;
    }

    /**
     * drawExtinctionGameplay
     * draws the gameplay mehchanics for extinction event
     * @param g Graphics to draw with
     * @param p Player
     * @param t1 initial time
     * @param t2 final time for gameplay mechanics timer
     */
    public void drawExtinctiongameplay(Graphics g, Player p, long t1, long t2) {
        if (this.map.getTile(p.getTileX(), p.getTileY()).isExtinction()) {
            if ((t2-t1)/1000000>=RATE) {
                this.extinctionTicks++;
            }
            g.setColor(Color.RED);
            g.drawOval(500-this.extinctionTicks*5, 500-this.extinctionTicks*5, this.extinctionTicks*10, this.extinctionTicks*10);

            if (this.extinctionTicks>=100) {
                this.extinctionTicks = 0;
                if (Math.pow(500-p.getX(), 2) + Math.pow(500-p.getY(), 2) < Math.pow(500, 2)) {
                    p.die();
                }
            }
        } 
    }

    /**
     * spawnCure
     * spawns cure for disease event
     * @param i arraylist to add to
     */
    public void spawnCure(ArrayList<InteractiveObject> i) {
        i.add(new Food(this.rand.nextInt(1000), this.rand.nextInt(1000), this.rand.nextInt(20), this.rand.nextInt(20), true, 1, 7));
    }

    /**
     * runEvent
     * runs the event
     * @param g Graphics to draw with
     * @param p Player 
     * @param t1 initial time
     * @param t2 final time for time calculations
     */
    public void runEvent(Graphics g, Player p, long t1, long t2) {
        if (this.event==1) {
            this.drawExtinctiongameplay(g, p, t1, t2);
        }
        if (this.event==0) {
            p.takeDOTDmg();
        }
    }

    /**
     * resetEvemts
     * resets the events
     */
    public void resetEvents() {
        if (this.changeEvents()) {
            this.map.resetMap();
        }
    }

    /**
     * changeEvents
     * checks if the event has changed
     * @return true if events have changed, false if not
     */
    public boolean changeEvents() {
        if (this.event!=this.prevEvent) {
            if ((this.prevEvent>3) && (this.event>3)) {
                this.prevEvent = this.event;
                return false;
            } else {
                this.prevEvent = this.event;
                return true;
            }
        }
        return false;
    }
}
