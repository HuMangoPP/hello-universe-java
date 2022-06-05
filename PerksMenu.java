/**
 * [PerksMenu.java]
 * introduces the perks menu which the player will be using
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.util.ArrayList;

public class PerksMenu {
    /** variables */
    private Player p;
    private BufferedImage menu;
    private ArrayList<BufferedImage> perkIcons = new ArrayList<BufferedImage>();

    /**
     * constructor
     * @param p Player class to draw perks
     */
    PerksMenu(Player p) {
        this.p = p;
    }

    /**
     * loadSprites
     * loads menu sprites
     * @param mouseX x pos for hober
     * @param mouseY y pos for hover
     */
    public void loadSprites(int mouseX, int mouseY) {
        try{
            if ((mouseX>20) && (mouseX<200) && (mouseY>65) && (mouseY<135)) {
                this.menu = ImageIO.read(new File("./graphics/menus/character_menu/perks/perks_info_hover.png"));
            } else if ((mouseX>200) && (mouseX<380) && (mouseY>65) && (mouseY<135)) {
                this.menu = ImageIO.read(new File("./graphics/menus/character_menu/perks/perks_skills_hover.png"));
            } else {
                this.menu = ImageIO.read(new File("./graphics/menus/character_menu/perks/perks.png"));
            }
            ArrayList<Integer> perks = p.displayPerks();
            for (int i=0; i<perks.size(); i++) {
                switch(perks.get(i)) {
                    case(0):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/accuracy.png")));
                        break;
                    case(1):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/agriculture.png")));
                        break;
                    case(3):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/block.png")));
                        break;
                    case(4):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/counterattack.png")));
                        break;
                    case(5):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/leech.png")));
                        break;
                    case(6):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/ranged.png")));
                        break;
                    case(7):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/scavenger.png")));
                        break;
                    case(8):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/swimming.png")));
                        break;
                    case(9):
                        this.perkIcons.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/winged.png")));
                        break;
                }
            }
        } catch(Exception e) {}
    }

    /**
     * draw
     * drwas the menu onto the screen
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     * @param mouseX x pos for hover
     * @param mouseY y pos for hover
     */
    public void draw(Graphics g, Panel panel, int mouseX, int mouseY) {
        this.loadSprites(mouseX, mouseY);
        g.drawImage(this.menu, 0, 0, panel);
        for (int i=0; i<this.perkIcons.size(); i++) {
            g.drawImage(this.perkIcons.get(i), 100+500*i, 300, panel);
        }
    }
}
