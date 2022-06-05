

/**
 * [Screen.java]
 * this is where the GraphicsFrame 
 * and GraphicsPanel classes
 * are located
 * they will handle all in game functionality
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame; 


public class Screen extends JFrame{
    /** variables */
    private ImageIcon img = new ImageIcon("./graphics/gameIcon.png");

    /**constructor */
    Screen() {
        setTitle("Hello Universe");
        setSize(1000, 1000);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new Panel());
        pack();
        setVisible(true);
        this.setIconImage(img.getImage());
    }

}

