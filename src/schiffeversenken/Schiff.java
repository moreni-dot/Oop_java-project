package schiffeversenken;

/** Ein Schiff belegt eine zusammenhaengende Strecke auf dem 1D-Spielfeld. */
public class Schiff {
    private final String name;
    private final int start;
    private final int laenge;
    private final boolean[] getroffen;

    public Schiff(String name, int start, int laenge) {
        if (laenge <= 0) {
            throw new IllegalArgumentException("Laenge muss positiv sein");
        }
        this.name = name;
        this.start = start;
        this.laenge = laenge;
        this.getroffen = new boolean[laenge];
    }

    public boolean belegt(int position) {
        return position >= start && position < start + laenge;
    }

    /** Markiert die Position als getroffen. Gibt true zurueck, wenn das Schiff dort liegt. */
    public boolean treffen(int position) {
        if (!belegt(position)) {
            return false;
        }
        getroffen[position - start] = true;
        return true;
    }

    public boolean istVersenkt() {
        for (boolean t : getroffen) {
            if (!t) {
                return false;
            }
        }
        return true;
    }

    public String getName() { return name; }
    public int getStart() { return start; }
    public int getEnde() { return start + laenge - 1; }
    public int getLaenge() { return laenge; }
}
