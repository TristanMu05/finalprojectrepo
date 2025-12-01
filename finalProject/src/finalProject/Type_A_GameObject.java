package finalProject;

import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Type A GameObject - Vertical movement only (UP and DOWN)
 */
public class Type_A_GameObject extends GameObject {

  public Type_A_GameObject(int x, int y) {
    super(x, y);
    setDirection(Direction.DOWN);
    setVelocity(3);

    // Type A can only move vertically
    addAllowedDirection(Direction.UP);
    addAllowedDirection(Direction.DOWN);

    imageList = new LinkedList<Icon>();
    ImageIcon upIcon = new ImageIcon("images/Type_A_Up.png");
    ImageIcon downIcon = new ImageIcon("images/Type_A_Down.png");

    // Add images or placeholders
    if (upIcon.getIconWidth() > 0 && upIcon.getIconHeight() > 0) {
      imageList.add(upIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_A_Up.png");
      imageList.add(createPlaceholderIcon());
    }
    if (downIcon.getIconWidth() > 0 && downIcon.getIconHeight() > 0) {
      imageList.add(downIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_A_Down.png");
      imageList.add(imageList.isEmpty() ? createPlaceholderIcon() : imageList.get(0));
    }

    // Save initial autonomous behavior
    saveAutonomousBehavior();
  }

  private Icon createPlaceholderIcon() {
    return new javax.swing.Icon() {
      public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        g.setColor(java.awt.Color.RED);
        g.fillRect(x, y, 50, 50);
        g.setColor(java.awt.Color.WHITE);
        g.drawString("A", x + 20, y + 30);
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
    java.awt.Dimension size = c.getSize();
    if (size == null) {
      return;
    }
    int canvasHeight = (int) size.getHeight();

    // Autonomous behavior: bounce vertically
    if (!isUnderUserControl()) {
      switch (getDirection()) {
        case Direction.UP:
          setY(getY() - getVelocity());
          if (getY() < 0) {
            setY(0);
            setDirection(Direction.DOWN); // Bounce
          }
          break;
        case Direction.DOWN:
          setY(getY() + getVelocity());
          if (getY() + iconHeight > canvasHeight) {
            setY((int) (canvasHeight - iconHeight));
            setDirection(Direction.UP); // Bounce
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
      default:
        currentImage = 0;
        break;
    }
  }
}
