package gn.hotel.models;

import java.util.Date;

public class Facture {
    private int id;
    private double montantTotal;
    private Date datePaiement;
    private String modePaiement;
    private String fileName;
    private int idReservation;

    public Facture(){}

    public Facture(int id, double montantTotal, Date datePaiement, String modePaiement, String fileName, int idReservation) {
        this.id = id;
        this.montantTotal = montantTotal;
        this.datePaiement = datePaiement;
        this.modePaiement = modePaiement;
        this.fileName = fileName;
        this.idReservation = idReservation;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }
}
