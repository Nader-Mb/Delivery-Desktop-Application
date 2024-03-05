package services;

import models.Commande;
import models.Livraison;

import java.sql.SQLException;
import java.util.List;

public interface IServiceLivraison {
    void planifierLivraison(Livraison livraison) throws SQLException;
    public List<Commande> recuprerlivraisonEncours() throws SQLException;
    List<Commande> récupérerToutesLesLivraisons() throws SQLException;
    // Autres méthodes pour la gestion des livraisons*
}
