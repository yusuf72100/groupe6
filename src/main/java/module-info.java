/**
 * Le module Slitherlink est le module principal de l'application.
 * <br>
 * <br>
 * Ce projet est réalisé par :
 * <ul>
 *   <li>Nathan Raccouard</li>
 *   <li>Yusuf Ulas</li>
 *   <li>Yamis Manfaloti</li>
 *   <li>Emmanuel Ntame</li>
 *   <li>Tom Marsura</li>
 * 	 <li>William Sardon Arraz</li>
 *   <li>Matéo Gallais	Oussama Boudallaa</li>
 * </ul>
 * <br>
 * Il contient les packages suivants :
 * <ul>
 *   <li>affichage</li>
 *   <li>launcher</li>
 *   <li>model.partie</li>
 *   <li>model.partie.action</li>
 *   <li>model.partie.aide</li>
 *   <li>model.partie.erreur</li>
 *   <li>model.partie.info</li>
 *   <li>model.partie.puzzle</li>
 *   <li>model.partie.puzzle.cellule</li>
 *   <li>model.partie.sauvegarde</li>
 *   <li>model.profil</li>
 *   <li>model.technique</li>
 *   <li>test</li>
 *   <li>tools.puzzleGenerator</li>
 * </ul>
 */
module Slitherlink {
    requires javafx.controls;
    requires java.desktop;

    exports groupe6.affichage;
    exports groupe6.launcher;
    exports groupe6.model.partie;
    exports groupe6.model.partie.action;
    exports groupe6.model.partie.aide;
    exports groupe6.model.partie.erreur;
    exports groupe6.model.partie.info;
    exports groupe6.model.partie.puzzle;
    exports groupe6.model.partie.puzzle.cellule;
    exports groupe6.model.partie.sauvegarde;
    exports groupe6.model.profil;
    exports groupe6.model.technique;
    exports groupe6.tools.puzzleGenerator;
}