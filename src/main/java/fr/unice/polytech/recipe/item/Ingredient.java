package fr.unice.polytech.recipe.item;

/**
 * interface for ingredients with price and count (cooking and mixtype doesn't have a count)
 */
public interface Ingredient {
    Object getType();
    String toString();
}
