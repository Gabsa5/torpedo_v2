package gui;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import game.AIPlayer;
import game.Game;
import game.MultiPlayer;
import game.Player;
import game.SinglePlayer;
import game.Board;
import javafx.stage.Stage;

public class Controller {
	private Game game;
	private SinglePlayer singlePlayer;
	private Player otherPlayer;

	public void start(Stage stage, GameType gameType) {
		if(gameType.equals(GameType.CONTINUE)) {
			this.game = new Game(10);

			
			try {
				FileInputStream fis = new FileInputStream(new File("./singleBoard.xml"));
				XMLDecoder decoder = new XMLDecoder(fis);
				
				
				FileInputStream fis2= new FileInputStream(new File("./enemyBoard.xml"));
				XMLDecoder decoder2 = new XMLDecoder(fis2);
				
				ArrayList<Board> boards = new ArrayList<Board>();
				boards.add(((Board)decoder.readObject()));
				boards.add(((Board)decoder2.readObject()));
			
				
				game.setBoards(boards);
				
				this.singlePlayer = new SinglePlayer(stage);
				this.otherPlayer = new AIPlayer();
				
				this.singlePlayer.setMyBoard(boards.get(0));
				this.singlePlayer.setEnemyBoard(boards.get(1));
				
				
				decoder.close();
				fis.close();
				decoder2.close();
				fis2.close();
				
				
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
			System.out.println("Single Player Board:");
			this.game.getSinglePlayerBoard().prettyPrint();

			System.out.println("Other Player Board:");
			this.game.getOtherBoard().prettyPrint();
			

			
			this.singlePlayer.updateEnemyBoard(this.game.getOtherBoard());
			this.otherPlayer.updateMyBoard(this.game.getOtherBoard());
			
			this.singlePlayer.shipDrawContinue();
			
			this.singlePlayer.updateMyBoard(this.game.getSinglePlayerBoard());
			this.otherPlayer.updateEnemyBoard(this.game.getSinglePlayerBoard());
			
			this.singlePlayer.setEnemyLife(this.singlePlayer.lifeLeft(this.singlePlayer.getEnemyBoard()));
			this.otherPlayer.setEnemyLife(this.singlePlayer.lifeLeft(this.singlePlayer.getMyBoard()));
			
			this.singlePlayer.changeTextVisibility();
			
			Thread gameThread = new Thread(() -> {

				System.out.println("Single Player Board:");
				this.game.getSinglePlayerBoard().prettyPrint();

				System.out.println("Other Player Board:");
				this.game.getOtherBoard().prettyPrint();

				// Start shooting part
				while (true) {
					// Single player turn
					this.singlePlayer.shoot(this.game.getOtherBoard());
					waitWhileShoot(this.singlePlayer);
					this.singlePlayer.updateEnemyBoard(this.game.getOtherBoard());
					this.otherPlayer.updateMyBoard(this.game.getOtherBoard());
					if(game.getOtherBoard().isShotDone()) {
						this.singlePlayer.setEnemyLife();
					}
					if(singlePlayer.getEnemyLife() == 0) {
						this.singlePlayer.createEndButton(true);
					}else
						this.singlePlayer.changeTextVisibility();
					// TODO check if game is over

					// Other player turn
					this.otherPlayer.shoot(this.game.getSinglePlayerBoard());
					waitWhileShoot(this.otherPlayer);
					this.singlePlayer.updateMyBoard(this.game.getSinglePlayerBoard());
					this.otherPlayer.updateEnemyBoard(this.game.getSinglePlayerBoard());
					System.out.println(this.otherPlayer.getEnemyLife());
					if(game.getSinglePlayerBoard().isShotDone()) {
						this.otherPlayer.setEnemyLife();
					}
					if(otherPlayer.getEnemyLife() == 0) {
						this.singlePlayer.createEndButton(false);
					}else
						this.singlePlayer.changeTextVisibility();
					// TODO check if game is over

					System.out.println("Single Player Board:");
					this.game.getSinglePlayerBoard().prettyPrint();

					System.out.println("Other Player Board:");
					this.game.getOtherBoard().prettyPrint();
				}

			});
			
			gameThread.start();
			
			
		}
		else if (gameType.equals(GameType.CLIENT)) {
			MultiplayerSettingsView multiplayerSettingsView = new MultiplayerSettingsView(stage);
			multiplayerSettingsView.build();
		} else {
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
				
				this.singlePlayer.changeTextVisibility();
				

				// Start shooting part
				while (true) {
					// Single player turn
					this.singlePlayer.shoot(this.game.getOtherBoard());
					waitWhileShoot(this.singlePlayer);
					this.singlePlayer.updateEnemyBoard(this.game.getOtherBoard());
					this.otherPlayer.updateMyBoard(this.game.getOtherBoard());
					this.singlePlayer.changeText();
					if(game.getOtherBoard().isShotDone()) {
						this.singlePlayer.setEnemyLife();
					}
					if(singlePlayer.getEnemyLife() == 0) {
						this.singlePlayer.createEndButton(true);
					}
					// TODO check if game is over

					// Other player turn
					this.otherPlayer.shoot(this.game.getSinglePlayerBoard());
					waitWhileShoot(this.otherPlayer);
					this.singlePlayer.updateMyBoard(this.game.getSinglePlayerBoard());
					this.otherPlayer.updateEnemyBoard(this.game.getSinglePlayerBoard());
					this.singlePlayer.changeText();
					if(game.getSinglePlayerBoard().isShotDone()) {
						this.otherPlayer.setEnemyLife();
					}
					if(otherPlayer.getEnemyLife() == 0) {
						this.singlePlayer.createEndButton(false);
						break;
					}
					// TODO check if game is over

					System.out.println("Single Player Board:");
					this.game.getSinglePlayerBoard().prettyPrint();

					System.out.println("Other Player Board:");
					this.game.getOtherBoard().prettyPrint();
				}

			});
			
			gameThread.start();

		}
	}

	private void waitWhileShipPlacement() {
		// TODO check player timeout
		while (true) {
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
		while (true) {
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
