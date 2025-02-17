package gn.hotel.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import gn.hotel.base.KPanel;
import gn.hotel.components.ActionButtonEditor;
import gn.hotel.components.ActionButtonRenderer;
import gn.hotel.components.ClientProfile;
import gn.hotel.interfaces.IClient;
import gn.hotel.models.Client;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ClientPanel extends KPanel implements IClient {
    private JTextField txtNom, txtPrenom, txtAdresse, txtTel, txtEmail;
    private JComboBox<String> txtNationalite;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnAfficher;
    private JTable tableClients;
    private DefaultTableModel tableModel;

    public ClientPanel() {
        super();
        setLayout(new MigLayout("fillx, insets 20", "[right][grow]", "[]20[][]"));

        // 🟢 Section Formulaire
        JPanel formPanel = new JPanel(new MigLayout("fillx", "[right][grow]", "[]10[]10[]10[]10[]10[]10[]"));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Client"));

        formPanel.add(new JLabel("Nom:"), "cell 0 0");
        txtNom = new JTextField();
        txtNom.setPreferredSize(new Dimension(txtNom.getPreferredSize().width, 30));
        formPanel.add(txtNom, "cell 1 0, growx, h 30!");

        formPanel.add(new JLabel("Prénom:"), "cell 0 1");
        txtPrenom = new JTextField();
        txtPrenom.setPreferredSize(new Dimension(txtPrenom.getPreferredSize().width, 30));
        formPanel.add(txtPrenom, "cell 1 1, growx, h 30!");

        formPanel.add(new JLabel("Adresse:"), "cell 0 2");
        txtAdresse = new JTextField();
        txtAdresse.setPreferredSize(new Dimension(txtAdresse.getPreferredSize().width, 30));
        formPanel.add(txtAdresse, "cell 1 2, growx, h 30!");

        formPanel.add(new JLabel("Téléphone:"), "cell 0 3");
        txtTel = new JTextField();
        txtTel.setPreferredSize(new Dimension(txtTel.getPreferredSize().width, 30));
        formPanel.add(txtTel, "cell 1 3, growx, h 30!");

        formPanel.add(new JLabel("Email:"), "cell 0 4");
        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(txtEmail.getPreferredSize().width, 30));
        formPanel.add(txtEmail, "cell 1 4, growx, h 30!");

        formPanel.add(new JLabel("Nationalité:"), "cell 0 5");
        txtNationalite = new JComboBox<>(new String[]{"Guinéenne", "Ivoirienne", "Française", "Sénegalaise", "Malienne"});
        txtNationalite.setPreferredSize(new Dimension(txtNationalite.getPreferredSize().width, 30));
        formPanel.add(txtNationalite, "cell 1 5, growx, h 30!");

        btnAjouter = new JButton("Ajouter", new ImageIcon("/svgs/call.svg"));
        btnAjouter.setPreferredSize(new Dimension(btnAjouter.getPreferredSize().width, 35)); // Bouton légèrement plus grand
        formPanel.add(btnAjouter, "cell 0 6, span 2, growx, gaptop 10, h 35!");

        add(formPanel, "span, grow");

        // 🟢 Section Boutons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnAfficher = new JButton("Afficher");

        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnAfficher);

//        add(buttonPanel, BorderLayout.EAST);

        // 🟢 Section Tableau
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Nom", "Prénom", "Adresse", "Téléphone", "Email", "Nationalité", "Actions"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Seule la colonne Actions est éditable
            }
        };
        tableClients = new JTable(tableModel);
        // Définir le renderer et l'éditeur pour la colonne Actions
        tableClients.getColumnModel().getColumn(7).setCellRenderer(new ActionButtonRenderer());
        tableClients.getColumnModel().getColumn(7).setCellEditor(new ActionButtonEditor(tableClients));

        // Ajuster la hauteur des lignes pour les icônes
        tableClients.setRowHeight(35);

        // Ajouter le tableau dans un JScrollPane
        add(new JScrollPane(tableClients), BorderLayout.SOUTH);
        loadClients();

        tableClients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tableClients.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / tableClients.getRowHeight();

                if (row < tableClients.getRowCount() && row >= 0 && column == 7) {
                    Object value = tableClients.getValueAt(row, 0); // ID de la ligne
                    if ("view".equals(tableClients.getCellEditor(row, column).getCellEditorValue())) {
                        // Action pour voir
                        System.out.println("Voir l'élément " + value);
                    } else if ("edit".equals(tableClients.getCellEditor(row, column).getCellEditorValue())) {
                        // Action pour modifier
                        System.out.println("Modifier l'élément " + value);
                    } else if ("delete".equals(tableClients.getCellEditor(row, column).getCellEditorValue())) {
                        // Action pour supprimer
                        System.out.println("Supprimer l'élément " + value);
                    }
                }
            }
        });

        // 🔴 Gestion des événements
        btnAjouter.addActionListener(e ->{
            Client client = new Client();
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String adresse = txtAdresse.getText();
            String telephone = txtTel.getText();
            String email = txtEmail.getText();


            if( nom.isEmpty() ){
                txtNom.requestFocus();
                return;
            }else if( prenom.isEmpty() ){
                txtPrenom.requestFocus();
                return;
            }
            else if(adresse.isEmpty()){
                txtAdresse.requestFocus();
                return;
            }
            else if(telephone.isEmpty()){
                txtTel.requestFocus();
                return;
            }
            else if(email.isEmpty()){
                txtEmail.requestFocus();
                return;
            }


            client.setNom(nom);
            client.setPrenom(prenom);
            client.setAdresse(adresse);
            client.setTel(telephone);
            client.setEmail(email);
            client.setNationalite(txtNationalite.getSelectedItem().toString());
            createClient(client);
        });
        btnModifier.addActionListener(e -> {
            Client client = new Client();
            int selectedRow = tableClients.getSelectedRow();
            if( selectedRow == -1){
                JOptionPane.showMessageDialog(this, "Aucune ligne selectionné !");
                return;
            }

            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String adresse = txtAdresse.getText();
            String telephone = txtTel.getText();
            String email = txtEmail.getText();


            if( nom.isEmpty() ){
                txtNom.requestFocus();
                return;
            }else if( prenom.isEmpty() ){
                txtPrenom.requestFocus();
                return;
            }
            else if(adresse.isEmpty()){
                txtAdresse.requestFocus();
                return;
            }
            else if(telephone.isEmpty()){
                txtTel.requestFocus();
                return;
            }
            else if(email.isEmpty()){
                txtEmail.requestFocus();
                return;
            }

            client.setId(Integer.parseInt(tableClients.getValueAt(selectedRow,0).toString()));
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setAdresse(adresse);
            client.setTel(telephone);
            client.setEmail(email);
            client.setNationalite(txtNationalite.getSelectedItem().toString());
            updateClient(client);
        });
        btnSupprimer.addActionListener(e ->{
            Client client = new Client();
            int selectedRow = tableClients.getSelectedRow();
            if( selectedRow == -1){
                JOptionPane.showMessageDialog(this, "Aucune ligne selectionné !");
                return;
            }

            int choix = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce client ?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choix != JOptionPane.YES_OPTION) {
                return;
            }

            client.setId(Integer.parseInt(tableClients.getValueAt(selectedRow,0).toString()));
            deleteClient(client);
        });
        listeners();
    }

    public void listeners(){
        tableClients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = tableClients.getSelectedRow();
                if( selectedRow == -1 )return;

                txtNom.setText(tableClients.getValueAt(selectedRow, 1).toString());
                txtPrenom.setText(tableClients.getValueAt(selectedRow, 2).toString());
                txtAdresse.setText(tableClients.getValueAt(selectedRow, 3).toString());
                txtTel.setText(tableClients.getValueAt(selectedRow, 4).toString());
                txtEmail.setText(tableClients.getValueAt(selectedRow, 5).toString());
                txtNationalite.setSelectedItem(tableClients.getValueAt(selectedRow, 6).toString());
            }


        });
    }

    public void loadClients(){
        tableModel.setRowCount(0);
        String requete = "SELECT * FROM client ORDER BY id DESC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete) ){
            try(ResultSet resultSet = preparedStatement.executeQuery() ){
                while (resultSet.next()){
                    tableModel.addRow(new String[]{
                            resultSet.getInt("id") + "",
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("adresse"),
                            resultSet.getString("tel"),
                            resultSet.getString("email"),
                            resultSet.getString("nationalite")
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createClient(Client client) {
        try {
            // Vérifier si l'email existe déjà
            String checkEmailQuery = "SELECT COUNT(*) FROM client WHERE email = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkEmailQuery);
            checkStmt.setString(1, client.getEmail());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Si l'email existe, afficher une boîte de dialogue et arrêter l'insertion
                JOptionPane.showMessageDialog(null, "Cet email est déjà utilisé. Veuillez en choisir un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si l'email n'existe pas, insérer le client
            String requete = "INSERT INTO client(nom, prenom, adresse, tel, email, nationalite) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getAdresse());
            ps.setString(4, client.getTel());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getNationalite());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Client ajouté !");
            loadClients();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client client) {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String requete = "UPDATE client SET nom=?, prenom=?, adresse=?, tel=?, email=?, nationalite=? WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(requete);
                ps.setString(1, client.getNom());
                ps.setString(2, client.getPrenom());
                ps.setString(3, client.getAdresse());
                ps.setString(4, client.getTel());
                ps.setString(5, client.getEmail());
                ps.setString(6, client.getNationalite());
                ps.setInt(7, Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Client modifié !");
                loadClients();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un client !");
        }
    }

    @Override
    public void deleteClient(Client client) {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String requete = "DELETE FROM client WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(requete);
                ps.setInt(1, Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Client supprimé !");
                loadClients();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un client !");
        }
    }

    @Override
    public void viewClient(Client client) {
        tableModel.setRowCount(0);
        try {
            String requete = "SELECT * FROM client WHERE ID = ? ";
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, client.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getLong("id"));
                row.add(rs.getString("nom"));
                row.add(rs.getString("prenom"));
                row.add(rs.getString("adresse"));
                row.add(rs.getString("tel"));
                row.add(rs.getString("email"));
                row.add(rs.getString("nationalité"));
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}