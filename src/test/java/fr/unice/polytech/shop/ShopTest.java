package fr.unice.polytech.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.order.command.CommandPrepareOrder;
import fr.unice.polytech.order.command.CommandValidateOrder;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;

public class ShopTest {

    Shop shop;
    Order order;
    private FactoryFacade factory;
    private Shop shop1;
    private Shop shop2;
    private Guest guest3;
    private Guest guest2;
    private Guest guest4;
    private Guest guest5;

    @BeforeEach
    public void setUp() {
        shop = new Shop(new FactoryFacade());
        factory = new FactoryFacade();
        shop1 = new Shop(factory, 1.0);
        shop2 = new Shop(factory, 1.0);
        guest2 = new Guest("gege2@gmail.com");
        guest3 = new Guest("gege3@gmail.com");
        guest4 = new Guest("gege4@gmail.com");
        guest5 = new Guest("gege5@gmail.com");
        //order = new Order();
    }

    @Test
    /**
     * Test the method getValidateCommandById of the shop and implicitely the method getCommandById of ShopValidateOrders
     */
    public void getValidateCommandById() {
        OrderItem oI = new OrderItem(RecipeBuilder.prepareCHOCOLALALA(), 1);
        ArrayList<OrderItem> oIs = new ArrayList<>();
        oIs.add(oI);
        Order order1 = new Order(guest2, shop, oIs);
        Order order2 = new Order(guest2, shop, oIs);
        order1.setId(1);
        order2.setId(12);
        CommandValidateOrder cvo1 = new CommandValidateOrder(order1);
        CommandValidateOrder cvo2 = new CommandValidateOrder(order2);
        shop.addValidateOrder(cvo1);
        shop.addValidateOrder(cvo2);

        assertEquals(12, shop.getValidateCommandById(12).getOrder().getId());
        assertEquals(1, shop.getValidateCommandById(1).getOrder().getId());

    }

    @Test
    /**
     * Test the method getPrepareCommandById of the shop and implicitely the method getCommandById of ShopValidateOrders
     */
    public void getPrepareCommandById() {
        OrderItem oI = new OrderItem(RecipeBuilder.prepareCHOCOLALALA(), 1);
        ArrayList<OrderItem> oIs = new ArrayList<>();
        oIs.add(oI);
        Order order1 = new Order(guest2, shop, oIs);
        Order order2 = new Order(guest2, shop, oIs);
        order1.setId(1);
        order2.setId(12);

        CommandPrepareOrder cvo1 = new CommandPrepareOrder(order1);
        CommandPrepareOrder cvo2 = new CommandPrepareOrder(order2);
        shop.addPrepareOrder(cvo1);
        shop.addPrepareOrder(cvo2);

        assertEquals(12, shop.getPrepareCommandById(12).getOrder().getId());
        assertEquals(1, shop.getPrepareCommandById(1).getOrder().getId());

    }

    @Test
    /**
     * Test the method cook of Shop
     */
    public void cookTest(){
        OrderItem oI = new OrderItem(RecipeBuilder.prepareCHOCOLALALA(), 1);
        ArrayList<OrderItem> oIs = new ArrayList<>();
        oIs.add(oI);
        Order order = new Order(guest2, shop, oIs);
        order.setId(13);

        CommandValidateOrder command = new CommandValidateOrder(order);
        shop.addValidateOrder(command);
        assertNull(shop.getPrepareCommandById(13));
        assertEquals(13, shop.getValidateCommandById(13).getOrder().getId());

        shop.cook(command);
        assertNull(shop.getValidateCommandById(13));
        assertEquals(13, shop.getPrepareCommandById(13).getOrder().getId());

    }

    @Test
    public void discountBestRecipe() throws Exception {
        Recipe chocolalala = RecipeBuilder.prepareCHOCOLALALA();
        ArrayList<OrderItem> onlyChocolalala = new ArrayList<OrderItem>();
        onlyChocolalala.add(new OrderItem(chocolalala, 1));
        Order orderChocolalala = new Order(guest3, shop1, onlyChocolalala);
        orderChocolalala.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        double priceNotDiscouted = orderChocolalala.calculatePrice();
        factory.startCommand(orderChocolalala);
        factory.payCommand(orderChocolalala, orderChocolalala.calculatePrice());
        orderChocolalala.isReady();
        shop1.cook(shop1.getValidateCommandById(orderChocolalala.getId()));

        // make chocolalala the best recipe of the tested shop
        ArrayList<OrderItem> manyChocolalala = new ArrayList<OrderItem>();
        manyChocolalala.add(new OrderItem(chocolalala, 10));
        Order orderManyChocolalala = new Order(guest4, shop1, manyChocolalala);
        orderManyChocolalala.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        factory.startCommand(orderManyChocolalala);
        factory.payCommand(orderManyChocolalala, orderManyChocolalala.calculatePrice());
        orderManyChocolalala.isReady();
        shop1.cook(shop1.getValidateCommandById(orderManyChocolalala.getId()));

        // make darktemptation the best recipe of the factory
        Recipe dark = RecipeBuilder.prepareDARKTEMPTATION();
        ArrayList<OrderItem> manyDark = new ArrayList<OrderItem>();
        manyDark.add(new OrderItem(dark, 30));
        Order ordermanyDark = new Order(guest2, shop2, manyDark);
        ordermanyDark.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        factory.startCommand(ordermanyDark);
        factory.payCommand(ordermanyDark, ordermanyDark.calculatePrice());
        ordermanyDark.isReady();
        shop2.cook(shop2.getValidateCommandById(ordermanyDark.getId()));

        // order a chocolalala discounted
        Order orderChocolalalaBis = new Order(guest5, shop1, onlyChocolalala);
        orderChocolalalaBis.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        double priceDiscounted = orderChocolalalaBis.calculatePrice();
        factory.startCommand(orderChocolalalaBis);
        factory.payCommand(orderChocolalalaBis, orderChocolalalaBis.calculatePrice());
        orderChocolalalaBis.isReady();
        shop1.cook(shop1.getValidateCommandById(orderChocolalalaBis.getId()));
        assertTrue(factory.getBestRecipe().equals(dark));
        assertTrue(shop1.getBestRecipe().equals(chocolalala));
        assertTrue(priceNotDiscouted > priceDiscounted);
    }

    @Test
    /**
     * Test the method hasTechnicalFailure of the shop and if the exception thrown contains the shop which has a technical failure
     */
    public void hasTechnicalFailure(){
        Recipe recipe;
        recipe = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CHEWY)
                .withFlavour(Flavour.FlavourType.CINNAMON)
                .withMix(MixType.MIXED)
                .withTopping(Topping.ToppingType.DARK_CHOCOLATE)
                .withDose(3)
                .build();
        shop1.hasTechnicalFailure(true);
        OrderItem orderItem = new OrderItem(recipe, 1);
        List<OrderItem> orderItems  = new ArrayList<OrderItem>();
        orderItems.add(orderItem);
        Order order = new Order(new Guest("abc@gmail.com"), shop1, orderItems);
        UnavailableShopException ex = assertThrows(UnavailableShopException.class, () -> {
            factory.startCommand(order);
        });
        assertEquals("Woosh! This Shop has a burning furnace!", ex.getMessage());
        List<Shop> nearestShops = ex.getFallBackShops();
        assertFalse(nearestShops.contains(shop1));
        assertEquals(1, nearestShops.size());
        assertEquals(shop2, nearestShops.get(0));
    }
}
