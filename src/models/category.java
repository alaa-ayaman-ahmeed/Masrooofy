package models;

/**
 * Represents the available expense categories in the system.
 * Each category has an id and a display name.
 */
public enum category {
    FOOD(1, "Food"),
    TRANSPORT(2, "Transportation"),
    SHOPPING(3, "Shopping"),
    ENTERTAINMENT(4, "Entertainment"),
    BILLS(5, "Utilities & Bills"),
    HEALTH(6, "Healthcare"),
    EDUCATION(7, "Education"),
    OTHER(8, "Other");

    private int id;
    private String name;

    /**
     * Creates a category constant.
     *
     * @param id unique category identifier
     * @param displayName readable category name
     */
    category(int id, String displayName) {
        this.id = id;
        this.name = displayName;
    }

    /**
     * Returns the category id.
     *
     * @return category id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the display name of the category.
     *
     * @return category display name
     */
    public String getDisplayName() {
        return name;
    }

    /**
     * Returns a category matching the given id.
     *
     * @param id category identifier
     * @return matching category, or OTHER if not found
     */
    public static category fromId(int id) {
        for (category cat : values()) {
            if (cat.id == id) {
                return cat;
            }
        }
        return OTHER;
    }

    /**
     * Returns a category matching the given name.
     *
     * @param name category name
     * @return matching category, or OTHER if not found
     */
    public static category fromName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }

    /**
     * Returns the display name of the category.
     *
     * @return category display name
     */
    public String toString() {
        return name;
    }
}