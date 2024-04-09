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
     * Informations sur la technique utilisée
     */
    private final TechniqueInfos infoTechnique;

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
     * @param uneInfoTechnique l'information sur la technique
     * @param unResultatTechnique le résultat de la technique
     */
    public AideInfos(TechniqueInfos uneInfoTechnique, ResultatTechnique unResultatTechnique){
        this.infoTechnique=uneInfoTechnique;
        this.resultatTechnique=unResultatTechnique;
        this.niveau=1;
    }

    /**
     * Méthode pour obtenir les informations sur la technique
     *
     * @return les informations sur la technique
     */
    TechniqueInfos getInfoTechnique(){
        return this.infoTechnique;
    }

    /**
     * Méthode pour obtenir le résultat de la technique
     *
     * @return le résultat de la technique
     */
    ResultatTechnique getResultatTechnique (){
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

}
