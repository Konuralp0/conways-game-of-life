package GOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author  Melis Yade Layik, Sinan Ince, Konuralp Celikyay, Benno Dinsch, Lau Kailany
 * @version 1, 21.06.2023
 **/

public class StartMenuGameOfLife extends JFrame implements ActionListener {

    // Buttons erstellen
    private JButton starten = new JButton("Starten");
    private JButton verlassen = new JButton("Verlassen");
    private JButton autoren = new JButton("Autoren");

    public StartMenuGameOfLife() {
        //größe Fenster und ort
        setBounds(650, 350, 390, 430);
        //buttons farbe
        starten.setBackground(FarbenT.WEISS.getColor());
        verlassen.setBackground(FarbenT.WEISS.getColor());
        autoren.setBackground(FarbenT.WEISS.getColor());
        //Fenster in 2 panels teilen
        setLayout(new GridLayout(2, 1));
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JLabel title = new JLabel(new ImageIcon(getClass().getResource("asset/GOL.gif")));
        titlePanel.add(title);
        setTitle("Game of Life V2.1");
        add(titlePanel);
        //buttoms setzen und funktion geben
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.add(starten);
        buttonPanel.add(verlassen);
        buttonPanel.add(autoren);
        starten.addActionListener(this);
        verlassen.addActionListener(this);
        autoren.addActionListener(this);

        add(buttonPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Starten")) {
            //neues fenster für anzahl auswählen
            new WindowAmountGameOfLife();
            setVisible(false);
        }
        //schließen
        else if (evt.getActionCommand().equals("Verlassen")) {
            System.exit(0);
        } else {
            // autoren in seperaten fenster ausgeben
            JFrame autorenFrame = new JFrame();
            autorenFrame.setSize(390, 430);
            autorenFrame.setLocation(250,350);
            String[] authors = {"Melis Yade Layik", "Sinan Ince", "Konuralp Celikyay", "Benno Dinsch", "Lau Kailany"};
            autorenFrame.setLayout(new GridLayout(5, 1));
            autorenFrame.setTitle("Autoren");
            for (int i = 0; i < authors.length; i++) {
                JLabel label = new JLabel(authors[i]);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                autorenFrame.add(label);
            }
            autorenFrame.setVisible(true);
        }

    }
//konstruktor öffnen
    public static void main(String[] args) {
        new StartMenuGameOfLife();
    }
}
