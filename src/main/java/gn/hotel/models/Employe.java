package gn.hotel.models;

public class Employe {

    private int id;
    private String nom;
    private String poste;
    private String prenom;
    private String email;
    private double salaire;
    private String adresse;
    private String telephone;
    private int idService;

    public Employe(){}

    public Employe(int id, String nom, String poste, String prenom, String email, double salaire, String adresse, String telephone, int idService) {
        this.id = id;
        this.nom = nom;
        this.poste = poste;
        this.prenom = prenom;
        this.email = email;
        this.salaire = salaire;
        this.adresse = adresse;
        this.telephone = telephone;
        this.idService = idService;
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

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
}
