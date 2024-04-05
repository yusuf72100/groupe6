package groupe6.model.technique;

import groupe6.model.technique.TechniqueInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe modélise un glossaire contenant la liste des informations
 * sur les techniques.
 * 
 * @author William Sardon
 */
public class Glossaire {

    /**
     * La listes des informations sur les techniques
     */
    private final List<TechniqueInfos> listeInfoTechnique;

    /**
     * Constructeur de la classe Glossaire
     */
    public Glossaire() {
        listeInfoTechnique = new ArrayList<TechniqueInfos>();
    }

    /**
     * Méthode pour obtenir la liste des informations sur les techniques
     *
     * @return la liste des informations sur les techniques
     */
    public List<TechniqueInfos> getInfoTechnique() {
        return listeInfoTechnique;
    }

    /**
     * Méthode pour ajouter une information sur une technique à la liste
     *
     * @param info l'information sur une technique à ajouter
     */
    public void ajouterInfoTechnique(TechniqueInfos info) {
        listeInfoTechnique.add(info);
    }
}
