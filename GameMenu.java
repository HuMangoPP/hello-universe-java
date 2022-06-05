/**
 * [GameMenu.java]
 * introduces some of the game menus the player will be using
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

public class GameMenu {
    /** varaibles */
    private int gameState = 0;
    private BufferedImage image;

    /** constructor */
    GameMenu() {}

    /**
     * setGameState
     * sets the current gamestate
     * @param gameState new game state to set to
     */
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    /**
     * loadSprites
     * laods menu sprites
     * @param mouseX mouseX for hover
     * @param mouseY mouseY for hover
     * @param p Player to check player stats
     * @param overlay overlays to draw onto menus
     */
    public void loadSprites(int mouseX, int mouseY, Player p, Overlay overlay) {
        try {
            switch(this.gameState) {
                case(0):
                    //main start menu
                    this.image = ImageIO.read(new File("./graphics/menus/start/main.png"));
                    break;
                case(1):
                    //start new game menu
                    this.image = ImageIO.read(new File("./graphics/menus/start/start.png")); 
                    break;
                case(2):
                    //in-game screen
                case(3):
                    //settings menu
                    //if mouse is hovering, then load other images
                    if ((mouseX>=250) && (mouseX<=750) && (mouseY>=565) && (mouseY<=715)) {
                        this.image = ImageIO.read(new File("./graphics/menus/settings/settings_game_hover.png"));
                    } else if ((mouseX>=175) && (mouseX<=825) && (mouseY>=770) && (mouseY<=930)) {
                        this.image = ImageIO.read(new File("./graphics/menus/settings/settings_title_hover.png"));
                    } else {
                        this.image = ImageIO.read(new File("./graphics/menus/settings/settings.png"));
                    }
                    break;
                case(4):
                    //character menu
                    break;
                case(5):
                    //skills menu
                    break;
                case(6):
                    //perks tab
                    break;
                case(8):
                    //inventory menu
                    break;
                case(9):
                    //crafting menu
                    if (overlay.getCurrentOverlay()==0) {
                        if (p.getIntelligence()>=30) {
                            if ((mouseX<500) && (mouseX>100) && (mouseY>250) && (mouseY<600)) {
                                this.image = ImageIO.read(new File("./graphics/menus/crafting/crafting_sword_hover.png"));
                            } else if ((mouseX<500) && (mouseX>100) && (mouseY>600) && (mouseY<950)) {
                                this.image = ImageIO.read(new File("./graphics/menus/crafting/crafting_stick_hover.png"));
                            } else if ((mouseX>500) && (mouseX<900) && (mouseY>250) && (mouseY<600)) {
                                this.image = ImageIO.read(new File("./graphics/menus/crafting/crafting_gun_hover.png"));
                            } else if ((mouseX>500) && (mouseX<900) && (mouseY>600) && (mouseY<950)) {
                                this.image = ImageIO.read(new File("./graphics/menus/crafting/crafting_rod_hover.png"));
                            } else {
                                this.image = ImageIO.read(new File("./graphics/menus/crafting/craftingMenu.png"));
                            }
                        } else {
                            this.image = ImageIO.read(new File("./graphics/menus/crafting/crafting_notUnlocked.png"));
                        }
                    }
                    break;
                case(10):
                    this.image = ImageIO.read(new File("./graphics/menus/start/gameOver.png"));
                    break;
            } 
        } catch(Exception e) {}
    }

    /**
     * currentGameState
     * gets the current gamestate
     * @return current game state in integer value
     */
    public int currentGameState() {
        return this.gameState;
    }

    /**
     * draw
     * draws the menu to the screen
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     */
    public void draw(Graphics g, Panel panel) {
        g.drawImage(this.image, 0, 0, panel);
    }
}
