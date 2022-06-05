/**
 * [Map.java]
 * creates the map which the player will play on
 */


import java.util.Random;

public class Map {
    /**
     * variables
     */
    private Tiles[][] map = new Tiles[20][20];
    private Random rand = new Random();

    /** constructor */
    Map() {
        for (int i=0; i<20; i++) {
            for (int j=0; j<20; j++) {
                this.map[i][j] = new Tiles();
            }
        }
    }

    /**
     * setExtinction
     * set tile as extionction event
     * @param tileY y pos of tile
     * @param tileX x pos of tile
     */
    public void setExtinction(int tileY, int tileX) {
        this.map[tileY][tileX].setExtinction();
    }

    /**
     * setIced
     * set tile as ice age event
     */
    public void setIced() {
        for (int i=0; i<20; i++) {
            for (int j=0; j<20; j++) {
                this.map[i][j].setIced();
            }
        }
    }

    /**
     * resetMap
     * resets the map
     */
    public void resetMap() {
        for (int i=0; i<20; i++) {
            for (int j=0; j<20; j++) {
                map[i][j].resetTile();
            }
        }
    }

    /**
     * getType
     * gets tile's type
     * @param x x pos
     * @param y y pos
     * @return name of type
     */
    public String getType(int x, int y) {
        switch(map[y][x].getType()) {
            case(0):
                return "Grasslands";
            case(1):
                return "Rocky Hills";
            case(2):
                return "Water";
        }
        return ""; 
    }

    /**
     * getTile
     * gets the Tile
     * @param tileX x pos
     * @param tileY y pos
     * @return
     */
    public Tiles getTile(int tileX, int tileY) {
        return this.map[tileY][tileX];
    }

    /**
     * transformLandTiles
     * turns water tiles into rock tiles
     */
    public void transformLandTiles() {
        for (int i=0; i<150; i++) {
            if (this.map[this.rand.nextInt(20)][this.rand.nextInt(20)].getType()==2) {
                this.map[this.rand.nextInt(20)][this.rand.nextInt(20)].setType(1);
            }
        }
    }

    /**
     * transformGrassTiles
     * turns rock tiles into grass tiles
     */
    public void transformGrassTiles() {
        for (int i=0; i<50; i++) {
            if (this.map[this.rand.nextInt(20)][this.rand.nextInt(20)].getType()==1) {
                this.map[this.rand.nextInt(20)][this.rand.nextInt(20)].setType(0);
            }
        }
    }
}
