package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IReservation;
import gn.hotel.interfaces.IReservationService;
import gn.hotel.models.Reservation;
import gn.hotel.models.ReservationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ReservationPanel extends KPanel implements IReservation, IReservationService {

    private JTextField txtDateDebut, txtDateFin;
    private JComboBox<String> txtEmailClient, txtNumerosChambres, txtStatut;
    private JButton btnCreate;
    private JTable reservationTable;
    private DefaultTableModel tableModel;

    public ReservationPanel() {
        setLayout(new BorderLayout());

        // Formulaire de création de réservation
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 0, 10));
        txtDateDebut = new JTextField();
        txtDateFin = new JTextField();
        txtStatut = new JComboBox<>(new String[]{"Payé", "Impayé"});
        txtEmailClient = new JComboBox<>(getEmailsClient());
        txtNumerosChambres = new JComboBox<>(getNumerosChambre());
        btnCreate = new JButton("Valider la Reservation");


        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        formPanel.add(new JLabel("Email Client:"));
        formPanel.add(txtEmailClient);
        formPanel.add(new JLabel("Numero Chambre:"));
        formPanel.add(txtNumerosChambres);
        formPanel.add(new JLabel("Statut:"));
        formPanel.add(txtStatut);
        formPanel.add(new JLabel("Date de début (yyyy-mm-dd):"));
        formPanel.add(txtDateDebut);
        formPanel.add(new JLabel("Date de fin (yyyy-mm-dd):"));
        formPanel.add(txtDateFin);

        formPanel.add(btnCreate);
        add(formPanel, BorderLayout.NORTH);

        // Panel pour centrer le bouton

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnCreate);

        // Ajout des panels au formulaire
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(formPanel, BorderLayout.NORTH);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(containerPanel, BorderLayout.NORTH);
        // Tableau des réservations
        tableModel = new DefaultTableModel(new Object[]{"ID", "Date Début", "Date Fin", "Statut", "Client", "Chambre", "Actions"}, 0);
        reservationTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 6) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    JButton btnUpdate = new JButton("Modifier");
                    JButton btnDelete = new JButton("Supprimer");
                    JButton btnDetails = new JButton("Détails");

                    int reservationId = (int) getValueAt(row, 0);

                    btnUpdate.addActionListener(e -> updateReservation(reservationId));
                    btnDelete.addActionListener(e -> deleteReservation(reservationId));
                    btnDetails.addActionListener(e -> viewReservation(reservationId));

                    panel.add(btnUpdate);
                    panel.add(btnDelete);
                    panel.add(btnDetails);
                    return panel;
                }
                return c;
            }
        };

        add(new JScrollPane(reservationTable), BorderLayout.CENTER);

        btnCreate.addActionListener(e -> {
            Reservation reservation = getReservationFromFields();
            createReservation(reservation);
            loadReservations();
        });

        loadReservations();
    }

    private Reservation getReservationFromFields() {

        return new Reservation(
                0,
                Date.valueOf(txtDateDebut.getText()),
                Date.valueOf(txtDateFin.getText()),
                txtStatut.getSelectedItem().toString(),
                getIDClient(txtEmailClient.getSelectedItem().toString()),
                getIDChambre(txtNumerosChambres.getSelectedItem().toString())
        );
    }

    private void loadReservations() {
        tableModel.setRowCount(0);
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservation");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("statut"),
                        rs.getInt("id_client"),
                        rs.getInt("id_chambre"),
                        ""
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReservation(int id) {
        JOptionPane.showMessageDialog(this, "Mise à jour de la réservation ID " + id);
    }

    private Vector<String> getEmailsClient(){
        Vector<String> vector = new Vector<>();
        String requete = "SELECT email FROM client ORDER BY ID DESC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            try(ResultSet resultSet = preparedStatement.executeQuery() ){
                while (resultSet.next()){
                    vector.add(resultSet.getString("email"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return vector;
    }

    private int getIDClient(String email){
        String requete = "SELECT id FROM client WHERE email = ? ORDER BY ID DESC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            preparedStatement.setString(1, email);
            try(ResultSet resultSet = preparedStatement.executeQuery() ){
                if (resultSet.next()){
                    return resultSet.getInt("id");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private Vector<String> getNumerosChambre(){
        Vector<String> vector = new Vector<>();
        String requete = "SELECT numero FROM chambre ORDER BY ID DESC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            try(ResultSet resultSet = preparedStatement.executeQuery() ){
                while (resultSet.next()){
                    vector.add(resultSet.getString("numero"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return vector;
    }

    private int getIDChambre(String numero){
        String requete = "SELECT id FROM chambre WHERE numero = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            preparedStatement.setString(1, numero);
            try(ResultSet resultSet = preparedStatement.executeQuery() ){
                if (resultSet.next()){
                    return resultSet.getInt("id");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void deleteReservation(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cette réservation ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM reservations WHERE id=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Réservation supprimée !");
                loadReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void viewReservation(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservations WHERE id=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Détails de la réservation:\nID: " + rs.getInt("id") +
                        "\nDate début: " + rs.getDate("date_debut") +
                        "\nDate fin: " + rs.getDate("date_fin") +
                        "\nStatut: " + rs.getString("statut") +
                        "\nClient: " + rs.getInt("id_client") +
                        "\nChambre: " + rs.getInt("id_chambre"));
            } else {
                JOptionPane.showMessageDialog(this, "Aucune réservation trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createReservation(Reservation reservation) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO reservations(date_debut, date_fin, statut, id_client, id_chambre) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(reservation.getDateDebut().getTime()));
            stmt.setDate(2, new java.sql.Date(reservation.getDateFin().getTime()));
            stmt.setString(3, reservation.getStatut());
            stmt.setInt(4, reservation.getIdClient());
            stmt.setInt(5, reservation.getIdChambre());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Réservation créée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    @Override
    public void createReservationService(ReservationService reservationService) { }
    @Override
    public void updateReservationService(ReservationService reservationService) { }
    @Override
    public void deleteReservationService(ReservationService reservationService) { }
    @Override
    public void viewReservationService(ReservationService reservationService) { }
}