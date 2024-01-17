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

    }
}
