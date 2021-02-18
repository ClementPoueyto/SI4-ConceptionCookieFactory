package fr.unice.polytech.shop.stocks;

import fr.unice.polytech.exception.StockException;
import fr.unice.polytech.recipe.item.Ingredient;

public class StockIngredient {

    private Ingredient ingredient;
    private int quantity;

    public StockIngredient(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "StockIngredient{" +
                "quantity=" + quantity +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }

    /**
     * Retire quantity to the stock if there is enough stock
     * @param quantity
     */
    public void update(Integer quantity) { 
		if(enoughQuantity(quantity)>0){
            this.quantity -= enoughQuantity(quantity);
        }
    }
    
    /**
     * @param quantity
     * @return true if param quantity <= current quantity, else throw an exception 
     */
    public int enoughQuantity(Integer quantity){
        if(quantity > this.quantity) {
            throw new StockException("Quantity needed (" + quantity + ") of "+ this.ingredient +" for an order is higher than the available stock (" + this.quantity + ")");
        }
        return quantity;
    }
}
