package GOL;

import java.awt.*;
import java.util.Random;

/**
 * @author  Melis Yade Layik, Sinan Ince, Konuralp Celikyay, Benno Dinsch, Lau Kailany
 * @version 1, 21.06.2023
 **/



public enum FarbenT { //farben werden in enum zugewiesen
    SCHWARZ(Color.BLACK),
    GELB(Color.YELLOW),
    GRUEN(Color.GREEN),
    ROT(Color.RED),
    ORANGE(Color.ORANGE),
    BLAU(Color.BLUE),
    CYAN(Color.CYAN),
    WEISS(Color.WHITE),
    MAGENTA(Color.MAGENTA),
    GRAU(Color.GRAY);

    private final Color color;

    FarbenT(Color color) {
        this.color = color;

    }

    public Color getColor() {
        return color;
    }

    public static Color getRandomColor() { // da haben wir alle farben in ein array getan und rufen das in zeile 38 random auf, damit wir eine random farbe bekommen
        Color[] colors = {SCHWARZ.getColor(), GELB.getColor(), GRUEN.getColor(), ROT.getColor(),
        ORANGE.getColor(), BLAU.getColor(),WEISS.getColor(), MAGENTA.getColor(),GRAU.getColor()};

        Random random = new Random();
        return colors[random.nextInt(colors.length)];


    }
}
