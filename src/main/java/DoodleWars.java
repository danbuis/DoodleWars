import BBDGameLibrary.GameEngine.GameComponent;
import BBDGameLibrary.GameEngine.Engine;
import BBDGameLibrary.GameEngine.GameComponent;
import BBDGameLibrary.OpenGL.Window;
import engine.DoodleWarsGame;


public class DoodleWars {

	public static void main(String[] args) {
		try {
			GameComponent gameLogic = new DoodleWarsGame();
			Engine gameEng = new Engine("GAME",
					1800, 980, true, new Window.WindowOptions(), gameLogic);
			gameEng.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}

}