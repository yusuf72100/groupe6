package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Classe Puzzle qui implémente Serializable
class Puzzle implements Serializable {

    // Enum pour les difficultés
    public enum DifficultePuzzle {
        FACILE,
        MOYEN,
        DIFFICILE;
    }

    private DifficultePuzzle difficulte; // Difficulté du puzzle
    private int largeur; // Nombre de lignes
    private int longueur; // Nombre de colonnes
    private Cellule[][] grilleCellules; // Grille de cellules
    private GestionnaireAction gestionnaireAction; // Gestionnaire d'actions

    // Méthode pour obtenir la difficulté
    public DifficultePuzzle getDifficulte() {
        return difficulte;
    }

    // Méthode pour obtenir la largeur
    public int getLargeur() {
        return largeur;
    }

    // Méthode pour obtenir la longueur
    public int getLongueur() {
        return longueur;
    }

    // Constructeur de la classe Puzzle
    public Puzzle(DifficultePuzzle difficulte, int largeur, int longueur, Cellule[][] grilleCellules) {
        if (grilleCellules.length != largeur || grilleCellules[0].length != longueur) {
            throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
        }

        this.difficulte = difficulte;
        this.largeur = largeur;
        this.longueur = longueur;
        this.grilleCellules = grilleCellules;
    }

    // Méthode pour afficher le puzzle dans la console
    @Override
    public String toString() {
        String str = "";
        str += "Difficulté : " + difficulte + "\n";
        str += "Largeur : " + largeur + "\n";
        str += "Longueur : " + longueur + "\n";
        str += "Grille : \n";

        for (int y = 0; y < grilleCellules.length; y++) {
            for (int x = 0; x < grilleCellules[y].length; x++) {
                str += grilleCellules[y][x] + " ";
            }
            str += "\n";
        }

        return str;
    }

    // Méthode pour obenir une cellule dans la grille
    public Cellule getCellule(int y, int x) {
        return grilleCellules[y][x];
    }

    // Méthode pour sauvegarder le puzzle
    public static void sauvegarderPuzzle(Puzzle puzzle, String chemin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(puzzle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger le puzzle
    public static Puzzle chargerPuzzle(String chemin) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            Puzzle puzzle = (Puzzle) ois.readObject();
            return puzzle;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
