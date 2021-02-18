package fr.unice.polytech.shop;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.order.command.CommandPrepareOrder;

public class ShopPrepareOrders extends ArrayList<CommandPrepareOrder> {



    List<ShopOrdersObserver> observers;
    
    public ShopPrepareOrders(List<CommandPrepareOrder> orders){
        super(orders);
        this.observers = new ArrayList<>();
    }

    public ShopPrepareOrders(){
        super();
        this.observers = new ArrayList<>();
    }

    /**
     * Add observer obs to observers
     * @param obs
     * @return this
     */
    public ShopPrepareOrders addObserver(ShopOrdersObserver obs){
        this.observers.add(obs);
        return this;
    }

    /**
     * Add multiple observers to observers
     * @param observers
     * @return this
     */
    public ShopPrepareOrders addObservers(List<ShopOrdersObserver> observers){
        observers.forEach(obs -> this.addObserver(obs));
        return this;
    }

    /**
     * notify all observers (Analytics & StocksIngredient) when a new Prepare order is added
     * @param newOrder order added
     */
    private void notifyObservers(CommandPrepareOrder newOrder){
        observers.forEach(obs -> obs.update(newOrder.getOrder()));
    }

    @Override
    public boolean add(CommandPrepareOrder e) {
        this.notifyObservers(e);
        return super.add(e);
    }

    /**
     * Get a prepare command with its id
     * @param id
     * @return prepare command with id
     */
    public CommandPrepareOrder getCommandById(long id){
        for(CommandPrepareOrder order : this){
            if(id == order.getOrder().getId()){ return order;}
        }
        return null;
    }

    public List<ShopOrdersObserver> getObservers() {
        return observers;
    }
}
