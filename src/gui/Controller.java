package gui;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

import game.Game;
import game.Ship;
import gui.GameType;
import gui.GameView;

public class Controller {
	
	private Stage s;
	private GameType gameType;
	private GameView gameView;
	private Game game;
	
	
	public Controller(Stage s) {
		this.s = s;
		gameType = GameType.SINGLEPLAYER;

		
	}
	
	public void start() {
		gameView = new GameView(s, this);
		try {
			gameView.build();
			gameView.createUnPlacedShip(2);
			gameView.createUnPlacedShip(2);
			gameView.createUnPlacedShip(3);
			gameView.createUnPlacedShip(3);
			gameView.createUnPlacedShip(4);
			gameView.createUnPlacedShip(5);
		}catch (Exception e) {
			e.printStackTrace();
		}
		switch(gameType) {
		case SINGLEPLAYER:
			
		}
	}
	
	public void afterShipSelection(ArrayList<Ship> ships) {
		Game game = new Game(10, false);
		game.getPlayers().get(0).getPlayerBoard().getShips().addAll(ships);
	}

}
