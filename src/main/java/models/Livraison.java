package models;

public class Livraison {

    private int idLivraison;
    private int idLivreur;
    private int idCommande;
    private String statut="on hold ";
    public Livraison() {
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Livraison(int idLivraison, int idLivreur, int idCommande,String statut) {
        this.idLivraison = idLivraison;
        this.idLivreur = idLivreur;
        this.idCommande = idCommande;
        this.statut=statut;
    }

    public int getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public int getIdLivreur() {
        return idLivreur;
    }

    public void setIdLivreur(int idLivreur) {
        this.idLivreur = idLivreur;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "idLivraison=" + idLivraison +
                ", idLivreur=" + idLivreur +
                ", idCommande=" + idCommande +
                ", statut='" + statut + '\'' +
                '}';
    }
}
