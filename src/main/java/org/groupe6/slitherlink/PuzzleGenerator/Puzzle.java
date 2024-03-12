package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Yamis
 */

// Classe Puzzle qui implémente Serializable
public class Puzzle implements Serializable {

    private static final long serialVersionUID = 1L;

    private int largeur; // Nombre de lignes
    private int longueur; // Nombre de colonnes
    private CelluleData[][] solutionPuzzle; // Grille avec la solution du puzzle
    private CelluleData[][] grilleCellules; // Grille de cellules du puzzle
    private DifficultePuzzle difficulte; // Difficulté du puzzle

    // Constructeur de la classe Puzzle
    public Puzzle(int largeur, int longueur, CelluleData[][] solutionPuzzle, DifficultePuzzle difficulte) {
        if (solutionPuzzle.length != largeur || solutionPuzzle[0].length != longueur) {
            throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
        }

        this.largeur = largeur;
        this.longueur = longueur;
        this.solutionPuzzle = solutionPuzzle;
        this.grilleCellules = genererGrillePropre();
        this.difficulte = difficulte;
    }

    // Méthode pour obtenir la largeur
    public int getLargeur() {
        return largeur;
    }

    // Méthode pour obtenir la longueur
    public int getLongueur() {
        return longueur;
    }

    public DifficultePuzzle getDifficulte() {
        return difficulte;
    }

    // Méthode pour générer un puzzle propre a partir de la solution
    private CelluleData[][] genererGrillePropre() {
        CelluleData[][] grillePropre = new CelluleData[largeur][longueur];

        for (int y = 0; y < solutionPuzzle.length; y++) {
            for (int x = 0; x < solutionPuzzle[y].length; x++) {
                ValeurCote[] cotesVide = new ValeurCote[4];
                for (int i = 0; i < cotesVide.length; i++) {
                    cotesVide[i] = ValeurCote.VIDE;
                }
                grillePropre[y][x] = new CelluleData(solutionPuzzle[y][x].getValeur(), cotesVide);
            }
        }

        return grillePropre;
    }

    public CelluleData[][] getSolutionCelluleData() {
        return this.solutionPuzzle;
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
                str += grilleCellules[y][x].getValeur() + " ";
            }
            str += "\n";
        }

        return str;
    }

    // Méthode pour obenir une cellule dans la grille
    public CelluleData getCellule(int y, int x) {
        return grilleCellules[y][x];
    }

    // Méthode pour savoir si le puzzle est complet
    public boolean estComplet() {
        for (int y = 0; y < solutionPuzzle.length; y++) {
            for (int x = 0; x < solutionPuzzle[y].length; x++) {
                System.out.print("-----------------");
                System.out.println();
                System.out.print(solutionPuzzle[y][x].getValeur() + "-");
                for (int i = 0; i < 4; i++) {
                    System.out.print(solutionPuzzle[y][x].getCote(i) + ",");
                }
                System.out.println();
                System.out.print(grilleCellules[y][x].getValeur() + "-");
                for (int i = 0; i < 4; i++) {
                    System.out.print(grilleCellules[y][x].getCote(i) + ",");
                }
                System.out.println();

                if (!solutionPuzzle[y][x].equals(grilleCellules[y][x])) {
                    return false;
                }
            }
        }

        return true;
    }

    public CelluleData getCelluleAdjacente(int y, int x, int cote) {
        int yAdj = y;
        int xAdj = x;

        switch (cote) {
            case CelluleData.HAUT:
                yAdj--;
                break;
            case CelluleData.BAS:
                yAdj++;
                break;
            case CelluleData.GAUCHE:
                xAdj--;
                break;
            case CelluleData.DROITE:
                xAdj++;
                break;
        }

        if (estDansGrille(yAdj, xAdj)) {
            return grilleCellules[yAdj][xAdj];
        } else {
            return null;
        }
    }

    public boolean estDansGrille(int y, int x) {
        return y >= 0 && y < largeur && x >= 0 && x < longueur;
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
