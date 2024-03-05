package models;

import java.util.Date;

public class Rdv {
    private int id;
    private String nom;
    private String prenom;
    private int numTel;
    private String email;
    private Date dateRdv;
    private int id_cabinet;

    public Rdv(int id, String nom, String prenom, int numTel, String email, Date dateRdv, int id_cabinet) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.email = email;
        this.dateRdv = dateRdv;
        this.id_cabinet = id_cabinet;
    }

    public Rdv(String nom, String prenom, int numTel, String email, Date dateRdv, int id_cabinet) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.email = email;
        this.dateRdv = dateRdv;
        this.id_cabinet = id_cabinet;
    }

    public Rdv(String nom, String prenom, int numTel, String email, Date dateRdv) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.email = email;
        this.dateRdv = dateRdv;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(Date dateRdv) {
        this.dateRdv = dateRdv;
    }

    public int getId_cabinet() {
        return id_cabinet;
    }

    public void setId_cabinet(int id_cabinet) {
        this.id_cabinet = id_cabinet;
    }
}
