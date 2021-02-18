package fr.unice.polytech.recipe.item;

public class Dough implements Ingredient {
    
    public DoughType type;
    
    /**
     * Enumaration with methods to list all kind of dough and their price
     */
    public enum DoughType {
        PLAIN(0.2),
        CHOCOLATE(0.3),
        PEANUT_BUTTER(0.3),
        OATMEAL(0.5);
        double price;

        DoughType (double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    public DoughType getType(){
        return type;
    }

    public Dough(DoughType type) {
        this.type = type;
    }

    @Override
    public String toString(){
        String res;
        switch(this.type){
            case PLAIN:
                res = "Plain";
                break;
            case CHOCOLATE:
                res = "Chocolate";
                break;
            case PEANUT_BUTTER:
                res = "PeanutButter";
                break;
            case OATMEAL:
                res = "Oatmeal";
                break;
            default:
                res = "no dough";
        }
        return res;
    }
    
    /**
     * implematation of equals method to make possible comparison between recipes (usefull to avoid add twice a recipe to the base for example)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Dough)) return false;
        Dough topping = (Dough) obj;
        return this.type == topping.type;
    }


    @Override
    public int hashCode() {
        return 0;
    }

}