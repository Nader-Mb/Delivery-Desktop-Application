package services;

import models.Reservation;
import models.RestaurantTable;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTableService implements IService<RestaurantTable> {

    private final Connection connection;

    public RestaurantTableService() throws SQLException {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(RestaurantTable restaurantTable) throws SQLException {

    }

    @Override
    public void modifier(RestaurantTable restaurantTable) throws SQLException {

    }

    @Override
    public void modifier(int number, int goal, String title, String assossiation, String type, String desc, int id) throws SQLException {

    }

    @Override
    public void modifier(int idCamp, float valeurDon, int idDoantor, int idDon) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<RestaurantTable> recuperer() throws SQLException {
        return null;
    }

    @Override
    public void add(RestaurantTable table) throws SQLException {
        String sql = "INSERT INTO restauranttable (, isOccupied,restaurantId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, table.isOccupied());
            preparedStatement.setInt(2, table.getRestaurantId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(RestaurantTable table) throws SQLException {
        String sql = "UPDATE restauranttable SET  isOccupied = ?, restaurantId = ? WHERE tableId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setBoolean(1, table.isOccupied());
            preparedStatement.setInt(2, table.getRestaurantId());
            preparedStatement.setInt(3, table.getTableId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM restauranttable WHERE tableId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }

    @Override
    public List<RestaurantTable> retrieveAll() throws SQLException {
        String sql = "SELECT * FROM restauranttable";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<RestaurantTable> tables = new ArrayList<>();
            while (rs.next()) {
                RestaurantTable table = new RestaurantTable();
                table.setTableId(rs.getInt("tableId"));
                table.setOccupied(rs.getBoolean("isOccupied"));
                table.setRestaurantId(rs.getInt("restaurantId"));
                tables.add(table);
            }
            return tables;
        }
    }

    @Override
    public List<RestaurantTable> search(Object criteria) throws SQLException {
        List<RestaurantTable> tables = new ArrayList<>();
        if (criteria instanceof Integer) {
            int tableId = (int) criteria;
            String sql = "SELECT * FROM restauranttable WHERE tableId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, tableId);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        RestaurantTable table = new RestaurantTable();
                        table.setTableId(rs.getInt("tableId"));
                        table.setOccupied(rs.getBoolean("isOccupied"));
                        table.setRestaurantId(rs.getInt("restaurantId"));
                        tables.add(table);
                    }
                }
            }
        }
        return tables;
    }
}
