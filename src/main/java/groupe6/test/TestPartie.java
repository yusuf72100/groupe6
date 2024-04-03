package groupe6.test;

import groupe6.model.*;

import java.util.List;

public class TestPartie {

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
    partie.actionBasculeTroisEtat(0,0,Cellule.BAS);

    // Afiichage de la partie
    System.out.println(partie.getPuzzle());

    // Sauvegarde de la partie
    partie.sauvegarder();

  }

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

  public static void main(String[] args) {
    creerNouvellePartie();
    chargerPartieExistante();
  }
}