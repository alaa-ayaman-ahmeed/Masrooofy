package persistance;

import models.*;
import java.util.List;
import java.util.Map;

/**
 * Defines the contract for persistence operations in the system.
 * Provides CRUD operations for expenses, users, and budget cycles,
 * as well as analytical queries for budgeting calculations.
 */
public interface IPersistence {

    /**
     * Adds a new expense to the database.
     *
     * @param expense the expense object to be stored
     */
    void addExpense(expense expense);

    /**
     * Retrieves all expenses from the database.
     *
     * @return a list of all stored expenses
     */
    List<expense> getAllExpenses();

    /**
     * Deletes an expense by its ID.
     *
     * @param expenseId the ID of the expense to delete
     */
    void deleteExpense(int expenseId);

    /**
     * Updates the amount of an existing expense.
     *
     * @param id the expense ID
     * @param newAmount the new amount value
     */
    void updateExpense(int id, double newAmount);

    /**
     * Creates a new budget cycle.
     *
     * @param cycle the budget cycle to create
     */
    void createBudgetCycle(BudgetCycle cycle);

    /**
     * Retrieves the currently active budget cycle.
     *
     * @return the active BudgetCycle, or null if none exists
     */
    BudgetCycle getCurrentCycle();

    /**
     * Updates the total allowance of the active budget cycle.
     *
     * @param totalAllowance the new total allowance value
     */
    void updateCycle(double totalAllowance);

    /**
     * Calculates the total amount of all expenses.
     *
     * @return total spent amount
     */
    double getTotalExpenses();

    /**
     * Creates a new user in the system.
     *
     * @param user the user object to create
     */
    void createUser(user user);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user ID
     * @return the user object if found, otherwise null
     */
    user getUserById(int id);

    /**
     * Locks a user account to prevent further actions.
     *
     * @param userId the ID of the user to lock
     */
    void lockUser(int userId);

    /**
     * Calculates the number of remaining days in the current budget cycle.
     *
     * @return remaining days in the cycle
     */
    long getRemainingDays();

    /**
     * Calculates the remaining budget balance.
     *
     * @return remaining amount of money
     */
    double getRemainingBalance();

    /**
     * Calculates the safe daily spending limit based on remaining balance and days.
     *
     * @return recommended daily spending limit
     */
    double getSafeDailyLimit();

    /**
     * Retrieves total expenses grouped by category.
     *
     * @return a map where key is category and value is total spent amount
     */
    Map<category, Double> getTotalExpensesByCategory();
}