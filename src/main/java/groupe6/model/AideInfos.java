package groupe6.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe AideInfos qui permet de stocker les informations d'une aide de technique
 *
 * @author Nathan
 */

public class AideInfos implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final TechniqueInfos infoTechnique;
    private final ResultatTechnique resultatTechnique;
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
