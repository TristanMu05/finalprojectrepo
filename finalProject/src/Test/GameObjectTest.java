package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import finalProject.Direction;
import finalProject.Type_A_GameObject;
import finalProject.Type_B_GameObject;
import finalProject.Type_C_GameObject;
import finalProject.Type_D_GameObject;
import finalProject.GameObject;

/**
 * JUnit tests for GameObject behavior
 */
class GameObjectTest {

    private Type_A_GameObject typeA;
    private Type_B_GameObject typeB;
    private Type_C_GameObject typeC;
    private Type_D_GameObject typeD;

    @BeforeEach
    void setUp() {
        typeA = new Type_A_GameObject(100, 100);
        typeB = new Type_B_GameObject(200, 200);
        typeC = new Type_C_GameObject(300, 300);
        typeD = new Type_D_GameObject(100, 100);
    }

    @Test
    void testGameObjectInitialization() {
        assertEquals(100, typeA.getX());
        assertEquals(100, typeA.getY());
        assertFalse(typeA.isUnderUserControl());
    }

    @Test
    void testDirectionRestrictions_TypeA() {
        // Type A should only allow vertical movement
        assertTrue(typeA.isDirectionAllowed(Direction.UP));
        assertTrue(typeA.isDirectionAllowed(Direction.DOWN));
        assertFalse(typeA.isDirectionAllowed(Direction.LEFT));
        assertFalse(typeA.isDirectionAllowed(Direction.RIGHT));
    }

    @Test
    void testDirectionRestrictions_TypeB() {
        // Type B should allow all 4 directions
        assertTrue(typeB.isDirectionAllowed(Direction.UP));
        assertTrue(typeB.isDirectionAllowed(Direction.DOWN));
        assertTrue(typeB.isDirectionAllowed(Direction.LEFT));
        assertTrue(typeB.isDirectionAllowed(Direction.RIGHT));
    }

    @Test
    void testDirectionRestrictions_TypeC() {
        // Type C should only allow horizontal movement
        assertFalse(typeC.isDirectionAllowed(Direction.UP));
        assertFalse(typeC.isDirectionAllowed(Direction.DOWN));
        assertTrue(typeC.isDirectionAllowed(Direction.LEFT));
        assertTrue(typeC.isDirectionAllowed(Direction.RIGHT));
    }

    @Test
    void testDirectionRestrictions_TypeD() {
        // Type D should allow all 4 directions
        assertTrue(typeD.isDirectionAllowed(Direction.UP));
        assertTrue(typeD.isDirectionAllowed(Direction.DOWN));
        assertTrue(typeD.isDirectionAllowed(Direction.LEFT));
        assertTrue(typeD.isDirectionAllowed(Direction.RIGHT));
    }

    @Test
    void testUserControlToggle() {
        // Initially not under user control
        assertFalse(typeA.isUnderUserControl());

        // Enable user control
        typeA.enableUserControl();
        assertTrue(typeA.isUnderUserControl());

        // Restore autonomous behavior
        typeA.restoreAutonomousBehavior();
        assertFalse(typeA.isUnderUserControl());
    }

    @Test
    void testAutonomousBehaviorPreservation() {
        // Set initial state
        typeA.setDirection(Direction.DOWN);
        typeA.setVelocity(4);

        // Enable user control (should save current state)
        typeA.enableUserControl();

        // Change direction and velocity under user control
        typeA.setDirection(Direction.UP);
        typeA.setVelocity(5);

        // Verify changed values
        assertEquals(Direction.UP, typeA.getDirection());
        assertEquals(5, typeA.getVelocity());

        // Restore autonomous behavior
        typeA.restoreAutonomousBehavior();

        // Should restore to original values
        assertEquals(Direction.DOWN, typeA.getDirection());
        assertEquals(4, typeA.getVelocity());
    }

    @Test
    void testSettersAndGetters() {
        typeA.setX(150);
        typeA.setY(250);
        typeA.setVelocity(10);
        typeA.setDirection(Direction.LEFT);

        assertEquals(150, typeA.getX());
        assertEquals(250, typeA.getY());
        assertEquals(10, typeA.getVelocity());
        assertEquals(Direction.LEFT, typeA.getDirection());
    }

    @Test
    void testImageListNotNull() {
        assertNotNull(typeA.getCurrentImage());
        assertNotNull(typeB.getCurrentImage());
        assertNotNull(typeC.getCurrentImage());
        assertNotNull(typeD.getCurrentImage());
    }

    @Test
    void testAllowedDirectionsSet() {
        // Type A should have 2 allowed directions (UP, DOWN)
        assertEquals(2, typeA.getAllowedDirections().size());

        // Type B should have 4 allowed directions
        assertEquals(4, typeB.getAllowedDirections().size());

        // Type C should have 2 allowed directions (LEFT, RIGHT)
        assertEquals(2, typeC.getAllowedDirections().size());

        // Type D should have 4 allowed directions
        assertEquals(4, typeD.getAllowedDirections().size());
    }

    @Test
    void testAddAllowedDirection() {
        GameObject newObject = new Type_D_GameObject(0, 0);
        // Clear and add specific direction
        newObject.addAllowedDirection(Direction.UP);
        assertTrue(newObject.isDirectionAllowed(Direction.UP));
    }
}
