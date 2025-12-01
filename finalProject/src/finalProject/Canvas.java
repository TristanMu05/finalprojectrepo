package finalProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Canvas extends JComponent implements ActionListener, KeyListener {
	// DEFAULT SERIAL NUMBER
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private Timer gameLoopTimer;
	private List<GameObject> gameObjectList;
	private int highlighted = 0;
	


	public Canvas() {
		// TASK 1: CREATE A LIST OF CHARACTERS THAT WILL APPEAR ON THE CANVAS
		gameObjectList = new LinkedList<GameObject>();

		// TASK 2: CREATE A WINDOW FOR THE APPLICATION
		frame = new JFrame("Animation Canvas");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		
		// Set preferred size for the component
		setPreferredSize(new java.awt.Dimension(800, 800));
		setFocusable(true);

		// TASK 3: CONSTRUCT A TIMER FOR GAME LOOP
		gameLoopTimer = new Timer(25, this);
		gameLoopTimer.start();
		
		setFocusTraversalKeysEnabled(false);
	    addKeyListener(this);

		// TASK 4: MAKE THE WINDOW VISIBLE
		frame.setVisible(true);
		frame.pack();
		
		// Force initial repaint
		repaint();

	}
	
	public void start() {
		gameLoopTimer.start();
	}
	
	/**
	 * Adds GameObjects to the List, which are latter added to the Canvas
	 */
	public synchronized void addGameObject(GameObject sprite) {
		gameObjectList.add(sprite);
		repaint(); // Repaint when a new object is added
	}

	/**
	 * Draws the GameObject graphic onto the Canvas
	 */
	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < gameObjectList.size(); i++) {
			GameObject s = gameObjectList.get(i);
			s.draw(this, g);

			// Draw highlight square around selected GameObject
			if (i == highlighted) {
				g.setColor(java.awt.Color.YELLOW);
				java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
				g2d.setStroke(new java.awt.BasicStroke(3));

				// Get icon dimensions for the square
				javax.swing.Icon icon = s.getCurrentImage();
				if (icon != null) {
					int width = icon.getIconWidth();
					int height = icon.getIconHeight();
					g2d.drawRect(s.getX() - 2, s.getY() - 2, width + 4, height + 4);
				}
			}
		}
	}
	
	
	// ****************************************************
	// Canvas must implement the inherited abstract method
	// ActionListener.actionPerformed(ActionEvent)
	public synchronized void actionPerformed(ActionEvent e) {
		for (GameObject gameObject : gameObjectList) {
			gameObject.move(this);
			gameObject.setImage();
		}
		repaint();
	}


	
	
	// ****************************************************
	// Canvas must implement the inherited abstract methods
	// for KeyListener
	
	  public void keyTyped(KeyEvent e) {
	  }

	  public void keyPressed(KeyEvent e) {
	    if (gameObjectList.isEmpty()) {
	      return;
	    }

	    GameObject s = gameObjectList.get(highlighted);

	    // Enable user control for the highlighted object
	    s.enableUserControl();

	    int requestedDirection = Direction.NONE;

	    switch (e.getKeyCode()) {
	      case KeyEvent.VK_UP:
	        requestedDirection = Direction.UP;
	        break;
	      case KeyEvent.VK_DOWN:
	        requestedDirection = Direction.DOWN;
	        break;
	      case KeyEvent.VK_LEFT:
	        requestedDirection = Direction.LEFT;
	        break;
	      case KeyEvent.VK_RIGHT:
	        requestedDirection = Direction.RIGHT;
	        break;
	    }

	    // Only allow movement in permitted directions
	    if (requestedDirection != Direction.NONE && s.isDirectionAllowed(requestedDirection)) {
	      s.setDirection(requestedDirection);
	      s.setVelocity(5);
	    }
	  }

	  public void keyReleased(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_TAB) {
	      // Restore autonomous behavior for the currently highlighted object
	      if (!gameObjectList.isEmpty()) {
	        GameObject currentObject = gameObjectList.get(highlighted);
	        currentObject.restoreAutonomousBehavior();
	      }

	      // Move to next object
	      highlighted = highlighted + 1;
	      if (highlighted == gameObjectList.size()) {
	        highlighted = 0;
	      }

	      // Repaint to update the highlight visual
	      repaint();
	    }
	  }

}
