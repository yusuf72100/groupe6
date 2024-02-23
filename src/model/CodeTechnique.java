package model;

import javax.swing.CellEditor;

/**
 * Cette classe mod\u00e8lise le code de detection des techniques applicables.
 * 
 * @author William Sardon
 */
public class CodeTechnique {

    public Cellule trouverCellule(int valeur) {
        for (int y = 0; y < grille.getLargeur(); y++) {
            for (int x = 0; x < grille.getLongueur(); x++) {
                Cellule c = grille.getCellule(y, x);
                if (c.getValeur() == 3) {
                    return c;
                }
            }
        }
    }

    /**
     * Technique des deux trois en diagonale
     * 
     * @param grille
     * @return
     */
    public ResultatTechnique troisDiagonale(Puzzle grille) {
        Cellule c = trouverCellule(3);

    }
}
