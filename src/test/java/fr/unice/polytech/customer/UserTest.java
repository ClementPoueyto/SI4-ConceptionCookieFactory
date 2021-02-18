package fr.unice.polytech.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("name", "user@gmail.com");
    }

    @Test
    public void isMember() {
        assertFalse(user.isMember());
        user.setMember(true);
        assertTrue(user.isMember());
    }

    @Test
    public void subscribeToLoyaltyProgramTest(){
        user.subscribeToLoyaltyProgram();
        assertTrue(user.isMember);
        assertFalse(user.subscribeToLoyaltyProgram());
    }

    @Test
    public void cookiePotNb() {
        assertEquals(user.getCookiePot(), 0);
        user.setCookiePot(2);
        assertEquals(2, user.getCookiePot());
        user.addToCookiePot(3);
        assertEquals(5, user.getCookiePot());
    }

    @Test
    public void idGeneration() { // WARNING: not testable as id depends on Time! (can fail with a difference of 1ms)
//        assertEquals(new Date().getTime(), user.getId());
//        User user2 = new User("another name", "another mail");
//        assertEquals(new Date().getTime(), user2.getId());
    }

}
