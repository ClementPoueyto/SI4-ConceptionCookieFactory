package fr.unice.polytech.command;

import fr.unice.polytech.customer.Customer;
import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.exception.CustomerCommandException;
import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.order.command.CommandPlaceOrder;
import fr.unice.polytech.order.command.CommandValidateOrder;
import fr.unice.polytech.shop.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommandValidateOrderTest {

    Customer client;
    Shop s;
    Order o;
    @BeforeEach
    public void setUp() throws UnavailableShopException {
         client = new Guest("test");
        s = new Shop(new FactoryFacade());
         o = new Order(client, s, new ArrayList<>(), LocalDateTime.of(2020,1,3,15,1));
        CommandPlaceOrder cp = new CommandPlaceOrder(o);
        cp.execute();
    }


    @Test
    public void executeTest(){
        CommandValidateOrder cv = new CommandValidateOrder(o);

        cv.execute();
        assertEquals(o,s.getValidateCommandById(o.getId()).getOrder());
        assertEquals(OrderStatus.VALIDATED,o.getOrderStatus());



    }

    @Test
    public void cancelTest() {

        CommandValidateOrder cv = new CommandValidateOrder(o);
        cv.cancel();
        assertNull(o.getCustomer().getCommand());
        assertThrows(CustomerCommandException.class, cv::cancel);
    }

    @Test
    public void cancelTest2() {

        CommandValidateOrder cv = new CommandValidateOrder(o);
        cv.execute();
        assertEquals(o,s.getValidateCommandById(o.getId()).getOrder());

        cv.cancel();
        assertNull(o.getCustomer().getCommand());
        assertNull(s.getValidateCommandById(o.getId()));
    }

}
