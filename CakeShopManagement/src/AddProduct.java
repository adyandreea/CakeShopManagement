import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class AddProduct extends JFrame {
    private JTextField name;
    private JTextField cost;
    private JTextField company;
    private JTextField type;
    private JTextArea description;
    private JButton SAVEPRODUCTButton;
    private JPanel panel;
    private JTextField quantity;

    public AddProduct() {
        this.setContentPane(panel);
        this.setSize(350, 425);
        this.setResizable(false);


        SAVEPRODUCTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "Albert9378");
                    // Interogarea SQL pentru a insera un nou produs în tabelul "addproduct"
                    String sql = "INSERT INTO addproduct (productname, cost, quantity, company, type, description) VALUES (?, ?, ?, ?, ?, ?)";

                    PreparedStatement ptst = conn.prepareStatement(sql);
                    ptst.setString(1, name.getText());
                    ptst.setString(2, cost.getText());
                    ptst.setString(3, quantity.getText());
                    ptst.setString(4, company.getText());
                    ptst.setString(5, type.getText());
                    ptst.setString(6, description.getText());

                    // Execută interogarea pentru a adăuga produsul în baza de date.
                    ptst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Product has been inserted successfully");

                    conn.close();
                }catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex);
                }
            }
        });

    }
}
