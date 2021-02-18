package fr.unice.polytech.factory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.customer.Customer;
import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.marceleat.MarcelEat;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.tools.Position;

public class OrderTest {

    Recipe cookie;
    Customer customer;
    Shop shop;
    ArrayList<OrderItem> orderItems;
    FactoryFacade factory;

    @BeforeEach
    public void setUp() {
        cookie = RecipeBuilder.prepareCHOCOLALALA();
        factory = new FactoryFacade();

        customer = new Guest("mail");
        shop = new Shop(factory);
        orderItems = new ArrayList<>();

        factory.addShop(shop);
    }

    @Test
    public void shopUnavailable() {
        orderItems.add(new OrderItem(cookie, 3));
        Order order = new Order(customer, shop, orderItems);
        order.setPickupDate(LocalDateTime.of(2020, 5, 26, 10, 0));
        order.add(new OrderItem(cookie, 200)); // On commande 200 cookies pour mettre à mal les stocks de la franchise sélectionné
        UnavailableShopException ex = assertThrows(UnavailableShopException.class, () -> {
            factory.startCommand(order);
        });
        assertEquals(ex.getMessage(), "No stock");
    }

    @Test
    public void askForDliveryTest() throws Exception{
        MarcelEat marcelEat = new MarcelEat();
        customer = mock(Customer.class);
        Position pos = mock(Position.class);
        when(pos.distance(shop.getPosition())).thenReturn(1000.0);
        when(customer.getPosition()).thenReturn(pos);
        Order order = new Order(customer, shop, orderItems, LocalDateTime.now().plusHours(1));
        assertTrue(order.asksForDelivery(marcelEat));
        when(customer.getPosition().distance(shop.getPosition())).thenReturn(15000.0);
        assertFalse(order.asksForDelivery(marcelEat));
    }
}
