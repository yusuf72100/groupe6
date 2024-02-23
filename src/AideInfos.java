/**
 * @author Nathan
 */

public class AideInfos {
    private InfoTechnique infoTechnique;
    private ResultatTechnique resultatTechnique;
    /** COnstructeur de la classe AideInfos */
    AideInfos(InfoTechnique uneInfoTechnique, ResultatTechnique unResultatTechnique){
        this.infoTechnique=uneInfoTechnique;
        this.resultatTechnique=unResultatTechnique;
    }

    InfoTechnique getInfoTechnique(){
        return this.infoTechnique;
    }

    ResultatTechnique getResultatTechnique (){
        return this.resultatTechnique;
    }

}
