package finalProject;

public class Main {
	public static void main(String[] args) {
		Canvas canvas = new Canvas();


		// Type A - Vertical movement only (UP, DOWN)
		canvas.addGameObject(new Type_A_GameObject(50, 50));

		// Type B - Can move in all 4 directions
		canvas.addGameObject(new Type_B_GameObject(200, 150));

		// Type C - Horizontal movement only (LEFT, RIGHT)
		canvas.addGameObject(new Type_C_GameObject(400, 100));

		// Type D - Can move in all 4 directions
		canvas.addGameObject(new Type_D_GameObject(300, 300));

		canvas.start();
	}

}
