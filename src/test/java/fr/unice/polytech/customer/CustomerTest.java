package fr.unice.polytech.customer;

import fr.unice.polytech.factory.Employee;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.order.command.CommandValidateOrder;
import fr.unice.polytech.shop.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CustomerTest {

    Customer guest;
    FactoryFacade factory;

    @BeforeEach
    public void setUp() {
        guest = new Guest("guest@gmail.com");
        factory = new FactoryFacade();
    }


    @Test
    public void getOrderStatusTest() throws Exception {
        Shop shop = new Shop(factory);
        Order order = new Order(guest,shop,new ArrayList<OrderItem>(), LocalDateTime.of(2020,05,26, 12, 0, 0));
        factory.startCommand(order);

        assertEquals(OrderStatus.PLACED,guest.getStatusCommand());
        factory.payCommand(order, order.calculatePrice());
        assertEquals(OrderStatus.VALIDATED,guest.getStatusCommand());

        shop.cook((CommandValidateOrder) guest.getCommand());
        assertEquals(OrderStatus.PREPARED,guest.getStatusCommand());

        Employee e = new Employee(shop, "", "", false);
        e.scans(order.getId());
        assertNull(guest.getStatusCommand());

    }

}
