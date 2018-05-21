package game;

import java.io.IOException;

import AI.BattleshipAI;
import AI.NeuralNetwork;

public class AIPlayer implements Player {

	private NeuralNetwork neuralNetwork;
	private int enemyLife = 19;

	private Board myBoard;
	private Board enemyBoard;

	public AIPlayer() {
		try {
			this.neuralNetwork = new NeuralNetwork();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setEnemyLife(int life) {
		this.enemyLife = life;
	}

	@Override
	public Board getMyBoard() {
		return this.myBoard;
	}

	@Override
	public Board getEnemyBoard() {
		return this.enemyBoard;
	}

	public void updateEnemyLife() {
		this.enemyLife = enemyLife - 1;
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
	}

	@Override
	public void shoot(Board board) {
		int cellIndex = neuralNetwork.nextStep(board);
		board.shootShip(cellIndex);
		this.enemyBoard = board;
	}

	@Override
	public void setMyBoard(Board board) {
		this.myBoard = board;
	}

	@Override
	public void setEnemyBoard(Board board) {
		this.enemyBoard = board;
	}


	@Override
	public boolean isReadyWithPlaceShips() {
		return true;
	}

	@Override
	public boolean isReadyWithShoot() {
		return true;
	}	
	
	@Override
	public int getEnemyLife() {
		return this.enemyLife;
	}

	@Override
	public void createEndButton(boolean isWin) {
		
	}
	
}
