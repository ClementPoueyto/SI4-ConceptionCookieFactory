package fr.unice.polytech.shop;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.order.command.CommandValidateOrder;

public class ShopValidateOrders extends ArrayList<CommandValidateOrder> {
    

    public ShopValidateOrders(List<CommandValidateOrder> orders) {
        super(orders);
    }

    public ShopValidateOrders(){
        super();
    }

    /**
     * Get a validate command with its id
     * @param id
     * @return validate command with id
     */
    public CommandValidateOrder getCommandById(long id){
        for(CommandValidateOrder order : this){
            if(id == order.getOrder().getId()){ return order;}
        }
        return null;
    }
}
