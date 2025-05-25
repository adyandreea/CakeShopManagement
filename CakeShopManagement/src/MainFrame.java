import javax.swing.*;

public class MainFrame {
    /**
     * Current frame.
     */
    private static JFrame frame;

    /**
     * Sets the current panel.
     */
    public static void setPanel(JPanel panel, int width, int height) {
        frame.setContentPane(panel);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Setează comportamentul ferestrei la închidere
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        frame = new JFrame("Cake Shop Management");
        setPanel(new LoginPage().panelMain, 450, 350); //Setează panoul de login ca fiind panoul principal al ferestrei
    }
}
