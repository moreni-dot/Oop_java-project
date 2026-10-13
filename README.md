# Schiffe versenken (1D) – Java, Konsole

Eindimensionale Version von Schiffe versenken gegen den Computer.

## Starten
```bash
javac -d out src/schiffeversenken/*.java
java -cp out schiffeversenken.Main
```

## Regeln
- Spielfeld: 20 Felder in einer Reihe.
- Flotte: Schlachtschiff (4), Kreuzer (3), Zerstoerer (2), U-Boot (1).
- Schiffe duerfen sich nicht beruehren.
- Wer trifft, darf nochmal schiessen. Wer zuerst alle gegnerischen Schiffe versenkt, gewinnt.

Legende: `~` Wasser/unbekannt, `S` Schiff, `o` daneben, `X` Treffer, `#` versenkt
