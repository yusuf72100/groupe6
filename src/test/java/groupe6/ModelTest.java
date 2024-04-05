package groupe6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Classe abstraie qui represente un modèle de test.
 *
 * @author Yamis
 */
@ExtendWith(VerifierTest.class)
public abstract class ModelTest {

  /**
   * Méthode exécutée avant tous les tests de cette classe pour afficher le nom de la classe testée.
   *
   * @param T la classe qui est testée
   */
  public static void afficherNomDebut(Class<? extends ModelTest> T){
    System.out.println(CouleursANSI.BLUE_BOLD + "──────────────────────────────────────────────────" + CouleursANSI.RESET);
    System.out.println(CouleursANSI.BLUE_BOLD + " Début des tests pour la classe : " + T.getSimpleName() + CouleursANSI.RESET);
    System.out.println(CouleursANSI.BLUE_BOLD + "──────────────────────────────────────────────────" + CouleursANSI.RESET);
  }

  /**
   * Méthode exécutée après tous les tests de cette classe pour afficher le nom de la classe testée.
   *
   * @param T la classe qui est testée
   */
  public static void afficherNomFin(Class<? extends ModelTest> T){
    System.out.println(CouleursANSI.BLUE_BOLD + "──────────────────────────────────────────────────" + CouleursANSI.RESET);
    System.out.println(CouleursANSI.BLUE_BOLD + " Fin des tests pour la classe : " + T.getSimpleName() + CouleursANSI.RESET);
    System.out.println(CouleursANSI.BLUE_BOLD + "──────────────────────────────────────────────────" + CouleursANSI.RESET);
  }

  /**
   * Méthode exécutée avant tous les tests de cette classe.
   * Appelle au minimum la méthode afficherNomDebut.
   */
  @BeforeAll
  public static void initAll(){
    afficherNomDebut(ModelTest.class);
  }

  /**
   * Méthode exécutée après tous les tests de cette classe.
   * Appelle au minimum la méthode afficherNomFin.
   */
  @AfterAll
  public static void tearDownAll(){
    afficherNomFin(ModelTest.class);
  }

}
