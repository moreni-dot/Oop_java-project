package schiffeversenken;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int FELD_GROESSE = 20;

    public static void main(String[] args) {
        Scanner eingabe = new Scanner(System.in);
        Spieler mensch = new MenschSpieler("Du", FELD_GROESSE, eingabe);
        Spieler computer = new ComputerSpieler("Computer", FELD_GROESSE, new Random());
        new Spiel(mensch, computer, Flotte.standard()).starten();
    }
}
