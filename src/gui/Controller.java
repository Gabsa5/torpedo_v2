package gui;

import game.AIPlayer;
import game.Game;
import game.MultiPlayer;
import game.Player;
import game.SinglePlayer;
import javafx.stage.Stage;

public class Controller {
	private Game game;
	private SinglePlayer singlePlayer;
	private Player otherPlayer;

	public void start(Stage stage, GameType gameType) {
		this.game = new Game(10);
		this.singlePlayer = new SinglePlayer(stage);
		switch (gameType) {
			case SINGLEPLAYER:
				otherPlayer = new AIPlayer();
				break;
			case SERVER:
				otherPlayer = new MultiPlayer();
				break;
			case CLIENT:
				otherPlayer = new MultiPlayer();
				break;
		}

		singlePlayer.placeShips(game.getMyBoard());
		otherPlayer.placeShips(game.getOtherBoard());

		Thread thread = new Thread(() -> {

			// Wait while players place ships
			while(true) {
				if (singlePlayer.isReadyWithPlaceShips() && otherPlayer.isReadyWithPlaceShips()) {
					break;
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println("Single Player Board:");
			this.game.getMyBoard().prettyPrint();

			System.out.println("Other Player Board:");
			this.game.getOtherBoard().prettyPrint();

			// TODO: Start shooting part
		});

		thread.start();
	}

}
