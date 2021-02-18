package fr.unice.polytech.shop;

import fr.unice.polytech.order.Order;

public interface ShopOrdersObserver {

    /**
     * Update the current object in terms of order
     * @param order
     */
    void update(Order order);
    
}
