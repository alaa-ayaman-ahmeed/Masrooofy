package Logic;

import models.BudgetCycle;
import persistance.Datapersistance;

/**
 * Provides business logic for budget calculations.
 * Handles rollover adjustments when the budget changes.
 */
public class BudgetCalculationService {

    private Datapersistance db = new Datapersistance();

    /**
     * Handles daily rollover calculation for a budget cycle.
     * Determines whether the limit should increase or decrease
     * depending on remaining balance.
     *
     * @param budget active budget cycle
     */
    public void handleRollover(BudgetCycle budget) {

        double allowanceLimit = budget.getTotalAllowance();
        double currentSpending = budget.getTotalAllowance() - budget.getRemainingBalance();
        double remaining = allowanceLimit - currentSpending;

        if (remaining > 0) {
            recalculateIncreasedLimit(budget, remaining);
        } else {
            recalculateReducedLimit(budget, remaining);
        }
    }

    /**
     * Recalculates the budget when there is remaining unused money.
     *
     * @param budget active budget cycle
     * @param extra remaining amount available
     */
    public void recalculateIncreasedLimit(BudgetCycle budget, double extra) {
        double newLimit = budget.getTotalAllowance() + extra;
        System.out.println("New Limit Increased: " + newLimit);
    }

    /**
     * Recalculates the budget when there is overspending.
     *
     * @param budget active budget cycle
     * @param deficit exceeded amount
     */
    public void recalculateReducedLimit(BudgetCycle budget, double deficit) {
        double newLimit = budget.getTotalAllowance() + deficit;
        System.out.println("New Limit Reduced (Deficit): " + newLimit);
    }
}