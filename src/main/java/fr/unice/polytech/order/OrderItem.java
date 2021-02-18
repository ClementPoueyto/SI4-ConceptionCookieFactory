package fr.unice.polytech.order;


import fr.unice.polytech.recipe.Recipe;

public class OrderItem {

    Recipe recipe;
    int count;

    public OrderItem(Recipe recipe, int count) {
        this.recipe = recipe;
        this.count = count;
    }

    public Recipe getCookie() {
        return recipe;
    }

    public void setCookie(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
