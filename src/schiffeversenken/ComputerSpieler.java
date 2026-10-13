package schiffeversenken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Computergegner: schiesst zufaellig, verfolgt aber Treffer
 * nach links und rechts, bis das Schiff versenkt ist.
 */
public class ComputerSpieler extends Spieler {
    private final Random zufall;
    private final List<Integer> offeneTreffer = new ArrayList<>();

    public ComputerSpieler(String name, int feldGroesse, Random zufall) {
        super(name, feldGroesse);
        this.zufall = zufall;
    }

    @Override
    public void platziereFlotte(Flotte flotte) {
        platziereZufaellig(flotte, zufall);
    }

    @Override
    public int waehleZiel(Spielfeld gegnerFeld) {
        if (!offeneTreffer.isEmpty()) {
            int min = offeneTreffer.stream().min(Integer::compare).get();
            int max = offeneTreffer.stream().max(Integer::compare).get();
            List<Integer> kandidaten = new ArrayList<>();
            if (gegnerFeld.istGueltig(min - 1) && !gegnerFeld.istBeschossen(min - 1)) {
                kandidaten.add(min - 1);
            }
            if (gegnerFeld.istGueltig(max + 1) && !gegnerFeld.istBeschossen(max + 1)) {
                kandidaten.add(max + 1);
            }
            if (!kandidaten.isEmpty()) {
                return kandidaten.get(zufall.nextInt(kandidaten.size()));
            }
        }
        List<Integer> frei = new ArrayList<>();
        for (int p = 0; p < gegnerFeld.getGroesse(); p++) {
            if (!gegnerFeld.istBeschossen(p)) {
                frei.add(p);
            }
        }
        return frei.get(zufall.nextInt(frei.size()));
    }

    @Override
    public void ergebnisMitteilen(int position, Schussergebnis ergebnis) {
        if (ergebnis == Schussergebnis.TREFFER) {
            offeneTreffer.add(position);
        } else if (ergebnis == Schussergebnis.VERSENKT) {
            offeneTreffer.clear();
        }
    }
}
