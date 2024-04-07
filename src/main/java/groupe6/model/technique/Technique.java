package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite qui représente une technique
 *
 * @author Nathan
 */
public abstract class Technique{

    /**
     * Booleen vrai si les cases concerné par la détection sont positionné de manière adjacente.
     */
    protected static final boolean ADJ = true;

    /**
     * TODO
     */
    protected static final boolean DIAG = false;

    /**
     * TODO
     */
    public static final boolean HORIZ = false;

    /**
     * TODO
     */
    public static final boolean VERTI = true;

    /**
     * La difficulté de la technique
     */
    private final DifficulteTechnique difficulte;

    /**
     * Constructeur de la classe Technique
     *
     * @param uneDifficulte la difficulté de la technique
     */
    public Technique(DifficulteTechnique uneDifficulte){
        this.difficulte = uneDifficulte;
    }

    /**
     * Méthode abstraite qui permet de lancer recherche de la technique
     *
     * @param partie la partie sur laquelle on cherche la technique
     * @param idx l'index de la technique dans la liste des techniques
     * @return le résultat de la technique
     */
    public abstract ResultatTechnique run(Partie partie, int idx);

    /**
     * Méthode pour obtenir la difficulté de la technique
     *
     * @return la difficulté de la technique
     */
    public DifficulteTechnique getDifficulte() {
        return difficulte;
    }

    /**
     * Méthode qui permet de rechercher un numéro dans une grille
     *
     * @param uneGrille la grille dans laquelle on cherche le numéro
     * @param unNumero le numéro que l'on cherche
     * @return la liste des coordonnées où le numéro a été trouvé
     */
    public static List<Coordonnee> rechercherNumero(Puzzle uneGrille, int unNumero) {
        List<Coordonnee> coord = new ArrayList<>();
        for(int y = 0; y < uneGrille.getLargeur(); y++){
            for(int x = 0; x < uneGrille.getLongueur(); x++){
                Cellule courante = uneGrille.getCellule(y,x);
                if(courante.getValeur() == unNumero){
                    coord.add(new Coordonnee(y,x));
                }
            }
        }
        return coord;
    }

    /**
     * TODO
     *
     * @param uneGrille TODO
     * @param unNumero TODO
     * @param y TODO
     * @param x TODO
     * @return TODO
     */
    private static List<Coordonnee> rechercherNumeroDiagonal(Puzzle uneGrille, int unNumero, int y, int x) {
        List<Coordonnee> diagonal = new ArrayList<>();
        if(uneGrille.getCellule(y-1,x-1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y-1,x-1));
        }
        if(uneGrille.getCellule(y+1,x-1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y+1,x-1));
        }
        if(uneGrille.getCellule(y+1,x+1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y+1,x+1));
        }
        if(uneGrille.getCellule(y-1,x+1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y-1,x+1));
        }
        return diagonal;

    }

    /**
     * TODO
     *
     * @param uneGrille TODO
     * @param unNumero TODO
     * @param y TODO
     * @param x TODO
     * @return TODO
     */
    private static List<Coordonnee> rechercherNumeroAdjacent(Puzzle uneGrille, int unNumero, int y, int x) {
        List<Coordonnee> adjacent = new ArrayList<>();
        if(uneGrille.getCellule(y,x-1).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y,x-1));
        }
        if(uneGrille.getCellule(y+1,x).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y+1,x));
        }
        if(uneGrille.getCellule(y,x+1).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y,x+1));
        }
        if(uneGrille.getCellule(y-1,x).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y-1,x));
        }
        return adjacent;
    }

    /**
     * TODO
     *
     * @param uneGrille TODO
     * @param unNumero TODO
     * @param unePosition TODO
     * @param direction TODO
     * @return TODO
     */
    public static List<Coordonnee> rechercherNumeroAutour(Puzzle uneGrille, int unNumero, Coordonnee unePosition, Direction direction) {
        List<Coordonnee> coord = new ArrayList<>();
        int y = unePosition.getY();
        int x = unePosition.getX();
        if(direction == Direction.ADJACENT){
            coord.addAll(rechercherNumeroAdjacent(uneGrille, unNumero, y, x));
        }else if(direction == Direction.DIAGONAL){
            coord.addAll(rechercherNumeroDiagonal(uneGrille, unNumero, y, x));
        }else {
            coord.addAll(rechercherNumeroAdjacent(uneGrille, unNumero, y, x));
            coord.addAll(rechercherNumeroDiagonal(uneGrille, unNumero, y, x));
        }
        return coord;
    }

    /**
     * TODO
     *
     * @param uneGrille TODO
     * @param unePosition TODO
     * @return TODO
     */
    public boolean rechercherBoucleAutour(Puzzle uneGrille, Coordonnee unePosition) {
        int positionY = unePosition.getY();
        int positionX = unePosition.getX();
        Cellule hautGauche = uneGrille.getCellule(positionY-1,positionX-1);
        Cellule hautDroit = uneGrille.getCellule(positionY+1,positionX-1);
        Cellule basGauche = uneGrille.getCellule(positionY-1,positionX+1);
        Cellule basDroit = uneGrille.getCellule(positionY+1,positionX+1);
        if(hautGauche.getCote(Cellule.DROITE) == ValeurCote.TRAIT || hautGauche.getCote(Cellule.BAS) == ValeurCote.TRAIT) return true;
        if(hautDroit.getCote(Cellule.GAUCHE) == ValeurCote.TRAIT || hautDroit.getCote(Cellule.BAS) == ValeurCote.TRAIT) return true;
        if(basGauche.getCote(Cellule.DROITE) == ValeurCote.TRAIT || basGauche.getCote(Cellule.HAUT) == ValeurCote.TRAIT) return true;
        if(basDroit.getCote(Cellule.GAUCHE) == ValeurCote.TRAIT || basDroit.getCote(Cellule.HAUT) == ValeurCote.TRAIT) return true;
        return false;
    }

    /**
     * TODO
     *
     * @param grille TODO
     * @param listeCoordonnee TODO
     * @param adjacent TODO
     * @param sens TODO
     * @return TODO
     */
    public static boolean verificationMultidir(Puzzle grille, List<Coordonnee> listeCoordonnee, boolean adjacent, boolean sens) {
        Coordonnee coordonneeA = listeCoordonnee.get(0);
        Coordonnee coordonneeB = listeCoordonnee.get(1);
        int yA = coordonneeA.getY(), yB = coordonneeB.getY();
        int xA = coordonneeA.getX(), xB = coordonneeB.getX();
        Cellule celluleA = grille.getCellule(yA,xA);
        Cellule celluleB = grille.getCellule(yB,xB);

        if(adjacent){
            if(sens == VERTI){
                Cellule cellule1 = (xA < xB ? celluleA : celluleB);
                Cellule cellule2 = (xA < xB ? celluleB : celluleA);
                return (
                    cellule1.getCote(Cellule.BAS) != ValeurCote.TRAIT ||
                    cellule1.getCote(Cellule.HAUT) != ValeurCote.TRAIT ||
                    cellule2.getCote(Cellule.HAUT) != ValeurCote.TRAIT
                );
            }else{
                Cellule cellule1 = (yA < yB ? celluleA : celluleB);
                Cellule cellule2 = (yA < yB ? celluleB : celluleA);
                return (
                    cellule1.getCote(Cellule.GAUCHE) != ValeurCote.TRAIT ||
                    cellule1.getCote(Cellule.DROITE) != ValeurCote.TRAIT ||
                    cellule2.getCote(Cellule.DROITE) != ValeurCote.TRAIT
                ); //HORIZ
            }
        }else{
            Cellule cellule1 = (xA < xB ? celluleA : celluleB);
            Cellule cellule2 = (xA < xB ? celluleB : celluleA);
            if((yA+xA) == (yB+xB)){
                return (
                    cellule1.getCote(Cellule.BAS) != ValeurCote.TRAIT ||
                        cellule1.getCote(Cellule.GAUCHE) != ValeurCote.TRAIT ||
                        cellule2.getCote(Cellule.HAUT) != ValeurCote.TRAIT ||
                        cellule2.getCote(Cellule.DROITE) != ValeurCote.TRAIT
                );
            }else{
                return (
                    cellule1.getCote(Cellule.BAS) != ValeurCote.TRAIT ||
                        cellule1.getCote(Cellule.DROITE) != ValeurCote.TRAIT ||
                        cellule2.getCote(Cellule.GAUCHE) != ValeurCote.TRAIT ||
                        cellule2.getCote(Cellule.HAUT) != ValeurCote.TRAIT
                ); //HORIZ
            }
        }
    }
}