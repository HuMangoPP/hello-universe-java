/**
 * [Panel.java]
 * extends JPanel
 * used to draw the game to Screen
 */

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Panel extends JPanel implements KeyListener, MouseInputListener{
    /** variables */
    private long t1 = 0;
    private long t2 = 0;
    static final int RATE = 100;
    private int x = 500, y = 500;
    private int tileX = 10, tileY = 10;
    private int mouseX, mouseY;
    private int xBound = 1000, yBound = 1000;

    private Map map;

    private GameEvents events;

    private GameMenu menu = new GameMenu();

    private Overlay overlay = new Overlay();

    private Player player = new Player(x, y, tileX, tileY);

    private InventoryMenu iMenu = new InventoryMenu(player);

    private SkillsMenu sMenu = new SkillsMenu();

    private CharacterMenu cMenu = new CharacterMenu(player);

    private PerksMenu pMenu = new PerksMenu(player);

    private ArrayList<Enemy>[][] enemyArray = new ArrayList[20][20];

    private ArrayList<InteractiveObject> list = new ArrayList<InteractiveObject>();

    private Random rand = new Random();

    private boolean tutorial = true;
    private boolean paused = false;

    /**
     * constructor
     */
    public Panel() {
        setPreferredSize(new Dimension(1000, 1000));
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        
        System.out.println("Loading Map...");
        map = new Map();
        events = new GameEvents(map);

        System.out.println("Loading Enemies...");
        for (int i=0; i<20; i++) {
            for (int j=0; j<20; j++) {
                enemyArray[i][j] = new ArrayList<Enemy>();
                for (int k=0; k<5; k++) {
                    enemyArray[i][j].add(new Enemy(rand.nextInt(xBound), rand.nextInt(yBound), j, i, rand.nextInt(100)+50));
                }
            }
        }

        System.out.println("Loading Items...");
        for (int i=0; i<800; i++) {
            events.loadFirstFood(list);
        }

        System.out.println("Loading Menus...");
        overlay.loadSprites(menu, mouseX, mouseY);
        menu.loadSprites(mouseX, mouseY, player, overlay);
    }

    /**
     * paintComponent
     * draws the game to the screen
     * @param g Graphics to draw the game screen
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (menu.currentGameState()==2) {
            this.drawGame(g);
        } else if (menu.currentGameState()==8) {
            iMenu.draw(g, this);
        } else if (menu.currentGameState()==5) {
            sMenu.draw(g, this, mouseX, mouseY, player);
        } else if (menu.currentGameState()==6) {
            pMenu.draw(g, this, mouseX, mouseY);
        } else if (menu.currentGameState()==4) {
            cMenu.draw(g, this, mouseX, mouseY);
        } else {
            menu.draw(g, this);
        }

        if (menu.currentGameState()>1) {
            overlay.drawOverlay(g, this);
        }

        repaint();
    }

    /**
     * drawGame
     * draws the in-game graphics
     * @param g Graphics to draw with
     */
    public void drawGame(Graphics g) {
        
        if ((this.tutorial) || (this.paused)) {
            t1 = System.nanoTime();
            t2 = System.nanoTime();
        }

        t2 = System.nanoTime();

        events.newGameTick(t1, t2, list, player, enemyArray);
        map.getTile(player.getTileX(), player.getTileY()).draw(g, this);
        events.runEvent(g, player, t1, t2);

        if (events.getRiggedGameTicks()==3) {
            map.transformLandTiles();
            if (events.getGameTicks()>20) {
                map.transformGrassTiles();
            }
        }

        if (events.changeEvents()) {
            this.paused = true;
            overlay.setOverlay(5);
            overlay.loadNewEventIndicator(events);
        }
        for (int i=0; i<list.size(); i++) {
            list.get(i).draw(g, player, this);
        }

        for (int i=0; i<enemyArray[player.getTileY()][player.getTileX()].size(); i++) {
             //enemies will be in arraylist
             enemyArray[player.getTileY()][player.getTileX()].get(i).run(g, player, t2, t1, this, map.getTile(player.getTileX(), player.getTileY()));
             if (enemyArray[player.getTileY()][player.getTileX()].get(i).isDead()) {
                enemyArray[player.getTileY()][player.getTileX()].remove(i);
             }
        }

        player.run(g, this, t1, t2);

        if (!this.paused) {
            if ((events.currentEvent()==2)) {
                player.takeDOTDmg();
            }
    
            if (player.isStarving()) {
                player.takeDOTDmg();
            }

            if (events.currentEvent()==0) {
                player.loseStaminaOverTime();
            }

            player.loseStaminaOverTime();
        }

        if (player.isDead()) {
            menu.setGameState(10);
            menu.loadSprites(mouseX, mouseY, player, overlay);
        }

        if ((t2-t1)/1000000>=100) {
            t1=t2;
        }
    }

    /**
     * keyTyped
     * does nothing
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e) {}

    /**
     * keyPressed
     * checks when key is pressed
     * @param e
     */
    public void keyPressed(KeyEvent e) { 
        int key = e.getKeyCode();
        if ((!this.paused) && (!this.tutorial)) {
            switch(key) {
                case (KeyEvent.VK_A):
                    player.moveLeft(map.getTile(player.getTileX(), player.getTileY()));
                    break;
                case(KeyEvent.VK_S):
                    player.moveDown(map.getTile(player.getTileX(), player.getTileY()));
                    break;
                case(KeyEvent.VK_D):
                    player.moveRight(map.getTile(player.getTileX(), player.getTileY()));
                    break;
                case(KeyEvent.VK_W):
                    player.moveUp(map.getTile(player.getTileX(), player.getTileY()));
                    break;
                case(KeyEvent.VK_F):
                    //interact with interactive
                    player.detectItemInRange(list);
                    break;
                case(KeyEvent.VK_J):
                    //attack enemy
                    //attacks first from list which is inRange
                    player.detectEnemyInRange(enemyArray[player.getTileY()][player.getTileX()], t1);
                    break;
                case(KeyEvent.VK_SPACE):
                    player.moveMap();
                    break;
                case(KeyEvent.VK_H):
                    player.reproduce();
                    break; 
            }
        }
    }

    /**
     * keyReleased
     * checks when key is released
     * @param e
     */
    public void keyReleased(KeyEvent e) { 
        //empty
        int key = e.getKeyCode();
        if ((menu.currentGameState()==2) && (!this.tutorial)) {
            switch(key) {
                case(KeyEvent.VK_TAB):
                    if (this.paused) {
                        this.paused = false;
                        overlay.setOverlay(0);
                    } else {
                        this.paused = true;
                        overlay.setOverlay(2);
                    }
                    overlay.loadSprites(menu, mouseX, mouseY);
                    break;
                case (KeyEvent.VK_A):
                    player.setNotMoving();
                    break;
                case(KeyEvent.VK_S):
                    player.setNotMoving();
                    break;
                case(KeyEvent.VK_D):
                    player.setNotMoving();
                    break;
                case(KeyEvent.VK_W):
                    player.setNotMoving();
                    break;
            }
        }
        if ((menu.currentGameState()>1) && (!this.tutorial)) {
            switch (key) {
                case(KeyEvent.VK_I):
                    if (this.paused) {
                        this.paused = false;
                        menu.setGameState(2);
                        overlay.setOverlay(0);
                    } else {
                        menu.setGameState(8);
                        this.paused = true;
                    }
                    break;
                case(KeyEvent.VK_C):
                    if (this.paused) {
                        this.paused = false;
                        menu.setGameState(2);
                        overlay.setOverlay(0);
                    } else {
                        this.paused = true;
                        menu.setGameState(9);
                    }
                    break;
                case(KeyEvent.VK_P):
                    if (this.paused) {
                        this.paused = false;
                        menu.setGameState(2);
                        sMenu.setBranch(0);
                        overlay.setOverlay(0);
                    } else {
                        menu.setGameState(4);
                        this.paused = true;
                    }
                    break;
                }
            menu.loadSprites(mouseX, mouseY, player, overlay);
        }
    }

    /**
     * mouseReleased
     * does nothing
     * @param e mouseEvent
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * mouseEntered
     * does nothing
     * @param e mouseEvent
     */
    public void mouseEntered(MouseEvent e) {}

    /**
     * mouseExited
     * does nothing
     * @param e mouseEvent
     */
    public void mouseExited(MouseEvent e) {}

    /**
     * mouseDragged
     * does nothing
     * @param e mouseEvent
     */
    public void mouseDragged(MouseEvent e) {}

    /**
     * mouseMoved
     * does nothing
     * @param e mouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        overlay.loadSprites(menu, mouseX, mouseY);
        menu.loadSprites(mouseX, mouseY, player, overlay);
    }

    /**
     * mouseClicked
     * does nothing
     * @param e mouseEvent
     */
	public void mouseClicked(MouseEvent e) {}

    /**
     * mousePressed
     * checks when mouse is pressed
     * @param e mouseEvent
     */
	public void mousePressed(MouseEvent e) {
        switch (menu.currentGameState()) {
            case(0):
                menu.setGameState(1);
                break;
            case(1):
                menu.setGameState(2);
                this.paused = false;
                break;
            case(2):
                if (this.tutorial) {
                    overlay.setOverlay(0);
                    this.tutorial = false;
                }

                if (overlay.getCurrentOverlay()==2) {
                    if ((mouseX>300) && (mouseX<500) && (mouseY>300) && (mouseY<500)) {
                        //char info
                        menu.setGameState(4);
                    } else if ((mouseX>300) && (mouseX<500) && (mouseY>500) && (mouseY<700)) {
                        //crafting
                        menu.setGameState(9);
                    } else if ((mouseX>500) && (mouseX<700) && (mouseY>300) && (mouseY<500)) {
                        //settings
                        menu.setGameState(3);
                    } else if ((mouseX>500) && (mouseX<700) && (mouseY>500) && (mouseY<700)) {
                        //inventory
                        menu.setGameState(8);
                    } else {
                        this.paused = false;
                    }

                    overlay.setOverlay(0);
                } else if (overlay.getCurrentOverlay() == 5) {
                    this.paused = false;
                    overlay.setOverlay(0);
                } 

                menu.loadSprites(mouseX, mouseY, player, overlay);

                break;
            case(3):
                if ((mouseX>=250) && (mouseX<=750) && (mouseY>=565) && (mouseY<=715)) {
                    menu.setGameState(2);
                    overlay.setOverlay(2);
                } else if ((mouseX>=175) && (mouseX<=825) && (mouseY>=770) && (mouseY<=930)) {
                    menu.setGameState(0);
                    overlay.setOverlay(0);
                }

                break;
            case(4):
                if ((mouseX>200) && (mouseX<380) && (mouseY>65) && (mouseY<135)) {
                    menu.setGameState(5);
                } else if ((mouseX>380) && (mouseX<560) && (mouseY>65) && (mouseY<135)) {
                    menu.setGameState(6);
                } else if ((mouseX>900) && (mouseX<950) && (mouseY>50) && (mouseY<100)) {
                    menu.setGameState(2);
                    overlay.setOverlay(2);
                }
                break;
            case(5):
                int gridX = -1, gridY = -1;
                switch(sMenu.currentBranch()) {
                    case(0):
                        if ((mouseX>20) && (mouseX<200) && (mouseY>65) && (mouseY<135)) {
                            menu.setGameState(4);
                        } else if ((mouseX>380) && (mouseX<560) && (mouseY>65) && (mouseY<135)) {
                            menu.setGameState(6);
                        } else if ((mouseX>900) && (mouseX<950) && (mouseY>50) && (mouseY<100)) {
                            menu.setGameState(2);
                            overlay.setOverlay(2);
                        }
                    
                        int dist = (int) Math.floor(Math.sqrt(Math.pow(500-mouseX, 2)+Math.pow(500-mouseY, 2)));
                        if ((dist<300) && (dist>75)) {
                            if ((mouseX>500) && (mouseY<500)) {
                                sMenu.setBranch(2);
                            } else if ((mouseX>500) && (mouseY)>500) {
                                sMenu.setBranch(3);
                            } else if ((mouseX<500) && (mouseY>500)) {
                                sMenu.setBranch(4);
                            } else if ((mouseX<500) && (mouseY<500)) {
                                sMenu.setBranch(1);
                            }
                        } else if (dist<75) {
                            sMenu.setBranch(5);
                        }
                        break;
                    case(1):
                        if (!sMenu.overlayOn()) {
                            if (Math.pow(1000-mouseX, 2) + Math.pow(1000-mouseY, 2) < Math.pow(300, 2)) {
                                sMenu.setBranch(0);
                            } 

                            if (Math.pow(823-mouseX, 2) + Math.pow(490-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(0);
                                sMenu.setThisOverlay();
                            } else if (Math.pow(372-mouseX, 2) + Math.pow(721-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(1);
                                sMenu.setThisOverlay();
                            }
                        } else {
                            if (Math.pow(500-mouseX, 2) + Math.pow(800-mouseY, 2) < Math.pow(75, 2)) {
                                if (sMenu.getSelectedStat()==0) {
                                    player.addStats(2, 0, 0, 0, 0, 0, 0, 0);
                                } else {
                                    player.addStats(0, 0, 0, 0, 2, 0, 0, 0);
                                }
                            } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                sMenu.setThisOverlay();
                            }
                        }
                        break;
                    case(2):
                        if (!sMenu.overlayOn()) {
                            if (Math.pow(mouseX, 2) + Math.pow(1000-mouseY, 2) < Math.pow(300, 2)) {
                                sMenu.setBranch(0);
                            }

                            if (Math.pow(617-mouseX, 2) + Math.pow(841-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(0);
                                sMenu.setThisOverlay();
                            }
                            else if (Math.pow(360-mouseX, 2) + Math.pow(380-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(1);
                                sMenu.setThisOverlay();
                            }
                        } else {
                            if (Math.pow(500-mouseX, 2) + Math.pow(800-mouseY, 2) < Math.pow(75, 2)) {
                                if (sMenu.getSelectedStat()==0) {
                                    player.addStats(0, 0, 2, 0, 0, 0, 0, 0);
                                } else {
                                    player.addStats(0, 0, 0, 0, 0, 0, 2, 0);
                                }
                            } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                sMenu.setThisOverlay();
                            }
                        }
                        break;
                    case(3):
                        if (!sMenu.overlayOn()) {
                            if (Math.pow(mouseX, 2) + Math.pow(mouseY, 2) < Math.pow(300, 2)) {
                                sMenu.setBranch(0);
                            } 

                            if (Math.pow(441-mouseX, 2) + Math.pow(364-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(0);
                                sMenu.setThisOverlay();
                            } else if (Math.pow(525-mouseX, 2) + Math.pow(872-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(1);
                                sMenu.setThisOverlay();
                            }
                        } else {
                            if (Math.pow(500-mouseX, 2) + Math.pow(800-mouseY, 2) < Math.pow(75, 2)) {
                                if (sMenu.getSelectedStat()==0) {
                                    player.addStats(0, 0, 0, 2, 0, 0, 0, 0);
                                } else {
                                    player.addStats(0, 0, 0, 0, 0, 2, 0, 0);
                                }
                            } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                sMenu.setThisOverlay();
                            }
                        }
                        break;
                    case(4):
                        if (!sMenu.overlayOn()) {
                            if (Math.pow(1000-mouseX, 2) + Math.pow(mouseY, 2) < Math.pow(300, 2)) {
                                sMenu.setBranch(0);
                            } 

                            if (Math.pow(784-mouseX, 2) + Math.pow(500-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(0);
                                sMenu.setThisOverlay();
                            } else if (Math.pow(284-mouseX, 2) + Math.pow(673-mouseY, 2) < Math.pow(55, 2)) {
                                sMenu.statSelection(1);
                                sMenu.setThisOverlay();
                            }
                        } else {
                            if (Math.pow(500-mouseX, 2) + Math.pow(800-mouseY, 2) < Math.pow(75, 2)) {
                                if (sMenu.getSelectedStat()==0) {
                                    player.addStats(0, 2, 0, 0, 0, 0, 0, 0);
                                } else {
                                    player.addStats(0, 0, 0, 0, 0, 0, 0, 2);
                                }
                            } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                sMenu.setThisOverlay();
                            }
                        }
                        break;
                    case(5):
                        if (!sMenu.overlayOn()) {
                            if ((mouseX>940) && (mouseX<980) && (mouseY>30) && (mouseY<80)) {
                                sMenu.setBranch(0);
                            }

                            for (int i=0; i<4; i++) {
                                for (int j=0; j<3; j++) {
                                    if ((mouseX>61+250*i) && (mouseX<190+250*i) && (mouseY>311+250*j) && (mouseY<439+250*j)) {
                                        gridX = i;
                                        gridY = j;
                                        j=2;
                                        i=3;
                                    }
                                }
                            }

                            if ((gridX!=-1) && (gridY!=-1)) {
                                if (!player.isAscended()) {
                                    sMenu.setGrids(gridX, gridY);
                                    sMenu.setThisOverlay();
                                }
                            }
                        } else {
                            if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                if ((mouseX>375) && (mouseX<875) && (mouseY>850) && (mouseY<950)) {
                                    player.ascend(sMenu.getGridX(), sMenu.getGridY());
                                    player.addPerks();
                                    player.procPerks();
                                    player.loadSprites();
                                }
                                sMenu.setThisOverlay();
                                gridX = -1;
                                gridY = -1;
                            }
                        }
                        break;
                    }
                break;
            case(6):
                if ((mouseX>900) && (mouseX<950) && (mouseY>50) && (mouseY<100)) {
                    menu.setGameState(2);
                    overlay.setOverlay(2);
                }

                if ((mouseX>20) && (mouseX<200) && (mouseY>65) && (mouseY<135)) {
                    menu.setGameState(4);
                } else if ((mouseX>200) && (mouseX<380) && (mouseY>65) && (mouseY<135)) {
                    menu.setGameState(5);
                }
                break;
            case(8):
                if (overlay.getCurrentOverlay()==0) {
                    for (int i=0; i<player.displayInventory().size(); i++) {
                        if ((mouseX>50+64*3*(i%5)) && (mouseX<50+64*3*(i%5)+128) && (mouseY>186+64*3*(i/5)) && (mouseY<186+64*3*(i/5)+128)) {
                            overlay.detailOverlay(player.displayInventory().get(i));
                            overlay.setOverlay(3);
                        }
                    }
                } else {
                    switch(overlay.getCurrentObjType()) {
                        case(0):
                            if (Math.pow(800-mouseY, 2) + Math.pow(350-mouseX, 2) < Math.pow(50, 2)) {
                                //delete food
                                player.deleteItem(overlay.getCurrentObj());
                                overlay.setOverlay(0);
                            } else if (Math.pow(800-mouseY, 2) + Math.pow(650-mouseX, 2) < Math.pow(50, 2)) {
                                //eat food
                                player.useFood((Food) overlay.getCurrentObj());
                                if (((Food) overlay.getCurrentObj()).getAmt() == 0) {
                                    overlay.setOverlay(0);
                                }
                            } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                overlay.setOverlay(0);
                            }
                            break;
                        case(1):
                            if (Math.pow(800-mouseY, 2) + Math.pow(350-mouseX, 2) < Math.pow(50, 2)) {
                                //delete item
                                player.deleteItem(overlay.getCurrentObj());
                                overlay.setOverlay(0);
                            } else if (Math.pow(800-mouseY, 2) + Math.pow(650-mouseX, 2) < Math.pow(50, 2)) {
                                //equip item
                                player.equipItem((Item) overlay.getCurrentObj());
                            } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                overlay.setOverlay(0);
                            }
                            break;
                        case(2):
                            if (Math.pow(500-mouseX, 2) + Math.pow(800-mouseY, 2) < Math.pow(50, 2)) {
                                //delete itemdrop
                                player.deleteItem(overlay.getCurrentObj());
                                overlay.setOverlay(0);
                            } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                                overlay.setOverlay(0);
                            }
                            break;
                    }
                }
                break;
            case(9):
                if (overlay.getCurrentOverlay()==0) {
                    if ((mouseX<500) && (mouseX>100) && (mouseY>250) && (mouseY<600)) {
                        overlay.setOverlay(4);
                        overlay.craftingOverlay(0, player);
                    } else if ((mouseX<500) && (mouseX>100) && (mouseY>600) && (mouseY<950)) {
                        overlay.setOverlay(4);
                        overlay.craftingOverlay(1, player);
                    } else if ((mouseX>500) && (mouseX<900) && (mouseY>250) && (mouseY<600)) {
                        overlay.setOverlay(4);
                        overlay.craftingOverlay(2, player);
                    } else if ((mouseX>500) && (mouseX<900) && (mouseY>600) && (mouseY<950)) {
                        overlay.setOverlay(4);
                        overlay.craftingOverlay(3, player);
                    } 
                } else {
                    if (Math.pow(500-mouseX, 2) + Math.pow(800-mouseY, 2) < Math.pow(50, 2)) {
                        //craft item
                        player.craftItem(overlay.getCurrentCrafting());
                    } else if ((mouseX<200) || (mouseX>800) || (mouseY<200) || (mouseY>800)) {
                        overlay.setOverlay(0);
                    }
                }
                break;
            case(10):
                menu.setGameState(0);
                break;
        }

        overlay.loadSprites(menu, mouseX, mouseY);
	}
}