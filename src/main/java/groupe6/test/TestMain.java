package groupe6.test;

import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.profil.CatalogueProfil;
import groupe6.model.profil.Profil;

import java.util.Arrays;

/**
 * Classe de test qui est lancer avec le paramètre --test
 *
 * @author Yamis
 */
public class TestMain {
    public static void main(String[] args) {

//        TestProfils.main(args);

//        Puzzle puzzle = Puzzle.chargerPuzzle("Slitherlink/puzzles/FACILE_6x6_F.puzzle");
//        puzzle.genererGrillePropre();
//        Puzzle.sauvegarderPuzzle(puzzle, "Slitherlink/puzzles/FACILE_6x6.puzzle");

        try {
            CatalogueProfil catalogueProfil = new CatalogueProfil();
            catalogueProfil.creerNouveauProfil("invite");

            Profil p = catalogueProfil.getProfilActuel();
            System.out.println(p.getNom());
            System.out.println(p.getIMG());
            System.out.println(p.getHistorique());
            System.out.println(p.getHistorique().getReultatPartie());
            System.out.println(p.getParametre());
            System.out.println(p.getParametre().getAideRemplissageCroix());
            System.out.println(Arrays.toString(p.getParametre().getAideTechniqueDemarrage()));
            System.out.println(p.getNiveauAventure());


            System.out.printf(catalogueProfil.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
