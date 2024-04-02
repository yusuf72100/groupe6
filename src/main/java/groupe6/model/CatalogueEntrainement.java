package groupe6.model;

import groupe6.launcher.Launcher;
import groupe6.model.Entrainement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Cette classe modélise un catalogue d'entrainement.
 * 
 * @author William Sardon
 */
public class CatalogueEntrainement {
    private List<Entrainement> listeEntrainement;

    // Constructeur de la classe CatalogueEntrainement
    public CatalogueEntrainement() {
        this.listeEntrainement = new ArrayList<Entrainement>();
    }

    // Méthode pour obtenir la liste des entrainements
    public List<Entrainement> getListeEntrainement() {
        return listeEntrainement;
    }

    // Méthode pour ajouter un entrainement
    public void ajouterEntrainement(Entrainement entrainement) {
        listeEntrainement.add(entrainement);
    }

    // Méthode statique pour charger le catalogue d'entrainement
    public static CatalogueEntrainement chargerCatalogueEntrainement() {
        throw new UnsupportedOperationException("Not implemented yet");

//        CatalogueEntrainement catalogue = new CatalogueEntrainement();
//
//        String cheminRessourcesEntrainements = Launcher.normaliserChemin("Slitherlink/entrainements");
//        File pathDossier = new File(cheminRessourcesEntrainements);
//        String[] contenu = pathDossier.list();
//
//        if (contenu != null) {
//            for (String s : contenu) {
//                if (s.endsWith(".entrainement")) {
//                    // Charger l'entrainement
//                    Entrainement entrainement = null;
//                    // Ajouter l'entrainement au catalogue
//                    catalogue.ajouterEntrainement(entrainement);
//                }
//            }
//        }
//
//        return catalogue;
    }
}
