package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MyDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/greenmenu";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    private static MyDatabase instance;


    private static final Logger logger = Logger.getLogger(MyDatabase.class.getName());

    private MyDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connected to the database successfully");
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static MyDatabase getInstance() {
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // not used yet
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection to the database closed successfully");
            } catch (SQLException e) {
                logger.severe("Failed to close the database connection: " + e.getMessage());
            }
        }
    }
}
