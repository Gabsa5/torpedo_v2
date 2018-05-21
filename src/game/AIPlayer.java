package game;

import java.io.IOException;

import AI.BattleshipAI;
import AI.NeuralNetwork;

public class AIPlayer implements Player {

	private NeuralNetwork neuralNetwork;
	private int enemyLife = 19;

	public void setEnemyLife(int life) {
		this.enemyLife = life;
	}

	public void setEnemyLife() {
		this.enemyLife = enemyLife - 1;
	}

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
	}

	@Override
	public void shoot(Board board) {
		int cellIndex = neuralNetwork.nextStep(board);
		board.shootShip(cellIndex);
	}

	@Override
	public void updateMyBoard(Board board) {

	}

	@Override
	public void updateEnemyBoard(Board board) {

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
