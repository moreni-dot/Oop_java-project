package schiffeversenken;

import java.util.Random;

/** Abstrakte Basisklasse fuer alle Spieler. */
public abstract class Spieler {
    private final String name;
    private final Spielfeld eigenesFeld;

    protected Spieler(String name, int feldGroesse) {
        this.name = name;
        this.eigenesFeld = new Spielfeld(feldGroesse);
    }

    public String getName() { return name; }

    public Spielfeld getEigenesFeld() { return eigenesFeld; }

    public boolean hatVerloren() { return eigenesFeld.alleVersenkt(); }

    /** Platziert die Flotte auf dem eigenen Feld. */
    public abstract void platziereFlotte(Flotte flotte);

    /** Waehlt eine Zielposition (0-basiert) auf dem gegnerischen Feld. */
    public abstract int waehleZiel(Spielfeld gegnerFeld);

    /** Wird nach jedem eigenen Schuss aufgerufen, damit der Spieler daraus lernen kann. */
    public void ergebnisMitteilen(int position, Schussergebnis ergebnis) {
        // Standard: nichts tun
    }

    /** Hilfsmethode: platziert alle Schiffe zufaellig. */
    protected void platziereZufaellig(Flotte flotte, Random zufall) {
        for (Flotte.Eintrag e : flotte.getEintraege()) {
            int start;
            do {
                start = zufall.nextInt(eigenesFeld.getGroesse() - e.laenge() + 1);
            } while (!eigenesFeld.kannPlatzieren(start, e.laenge()));
            eigenesFeld.platziere(e.name(), start, e.laenge());
        }
    }
}
