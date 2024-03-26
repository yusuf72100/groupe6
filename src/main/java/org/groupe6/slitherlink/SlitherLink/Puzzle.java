package org.groupe6.slitherlink.SlitherLink;

import org.groupe6.slitherlink.PuzzleGenerator.CelluleData;
import org.groupe6.slitherlink.PuzzleGenerator.ValeurCote;

import java.io.*;

/**
 * @author Yamis
 */

// Classe Puzzle qui implémente Serializable
public class Puzzle implements Serializable {

    private static final long serialVersionUID = 1L;

    private int largeur; // Nombre de lignes
    private int longueur; // Nombre de colonnes
    private org.groupe6.slitherlink.SlitherLink.CelluleData[][] solutionPuzzle; // Grille avec la solution du puzzle
    private org.groupe6.slitherlink.SlitherLink.CelluleData[][] grilleCellules; // Grille de cellules du puzzle
    private org.groupe6.slitherlink.SlitherLink.DifficultePuzzle difficulte; // Difficulté du puzzle

    // Constructeur de la classe Puzzle
    public Puzzle(int largeur, int longueur, org.groupe6.slitherlink.SlitherLink.CelluleData[][] solutionPuzzle, org.groupe6.slitherlink.SlitherLink.DifficultePuzzle difficulte) {
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

    public org.groupe6.slitherlink.SlitherLink.DifficultePuzzle getDifficulte() {
        return difficulte;
    }

    // Méthode pour générer un puzzle propre a partir de la solution
    private org.groupe6.slitherlink.SlitherLink.CelluleData[][] genererGrillePropre() {
        org.groupe6.slitherlink.SlitherLink.CelluleData[][] grillePropre = new org.groupe6.slitherlink.SlitherLink.CelluleData[largeur][longueur];

        for (int y = 0; y < solutionPuzzle.length; y++) {
            for (int x = 0; x < solutionPuzzle[y].length; x++) {
                ValeurCote[] cotesVide = new ValeurCote[4];
                for (int i = 0; i < cotesVide.length; i++) {
                    cotesVide[i] = ValeurCote.VIDE;
                }
                grillePropre[y][x] = new org.groupe6.slitherlink.SlitherLink.CelluleData(solutionPuzzle[y][x].getValeur(), cotesVide);
            }
        }

        return grillePropre;
    }

    public org.groupe6.slitherlink.SlitherLink.CelluleData[][] getSolutionCelluleData() {
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
    public org.groupe6.slitherlink.SlitherLink.CelluleData getCellule(int y, int x) {
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

    public org.groupe6.slitherlink.SlitherLink.CelluleData getCelluleAdjacente(int y, int x, int cote) {
        int yAdj = y;
        int xAdj = x;

        switch (cote) {
            case org.groupe6.slitherlink.PuzzleGenerator.CelluleData.HAUT:
                yAdj--;
                break;
            case org.groupe6.slitherlink.PuzzleGenerator.CelluleData.BAS:
                yAdj++;
                break;
            case org.groupe6.slitherlink.PuzzleGenerator.CelluleData.GAUCHE:
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
