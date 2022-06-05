/**
 * [SkillsMenu.java]
 * access the skills menu which the player will be using
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.util.ArrayList;

public class SkillsMenu {
    /**variables */
    private int branch = 0;
    private BufferedImage image;
    private int gridX = -1, gridY = -1;
    private BufferedImage ascensionIcon;
    private BufferedImage skillsIcon;
    private boolean overlay;
    private int selectedStat;

    /**constructor */
    SkillsMenu() {}

    /**
     * loadSprites
     * loads menu sprites
     * @param mouseX x pos for hover
     * @param mouseY y pos for hover
     * @param player player for player stats
     */
    public void loadSprites(int mouseX, int mouseY, Player player) {
        try {
            switch(this.branch) {
                case(0):
                    if ((mouseX>20) && (mouseX<200) && (mouseY>65) && (mouseY<135)) {
                        this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills_info_hover.png"));
                    } else if ((mouseX>380) && (mouseX<560) && (mouseY>65) && (mouseY<135)) {
                        this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills_perks_hover.png"));
                    } else {
                        this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills.png"));
                    }
            
                    int dist = (int) Math.floor(Math.sqrt(Math.pow(500-mouseX, 2)+Math.pow(500-mouseY, 2)));
                    if (dist<300) {
                        if (dist<75) {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills_ascend_hover.png"));
                        } else {
                            if ((mouseX>500) && (mouseY<500)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills_boots.png"));
                            } else if ((mouseX>500) && (mouseY)>500) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills_brain.png"));
                            } else if ((mouseX<500) && (mouseY>500)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills_shield.png"));
                            } else if ((mouseX<599) && (mouseY<500)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/skills_sword.png"));
                            }
                        }
                    }
                    break;
                case(1):
                    if (!this.overlay) {
                        if (Math.pow(1000-mouseX, 2) + Math.pow(1000-mouseY, 2) < Math.pow(300, 2)) {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_attack_back.png"));
                        } else {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_attack.png"));
                        }
                    } else {
                        this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/upgradeSkill.png"));
                    }
                    break;
                case(2):
                    if (!this.overlay) {
                        if (Math.pow(mouseX, 2) + Math.pow(1000-mouseY, 2) < Math.pow(300, 2)) {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_mobility_back.png"));
                        } else {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_mobility.png"));
                        }
                    } else {
                        this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/upgradeSkill.png"));
                    }
                    break;
                case(3):
                    if (!this.overlay) {
                        if (Math.pow(mouseX, 2) + Math.pow(mouseY, 2) < Math.pow(300, 2)) {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_intelligence_back.png"));
                        } else {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_intelligence.png"));
                        }
                    } else {
                        this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/upgradeSkill.png"));
                    }
                    break;
                case(4):
                    if (!this.overlay) {
                        if (Math.pow(1000-mouseX, 2) + Math.pow(mouseY, 2) < Math.pow(300, 2)) {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_defense_back.png"));
                        } else {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/tree_defense.png"));
                        }
                    } else {
                        this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/upgradeSkill.png"));
                    }
                    break;
                case(5):
                    if (!this.overlay) {
                        for (int i=0; i<4; i++) {
                            for (int j=0; j<3; j++) {
                                if ((mouseX>61+250*i) && (mouseX<190+250*i) && (mouseY>311+250*j) && (mouseY<439+250*j)) {
                                    this.gridX = i;
                                    this.gridY = j;
                                    j=2;
                                    i=3;
                                } else {
                                    this.gridX = -1;
                                    this.gridY = -1;
                                }
                            }

                        }

                        if (player.isAscended()) {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_ascended.png"));
                        } else {
                            if ((this.gridX==0) && (this.gridY==0)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_llama.png"));
                            } else if ((this.gridX==0) && (this.gridY==1)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_human.png"));
                            } else if ((this.gridX==0) && (this.gridY==2)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_raccoon.png"));
                            } else if ((this.gridX==1) && (this.gridY==0)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_eagle.png"));
                            } else if ((this.gridX==1) && (this.gridY==1)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_duck.png"));
                            } else if ((this.gridX==1) && (this.gridY==2)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_mosquito.png"));
                            } else if ((this.gridX==2) && (this.gridY==0)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_turtle.png"));
                            } else if ((this.gridX==2) && (this.gridY==1)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_snake.png"));
                            } else if ((this.gridX==2) && (this.gridY==2)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_frog.png"));
                            } else if ((this.gridX==3) && (this.gridY==0)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_shark.png"));
                            } else if ((this.gridX==3) && (this.gridY==1)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_cod.png"));
                            } else if ((this.gridX==3) && (this.gridY==2)) {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_jellyfish.png"));
                            } else {
                                this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension.png"));
                            }
                        }
                    } else  {
                        this.loadSpeciesIcon();
                        if ((mouseX>375) && (mouseX<875) && (mouseY>850) && (mouseY<950)) {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_detail_hover.png"));
                        } else {
                            this.image = ImageIO.read(new File("./graphics/menus/character_menu/skills/ascension_detail.png"));
                        }
                    
                    }
                    break;
            }
        } catch(Exception e) {}
    }

    /**
     * draw
     * draws menus onto screen
     * @param g Graphics to draw with
     * @param panel panel to draw onto
     * @param mouseX x pos for hover
     * @param mouseY y pos for hover
     * @param player player for player stats
     */
    public void draw(Graphics g, Panel panel, int mouseX, int mouseY, Player player) {
        this.loadSprites(mouseX, mouseY, player);
        g.drawImage(this.image, 0, 0, panel);
        if (this.overlay) {
            if (this.branch==5) {
                this.drawSpeciesOverlay(g, this.loadSpeciesName(), this.loadStatsAndRequirements(), panel);
            } else {
                this.drawSkillsOverlay(g, panel, player);
            }
        }
    }

    /**
     * setBranch
     * set branch of skilltree
     * @param branch new branch
     */
    public void setBranch(int branch) {
        this.branch = branch;
    }

    /**
     * currentBranch
     * gets the current branch pf skill tree
     * @return
     */
    public int currentBranch() {
        return this.branch;
    }

    /**
     * loadSpeciesName
     * load name of species
     * @return name of species
     */
    public String loadSpeciesName() {
        String text = "";
        if ((gridX==0) && (gridY==0)) {
            text = "Llama";
        } else if ((gridX==0) && (gridY==1)) {
            text = "Human"; 
        } else if ((gridX==0) && (gridY==2)) {
            text = "Raccoon"; 
        } else if ((gridX==1) && (gridY==0)) {
            text = "Eagle"; 
        } else if ((gridX==1) && (gridY==1)) {
            text = "Duck"; 
        } else if ((gridX==1) && (gridY==2)) {
            text = "Mosquito"; 
        } else if ((gridX==2) && (gridY==0)) {
            text = "Turtle"; 
        } else if ((gridX==2) && (gridY==1)) {
            text = "Python"; 
        } else if ((gridX==2) && (gridY==2)) {
            text = "Frog"; 
        } else if ((gridX==3) && (gridY==0)) {
            text = "Shark"; 
        } else if ((gridX==3) && (gridY==1)) {
            text = "Cod"; 
        } else if ((gridX==3) && (gridY==2)) {
            text = "Jellyfish"; 
        }

        return text;
    }

    /**
     * loadStatsAndRequirements
     * loads the stat and requirements for species
     * @return ArrayKlist of stats and requirements for species
     */
    public ArrayList<String> loadStatsAndRequirements() {
        ArrayList<String> text = new ArrayList<String>();
        text.add("Bonus Stats");
        if ((gridX==0) && (gridY==0)) {
            text.add("Range +3 - Defense +3");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 20 - Def: 20 - Spd: 15");
        } else if ((gridX==0) && (gridY==1)) {
            text.add("Intelligence +2 - Seduction +1");
            text.add("Requirements");
            text.add("Int: 30 - Atk: 20 - Def: 15 - Spd: 10");
        } else if ((gridX==0) && (gridY==2)) {
            text.add("Speed +3 - Attack +3");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 20 - Def: 10 - Spd: 20");
        } else if ((gridX==1) && (gridY==0)) {
            text.add("Evasion +2 - Attack +3");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 20 - Def: 20 - Spd: 20");
        } else if ((gridX==1) && (gridY==1)) {
            text.add("Speed +3 - Evasion +1");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 20 - Def: 15 - Spd: 15");
        } else if ((gridX==1) && (gridY==2)) {
            text.add("Attack +5 - Speed +2");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 20 - Def: 10 - Spd: 20");
        } else if ((gridX==2) && (gridY==0)) {
            text.add("Defense +5 - Intelligence +1");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 20 - Def: 30 - Spd: 10");
        } else if ((gridX==2) && (gridY==1)) {
            text.add("Speed +3 - Attack +3");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 15 - Def: 15 - Spd: 20");
        } else if ((gridX==2) && (gridY==2)) {
            text.add("Evasion +2 - Speed +2");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 10 - Def: 15 - Spd: 15");
        } else if ((gridX==3) && (gridY==0)) {
            text.add("Attack +5 - Speed +2");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 25 - Def: 20 - Spd: 25");
        } else if ((gridX==3) && (gridY==1)) {
            text.add("Speed + 3 - Evasion +1");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 10 - Def: 20 - Spd: 20");
        } else if ((gridX==3) && (gridY==2)) {
            text.add("Attack +5 - Attack +3");
            text.add("Requirements");
            text.add("Int: 10 - Atk: 20 - Def: 15 - Spd: 15");
        }
        return text;

    }

    /**
     * loadSpeciesIcon
     * loads the icon for the species
     */
    public void loadSpeciesIcon() {
        try {
            if ((gridX==0) && (gridY==0)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/llama_icon.png"));
            } else if ((gridX==0) && (gridY==1)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/human_icon.png"));
            } else if ((gridX==0) && (gridY==2)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/raccoon_icon.png"));
            } else if ((gridX==1) && (gridY==0)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/eagle_icon.png"));
            } else if ((gridX==1) && (gridY==1)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/duck_icon.png"));
            } else if ((gridX==1) && (gridY==2)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/mosquito_icon.png"));
            } else if ((gridX==2) && (gridY==0)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/turtle_icon.png"));
            } else if ((gridX==2) && (gridY==1)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/python_icon.png"));
            } else if ((gridX==2) && (gridY==2)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/frog_icon.png"));
            } else if ((gridX==3) && (gridY==0)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/shark_icon.png"));
            } else if ((gridX==3) && (gridY==1)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/cod_icon.png"));
            } else if ((gridX==3) && (gridY==2)) {
                this.ascensionIcon = ImageIO.read(new File("./graphics/character/jellyfish_icon.png"));
            }
        } catch (Exception e) {}
    }

    /**
     * loadSpeciesPerks
     * loads the perks for the species
     * @return arraylist of perks icons
     */
    public ArrayList<BufferedImage> loadSpeciesPerks() {
        ArrayList<BufferedImage> perks = new ArrayList<BufferedImage>();
        try {
            if ((gridX==0) && (gridY==0)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/ranged_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/accuracy_icon.png")));
            } else if ((gridX==0) && (gridY==1)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/agriculture_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/counterattack_icon.png")));
            } else if ((gridX==0) && (gridY==2)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/scavenger_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/leech_icon.png")));
            } else if ((gridX==1) && (gridY==0)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/winged_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/accuracy_icon.png")));
            } else if ((gridX==1) && (gridY==1)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/winged_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/counterattack_icon.png")));
            } else if ((gridX==1) && (gridY==2)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/winged_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/leech_icon.png")));
            } else if ((gridX==2) && (gridY==0)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/block_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/counterattack_icon.png")));
            } else if ((gridX==2) && (gridY==1)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/leech_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/counterattack_icon.png")));
            } else if ((gridX==2) && (gridY==2)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/counterattack_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/ranged_icon.png")));
            } else if ((gridX==3) && (gridY==0)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/accuracy_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/swimming_icon.png")));
            } else if ((gridX==3) && (gridY==1)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/scavenger_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/swimming_icon.png")));
            } else if ((gridX==3) && (gridY==2)) {
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/counterattack_icon.png")));
                perks.add(ImageIO.read(new File("./graphics/menus/character_menu/perks/swimming_icon.png")));
            }
        } catch (Exception e) {}

        return perks;
    }

    /**
     * drawSpeciesOverlay
     * draws the overlay for the skillsmneu
     * @param g Graphics to draw with
     * @param name name to draw
     * @param stats stats to draw
     * @param panel panel to draw onto
     */
    public void drawSpeciesOverlay(Graphics g, String name, ArrayList<String> stats, Panel panel) {
        Font font = new Font("Comic Sans MS", Font.BOLD, 30);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(name, 500-metrics.stringWidth(name)/2, 415);
        for (int i=0; i<stats.size(); i++) {
            g.drawString(stats.get(i), 500-metrics.stringWidth(stats.get(i))/2, 450+metrics.getHeight()*i);
        }
        g.drawImage(this.ascensionIcon, 436, 236, panel);
        ArrayList<BufferedImage> perks = this.loadSpeciesPerks();
        for (int i=0; i<perks.size(); i++) {
            g.drawImage(perks.get(i), 300+250*i, 625, panel);
        }
    }

    /**
     * setThisOver
     * set whether or not overlay is on
     */
    public void setThisOverlay() {
        if (this.overlay) {
            this.overlay = false;
        } else {
            this.overlay = true;
        }
    }

    /**
     * overlayOn
     * check if overlay is on
     * @return true if overlay is on, false if not
     */
    public boolean overlayOn() {
        return this.overlay;
    }

    /**
     * setGrids
     * sets the selected grid
     * @param gridX x pos
     * @param gridY y pos
     */
    public void setGrids(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }

    /**
     * getDriX
     * gets the x pos grid
     * @return x pos grid
     */
    public int getGridX() {
        return this.gridX;
    }

    /**
     * getGridY
     * gets the y pso grid
     * @return y pos grid
     */
    public int getGridY() {
        return this.gridY;
    }

    /**
     * loadSkillInfo
     * loads the skills inof
     * @return arraylist of skills info
     */
    public ArrayList<String> loadSkillInfo() {
        ArrayList<String> textInfo = new ArrayList<String>();
        try {
            switch(this.branch) {
                case(1):
                    switch(this.selectedStat) {
                        case(0):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/attack_stat.png"));
                            textInfo.add("Attack Up");
                            textInfo.add("Increases damage dealt");
                            return textInfo;
                        case(1):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/range_stat.png"));
                            textInfo.add("Range Up");
                            textInfo.add("Increases character reach");
                            return textInfo;
                    }
                    break;
                case(2):
                    switch(this.selectedStat) {
                        case(0):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/speed_stat.png"));
                            textInfo.add("Speed Up");
                            textInfo.add("Increases movement speed");
                            return textInfo;
                        case(1):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/evasion_stat.png"));
                            textInfo.add("Evasion Up");
                            textInfo.add("Increases chance to dodge attack");
                            return textInfo;
                    }
                    break;
                case(3):
                    switch(this.selectedStat) {
                        case(0):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/intelligence_stat.png"));
                            textInfo.add("Intelligence Up");
                            textInfo.add("Craft Items");
                            return textInfo;
                        case(1):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/seduction_stat.png"));
                            textInfo.add("Seduction Up");
                            textInfo.add("Increases reproductive growth");
                            return textInfo;
                    }
                    break;
                case(4):
                    switch(this.selectedStat) {
                        case(0):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/defense_stat.png"));
                            textInfo.add("Defense Up");
                            textInfo.add("Decreases damage take");
                            return textInfo;
                        case(1):
                            this.skillsIcon = ImageIO.read(new File("./graphics/menus/character_menu/skills/stamina_stat.png"));
                            textInfo.add("Stamina Up");
                            textInfo.add("Increases total huhnger and thirst");
                            return textInfo;
                    }
                    break;
            }
        } catch(Exception e) {}

        return textInfo;
    }

    /**
     * drawSkillsOverlay
     * draws the overlay for skills menu
     * @param g Graphics to draw witj
     * @param panel panel to draw onto
     * @param p Player for player stats
     */
    public void drawSkillsOverlay(Graphics g, Panel panel, Player p) {
        Font font = new Font("Comic Sans MS", Font.BOLD, 30);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        ArrayList<String> text = this.loadSkillInfo();
        for (int i=0; i<text.size(); i++) {
            g.drawString(text.get(i), 500-metrics.stringWidth(text.get(i))/2, 400+metrics.getHeight()+100*i);
        }
        g.drawImage(this.skillsIcon, 436, 236, panel);
        g.setColor(Color.RED);
        String skillPts = "Skill Pts remaining: "+p.getSkillPts();
        g.drawString(skillPts, 500-metrics.stringWidth(skillPts)/2, 925);
    }

    /**
     * statSelected
     * set the current stat selected for skill tree
     * @param stat
     */
    public void statSelection(int stat) {
        this.selectedStat = stat;
    }

    /**
     * getSelectedStat
     * gets the current selected stat
     * @return selected stat in integer value
     */
    public int getSelectedStat() {
        return this.selectedStat;
    }
}