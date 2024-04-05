module Slitherlink {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    exports groupe6.affichage;
    exports groupe6.launcher;
    exports groupe6.model.entrainement;
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
    exports groupe6.test;
    exports groupe6.tools.puzzleGenerator;
}