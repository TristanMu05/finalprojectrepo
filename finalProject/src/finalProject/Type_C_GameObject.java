package finalProject;

import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Type C GameObject - Horizontal movement only (LEFT and RIGHT)
 */
public class Type_C_GameObject extends GameObject {

  public Type_C_GameObject(int x, int y) {
    super(x, y);
    setDirection(Direction.RIGHT);
    setVelocity(5);

    // Type C can only move horizontally
    addAllowedDirection(Direction.LEFT);
    addAllowedDirection(Direction.RIGHT);

    imageList = new LinkedList<Icon>();
    ImageIcon leftIcon = new ImageIcon("images/Type_C_Left.png");
    ImageIcon rightIcon = new ImageIcon("images/Type_C_Right.png");

    // Add images or placeholders
    if (leftIcon.getIconWidth() > 0 && leftIcon.getIconHeight() > 0) {
      imageList.add(leftIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_C_Left.png");
      imageList.add(createPlaceholderIcon());
    }

    if (rightIcon.getIconWidth() > 0 && rightIcon.getIconHeight() > 0) {
      imageList.add(rightIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_C_Right.png");
      imageList.add(imageList.isEmpty() ? createPlaceholderIcon() : imageList.get(0));
    }

    // Save initial autonomous behavior
    saveAutonomousBehavior();
  }

  private Icon createPlaceholderIcon() {
    return new javax.swing.Icon() {
      public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        g.setColor(java.awt.Color.BLUE);
        g.fillRect(x, y, 50, 50);
        g.setColor(java.awt.Color.WHITE);
        g.drawString("C", x + 20, y + 30);
      }
      public int getIconWidth() { return 50; }
      public int getIconHeight() { return 50; }
    };
  }

  @Override
  public void move(Canvas c) {
    Icon icon = getCurrentImage();
    if (icon == null) {
      return;
    }

    int iconWidth = icon.getIconWidth();
    java.awt.Dimension size = c.getSize();
    if (size == null) {
      return;
    }
    int canvasWidth = (int) size.getWidth();

    // Autonomous behavior: bounce horizontally
    if (!isUnderUserControl()) {
      switch (getDirection()) {
        case Direction.LEFT:
          setX(getX() - getVelocity());
          if (getX() < 0) {
            setX(0);
            setDirection(Direction.RIGHT); // Bounce
          }
          break;
        case Direction.RIGHT:
          setX(getX() + getVelocity());
          if (getX() + iconWidth > canvasWidth) {
            setX((int) (canvasWidth - iconWidth));
            setDirection(Direction.LEFT); // Bounce
          }
          break;
      }
    } else {
      // User-controlled movement
      switch (getDirection()) {
        case Direction.LEFT:
          setX(getX() - getVelocity());
          if (getX() < 0) {
            setX(0);
          }
          break;
        case Direction.RIGHT:
          setX(getX() + getVelocity());
          if (getX() + iconWidth > canvasWidth) {
            setX((int) (canvasWidth - iconWidth));
          }
          break;
      }
    }
  }

  @Override
  public void setImage() {
    switch (getDirection()) {
      case Direction.LEFT:
        currentImage = 0;
        break;
      case Direction.RIGHT:
        currentImage = 1;
        break;
      default:
        currentImage = 0;
        break;
    }
  }
}
