package Logic;

import models.BudgetCycle;
import models.category;
import models.expense;
import persistance.Datapersistance;

import java.util.Date;

/**
 * Controller class responsible for managing budget cycles
 * and expense operations.
 * <p>
 * This class handles business logic related to creating
 * budget cycles, adding expenses, and retrieving
 * financial information from the persistence layer.
 * </p>
 */
public class BudgetController {

    /**
     * Reference to the data persistence layer.
     */
    private Datapersistance db;

    /**
     * Constructs a BudgetController object and initializes
     * the persistence layer.
     */
    public BudgetController() {
        db = new Datapersistance();
    }

    // USER STORY #1

    /**
     * Creates a new budget cycle after validating the input data.
     * <p>
     * Validation rules:
     * </p>
     * <ul>
     *     <li>Allowance must be greater than zero.</li>
     *     <li>End date must be after the start date.</li>
     * </ul>
     *
     * @param allowance the total allowance for the budget cycle
     * @param start the start date of the budget cycle
     * @param end the end date of the budget cycle
     * @return true if the budget cycle was created successfully,
     * otherwise false
     */
    public boolean createBudgetCycle(double allowance,
                                     Date start,
                                     Date end) {

        // validation
        if (allowance <= 0) {
            System.out.println("Allowance must be positive.");
            return false;
        }

        if (end.before(start) || end.equals(start)) {
            System.out.println("End date must be after start date.");
            return false;
        }

        BudgetCycle cycle = new BudgetCycle(
                0,
                allowance,
                start,
                end,
                allowance
        );

        db.createBudgetCycle(cycle);

        System.out.println("Budget Cycle Created!");
        System.out.println("Safe Daily Limit = "
                + db.getSafeDailyLimit());

        return true;
    }

    // USER STORY #2

    /**
     * Adds a new expense to the current active budget cycle.
     * <p>
     * Validation rules:
     * </p>
     * <ul>
     *     <li>Expense amount must be greater than zero.</li>
     *     <li>An active budget cycle must exist.</li>
     * </ul>
     *
     * @param amount the expense amount
     * @param cat the category of the expense
     * @return true if the expense was successfully added,
     * otherwise false
     */
    public boolean addExpense(double amount,
                              category cat) {

        // validation
        if (amount <= 0) {
            System.out.println("Invalid expense amount.");
            return false;
        }

        BudgetCycle current = db.getCurrentCycle();

        if (current == null) {
            System.out.println("No active budget cycle.");
            return false;
        }

        expense e = new expense(
                0,
                amount,
                new Date(),
                cat
        );

        db.addExpense(e);

        System.out.println("Expense Saved!");
        System.out.println("Updated Daily Limit = "
                + db.getSafeDailyLimit());

        return true;
    }

    // Helper Methods

    /**
     * Retrieves the current safe daily spending limit.
     *
     * @return the safe daily limit
     */
    public double getDailyLimit() {
        return db.getSafeDailyLimit();
    }

    /**
     * Retrieves the remaining balance for the current budget cycle.
     *
     * @return the remaining balance
     */
    public double getRemainingBalance() {
        return db.getRemainingBalance();
    }
}