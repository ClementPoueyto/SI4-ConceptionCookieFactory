package fr.unice.polytech.recipe;

import fr.unice.polytech.recipe.item.Ingredient;


/**
 * Class that associate an ingredient to a price
 */
public class RecipeIngredient {
    Ingredient ingredient;
    double price;

    RecipeIngredient(Ingredient ingredient, double price){
        this.ingredient = ingredient;
        this.price = price;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String toString(){
        return this.ingredient.toString();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * implematation of equals method to make possible comparison between recipes (usefull to avoid add twice a recipe to the base for example)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return ingredient.equals(that.ingredient);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
