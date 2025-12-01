package finalProject;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Type_D_GameObject extends GameObject implements KeyListener {

  public Type_D_GameObject(int x, int y) {
    super(x, y);
    setDirection(Direction.DOWN);
    setVelocity(4);

    // Type D can move in all directions
    addAllowedDirection(Direction.UP);
    addAllowedDirection(Direction.DOWN);
    addAllowedDirection(Direction.LEFT);
    addAllowedDirection(Direction.RIGHT);

    imageList = new LinkedList<Icon>();
    ImageIcon upIcon = new ImageIcon("images/Type_D_Up.png");
    ImageIcon downIcon = new ImageIcon("images/Type_D_Down.png");
    ImageIcon leftIcon = new ImageIcon("images/Type_D_Right.png");
    ImageIcon rightIcon = new ImageIcon("images/Type_D_Left.png");
    
    // Add images only if they loaded successfully (getIconWidth() returns -1 on failure)
    if (upIcon.getIconWidth() > 0 && upIcon.getIconHeight() > 0) {
      imageList.add(upIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_D_Up.png");
      imageList.add(createPlaceholderIcon());
    }
    if (downIcon.getIconWidth() > 0 && downIcon.getIconHeight() > 0) {
      imageList.add(downIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_D_Down.png");
      imageList.add(imageList.isEmpty() ? createPlaceholderIcon() : imageList.get(0));
    }
    if (leftIcon.getIconWidth() > 0 && leftIcon.getIconHeight() > 0) {
      imageList.add(leftIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_D_Left.png");
      imageList.add(imageList.isEmpty() ? createPlaceholderIcon() : imageList.get(0));
    }
    if (rightIcon.getIconWidth() > 0 && rightIcon.getIconHeight() > 0) {
      imageList.add(rightIcon);
    } else {
      System.err.println("Warning: Could not load images/Type_D_Right.png");
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
        g.drawString("D", x + 20, y + 30);
      }
      public int getIconWidth() { return 50; }
      public int getIconHeight() { return 50; }
    };
  }

  public void move(Canvas c) {
    Icon icon = getCurrentImage();
    if (icon == null) {
      return;
    }

    int  iconHeight   = icon.getIconHeight();
    int  iconWidth    = icon.getIconWidth();
    java.awt.Dimension size = c.getSize();
    if (size == null) {
      return;
    }
    int  canvasHeight = (int)size.getHeight();
    int  canvasWidth  = (int)size.getWidth();

    // Autonomous behavior: move in opposite circular pattern from Type B
    // Type B goes: UP->RIGHT->DOWN->LEFT
    // Type D goes: DOWN->LEFT->UP->RIGHT (opposite direction)
    if (!isUnderUserControl()) {
      switch (getDirection()) {
        case Direction.UP:
          setY(getY() - getVelocity());
          if (getY() < 0) {
            setY(0);
            setDirection(Direction.LEFT); // Opposite of Type B (which goes RIGHT)
          }
          break;
        case Direction.DOWN:
          setY(getY() + getVelocity());
          if (getY() + iconHeight > canvasHeight) {
            setY((int)(canvasHeight - iconHeight));
            setDirection(Direction.RIGHT); // Opposite of Type B (which goes LEFT)
          }
          break;
        case Direction.LEFT:
          setX(getX() - getVelocity());
          if (getX() < 0) {
            setX(0);
            setDirection(Direction.DOWN); // Opposite of Type B (which goes UP)
          }
          break;
        case Direction.RIGHT:
          setX(getX() + getVelocity());
          if (getX() + iconWidth > canvasWidth) {
            setX((int)(canvasWidth - iconWidth));
            setDirection(Direction.UP); // Opposite of Type B (which goes DOWN)
          }
          break;
        default:
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
            setY((int)(canvasHeight - iconHeight));
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
            setX((int)(canvasWidth - iconWidth));
          }
          break;
        default:
          break;
      }
    }

  }

  //SPECIFY THE IMAGE TO DISPLAY
  //   USED FOR ANIMATION
  public void setImage() {
	    switch (getDirection()) {
	      case Direction.NONE:
	        currentImage = 0; // Show first image when no direction
	        break;
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
	    }
	  }

  public void keyTyped(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() != KeyEvent.VK_TAB) {
      setDirection(Direction.NONE);
    }
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      setDirection(Direction.UP);
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      setDirection(Direction.DOWN);
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      // Correct mapping: right arrow -> RIGHT
      setDirection(Direction.RIGHT);
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      // Correct mapping: left arrow -> LEFT
      setDirection(Direction.LEFT);
    }
  }

}