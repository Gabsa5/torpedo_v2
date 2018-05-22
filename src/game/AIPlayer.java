package game;

import java.io.IOException;

import AI.BattleshipAI;
import AI.NeuralNetwork;

public class AIPlayer extends Player {

	private NeuralNetwork neuralNetwork;

	public AIPlayer() {
		try {
			this.neuralNetwork = new NeuralNetwork();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void placeShips(Board board) {
		BattleshipAI battleshipAI = new BattleshipAI(board);
		battleshipAI.addRandomShip(2);
		battleshipAI.addRandomShip(2);
		battleshipAI.addRandomShip(3);
		battleshipAI.addRandomShip(3);
		battleshipAI.addRandomShip(4);
		battleshipAI.addRandomShip(5);
		this.myBoard = board;
		this.readyWithShipPlacement = true;
	}

	@Override
	public void shoot(Board board) {
		int cellIndex = neuralNetwork.nextStep(board);
		board.shootShip(cellIndex);
		this.enemyBoard = board;
		this.readyWithShoot = true;
	}

	@Override
	public void updateMyBoardAfterShoot(Board board) {
		this.myBoard = board;
	}

	@Override
	public void updateEnemyBoardAfterShoot(Board board) {
		this.enemyBoard = board;
	}

	@Override
	public void onGameOver() {
		// Do nothing
	}

	@Override
	public boolean isReadyWithPlaceShips() {
		return true;
	}
}
