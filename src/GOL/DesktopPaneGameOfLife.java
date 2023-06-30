package GOL;

import javax.swing.*;


/**
 * @author  Melis Yade Layik, Sinan Ince, Konuralp Celikyay, Benno Dinsch, Lau Kailany
 * @version 1, 21.06.2023
 **/


public class DesktopPaneGameOfLife extends JFrame {

    private JDesktopPane pane;

    public DesktopPaneGameOfLife() { //hauptfenster spiel
        pane = new JDesktopPane();
        pane.setDesktopManager(new DefaultDesktopManager());
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocation(450, 250);
        setTitle("Game Of Life :O awwmagawd");
        setVisible(true);
    }


    public void addChild(JInternalFrame child, int x, int y) { //kinder fenster hinzufügen
        child.setLocation(x, y);
        child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        pane.add(child);
    }


}
