package fr.unice.polytech.factory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.order.command.CommandPlaceOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.customer.User;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.tools.Position;


public class FactoryTest {

    private FactoryFacade factory;

    @BeforeEach
    public void setUp() {
        factory = new FactoryFacade();
    }

    @Test
    public void findUser() throws Exception {
        factory.addUser(new User("toto", "toto@gmail.com"));
        factory.addUser(new User("titi", "titi@gmail.com"));
        factory.addUser(new User("tutu", "tutu@gmail.com"));
        User user = factory.findUser("tutu@gmail.com");
        assertEquals("tutu@gmail.com", user.getEmail());
        assertEquals("tutu", user.getName());
    }

    @Test
    public void startCommandtest() {
        Shop shop = new Shop(new FactoryFacade());
        Guest gege = new Guest("gege@gmail.com");
        ArrayList<OrderItem> items = new ArrayList<>();
        Order newOrder = new Order(gege, shop, items);
        newOrder.setPickupDate(LocalDateTime.of(2020, 5, 26, 10, 0));

        try {
            factory.startCommand(newOrder);
        } catch (Exception e) {
        }
        assertNotEquals(null,gege.getCommand());

    }

    @Test
    public void payCommandtest() {
        Shop shop = new Shop(new FactoryFacade());
        Guest gege = new Guest("gege@gmail.com");
        Guest gege2 = new Guest("gege@gmail.com");

        ArrayList<OrderItem> items = new ArrayList<>();
        Order newOrder =  new Order(gege, shop, items);
        Order newOrder2 =  new Order(gege2, shop, items);

        newOrder.setPickupDate(LocalDateTime.of(2020,5,26,10,0));
        newOrder2.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        try {
            factory.startCommand(newOrder);
            factory.payCommand(newOrder, newOrder.calculatePrice());

        } catch (Exception e) {
        }
        assertEquals(OrderStatus.VALIDATED,newOrder.getOrderStatus());
        assertNotEquals(null,gege.getCommand());

        try {
            factory.startCommand(newOrder2);
            factory.payCommand(newOrder2, newOrder.calculatePrice()-1);

        }
        catch (Exception e){}
        assertEquals(OrderStatus.PLACED,newOrder2.getOrderStatus());
        assertNotEquals(null,gege.getCommand());
    }

    @Test
    public void getNearestShopsTest() {
        FactoryFacade f = new FactoryFacade();
        Shop shop1  = new Shop(f, new Position(0,0));
        Shop shop2 = new Shop(f, new Position(89,179));
        Shop shop3  = new Shop(f, new Position(10,10));
        Shop shop4 = new Shop(f, new Position(35,35));

        List<Shop> shops = f.getNearestShops(shop1);
        assertEquals(3,shops.size());
        assertEquals(shop3,shops.get(0));
        assertEquals(shop4,shops.get(1));
        assertEquals(shop2,shops.get(2));

    }
    @Test
    public void getCookieTest() {
        assertNull(factory.getCookie("test"));
    }

    @Test
    public void removeWorstRecipeTest(){
        FactoryFacade f = mock(FactoryFacade.class);
        Recipe worstrecipe = new RecipeBuilder(Dough.DoughType.CHOCOLATE, CookingType.CHEWY).withFlavour(Flavour.FlavourType.VANILLA).withTopping(Topping.ToppingType.DARK_CHOCOLATE).withMix(MixType.TOPPED).build();
        when(f.getWorstRecipe()).thenReturn(worstrecipe);
        assertEquals(worstrecipe, f.getWorstRecipe());
        f.removeWorstRecipe();

    }

    @Test
    public void cancelCommandTest() throws UnavailableShopException {
        Order o = new Order(new Guest("test"),new Shop(factory),new ArrayList<>(), LocalDateTime.of(2012,12,12,10,10));
        CommandPlaceOrder cpo = new CommandPlaceOrder(o);
        cpo.execute();
        assertDoesNotThrow(()->factory.cancelCommand(cpo));
    }
}
