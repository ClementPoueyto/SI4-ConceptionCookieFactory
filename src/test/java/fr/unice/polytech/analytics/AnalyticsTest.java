package fr.unice.polytech.analytics;

import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.factory.FactoryFacade;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.shop.Shop;

import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import fr.unice.polytech.tools.Analytics;
import fr.unice.polytech.tools.Analytic;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticsTest {
    FactoryFacade factory;
    Shop shop1;
    Shop shop2;
    Guest guest1;
    Guest guest2;


    @BeforeEach
    public void setUp(){
        factory = new FactoryFacade();
        shop1 = new Shop(factory);
        shop2 = new Shop(factory);
        guest1 = new Guest("gege@gmail.com");
        guest2 = new Guest("gege2@gmail.com");
    }

    @Test
    public void testAddOrderToAnalytics() throws Exception {
        // create Recipes
        Recipe recipe1 = new RecipeBuilder(Dough.DoughType.PLAIN, CookingType.CRUNCHY)
                            .withFlavour(Flavour.FlavourType.VANILLA)
                            .withMix(MixType.TOPPED)
                            .withTopping(Topping.ToppingType.WHITE_CHOCOLATE)
                            .withTopping(Topping.ToppingType.DARK_CHOCOLATE)
                            .build();
        Recipe recipe2 = RecipeBuilder.prepareCHOCOLALALA();
        Recipe recipe3 = RecipeBuilder.prepareDARKTEMPTATION();
        OrderItem orderItem1 = new OrderItem(recipe1, 2);
        OrderItem orderItem2 = new OrderItem(recipe2, 4);
        OrderItem orderItem3 = new OrderItem(recipe3, 1);
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        Order orderFirst = new Order(guest2, shop2, orderItems);
        orderFirst.setPickupDate(LocalDateTime.of(2020,5,26,10,0));


        orderItems.add(orderItem3);
        // add them to the shop as orders
        // it will automatically add them to the analytics of the shop and to the factory
        Order orderBis = new Order(guest1, shop1, orderItems);
        orderBis.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        factory.startCommand(orderFirst);
        factory.payCommand(orderFirst, orderFirst.calculatePrice());
        shop2.cook(shop2.getValidateCommandById(orderFirst.getId()));
        factory.startCommand(orderBis);
        factory.payCommand(orderBis, orderBis.calculatePrice());
        shop1.cook(shop1.getValidateCommandById(orderBis.getId()));
        assertEquals(recipe2, factory.getBestRecipe());
        assertEquals(recipe2, shop1.getBestRecipe());
        assertEquals(recipe2, shop2.getBestRecipe());
    }

    @Test
    public void testAddAndGet(){
        Recipe chocolalala = factory.getCookie("Chocolalala");
        Analytics analytics = new Analytics();
        Analytic analytic = new Analytic(chocolalala, 10);
        analytics.add(analytic);
        assertTrue(analytics.getAnalytics().contains(analytic));
        assertTrue(analytics.getOneAnalytic(chocolalala).equals(analytic));
        assertEquals(analytics.getSize(), 1);
        assertEquals(analytic.getCount(), 10);
        assertEquals(analytic.getRecipe(), chocolalala);
    }

    @Test
    public void testSort(){
        Recipe choco = factory.getCookie("Chocolalala");
        Recipe darkTempt = factory.getCookie("Dark Temptation");
        Recipe sooChoco = factory.getCookie("Soo Chocolate");
        Analytics analytics = new Analytics();
        Analytic chocolalala = new Analytic(choco, 23);
        Analytic darkT = new Analytic(darkTempt, 2);
        Analytic sooChocolate = new Analytic(sooChoco, 45);
        analytics.add(chocolalala);
        analytics.add(darkT);
        analytics.add(sooChocolate);
        assertTrue(analytics.getWorst().equals(darkTempt));
        assertTrue(analytics.getBest().equals(sooChoco));
        assertTrue(analytics.getRankedRecipe(1).equals(chocolalala));
        analytics.removeAnalytic(choco);
        assertFalse(analytics.getAnalytics().contains(chocolalala));
        assertFalse(analytics.getRecipes().contains(choco));
    }

    @Test
    public void testThrowErrors(){
        Recipe choco = factory.getCookie("Chocolalala");
        Recipe darkTempt = factory.getCookie("Dark Temptation");
        Recipe sooChoco = factory.getCookie("Soo Chocolate");
        Analytics analytics = new Analytics();
        Analytic chocolalala = new Analytic(choco, 23);
        Analytic darkT = new Analytic(darkTempt, 2);
        Analytic sooChocolate = new Analytic(sooChoco, 45);
        analytics.add(chocolalala);
        analytics.add(darkT);
        analytics.add(sooChocolate);
        assertThrows(Exception.class, ()->analytics.getRankedRecipe(5));
    }
}