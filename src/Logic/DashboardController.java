package Logic;

import models.category;
import persistance.Datapersistance;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class responsible for handling dashboard-related operations.
 * <p>
 * This class acts as an intermediary between the presentation layer
 * and the persistence layer by retrieving and processing financial data
 * required for the dashboard.
 * </p>
 */
public class DashboardController {

    /**
     * Reference to the data persistence layer.
     */
    private Datapersistance db;

    /**
     * Constructs a DashboardController object and initializes
     * the persistence layer object.
     */
    public DashboardController() {
        db = new Datapersistance();
    }

    // User Story 4

    /**
     * Retrieves the safe daily spending limit for the user.
     *
     * @return the safe daily spending limit
     */
    public double getSafeDailyLimit() {
        return db.getSafeDailyLimit();
    }

    /**
     * Retrieves the remaining balance available in the current budget cycle.
     *
     * @return the remaining balance
     */
    public double getRemainingBalance() {
        return db.getRemainingBalance();
    }

    /**
     * Retrieves the number of remaining days in the current budget cycle.
     *
     * @return the remaining number of days
     */
    public long getRemainingDays() {
        return db.getRemainingDays();
    }

    /**
     * Checks whether the current day is the final day
     * of the budget cycle.
     *
     * @return true if it is the final day, otherwise false
     */
    public boolean isFinalDay() {
        return db.getRemainingDays() == 1;
    }

    // User Story 4

    /**
     * Retrieves the total expenses grouped by category.
     *
     * @return a map containing each category and its total expense amount
     */
    public Map<category, Double> getCategoryTotals() {
        return db.getTotalExpensesByCategory();
    }

    /**
     * Calculates the percentage of total expenses for each category.
     * <p>
     * The percentage is calculated using the formula:
     * </p>
     *
     * :contentReference[oaicite:0]{index=0}
     *
     * @return a map containing each category and its percentage
     * contribution to the total expenses
     */
    public Map<category, Double> getCategoryPercentages() {

        Map<category, Double> percentages =
                new HashMap<>();

        Map<category, Double> totals =
                db.getTotalExpensesByCategory();

        double overall =
                db.getTotalExpenses();

        if (overall == 0) {
            return percentages;
        }

        for (Map.Entry<category, Double> entry : totals.entrySet()) {

            double percentage =
                    (entry.getValue() / overall) * 100;

            percentages.put(
                    entry.getKey(),
                    percentage
            );
        }

        return percentages;
    }
}