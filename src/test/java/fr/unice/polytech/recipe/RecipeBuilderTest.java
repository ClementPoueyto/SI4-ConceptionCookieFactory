package fr.unice.polytech.recipe;

import fr.unice.polytech.exception.InvalidRecipeException;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeBuilderTest {

    private RecipeBuilder recipeBuilder;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void withToppingTest(){
        Recipe recipe=null;
        Topping.ToppingType topTest=Topping.ToppingType.DARK_CHOCOLATE;
        recipeBuilder = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CRUNCHY);
        try {
            recipe=recipeBuilder.withTopping(topTest).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert recipe != null;
        assertEquals(topTest, recipe.getToppings().get(0).getIngredient().getType());
    }

    @Test void withErrors(){
        Topping.ToppingType topTest=Topping.ToppingType.DARK_CHOCOLATE;

        recipeBuilder = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CHEWY)
                .withTopping(topTest).withTopping(topTest).withTopping(topTest);
        assertThrows(InvalidRecipeException.class,()->recipeBuilder.withTopping(topTest));
        assertThrows(InvalidRecipeException.class,()->recipeBuilder.withTopping(topTest,3));

        assertThrows(InvalidRecipeException.class,()->recipeBuilder.withFlavour(null));
        assertThrows(InvalidRecipeException.class,()->recipeBuilder.withMix(null));
        assertThrows(InvalidRecipeException.class,()->recipeBuilder.withName(null));
        assertThrows(InvalidRecipeException.class,()->recipeBuilder.withDose(4));



    }

    @Test
    public void withToppingTestLimit(){
        recipeBuilder = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CRUNCHY);

            recipeBuilder.withTopping(Topping.ToppingType.DARK_CHOCOLATE).withTopping(Topping.ToppingType.MILK_CHOCOLATE).
            withTopping(Topping.ToppingType.MNMS);

        assertThrows(InvalidRecipeException.class,()-> recipeBuilder.withTopping(Topping.ToppingType.WHITE_CHOCOLATE));
    }

    @Test
    public void withFlavourTest(){
        Recipe recipe=null;
        Flavour.FlavourType flavourType = Flavour.FlavourType.VANILLA;
        recipeBuilder = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CRUNCHY);
        try {
            recipe=recipeBuilder.withFlavour(flavourType).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert recipe != null;
        assertEquals(flavourType, recipe.getFlavour().getIngredient().getType());
    }

    @Test
    public void withMixTest(){
        Recipe recipe=null;
        MixType mixType = MixType.TOPPED;
        recipeBuilder = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CRUNCHY);
        try {
            recipe=recipeBuilder.withMix(mixType).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert recipe != null;
        assertEquals(mixType, recipe.getMix());
    }

    @Test
    public void withNameTest(){
        Recipe recipe=null;
        String name = "myRecipe";
        recipeBuilder = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CRUNCHY);
        try {
            recipe=recipeBuilder.withName(name).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert recipe != null;
        assertEquals(name, recipe.getName());
    }

    @Test
    public void buildTest(){
        Recipe recipe = RecipeBuilder.prepareCHOCOLALALA();
        assertEquals("Chocolalala", recipe.getName());
        assertEquals(Dough.DoughType.OATMEAL, recipe.getDough().getIngredient().getType());
        assertEquals(CookingType.CHEWY,recipe.getCooking());
        assertEquals(MixType.TOPPED, recipe.getMix());
        assertEquals(Flavour.FlavourType.CINNAMON, recipe.getFlavour().getIngredient().getType());
        assertEquals(2, recipe.getToppings().size());
        assertEquals(1, recipe.getDose());

    }

}