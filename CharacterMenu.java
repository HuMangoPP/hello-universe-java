/**
 * [CharacterMenu.java]
 * works the graphics and functionality for the character menu the player accesses in game
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Font;

public class CharacterMenu {
    /**
     * variables
     */
    private Player p;
    private BufferedImage menu;
    private BufferedImage character, weapon;
    private ArrayList<String> stats = new ArrayList<String>();

    /**
     * constructor
     * @param p Player 
     */
    CharacterMenu(Player p) {
        this.p = p;
    }

    /**
     * loadSprites
     * loads sprites for menu
     * @param mouseX mouseX pos for hover
     * @param mouseY mouseY pos for hover
     */
    public void loadSprites(int mouseX, int mouseY) {
        try {
            if ((mouseX>200) && (mouseX<380) && (mouseY>65) && (mouseY<135)) {
                this.menu = ImageIO.read(new File("./graphics/menus/character_menu/info/info_skills_hover.png"));
            } else if ((mouseX>380) && (mouseX<560) && (mouseY>65) && (mouseY<135)) {
                this.menu = ImageIO.read(new File("./graphics/menus/character_menu/info/info_perks_hover.png"));
            } else {
                this.menu = ImageIO.read(new File("./graphics/menus/character_menu/info/info.png"));
            }

            switch(p.getSpecies()) {
                case(0):
                    this.character = ImageIO.read(new File("./graphics/character/cell_icon.png"));
                    break;
                case(1):
                    //llama
                    this.character = ImageIO.read(new File("./graphics/character/llama_icon.png"));
                    break;
                case(2):
                    //human
                    this.character = ImageIO.read(new File("./graphics/character/human_icon.png"));
                    break;
                case(3):
                    //raccoon
                    this.character = ImageIO.read(new File("./graphics/character/raccoon_icon.png"));
                    break;
                case(4):
                    //eagle
                    this.character = ImageIO.read(new File("./graphics/character/eagle_icon.png"));
                    break;
                case(5):
                    //duck
                    this.character = ImageIO.read(new File("./graphics/character/duck_icon.png"));
                    break;
                case(6):
                    //mosquito
                    this.character = ImageIO.read(new File("./graphics/character/mosquito_icon.png"));
                    break;
                case(7):
                    //turtle
                    this.character = ImageIO.read(new File("./graphics/character/turtle_icon.png"));
                    break;
                case(8):
                    //python
                    this.character = ImageIO.read(new File("./graphics/character/python_icon.png"));
                    break;
                case(9):
                    //frog
                    this.character = ImageIO.read(new File("./graphics/character/frog_icon.png"));
                    break;
                case(10):
                    //shark
                    this.character = ImageIO.read(new File("./graphics/character/shark_icon.png"));
                    break;
                case(11):
                    //cod
                    this.character = ImageIO.read(new File("./graphics/character/cod_icon.png"));
                    break;
                case(12):
                    //jellyfish
                    this.character = ImageIO.read(new File("./graphics/character/jellyfish_icon.png"));
                    break;
                    
            }

            if (p.hasWeapon()) {
                switch(p.currentWeaponType()) {
                    case(0):
                        this.weapon = ImageIO.read(new File(",/graphics/items/greatsword"));
                        break;
                    case(1):
                        this.weapon = ImageIO.read(new File(",/graphics/items/stick"));
                        break;
                    case(2):
                        this.weapon = ImageIO.read(new File(",/graphics/items/gun"));
                        break;
                    case(3):
                        this.weapon = ImageIO.read(new File(",/graphics/items/fishingrod"));
                        break;
                }
            } else {
                this.weapon = ImageIO.read(new File("./graphics/character/blank.png"));
            }

            this.loadStats();
        } catch (Exception e) {}
    }

    /**
     * loadStats
     * gets current player stats
     */
    public void loadStats() {
        this.p.getStats(this.stats);
        this.stats.add(p.getName());
    }

    /**
     * draw
     * draws the menu to screen
     * @param g Graphics to draw with
     * @param panel Panel to draw onto
     * @param mouseX mouseX pos for hover
     * @param mouseY mouseY pos for hover
     */
    public void draw(Graphics g, Panel panel, int mouseX, int mouseY) {
        Font font = new Font("Comic Sans MS", Font.BOLD, 30);
        g.setFont(font);
        this.loadSprites(mouseX, mouseY);
        g.drawImage(this.menu, 0, 0, panel);
        g.drawImage(this.character, 180-this.character.getWidth()/2, 305-this.character.getHeight()/2, panel);
        g.drawImage(this.weapon, 500-this.weapon.getWidth()/2, 305-this.weapon.getHeight()/2, panel);
        for (int i=0; i<this.stats.size()-1; i++) {
            g.drawString(this.stats.get(i), 280, 580+i*62);
        }
    }
}
