package fr.unice.polytech.position;

import fr.unice.polytech.factory.FactoryFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.tools.Position;
import fr.unice.polytech.customer.User;
import fr.unice.polytech.shop.Shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
    User user;
    FactoryFacade factory;
    Shop shop;
    Position posUser;
    Position posShop;

    @BeforeEach
    public void setUp() throws Exception {
        Position posCustomer = new Position(43.615669, 7.071896); // correspond à l'adresse de polytech
        Position posShop = new Position(43.696605, 7.271190); //subway place masséna
        user = new User("Véro", "vero@gmail.com", posCustomer);
        factory = new FactoryFacade();
        shop = new Shop(factory,posShop);
    }


    @Test
    public void getDistance(){
        posUser = new Position(0,0);
        posShop = new Position(89,89);
        assertEquals(1.0005603E7, posShop.distance(posUser));
    }


}
