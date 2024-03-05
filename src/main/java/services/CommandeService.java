package services;

import models.Commande;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements  IServiceCommande<Commande>{
    private Connection connection;
    public CommandeService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    public int getById(int id) {
        int commentCount = 0;
        String req = "SELECT COUNT(*) FROM commande WHERE idUser=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                commentCount = rs.getInt(1);
            }
            System.out.println("Number of commend where id_forum = " + id + ": " + commentCount);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return commentCount;
    }
    @Override
    public void ajouter(Commande commande) throws SQLException {
        String sql = "INSERT INTO `commande`(`dateCommande`, `adresseLivraison`, `montantTotalCommande`, `idUser`, `plats`,restaurantId) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(commande.getDateCommande()));
        preparedStatement.setString(2, commande.getAdresseLivraison());
        preparedStatement.setDouble(3, commande.getMontantTotalCommande());
        preparedStatement.setDouble(4, commande.getIdUser());
        preparedStatement.setString(5, commande.getPlats());
        preparedStatement.setInt(6, commande.getRestaurantId());
        preparedStatement.executeUpdate();


    }

    @Override
    public void modifier(Commande commande) throws SQLException {

    }

    public void modifierById(Commande commande,int id) throws SQLException {
        String sql="UPDATE commande SET dateCommande=?,adresseLivraison =?,montantTotalCommande=?,idUser =?,plats = ?,restaurantId= ? WHERE idCommande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(commande.getDateCommande()));
        preparedStatement.setString(2,commande.getAdresseLivraison());
        preparedStatement.setDouble(3,commande.getMontantTotalCommande());
        preparedStatement.setInt(4,commande.getIdUser());
        preparedStatement.setString(5,commande.getPlats());
        preparedStatement.setInt(6,commande.getRestaurantId());
        preparedStatement.setInt(7,commande.getIdCommande());
        preparedStatement.executeUpdate();

    }

    public List<Commande> searchCommandeByNomStartingWithLetter(String searchAttribute,String searchKeyword) throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Define the format


        String sql = "SELECT * FROM commande WHERE " + searchAttribute + " LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, searchKeyword + "%");
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                Commande s = new Commande();
                s.setIdCommande(rs.getInt("idCommande"));

                // Parse the date string using the defined format
                LocalDateTime dateCommande = LocalDateTime.parse(rs.getString("dateCommande"), formatter);
                s.setDateCommande(dateCommande);

                s.setAdresseLivraison(rs.getString("adresseLivraison"));
                s.setMontantTotalCommande(rs.getDouble("montantTotalCommande"));
                s.setIdUser(rs.getInt("idUser"));
                s.setPlats(rs.getString("plats"));

                commandes.add(s);
            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Candidats !!" + ex.getMessage());
        }

        return commandes;
    }






    @Override
    public void supprimer(int id) throws SQLException {

            String sql = "DELETE FROM commande WHERE idCommande = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }



    @Override
    public List<Commande> recuperer() throws SQLException {
        String sql = "select * from commande";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Commande> commandes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Define the format

        while (rs.next()) {
            Commande s = new Commande();
            s.setIdCommande(rs.getInt("idCommande"));

            // Parse the date string using the defined format
            LocalDateTime dateCommande = LocalDateTime.parse(rs.getString("dateCommande"), formatter);
            s.setDateCommande(dateCommande);

            s.setAdresseLivraison(rs.getString("adresseLivraison"));
            s.setMontantTotalCommande(rs.getDouble("montantTotalCommande"));
            s.setIdUser(rs.getInt("idUser"));
            s.setPlats(rs.getString("plats"));

            commandes.add(s);
        }
        return commandes;
    }
}
