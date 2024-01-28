import java.awt.Dimension;
import javax.swing.JFrame;

public class WindowTwo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stationary balls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PanelTwo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);

    }
}