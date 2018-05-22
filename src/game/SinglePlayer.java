package game;

import java.util.ArrayList;

import gui.GameView;
import javafx.stage.Stage;

public class SinglePlayer extends Player {

	private GameView gameView;

	public SinglePlayer(Stage stage, boolean fromSave, boolean isMultiplayer) {

		gameView = new GameView(stage, this, isMultiplayer);
		gameView.build();

		if (fromSave) {
			this.readyWithShipPlacement = true;
		}
	}

	@Override
	public void placeShips(Board board) {
		this.myBoard = board;
		gameView.createUnPlacedShip(2);
		gameView.createUnPlacedShip(2);
		gameView.createUnPlacedShip(3);
		gameView.createUnPlacedShip(3);
		gameView.createUnPlacedShip(4);
		gameView.createUnPlacedShip(5);
	}

	@Override
	public void shoot(Board board) {
		this.enemyBoard = board;
		this.readyWithShoot = false;
		this.gameView.shoot();
		this.gameView.updateEnemyShips(board);
	}

	@Override
	public void updateMyBoardAfterShoot(Board board) {
		this.gameView.redrawMyBoard(board);
		this.gameView.updateMyShips(board);
		this.myBoard = board;
	}

	@Override
	public void updateEnemyBoardAfterShoot(Board board) {
		this.gameView.redrawEnemyBoard(board);
		this.gameView.updateEnemyShips(board);
		this.enemyBoard = board;
	}

	@Override
	public void onGameOver() {
		// Do nothing
	}

	public void afterShipSelection(ArrayList<Ship> ships) {
		for (Ship ship: ships) {
			myBoard.addShip(ship.getShipStartIndex(), ship.getShipEndIndex());
		}
		this.readyWithShipPlacement = true;
		this.gameView.updateMyShips(myBoard);
	}

	public void afterShoot(int cellIndex) {
		if(this.enemyBoard.shootShip(cellIndex))
			this.readyWithShoot = true;
		else {
			this.readyWithShoot = false;
		}
	}

	public void createEndButton(boolean isWin) {
		this.gameView.createEnd(isWin);
	}

	public void shipDrawContinue() {
		this.readyWithShipPlacement = true;
		this.gameView.shipDrawContinue();
	}
	
	public void changeTextVisibility() {
		this.gameView.changeMyTurnTextVisibility();
	}
	
	public void changeText() {
		this.gameView.changeMyTurn();
	}
}
