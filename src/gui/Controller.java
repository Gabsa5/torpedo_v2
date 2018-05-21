package gui;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import game.AIPlayer;
import game.MultiPlayerClient;
import game.MultiPlayerServer;
import game.Player;
import game.SinglePlayer;
import game.Board;
import javafx.stage.Stage;

public class Controller {
	private MultiplayerSettingsView multiplayerSettingsView;

	private SinglePlayer singlePlayer;
	private Player otherPlayer;

	private Board singlePlayerBoard;
	private Board otherBoard;

	private GameType gameType;

	public void networkSettings(Stage stage) {
		// Multiplayer Client
		multiplayerSettingsView = new MultiplayerSettingsView(stage, this);
		multiplayerSettingsView.build();
	}

	public void start(Stage stage, GameType gameType) {

		this.singlePlayerBoard = new Board(10);
		this.otherBoard = new Board(10);
		this.gameType = gameType;

		if(gameType.equals(GameType.CONTINUE)) {
			this.singlePlayer = new SinglePlayer(stage, true, false);
			
			try {
				FileInputStream fis = new FileInputStream(new File("./singleBoard.xml"));
				XMLDecoder decoder = new XMLDecoder(fis);
				
				
				FileInputStream fis2= new FileInputStream(new File("./enemyBoard.xml"));
				XMLDecoder decoder2 = new XMLDecoder(fis2);
				
				ArrayList<Board> boards = new ArrayList<Board>();
				boards.add(((Board)decoder.readObject()));
				boards.add(((Board)decoder2.readObject()));

				this.singlePlayerBoard = boards.get(0);
				this.otherBoard = boards.get(1);

				this.otherPlayer = new AIPlayer();
				
				this.singlePlayer.updateMyBoardAfterShoot(boards.get(0));
				this.singlePlayer.updateEnemyBoardAfterShoot(boards.get(1));
				
				
				decoder.close();
				fis.close();
				decoder2.close();
				fis2.close();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
			System.out.println("Single Player Board:");
			this.singlePlayerBoard.prettyPrint();

			System.out.println("Other Player Board:");
			this.otherBoard.prettyPrint();
			

			
			this.singlePlayer.updateEnemyBoardAfterShoot(this.otherBoard);
			this.otherPlayer.updateMyBoardAfterShoot(this.otherBoard);
			
			this.singlePlayer.shipDrawContinue();
			
			this.singlePlayer.updateMyBoardAfterShoot(this.singlePlayerBoard);
			this.otherPlayer.updateEnemyBoardAfterShoot(this.singlePlayerBoard);
			
			this.singlePlayer.changeTextVisibility();

			Thread gameThread = new Thread(this::playGame);
			gameThread.start();
		} else {

			switch (gameType) {
				case SINGLEPLAYER:
					this.singlePlayer = new SinglePlayer(stage, false, false);
					otherPlayer = new AIPlayer();
					break;
				case CLIENT:
					this.singlePlayer = new SinglePlayer(stage, false, true);
					String ipAddress = multiplayerSettingsView.getIpAddress();
					otherPlayer = new MultiPlayerClient(ipAddress);
					break;
				case SERVER:
					this.singlePlayer = new SinglePlayer(stage, false, true);
					otherPlayer = new MultiPlayerServer();
					break;
			}

			singlePlayer.placeShips(this.singlePlayerBoard);
			otherPlayer.placeShips(this.otherBoard);

			Thread gameThread = new Thread(this::playGame);
			gameThread.start();
		}
	}

	private void playGame() {

		waitWhileShipPlacement(this.singlePlayer);
		this.singlePlayerBoard = singlePlayer.getMyBoard();
		this.otherPlayer.updateEnemyBoardAfterShoot(this.singlePlayerBoard);

		// TODO: show 'Waiting for other' text
		waitWhileShipPlacement(this.otherPlayer);
		// TODO: hide 'Waiting for other' text
		this.otherBoard = otherPlayer.getMyBoard();
		this.singlePlayer.updateEnemyBoardAfterShoot(this.otherBoard);


		System.out.println("Single Player Board:");
		this.singlePlayerBoard.prettyPrint();

		System.out.println("Other Player Board:");
		this.otherBoard.prettyPrint();

		this.singlePlayer.changeTextVisibility();

		// Start shooting part
		while (true) {
			if (this.gameType.equals(GameType.CLIENT)) {
				this.singlePlayer.changeText();

				// Server shoots
				System.out.println("Waiting for other player to shoot");
				this.otherPlayer.shoot(this.singlePlayerBoard);
				waitWhileShoot(this.otherPlayer);
				this.singlePlayerBoard = this.otherPlayer.getEnemyBoard();
				this.singlePlayer.updateMyBoardAfterShoot(this.singlePlayerBoard);
				this.singlePlayer.changeText();
				if (singlePlayer.getLife() == 0) {
					this.singlePlayer.createEndButton(false);
					break;
				}

				// Single player turn
				System.out.println("Waiting for player to shoot");
				this.singlePlayer.shoot(this.otherBoard);
				waitWhileShoot(this.singlePlayer);
				this.otherBoard = this.singlePlayer.getEnemyBoard();
				this.singlePlayer.updateEnemyBoardAfterShoot(this.otherBoard);
				this.otherPlayer.updateMyBoardAfterShoot(this.otherBoard);
				this.singlePlayer.changeText();
				if (otherPlayer.getLife() == 0) {
					this.singlePlayer.createEndButton(true);
					break;
				}

			} else {
				// Single player turn
				System.out.println("Waiting for player to shoot");
				this.singlePlayer.shoot(this.otherBoard);
				waitWhileShoot(this.singlePlayer);
				this.otherBoard = this.singlePlayer.getEnemyBoard();
				this.singlePlayer.updateEnemyBoardAfterShoot(this.otherBoard);
				this.otherPlayer.updateMyBoardAfterShoot(this.otherBoard);
				this.singlePlayer.changeText();
				if (otherPlayer.getLife() == 0) {
					this.singlePlayer.createEndButton(true);
					break;
				}

				// Other player turn
				System.out.println("Waiting for other player to shoot");
				this.otherPlayer.shoot(this.singlePlayerBoard);
				waitWhileShoot(this.otherPlayer);
				this.singlePlayerBoard = this.otherPlayer.getEnemyBoard();
				this.singlePlayer.updateMyBoardAfterShoot(this.singlePlayerBoard);
//				this.otherPlayer.updateEnemyBoardAfterShoot(this.singlePlayerBoard);
				this.singlePlayer.changeText();
				if (singlePlayer.getLife() == 0) {
					this.singlePlayer.createEndButton(false);
					break;
				}
			}

			System.out.println("Single Player Board:");
			this.singlePlayerBoard.prettyPrint();

			System.out.println("Other Player Board:");
			this.otherBoard.prettyPrint();
		}
	}

	private void waitWhileShipPlacement(Player player) {
		// TODO check player timeout
		while (true) {
			if (player.isReadyWithPlaceShips()) {
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
