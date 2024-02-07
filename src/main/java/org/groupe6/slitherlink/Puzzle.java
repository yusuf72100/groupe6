package org.groupe6.slitherlink;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Puzzle implements Serializable {

    Cellule[][] cellules;

    // Constructeur
    public Puzzle(int l, int L) {
        cellules = new Cellule[l][L];
    }

    // Méthode pour sauvegarder le puzzle
    public static void sauvegarderPuzzle(Puzzle puzzle, String chemin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(puzzle);
            System.out.println("Puzzle sauvegardé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger le puzzle
    public static Puzzle chargerPuzzle(String chemin) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            Puzzle puzzle = (Puzzle) ois.readObject();
            System.out.println("Puzzle chargé avec succès.");
            return puzzle;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
