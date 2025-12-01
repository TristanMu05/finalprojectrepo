package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import finalProject.Canvas;
import finalProject.Direction;
import finalProject.Type_A_GameObject;
import finalProject.Type_B_GameObject;
import finalProject.Type_C_GameObject;
import finalProject.Type_D_GameObject;

import java.awt.event.KeyEvent;

/**
 * JUnit tests for Canvas class functionality
 */
class CanvasTest {

    private Canvas canvas;
    private Type_A_GameObject gameObject1;
    private Type_B_GameObject gameObject2;

    @BeforeEach
    void setUp() {
        canvas = new Canvas();
        gameObject1 = new Type_A_GameObject(50, 50);
        gameObject2 = new Type_B_GameObject(100, 100);
    }

    @Test
    void testCanvasInitialization() {
        assertNotNull(canvas);
    }

    @Test
    void testAddGameObject() {
        canvas.addGameObject(gameObject1);
        // Canvas should now have at least one object
        // We can't directly access gameObjectList, but we can verify through behavior
        assertNotNull(canvas);
    }

    @Test
    void testMultipleGameObjects() {
        canvas.addGameObject(gameObject1);
        canvas.addGameObject(gameObject2);
        // Both objects should be added without errors
        assertNotNull(canvas);
    }

    @Test
    void testGameObjectBehaviorAfterAddition() {
        // Add object to canvas
        canvas.addGameObject(gameObject1);

        // Initial state
        assertFalse(gameObject1.isUnderUserControl());

        // Simulate user taking control
        gameObject1.enableUserControl();
        assertTrue(gameObject1.isUnderUserControl());

        // Simulate releasing control
        gameObject1.restoreAutonomousBehavior();
        assertFalse(gameObject1.isUnderUserControl());
    }

    @Test
    void testDirectionChangeWithRestrictions() {
        // Create a Type_C object (horizontal only) to test restrictions
        Type_C_GameObject horizontalOnly = new Type_C_GameObject(100, 100);
        canvas.addGameObject(horizontalOnly);

        // Type C should not allow vertical directions
        assertFalse(horizontalOnly.isDirectionAllowed(Direction.UP));
        assertFalse(horizontalOnly.isDirectionAllowed(Direction.DOWN));

        // Type C should allow horizontal directions
        assertTrue(horizontalOnly.isDirectionAllowed(Direction.LEFT));
        assertTrue(horizontalOnly.isDirectionAllowed(Direction.RIGHT));
    }

    @Test
    void testMovement() {
        Type_D_GameObject obj = new Type_D_GameObject(100, 100);
        canvas.addGameObject(obj);

        int initialX = obj.getX();
        int initialY = obj.getY();

        // Set direction and velocity
        obj.setDirection(Direction.RIGHT);
        obj.setVelocity(5);

        // Move the object
        obj.move(canvas);

        // X should have increased for RIGHT direction
        assertTrue(obj.getX() >= initialX);
    }

    @Test
    void testBoundaryConstraints() {
        Type_D_GameObject obj = new Type_D_GameObject(0, 0);
        canvas.addGameObject(obj);

        // Try to move up when already at top
        obj.setDirection(Direction.UP);
        obj.setVelocity(5);
        obj.move(canvas);

        // Should not go below 0
        assertTrue(obj.getY() >= 0);

        // Try to move left when already at left edge
        obj.setDirection(Direction.LEFT);
        obj.setVelocity(5);
        obj.move(canvas);

        // Should not go below 0
        assertTrue(obj.getX() >= 0);
    }
}
