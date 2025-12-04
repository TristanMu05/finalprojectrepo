package finalProject;

import java.awt.Component;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.Icon;

public abstract class GameObject {
	//EACH GAME OBJECT HAS AN X,Y LOCATION, VELOCITY, AND A DIRECTION

	private int x;
	private int y;
	private int velocity;
	private int direction;

	//EACH GAME OBJECT CAN HAVE A COLLECTION OF IMAGES
	protected List<Icon> imageList;
	protected int currentImage;

	// Track allowed directions for this GameObject
	protected Set<Integer> allowedDirections;

	// Store original autonomous behavior
	private int originalDirection;
	private int originalVelocity;
	private boolean isUnderUserControl; 

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		velocity = 0;
		currentImage = 0;
		allowedDirections = new HashSet<Integer>();
		isUnderUserControl = false;
		originalDirection = Direction.NONE;
		originalVelocity = 0;
	}

	public void draw(Component c, Graphics g) {
		if (imageList != null && !imageList.isEmpty() && currentImage >= 0 && currentImage < imageList.size()) {
			Icon icon = imageList.get(currentImage);
			if (icon != null) {
				icon.paintIcon(c, g, x, y);
			}
		}
	}

	// SETTERS AND GETTERS

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public int getVelocity() {
		return velocity;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Icon getCurrentImage() {
		return imageList.get(currentImage);
	}

	// Methods for managing allowed directions
	public void addAllowedDirection(int direction) {
		allowedDirections.add(direction);
	}

	public boolean isDirectionAllowed(int direction) {
		return allowedDirections.isEmpty() || allowedDirections.contains(direction);
	}

	public Set<Integer> getAllowedDirections() {
		return new HashSet<Integer>(allowedDirections);
	}

	// Methods for managing autonomous behavior
	public void saveAutonomousBehavior() {
		originalDirection = direction;
		originalVelocity = velocity;
	}

	public void enableUserControl() {
		if (!isUnderUserControl) {
			saveAutonomousBehavior();
			isUnderUserControl = true;
		}
	}

	public void restoreAutonomousBehavior() {
		if (isUnderUserControl) {
			direction = originalDirection;
			velocity = originalVelocity;
			isUnderUserControl = false;
		}
	}

	public boolean isUnderUserControl() {
		return isUnderUserControl;
	}

	// New: disable user control but keep the current direction/velocity so the
	// object continues moving in the direction the user left it in when they
	// tab away.
	public void disableUserControlKeepCurrent() {
		if (isUnderUserControl) {
			// Do not restore original autonomous direction/velocity; just exit
			// user-control mode so the object keeps its current direction/velocity.
			isUnderUserControl = false;
		}
	}

	//ABSTRACT METHODS
	public abstract void move(Canvas c);
	public abstract void setImage();

}