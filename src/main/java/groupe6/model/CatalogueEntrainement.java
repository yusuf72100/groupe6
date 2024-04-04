package groupe6.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe modélise un catalogue d'entrainement.
 * 
 * @author William Sardon
 */
public class CatalogueEntrainement {

    /**
     * Liste des entrainements dans le catalogue
     */
    private final List<Entrainement> listeEntrainement;

    /**
     * Constructeur de la classe CatalogueEntrainement
     */
    public CatalogueEntrainement() {
        this.listeEntrainement = new ArrayList<Entrainement>();
    }

    /**
     * Méthode pour obtenir la liste des entrainements
     *
     * @return la liste des entrainements
     */
    public List<Entrainement> getListeEntrainement() {
        return listeEntrainement;
    }

    /**
     * Méthode pour ajouter un entrainement au catalogue
     *
     * @param entrainement l'entrainement à ajouter
     */
    public void ajouterEntrainement(Entrainement entrainement) {
        listeEntrainement.add(entrainement);
    }

    /**
     * Méthode statique pour charger un catalogue d'entrainement
     *
     * @return le catalogue d'entrainement chargé
     */
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
