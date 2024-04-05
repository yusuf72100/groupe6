package groupe6;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

/**
 * Classe qui surveille l'exécution des tests et affiche des messages en fonction de leur résultat.
 *
 * @author Yamis
 */
public class VerifierTest implements TestWatcher {

  @Override
  public void testDisabled(ExtensionContext context, Optional<String> reason) {
    System.out.println( CouleursANSI.YELLOW + "Attention : Le test " + context.getDisplayName() + " est désactivé." + CouleursANSI.RESET);
  }

  @Override
  public void testSuccessful(ExtensionContext context) {
    System.out.println(CouleursANSI.GREEN + "Succès : Le test " + context.getDisplayName() + " a réussi." + CouleursANSI.RESET);
  }

  @Override
  public void testAborted(ExtensionContext context, Throwable cause) {
    System.out.println(CouleursANSI.RED + "Échec : Le test " + context.getDisplayName() + " a été abandonné." + CouleursANSI.RESET);
  }

  @Override
  public void testFailed(ExtensionContext context, Throwable cause) {
    System.out.println(CouleursANSI.RED + "Échec : Le test " + context.getDisplayName() + " a échoué : " + cause.getMessage() + CouleursANSI.RESET);
  }
}
