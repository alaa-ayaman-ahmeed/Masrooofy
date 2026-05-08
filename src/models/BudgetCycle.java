package models;

import java.util.Date;

/**
 * Represents a budgeting cycle for the user.
 * A budget cycle stores the total allowance, cycle duration,
 * and remaining balance.
 */
public class BudgetCycle {
    private int budgetID;
    private double totalAllowance;
    private Date start;
    private Date end;
    private double remainingBalance;

    /**
     * Creates a new budget cycle.
     *
     * @param id unique budget cycle identifier
     * @param allowance total allowance assigned to the cycle
     * @param start start date of the cycle
     * @param end end date of the cycle
     * @param remaining remaining balance in the cycle
     */
    public BudgetCycle(int id,double allowance,Date start, Date end,double remaining)
    {
        this.budgetID=id;
        this.totalAllowance=allowance;
        this.start=start;
        this.end=end;
        this.remainingBalance=remaining;
    }

    /**
     * Returns the budget cycle identifier.
     *
     * @return budget cycle id
     */
    public int getBudgetID()
    {
        return budgetID;
    }

    /**
     * Returns the total allowance of the cycle.
     *
     * @return total allowance
     */
    public double getTotalAllowance()
    {
        return totalAllowance;
    }

    /**
     * Returns the remaining balance.
     *
     * @return remaining balance
     */
    public double getRemainingBalance()
    {
        return remainingBalance;
    }

    /**
     * Returns the start date of the cycle.
     *
     * @return cycle start date
     */
    public Date getStartDate()
    {
        return start;
    }

    /**
     * Returns the end date of the cycle.
     *
     * @return cycle end date
     */
    public Date getEndDate()
    {
        return end;
    }

    /**
     * Updates the total allowance.
     *
     * @param l new total allowance
     */
    public void setTotalAllowance(double l)
    {
        this.totalAllowance=l;
    }

    /**
     * Updates the start date of the cycle.
     *
     * @param d new start date
     */
    public void setStartDate(Date d)
    {
        this.start=d;
    }

    /**
     * Updates the end date of the cycle.
     *
     * @param d new end date
     */
    public void setEndDate(Date d)
    {
        this.end=d;
    }
}