package GOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * @author  Melis Yade Layik, Sinan Ince, Konuralp Celikyay, Benno Dinsch, Lau Kailany
 * @version 1, 21.06.2023
 **/



public class MenuBarGameOfLife {

    public static JMenuBar setupMenuForBoard(BoardGameOfLife board) {
        JMenuBar menuBar = new JMenuBar();
        //menübar erstellen mit vorausgesetzten optionen
        JColorChooser chooser = new JColorChooser();
        JMenu modus = new JMenu("Modus");
        JMenu geschw = new JMenu("Geschwindigkeit");
        JMenu fenster = new JMenu("Fenster");
        JMenu fig = new JMenu("Figuren");
        JMenuItem l = new JMenuItem("Laufen");
        JMenuItem s = new JMenuItem("Setzen");
        JMenuItem m = new JMenuItem("Malen");
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        JMenuItem todItem = new JMenuItem("Tod");
        JMenuItem lebendigItem = new JMenuItem("Lebendig");
        JMenuItem individuelleForm = new JMenuItem("Individuelle Form");
        JMenuItem glider = new JMenuItem("Glider");
        JMenuItem bogen = new JMenuItem("Bogen");
        JMenuItem myForm = new JMenuItem("Experimental Eggs");
        JMenuItem schmetterling = new JMenuItem("Schmetterling");

        glider.addActionListener(e -> {
            board.newCells(FigurenT.GLIDER.getValue());
            board.getTimer().restart();
            board.getTimer().start();
        });
        bogen.addActionListener(e -> {
            board.newCells(FigurenT.BOGEN.getValue());
            board.getTimer().restart();
            board.getTimer().start();
        });
        myForm.addActionListener(e -> {
            board.newCells(FigurenT.MYFORM.getValue());
            board.getTimer().restart();
            board.getTimer().start();
        });
        schmetterling.addActionListener(e -> {
            board.newCells(FigurenT.SCHMETTERLING.getValue());
            board.getTimer().restart();
            board.getTimer().start();
        });


        individuelleForm.addActionListener(e -> {
            String figureText = board.getFigureText();
            figureText = JOptionPane.showInputDialog(board.getMainPane(), "Text von catagolue.hatsya.com: ", "4bob2o$4b2obo2$4b3o$2obo3bo$2obo3bo$3b2o2bob2o$3bobobob2o$4b3o2$3bob2o$3b2obo!");
            if (figureText != null) {
                board.newCells(figureText);
            }

            board.getTimer().restart();
            board.getTimer().start();
        });
        l.addActionListener(e -> {
            board.setZustand(0);
            board.getTimer().restart();
            board.getTimer().start();
        });
        s.addActionListener(e -> {
            board.setZustand(1);
            board.getTimer().restart();
            board.getTimer().stop();
        });
        m.addActionListener(e -> {
            board.setZustand(2);
            if (board.getZustand() == 2) {
                board.getTimer().restart();
                board.getTimer().stop();
            } else {
                board.getTimer().restart();
                board.getTimer().start();
                board.getTimer().restart();
            }
        });

        plus.addActionListener(e -> {
            board.setGeschwindigkeit(board.getGeschwindigkeit() - 10);
            if (board.getGeschwindigkeit() < 10) { //da geschw unter 10 zu langsam ist, ist die grenze 10
                board.setGeschwindigkeit(10);
            }
            board.getTimer().setDelay(board.getGeschwindigkeit());
        });
        minus.addActionListener(e -> {
            board.setGeschwindigkeit(board.getGeschwindigkeit() + 10);
            board.getTimer().setDelay(board.getGeschwindigkeit());
        });
        todItem.addActionListener(e -> { //farbe ändern für tod
            try {
                final JColorChooser chooser1 = new JColorChooser();
                class OkListener implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        board.setTod(chooser1.getColor());
                    }
                }
                JDialog co = JColorChooser.createDialog(null, "Farbe auswählen" , false, chooser1, new OkListener(), null);
                chooser1.setColor(new Color(178, 32, 158));
                co.setVisible(true);
            } catch (Exception ex) {
                System.out.println("Kaputt! Neustarten");
            }
        });

        lebendigItem.addActionListener(e -> {
            try {
                final JColorChooser chooser12 = new JColorChooser();
                class OkListener implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        board.setLebendig(chooser12.getColor());
                    }
                }
                JDialog co = JColorChooser.createDialog(null, "Farbe auswählen", false, chooser12, new OkListener(), null);
                chooser12.setColor(new Color(35, 255, 0));
                co.setVisible(true);
            } catch (Exception ex) {
                System.out.println("Kaputt! Neustarten");
            }
        });
        //einzelne dropbars-menues hinzufügen
        modus.add(l);
        modus.add(s);
        modus.add(m);
        geschw.add(plus);
        geschw.add(minus);
        fenster.add(lebendigItem);
        fenster.add(todItem);
        fig.add(glider);
        fig.add(bogen);
        fig.add(myForm);
        fig.add(schmetterling);
        fig.add(individuelleForm);
        //alles hinzufügen in hauptcontainer
        menuBar.add(modus);
        menuBar.add(geschw);
        menuBar.add(fenster);
        menuBar.add(fig);
        return menuBar;


    }
}
