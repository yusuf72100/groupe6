package groupe6.test;

import groupe6.model.CatalogueProfil;
import groupe6.model.Profil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * Classe de test pour les profils
 *
 * @author Yamis
 */
public class TestProfils extends Application {
  private static Stage fenetre;
  @Override
  public void start(Stage primary) throws Exception {
    Button bouton = new Button("Charger une image");
    // Création du gestionnaire d'événements (handler)
    bouton.setOnAction( new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
          try {
            creerNouveauxProfils(fenetre);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
      }
    });

    // gestion de la scène
    fenetre = primary;
    VBox root = new VBox();
    root.getChildren().add(bouton);
    Scene Main = new Scene(root, 800, 800);

    // gestion de la fenêtre
    primary.initStyle(StageStyle.DECORATED);
    primary.setScene(Main);
    primary.setTitle("Test Profils");
    primary.setResizable(true);
    primary.setMaximized(true);
    primary.show();
  }

  public static void creerNouveauxProfils(Stage fenetre) throws IOException {
    System.out.println("===================================");
    System.out.println("Création de nouveaux profils");
    System.out.println("===================================");

    Profil yamis = new Profil("yamis",null);
    yamis.choisirImage(fenetre);

    Profil utilisateur = new Profil("utilisateur",null);
    utilisateur.choisirImage(fenetre);

    CatalogueProfil catalogueProfil = new CatalogueProfil();
    catalogueProfil.ajouterProfil(yamis);
    catalogueProfil.ajouterProfil(utilisateur);
    catalogueProfil.setProfilActuel(utilisateur);

    System.out.println(catalogueProfil);

    Profil.sauvegarderProfil(yamis);
    Profil.sauvegarderProfil(utilisateur);

    CatalogueProfil.sauvegarderProfilActuel(catalogueProfil);
    chargerProfilsExistant();
  }

  public static void chargerProfilsExistant() {
    System.out.println("===================================");
    System.out.println("Chargement des profils existants");
    System.out.println("===================================");

    CatalogueProfil catalogueProfil = CatalogueProfil.chargerCatalogueProfil();
    System.out.println(catalogueProfil);
  }

  public static void main(String[] args) {
    launch();
  }
}
