package fr.unice.polytech.recipe;

import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    Recipe recipe1;
    Recipe recipe2;
    Recipe recipe3;

    @BeforeEach
    public void setUp() {
        recipe1 = RecipeBuilder.prepareCHOCOLALALA();
        recipe2 = new RecipeBuilder(Dough.DoughType.OATMEAL, CookingType.CHEWY)
                .withMix(MixType.MIXED)
                .withFlavour(Flavour.FlavourType.CHILI)
                .withTopping(Topping.ToppingType.DARK_CHOCOLATE)
                .build();
        recipe3 = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CRUNCHY)
                .withMix(MixType.TOPPED)
                .withFlavour(Flavour.FlavourType.CINNAMON)
                .withTopping(Topping.ToppingType.REESES_BUTTERCUP)
                .build();
    }

    @Test
    public void getPriceTest(){
        assertEquals(1.7, recipe1.getPriceExclTaxes());
        assertEquals(1.2, recipe2.getPriceExclTaxes());
        assertEquals(1.3, recipe3.getPriceExclTaxes());
    }

}