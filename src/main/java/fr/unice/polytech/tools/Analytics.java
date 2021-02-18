package fr.unice.polytech.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.shop.ShopOrdersObserver;

public class Analytics implements ShopOrdersObserver {
    ArrayList<Analytic> analytics;

    public Analytics(Recipe recipe) {
        this.analytics = new ArrayList<>();
        this.analytics.add(new Analytic(recipe));
    }

    public Analytics(){
        this.analytics = new ArrayList<>();
    }

    public void add(Analytic analytic){
        boolean alreadyRegistered = false;
        for(Analytic analyticItem : this.analytics){
            if(analyticItem.getRecipe().equals(analytic.getRecipe())){
                analyticItem.count += analytic.getCount();
                alreadyRegistered = true;
            }
        }
        if(!alreadyRegistered){
            this.analytics.add(analytic);
        }
    }

    @Override
    public void update(Order order){
        order.forEach(orderItem -> {
            // if the recipe we want to update is not yet registered in analytics, then we
            // add it before updating its count
            if (analytics.stream().noneMatch(analytic -> analytic.getRecipe().equals(orderItem.getCookie()))) {
                analytics.add(new Analytic(orderItem.getCookie()));
            }
            analytics.forEach(analytic -> analytic.update(orderItem));
        });
    }


    public List<Recipe> getRecipes() {
        ArrayList<Recipe> res = new ArrayList<Recipe>();
        for (Analytic analytic : analytics) {
            res.add(analytic.getRecipe());
        }
        return res;
    }

    public List<Analytic> getAnalytics() {
        return this.analytics;
    }

    /**
     * 
     * @param recipe
     * @return Analytic
     * return the object [Analytic] matching with the recipe given in parameters
     */
    public Analytic getOneAnalytic(Recipe recipe) {
    for(Analytic analytic : analytics){
        if(analytic.getRecipe().equals(recipe)){
            return analytic;
        }
    }
    return null;
    }

    public String toString(){
        sortAnalytics();
        String res = "";
        for(Analytic analytic: this.analytics){
            res += "\n\tRecipe: \n";
            res += analytic.toString();
            res += "\n\n";
        }
        return res;
    }
    

    public int getSize(){
        return this.analytics.size();
    }

    /**
     * Sort analytics by decreasing quantity ordered (make possible thanks to compareto method in Analytic)
     */
    private void sortAnalytics(){
        Collections.sort(this.analytics);
    }

    public Recipe getBest(){
        sortAnalytics();
        return getRankedRecipe(0).getRecipe();
    }

    public Recipe getWorst(){
        sortAnalytics();
        int length = this.analytics.size();
        return getRankedRecipe(length-1).getRecipe();
    }

    /**
     * 
     * @param rank
     * @return
     * get the analytic at the given rank : rank 0 = bestRecipe.
     */
    public Analytic getRankedRecipe(int rank){
        Analytic res;
        sortAnalytics();
        try {
            res = this.analytics.get(rank);
            return res;
        } catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("Error: there isn't this much recipes in the analytics.");
        }
    }

    /**
     * 
     * @param recipeToRemove
     * remove the recipe to analytics
     */
    public void removeAnalytic(Recipe recipeToRemove){
        this.analytics.removeIf(analytic -> analytic.getRecipe().equals(recipeToRemove));
    }
}
