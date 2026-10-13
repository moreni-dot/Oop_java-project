package schiffeversenken;

/** Steuert den Spielablauf zwischen zwei Spielern. */
public class Spiel {
    private final Spieler mensch;
    private final Spieler computer;
    private final Flotte flotte;

    public Spiel(Spieler mensch, Spieler computer, Flotte flotte) {
        this.mensch = mensch;
        this.computer = computer;
        this.flotte = flotte;
    }

    public void starten() {
        System.out.println("=== Schiffe versenken (1D) ===");
        System.out.println("Legende: ~ Wasser/unbekannt  S Schiff  o daneben  X Treffer  # versenkt");
        System.out.println("Wer trifft, darf nochmal schiessen.\n");

        mensch.platziereFlotte(flotte);
        computer.platziereFlotte(flotte);

        Spieler amZug = mensch;
        Spieler gegner = computer;
        while (true) {
            zeigeFelder();
            boolean nochmal = zug(amZug, gegner);
            if (gegner.hatVerloren()) {
                zeigeFelder();
                System.out.println("\n*** " + amZug.getName() + " hat gewonnen! ***");
                return;
            }
            if (!nochmal) {
                Spieler tmp = amZug;
                amZug = gegner;
                gegner = tmp;
            }
        }
    }

    /** Fuehrt einen Schuss aus. Gibt true zurueck, wenn der Spieler nochmal darf. */
    private boolean zug(Spieler schuetze, Spieler ziel) {
        Spielfeld zielFeld = ziel.getEigenesFeld();
        int pos = schuetze.waehleZiel(zielFeld);
        Schussergebnis ergebnis = zielFeld.beschiesse(pos);
        schuetze.ergebnisMitteilen(pos, ergebnis);

        String meldung = switch (ergebnis) {
            case WASSER -> "Wasser.";
            case TREFFER -> "Treffer!";
            case VERSENKT -> zielFeld.schiffAn(pos).getName() + " versenkt!";
            case BEREITS_BESCHOSSEN -> "Feld war schon beschossen.";
        };
        System.out.println("\n" + schuetze.getName() + " schiesst auf Feld " + (pos + 1) + ": " + meldung);
        return ergebnis == Schussergebnis.TREFFER || ergebnis == Schussergebnis.VERSENKT;
    }

    private void zeigeFelder() {
        Spielfeld eigen = mensch.getEigenesFeld();
        System.out.println();
        System.out.println("     " + eigen.nummernZeile());
        System.out.println("Du:  " + eigen.darstellen(true));
        System.out.println("CPU: " + computer.getEigenesFeld().darstellen(false));
    }
}
