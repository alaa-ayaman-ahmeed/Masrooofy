package persistance;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Responsible for initializing the database schema.
 * Creates all required tables if they do not already exist.
 */
public class DatabaseSetup {

    /**
     * Creates all application tables in the SQLite database.
     * This method executes SQL CREATE TABLE statements for:
     * <ul>
     *   <li>expense</li>
     *   <li>BudgetCycle</li>
     *   <li>users</li>
     *   <li>category</li>
     * </ul>
     */
    public static void createTables() {

        try (Connection conn = connection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS expense(
                        expenseID INTEGER PRIMARY KEY AUTOINCREMENT,
                        amount REAL,
                        date TEXT,
                        categoryId INTEGER
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS BudgetCycle(
                        budgetID INTEGER PRIMARY KEY AUTOINCREMENT,
                        totalAllowance REAL,
                        startDate TEXT,
                        endDate TEXT,
                        isActive INTEGER
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS users(
                        userID INTEGER PRIMARY KEY AUTOINCREMENT,
                        userName TEXT,
                        isLocked INTEGER
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS category(
                        categoryID INTEGER PRIMARY KEY,
                        name TEXT
                    )
                    """);

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}