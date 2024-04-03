package groupe6.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nathan
 */
public abstract class Technique{

    /**
     * Booleen vrai si les cases concerné par la détection sont positionné de manière adjacente.
     */
    protected static final boolean ADJ = true;
    protected static final boolean DIAG = false;
    public static final boolean HORIZ = false;
    public static final boolean VERTI = true;


    private final DifficulteTechnique difficulte;
    private final int ordre;
    Technique(DifficulteTechnique uneDifficulte, int unOrdre){
        this.difficulte = uneDifficulte;
        this.ordre = unOrdre;
    }

    public abstract ResultatTechnique run(Partie partie);

    public int getOrdre() {
        return ordre;
    }

    public DifficulteTechnique getDifficulte() {
        return difficulte;
    }

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