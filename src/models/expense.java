package models;

import java.util.Date;

/**
 * Represents a single expense transaction recorded by the user.
 * Each expense contains an id, amount, date, and category.
 */
public class expense
{
    private int expenseID;
    private double amount;
    private Date date;
    private category cat;

    /**
     * Creates a new expense object.
     *
     * @param id unique expense identifier
     * @param amount expense amount
     * @param date date of the expense
     * @param cat category of the expense
     */
    public expense(int id,double amount , Date date, category cat){
        this.expenseID=id;
        this.amount=amount;
        this.date=date;
        this.cat=cat;
    }

    /**
     * Returns the expense amount.
     *
     * @return expense amount
     */
    public double getAmount(){
        return amount;
    }

    /**
     * Updates the expense amount.
     *
     * @param am new expense amount
     */
    public void setAmount(double am)
    {
        this.amount=am;
    }

    /**
     * Returns the date of the expense.
     *
     * @return expense date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Updates the expense date.
     *
     * @param d new expense date
     */
    public void setDate(Date d) {
        this.date=d;
    }

    /**
     * Returns the expense id.
     *
     * @return expense identifier
     */
    public int getExpenseID()
    {
        return expenseID;
    }

    /**
     * Returns the expense category.
     *
     * @return expense category
     */
    public category getCat() {
        return cat;
    }

    /**
     * Updates the expense category.
     *
     * @param c new expense category
     */
    public void setCat(category c)
    {
        this.cat=c;
    }
}