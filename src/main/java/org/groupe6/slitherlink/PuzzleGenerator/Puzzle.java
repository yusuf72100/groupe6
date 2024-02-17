package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Yamis
 */

/**
 * Classe Puzzle qui implémente Serializable
 */
public class Puzzle implements Serializable {

    private final PartieInfos infoPartie; // Les informations de la partie
    private final int largeur; // Nombre de lignes
    private final int longueur; // Nombre de colonnes
    private final Cellule_Data[][] grilleCellules; // Grille de cellules
    private final Cellule_Data[][] sollutionPuzzle; // Solution du puzzle
    // private GestionnaireAction gestionnaireAction; // Gestionnaire d'actions
    // private List<AideInfos> historiqueAide; // Historique des aides

    // Méthode pour obtenir les informations de la partie
    public PartieInfos getInfoPartie() {
        return infoPartie;
    }

    /**
     * Méthode pour obtenir la largeur
     * @return
     */
    public int getLargeur() {
        return largeur;
    }

    // Méthode pour obtenir la longueur
    public int getLongueur() {
        return longueur;
    }

    /**
     * Constructeur de la classe Puzzle
     * @param infoPartie
     * @param largeur
     * @param longueur
     * @param sollutionPuzzle
     */
    public Puzzle(PartieInfos infoPartie, int largeur, int longueur, Cellule_Data[][] sollutionPuzzle) {
        if (sollutionPuzzle.length != largeur || sollutionPuzzle[0].length != longueur) {
            throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
        }

        this.infoPartie = infoPartie;
        this.largeur = largeur;
        this.longueur = longueur;
        this.sollutionPuzzle = sollutionPuzzle;
        this.grilleCellules = genererGrillePropre();
    }

    /**
     * Méthode pour générer un puzzle propre a partir de la solution
     * @return Cellule_Data[][]
     */
    private Cellule_Data[][] genererGrillePropre() {
        Cellule_Data[][] grillePropre = new Cellule_Data[largeur][longueur];

        for (int y = 0; y < sollutionPuzzle.length; y++) {
            for (int x = 0; x < sollutionPuzzle[y].length; x++) {
                Cellule_Data.ValeurCote[] cotesVide = new Cellule_Data.ValeurCote[4];
                Arrays.fill(cotesVide, Cellule_Data.ValeurCote.VIDE);
                grillePropre[y][x] = new Cellule_Data(sollutionPuzzle[y][x].getValeur(), cotesVide);
            }
        }

        return grillePropre;
    }

    /**
     * Méthode pour afficher le puzzle dans la console
     * @return
     */
    @Override
    public String toString() {
        String str = "";
        str += "Date : " + infoPartie.getDate() + "\n";
        str += "Score : " + infoPartie.getScore() + "\n";
        str += "Chrono : " + infoPartie.getChrono() + "\n";
        str += "Complète : " + infoPartie.getComplete() + "\n";
        str += "Mode de jeu : " + infoPartie.getModeJeu() + "\n";
        str += "Difficulté : " + infoPartie.getDifficulte() + "\n";
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

    /**
     * Méthode pour obenir une cellule dans la grille
     * @param y
     * @param x
     * @return
     */
    public Cellule_Data getCellule(int y, int x) {
        return grilleCellules[y][x];
    }

    public Cellule_Data[][] getCelluleData() { return this.sollutionPuzzle; }

    // Méthode pour sauvegarder le puzzle
    public static void sauvegarderPuzzle(Puzzle puzzle, String chemin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(puzzle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour charger le puzzle
     * @param chemin
     * @return
     */
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
