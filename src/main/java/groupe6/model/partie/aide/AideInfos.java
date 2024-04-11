package groupe6.model.partie.aide;

import groupe6.model.technique.ResultatTechnique;
import groupe6.model.technique.TechniqueInfos;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe AideInfos qui permet de stocker les informations d'une aide de technique
 *
 * @author Nathan
 */
public class AideInfos implements Serializable {

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Résultat de la technique utilisée
     */
    private final ResultatTechnique resultatTechnique;

    /**
     * Le niveau actuel de l'aide
     */
    private int niveau;

    /**
     * Constructeur de la classe AideInfos
     *
     * @param unResultatTechnique le résultat de la technique
     */
    public AideInfos(ResultatTechnique unResultatTechnique){
        this.resultatTechnique=unResultatTechnique;
        this.niveau=1;
    }

    /**
     * Méthode pour obtenir le résultat de la technique
     *
     * @return le résultat de la technique
     */
    public ResultatTechnique getResultatTechnique (){
        return this.resultatTechnique;
    }

    /**
     * Méthode pour obtenir le niveau de l'aide
     *
     * @return le niveau de l'aide
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Méthode pour augmenter une aide de niveau 1 en niveau 2
     */
    public void upgradeNiveau(){
        this.niveau = 2;
    }

    /**
     * Méthode pour obtenir une représentation textuelle d'une aide
     *
     * @return la représentation textuelle de l'aide
     */
    @Override
    public String toString(){
        return "{niveau="+this.niveau+", "+this.resultatTechnique.toString()+"}";
    }

}
