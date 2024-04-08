package groupe6.model.partie.puzzle.cellule;

import groupe6.ModelTest;
import groupe6.model.partie.puzzle.TestPuzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCellule extends ModelTest {

  @BeforeAll
  public static void initAll(){
    ModelTest.afficherNomDebut(TestCellule.class);
  }

  @Test
  public void testNouvelleCellule() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(0,cotes);

    // Vérification de la valeur de la cellule
    assertEquals(0, cellule.getValeur());

    // Vérification des cotés de la cellule
    assertArrayEquals(cotes, cellule.getCotes());
  }

  @Test
  public void testGetterUnSeulCote() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(0,cotes);

    // Vérification de la valeur du coté 0
    assertEquals(ValeurCote.VIDE, cellule.getCote(0));
    // Vérification de la valeur du coté 1
    assertEquals(ValeurCote.TRAIT, cellule.getCote(1));
    // Vérification de la valeur du coté 2
    assertEquals(ValeurCote.CROIX, cellule.getCote(2));
    // Vérification de la valeur du coté 3
    assertEquals(ValeurCote.TRAIT, cellule.getCote(3));

    // Vérification du cas où un coté invalide est passé au getter
    assertThrows(IllegalArgumentException.class, () -> cellule.getCote(4));
  }

  @Test
  void testSetValeur() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(2, cotes);

    // Vérification du setter de la valeur de la cellule
    cellule.setValeur(1);
    assertEquals(1, cellule.getValeur());

    // Vérification du cas où une valeur invalide est passée au setter
    assertThrows(IllegalArgumentException.class, () -> cellule.setValeur(4));
  }

  @Test
  void testSetCotes() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(2, cotes);

    // Vérification du setter du coté 0
    cellule.setCote(0, ValeurCote.CROIX);
    assertEquals(ValeurCote.CROIX, cellule.getCote(0));
    // Vérification du setter du coté 1
    cellule.setCote(1, ValeurCote.VIDE);
    assertEquals(ValeurCote.VIDE, cellule.getCote(1));
    // Vérification du setter du coté 2
    cellule.setCote(2, ValeurCote.TRAIT);
    assertEquals(ValeurCote.TRAIT, cellule.getCote(2));
    // Vérification du setter du coté 3
    cellule.setCote(3, ValeurCote.CROIX);
    assertEquals(ValeurCote.CROIX, cellule.getCote(3));
  }

  @Test
  public void testNbTrait() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(2, cotes);

    // Vérification du nombre de coté TRAIT
    assertEquals(2, cellule.nbTrait());
  }

  @Test
  public void testMaxNbTrait() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.CROIX};
    Cellule cellule = new Cellule(3, cotes);

    // Vérification du maximum de coté TRAIT
    assertTrue(cellule.maxTrait());

    // Modification de la cellule pour qu'elle n'ait plus le maximum de coté TRAIT
    cellule.setCote(0, ValeurCote.VIDE);

    // Vérification du maximum de coté TRAIT
    assertFalse(cellule.maxTrait());
  }

  @Test
  public void testCelluleEquivalente() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(2, cotes);

    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes2 = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule2 = new Cellule(2, cotes2);

    // Vérification de l'équivalence des cellules
    assertEquals(cellule, cellule2);

    // Modification de la cellule 2 ( CROIX et VIDE equivalent )
    cellule2.setCote(0, ValeurCote.CROIX);

    // Vérification de l'équivalence des cellules
    assertEquals(cellule, cellule2);

    // Modification de la cellule 2 pour qu'elle ne soit plus équivalente
    cellule2.setCote(0, ValeurCote.TRAIT);

    // Vérification de la non équivalence des cellules
    assertNotEquals(cellule, cellule2);
  }

  @Test
  public void testBasculeTroisEtats() {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(2, cotes);

    // Bascule à trois états sur le coté 0
    ValeurCote nouvelleValeur = cellule.basculeTroisEtats(Cellule.HAUT);

    // Vérification de la nouvelle valeur
    assertEquals(ValeurCote.TRAIT, nouvelleValeur);

  }

  @Test
  public void testCoteAdjacent() {
    // Vérification des cotés adjacents
    assertEquals(Cellule.BAS,Cellule.getCoteAdjacent(Cellule.HAUT));
    assertEquals(Cellule.HAUT,Cellule.getCoteAdjacent(Cellule.BAS));
    assertEquals(Cellule.DROITE,Cellule.getCoteAdjacent(Cellule.GAUCHE));
    assertEquals(Cellule.GAUCHE,Cellule.getCoteAdjacent(Cellule.DROITE));
  }

  @Test
  public void testCloneCellule() throws CloneNotSupportedException {
    // Création d'une cellule avec la valeur 0 et les cotés [VIDE, TRAIT, CROIX, TRAIT]
    ValeurCote[] cotes = new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.CROIX, ValeurCote.TRAIT};
    Cellule cellule = new Cellule(2, cotes);

    // Clonage de la cellule
    Cellule clone = (Cellule) cellule.clone();

    // Vérification de l'équivalence des cellules
    assertEquals(cellule, clone);

    // Modification de la cellule
    cellule.setCote(0, ValeurCote.TRAIT);

    // Vérification de la non équivalence des cellules
    assertNotEquals(cellule, clone);
  }

  @AfterAll
  public static void tearDownAll() {
    ModelTest.afficherNomFin(TestCellule.class);
  }
}
