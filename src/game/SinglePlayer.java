package game;

import java.util.ArrayList;

import gui.GameView;
import javafx.stage.Stage;

public class SinglePlayer implements Player {

	private Board myBoard;
	private Board enemyBoard;
	private GameView gameView;
	private boolean readyWithShipPlacement = false;
	private boolean readyWithShoot = false;

	public SinglePlayer(Stage stage) {

		gameView = new GameView(stage, this);
		gameView.build();
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

 		// TODO
	}

	@Override
	public void updateMyBoard(Board board) {
		this.gameView.redrawMyBoard(board);
	}

	@Override
	public void updateEnemyBoard(Board board) {
		this.gameView.redrawEnemyBoard(board);
	}

	@Override
	public boolean isReadyWithPlaceShips() {
		return this.readyWithShipPlacement;
	}

	@Override
	public boolean isReadyWithShoot() {
		return this.readyWithShoot;
	}

	public void afterShipSelection(ArrayList<Ship> ships) {
		for (Ship ship: ships) {
			myBoard.addShip(ship.getShipStartIndex(), ship.getShipEndIndex());
		}
		this.readyWithShipPlacement = true;
	}

	public void afterShoot(int cellIndex) {
		this.enemyBoard.shootShip(cellIndex);
		this.readyWithShoot = true;
	}
}
