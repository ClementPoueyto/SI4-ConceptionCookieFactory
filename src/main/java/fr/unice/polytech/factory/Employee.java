package fr.unice.polytech.factory;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.exception.RoleEmployeeException;
import fr.unice.polytech.order.Invoker;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.order.command.CommandPrepareOrder;
import fr.unice.polytech.order.command.CommandServeOrder;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.shop.timesheet.Timesheet;

public class Employee extends Invoker {
    int id;
    Shop shop;
    String firstName;
    String lastName;
    boolean isManager;
    List<CommandServeOrder> servedOrders = new ArrayList<>();

    public Employee(Shop shop, String firstName, String lastName, boolean isManager) {
        this.shop = shop;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isManager = isManager;
        this.shop.addEmployee(this);
    }


    /**
     * 
     * @param orderId
     * @return  - false if there is a problem wihth command (null, bad status(already served for example))
     *         - true if all goes well
     * Simulates the scanning of an order bar code (associated to an orderId)
     * and the delivery of the order requested by the user
     */
    public boolean scans(long orderId) {
        CommandPrepareOrder order = shop.getPrepareCommandById(orderId);
        if (order != null && order.getOrder().getOrderStatus() == OrderStatus.PREPARED) {
            return deliverOrder(order);
        } else {
            return false;
        }
    }
    /**
     * 
     * @param timeSheet
     * set shop's time sheet if the employee is manager, if not throws an RoleEmployeeException
     */
    public void setShopTimeSheet(Timesheet timeSheet) {
        if (this.isManager) {
            shop.setTimesheet(timeSheet);
        } else {
            throw new RoleEmployeeException("Can't change timesheet as an employee");
        }

    }

    /**
     * 
     * @param bool
     * if the employee is manager, change shop's status (with or without technical failure)
     */
    public void setShopTechnicalFailure(Boolean bool) {
        if (this.isManager) {
            shop.setHasTechnicalFailure(bool);
        } else {
            throw new RoleEmployeeException("Can't change technical state as an employee");
        }

    }

    /**
     * 
     * @param order
     * @return true if all goes well, return false if there is a problem with order status
     * - execute the command : change its status from PREPARED to SERVED and clean customer's order
     *  (i.e: the customer hasn't any pending order)
     * - if status is good (SERVED), then it adds this order to the history of shop
     */
    private boolean deliverOrder(CommandPrepareOrder order) {
        CommandServeOrder cso = new CommandServeOrder(order.getOrder());
        cso.execute();
        if (order.getOrder().getOrderStatus() == OrderStatus.SERVED) {
            servedOrders.add(cso);
            return true;
        }
        return false;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public List<CommandServeOrder> getServedOrders() {
        return servedOrders;
    }

    public void setServedOrders(List<CommandServeOrder> servedOrders) {
        this.servedOrders = servedOrders;
    }
}
