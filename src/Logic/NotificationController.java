package Logic;

import models.BudgetCycle;

/**
 * Controller responsible for handling budget notifications
 * based on spending thresholds.
 */
public class NotificationController {

    /**
     * Checks the current budget usage percentage and triggers
     * notifications based on predefined thresholds.
     *
     * <ul>
     *   <li>80% - 99%: Warning notification</li>
     *   <li>100% or more: Budget exhausted notification</li>
     * </ul>
     *
     * @param percentage the percentage of budget used
     */
    public void checkThreshold(double percentage) {
        if (percentage >= 80 && percentage < 100) {
            showNotification("Warning: 80% used");
        } else if (percentage >= 100) {
            showNotification("Budget Exhausted");
        }
    }

    /**
     * Displays a notification message to the user.
     * Currently implemented as console output.
     *
     * @param message the notification message to display
     */
    public void showNotification(String message) {
        System.out.println("Notification: " + message);
    }
}