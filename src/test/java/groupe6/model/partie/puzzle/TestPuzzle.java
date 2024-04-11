package groupe6.model.partie.puzzle;

import groupe6.ModelTest;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPuzzle extends ModelTest {

  /**
   * Le puzzle sur lequel les tests sont effectués
   */
  private static Puzzle puzzle;

  @BeforeAll
  public static void initAll(){
    ModelTest.afficherNomDebut(TestPuzzle.class);

    int largeur = 3;
    int longueur = 4;

    Cellule[][] grilleCellules = new Cellule[largeur][longueur];
                                                                  // Haut ,        Bas,             Gauche,           Droite
    grilleCellules[0][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
    grilleCellules[0][1] = new Cellule(2, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.TRAIT});
    grilleCellules[0][2] = new Cellule(3, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.TRAIT});
    grilleCellules[0][3] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE});

    grilleCellules[1][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT});
    grilleCellules[1][1] = new Cellule(3, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.VIDE});
    grilleCellules[1][2] = new Cellule(2, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.TRAIT});
    grilleCellules[1][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE});

    grilleCellules[2][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
    grilleCellules[2][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
    grilleCellules[2][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
    grilleCellules[2][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

    TestPuzzle.puzzle = new Puzzle(largeur, longueur, grilleCellules, grilleCellules, DifficultePuzzle.FACILE);
  }

  @Test
  public void testGetTaille(){
    assertEquals(3, TestPuzzle.puzzle.getLargeur());
    assertEquals(4, TestPuzzle.puzzle.getLongueur());
  }

  @Test
  public void testEstComplet() throws CloneNotSupportedException {
    // Création d'une copie du puzzle
    Puzzle copy = (Puzzle) TestPuzzle.puzzle.clone();

    // Vérification que le puzzle est complet
    assertTrue(copy.estComplet());

    // Transformation d'une cellule vide en cellule avec croix
    copy.getCellule(0,0).setCote(Cellule.BAS, ValeurCote.CROIX);

    // Vérification que le puzzle est toujours complet
    assertTrue(copy.estComplet());

    // Modification d'un trait
    copy.getCellule(0,1).setCote(Cellule.BAS, ValeurCote.VIDE);

    // Vérification que le puzzle n'est plus complet
    assertFalse(copy.estComplet());
  }

  @Test
  public void testEstDansGrille() {
    assertTrue(TestPuzzle.puzzle.estDansGrille(0,0));
    assertTrue(TestPuzzle.puzzle.estDansGrille(2,3));
    assertFalse(TestPuzzle.puzzle.estDansGrille(-1,0));
    assertFalse(TestPuzzle.puzzle.estDansGrille(0,-1));
    assertFalse(TestPuzzle.puzzle.estDansGrille(3,0));
    assertFalse(TestPuzzle.puzzle.estDansGrille(0,4));
  }

  @Test
  public void testGetCellule() {
    assertEquals(-1, TestPuzzle.puzzle.getCellule(0,0).getValeur());
    assertEquals(2, TestPuzzle.puzzle.getCellule(0,1).getValeur());
    assertEquals(3, TestPuzzle.puzzle.getCellule(0,2).getValeur());
    assertEquals(1, TestPuzzle.puzzle.getCellule(0,3).getValeur());
  }

  @Test
  public void testGetCelluleAdjacente() {
    assertEquals(2, TestPuzzle.puzzle.getCelluleAdjacente(0,0, Cellule.DROITE).getValeur());
    assertEquals(3, TestPuzzle.puzzle.getCelluleAdjacente(0,1, Cellule.BAS).getValeur());
    assertEquals(3, TestPuzzle.puzzle.getCelluleAdjacente(0,3, Cellule.GAUCHE).getValeur());
    assertEquals(2, TestPuzzle.puzzle.getCelluleAdjacente(2,2, Cellule.HAUT).getValeur());
  }

  @Test
  public void testClonePuzzle() throws CloneNotSupportedException {
    // Création d'une copie du puzzle
    Puzzle copy = (Puzzle) TestPuzzle.puzzle.clone();

    // Vérification que les informations du puzzle sont identiques
    assertEquals(TestPuzzle.puzzle.getLargeur(), copy.getLargeur());
    assertEquals(TestPuzzle.puzzle.getLongueur(), copy.getLongueur());
    assertEquals(TestPuzzle.puzzle.getDifficulte(), copy.getDifficulte());

    // Vérification que les cellules sont identiques
    for(int i = 0; i < TestPuzzle.puzzle.getLargeur(); i++){
      for(int j = 0; j < TestPuzzle.puzzle.getLongueur(); j++){
        assertEquals(TestPuzzle.puzzle.getCellule(i,j), copy.getCellule(i,j));
      }
    }

  }

  @AfterAll
  public static void tearDownAll() {
      ModelTest.afficherNomFin(TestPuzzle.class);
  }


}
