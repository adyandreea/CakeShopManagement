import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginPage extends JFrame {
    public JPanel panelMain; // Panoul principal al ferestrei
    private JTextField user;
    private JPasswordField password;
    private JButton loginButton;

    public LoginPage() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String un = user.getText();
                    String ps = password.getText();

                    // Se creează o conexiune la baza de date
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "Albert9378");
                    // Se creează un obiect Statement pentru a executa interogările SQL
                    Statement st = conn.createStatement();
                    // Interogarea SQL pentru a obține toate datele din tabelul 'users'
                    String sql = "Select * from users";
                    // Se execută interogarea și se obține rezultatele
                        ResultSet rs = st.executeQuery(sql);
                    // Se parcurge rezultatele obținute din baza de date
                    while(rs.next()){
                            String username = rs.getString("username");
                            String password = rs.getString("password");

                        // Se compară datele introduse de utilizator cu cele din baza de date
                        if (un.equals(username) && ps.equals(password)){
                            // Dacă username și parola sunt corecte, trece la panoul Dashboard
                            MainFrame.setPanel(new Dashboard().panel, 550, 550);
                            }else{
                                JOptionPane.showMessageDialog(null,"Username and password is incorrect");
                            }
                        }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Error while establishing connection!");
                }
            }
        });
    }
}
