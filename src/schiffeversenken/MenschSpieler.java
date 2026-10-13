package schiffeversenken;

import java.util.Random;
import java.util.Scanner;

/** Ein Spieler, der ueber die Konsole steuert. */
public class MenschSpieler extends Spieler {
    private final Scanner eingabe;

    public MenschSpieler(String name, int feldGroesse, Scanner eingabe) {
        super(name, feldGroesse);
        this.eingabe = eingabe;
    }

    @Override
    public void platziereFlotte(Flotte flotte) {
        System.out.print("Schiffe selbst platzieren? (j/n): ");
        if (!eingabe.nextLine().trim().equalsIgnoreCase("j")) {
            platziereZufaellig(flotte, new Random());
            return;
        }
        Spielfeld feld = getEigenesFeld();
        for (Flotte.Eintrag e : flotte.getEintraege()) {
            while (true) {
                System.out.println();
                System.out.println(feld.nummernZeile());
                System.out.println(feld.darstellen(true));
                int start = leseZahl("Startfeld fuer " + e.name() + " (Laenge " + e.laenge() + "): ") - 1;
                if (feld.platziere(e.name(), start, e.laenge())) {
                    break;
                }
                System.out.println("Dort ist kein Platz (Schiffe duerfen sich nicht beruehren).");
            }
        }
    }

    @Override
    public int waehleZiel(Spielfeld gegnerFeld) {
        while (true) {
            int pos = leseZahl("Auf welches Feld schiessen? (1-" + gegnerFeld.getGroesse() + "): ") - 1;
            if (!gegnerFeld.istGueltig(pos)) {
                System.out.println("Ungueltiges Feld.");
            } else if (gegnerFeld.istBeschossen(pos)) {
                System.out.println("Dort hast du schon hingeschossen.");
            } else {
                return pos;
            }
        }
    }

    private int leseZahl(String frage) {
        while (true) {
            System.out.print(frage);
            if (!eingabe.hasNextLine()) {
                throw new IllegalStateException("Eingabe beendet");
            }
            String zeile = eingabe.nextLine().trim();
            try {
                return Integer.parseInt(zeile);
            } catch (NumberFormatException ex) {
                System.out.println("Bitte eine Zahl eingeben.");
            }
        }
    }
}
