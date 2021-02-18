package fr.unice.polytech.recipe.item;

public class Topping implements Ingredient {

    public ToppingType type;

    /**
     * Enumaration with methods to list all kind of toppings and their price
    */
    public enum ToppingType {
        WHITE_CHOCOLATE(0.1),
        DARK_CHOCOLATE(0.2),
        MILK_CHOCOLATE(0.2),
        MNMS(0.6),
        REESES_BUTTERCUP(0.6);
        double price;
        ToppingType (double price) {
            this.price = price;
        }
        public double getPrice() {
            return price;
        }
    }

    public Topping(ToppingType type) {
        this.type = type;
    }

    @Override
    public String toString(){
        String res;
        switch(this.type){
            case WHITE_CHOCOLATE:
                res = "WhiteChocolate";
                break;
            case DARK_CHOCOLATE:
                res = "DarkChocolate";
                break;
            case MILK_CHOCOLATE:
                res = "MilkChocolate";
                break;
            case MNMS:
                res = "MnMs";
                break;
            case REESES_BUTTERCUP:
                res = "ReesesButtercup";
                break;
            default:
                res = "";
        }
        return res;
    }
    
    public ToppingType getType(){
        return type;
    }

    /**
     * implematation of equals method to make possible comparison between recipes (usefull to avoid add twice a recipe to the base for example)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Topping)) return false;
        Topping topping = (Topping) obj;
        return this.type == topping.type;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
