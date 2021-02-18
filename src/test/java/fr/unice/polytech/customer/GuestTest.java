package fr.unice.polytech.customer;

import fr.unice.polytech.factory.FactoryFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {

    Guest guest;
    FactoryFacade factory;

    @BeforeEach
    public void setUp() {
        guest = new Guest("guest@gmail.com");
        factory = new FactoryFacade();
    }

    @Test
    public void checkRegister() {
//        guest.setRegistered(true);
//        assertTrue(guest.isRegistered());
//        guest.setRegistered(false);
//        assertFalse(guest.isRegistered());
    }

    @Test
    public void register() {
        assertNull(factory.findUser(guest.getEmail()));

        User user = factory.addUser(guest, "pedro");

        assertNotNull(user);
        assertEquals(user.getName(), "pedro");
        assertEquals(user, factory.findUser(user.getEmail()));
    }

    @Test
    public void cookiePot() {
        assertEquals(0, guest.getCookiePot());
    }

}
