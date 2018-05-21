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
	private int enemyLife;

	public SinglePlayer(Stage stage) {

		gameView = new GameView(stage, this);
		gameView.build();
		this.enemyLife = 19;
	}

	@Override
	public void placeShips(Board board) {
		this.myBoard = board;
//		gameView.createUnPlacedShip(2);
//		gameView.createUnPlacedShip(2);
//		gameView.createUnPlacedShip(3);
//		gameView.createUnPlacedShip(3);
//		gameView.createUnPlacedShip(4);
		gameView.createUnPlacedShip(5);
	}

	@Override
	public void shoot(Board board) {
		this.enemyBoard = board;
		this.readyWithShoot = false;
		this.gameView.shoot();
	}

	@Override
	public void setMyBoard(Board board) {
		this.gameView.redrawMyBoard(board);
		this.myBoard = board;
	}

	@Override
	public void setEnemyBoard(Board board) {
		this.gameView.redrawEnemyBoard(board);
		this.enemyBoard = board;
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
		if(this.enemyBoard.shootShip(cellIndex) && this.enemyLife != 0)
			this.readyWithShoot = true;
		else {
			this.readyWithShoot = false;
		}
	}

	public void updateEnemyLife() {
		this.enemyLife = enemyLife - 1;
		
	}
	
	public void setEnemyLife(int life) {
		this.enemyLife = life;
	}

	@Override
	public int getEnemyLife() {
		return this.enemyLife;
		
	}

	@Override
	public void createEndButton(boolean isWin) {
		this.gameView.createEnd(isWin);
		
	}

	@Override
	public Board getMyBoard() {
		return myBoard;
	}

	@Override
	public Board getEnemyBoard() {
		return enemyBoard;
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
	
	public int lifeLeft(Board board)
	{
		int life=0;
		for(int index=0; index<100; index++)
		{
			if(!board.getCells().get(index).getIsEmptyCell() && 
					!board.getCells().get(index).getIsShootedCell())
			{
				life++;
			}
			
		}
		return life;
	}
}
