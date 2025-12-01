package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import finalProject.Direction;

/**
 * JUnit tests for Direction constants
 */
class DirectionTest {

    @Test
    void testDirectionConstants() {
        assertEquals(0, Direction.NONE);
        assertEquals(1, Direction.UP);
        assertEquals(2, Direction.LEFT);
        assertEquals(3, Direction.DOWN);
        assertEquals(4, Direction.RIGHT);
    }

    @Test
    void testDirectionUniqueness() {
        // Ensure all directions have unique values
        int[] directions = {
            Direction.NONE,
            Direction.UP,
            Direction.LEFT,
            Direction.DOWN,
            Direction.RIGHT
        };

        for (int i = 0; i < directions.length; i++) {
            for (int j = i + 1; j < directions.length; j++) {
                assertNotEquals(directions[i], directions[j],
                    "Direction values should be unique");
            }
        }
    }

    @Test
    void testDirectionRange() {
        // Ensure directions are within expected range
        assertTrue(Direction.NONE >= 0);
        assertTrue(Direction.UP >= 0);
        assertTrue(Direction.LEFT >= 0);
        assertTrue(Direction.DOWN >= 0);
        assertTrue(Direction.RIGHT >= 0);

        assertTrue(Direction.NONE <= 4);
        assertTrue(Direction.UP <= 4);
        assertTrue(Direction.LEFT <= 4);
        assertTrue(Direction.DOWN <= 4);
        assertTrue(Direction.RIGHT <= 4);
    }
}
