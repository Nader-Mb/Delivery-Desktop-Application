package services;

import models.Reservation;
import models.User;
import utils.MyDatabase;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class userService implements IService<User> {

    private Connection connection;

    public userService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "insert into user (FirstName,LastName,Email,Address,Role,Number,Rating,Password) values (?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getAddress());
        preparedStatement.setString(5, user.getRole());
        preparedStatement.setInt(6, user.getNumber());
        preparedStatement.setDouble(7, user.getRating());
        preparedStatement.setString(8,user.getPassword());
        preparedStatement.executeUpdate();

    }

    // In userService.java

    public String generateVerificationCode() {
        // Generate a random 6-digit verification code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void ResetPassword(String email, String password) {
        try {

            String req = "UPDATE user SET Password = ? WHERE Email = ?";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, password);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("Password updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }

    public int ChercherMail(String email) {

        try {
            String req = "SELECT * from user WHERE Email ='" + email + "'  ";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
               return 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    @Override
    public void modifier(User user) throws SQLException {

        String sql = "UPDATE user SET FirstName = ?,LastName = ?,Email = ?,Address = ?,Role = ?,Number = ?,Rating = ?,Password = ? WHERE idUser = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getRole());
            statement.setInt(6, user.getNumber());
            statement.setDouble(7, user.getRating());
            statement.setString(8, user.getPassword());
            statement.setInt(9, user.getId());
            statement.executeUpdate();


        }

    @Override
    public void modifier(int number, int goal, String title, String assossiation, String type, String desc, int id) throws SQLException {

    }

    @Override
    public void modifier(int idCamp, float valeurDon, int idDoantor, int idDon) throws SQLException {

    }

    public void modifierByEmail(User user,String email) throws SQLException {

        String sql = "UPDATE user SET FirstName = ?,LastName = ?,Email = ?,Address = ?,Role = ?,Number = ?,Rating = ?,Password = ? WHERE email = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getAddress());
        statement.setString(5, user.getRole());
        statement.setInt(6, user.getNumber());
        statement.setDouble(7, user.getRating());
        statement.setString(8, user.getPassword());
        statement.setString(9, email);
        statement.executeUpdate();


    }



    @Override
    public void supprimer(int id) throws SQLException {
        String requete = "DELETE FROM user WHERE idUser = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }


    public void supprimerEmail(String email) throws SQLException {
        String requete = "DELETE FROM user WHERE Email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<User> recuperer() throws SQLException {
        String sql = "SELECT * FROM `user`";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User p = new User();
            p.setId(rs.getInt("idUser"));
            p.setFirstName(rs.getString("FirstName"));
            p.setLastName(rs.getString("LastName"));
            p.setEmail(rs.getString("Email"));
            p.setAddress(rs.getString("Address"));
            p.setRole(rs.getString("Role"));
            p.setRating(rs.getDouble("Rating"));
            p.setNumber(rs.getInt("Number"));
            p.setPassword(rs.getString("Password"));




            users.add(p);

        }
        return users;
    }

    @Override
    public void add(User entity) throws SQLException {

    }

    @Override
    public void update(User entity) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }

    @Override
    public List<User> retrieveAll() throws SQLException {
        return null;
    }

    @Override
    public List<User> search(Object criteria) throws SQLException {
        return null;
    }

    public boolean checkEmailExists(String email) {

        boolean result = false;

        try {
            String req = "SELECT * FROM user WHERE Email = ?";
            PreparedStatement st = connection.prepareStatement(req);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            result = rs.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return result;
    }

    public User readById(int id) {
        User u = null;
        try  {
            String req ="SELECT * from user WHERE idUser = '" + id +"'";
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                u= new User(rs.getInt("Id"),rs.getString("First Name"), rs.getString("LastName"),rs.getString("Email"), rs.getString("Address"), rs.getDouble("Rating"),rs.getString("Role"), rs.getInt("Number"),"");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return u;
    }


    public  int getUserId(String emailU){
        String req ="SELECT id FROM users WHERE email = emailU ";

        int x=0;
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
             x= rs.getInt("Id");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return x;

    }



    public List<User> searchUsersByEmailStartingWithLetter(String searchAttribute,String startingLetter) throws SQLException{
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM user WHERE " + searchAttribute + " LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, startingLetter + "%");
            ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    User p = new User();
                    p.setId(rs.getInt("idUser"));
                    p.setFirstName(rs.getString("FirstName"));
                    p.setLastName(rs.getString("LastName"));
                    p.setEmail(rs.getString("Email"));
                    p.setAddress(rs.getString("Address"));
                    p.setRole(rs.getString("Role"));
                    p.setRating(rs.getDouble("Rating"));
                    p.setNumber(rs.getInt("Number"));
                    p.setPassword(rs.getString("Password"));




                    users.add(p);

                }

        } catch (SQLException ex) {
            System.out.println("Error while searching for users by email: " + ex.getMessage());
        }

        return users;
    }

    public String authentification(String email, String password) {
        String Role = null;


        try{


        String req = "SELECT * from user WHERE Email = ? AND Password = ?";

            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
             if(rs.next()) {
                 return rs.getString("Role");
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }


        return Role;
    }


    public String getHashedPasswordByEmail(String email) {
        String hashedPassword = null;
        String req = "SELECT Password FROM user WHERE Email = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hashedPassword = rs.getString("Password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hashedPassword;
    }
    public User recupererUtilisateur(int id) throws SQLException {
        String sql = "SELECT * FROM `user` WHERE idUser = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("idUser"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setEmail(rs.getString("Email"));
            user.setAddress(rs.getString("Address"));
            user.setRole(rs.getString("Role"));
            user.setRating(rs.getDouble("Rating"));
            user.setNumber(rs.getInt("Number"));
        }
        return user;
    }
    public User getUserById(int idUser) throws SQLException {
        User user = null;
        String sql  = "SELECT * FROM user WHERE idUser = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("idUser");
                    String firstName = rs.getString("Firstname");
                    String lastName = rs.getString("Lastname");
                    String email = rs.getString("Email");
                    String address = rs.getString("Address");
                    String role = rs.getString("Role");
                    int number = rs.getInt("Number");
                    float rating = rs.getFloat("Rating");

                    user = new User(id, firstName, lastName, email, address, role, number, rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
