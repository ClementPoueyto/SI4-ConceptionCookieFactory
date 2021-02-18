package fr.unice.polytech.recipe;

import java.util.ArrayList;

import fr.unice.polytech.exception.InvalidRecipeException;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;

/**
 * Class factory that builds Recipe Objects
 */

public class RecipeBuilder {

    private String name;
    private RecipeIngredient dough;
    private RecipeIngredient flavour;
    private ArrayList<RecipeIngredient> toppings = new ArrayList<>();
    private MixType mix;
    private int dose;
    private CookingType cooking;


    public RecipeBuilder(Dough.DoughType dough, CookingType cooking) {
        if (dough == null || cooking == null) {
            throw new IllegalArgumentException("parameters cannot be null");
        }
        this.dough = new RecipeIngredient(new Dough(dough), dough.getPrice());
        this.cooking = cooking;
        this.dose = 1;
    }

    /**
     * Add a topping to the recipe that is being created
     * @param top
     * @param index
     * @return
     */
    public RecipeBuilder withTopping(Topping.ToppingType top, int index) {
        if (index < 3 && index >= 0 && top != null) {
            this.toppings.set(index, new RecipeIngredient(new Topping(top), top.getPrice()));
        } else {
            throw new InvalidRecipeException("Invalid Topping index");
        }
        return this;
    }

    /**
     * Add a topping add an index to the recipe that is being created
     * @param top
     * @return the recipe that is being created
     */
    public RecipeBuilder withTopping(Topping.ToppingType top){
        if (this.toppings.size() < 3 && top != null) {
            this.toppings.add(new RecipeIngredient(new Topping(top), top.getPrice()));
        } else {
            throw new InvalidRecipeException("too much toppings");
        }
        return this;
    }

    /**
     * Add a flavour to the recipe that is being created
     * @param flavour
     * @return the recipe that is being created
     */
    public RecipeBuilder withFlavour(Flavour.FlavourType flv) {
        if (flv != null) {
            this.flavour = new RecipeIngredient(new Flavour(flv), flv.getPrice());
        } else {
            throw new InvalidRecipeException("no flavour");
        }
        return this;
    }

    /**
     * Set the choosen mix to the recipe that is being created
     * @param mix
     * @return
     */
    public RecipeBuilder withMix(MixType mix){
        if (mix != null) {
            this.mix = mix;
        } else {
            throw new InvalidRecipeException("no mix");
        }
        return this;
    }

    /**
     * Add a name to the recipe that is being created
     * @param name
     * @return the recipe that is being created
     */
    public RecipeBuilder withName(String name){
        if (name != null) {
            this.name = name;
        } else {
            throw new InvalidRecipeException("wrong name");
        }
        return this;
    }

    /**
     * Set the dose choosen for the recipe that is being created
     * @param dose
     * @return the recipe that is being created
     */
    public RecipeBuilder withDose(int dose){
        if(dose>0 && dose<4){
            this.dose = dose;
        } else {
            throw new InvalidRecipeException("wrong dose");
        }
        return this;
    }

    /**
     * Prepare a predifined recipe 
     * @return the chocolalala recipe
     */
    public static Recipe prepareCHOCOLALALA() {

        return new RecipeBuilder(Dough.DoughType.OATMEAL, CookingType.CHEWY)
                .withMix(MixType.TOPPED).withFlavour(Flavour.FlavourType.CINNAMON)
                .withName("Chocolalala").withTopping(Topping.ToppingType.MILK_CHOCOLATE)
                .withTopping(Topping.ToppingType.MNMS).build();
    }

    /**
     * Prepare a soochocolate recipe 
     * @return the chocolalala recipe
     */
    public static Recipe prepareSOOCHOCOLATE() {

        return new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CRUNCHY)
                .withMix(MixType.MIXED).withFlavour(Flavour.FlavourType.VANILLA)
                .withName("Soo Chocolate").withTopping(Topping.ToppingType.DARK_CHOCOLATE)
                .build();

    }

    /**
     * Prepare a predifined recipe 
     * @return the derktemptation recipe
     */
    public static Recipe prepareDARKTEMPTATION() {

        return new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CHEWY)
                .withMix(MixType.MIXED).withFlavour(Flavour.FlavourType.CHILI)
                .withName("Dark Temptation").withTopping(Topping.ToppingType.DARK_CHOCOLATE)
                .withTopping(Topping.ToppingType.WHITE_CHOCOLATE).withTopping(Topping.ToppingType.MILK_CHOCOLATE)
                .build();

    }

    /**
     * Prepare a new recipe from a recipe set in parameter
     * @param recipe
     * @return
     */
    public static RecipeBuilder load(Recipe recipe){
        RecipeBuilder builder = new RecipeBuilder((Dough.DoughType) recipe.getDough().getIngredient().getType(), recipe.getCooking());
        builder.name = recipe.getName();
        builder.withDose(recipe.getDose());
        builder.withFlavour((Flavour.FlavourType) recipe.getFlavour().getIngredient().getType());
        for(RecipeIngredient rI : recipe.getToppings()){
            builder.withTopping((Topping.ToppingType) rI.getIngredient().getType());
        }
        builder.mix = recipe.getMix();
        return builder;
    }

    /**
     * Build a recipe being created
     * @return the recipe
     */
    public Recipe build() {
        return new Recipe(this);
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

    public RecipeIngredient getFlavour() {
        return flavour;
    }

    public void setFlavour(RecipeIngredient flavour) {
        this.flavour = flavour;
    }

    public ArrayList<RecipeIngredient> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<RecipeIngredient> topping) {
        this.toppings = topping;
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

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }
}
