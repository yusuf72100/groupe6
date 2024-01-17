import java.io.FileReader;

import javax.swing.*;

/**
 * Cette classe sera la classe principale de notre programme
 */
public class SlitherLink extends JFrame {
    public SlitherLink(){
        super("Slitherlink");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] var){
        SlitherLink slitherLink = new SlitherLink();
        slitherLink.setVisible(true);
        System.out.print("Ceci est un test jeune truite\n");

        JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("./template_grille.json"));

        
    }
}
