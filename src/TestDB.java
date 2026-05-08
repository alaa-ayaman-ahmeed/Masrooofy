import models.*;
import persistance.Datapersistance;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class TestDB {

    public static void main(String[] args) {

        Datapersistance db = new Datapersistance();

        try {

            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");

            // =========================
            // CREATE USER
            // =========================

            System.out.println("===== CREATE USER =====");

            user u = new user(0, "Ahmed", false);

            db.createUser(u);

            System.out.println("User created successfully");


            // =========================
            // CREATE BUDGET CYCLE
            // =========================

            System.out.println("\n===== CREATE BUDGET CYCLE =====");

            BudgetCycle cycle = new BudgetCycle(
                    0,
                    5000,
                    sdf.parse("2026-05-01"),
                    sdf.parse("2026-05-31"),
                    0
            );

            db.createBudgetCycle(cycle);

            System.out.println("Budget cycle created");


            // =========================
            // ADD EXPENSES
            // =========================

            System.out.println("\n===== ADD EXPENSES =====");

            expense e1 = new expense(
                    0,
                    500,
                    sdf.parse("2026-05-05"),
                    category.FOOD
            );

            expense e2 = new expense(
                    0,
                    300,
                    sdf.parse("2026-05-06"),
                    category.TRANSPORT
            );

            expense e3 = new expense(
                    0,
                    600,
                    sdf.parse("2026-05-07"),
                    category.SHOPPING
            );

            db.addExpense(e1);
            db.addExpense(e2);
            db.addExpense(e3);

            System.out.println("Expenses added successfully");


            // =========================
            // GET ALL EXPENSES
            // =========================

            System.out.println("\n===== ALL EXPENSES =====");

            List<expense> expenses = db.getAllExpenses();

            for (expense e : expenses) {

                System.out.println(
                        "ID: " + e.getExpenseID()
                                + " | Amount: " + e.getAmount()
                                + " | Category: " + e.getCat()
                                + " | Date: " + sdf.format(e.getDate())
                );
            }


            // =========================
            // UPDATE EXPENSE
            // =========================

            System.out.println("\n===== UPDATE EXPENSE =====");

            db.updateExpense(1, 900);

            System.out.println("Expense updated");


            // =========================
            // DELETE EXPENSE
            // =========================

            System.out.println("\n===== DELETE EXPENSE =====");

            db.deleteExpense(2);

            System.out.println("Expense deleted");


            // =========================
            // CURRENT CYCLE
            // =========================

            System.out.println("\n===== CURRENT CYCLE =====");

            BudgetCycle current = db.getCurrentCycle();

            if (current != null) {

                System.out.println(
                        "Allowance: "
                                + current.getTotalAllowance()
                );

                System.out.println(
                        "Start: "
                                + current.getStartDate()
                );

                System.out.println(
                        "End: "
                                + current.getEndDate()
                );
            }


            // =========================
            // USER STORY 3
            // SAFE DAILY LIMIT
            // =========================

            System.out.println("\n===== USER STORY 3 =====");

            System.out.println(
                    "Remaining Balance = "
                            + db.getRemainingBalance()
            );

            System.out.println(
                    "Remaining Days = "
                            + db.getRemainingDays()
            );

            System.out.println(
                    "Safe Daily Limit = "
                            + db.getSafeDailyLimit()
            );


            // =========================
            // USER STORY 4
            // PIE CHART DATA
            // =========================

            System.out.println("\n===== USER STORY 4 =====");

            Map<category, Double> totals =
                    db.getTotalExpensesByCategory();

            if (totals.isEmpty()) {

                System.out.println(
                        "No data available. Log an expense to see your insights."
                );

            } else {

                for (Map.Entry<category, Double> entry : totals.entrySet()) {

                    System.out.println(
                            entry.getKey()
                                    + " -> "
                                    + entry.getValue()
                    );
                }
            }


            // =========================
            // LOCK USER
            // =========================

            System.out.println("\n===== LOCK USER =====");

            db.lockUser(1);

            System.out.println("User locked successfully");


            // =========================
            // GET USER
            // =========================

            System.out.println("\n===== GET USER =====");

            user fetched = db.getUserById(1);

            if (fetched != null) {

                System.out.println(
                        "User Name: "
                                + fetched.getName()
                );

                System.out.println(
                        "Locked: "
                                + fetched.locked()
                );
            }


            System.out.println(
                    "\n===== ALL TESTS COMPLETED SUCCESSFULLY ====="
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}