import java.util.ArrayList;

/**
 * Cette classe mod\u00e8lise un glossaire contenant la liste des techniques
 * possibles a appliquer
 * 
 */
public class Glossaire {
    /** Liste d'objet InfoTechnique */
    private ArrayList<InfoTechnique> listeTechnique;

    public Glossaire() {
        listeTechnique = new ArrayList<InfoTechnique>();
    }

    public void addTechnique(InfoTechnique info) {
        listeTechnique.add(info);
    }
}
