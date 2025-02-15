package gn.hotel.components;

import gn.hotel.models.Client;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientProfile extends JFrame {
    private JLabel lblId, lblNom, lblPrenom, lblAdresse, lblTel, lblEmail, lblNationalite;
    private JPanel panel;
    private Connection connection;

    public ClientProfile(Connection connection) {
        this.connection = connection;

        // Configuration de la fenêtre
        setTitle("Profil du Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Création du panneau principal
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        // Création des labels
        panel.add(new JLabel("ID :"));
        lblId = new JLabel();
        panel.add(lblId);

        panel.add(new JLabel("Nom :"));
        lblNom = new JLabel();
        panel.add(lblNom);

        panel.add(new JLabel("Prénom :"));
        lblPrenom = new JLabel();
        panel.add(lblPrenom);

        panel.add(new JLabel("Adresse :"));
        lblAdresse = new JLabel();
        panel.add(lblAdresse);

        panel.add(new JLabel("Téléphone :"));
        lblTel = new JLabel();
        panel.add(lblTel);

        panel.add(new JLabel("Email :"));
        lblEmail = new JLabel();
        panel.add(lblEmail);

        panel.add(new JLabel("Nationalité :"));
        lblNationalite = new JLabel();
        panel.add(lblNationalite);

        // Ajout du panel à la fenêtre
        add(panel, BorderLayout.CENTER);
    }

    // Méthode pour afficher les informations du client
    public void viewClient(Client client) {
        try {
            String requete = "SELECT * FROM client WHERE ID = ? ";
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, client.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblId.setText(String.valueOf(rs.getLong("id")));
                lblNom.setText(rs.getString("nom"));
                lblPrenom.setText(rs.getString("prenom"));
                lblAdresse.setText(rs.getString("adresse"));
                lblTel.setText(rs.getString("tel"));
                lblEmail.setText(rs.getString("email"));
                lblNationalite.setText(rs.getString("nationalite"));
            } else {
                JOptionPane.showMessageDialog(this, "Client non trouvé !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour afficher la fenêtre de profil du client
    public static void afficherProfil(Client client, Connection connection) {
        ClientProfile profil = new ClientProfile(connection);
        profil.viewClient(client);
        profil.setVisible(true);
    }
}