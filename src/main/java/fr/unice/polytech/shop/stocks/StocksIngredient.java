package fr.unice.polytech.shop.stocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import fr.unice.polytech.exception.StockException;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.recipe.RecipeIngredient;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Ingredient;
import fr.unice.polytech.recipe.item.Topping;
import fr.unice.polytech.recipe.item.Dough.DoughType;
import fr.unice.polytech.recipe.item.Flavour.FlavourType;
import fr.unice.polytech.recipe.item.Topping.ToppingType;
import fr.unice.polytech.shop.ShopOrdersObserver;

public class StocksIngredient extends HashMap<Ingredient,StockIngredient> implements ShopOrdersObserver {


    public StocksIngredient(int quantity) {
        Arrays.asList(DoughType.values()).forEach(type -> this.put(new Dough(type), new StockIngredient(new Dough(type), quantity)));
        Arrays.asList(FlavourType.values()).forEach(type -> this.put(new Flavour(type), new StockIngredient(new Flavour(type), quantity)));
        Arrays.asList(ToppingType.values()).forEach(type -> this.put(new Topping(type), new StockIngredient(new Topping(type), quantity)));
    }

    @Override
    public void update(Order order){
        if(order != null){
            HashMap<StockIngredient, Integer> stockAndQuantity = this.stockAndQuantity(order);
            stockAndQuantity.keySet().forEach(stockIngredient -> stockIngredient.update(stockAndQuantity.get(stockIngredient)));
        }
    }

    /**
     * Generate a HashMap with the stock of and ingredient and the quantity needed of this ingredient for the order
     * @param order
     * @return the stock ingredient associated to the quantity asked
     */
    private HashMap<StockIngredient, Integer> stockAndQuantity(Order order) throws StockException {
        if(order != null){
            HashMap<StockIngredient, Integer> stockAndQuantity = new HashMap<StockIngredient,Integer>();
            for(OrderItem orderItem : order) {
                List<RecipeIngredient> ingredients = new ArrayList<>();
                ingredients.add(orderItem.getCookie().getDough());
                ingredients.add(orderItem.getCookie().getFlavour());
                ingredients.addAll(orderItem.getCookie().getToppings());
                ingredients.forEach(ingredient ->{
                    if(stockAndQuantity.containsKey(this.get(ingredient.getIngredient()))){
                        stockAndQuantity.put(this.get(ingredient.getIngredient()),stockAndQuantity.get(this.get(ingredient.getIngredient())) + orderItem.getCount()*orderItem.getCookie().getDose());
                    } else {
                        stockAndQuantity.put(this.get(ingredient.getIngredient()), orderItem.getCount()*orderItem.getCookie().getDose());
                    }
                });
            }
            return stockAndQuantity;
        }
        throw new StockException("Order is null");
    }

    /**
     * Check if there is enough stock for the order
     * @param order
     * @return true if there is enough stock for the order, else false
     */
    public boolean enoughStock(Order order){
        if(order == null){
            return false;
        }
        HashMap<StockIngredient, Integer> stockAndQuantity = this.stockAndQuantity(order);
        boolean enoughStock = true;
        for(StockIngredient sI : stockAndQuantity.keySet()){
            enoughStock = enoughStock && sI.enoughQuantity(stockAndQuantity.get(sI))>=0;
        }   
        return enoughStock;
    }
    
}
