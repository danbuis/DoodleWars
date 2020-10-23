import GameEngine.Engine;
import GameEngine.GameComponent;
import engine.DoodleWarsGame;


public class DoodleWars {

	public static void main(String[] args) {
		try {
			GameComponent gameLogic = new DoodleWarsGame();
			Engine gameEng = new Engine("GAME",
					900, 480, true, gameLogic);
			gameEng.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}

}