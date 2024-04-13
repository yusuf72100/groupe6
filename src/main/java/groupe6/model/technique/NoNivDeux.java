package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe abstraite qui représente une technique qui ne dispose pas d'aide de niveau 2
 *
 * @author Yamis
 */
public abstract class NoNivDeux extends Technique {

  /**
   * Constructeur de la classe abstraite NoNivDeux
   *
   * @param uneDifficulte la difficulté de la technique
   * @param nomTechniqueStylise le nom stylisé de la technique
   */
  public NoNivDeux(DifficulteTechnique uneDifficulte, String nomTechniqueStylise) {
    super(uneDifficulte, nomTechniqueStylise);
  }

  /**
   * Méthode pour exécuter la technique
   *
   * @param partie la partie sur laquelle on cherche la technique
   * @param idx l'index de la technique dans la liste des techniques
   * @return le résultat technique
   */
  @Override
  public ResultatTechnique run(Partie partie, int idx) {
    // Ajout d'une coordonnée spéciale pour indiquer une technique trouvée, mais sans aide de niveau 2
    Set<Coordonnee> coordonnees = new HashSet<Coordonnee>();
    coordonnees.add(new Coordonnee(-999, -999));

    // Création du résultat technique
    ResultatTechnique resultat = new ResultatTechnique(
        true,
        coordonnees,
        idx,
        this.nomTechnique,
        this.nomTechniqueStylise,
        this.difficulte
    );

    // Vérifie que la technique n'a pas déjà été trouvée
    if ( !partie.getHistoriqueAide().aideDejaPresente(resultat)) {
      return resultat;
    } else {
      return new ResultatTechnique(false);
    }
  }

}
