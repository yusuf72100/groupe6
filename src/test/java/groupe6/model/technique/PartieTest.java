package groupe6.model.technique;

import groupe6.ModelTest;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.*;
import groupe6.model.profil.Profil;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

public abstract class PartieTest{

    //Constructeur privé de PartieTest
    public static Partie getPartieTest(){

        Cellule[][] grilleCellules = new Cellule[6][6];
        grilleCellules[0][0] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][2] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][3] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][5] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[1][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[2][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][2] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][5] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[3][0] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][4] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[4][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[5][0] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][2] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][3] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][5] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        
        Profil profil = new Profil("Test",null);
        Puzzle puzzle = new Puzzle(6, 6, grilleCellules, grilleCellules, DifficultePuzzle.FACILE);
        
        return new Partie(puzzle,ModeJeu.CLASSIQUE,profil);
    }


}