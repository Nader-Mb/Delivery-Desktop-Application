package services;

import models.Reservation;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {

    private final Connection connection;

    public ReservationService() throws SQLException {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {

    }

    @Override
    public void modifier(Reservation reservation) throws SQLException {

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
    public List<Reservation> recuperer() throws SQLException {
        return null;
    }

    @Override
    public void add(Reservation reservation) throws SQLException {
        // Step 1: Find an unoccupied table
        int tableId = findUnoccupiedTable();

        // Step 2: Add the reservation
        addReservation(reservation, tableId);

        // Step 3: Mark the table as occupied
        markTableAsOccupied(tableId);
    }

    // Step 1: Find an unoccupied table
    private int findUnoccupiedTable() throws SQLException {
        String selectTableSQL = "SELECT tableId FROM restauranttable WHERE isOccupied = 0 LIMIT 1";

        try (PreparedStatement selectTableStatement = connection.prepareStatement(selectTableSQL);
             ResultSet resultSet = selectTableStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("tableId");
            } else {
                throw new SQLException("No unoccupied tables available.");
            }
        }
    }

    // Step 2: Add the reservation
    private void addReservation(Reservation reservation, int tableId) throws SQLException {
        String insertReservationSQL = "INSERT INTO reservation (userId, dateTime, tableId, numberOfPersons) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertReservationStatement = connection.prepareStatement(insertReservationSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertReservationStatement.setInt(1, reservation.getUserId());
            insertReservationStatement.setTimestamp(2, Timestamp.valueOf(reservation.getDateTime()));
            insertReservationStatement.setInt(3, tableId);
            insertReservationStatement.setInt(4, reservation.getNumberOfPersons());
            insertReservationStatement.executeUpdate();

            try (ResultSet generatedKeys = insertReservationStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setReservationId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Step 3: Mark the table as occupied
    private void markTableAsOccupied(int tableId) throws SQLException {
        String updateTableSQL = "UPDATE restauranttable SET isOccupied = 1 WHERE tableId = ?";

        try (PreparedStatement updateTableStatement = connection.prepareStatement(updateTableSQL)) {
            updateTableStatement.setInt(1, tableId);
            updateTableStatement.executeUpdate();
        }
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET userId = ?, dateTime = ?, tableId = ?, numberOfPersons = ? WHERE reservationId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getUserId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(reservation.getDateTime()));
            preparedStatement.setInt(3, reservation.getTableId());
            preparedStatement.setInt(4, reservation.getNumberOfPersons());
            preparedStatement.setInt(5, reservation.getReservationId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Reservation reservation) throws SQLException {
        // Step 1: Get the table ID associated with the reservation
        int tableId = reservation.getTableId();

        // Step 2: Delete the reservation
        deleteReservation(reservation);

        // Step 3: Mark the table as unoccupied
        markTableAsUnoccupied(tableId);
    }

    // Step 2: Delete the reservation
    private void deleteReservation(Reservation reservation) throws SQLException {
        String deleteReservationSQL = "DELETE FROM reservation WHERE reservationId = ?";

        try (PreparedStatement deleteReservationStatement = connection.prepareStatement(deleteReservationSQL)) {
            deleteReservationStatement.setInt(1, reservation.getReservationId());
            deleteReservationStatement.executeUpdate();
        }
    }

    // Step 3: Mark the table as unoccupied
    private void markTableAsUnoccupied(int tableId) throws SQLException {
        String updateTableSQL = "UPDATE restauranttable SET isOccupied = 0 WHERE tableId = ?";

        try (PreparedStatement updateTableStatement = connection.prepareStatement(updateTableSQL)) {
            updateTableStatement.setInt(1, tableId);
            updateTableStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        // Step 1: Find the reservation associated with the ID
        Reservation reservation = findReservationById(id);
        if (reservation == null) {
            // Handle the case where the reservation with the given ID does not exist
            System.out.println("Reservation with ID " + id + " does not exist.");
            return;
        }

        // Step 2: Get the table ID associated with the reservation
        int tableId = reservation.getTableId();

        // Step 3: Delete the reservation
        deleteReservationById(id);

        // Step 4: Mark the table as unoccupied
        markTableAsUnoccupied(tableId);
    }

    // Step 1: Find the reservation associated with the ID
    private Reservation findReservationById(int id) throws SQLException {
        // Step 1: Find the reservation associated with the ID
            String selectReservationSQL = "SELECT * FROM reservation WHERE reservationId = ?";
            Reservation reservation = null;

            try (PreparedStatement selectReservationStatement = connection.prepareStatement(selectReservationSQL)) {
                selectReservationStatement.setInt(1, id);
                try (ResultSet resultSet = selectReservationStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // If a reservation is found, create a Reservation object and populate it with data from the result set
                        reservation = new Reservation();
                        reservation.setReservationId(resultSet.getInt("reservationId"));
                        reservation.setUserId(resultSet.getInt("userId"));
                        reservation.setDateTime(resultSet.getTimestamp("dateTime").toLocalDateTime());
                        reservation.setTableId(resultSet.getInt("tableId"));
                        reservation.setNumberOfPersons(resultSet.getInt("numberOfPersons"));
                    }
                }
            }

            return reservation; // Return the reservation if found, otherwise return null
        }



    // Step 3: Delete the reservation by ID
    private void deleteReservationById(int id) throws SQLException {
        String deleteReservationSQL = "DELETE FROM reservation WHERE reservationId = ?";

        try (PreparedStatement deleteReservationStatement = connection.prepareStatement(deleteReservationSQL)) {
            deleteReservationStatement.setInt(1, id);
            deleteReservationStatement.executeUpdate();
        }
    }

    // Step 4: Mark the table as unoccupied see the function above in add reservation




    @Override
    public List<Reservation> retrieveAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservationId");
                int userId = resultSet.getInt("userId");
                LocalDateTime dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();
                int tableId = resultSet.getInt("tableId");
                int numberOfPersons = resultSet.getInt("numberOfPersons");
                Reservation reservation = new Reservation(reservationId, userId, dateTime, tableId, numberOfPersons);
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    @Override
    public List<Reservation> search(Object criteria) throws SQLException {
        List<Reservation> matchingReservations = new ArrayList<>();

        if (!(criteria instanceof String || criteria instanceof Integer)) {
            throw new IllegalArgumentException("Criteria must be a String representing the restaurant name, an Integer representing the number of persons, or a Number as part of the datetime.");
        }

        String sql = "SELECT reservation.* " +
                "FROM reservation " +
                "INNER JOIN restauranttable ON reservation.tableId = restauranttable.tableId " +
                "INNER JOIN restaurant ON restauranttable.restaurantId = restaurant.restaurantId " +
                "WHERE restaurant.name LIKE ? OR reservation.numberOfPersons = ? OR reservation.dateTime LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (criteria instanceof String) {
                String restaurantName = (String) criteria;
                preparedStatement.setString(1, "%" + restaurantName + "%");
                preparedStatement.setNull(2, Types.INTEGER);
                preparedStatement.setString(3, "%" + restaurantName + "%");
            } else if (criteria instanceof Integer) {
                int numberOfPersons = (Integer) criteria;
                preparedStatement.setNull(1, Types.VARCHAR);
                preparedStatement.setInt(2, numberOfPersons);
                preparedStatement.setNull(3, Types.VARCHAR);
            } else if (criteria instanceof Number) {
                Number dateTime = (Number) criteria;
                preparedStatement.setNull(1, Types.VARCHAR);
                preparedStatement.setNull(2, Types.INTEGER);
                preparedStatement.setString(3, "%" + dateTime + "%");
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int reservationId = resultSet.getInt("reservationId");
                    int userId = resultSet.getInt("userId");
                    LocalDateTime dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();
                    int tableId = resultSet.getInt("tableId");
                    int numberOfPersons = resultSet.getInt("numberOfPersons");

                    Reservation reservation = new Reservation(reservationId, userId, dateTime, tableId, numberOfPersons);
                    matchingReservations.add(reservation);
                }
            }
        }

        return matchingReservations;
    }




    public String getRestaurantNameByResId(int id) throws SQLException {
        String sql = "SELECT restaurantId FROM restauranttable WHERE tableId = ?";
        String sql2 = "SELECT name FROM restaurant WHERE restaurantId = ?";
        String restaurantName = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int restaurantId = resultSet.getInt("restaurantId");
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {
                        preparedStatement2.setInt(1, restaurantId);
                        try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
                            if (resultSet2.next()) {
                                restaurantName = resultSet2.getString("name");
                            }
                        }
                    }
                }
            }
        }

        return restaurantName;
    }
    public List<Reservation> getReservationsByRestaurant(int restaurantId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE restaurantId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    // Populate reservation object with data from ResultSet
                    reservation.setReservationId(rs.getInt("id"));
                    // Populate other fields similarly
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving reservations by restaurant ID", e);
        }
        return reservations;
    }


}
