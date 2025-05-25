import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ProductReport extends JFrame {
    private JTable table1;
    private JPanel panel;

    public ProductReport() {
        this.setContentPane(panel);
        this.setSize(600, 550);
        this.setResizable(false);

        //model tabel

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("ProductName");
        model.addColumn("Cost");
        model.addColumn("Quantity");
        model.addColumn("Company");
        model.addColumn("Type");
        model.addColumn("Description");

        // Asociază modelul cu tabelul
        table1.setModel(model);

        // Apelează metoda care încarcă datele din baza de date în tabel.
        tableData();


    }
    // Metodă care încarcă datele din baza de date și le adaugă în tabel
    private void tableData() {

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "Albert9378");
            Statement st = conn.createStatement();
            String sql = "Select * from addproduct ";

            PreparedStatement ptst = conn.prepareStatement(sql);
            ResultSet rs = ptst.executeQuery();
            DefaultTableModel dt = (DefaultTableModel) table1.getModel();
            dt.setRowCount(0);

            // Parcurge rezultatele obținute din baza de date și le adaugă în tabel.
            while (rs.next()) {
                Object o[] = {rs.getInt("ID"), rs.getString("ProductName"),
                        rs.getInt("cost"), rs.getInt("quantity"), rs.getString("company"), rs.getString("type"),
                        rs.getString("Description")};
                dt.addRow(o);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
    }
}
