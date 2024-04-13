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
 * @author Nathan, Mateo
 */
public abstract class Technique{

    /**
     * Booleen vrai s'il y a un 0 dans les cases concernées par la verification.
     */
    protected static final boolean ZERO = true;

    /**
     * Booleen vrai s'il y a un 3 dans les cases concernées par la verification.
     */
    protected static final boolean TROIS = false;

    /**
     * La difficulté de la technique
     */
    protected final DifficulteTechnique difficulte;

    /**
     * Nom de la classe de la technique
     */
    protected final String nomTechnique;

    /**
     * Nom stylisé de la technique
     */
    protected final String nomTechniqueStylise;

    /**
     * Constructeur de la classe Technique
     *
     * @param uneDifficulte la difficulté de la technique
     * @param nomTechniqueStylise le nom stylisé de la technique
     */
    public Technique(DifficulteTechnique uneDifficulte, String nomTechniqueStylise){
        this.difficulte = uneDifficulte;
        this.nomTechnique = this.getClass().getSimpleName();
        this.nomTechniqueStylise = nomTechniqueStylise;
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
     * Méthode pour obtenir le nom de la technique
     *
     * @return le nom de la technique
     */
    public String getNomTechnique() {
        return nomTechnique;
    }

    /**
     * Méthode pour obtenir le nom stylisé de la technique
     *
     * @return le nom stylisé de la technique
     */
    public String getNomTechniqueStylise() {
        return nomTechniqueStylise;
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
     * Recherche d'un numero en diagonal, cette méthode connait :
     *
     * @param uneGrille La grille dans laquelle on cherche le numéro.
     * @param unNumero Le numéro qu'il faut rechercher.
     * @param y La coordonnée en y de la position autour de laquelle chercher.
     * @param x La coordonnée en x de la position autour de laquelle chercher.
     * @return Une liste contenant les coordonnées de toutes les cellules diagonales dans lesquelles unNumero a été trouvé.
     */
    private static List<Coordonnee> rechercherNumeroDiagonal(Puzzle uneGrille, int unNumero, int y, int x) {
        List<Coordonnee> diagonal = new ArrayList<>();
        if(uneGrille.estDansGrille(y-1,x-1) && uneGrille.getCellule(y-1,x-1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y-1,x-1));
        }
        if(uneGrille.estDansGrille(y+1,x-1) && uneGrille.getCellule(y+1,x-1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y+1,x-1));
        }
        if(uneGrille.estDansGrille(y+1,x+1) && uneGrille.getCellule(y+1,x+1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y+1,x+1));
        }
        if(uneGrille.estDansGrille(y-1,x+1) && uneGrille.getCellule(y-1,x+1).getValeur() == unNumero){
            diagonal.add(new Coordonnee(y-1,x+1));
        }
        return diagonal;

    }

    /**
     * Recherche d'un numero adjacent, cette méthode connait :
     *
     * @param uneGrille La grille dans laquelle on cherche le numéro.
     * @param unNumero Le numéro qu'il faut rechercher.
     * @param y La coordonnée en y de la position autour de laquelle chercher.
     * @param x La coordonnée en x de la position autour de laquelle chercher.
     * @return Une liste contenant les coordonnées de toutes les cellules adjacentes dans lesquelles unNumero a été trouvé.
     */
    private static List<Coordonnee> rechercherNumeroAdjacent(Puzzle uneGrille, int unNumero, int y, int x) {
        List<Coordonnee> adjacent = new ArrayList<>();
        if(uneGrille.estDansGrille(y,x-1) && uneGrille.getCellule(y,x-1).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y,x-1));
        }
        if(uneGrille.estDansGrille(y+1,x) && uneGrille.getCellule(y+1,x).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y+1,x));
        }
        if(uneGrille.estDansGrille(y,x+1) && uneGrille.getCellule(y,x+1).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y,x+1));
        }
        if(uneGrille.estDansGrille(y-1,x) && uneGrille.getCellule(y-1,x).getValeur() == unNumero){
            adjacent.add(new Coordonnee(y-1,x));
        }
        return adjacent;
    }

    /**
     * Recherche d'un numero en diagonal, cette méthode connait :
     *
     * @param uneGrille La grille dans laquelle on cherche le numéro.
     * @param unNumero Le numéro qu'il faut rechercher.
     * @param unePosition Les coordonées de la position autour de laquelle chercher.
     * @param direction Une précision sur la recherche (uniquement en diagonal, adjacent ou les deux)
     * @return Une liste dans laquelle ont été concaténé les listes de coordonnées soit diagonale, adjacente ou bien les deux.
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
     * Recherche d'une extremité de boucle autour d'une cellule
     *
     * @param uneGrille Une grille dans laquelle on recherche la boucle.
     * @param unePosition Une position autour de laquelle on cherche une extremité de boucle.
     * @return Renvoie vrai si on trouve une boucle sur un côté de cellule rejoignant un coin de la cellule sur unPosition.
     */
    public boolean rechercherBoucleAutour(Puzzle uneGrille, Coordonnee unePosition) {
        int positionY = unePosition.getY();
        int positionX = unePosition.getX();
        Cellule hautGauche = null;
        Cellule hautDroit = null;
        Cellule basGauche = null;
        Cellule basDroit = null;

        if(uneGrille.estDansGrille(positionY-1,positionX-1))hautGauche = uneGrille.getCellule(positionY-1,positionX-1);
        if(uneGrille.estDansGrille(positionY+1,positionX-1))hautDroit = uneGrille.getCellule(positionY+1,positionX-1);
        if(uneGrille.estDansGrille(positionY-1,positionX+1))basGauche = uneGrille.getCellule(positionY-1,positionX+1);
        if(uneGrille.estDansGrille(positionY+1,positionX+1))basDroit = uneGrille.getCellule(positionY+1,positionX+1);

        if(hautGauche != null && (hautGauche.getCote(Cellule.DROITE) == ValeurCote.TRAIT || hautGauche.getCote(Cellule.BAS) == ValeurCote.TRAIT)) return true;
        if(hautDroit != null && (hautDroit.getCote(Cellule.GAUCHE) == ValeurCote.TRAIT || hautDroit.getCote(Cellule.BAS) == ValeurCote.TRAIT)) return true;
        if(basGauche != null && (basGauche.getCote(Cellule.DROITE) == ValeurCote.TRAIT || basGauche.getCote(Cellule.HAUT) == ValeurCote.TRAIT)) return true;
        if(basDroit != null && (basDroit.getCote(Cellule.GAUCHE) == ValeurCote.TRAIT || basDroit.getCote(Cellule.HAUT) == ValeurCote.TRAIT)) return true;
        return false;
    }

    /**
     * Vérification de la completion de plusieurs schemas d'aide
     *
     * @param grille La grille dans laquelle rechercher.
     * @param listeCoordonnee La liste des coordonées des cellules concernées par la vérification.
     * @param zero Booleen servant à informer si les cellules sont positionnées de manière zero.
     * @return Renvoie vrai si les schemas d'aides n'ont pas été completés.
     */
    public static boolean verificationMultidir(Puzzle grille, List<Coordonnee> listeCoordonnee, boolean zero) {

        if(zero){
            Coordonnee coordonnee3 = listeCoordonnee.get(0);
            Coordonnee coordonnee0 = listeCoordonnee.get(1);
            int y3 = coordonnee3.getY(), y0 = coordonnee0.getY();
            int x3 = coordonnee3.getX(), x0 = coordonnee0.getX();
            Cellule cellule3 = grille.getCellule(y3,x3);
            Cellule cellule0 = grille.getCellule(y0,x0);

            boolean montant = (y3+x3) == (y0+x0);

            if(y3 < y0) {
                if(montant){
                    return( cellule3.getCote(Cellule.BAS) != ValeurCote.TRAIT ||
                            cellule3.getCote(Cellule.GAUCHE) != ValeurCote.TRAIT);
                }else{
                    return( cellule3.getCote(Cellule.BAS) != ValeurCote.TRAIT ||
                            cellule3.getCote(Cellule.DROITE) != ValeurCote.TRAIT);
                }
            }else {
                if(montant){
                    return( cellule3.getCote(Cellule.HAUT) != ValeurCote.TRAIT ||
                            cellule3.getCote(Cellule.DROITE) != ValeurCote.TRAIT);
                }else{
                    return( cellule3.getCote(Cellule.HAUT) != ValeurCote.TRAIT ||
                            cellule3.getCote(Cellule.GAUCHE) != ValeurCote.TRAIT);
                }
            }
        }else{

            Coordonnee coordonneeA = listeCoordonnee.get(0);
            Coordonnee coordonneeB = listeCoordonnee.get(1);
            int yA = coordonneeA.getY(), yB = coordonneeB.getY();
            int xA = coordonneeA.getX(), xB = coordonneeB.getX();
            Cellule celluleA = grille.getCellule(yA,xA);
            Cellule celluleB = grille.getCellule(yB,xB);

            boolean adjacent = (xA == xB || yA == yB);
            if(adjacent){
                if(yA == yB){
                    Cellule cellule1 = (xA < xB ? celluleA : celluleB);
                    Cellule cellule2 = (xA < xB ? celluleB : celluleA);
                    return (
                        cellule1.getCote(Cellule.GAUCHE) != ValeurCote.TRAIT ||
                            cellule1.getCote(Cellule.DROITE) != ValeurCote.TRAIT ||
                            cellule2.getCote(Cellule.DROITE) != ValeurCote.TRAIT
                    );
                }else{
                    Cellule cellule1 = (yA < yB ? celluleA : celluleB);
                    Cellule cellule2 = (yA < yB ? celluleB : celluleA);
                    return (
                        cellule1.getCote(Cellule.HAUT) != ValeurCote.TRAIT ||
                            cellule1.getCote(Cellule.BAS) != ValeurCote.TRAIT ||
                            cellule2.getCote(Cellule.BAS) != ValeurCote.TRAIT
                    );
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
                    );
                }
            }
        }
    }
}