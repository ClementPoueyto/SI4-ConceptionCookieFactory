package fr.unice.polytech.tools;

import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.recipe.Recipe;

public class Analytic implements Comparable<Analytic> {
    Recipe recipe;
    int count;

    public Analytic(Recipe recipe){
        this.recipe = recipe;
        this.count = 0;
    }


    public Analytic(Recipe recipe, int count){
        this.recipe = recipe;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void update(OrderItem order) {
        if (order.getCookie().equals(this.recipe)) {  
            this.count += order.getCount();
        }
    }

    @Override
    public String toString(){
        String res;
        res = this.recipe.toString();
        res += "\n\t\t --------------------------";
        res += "\n\t\t Ordered " + this.count+ " time(s)";
        return res;
    }


    /**
     * to sort analytics for having best and worst recipes according to quantity ordered by customers.
     */
    @Override
    public int compareTo(Analytic compareAnalytic){
        int compareCount = (compareAnalytic).getCount();
        return compareCount-this.getCount();
    }




}
