package gn.hotel.models;

public class Chambre {
    private int id;
    private String numero;
    private String type;
    private double prix;
    private int disponibilite;

    public Chambre(){}

    public Chambre(int id, String numero, String type, double prix, int disponibilite) {
        this.id = id;
        this.numero = numero;
        this.type = type;
        this.prix = prix;
        this.disponibilite = disponibilite;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(int disponibilite) {
        this.disponibilite = disponibilite;
    }
}
