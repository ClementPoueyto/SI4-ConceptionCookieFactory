package fr.unice.polytech.order.command;

import fr.unice.polytech.exception.CancelCommandException;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderStatus;

public class CommandServeOrder implements Command{
    private Order order;

    public CommandServeOrder(Order order) {
        this.order = order;
    }

    /**
     * If the order is ordered, confirmed and ready then it can be served
     */
    @Override
    public void execute() {
        if(order.getOrderStatus() == OrderStatus.PREPARED) {
            order.setOrderStatus(OrderStatus.SERVED);
            order.getCustomer().setCommand(null);
        }
    }

    @Override
    public void cancel() {
        throw new CancelCommandException("Annulation impossible, votre commande est déjà préparée");
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
