package persistance;

import java.sql.Connection;
import java.sql.Statement;

/**
 * One-time database initialization utility.
 * Used to populate the category table with default values.
 *
 * This class should be executed only once during initial setup.
 */
public class OneTimeSetup {

    /**
     * Main method used to insert default categories into the database.
     * Uses INSERT OR IGNORE to avoid duplicate entries.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Default category seed data
        String[] categories = {
                "INSERT OR IGNORE INTO category VALUES(1, 'Food')",
                "INSERT OR IGNORE INTO category VALUES(2, 'Transportation')",
                "INSERT OR IGNORE INTO category VALUES(3, 'Shopping')",
                "INSERT OR IGNORE INTO category VALUES(4, 'Entertainment')",
                "INSERT OR IGNORE INTO category VALUES(5, 'Bills')",
                "INSERT OR IGNORE INTO category VALUES(6, 'Healthcare')",
                "INSERT OR IGNORE INTO category VALUES(7, 'Education')",
                "INSERT OR IGNORE INTO category VALUES(8, 'Other')"
        };

        try (Connection conn = connection.connect();
             Statement stmt = conn.createStatement()) {

            for (String sql : categories) {
                stmt.execute(sql);
            }

            System.out.println("Categories added successfully!");

        } catch (Exception e) {
            System.out.println("Error inserting categories: " + e.getMessage());
        }
    }
}