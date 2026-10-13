package schiffeversenken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Ein eindimensionales Spielfeld mit Schiffen und Schussprotokoll. */
public class Spielfeld {
    private final int groesse;
    private final List<Schiff> schiffe = new ArrayList<>();
    private final boolean[] beschossen;

    public Spielfeld(int groesse) {
        this.groesse = groesse;
        this.beschossen = new boolean[groesse];
    }

    public int getGroesse() { return groesse; }

    public List<Schiff> getSchiffe() { return Collections.unmodifiableList(schiffe); }

    public boolean istGueltig(int position) {
        return position >= 0 && position < groesse;
    }

    /** Prueft, ob ein Schiff dort Platz hat (im Feld, ohne Ueberlappung, ohne direkte Beruehrung). */
    public boolean kannPlatzieren(int start, int laenge) {
        int ende = start + laenge - 1;
        if (!istGueltig(start) || !istGueltig(ende)) {
            return false;
        }
        for (int p = start - 1; p <= ende + 1; p++) {
            if (schiffAn(p) != null) {
                return false;
            }
        }
        return true;
    }

    public boolean platziere(String name, int start, int laenge) {
        if (!kannPlatzieren(start, laenge)) {
            return false;
        }
        schiffe.add(new Schiff(name, start, laenge));
        return true;
    }

    public Schiff schiffAn(int position) {
        for (Schiff s : schiffe) {
            if (s.belegt(position)) {
                return s;
            }
        }
        return null;
    }

    public boolean istBeschossen(int position) {
        return beschossen[position];
    }

    public Schussergebnis beschiesse(int position) {
        if (!istGueltig(position)) {
            throw new IllegalArgumentException("Position ausserhalb des Feldes: " + position);
        }
        if (beschossen[position]) {
            return Schussergebnis.BEREITS_BESCHOSSEN;
        }
        beschossen[position] = true;
        Schiff schiff = schiffAn(position);
        if (schiff == null) {
            return Schussergebnis.WASSER;
        }
        schiff.treffen(position);
        return schiff.istVersenkt() ? Schussergebnis.VERSENKT : Schussergebnis.TREFFER;
    }

    public boolean alleVersenkt() {
        for (Schiff s : schiffe) {
            if (!s.istVersenkt()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Textdarstellung des Feldes.
     * Legende: ~ unbekannt/Wasser, S Schiff, o Fehlschuss, X Treffer, # versenkt
     */
    public String darstellen(boolean schiffeZeigen) {
        StringBuilder sb = new StringBuilder();
        for (int p = 0; p < groesse; p++) {
            Schiff s = schiffAn(p);
            char c;
            if (beschossen[p]) {
                c = s == null ? 'o' : (s.istVersenkt() ? '#' : 'X');
            } else {
                c = (schiffeZeigen && s != null) ? 'S' : '~';
            }
            sb.append(String.format("%3c", c));
        }
        return sb.toString();
    }

    public String nummernZeile() {
        StringBuilder sb = new StringBuilder();
        for (int p = 0; p < groesse; p++) {
            sb.append(String.format("%3d", p + 1));
        }
        return sb.toString();
    }
}
