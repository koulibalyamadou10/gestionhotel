package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IClient;
import gn.hotel.models.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
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
        setLayout(new BorderLayout());

        // 🟢 En-tête
        JLabel lblTitle = new JLabel("Gestion des Clients", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // 🟢 Section Formulaire
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Client"));

        formPanel.add(new JLabel("Nom:"));
        txtNom = new JTextField();
        formPanel.add(txtNom);

        formPanel.add(new JLabel("Prénom:"));
        txtPrenom = new JTextField();
        formPanel.add(txtPrenom);

        formPanel.add(new JLabel("Adresse:"));
        txtAdresse = new JTextField();
        formPanel.add(txtAdresse);

        formPanel.add(new JLabel("Téléphone:"));
        txtTel = new JTextField();
        formPanel.add(txtTel);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Nationalité:"));
        txtNationalite = new JComboBox<>(new String[]{"Guinéenne", "Ivoirienne", "Française", "Sénegalaise", "Malienne"});
        formPanel.add(txtNationalite);

        add(formPanel, BorderLayout.WEST);

        // 🟢 Section Boutons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnAfficher = new JButton("Afficher");

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnAfficher);

        add(buttonPanel, BorderLayout.EAST);

        // 🟢 Section Tableau
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Adresse", "Téléphone", "Email", "Nationalité"}, 0);
        tableClients = new JTable(tableModel);
        add(new JScrollPane(tableClients), BorderLayout.SOUTH);

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
        //btnModifier.addActionListener(e -> updateClient());
        //btnSupprimer.addActionListener(e ->deleteClient ());
        //btnAfficher.addActionListener(e ->viewClient ());

        //viewClient(); // Charger la liste au démarrage
    }

    @Override
    public void createClient(Client client) {
        try {
            String requete = "INSERT INTO client(nom, prenom, adresse, tel, email, nationalité) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getAdresse());
            ps.setString(4, client.getTel());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getNationalite());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Client ajouté !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client client) {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String requete = "UPDATE client SET nom=?, prenom=?, adresse=?, tel=?, email=?, nationalité=? WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(requete);
                ps.setString(1, txtNom.getText());
                ps.setString(2, txtPrenom.getText());
                ps.setString(3, txtAdresse.getText());
                ps.setString(4, txtTel.getText());
                ps.setString(5, txtEmail.getText());
                ps.setString(6, txtNationalite.getSelectedItem().toString());
                ps.setLong(7, (Long) tableModel.getValueAt(selectedRow, 0));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Client modifié !");
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
                ps.setLong(1, (Long) tableModel.getValueAt(selectedRow, 0));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Client supprimé !");
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