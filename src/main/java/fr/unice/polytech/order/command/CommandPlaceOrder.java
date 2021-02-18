package fr.unice.polytech.order.command;


import fr.unice.polytech.exception.CancelCommandException;
import fr.unice.polytech.exception.CustomerCommandException;
import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.shop.timesheet.DaySchedule;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

//Concrete Command

public class CommandPlaceOrder implements Command {

    private Order order;

    public CommandPlaceOrder(Order order) {
        this.order = order;
    }

    /**
     * if the order exists, date/jour is OK and there are enough stocks, then the order is placed/confirmed
     */
    @Override
    public void execute() throws UnavailableShopException {
        if(order.getCustomer().getCommand()==null) {
            if(checkPickUpDate(order.getPickupDate(),order.getShop())){ //on verifie les horaires du magasin
                try {
                    if (checkStock()) { //on verifie le stock
                        this.order.setOrderStatus(OrderStatus.PLACED);
                        this.order.getCustomer().setCommand(new CommandValidateOrder(order));
                    }
                } catch (Exception e) {
                    throw new UnavailableShopException("No stock", order.getShop());
                }
            }
            else {
                throw new UnavailableShopException("Invalid Date", order.getShop());
            }
        }
        else{
            throw new CustomerCommandException("You already have an order in preparation", order.getCustomer());
        }
    }

    /**
     * Cancel an order : remove it from the customer 
     */
    @Override
    public void cancel() {
        if(order.getCustomer().getCommand()!=null ){
            if(order.getOrderStatus()==OrderStatus.PLACED){
                order.getCustomer().setCommand(null);
            }
            else{
                throw new CancelCommandException("Annulation impossible, votre commande est déjà préparée");
            }
        }
        else{
            throw new CustomerCommandException("No command available", order.getCustomer());
        }
    }

    /**
     * 
     * @param pickupDate
     * @param shop
     * @return
     * check if the givne date and shop is possible with the given shop
     */
    public boolean checkPickUpDate(LocalDateTime pickupDate, Shop shop){
        if(pickupDate !=null && shop != null) {
            DayOfWeek day = pickupDate.getDayOfWeek();
            DaySchedule shopDaySchedule = shop.getTimesheet().getDaySchedule(day);

            if (shopDaySchedule.getFrom().isBefore(pickupDate.toLocalTime()) && shopDaySchedule.getTo().isAfter(pickupDate.toLocalTime())) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if there are enough stocks to make the order
     */
    private boolean checkStock() throws Exception {
        return this.order.getShop().enoughStock(order);
    }

    public Order getOrder() {
        return order;
    }
}