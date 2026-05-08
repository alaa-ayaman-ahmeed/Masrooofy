package persistance;

import java.sql.*;
import java.util.*;
import java.util.Date;

import models.*;

/**
 * Provides database persistence operations for the application.
 * Handles CRUD operations for expenses, users, budget cycles, and reporting queries.
 */
public class Datapersistance implements IPersistence {

    /**
     * Adds a new expense record to the database.
     *
     * @param e the expense object to be stored
     */
    public void addExpense(expense e) {
        String sql = "INSERT INTO expense(amount, date, categoryId) VALUES(?,?,?)";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            double remaining = getRemainingBalance();

            if (remaining <= 0) {
                System.out.println("Budget exhausted. Cannot add more expenses.");
                return;
            }
            stmt.setDouble(1, e.getAmount());

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd");

            stmt.setString(2, sdf.format(e.getDate()));
            stmt.setInt(3, e.getCat().getId());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Expense added! Rows affected: " + rowsAffected);

        } catch (Exception ex) {
            System.out.println("Error adding expense: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all expenses from the database.
     *
     * @return list of all expenses
     */
    public List<expense> getAllExpenses() {
        List<expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expense";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                category cat = category.fromId(rs.getInt("categoryId"));
                Date date = parseDate(rs.getString("date"));

                expense e = new expense(
                        rs.getInt("expenseID"),
                        rs.getDouble("amount"),
                        date,
                        cat
                );

                list.add(e);
            }

            System.out.println("Found " + list.size() + " expenses");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return list;
    }

    /**
     * Parses a date string into a Date object using multiple formats.
     *
     * @param dateString the date string from the database
     * @return parsed Date object, or current date if parsing fails
     */
    private Date parseDate(String dateString) {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e1) {
            try {
                return new java.text.SimpleDateFormat(
                        "EEE MMM dd HH:mm:ss zzz yyyy",
                        Locale.ENGLISH
                ).parse(dateString);
            } catch (Exception e2) {
                System.out.println("Could not parse date: " + dateString);
                return new Date();
            }
        }
    }

    /**
     * Updates the amount of a specific expense.
     *
     * @param id the expense ID
     * @param newAmount the new amount value
     */
    public void updateExpense(int id, double newAmount) {
        String sql = "UPDATE expense SET amount=? WHERE expenseID=?";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newAmount);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " expense(s)");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Deletes an expense by ID.
     *
     * @param id the expense ID to delete
     */
    public void deleteExpense(int id) {
        String sql = "DELETE FROM expense WHERE expenseID=?";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " expense(s)");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the user object to store
     */
    public void createUser(user user) {
        String sql = "INSERT INTO users(userName, isLocked) VALUES (?, ?)";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setInt(2, user.locked() ? 1 : 0);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error with user: " + e.getMessage());
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user ID
     * @return the user object if found, otherwise null
     */
    public user getUserById(int id) {
        String sql = "SELECT * FROM users WHERE userId = ?";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new user(
                        rs.getInt("userID"),
                        rs.getString("userName"),
                        rs.getInt("isLocked") == 1
                );
            }

        } catch (Exception e) {
            System.out.println("Error with user: " + e.getMessage());
        }

        return null;
    }

    /**
     * Locks a user account.
     *
     * @param userId the user ID to lock
     */
    public void lockUser(int userId) {
        String sql = "UPDATE users SET isLocked = 1 WHERE userID = ?";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error with user: " + e.getMessage());
        }
    }

    /**
     * Deactivates any currently active budget cycle.
     */
    private void deactivateCurrentCycle() {
        String sql = "UPDATE BudgetCycle SET isActive = 0 WHERE isActive = 1";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error deactivating cycle: " + e.getMessage());
        }
    }

    /**
     * Creates a new budget cycle and deactivates previous active cycles.
     *
     * @param cycle the budget cycle to create
     */
    public void createBudgetCycle(BudgetCycle cycle) {
        deactivateCurrentCycle();

        String sql = "INSERT INTO BudgetCycle (totalAllowance, startDate, endDate, isActive) VALUES (?, ?, ?, ?)";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd");

            stmt.setDouble(1, cycle.getTotalAllowance());
            stmt.setString(2, sdf.format(cycle.getStartDate()));
            stmt.setString(3, sdf.format(cycle.getEndDate()));
            stmt.setInt(4, 1);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error saving budget cycle: " + e.getMessage());
        }
    }

    /**
     * Retrieves the currently active budget cycle.
     *
     * @return active BudgetCycle or null if none exists
     */
    public BudgetCycle getCurrentCycle() {
        String sql = "SELECT * FROM BudgetCycle WHERE isActive = 1 LIMIT 1";

        try (Connection conn = connection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {

                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("yyyy-MM-dd");

                return new BudgetCycle(
                        rs.getInt("cycleID"),
                        rs.getDouble("totalAllowance"),
                        sdf.parse(rs.getString("startDate")),
                        sdf.parse(rs.getString("endDate")),
                        0
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    /**
     * Updates the total allowance of the active budget cycle.
     *
     * @param newAllowance the new allowance value
     */
    public void updateCycle(double newAllowance) {
        String sql = "UPDATE BudgetCycle SET totalAllowance = ? WHERE isActive = 1";

        try (Connection conn = connection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newAllowance);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error updating cycle: " + e.getMessage());
        }
    }

    /**
     * Calculates total expenses across all records.
     *
     * @return total spent amount
     */
    public double getTotalExpenses() {
        String sql = "SELECT SUM(amount) AS total FROM expense";

        try (Connection conn = connection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                double total = rs.getDouble("total");
                return rs.wasNull() ? 0 : total;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Calculates remaining days in the active budget cycle.
     *
     * @return number of remaining days
     */
    public long getRemainingDays() {

        BudgetCycle cycle = getCurrentCycle();
        if (cycle == null) return 0;

        Date today = new Date();
        Date end = cycle.getEndDate();

        long diff = end.getTime() - today.getTime();

        long days = diff / (1000 * 60 * 60 * 24);

        return Math.max(days, 1);
    }

    /**
     * Calculates remaining budget balance.
     *
     * @return remaining balance
     */
    public double getRemainingBalance() {
        BudgetCycle cycle = getCurrentCycle();

        if (cycle == null) return 0;

        return cycle.getTotalAllowance() - getTotalExpenses();
    }

    /**
     * Calculates safe daily spending limit.
     *
     * @return recommended daily budget limit
     */
    public double getSafeDailyLimit() {

        double remainingBalance = getRemainingBalance();
        long remainingDays = getRemainingDays();

        if (remainingBalance <= 0) {
            return 0;
        }

        return remainingBalance / remainingDays;
    }

    /**
     * Retrieves total expenses grouped by category.
     *
     * @return map of category to total amount spent
     */
    public Map<category, Double> getTotalExpensesByCategory() {

        Map<category, Double> map = new HashMap<>();

        String sql = "SELECT categoryId, SUM(amount) as total FROM expense GROUP BY categoryId";

        try (Connection conn = connection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                category cat = category.fromId(rs.getInt("categoryId"));
                double total = rs.getDouble("total");

                map.put(cat, total);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return map;
    }
    public void resetAllData() {
        try (Connection conn = connection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DELETE FROM expense");
            stmt.execute("DELETE FROM users");
            stmt.execute("DELETE FROM BudgetCycle");

            System.out.println("Database reset completed!");

        } catch (Exception e) {
            System.out.println("Reset error: " + e.getMessage());
        }
    }
}