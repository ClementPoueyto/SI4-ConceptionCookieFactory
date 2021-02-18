package fr.unice.polytech.shop;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.shop.stocks.StocksIngredient;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Dough.DoughType;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Flavour.FlavourType;
import fr.unice.polytech.recipe.item.Topping.ToppingType;

public class StocksIngredientTest {

    StocksIngredient stocksIngredient;
    Order order;
    Shop shop = mock(Shop.class);
    ArrayList<OrderItem> orderItems;
    Guest guest = new Guest("");

    @BeforeEach
    public void setUp() {
        this.stocksIngredient = new StocksIngredient(10);
        this.orderItems = new ArrayList<>();
    }

    @Test
    /**
     * Check the method enoughStock ok StocksIngredient in case that there is enough quantity for all ingredients
     * @throws Exception
     */
    void enoughQuantityTest() throws Exception {
        OrderItem oI = new OrderItem(RecipeBuilder.prepareCHOCOLALALA(), 3);
        this.orderItems.add(oI);
        order = new Order(guest, shop, orderItems);
        this.stocksIngredient.update(order);
        assertEquals(7, this.stocksIngredient.get(new Flavour(FlavourType.CINNAMON)).getQuantity());
        assertTrue(this.stocksIngredient.enoughStock(order));
    }

    @Test
    /**
     * Check if the method enoughStock of StocksIngredient throw an exception if there is not enough quantity for an ingredient at least but enoughQuantity of a StockIngredient return an integer superior to 0 because there is enough quantity of this ingredient
     */
    void notEnoughQuantityForOneIngredientTest(){
        Recipe recipe = new RecipeBuilder(DoughType.CHOCOLATE, CookingType.CHEWY)
        .withFlavour(FlavourType.CINNAMON)
        .withMix(MixType.MIXED)
        .withTopping(ToppingType.DARK_CHOCOLATE)
        .build();
        OrderItem oI = new OrderItem(RecipeBuilder.prepareCHOCOLALALA(), 3);
        OrderItem oI2 = new OrderItem(recipe, 8);
        this.orderItems.add(oI);
        this.orderItems.add(oI2);
        order = new Order(guest, shop, orderItems);
        RuntimeException e = assertThrows(RuntimeException.class, ()-> this.stocksIngredient.enoughStock(order));
        assertEquals("Quantity needed (11) of Cinnamon for an order is higher than the available stock (10)", e.getMessage());
        assertThrows(RuntimeException.class, ()-> this.stocksIngredient.get(new Flavour(FlavourType.CINNAMON)).enoughQuantity(oI.getCount()+oI2.getCount()));
        assertTrue(this.stocksIngredient.get(new Dough(DoughType.CHOCOLATE)).enoughQuantity(oI2.getCount())>0);

    }

    @Test
    /**
     * check if an exception is thrown if there is not enough quantity for the ingredients needed for an order
     */
    void notEnoughQuantityTest() {
        OrderItem oI = new OrderItem(RecipeBuilder.prepareCHOCOLALALA(), 11);
        this.orderItems.add(oI);
        order = new Order(guest, shop, orderItems);
        assertThrows(RuntimeException.class, ()-> this.stocksIngredient.update(order));
    }
    

}

