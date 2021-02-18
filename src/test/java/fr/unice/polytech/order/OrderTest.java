package fr.unice.polytech.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.customer.User;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.tools.Analytics;

public class OrderTest {
    Recipe recipe1;
    Recipe recipe2;
    Recipe recipe3;

    ArrayList<OrderItem> oi, oi2, oi3, oi4;
    Order order1, order2, order3, order4;
    Guest guest;
    User user1, user2;
    Shop shop;
    FactoryFacade factoryFacade;

    @BeforeEach
    public void setUp() {
        factoryFacade = new FactoryFacade();
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

        shop = new Shop(factoryFacade, 0.1);

        user1 = new User("", "");
        user1.subscribeToLoyaltyProgram();

        user2 = new User("", "");
        user2.subscribeToLoyaltyProgram();
        user2.setCookiePot(30);

        guest = new Guest("");

        oi = new ArrayList<>();
        oi.add(new OrderItem(recipe1, 2));
        order1 = new Order(guest, shop, oi);

        oi2 = new ArrayList<>();
        oi2.add(new OrderItem(recipe1, 2));
        oi2.add(new OrderItem(recipe2, 1));
        order2 = new Order(guest, shop, oi2);

        oi3 = new ArrayList<>();
        oi3.add(new OrderItem(recipe1, 2));
        oi3.add(new OrderItem(recipe2, 1));
        oi3.add(new OrderItem(recipe3, 3));
        order3 = new Order(user2, shop, oi3);

        oi4 = new ArrayList<>();
        Analytics analytics = new Analytics(RecipeBuilder.prepareCHOCOLALALA());
        Shop shop2 = new Shop(factoryFacade, 0.1);
        shop2.setAnalytics(analytics);
        factoryFacade.setAnalytics(analytics);
        oi4.add(new OrderItem(recipe1, 2));
        oi4.add(new OrderItem(recipe2, 1));
        oi4.add(new OrderItem(recipe3, 3));
        order4 = new Order(user2, shop2, oi4);
    }

    @Test
    public void calculatePrice() {
        assertEquals(3.74, order1.calculatePrice()); //(1.7*2)*1.1
        assertEquals(5.39, order2.calculatePrice()); //(1.7*2*1.1)+(1.2*1.1*1.25)
        assertEquals(9.68, order3.calculatePrice()); //((1.7*2*1.1)+(1.2*1.1*1.25)+(1.3*3*1.1*1.25))*0.9
        assertEquals(9.34, order4.calculatePrice()); //((1.7*2*1.1*0.9)+(1.2*1.1*1.25)+(1.3*3*1.1*1.25))*0.9
    }

    @Test
    public void isReadyTest(){
        assertFalse(order3.isReady());

    }
}
