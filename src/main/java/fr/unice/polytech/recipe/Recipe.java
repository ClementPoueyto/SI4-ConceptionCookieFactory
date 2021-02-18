package fr.unice.polytech.recipe;

import java.util.ArrayList;

public class Recipe {
    String name;
    RecipeIngredient dough;
    RecipeIngredient flavour;
    ArrayList<RecipeIngredient> toppings;
    MixType mix;
    CookingType cooking;
    int dose;

    public Recipe(RecipeBuilder recipeBuilder) {
        this.name = recipeBuilder.getName() == null ? "unnamed" : recipeBuilder.getName();
        this.dough = recipeBuilder.getDough();
        this.flavour = recipeBuilder.getFlavour();
        this.toppings = recipeBuilder.getToppings();
        this.mix = recipeBuilder.getMix();
        this.cooking = recipeBuilder.getCooking();
        this.dose = recipeBuilder.getDose();
    }

    /**
     * 
     * @return price for the recipe without taxes
     */
    public double getPriceExclTaxes() {
        double price = 0;
        price += dough.getPrice();
        price += flavour.getPrice();
        for(RecipeIngredient ri : toppings) {
            price += ri.getPrice();
        }
        price *= dose;
        price = price * 100;
        price = Math.round(price);
        return  price/100;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeIngredient getDough() {
        return dough;
    }

    public void setDough(RecipeIngredient dough) {
        this.dough = dough;
    }

    public RecipeIngredient getFlavour() {
        return flavour;
    }

    public void setFlavour(RecipeIngredient flavour) {
        this.flavour = flavour;
    }

    public ArrayList<RecipeIngredient> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<RecipeIngredient> toppings) {
        this.toppings = toppings;
    }

    public MixType getMix() {
        return mix;
    }

    public void setMix(MixType mixType) {
        this.mix = mixType;
    }

    public CookingType getCooking() {
        return cooking;
    }

    public void setCooking(CookingType cookingType) {
        this.cooking = cookingType;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        String res = "";
        res = "\t - Name : " + this.getName();
        res += "\n\t\t *Dough: " + this.getDough().toString();
        res += "\n\t\t *Flavour: " + this.getFlavour().toString();
        res += "\n\t\t *Toppings: " + this.getToppings().toString();
        res += "\n\t\t *Mix: " + this.getMix().toString();
        res += "\n\t\t *Cooking: " + this.getCooking().toString() ;
        res += "\n\t\t *Dose: " + this.getDose();
        return res;        
    }

    /**
     * implematation of equals method to make possible comparison between recipes (usefull to avoid add twice a recipe to the base for example)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Recipe)) return false;
        Recipe recipe = (Recipe) obj;
        return this.cooking.equals(recipe.cooking) && this.flavour.equals(recipe.flavour)
                && this.mix.equals(recipe.mix)
                && this.toppings.equals(recipe.toppings)
                && this.dough.equals(recipe.dough);
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
