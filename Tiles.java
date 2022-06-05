/**
 * [Tiles.java]
 * makes the tiles for the background
 */


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;

public class Tiles {
    /**varaibles */
    private boolean extinction = false;
    private boolean iced = false;
    private boolean warzone = false;
    private int type;
    private BufferedImage tileImage;

    /**constructor */
    Tiles() {
        this.type = 2;
        this.loadSprites();
    }

    /**
     * setExtinction
     * sets this tile as exintction
     */
    public void setExtinction() {
        this.extinction = true;
    }

    /**
     * setIced
     * set this tile as ice age
     */
    public void setIced() {
        this.iced = true;
    }

    /**
     * setWarzone
     * sets this tile as warzone
     */
    public void setWarzone() {
        this.warzone = true;
    }

    /**
     * resetTile
     * resets this tile
     */
    public void resetTile() {
        this.extinction = false;
        this.iced = false;
        this.warzone = false;
    }

    /**
     * loadSprites
     * loads the tile sprites
     */
    public void loadSprites() {
        try {
            switch(this.type) {
                case(0):
                    //load grass tile sprite
                    this.loadGrass();
                    break;
                case(1):
                    //load rocky tile sprite
                    this.loadStone();
                    break;
                case(2):
                    //load water tile sprite
                    this.loadWater();
                    break;
            }
        } catch (Exception e) {}
    }

    /**
     * loadGrass
     * loads the grass tile
     * @throws IOException to handle FileIO exceptions
     */
    public void loadGrass() throws IOException {
        if (this.iced) {
            //ice age grasslands
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/grasstile_iced.png"));
        } else if (this.extinction) {
            //extinction grasslands
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/grasstile_extinction.png"));
        } else if (this.warzone) {
            //warzone grasslands
        } else {
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/grasstile.png"));
        }
    }

    /**
     * loadStone
     * loads the rock tiles
     * @throws IOException to handle FileIO exceptions
     */
    public void loadStone() throws IOException {
        if (this.iced) {
            //ice age rock
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/rocktile_iced.png"));
        } else if (this.extinction) {
            //extinction rock
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/rocktile_extinction.png"));
        } else if (this.warzone) {
            //warzone rock
        } else {
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/rocktile.png"));
        }
    }

    /**
     * loadWater
     * loads the water tiles
     * @throws IOException to handle FileIO exceptions
     */
    public void loadWater() throws IOException {
        if (this.iced) {
            //ice age water
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/watertile_iced.png"));
        } else if (this.extinction) {
            //extinction water
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/watertile_extinction.png"));
        } else if (this.warzone) {
            //warzone water
        } else {
            this.tileImage = ImageIO.read(new File("./graphics/mapTiles/watertile.png"));
        }
    }

    /**
     * settype
     * sets the type of tile
     * @param type type of tile
     */
    public void setType(int type) {
        // 0 is grassy land
        // on grassy land, fruits, trees, etc can be found
        // 1 is rocky terrain
        // on rocky terrain, rocks can be found
        // 2 is water
        // in water, water organisms can be found
        // when water freezes in an ice-age, non-water  organisms
        // can traverse it, but if they do not cross it before ice
        // age ends, they will drown
        this.type = type;
    }

    /**
     * getType
     * gets type of tile
     * @return type of tile
     */
    public int getType() {
        return this.type;
    }

    /**
     * draw
     * drwas the tile
     * @param g Graphics to draw with
     * @param panel panel tp draw onto
     */
    public void draw(Graphics g, Panel panel) {
        this.loadSprites();
        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                g.drawImage(this.tileImage, i*100, j*100, panel);
            }
        }
    }

    /**
     * isExtinction
     * checks if this tile is extinction
     * @return true if this is extinction, false if not
     */
    public boolean isExtinction() {
        return this.extinction;
    }
}
