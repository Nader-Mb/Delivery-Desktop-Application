package services;

import models.Campaign;
import models.Reservation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampaignService implements IService<Campaign> {

    private final Connection connection;

    public CampaignService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Campaign campaign) throws SQLException {
        String sql = "insert into campaign set number = ?, goal = ?, titre = ?,associationName = ?,campaignType = ?,description = ?,image= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, campaign.getNumber());
        preparedStatement.setInt(2, campaign.getGoal());
        preparedStatement.setString(3, campaign.getTitle());
        preparedStatement.setString(4, campaign.getAssociationName());
        preparedStatement.setString(5, campaign.getCampaignType());
        preparedStatement.setString(6, campaign.getDescription());
        preparedStatement.setBlob(7, campaign.getImage());
        preparedStatement.executeUpdate();

    }

    @Override
    public void modifier(Campaign campaign) throws SQLException {

    }

    @Override
    public void modifier(int number, int goal, String title, String assossiation, String type, String desc, int id) throws SQLException {
        String sql = "update campaign set number = ?, goal = ?, titre = ?,associationName = ?,campaignType = ?,description = ? where idCamp = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, number);
        preparedStatement.setInt(2, goal);
        preparedStatement.setString(3, title);
        preparedStatement.setString(4, assossiation);
        preparedStatement.setString(5, type);
        preparedStatement.setString(6, desc);
        preparedStatement.setInt(7, id);

        // preparedStatement.setBlob(7, image);
        preparedStatement.executeUpdate();

    }


    @Override
    public void modifier(int idCamp, float valeurDon, int idDoantor, int idDon) throws SQLException {

    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from campaign where idCamp = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Campaign> recuperer() throws SQLException {
        String sql = "select * from campaign";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Campaign> campaigns = new ArrayList<>();
        while (rs.next()) {
            Campaign p = new Campaign();
            p.setId(rs.getInt("idCamp"));
            p.setNumber(rs.getInt("number"));
            p.setGoal(rs.getInt("goal"));
            p.setTitle(rs.getString("titre"));
            p.setAssociationName(rs.getString("associationName"));
            p.setCampaignType(rs.getString("campaignType"));
            p.setDescription(rs.getString("description"));
            p.setCurrent(rs.getFloat("current"));
            //p.setImage(rs.getBlob("image"));
            campaigns.add(p);
        }
        return campaigns;
    }

    @Override
    public void add(Campaign entity) throws SQLException {

    }

    @Override
    public void update(Campaign entity) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }

    @Override
    public List<Campaign> retrieveAll() throws SQLException {
        return null;
    }

    @Override
    public List<Campaign> search(Object criteria) throws SQLException {
        return null;
    }

    public List<String> recupererTitre(int id) throws SQLException {
        String sql = "select * from campaign where idCamp=" + id;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<String> campaigns = new ArrayList<>();
        while (rs.next()) {
            String p;

            p = (rs.getString("titre"));

            campaigns.add(p);
        }
        return campaigns;
    }

    public List<Campaign> searchCampaignByTitle(String title) throws SQLException {
        List<Campaign> campaigns = new ArrayList<>();

        String sql = "SELECT * FROM campaign WHERE titre  LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Campaign p = new Campaign();
                p.setId(rs.getInt("idCamp"));
                p.setNumber(rs.getInt("number"));
                p.setGoal(rs.getInt("goal"));
                p.setTitle(rs.getString("titre"));
                p.setAssociationName(rs.getString("associationName"));
                p.setCampaignType(rs.getString("campaignType"));
                p.setDescription(rs.getString("description"));
                //p.setImage(rs.getBlob("image"));
                campaigns.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("Error while searching for users by email: " + ex.getMessage());
        }

        return campaigns;
    }

}
