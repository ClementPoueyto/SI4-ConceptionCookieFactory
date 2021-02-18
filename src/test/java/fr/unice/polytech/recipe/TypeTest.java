package fr.unice.polytech.recipe;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeTest {



    @BeforeEach
    public void setUp() {

    }

    @Test
    public void toStringTest(){
        assertEquals("DarkChocolate", new Topping(Topping.ToppingType.DARK_CHOCOLATE).toString());
        assertEquals("WhiteChocolate", new Topping(Topping.ToppingType.WHITE_CHOCOLATE).toString());
        assertEquals("MnMs", new Topping(Topping.ToppingType.MNMS).toString());
        assertEquals("ReesesButtercup", new Topping(Topping.ToppingType.REESES_BUTTERCUP).toString());

        assertEquals("Chili", new Flavour(Flavour.FlavourType.CHILI).toString());
        assertEquals("Cinnamon",new Flavour( Flavour.FlavourType.CINNAMON).toString());
        assertEquals("Vanilla", new Flavour(Flavour.FlavourType.VANILLA).toString());

        assertEquals("Chewy", CookingType.CHEWY.toString());
        assertEquals("Crunchy", CookingType.CRUNCHY.toString());

        assertEquals("Mixed", MixType.MIXED.toString());
        assertEquals("Topped", MixType.TOPPED.toString());

        assertEquals("Chocolate",new Dough(Dough.DoughType.CHOCOLATE).toString());
        assertEquals("Oatmeal",new Dough( Dough.DoughType.OATMEAL).toString());
        assertEquals("PeanutButter",new Dough( Dough.DoughType.PEANUT_BUTTER).toString());

    }

}
