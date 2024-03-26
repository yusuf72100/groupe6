package groupe6.model;

/**
 * @author Nathan
 */

public class AideInfos {
    private TechniqueInfos infoTechnique;
    private ResultatTechnique resultatTechnique;
    /** Constructeur de la classe AideInfos */
    AideInfos(TechniqueInfos uneInfoTechnique, ResultatTechnique unResultatTechnique){
        this.infoTechnique=uneInfoTechnique;
        this.resultatTechnique=unResultatTechnique;
    }
    /** Getter pour infoTechnique */
    TechniqueInfos getInfoTechnique(){
        return this.infoTechnique;
    }
    /** Getter pour ResultatTechnique */
    ResultatTechnique getResultatTechnique (){
        return this.resultatTechnique;
    }

}
