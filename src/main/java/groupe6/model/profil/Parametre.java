package groupe6.model.profil;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

import groupe6.model.partie.puzzle.DifficultePuzzle;
import javafx.scene.input.KeyCode;

/**
 * Classe Parametre qui gère les paramètres de l'application
 *
 * @author Tom MARSURA
 */
public class Parametre implements Serializable{

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Booléen qui indique si l'aide de remplissage des croix est activée
     */
    private boolean aideRemplissageCroix;

    /**
     * Tableau de booléens qui indique si l'application automatique des techniques de démarrage est activée
     *   indice 0 : FACILE, indice 1 : MOYEN, indice 2 : DIFFICILE
     */
    private final boolean[] aideTechniqueDemarrage;

    /**
     * Touche pour l'action coté vide
     */
    private KeyCode toucheVide;

    /**
     * Touche pour l'action coté trait
     */
    private KeyCode toucheTrait;

    /**
     * Touche pour l'action coté croix
     */
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
     * Méthode pour obtenir le booléen qui indique si l'aide de remplissage des croix est activée
     *
     * @return le booléen de l'aide au remplissage des croix
     */
    public boolean getAideRemplissageCroix(){
        return aideRemplissageCroix;
    }

    /**
     *  Méthode pour obtenir le tableau de booléens qui indique si l'application automatique des techniques
     *  de démarrage est activée
     *
     * @return le tableau de booléens de l'aide a l'application des techniques de démarrage
     */
    public boolean[] getAideTechniqueDemarrage(){
        return aideTechniqueDemarrage;
    }

    /**
     * Méthode pour definir si l'aide de remplissage des croix est activée
     *
     * @param aideRemplissage le booléen de l'aide au remplissage des croix
     */
    public void setAideRemplissageCroix(boolean aideRemplissage){
        this.aideRemplissageCroix = aideRemplissage;
    }

    /**
     * Méthode pour definir la touche pour l'action coté vide
     *
     * @param toucheVide la touche pour l'action coté vide
     */
    public void setToucheVide(KeyCode toucheVide){
        this.toucheVide = toucheVide;
    }

    /**
     * Méthode pour definir la touche pour l'action coté trait
     *
     * @param toucheTrait la touche pour l'action coté trait
     */
    public void setToucheTrait(KeyCode toucheTrait){
        this.toucheTrait = toucheTrait;
    }

    /**
     * Méthode pour definir la touche pour l'action coté croix
     *
     * @param toucheCroix la touche pour l'action coté croix
     */
    public void setToucheCroix(KeyCode toucheCroix){
        this.toucheCroix = toucheCroix;
    }

    /**
     * Méthode pour obtenir la touche pour l'action coté vide
     *
     * @return la touche pour l'action coté vide
     */
    public KeyCode getToucheVide(){
        return toucheVide;
    }

    /**
     * Méthode pour obtenir la touche pour l'action coté trait
     *
     * @return la touche pour l'action coté trait
     */
    public KeyCode getToucheTrait(){
        return toucheTrait;
    }

    /**
     * Méthode pour obtenir la touche pour l'action coté croix
     *
     * @return la touche pour l'action coté croix
     */
    public KeyCode getToucheCroix(){
        return toucheCroix;
    }

    /**
     * Méthode pour definir si l'application automatique des techniques de démarrage est activée pour une
     * difficulté donnée
     *
     * @param aideTechniqueDemarrage le booléen qui correspond à l'application des techniques de démarrage
     * @param difficulte la difficulté pour laquelle on veut activer ou désactiver l'application des techniques de démarrage
     */
    public void setAideTechniqueDemarrage (boolean aideTechniqueDemarrage, DifficultePuzzle difficulte){
        this.aideTechniqueDemarrage[difficulte.ordinal()] = aideTechniqueDemarrage;
    }

    /**
     * Méthode pour obtenir une représentation textuelle des paramètres
     *
     * @return la représentation textuelle des paramètres
     */
    public String toString(){
        return "Aide au remplissage de la croix : " + this.aideRemplissageCroix + "\n" +
        "Aide technique au démarrage : " + Arrays.toString(this.aideTechniqueDemarrage) + "\n" +
        "Touche pour les vides : " + this.toucheVide + "\n" +
        "Touche pour les traits : " + this.toucheTrait + "\n" +
        "Touche pour les croix : " + this.toucheCroix + "\n";
    }
}
