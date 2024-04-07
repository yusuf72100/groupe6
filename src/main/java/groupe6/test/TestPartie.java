package groupe6.test;

import groupe6.model.partie.sauvegarde.CatalogueSauvegarde;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.sauvegarde.PartieSauvegarde;
import groupe6.model.profil.CatalogueProfil;
import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.DifficultePuzzle;

import java.util.List;

/**
 * Classe de test pour la partie
 */
public class TestPartie {

  /**
   * Méthode qui teste la création d'une nouvelle partie
   */
  public static void  creerNouvellePartie() {

    System.out.println("====================================");
    System.out.println("Création d'une nouvelle partie");
    System.out.println("====================================");

    // Chargement du catalogue de puzzle
    CataloguePuzzle cataloguePuzzle = CataloguePuzzle.chargerCataloguePuzzle();
    // Chargement du catalogue de profil
    CatalogueProfil catalogueProfil = CatalogueProfil.chargerCatalogueProfil();

    // Création d'une nouvelle partie
    Partie partie = Partie.nouvellePartie(
        cataloguePuzzle,
        DifficultePuzzle.FACILE,
        0,
        ModeJeu.NORMAL,
        catalogueProfil.getProfilActuel()
    );

    // Afiichage de la partie
    System.out.println(partie.getPuzzle());

    System.out.println("-----------------------------");
    System.out.println("Modification de la partie");
    System.out.println("-----------------------------");

    // Bascule 3 etats case 0 0
    partie.actionBasculeTroisEtat(0,0, Cellule.BAS);

    // Afiichage de la partie
    System.out.println(partie.getPuzzle());

    // Sauvegarde de la partie
    partie.sauvegarder();

  }

  /**
   * Méthode qui teste le chargement d'une partie existante
   */
  public static void chargerPartieExistante() {

    System.out.println("====================================");
    System.out.println("Chargement d'une partie existante");
    System.out.println("====================================");

    // Chargement du catalogue de puzzle
    CataloguePuzzle cataloguePuzzle = CataloguePuzzle.chargerCataloguePuzzle();
    // Chargement du catalogue de profil
    CatalogueProfil catalogueProfil = CatalogueProfil.chargerCatalogueProfil();

    // Chargement d'une partie existante
    List<String> lst = CatalogueSauvegarde.listerSauvegarde(catalogueProfil.getProfilActuel());

    for ( String nom : lst) {
      System.out.println(nom);
    }

    PartieSauvegarde pSave = PartieSauvegarde.chargerSauvegarde(lst.get(0), catalogueProfil.getProfilActuel());
    Partie p = new Partie(pSave,catalogueProfil.getProfilActuel());

    // Afiichage de la partie
    System.out.println(p.getPuzzle());



  }

  /**
   * Méthode principale
   *
   * @param args les arguments de la ligne de commande
   */
  public static void main(String[] args) {
    creerNouvellePartie();
    chargerPartieExistante();
  }
}
