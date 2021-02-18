package fr.unice.polytech.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PositionTest {

    Position position;

    @BeforeEach
    public void setUp() {
       position = new Position(50,50);
    }

    @Test
    public void positionTest(){
        position = new Position(-100,-200);
        assertEquals(0, position.getLatitude());
        assertEquals(0,position.getLongitude());
    }

    @Test
    public void distance(){
        Position pos1 = new Position(32,51);
        Position pos2 = new Position(17,22);

        assertEquals(3358485,pos1.distance(pos2));

        Position pos3 = new Position(32,-49);
        Position pos4 = new Position(-30,22);

        assertEquals(10172266,pos3.distance(pos4));
    }
}
