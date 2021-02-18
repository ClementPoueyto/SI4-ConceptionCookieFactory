package fr.unice.polytech.command;

import fr.unice.polytech.customer.Customer;
import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.exception.CancelCommandException;
import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.order.command.CommandPlaceOrder;
import fr.unice.polytech.order.command.CommandPrepareOrder;
import fr.unice.polytech.order.command.CommandServeOrder;
import fr.unice.polytech.order.command.CommandValidateOrder;
import fr.unice.polytech.shop.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommandServedOrderTest {

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
        CommandValidateOrder cv = new CommandValidateOrder(o);
        cv.execute();
        CommandPrepareOrder cpo = new CommandPrepareOrder(o);
        cpo.execute();
    }


    @Test
    public void executeTest(){
        CommandServeOrder cs = new CommandServeOrder(o);

        cs.execute();
        assertEquals(OrderStatus.SERVED,o.getOrderStatus());

    }


    @Test
    public void cancelTest() {

        CommandServeOrder cs = new CommandServeOrder(o);
        assertThrows(CancelCommandException.class,cs::cancel );
        cs.execute();
        assertThrows(CancelCommandException.class,cs::cancel );

    }
}
