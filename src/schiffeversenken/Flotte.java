package schiffeversenken;

import java.util.List;

/** Beschreibt, welche Schiffe jeder Spieler setzen muss. */
public class Flotte {
    public record Eintrag(String name, int laenge) { }

    private final List<Eintrag> eintraege;

    public Flotte(List<Eintrag> eintraege) {
        this.eintraege = List.copyOf(eintraege);
    }

    public static Flotte standard() {
        return new Flotte(List.of(
                new Eintrag("Schlachtschiff", 4),
                new Eintrag("Kreuzer", 3),
                new Eintrag("Zerstoerer", 2),
                new Eintrag("U-Boot", 1)));
    }

    public List<Eintrag> getEintraege() { return eintraege; }
}
