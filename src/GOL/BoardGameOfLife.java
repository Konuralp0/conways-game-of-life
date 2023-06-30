package GOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static java.lang.Math.min;

/**
 * @author  Melis Yade Layik, Sinan Ince, Konuralp Celikyay, Benno Dinsch, Lau Kailany
 * @version 1, 21.06.2023
 **/



public class BoardGameOfLife extends JInternalFrame {
    private int[] cells;
    private int scale = 15;
    private String figureText;
    private int size;
    private int zustand = 0;
    private static int anz = 0;
    private int geschwindigkeit = 100;
    private DesktopPaneGameOfLife mainPane;
    private Timer timer;
    private Color tod, lebendig;
    private MouseAdapter mouseAdapter;


    public BoardGameOfLife(Color tod, Color lebendig, DesktopPaneGameOfLife pane) {
        super("GameOfLife " + anz, true, true); //jinternalframe aufrufen
        //bei null default farbe
        if (tod == null && lebendig == null) {
            this.lebendig = FarbenT.SCHWARZ.getColor();
            this.tod = FarbenT.CYAN.getColor();
        } else {
            //wenn nicht null dann farbe von konstruktor nehmen
            this.lebendig = lebendig;
            this.tod = tod;
        }

        setSize(500, 500);
        size = getWidth() / scale; //kästchen anzahl berechnen
        mainPane = pane;
        setVisible(true);
        Container container = getContentPane();
        setUpCells(); //random 0 1 für unser board vorbereiten
        setMaximizable(true); // fenbster größer machen
        setIconifiable(true); //kleiner
        setClosable(true); //schließen
        JMenuBar menuBar = MenuBarGameOfLife.setupMenuForBoard(this);
        setJMenuBar(menuBar);


        drawCells(container);// board anzeigen

        timer = new Timer(geschwindigkeit, e -> { //timer mit geschw geben
            nextGen(); //nächste gen berechnen
            container.removeAll(); //letzte gen löcshen
            drawCells(container); //neue gen zeichnen
            container.revalidate();
            container.repaint();
        });
        timer.start();
        anz++;

    }


    private void setUpCells() { // random lebendig oder tote zellen einfügen
        Random random = new Random();
        cells = new int[size * size]; //s*s für anz der zellen
        for (int i = 0; i < cells.length; i++) {
            cells[i] = random.nextInt(2);
        }
    }
    private int calculateNeighbours(int i, int j) {

        int num = 0;

        for (int x = -1; x <= 1; x++) { //-1 für zelle links 0 für sich selbst 1 für rechts
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) { //wenn sich selber dann fortfahren
                    continue;
                }
                // 1 0 1
                // 0 1 1
                // 0 0 1
                int nachbarn_i = (i + x + size) % size; // Horizontal (torus-förmige welt,deswegen nachbar auch auf andere seite setzen wenn möglich)
                int nachbarn_j = (j + y + size) % size; // Vertikal
                num += cells[nachbarn_i * size + nachbarn_j]; //anz an nachbarn addieren und zurückgeben
            }
        }

        return num;
    }

    private void nextGen() {
        int[] next = cells.clone(); //cells array in next klonen
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int neighbours = calculateNeighbours(i, j);
                if (cells[i * size + j] == 1) {   //Wir wollen hier schauen ob schon eine lebendige Zelle in die nächste Generation geht?  i:zeile ; j:spalte
                    if (neighbours < 2 || neighbours > 3) { //nächste zelle tod
                        next[i * size + j] = 0;
                    }
                } else {
                    if (neighbours == 3) { // lebendig
                        next[i * size + j] = 1;

                    }
                }

            }
        }
        cells = next.clone(); //array in cells überschreiben
    }
    private void drawCells(Container container) {

        container.setLayout(new GridLayout(size, size)); //neuer container mit den zellen gitter
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JLabel label = new JLabel();            //für jede zelle neues label damit das interaktiv ist (bspw malen)
                label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                int index = i * size + j;   //da es ein 1D array ist, müssen alle zellen in einer zeile des arrays beschrieben werden (size anzahl an spalten)
                // bsp Der Zustand der Zelle ist cells[5] = 1
                //int[] cells = {1, 0, 1, 0, 1, 1, 0, 0, 1};
                // 1 0 1
                // 0 1 1
                // 0 0 1
                if (cells[index] == 1) { //wenn array mit index 1 ist dann lebendig wenn nicht tod
                    label.setBackground(lebendig);
                    label.setOpaque(true);
                } else {
                    label.setBackground(tod);
                    label.setOpaque(true);
                }

                label.addMouseMotionListener(mouseAdapter); //für zeichnen
                label.addMouseListener(mouseAdapter); // für setzen
                container.add(label); //hinzufügen in container

                mouseAdapter = new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        if (zustand == 2) { //modus malen wenn 2
                            JLabel label = (JLabel) e.getSource();  //welche zelle wurde ausgewählt
                            int index = container.getComponentZOrder(label); // kooridnaten für index
                            cells[index] = 1; //position wo maus ist lebendig setzen
                            label.setBackground(lebendig);
                            label.setOpaque(true);
                            label.setVisible(true);
                        }
                    }

                    public void mousePressed(MouseEvent e) {
                        if (zustand == 1 || zustand == 0) { // wenn zustand laufen oder setzen
                            JLabel label = (JLabel) e.getSource();
                            int index = container.getComponentZOrder(label);
                            if (cells[index] == 0) { // wenn tod dann lebendig
                                cells[index] = 1;
                                label.setBackground(lebendig);
                            } else if (cells[index] == 1) {// andersherum
                                cells[index] = 0;
                                label.setBackground(tod);
                            }
                            label.setOpaque(true);
                        }
                    }
                };
            }
        }
    }


    public void newCells(String fgrtxt) {
        cells = new int[size * size];
        int pos = 0; //chartxt position
        int r = 0; //r ist die anzahl der wiederholten kennung (b, o, oder $), bspw wir haben 22o, dann wird erstmal in else reingesprungen und 22 in r eingefügt,
        //dann wird 22 zellen in der for-schleife auf 1 gesetzt und r wird dann n dazu addiert um die näcste zelle fortfahren zu können

        int n = min(getWidth(), getHeight()) / (scale / 2), leftSide = n; //damit die formen mittig gemacht werden,
        // soll in n die aktuelle pos speichern und leftside ist der linkeste zelle der reihe(wird verwendet für zeilenumbruch $)
        // n ist für den beginn index, r für anz der lebendigen zellen in eine reihe
        while (fgrtxt.charAt(pos) != '!') { //wenn ! dann soll beenden, charat ist die abfrage an welcher position
            if (fgrtxt.charAt(pos) == 'b') { // für tod
                if (r == 0) {
                    r = 1;
                }
                n += r; // die verschiebung der lebendige zelle in n speichern
                r = 0;
            } else if (fgrtxt.charAt(pos) == 'o') { //für lebendig
                if (r == 0) {
                    r = 1;
                }
                for (int i = n; i < n + r; i++) { //solange i soll lebendig
                    cells[i] = 1;
                }

                n += r;
                r = 0;

            } else if (fgrtxt.charAt(pos) == '$') { //nächste zeile
                if (r == 0) {
                    r = 1;
                }
                leftSide += size * r;
                n = leftSide;
                r = 0;
            } else {
                if (r == 0) {
                    r = Integer.parseInt(String.valueOf(fgrtxt.charAt(pos)));
                } else {
                    r = r * 10 + Integer.parseInt(String.valueOf(fgrtxt.charAt(pos))); //wenn zahl zweistellig
                }
            }
            pos++;
        }
    }



    /*berechnet die Anzahl der Nachbarn einer
        Zelle mit den gegebenen Koordinaten (i, j)
        im 2D-Gitter des Game of Life.
        x von -1 bis 1 und die innere Schleife über die Werte
        von y von -1 bis 1. Dadurch werden alle möglichen
        horizontalen und vertikalen Verschiebungen um die Zelle abgedeckt.
        x == y continue denn die zelle selbst ist kein nachbarn
        ni = (i + x + size) % size für die horizontale
        Koordinate der Nachbarzelle.
                nj = (j + y + size) % size für die vertikale
        Koordinate der Nachbarzelle.*/




    public String getFigureText() {
        return figureText;
    }

    public int getZustand() {
        return zustand;
    }


    public int getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public DesktopPaneGameOfLife getMainPane() {
        return mainPane;
    }

    public Timer getTimer() {
        return timer;
    }


    public void setZustand(int zustand) {
        this.zustand = zustand;
    }

    public void setGeschwindigkeit(int geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }

    public void setTod(Color tod) {
        this.tod = tod;
    }

    public void setLebendig(Color lebendig) {
        this.lebendig = lebendig;
    }
}