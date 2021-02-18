package fr.unice.polytech.recipe.item;

public class Flavour implements Ingredient {

    public FlavourType type;

    /**
     * Enumaration with methods to list all kind of flavour and their price
     */
    public enum FlavourType {
        VANILLA(0.2),
        CINNAMON(0.4),
        CHILI(0.5);
        double price;
        FlavourType (double price) {
            this.price = price;
        }
        public double getPrice() {
            return price;
        }
    }
    
    public Flavour(FlavourType type) {
        this.type = type;
    }
    
    @Override
    public String toString(){
        String res;
        switch(this.type){
            case VANILLA:
            res = "Vanilla";
            break;
            case CINNAMON:
            res = "Cinnamon";
            break;
            case CHILI:
            res = "Chili";
            break;
            default:
            res = "no flavour";
        }
        return res;
    }
    
    public FlavourType getType(){
        return type;
    }
    
    /**
     * implematation of equals method to make possible comparison between recipes (usefull to avoid add twice a recipe to the base for example)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flavour)) return false;
        Flavour flavour = (Flavour) obj;
        return this.type == flavour.type;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
}
