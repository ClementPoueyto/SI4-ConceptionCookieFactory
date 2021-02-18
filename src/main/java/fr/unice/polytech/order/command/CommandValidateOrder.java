package fr.unice.polytech.order.command;

import fr.unice.polytech.exception.CancelCommandException;
import fr.unice.polytech.exception.CustomerCommandException;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderStatus;

public class CommandValidateOrder implements Command {
    private Order order;

    public CommandValidateOrder(Order order) {
        this.order = order;
    }

    /**
     * Once the customer paid, we add the order for being cooked
     */
    @Override
    public void execute() { 
        if(order.getOrderStatus() == OrderStatus.PLACED) {
            order.setOrderStatus(OrderStatus.VALIDATED);

            order.getShop().addValidateOrder(this);
        }
    }

    /**
     * Cancel an order : remove it from shop 
     */
    @Override
    public void cancel() {
        if(order.getCustomer().getCommand()!=null ){
            if(order.getOrderStatus()==OrderStatus.VALIDATED || order.getOrderStatus()==OrderStatus.PLACED){
                order.getCustomer().setCommand(null);
                order.getShop().getShopValidateOrders().removeIf(o-> o.getOrder().getId()==order.getId());

            }
            else{
                throw new CancelCommandException("Annulation impossible, votre commande est déjà préparée");
            }
        }
        else{
            throw new CustomerCommandException("No command available", order.getCustomer());
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

