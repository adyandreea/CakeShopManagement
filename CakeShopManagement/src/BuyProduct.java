import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class BuyProduct extends JFrame {
    private JButton BUYButton;
    private JTable table1;
    private JPanel panel;

    private JTextField quantity;
    private JTextField totalPriceField;
    private int selectedProductId;
    private String selectedProductName;
    private int selectedProductStock;


    public BuyProduct() {

        // Setează JPanel-ul principal ca fiind conținutul JFrame-ului
        this.setContentPane(panel);
        this.setSize(600, 650);
        this.setResizable(false);



        // Se creează și se configurează modelul tabelului
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("ProductName");
        model.addColumn("Cost Per 100g");
        model.addColumn("Quantity");
        model.addColumn("Company");
        model.addColumn("Type");
        model.addColumn("Description");

        // Asociază modelul cu tabelul
        table1.setModel(model);

        // Încărcarea datelor în tabel
        tableData();

        BUYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifică dacă un produs este selectat
                if (selectedProductName == null) {
                    JOptionPane.showMessageDialog(null, "Please select a product from the table!");
                    return;
                }

                try {
                    int requestedQuantity = Integer.parseInt(quantity.getText());
                    // Verifică dacă cantitatea introdusă este validă
                    if (requestedQuantity <= 0) {
                        JOptionPane.showMessageDialog(null, "Enter a valid quantity!");
                        return;
                    }

                    // Verifică dacă există suficient stoc pentru produs
                    if (requestedQuantity > selectedProductStock) {
                        JOptionPane.showMessageDialog(null,
                                "Insufficient stock! Amount available: " + selectedProductStock);
                        return;
                    }

                    // Calculează prețul
                    int costPer100g = (int) table1.getValueAt(table1.getSelectedRow(), 2); // Preț pe 100g
                    double totalPrice = (costPer100g * requestedQuantity) / 100.0; // Preț total pentru cantitatea cerută

                    // Actualizează câmpul de text cu prețul total
                    totalPriceField.setText(String.format("Total Price: %.2f", totalPrice));

                    // Conectare la baza de date
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "Albert9378");

                    // Scade cantitatea din stoc
                    String updateStockQuery = "UPDATE addproduct SET quantity = quantity - ? WHERE ID = ?";
                    PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery);
                    updateStockStmt.setInt(1, requestedQuantity);
                    updateStockStmt.setInt(2, selectedProductId);
                    updateStockStmt.executeUpdate();

                    // Notificare utilizator
                    JOptionPane.showMessageDialog(null, "The purchase was successful!");

                    // Reîmprospătare tabel
                    refreshTable();

                    // Resetare selecție
                    selectedProductName = null;
                    selectedProductStock = 0;
                    quantity.setText("");

                    conn.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "The quantity must be a valid number!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error updating the database: " + ex.getMessage());
                }

            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    // Salvează detaliile produsului selectat
                    selectedProductId = (int) table1.getValueAt(selectedRow, 0);
                    selectedProductName = (String) table1.getValueAt(selectedRow, 1);
                    selectedProductStock = (int) table1.getValueAt(selectedRow, 3);

                    JOptionPane.showMessageDialog(null,
                            "Selected item: " + selectedProductName + "\nStock available: " + selectedProductStock);
                }
            }
        });

    }

    // Metoda care actualizează și reîncarcă tabelul cu produsele disponibile
    public void refreshTable() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "Albert9378");
            String sql = "SELECT * FROM addproduct";
            PreparedStatement ptst = conn.prepareStatement(sql);
            ResultSet rs = ptst.executeQuery();

            DefaultTableModel dt = (DefaultTableModel) table1.getModel();
            dt.setRowCount(0);  // Șterge datele anterioare din tabel

            // Populează tabelul cu date noi
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("ID"),
                        rs.getString("ProductName"),
                        rs.getInt("cost"),
                        rs.getInt("quantity"),
                        rs.getString("company"),
                        rs.getString("type"),
                        rs.getString("Description")
                };
                dt.addRow(row);
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading the table: " + ex.getMessage());
        }
    }

    // Metoda care încarcă datele inițiale în tabel
    private void tableData(){

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "Albert9378");
            Statement st = conn.createStatement();
            String sql = "Select * from addproduct ";

            PreparedStatement ptst = conn.prepareStatement(sql);
            ResultSet rs = ptst.executeQuery();
            DefaultTableModel dt = (DefaultTableModel) table1.getModel();
            dt.setRowCount(0);

            while(rs.next()){
                Object o[]={rs.getInt("ID"),rs.getString("ProductName"),
                        rs.getInt("cost"),rs.getInt("quantity"),rs.getString("company"),rs.getString("type"),
                        rs.getString("Description")};
                dt.addRow(o);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);

        }
    }
}
