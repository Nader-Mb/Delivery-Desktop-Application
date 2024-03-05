package services;

import models.Donation;
import models.Reservation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonationService implements IService<Donation> {
    private final Connection connection;

    public DonationService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Donation donation) throws SQLException {
        String sql = "insert into donation set idDon = ?, idCamp = ?, valeurDon = ?,idDonator = ?, history = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, donation.getIdDon());
        preparedStatement.setInt(2, donation.getIdCamp());
        preparedStatement.setFloat(3, donation.getValeurDon());
        preparedStatement.setInt(4, donation.getIdDonator());
        preparedStatement.setDate(5, (Date) donation.getHistory());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Donation donation) throws SQLException {

    }

    @Override
    public void modifier(int number, int goal, String title, String assossiation, String type, String desc, int id) throws SQLException {

    }

    @Override
    public void modifier(int idCamp, float valeurDon, int idDoantor, int idDon) throws SQLException {
        String sql = "update donation set  idCamp = ?, valeurDon = ?,idDonator = ? where idDon = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idCamp);
        preparedStatement.setFloat(2, valeurDon);
        preparedStatement.setInt(3, idDoantor);
        preparedStatement.setInt(4, idDon);
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from donation where idDon = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();


    }

    @Override
    public List<Donation> recuperer() throws SQLException {
        String sql = "select * from donation";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Donation> donations = new ArrayList<>();
        while (rs.next()) {
            Donation donation = new Donation();
            donation.setIdDon(rs.getInt("idDon"));
            donation.setIdCamp(rs.getInt("idCamp"));
            donation.setValeurDon(rs.getFloat("valeurDon"));
            donation.setIdDonator(rs.getInt("idDonator"));
            donation.setHistory(rs.getDate("history"));
            donation.toString();
            donations.add(donation);
        }
        return donations;

    }

    @Override
    public void add(Donation entity) throws SQLException {

    }

    @Override
    public void update(Donation entity) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }

    @Override
    public List<Donation> retrieveAll() throws SQLException {
        return null;
    }

    @Override
    public List<Donation> search(Object criteria) throws SQLException {
        return null;
    }

    public void addDonation(float donation, int id) throws SQLException {
        String sql = "update campaign set current = current+ ? where idCamp = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setFloat(1, donation);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

}
