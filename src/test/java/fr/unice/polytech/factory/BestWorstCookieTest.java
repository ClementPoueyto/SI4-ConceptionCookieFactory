package fr.unice.polytech.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BestWorstCookieTest {

    private FactoryFacade factory;
    private Shop shop1;
    private Shop shop2;
    private Guest guest1;
    private Guest guest2;
    private Guest guest3;
    private Guest guest4;
    private Guest guest5;

    @BeforeEach
    public void setUp() throws Exception {
        factory = new FactoryFacade();
        shop1 = new Shop(factory, 1.0);
        shop2 = new Shop(factory);
        guest1 = new Guest("gege@gmail.com");
        guest2 = new Guest("gege2@gmail.com");
        guest3 = new Guest("gege3@gmail.com");
        guest4 = new Guest("gege4@gmail.com");
        guest5 = new Guest("gege5@gmail.com");
        // create Recipes
        Recipe recipe1 = new RecipeBuilder(Dough.DoughType.PLAIN, CookingType.CRUNCHY)
                            .withFlavour(Flavour.FlavourType.VANILLA)
                            .withMix(MixType.TOPPED)
                            .withTopping(Topping.ToppingType.WHITE_CHOCOLATE)
                            .withTopping(Topping.ToppingType.DARK_CHOCOLATE)
                            .build();
        Recipe recipe2 = RecipeBuilder.prepareCHOCOLALALA();
        Recipe recipe3 = RecipeBuilder.prepareDARKTEMPTATION();
        OrderItem orderItem1 = new OrderItem(recipe1, 5);
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
        orderFirst.isReady();
        factory.startCommand(orderBis);
        factory.payCommand(orderBis, orderBis.calculatePrice());
        orderBis.isReady();
    }


    @Test
    public void refreshCookies() {
        Recipe worst = factory.getWorstRecipe();
        Recipe best = factory.getBestRecipe();
        factory.refresh();
        if(factory.getFamousCookies().contains(worst)){
            assertTrue(factory.getCookies().contains(worst));
        }
        else{
            assertFalse(factory.getCookies().contains(worst));          
        }
        assertTrue(factory.getCookies().contains(best));
    }

    @Test
    public void discoutOnBest() throws Exception {
        /*
            * The test is in a few steps:
            * 1. The best recipe isn't CHOCOLALALA, 
            *        so we'll simulate an order with just one of it to see the price
            * 2. We'll make CHOCOLALALA the best cookie by ordering 10
            * 3. We'll order a CHOCOLALALA only to see if the price is lower than before
        */

        // 1. get price of a command with only one chocolalala
        Recipe chocolalala = RecipeBuilder.prepareCHOCOLALALA();
        ArrayList<OrderItem> onlyChocolalala = new ArrayList<OrderItem>();
        onlyChocolalala.add(new OrderItem(chocolalala, 1));
        Order orderChocolalala = new Order(guest3, shop1, onlyChocolalala);
        orderChocolalala.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        double priceNotDiscouted = orderChocolalala.calculatePrice();
        factory.startCommand(orderChocolalala);
        factory.payCommand(orderChocolalala, orderChocolalala.calculatePrice());
        shop1.cook(shop1.getValidateCommandById(orderChocolalala.getId()));
        orderChocolalala.isReady();

        // 2. make chocolalala the best recipe
        ArrayList<OrderItem> manyChocolalala = new ArrayList<OrderItem>();
        manyChocolalala.add(new OrderItem(chocolalala, 10));
        Order orderManyChocolalala = new Order(guest4, shop1, manyChocolalala);
        orderManyChocolalala.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        factory.startCommand(orderManyChocolalala);
        factory.payCommand(orderManyChocolalala, orderManyChocolalala.calculatePrice());
        shop1.cook(shop1.getValidateCommandById(orderManyChocolalala.getId()));
        orderManyChocolalala.isReady();

        // 3. order a chocolalala discounted
        Order orderChocolalalaBis = new Order(guest5, shop1, onlyChocolalala);
        orderChocolalalaBis.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        double priceDiscounted = orderChocolalalaBis.calculatePrice();
        factory.startCommand(orderChocolalalaBis);
        factory.payCommand(orderChocolalalaBis, orderChocolalalaBis.calculatePrice());
        shop1.cook(shop1.getValidateCommandById(orderChocolalalaBis.getId()));
        orderChocolalalaBis.isReady();
        
        assertTrue(priceNotDiscouted > priceDiscounted);
    }


}
