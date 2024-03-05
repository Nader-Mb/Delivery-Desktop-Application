package models;

import java.time.LocalDateTime;

public class Commande {
    private int idCommande;
    private int idUser;
    private LocalDateTime dateCommande;
    private String adresseLivraison;
    private double montantTotalCommande;
    private String plats;
    private int restaurantId=9;


    public Commande(int idCommande, int idUser, LocalDateTime dateCommande, String adresseLivraison, double montantTotalCommande, String plats,int restaurantId ) {
        this.idCommande = idCommande;
        this.idUser = idUser;
        this.dateCommande = dateCommande;
        this.adresseLivraison = adresseLivraison;
        this.montantTotalCommande = montantTotalCommande;
        this.plats = plats;
        this.restaurantId = restaurantId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Commande() {

    }

    public int getIdCommande() {
        return this.idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }



    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public double getMontantTotalCommande() {
        return montantTotalCommande;
    }

    public void setMontantTotalCommande(double montantTotalCommande) {
        this.montantTotalCommande = montantTotalCommande;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPlats() {
        return plats;
    }

    public void setPlats(String plats) {
        this.plats = plats;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", dateCommande=" + dateCommande +
                ", adresseLivraison='" + adresseLivraison + '\'' +
                ", montantTotalCommande=" + montantTotalCommande +
                ", idUser=" + idUser +
                ", plats='" + plats + '\'' +
                '}';
    }


}
