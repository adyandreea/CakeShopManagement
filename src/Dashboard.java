import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Dashboard extends JFrame {
    private JButton ADDPRODUCTButton;
     JButton PRODUCTREPORTButton;
    private JButton BUYPRODUCTButton;
    private JButton LOGOUTANDEXITButton;
    public JPanel panel;
    private JLabel image;

    public Dashboard() {
        this.setSize(600, 400);

        ImageIcon iconLogo = new ImageIcon("img/image2.jpeg");
        image.setIcon(iconLogo);

        ADDPRODUCTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se creează și se afișează fereastra pentru adăugarea unui produs
                AddProduct obj = new AddProduct();
                obj.setVisible(true);
                dispose();
            }
        });
        PRODUCTREPORTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se creează și se afișează fereastra pentru raport produse
                ProductReport obj = new ProductReport();
                obj.setVisible(true);
                dispose();
            }
        });


        LOGOUTANDEXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se creează și se afișează fereastra de login
                LoginPage obj = new LoginPage();
                obj.setVisible(true);
                dispose();
            }
        });
        BUYPRODUCTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se creează și se afișează fereastra pentru cumpărarea unui produs
                BuyProduct obj = new BuyProduct();
                obj.setVisible(true);
                dispose();
            }
        });
        LOGOUTANDEXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Închide întreaga aplicație
                System.exit(0);
            }
        });
    }
}
