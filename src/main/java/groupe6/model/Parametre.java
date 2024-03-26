package groupe6.model;
import java.io.Serializable;
import javafx.scene.input.KeyCode;

/**
 * Classe Parametre qui gère les paramètres de l'application
 * @author Tom MARSURA
 */
public class Parametre implements Serializable{
    private static final long serialVersionUID = 1L;

    private boolean aideRemplissageCroix;
    private boolean[] aideTechniqueDemarrage;
    private KeyCode toucheVide;
    private KeyCode toucheTrait;
    private KeyCode toucheCroix;

    /**
     * Constructeur de la classe Parametre
     */
    public Parametre(){
        this.aideRemplissageCroix = false;
        this.aideTechniqueDemarrage = new boolean[3];
        this.aideTechniqueDemarrage[0] = false;
        this.aideTechniqueDemarrage[1] = false;
        this.aideTechniqueDemarrage[2] = false;

        this.toucheVide = KeyCode.V;
        this.toucheTrait = KeyCode.T;
        this.toucheCroix = KeyCode.C;
    }

    /**
     * Getter de l'attribut aideRemplissage
     * @return L'aide au remplissage
     */
    public boolean getAideRemplissageCroix(){
        return aideRemplissageCroix;
    }

    /**
     * Getter de l'attribut aideCroixAuto
     * @return L'aide à la croix automatique
     */
    public boolean[] getAideTechniqueDemarrage(){
        return aideTechniqueDemarrage;
    }

    /**
     * Setter de l'attribut aideRemplissage
     * @param aideRemplissage L'aide au remplissage
     */
    public void setAideRemplissageCroix(boolean aideRemplissage){
        this.aideRemplissageCroix = aideRemplissage;
    }

    /**
     * Setter de l'attribut toucheVide
     * @param toucheVide La touche pour les vides
     */
    public void setToucheVide(KeyCode toucheVide){
        this.toucheVide = toucheVide;
    }

    /**
     * Setter de l'attribut toucheTrait
     * @param toucheTrait La touche pour les traits
     */
    public void setToucheTrait(KeyCode toucheTrait){
        this.toucheTrait = toucheTrait;
    }

    /**
     * Setter de l'attribut toucheCroix
     * @param toucheCroix La touche pour les croix
     */
    public void setToucheCroix(KeyCode toucheCroix){
        this.toucheCroix = toucheCroix;
    }

    /**
     * Getter de l'attribut toucheVide
     * @return La touche pour les vides
     */
    public KeyCode getToucheVide(){
        return toucheVide;
    }

    /**
     * Getter de l'attribut toucheTrait
     * @return La touche pour les traits
     */
    public KeyCode getToucheTrait(){
        return toucheTrait;
    }

    /**
     * Getter de l'attribut toucheCroix
     * @return La touche pour les croix
     */
    public KeyCode getToucheCroix(){
        return toucheCroix;
    }

    /**
     * Setter de l'attribut aideTechniqueDemarrage
     * @param aideTechniqueDemmarage L'aide d'application auto des techniques de démarrage
     * @param difficule Difficulte du puzzle où appliquer l'aide
     */
    public void setAideTechniqueDemarrage (boolean aideTechniqueDemarrage, DifficultePuzzle difficulte){
        this.aideTechniqueDemarrage[difficulte.ordinal()] = aideTechniqueDemarrage;
    }

    /**
     * Méthode qui permet d'afficher les paramètres
     */
    public String toString(){
        return "Aide au remplissage de la croix : " + this.aideRemplissageCroix + "\n" +
        "Aide technique au démarrage : " + this.aideTechniqueDemarrage + "\n" +
        "Touche pour les vides : " + this.toucheVide + "\n" +
        "Touche pour les traits : " + this.toucheTrait + "\n" +
        "Touche pour les croix : " + this.toucheCroix + "\n";
    }
}
