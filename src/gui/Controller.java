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

		singlePlayer.placeShips(game.getSinglePlayerBoard());
		otherPlayer.placeShips(game.getOtherBoard());

		Thread gameThread = new Thread(() -> {

			waitWhileShipPlacement();

			System.out.println("Single Player Board:");
			this.game.getSinglePlayerBoard().prettyPrint();

			System.out.println("Other Player Board:");
			this.game.getOtherBoard().prettyPrint();

			// Start shooting part
			while(true) {
				// Single player turn
				this.singlePlayer.shoot(this.game.getOtherBoard());
				waitWhileShoot(this.singlePlayer);
				this.singlePlayer.updateEnemyBoard(this.game.getOtherBoard());
				this.otherPlayer.updateMyBoard(this.game.getOtherBoard());
				// TODO check if game is over

				// Other player turn
				this.otherPlayer.shoot(this.game.getSinglePlayerBoard());
				waitWhileShoot(this.otherPlayer);
				this.singlePlayer.updateMyBoard(this.game.getSinglePlayerBoard());
				this.otherPlayer.updateEnemyBoard(this.game.getSinglePlayerBoard());
				// TODO check if game is over

				System.out.println("Single Player Board:");
				this.game.getSinglePlayerBoard().prettyPrint();

				System.out.println("Other Player Board:");
				this.game.getOtherBoard().prettyPrint();
			}


		});

		gameThread.start();
	}

	private void waitWhileShipPlacement() {
		// TODO check player timeout
		while(true) {
			if (singlePlayer.isReadyWithPlaceShips() && otherPlayer.isReadyWithPlaceShips()) {
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void waitWhileShoot(Player player) {
		// TODO check player timeout
		while(true) {
			if (player.isReadyWithShoot()) {
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
