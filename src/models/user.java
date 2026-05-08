package models;

/**
 * Represents a user of the budgeting system.
 * Stores the user's identifier, name, and lock status.
 */
public class user {
    private int userID;
    private String name;
    private boolean isLocked;

    /**
     * Creates a new user object.
     *
     * @param id unique user identifier
     * @param name user name
     * @param locked true if the user is locked, false otherwise
     */
    public user(int id, String name, boolean locked)
    {
        this.userID=id;
        this.name=name;
        this.isLocked=locked;
    }

    /**
     * Returns the user identifier.
     *
     * @return user id
     */
    public int getUserID()
    {
        return userID;
    }

    /**
     * Returns the user name.
     *
     * @return user name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns whether the user is locked.
     *
     * @return true if locked, false otherwise
     */
    public boolean locked()
    {
        return isLocked;
    }

    /**
     * Updates the user name.
     *
     * @param n new user name
     */
    public void setName(String n)
    {
        this.name=n;
    }

    /**
     * Updates the lock status of the user.
     *
     * @param status new lock status
     */
    public void setStatus(boolean status){
        this.isLocked=status;
    }
}