package fr.unice.polytech.order.command;

import fr.unice.polytech.exception.CancelCommandException;
import fr.unice.polytech.exception.CustomerCommandException;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderStatus;

public class CommandPrepareOrder implements Command {
    private Order order;

    public CommandPrepareOrder(Order order) {
        this.order = order;
    }

    /**
     * If the order is ordered and confirmed, and that the shop is avalaible, then it's going to kitchen to be prepared
     */
    @Override
    public void execute() {
        if(order.getOrderStatus() == OrderStatus.VALIDATED && !order.getShop().hasTechnicalFailure()) {
            order.getCustomer().applyLoyaltyProgram(order.getNumberOfCookies());
            order.setOrderStatus(OrderStatus.PREPARED);

        }
    }

    /**
     * Cancel an order : remove it from shop 
     */
    @Override
    public void cancel() {
        if(order.getCustomer().getCommand()!=null) {
            if (order.getOrderStatus() == OrderStatus.VALIDATED) {
                order.getCustomer().setCommand(null);
                order.getShop().getShopValidateOrders().removeIf(o -> o.getOrder().getId() == order.getId());
            } else {
                throw new CancelCommandException("Annulation impossible, votre commande est déjà préparée");
            }
        }
        else{
            throw new CustomerCommandException("Aucune commande attribuée",order.getCustomer());
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
