package GOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;;
import java.util.Random;

/**
 * @author  Melis Yade Layik, Sinan Ince, Konuralp Celikyay, Benno Dinsch, Lau Kailany
 * @version 1, 21.06.2023
 **/


public class WindowAmountGameOfLife extends JFrame implements ActionListener {

    private final JTextField field;
    private int num;

    public WindowAmountGameOfLife() {
        //größe Fenster und ort
        setBounds(600, 200, 500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); //null weil setBounds oben deklariert
        //text ausgeben
        JLabel label = new JLabel("Anzahl an Fenster eingeben ");
        label.setBounds(160, 140, 350, 100);
        add(label);
        //textfeld
        field = new JTextField();
        field.setBounds(190, 210, 100, 20);
        add(field);
        //button
        JButton button = new JButton("Start");
        button.setBounds(205, 350, 70, 35);
        add(button);
        button.addActionListener(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        new WindowAmountGameOfLife();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (field.getText().length() == 0) { //keine eingabe kommt fehler
            JOptionPane.showMessageDialog(this, "Invalide Eingabe", "Warning :O", JOptionPane.ERROR_MESSAGE);

        } else { //neues hauptfenster erstellen und anz an fenster ermitteln
            String numS = field.getText();
            num = Integer.parseInt(numS);
            DesktopPaneGameOfLife desktop = new DesktopPaneGameOfLife(); // Hauptfenster erzeugen


            int y = 10; //koordinate
            for (int i = 0; i < num; i++) {
                if (i == 0) { //erste fenster standard farbe
                    desktop.addChild(new BoardGameOfLife(null, null, desktop), y, y); // Ein Kindfenster einfuegen
                } else {//alle nächsten fenster random farben
                    desktop.addChild(new BoardGameOfLife(FarbenT.getRandomColor(), FarbenT.getRandomColor(), desktop), y, y);
                }
                y += 12; //postion um 12 verschieben
            }
            setVisible(false); //fenster von anz nicht mehr zeigen

            //menü von hauptfenster für neue fenster einfügen
            JMenuBar menuBar = new JMenuBar();
            JMenuItem fensterHinzufugen = new JMenuItem("Fenster hinzufügen");
            fensterHinzufugen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color color1 = FarbenT.getRandomColor();
                    Color color2 = FarbenT.getRandomColor();
                    while(color1.equals(color2)){
                        color1 = FarbenT.getRandomColor();
                        color2 = FarbenT.getRandomColor();
                    }
                    //kinder fenster erstellen mit random pos und farben
                    desktop.addChild(new BoardGameOfLife(color1,color2, desktop), new Random().nextInt(500), new Random().nextInt(500));


                }
            });

            menuBar.add(fensterHinzufugen);
            desktop.setJMenuBar(menuBar);
        }
    }
}
