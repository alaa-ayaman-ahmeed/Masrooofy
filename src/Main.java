import Logic.BudgetController;
import Logic.NotificationController;
import models.category;
import models.user;
import persistance.Datapersistance;
import persistance.DatabaseSetup;
import persistance.OneTimeSetup;

import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== MASROOFY FULL SYSTEM TEST =====");

        // 1. DATABASE SETUP TEST
        System.out.println("\n--- DATABASE SETUP ---");
        DatabaseSetup.createTables();
        OneTimeSetup.main(null);

        // 2. INITIALIZE CONTROLLERS
        BudgetController bc = new BudgetController();
        Datapersistance db = new Datapersistance();
        NotificationController nc = new NotificationController();

        Scanner input = new Scanner(System.in);

        // 3. CREATE USER TEST
        System.out.println("\n--- USER CREATION ---");
        user u = new user(0, "TestUser", false);
        db.createUser(u);

        System.out.println("Enter user ID to fetch:");
        int uid = input.nextInt();

        user fetched = db.getUserById(uid);
        if (fetched != null) {
            System.out.println("User Found: " + fetched.getName());
        } else {
            System.out.println("User not found.");
        }

        // 4. BUDGET CREATION TEST
        System.out.println("\n--- BUDGET CYCLE CREATION ---");

        System.out.println("Enter allowance:");
        double allowance = input.nextDouble();

        Date start = new Date();
        Date end = new Date(start.getTime() + (7L * 24 * 60 * 60 * 1000));

        boolean created = bc.createBudgetCycle(allowance, start, end);

        if (!created) {
            System.out.println("Budget creation failed. STOP TEST.");
            return;
        }

        // 5. ADD EXPENSE TEST
        System.out.println("\n--- ADD EXPENSES ---");

        System.out.println("Enter expense amount:");
        double amount = input.nextDouble();

        System.out.println("Choose category (1-8):");
        int choice = input.nextInt();

        category cat = category.fromId(choice);

        bc.addExpense(amount, cat);

        // 6. MULTIPLE EXPENSES TEST
        bc.addExpense(150, category.FOOD);
        bc.addExpense(100, category.TRANSPORT);
        bc.addExpense(250, category.SHOPPING);

        // 7. REPORTING TESTS
        System.out.println("\n--- REPORTING ---");

        System.out.println("Total Expenses: " + db.getTotalExpenses());
        System.out.println("Remaining Balance: " + db.getRemainingBalance());
        System.out.println("Remaining Days: " + db.getRemainingDays());
        System.out.println("Safe Daily Limit: " + db.getSafeDailyLimit());

        // 8. CATEGORY BREAKDOWN TEST
        System.out.println("\n--- CATEGORY BREAKDOWN ---");

        db.getTotalExpensesByCategory().forEach((c, val) -> {
            System.out.println(c + " -> " + val);
        });

        // 9. UPDATE EXPENSE TEST
        System.out.println("\n--- UPDATE EXPENSE ---");
        db.updateExpense(1, 999);

        // 10. DELETE EXPENSE TEST
        System.out.println("\n--- DELETE EXPENSE ---");
        db.deleteExpense(2);

        // 11. UPDATE BUDGET TEST
        System.out.println("\n--- UPDATE BUDGET ---");
        db.updateCycle(5000);

        System.out.println("Updated Cycle Active Data");

        // 12. NOTIFICATION TEST
        System.out.println("\n--- NOTIFICATION SYSTEM ---");

        double usedPercent = (db.getTotalExpenses() / allowance) * 100;

        System.out.println("Usage % = " + usedPercent);

        nc.checkThreshold(usedPercent);

        // 13. LOCK USER TEST
        System.out.println("\n--- USER LOCK TEST ---");
        db.lockUser(uid);

        System.out.println("User locked successfully");

        // 14. FINAL STATE TEST
        System.out.println("\n--- FINAL SYSTEM STATE ---");

        System.out.println("Remaining Balance: " + db.getRemainingBalance());
        System.out.println("Safe Daily Limit: " + db.getSafeDailyLimit());
        System.out.println("Total Expenses: " + db.getTotalExpenses());

        System.out.println("\n===== TEST COMPLETED =====");

    }
}