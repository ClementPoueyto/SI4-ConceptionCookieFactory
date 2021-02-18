package fr.unice.polytech.marceleat;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.Test;

public class MarcelEatTest {

    MarcelEat marcelEat;


    @Test
    public void testMarcelEat(){
        LocalDateTime lDateTime = LocalDateTime.now().plusHours(1);
        marcelEat = new MarcelEat();
        assertEquals(3.0, marcelEat.askDelivery(5000,lDateTime), 0.01);
        lDateTime = LocalDateTime.now().minusMinutes(40);
        assertEquals(4.5, marcelEat.askDelivery(5000,lDateTime), 0.01);
        assertEquals(0.0, marcelEat.askDelivery(10001,lDateTime), 0.01);
        assertNotEquals(0.0, marcelEat.askDelivery(10000,lDateTime), 0.01);
    }
}
