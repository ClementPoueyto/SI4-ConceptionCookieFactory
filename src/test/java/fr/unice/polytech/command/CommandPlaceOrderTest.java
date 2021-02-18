package fr.unice.polytech.command;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import fr.unice.polytech.exception.UnavailableShopException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.customer.Customer;
import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.command.CommandPlaceOrder;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.shop.timesheet.Timesheet;

public class CommandPlaceOrderTest {


    CommandPlaceOrder cpo;
    Order order = mock(Order.class);

    @BeforeEach
    public void setUp() {
        cpo = new CommandPlaceOrder(order);
    }

    @Test
    public void checkPickUpDateTest(){
        Timesheet timesheet = new Timesheet();
        timesheet.setDaySchedule(DayOfWeek.TUESDAY, LocalTime.of(9,0), LocalTime.of(18,0));

        Shop shop = new Shop(new FactoryFacade());
        shop.setTimesheet(timesheet);
        when(order.getShop()).thenReturn(shop);

        when(order.getPickupDate()).thenReturn(LocalDateTime.of(2020, 5,26, 9,1));
        assertTrue(cpo.checkPickUpDate(order.getPickupDate(), order.getShop()));

        when(order.getPickupDate()).thenReturn(LocalDateTime.of(2020, 5,26, 10,0));
        assertTrue(cpo.checkPickUpDate(order.getPickupDate(), order.getShop()));

        when(order.getPickupDate()).thenReturn(LocalDateTime.of(2020, 5,26, 18,0));
        assertFalse(cpo.checkPickUpDate(order.getPickupDate(), order.getShop()));

        when(order.getPickupDate()).thenReturn(LocalDateTime.of(2020, 5,26, 17,59));
        assertTrue(cpo.checkPickUpDate(order.getPickupDate(), order.getShop()));
    }

    @Test
    public void executeTest(){
        Customer client = new Guest("test");
        Timesheet timesheet = new Timesheet();
        timesheet.setDaySchedule(DayOfWeek.TUESDAY, LocalTime.of(9,0), LocalTime.of(18,0));

        Shop shop = mock(Shop.class);
        when(shop.getTimesheet()).thenReturn(timesheet);
        when(shop.enoughStock(order)).thenReturn(true);
        when(order.getShop()).thenReturn(shop);
        when(order.getCustomer()).thenReturn(client);
        when(order.getPickupDate()).thenReturn(LocalDateTime.of(2020, 5,26, 9,1));

        assertDoesNotThrow(()->{cpo.execute();});
        assertNotNull(client.getCommand());

        when(order.getPickupDate()).thenReturn(LocalDateTime.of(2020, 5,26, 9,0));
        assertThrows(Exception.class,()->{cpo.execute();});

    }

    @Test
    public void cancelTest() throws UnavailableShopException {
        Customer client = new Guest("test");
        Shop s = new Shop(new FactoryFacade());
        Order o = new Order(client, s, new ArrayList<>(), LocalDateTime.of(2020,1,3,15,1));
        CommandPlaceOrder cp = new CommandPlaceOrder(o);
        cp.execute();
        assertNotNull(client.getCommand());
        cp.cancel();
        assertNull(client.getCommand());

    }



}
