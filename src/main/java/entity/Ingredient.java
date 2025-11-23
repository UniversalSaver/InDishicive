package entity;

import java.util.Objects;

/**
 * A simple entity representing an ingredient.
 */
public class Ingredient {
    private final String name;
    private final String amount;

    /**
     * Creates a new ingredient given an ingredient name and its measurement
     *
     * The measurement is a String due to how vague measurements are allowed to be, e.g., a pinky's worth of flour,
     * a cup of ginger.
     *
     * @param name the name of the ingredient
     * @param amount the amount of the given ingredient
     */
    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public String getAmount(){
        return amount;
    }

    // This is to check lower/uppercases
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient i = (Ingredient) o;
        return Objects.equals(name.toLowerCase(), i.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
