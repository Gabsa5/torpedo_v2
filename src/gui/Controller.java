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
		this.singlePlayer = new SinglePlayer(stage);
		this.gameType = gameType;

		if(gameType.equals(GameType.CONTINUE)) {
			
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
			this.singlePlayerBoard.prettyPrint();

			System.out.println("Other Player Board:");
			this.otherBoard.prettyPrint();
			

			
			this.singlePlayer.setEnemyBoard(this.otherBoard);
			this.otherPlayer.setMyBoard(this.otherBoard);
			
			this.singlePlayer.shipDrawContinue();
			
			this.singlePlayer.setMyBoard(this.singlePlayerBoard);
			this.otherPlayer.setEnemyBoard(this.singlePlayerBoard);
			
			this.singlePlayer.setEnemyLife(this.singlePlayer.lifeLeft(this.singlePlayer.getEnemyBoard()));
			this.otherPlayer.setEnemyLife(this.singlePlayer.lifeLeft(this.singlePlayer.getMyBoard()));
			
			this.singlePlayer.changeTextVisibility();

			Thread gameThread = new Thread(this::playGame);
			gameThread.start();
		} else {
			switch (gameType) {
				case SINGLEPLAYER:
					otherPlayer = new AIPlayer();
					break;
				case CLIENT:
					String ipAddress = multiplayerSettingsView.getIpAddress();
					otherPlayer = new MultiPlayerClient(ipAddress);
					break;
				case SERVER:
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
		this.otherPlayer.setEnemyBoard(this.singlePlayerBoard);

		// TODO: show 'Waiting for other' text
		waitWhileShipPlacement(this.otherPlayer);
		// TODO: hide 'Waiting for other' text
		this.otherBoard = otherPlayer.getMyBoard();
		this.singlePlayer.setEnemyBoard(this.otherBoard);


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
				this.singlePlayer.setMyBoard(this.singlePlayerBoard);
				//this.otherPlayer.setEnemyBoard(this.singlePlayerBoard);
				this.singlePlayer.changeText();
				if (this.singlePlayerBoard.isShotDone()) {
					this.otherPlayer.updateEnemyLife();
				}
				if (otherPlayer.getEnemyLife() == 0) {
					this.singlePlayer.createEndButton(false);
					break;
				}


				// Single player turn
				System.out.println("Waiting for player to shoot");
				this.singlePlayer.shoot(this.otherBoard);
				waitWhileShoot(this.singlePlayer);
				this.otherBoard = this.singlePlayer.getEnemyBoard();
				this.singlePlayer.setEnemyBoard(this.otherBoard);
				this.otherPlayer.setMyBoard(this.otherBoard);
				this.singlePlayer.changeText();
				if (this.otherBoard.isShotDone()) {
					this.singlePlayer.updateEnemyLife();
				}
				if (singlePlayer.getEnemyLife() == 0) {
					this.singlePlayer.createEndButton(true);
				}


			} else {
				// Single player turn
				System.out.println("Waiting for player to shoot");
				this.singlePlayer.shoot(this.otherBoard);
				waitWhileShoot(this.singlePlayer);
				this.otherBoard = this.singlePlayer.getEnemyBoard();
				this.singlePlayer.setEnemyBoard(this.otherBoard);
				this.otherPlayer.setMyBoard(this.otherBoard);
				this.singlePlayer.changeText();
				if (this.otherBoard.isShotDone()) {
					this.singlePlayer.updateEnemyLife();
				}
				if (singlePlayer.getEnemyLife() == 0) {
					this.singlePlayer.createEndButton(true);
				}

				// Other player turn
				System.out.println("Waiting for other player to shoot");
				this.otherPlayer.shoot(this.singlePlayerBoard);
				waitWhileShoot(this.otherPlayer);
				this.singlePlayerBoard = this.otherPlayer.getEnemyBoard();
				this.singlePlayer.setMyBoard(this.singlePlayerBoard);
				this.otherPlayer.setEnemyBoard(this.singlePlayerBoard);
				this.singlePlayer.changeText();
				if (this.singlePlayerBoard.isShotDone()) {
					this.otherPlayer.updateEnemyLife();
				}
				if (otherPlayer.getEnemyLife() == 0) {
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
