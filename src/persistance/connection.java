package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class responsible for managing the database connection.
 * Uses SQLite as the underlying database.
 */
public class connection {

    /**
     * Path to the SQLite database file.
     */
    private static final String URL = "jdbc:sqlite:C:/Users/Dell/IdeaProjects/masroofy/masroofy.db";

    /**
     * Establishes and returns a connection to the SQLite database.
     *
     * @return a valid {@link Connection} object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}