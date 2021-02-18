package fr.unice.polytech.factory;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.order.command.CommandPrepareOrder;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.shop.timesheet.Timesheet;

public class EmployeeTest {

    private Employee employee;
    private Shop shop;

    @BeforeEach
    public void setUp() {
        shop = new Shop(new FactoryFacade());
        employee = new Employee(shop, "fn", "ln", false);
    }

    @Test
    public void setShopTimeSheetTest(){
        Timesheet newTimeSheet = new Timesheet();
        assertThrows(RuntimeException.class, ()->employee.setShopTimeSheet(newTimeSheet));
        employee.setManager(true);
        assertDoesNotThrow(()->employee.setShopTimeSheet(newTimeSheet));

    }

    @Test
    public void setShopTechnicalStateTest(){
        assertThrows(RuntimeException.class, ()->employee.setShopTechnicalFailure(true));
        assertFalse(shop.hasTechnicalFailure());

        employee.setManager(true);
        assertDoesNotThrow(()->employee.setShopTechnicalFailure(true));
        assertTrue(shop.hasTechnicalFailure());

    }

    @Test
    public void scansTest(){
        ArrayList<OrderItem> items = new ArrayList<>();
        Guest guest = new Guest("test");

        Order order = new Order(guest, shop, items);


        order.setOrderStatus(OrderStatus.PREPARED);
        assertFalse(employee.scans(order.getId()));

        shop.addPrepareOrder(new CommandPrepareOrder(order));

        assertTrue(employee.scans(order.getId()));
        assertEquals(OrderStatus.SERVED, order.getOrderStatus());
        assertFalse(employee.scans(order.getId()));
    }

}
