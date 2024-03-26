package groupe6.test;

import groupe6.model.CatalogueProfil;
import groupe6.model.Profil;

/**
 * Classe de test pour les profils
 *
 * @author Yamis
 */
public class TestProfils {

  public static void creerNouveauxProfils() {
    System.out.println("===================================");
    System.out.println("Création de nouveaux profils");
    System.out.println("===================================");


    Profil yamis = new Profil("yamis",null);
    yamis.choisirImage();

    Profil utilisateur = new Profil("utilisateur",null);
    utilisateur.choisirImage();

    CatalogueProfil catalogueProfil = new CatalogueProfil();
    catalogueProfil.ajouterProfil(yamis);
    catalogueProfil.ajouterProfil(utilisateur);
    catalogueProfil.setProfilActuel(utilisateur);

    System.out.println(catalogueProfil);

    Profil.sauvegarderProfil(yamis);
    Profil.sauvegarderProfil(utilisateur);

    CatalogueProfil.sauvegarderProfilActuel(catalogueProfil);

  }

  public static void chargerProfilsExistant() {
    System.out.println("===================================");
    System.out.println("Chargement des profils existants");
    System.out.println("===================================");


    CatalogueProfil catalogueProfil = CatalogueProfil.chargerCatalogueProfil();
    System.out.println(catalogueProfil);
  }

  public static void main(String[] args) {
    creerNouveauxProfils();
    chargerProfilsExistant();
  }
}
