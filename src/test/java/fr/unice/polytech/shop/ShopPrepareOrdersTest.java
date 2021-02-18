package fr.unice.polytech.shop;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import fr.unice.polytech.shop.stocks.StocksIngredient;
import fr.unice.polytech.tools.Analytics;

public class ShopPrepareOrdersTest {

    ShopPrepareOrders shopPrepareOrders;
    ShopOrdersObserver shopOrdersObserver;

    @Test 
    public void addObserverTest(){
        shopPrepareOrders = new ShopPrepareOrders();
        shopOrdersObserver = new StocksIngredient(10);
        assertEquals(shopOrdersObserver, shopPrepareOrders.addObserver(shopOrdersObserver).getObservers().get(0));
    }

    @Test 
    public void addObserversTest(){
        shopPrepareOrders = new ShopPrepareOrders();
        shopOrdersObserver = new StocksIngredient(10);
        ShopOrdersObserver shopOrdersObserver2 = new Analytics();
        assertEquals(shopOrdersObserver, shopPrepareOrders.addObservers(Arrays.asList(new ShopOrdersObserver[]{shopOrdersObserver,shopOrdersObserver2})).getObservers().get(0));
        assertEquals(shopOrdersObserver2, shopPrepareOrders.getObservers().get(1));
    }

    
}
