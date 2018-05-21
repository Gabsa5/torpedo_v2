package game;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.MenuView;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        MenuView menuView = new MenuView(primaryStage);
        menuView.build();

    }

	
	public static void main(String[] args) {
		launch(args);

//		Board board = new Board(10);
//
//		BattleshipAI battleshipAI = new BattleshipAI(board);
//
//		battleshipAI.addRandomShip(5);
//		battleshipAI.addRandomShip(4);
//		battleshipAI.addRandomShip(3);
//		battleshipAI.addRandomShip(3);
//		battleshipAI.addRandomShip(2);
//		battleshipAI.addRandomShip(2);
//
//		board.prettyPrint();
//
//		Player player = new AIPlayer();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
	}

}
