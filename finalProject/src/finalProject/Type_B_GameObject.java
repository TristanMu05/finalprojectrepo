package finalProject;

import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Type B GameObject - Can move in all 4 directions
 */
public class Type_B_GameObject extends GameObject {

  public Type_B_GameObject(int x, int y) {
    super(x, y);
    setDirection(Direction.RIGHT);
    setVelocity(4);

    // Type B can move in all directions
    addAllowedDirection(Direction.UP);
    addAllowedDirection(Direction.DOWN);
    addAllowedDirection(Direction.LEFT);
    addAllowedDirection(Direction.RIGHT);

    imageList = new LinkedList<Icon>();
    ImageIcon upIcon = new ImageIcon("images/Type_B_Up.png");
    ImageIcon downIcon = new ImageIcon("images/Type_B_Down.png");
    ImageIcon leftIcon = new ImageIcon("images/Type_B_Left.png");
    ImageIcon rightIcon = new ImageIcon("images/Type_B_Right.png");

    // Add images or placeholders
    if (upIcon.getIconWidth() > 0 && upIcon.getIconHeight() > 0) {
      imageList.add(upIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_B_Up.png");
      imageList.add(createPlaceholderIcon());
    }
    if (downIcon.getIconWidth() > 0 && downIcon.getIconHeight() > 0) {
      imageList.add(downIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_B_Down.png");
      imageList.add(imageList.isEmpty() ? createPlaceholderIcon() : imageList.get(0));
    }
    if (leftIcon.getIconWidth() > 0 && leftIcon.getIconHeight() > 0) {
      imageList.add(leftIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_B_Left.png");
      imageList.add(imageList.isEmpty() ? createPlaceholderIcon() : imageList.get(0));
    }
    if (rightIcon.getIconWidth() > 0 && rightIcon.getIconHeight() > 0) {
      imageList.add(rightIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_B_Right.png");
      imageList.add(imageList.isEmpty() ? createPlaceholderIcon() : imageList.get(0));
    }

    // Save initial autonomous behavior
    saveAutonomousBehavior();
  }

  private Icon createPlaceholderIcon() {
    return new javax.swing.Icon() {
      public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        g.setColor(java.awt.Color.GREEN);
        g.fillRect(x, y, 50, 50);
        g.setColor(java.awt.Color.WHITE);
        g.drawString("B", x + 20, y + 30);
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

    int iconHeight = icon.getIconHeight();
    int iconWidth = icon.getIconWidth();
    java.awt.Dimension size = c.getSize();
    if (size == null) {
      return;
    }
    int canvasHeight = (int) size.getHeight();
    int canvasWidth = (int) size.getWidth();

    // Autonomous behavior: move in diagonal pattern
    if (!isUnderUserControl()) {
      switch (getDirection()) {
        case Direction.UP:
          setY(getY() - getVelocity());
          if (getY() < 0) {
            setY(0);
            setDirection(Direction.RIGHT);
          }
          break;
        case Direction.DOWN:
          setY(getY() + getVelocity());
          if (getY() + iconHeight > canvasHeight) {
            setY((int) (canvasHeight - iconHeight));
            setDirection(Direction.LEFT);
          }
          break;
        case Direction.LEFT:
          setX(getX() - getVelocity());
          if (getX() < 0) {
            setX(0);
            setDirection(Direction.UP);
          }
          break;
        case Direction.RIGHT:
          setX(getX() + getVelocity());
          if (getX() + iconWidth > canvasWidth) {
            setX((int) (canvasWidth - iconWidth));
            setDirection(Direction.DOWN);
          }
          break;
      }
    } else {
      // User-controlled movement
      switch (getDirection()) {
        case Direction.UP:
          setY(getY() - getVelocity());
          if (getY() < 0) {
            setY(0);
          }
          break;
        case Direction.DOWN:
          setY(getY() + getVelocity());
          if (getY() + iconHeight > canvasHeight) {
            setY((int) (canvasHeight - iconHeight));
          }
          break;
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
      case Direction.UP:
        currentImage = 0;
        break;
      case Direction.DOWN:
        currentImage = 1;
        break;
      case Direction.LEFT:
        currentImage = 2;
        break;
      case Direction.RIGHT:
        currentImage = 3;
        break;
      default:
        currentImage = 0;
        break;
    }
  }
}
