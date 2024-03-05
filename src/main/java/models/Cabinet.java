package models;

public class Cabinet {
    private int id;
    private String nom;
    private String adresse;
    private int codePostal;
    private String adresseMail;
    private int id_medecin;

    public Cabinet(int id, String nom, String adresse, int codePostal, String adresseMail, int id_medecin) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.adresseMail = adresseMail;
        this.id_medecin = id_medecin;
    }

    public Cabinet(String nom, String adresse, int codePostal, String adresseMail, int id_medecin) {
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.adresseMail = adresseMail;
        this.id_medecin = id_medecin;
    }

    public Cabinet(String nom, String adresse, int codePostal, String adresseMail) {
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.adresseMail = adresseMail;
    }

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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public int getId_medecin() {
        return id_medecin;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }
}
