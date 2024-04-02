package groupe6.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe modélise un glossaire contenant la liste des informations
 * sur les techniques.
 * 
 * @author William Sardon
 */
public class Glossaire {
    /** Liste d'objet InfoTechnique */
    private final List<TechniqueInfos> listeInfoTechnique;

    public Glossaire() {
        listeInfoTechnique = new ArrayList<TechniqueInfos>();
    }

    public List<TechniqueInfos> getInfoTechnique() {
        return listeInfoTechnique;
    }

    public void ajouterInfoTechnique(TechniqueInfos info) {
        listeInfoTechnique.add(info);
    }
}
