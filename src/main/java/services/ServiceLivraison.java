package services;

import models.Commande;
import models.Livraison;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceLivraison implements  IServiceLivraison{
    private Connection connection;
    public ServiceLivraison() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void planifierLivraison(Livraison livraison) throws SQLException {
        String sql = "insert into livraison (idLivreur,idCommande,statut) values (?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,2);
        preparedStatement.setInt(2,livraison.getIdCommande());
        preparedStatement.setString(3,"in progresse");
        preparedStatement.executeUpdate();
    }

    public void update(Livraison l,int idCom)throws SQLException{

        String sql="UPDATE livraison SET statut = ? WHERE idCommande=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,l.getStatut());
        preparedStatement.setInt(2,idCom);
        preparedStatement.executeUpdate();
    }
    public Livraison searchLivraisonByIdCom(int idCom) throws SQLException {
        Livraison l = new Livraison();

        String sql = "SELECT * FROM `livraison` WHERE idCommande = "+ idCom ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeQuery();

        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            l.setIdLivraison(rs.getInt("idLivraison"));
            l.setIdCommande(rs.getInt("idCommande"));
            l.setStatut(rs.getString("statut"));
            l.setIdLivreur(rs.getInt("idLivreur"));
            return l;

        }
        else{
            return null;
        }
    }


    @Override
    public List<Commande> recuprerlivraisonEncours() throws SQLException {
        List<Commande> listefinale = new ArrayList<>();
        IServiceCommande serviceCommande = new CommandeService();
        List<Commande> listets = serviceCommande.recuperer();
        List<Livraison> listliv = new ArrayList<>();
        String sql = "select * from livraison";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Commande> commandes = new ArrayList<>();

        while (rs.next()) {
            Livraison l = new Livraison();
            l.setIdLivraison(rs.getInt("idLivraison"));
            l.setIdCommande(rs.getInt("idCommande"));
            l.setStatut(rs.getString("statut"));
            l.setIdLivreur(rs.getInt("idLivreur"));
            listliv.add(l);
        }
        if (listliv.isEmpty()) {
            return listets;
        } else {
            for (Commande c : listets
            ) {
                for (Livraison l : listliv
                ) {
                    if (c.getIdCommande() != l.getIdCommande()) {
                        listefinale.add(c);
                    }
                }


            }

        }
        return listefinale;
    }
    @Override
    public List<Commande> récupérerToutesLesLivraisons() throws SQLException {
        List<Commande> listefinale=new ArrayList<>();
        IServiceCommande serviceCommande=new CommandeService();
        List<Commande> listets=serviceCommande.recuperer();
        List<Livraison> listliv=new ArrayList<>();
        String sql = "select * from livraison";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Commande> commandes = new ArrayList<>();

        while (rs.next()) {
            Livraison l = new Livraison();
           l.setIdLivraison(rs.getInt("idLivraison"));
           l.setIdCommande(rs.getInt("idCommande"));
           l.setStatut(rs.getString("statut"));
           l.setIdLivreur(rs.getInt("idLivreur"));
            listliv.add(l);
        }
        for ( Livraison l:listliv
             ) {
            for (Commande c : listets
                 ) {
                if (c.getIdCommande()==l.getIdCommande()){listefinale.add(c);}
            }
        }
        return listefinale;
    }
    public void supprimerLivraisonByCommande(int idCo) throws SQLException {

    String sql = "DELETE FROM livraison WHERE idCommande = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idCo);
            preparedStatement.executeUpdate();}

}
