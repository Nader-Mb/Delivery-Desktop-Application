
package services;

import models.Reservation;
import models.Restaurant;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService implements IService<Restaurant> {

    private final Connection connection;

    public RestaurantService() throws SQLException {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Restaurant restaurant) throws SQLException {

    }

    @Override
    public void modifier(Restaurant restaurant) throws SQLException {

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
    public List<Restaurant> recuperer() throws SQLException {
        return null;
    }

    public void add(Restaurant restaurant) throws SQLException {
        String restaurantSql = "INSERT INTO restaurant (name, address, description, userId,imagePath) VALUES (?, ?, ?, ?,?)";
        String tableSql = "INSERT INTO restauranttable (isOccupied, restaurantId) VALUES (?, ?)";

        try (PreparedStatement restaurantStatement = connection.prepareStatement(restaurantSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement tableStatement = connection.prepareStatement(tableSql)) {

            // Insert restaurant
            restaurantStatement.setString(1, restaurant.getName());
            restaurantStatement.setString(2, restaurant.getAddress());
            restaurantStatement.setString(3, restaurant.getDescription());
            restaurantStatement.setInt(4, restaurant.getUserId());
            restaurantStatement.setString(5, restaurant.getImagePath());
            restaurantStatement.executeUpdate();

            // Retrieve generated restaurant ID
            ResultSet generatedKeys = restaurantStatement.getGeneratedKeys();
            int restaurantId;
            if (generatedKeys.next()) {
                restaurantId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated restaurant ID");
            }

            // Insert 10 tables for the restaurant
            for (int i = 0; i < 10; i++) {
                tableStatement.setBoolean(1, false); // Assuming tables start unoccupied
                tableStatement.setInt(2, restaurantId);
                tableStatement.executeUpdate();
            }
        }
    }


    @Override
    public void update(Restaurant restaurant) throws SQLException {
        String sql = "UPDATE restaurant SET name = ?, address = ?, description = ?, userId = ?, imagePath = ? WHERE restaurantId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, restaurant.getName());
            preparedStatement.setString(2, restaurant.getAddress());
            preparedStatement.setString(3, restaurant.getDescription());
            preparedStatement.setInt(4, restaurant.getUserId());
            preparedStatement.setString(5, restaurant.getImagePath());
            preparedStatement.setInt(6, restaurant.getRestaurantId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        // Step 1: Delete all reservations associated with the restaurant
        deleteReservationsByRestaurantId(id);

        // Step 2: Delete all tables associated with the restaurant
        deleteTablesByRestaurantId(id);

        // Step 3: Delete the restaurant
        deleteRestaurantById(id);
    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }

    // Step 1: Delete all reservations associated with the restaurant
    private void deleteReservationsByRestaurantId(int restaurantId) throws SQLException {
        String deleteReservationsSQL = "DELETE FROM reservation WHERE tableId IN (SELECT tableId FROM restauranttable WHERE restaurantId = ?)";
        try (PreparedStatement deleteReservationsStatement = connection.prepareStatement(deleteReservationsSQL)) {
            deleteReservationsStatement.setInt(1, restaurantId);
            deleteReservationsStatement.executeUpdate();
        }
    }

    // Step 2: Delete all tables associated with the restaurant
    private void deleteTablesByRestaurantId(int restaurantId) throws SQLException {
        String deleteTablesSQL = "DELETE FROM restauranttable WHERE restaurantId = ?";
        try (PreparedStatement deleteTablesStatement = connection.prepareStatement(deleteTablesSQL)) {
            deleteTablesStatement.setInt(1, restaurantId);
            deleteTablesStatement.executeUpdate();
        }
    }

    // Step 3: Delete the restaurant
    private void deleteRestaurantById(int restaurantId) throws SQLException {
        String deleteRestaurantSQL = "DELETE FROM restaurant WHERE restaurantId = ?";
        try (PreparedStatement deleteRestaurantStatement = connection.prepareStatement(deleteRestaurantSQL)) {
            deleteRestaurantStatement.setInt(1, restaurantId);
            deleteRestaurantStatement.executeUpdate();
        }

    }



    @Override
    public List<Restaurant> retrieveAll() throws SQLException {
        String sql = "SELECT * FROM restaurant";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Restaurant> restaurants = new ArrayList<>();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurantId"));
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setUserId(rs.getInt("userId"));
                restaurant.setImagePath(rs.getString("ImagePath"));
                restaurants.add(restaurant);
            }
            return restaurants;
        }
    }
    public List<Restaurant> specificretrieveAll() throws SQLException {
        String sql = "SELECT * FROM restaurant";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Restaurant> restaurants = new ArrayList<>();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setImagePath(rs.getString("imagePath"));
                restaurants.add(restaurant);
            }
            return restaurants;
        }
    }

    @Override
    public List<Restaurant> search(Object criteria) throws SQLException {
        List<Restaurant> restaurants = new ArrayList<>();
        if (criteria instanceof String) {
            String searchString = (String) criteria;
            String sql = "SELECT * FROM restaurant WHERE name LIKE ? OR address LIKE ? OR description LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String searchPattern = "%" + searchString + "%";
                preparedStatement.setString(1, searchPattern);
                preparedStatement.setString(2, searchPattern);
                preparedStatement.setString(3, searchPattern);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Restaurant restaurant = new Restaurant();
                        restaurant.setRestaurantId(rs.getInt("restaurantId"));
                        restaurant.setName(rs.getString("name"));
                        restaurant.setAddress(rs.getString("address"));
                        restaurant.setDescription(rs.getString("description"));
                        restaurant.setUserId(rs.getInt("userId"));
                        restaurant.setImagePath(rs.getString("imagePath"));

                        restaurants.add(restaurant);
                    }
                }
            }
        }
        return restaurants;
    }
    public List<String> getAllAddresses() throws SQLException {
        List<String> addresses = new ArrayList<>();
        String sql = "SELECT address FROM restaurant";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                addresses.add(resultSet.getString("address"));
            }
        }
        return addresses;
    }
}
