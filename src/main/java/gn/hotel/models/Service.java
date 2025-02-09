package gn.hotel.models;

public class Service {
    private int id;
    private String nom;
    private String description;
    private double prix;

    public Service(){}

    public Service(int id, String nom, String description, double prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
