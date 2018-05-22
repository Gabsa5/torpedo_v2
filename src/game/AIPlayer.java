package game;

import java.io.IOException;

import AI.BattleshipAI;
import AI.NeuralNetwork;
/**
 * AIPlayer class will be an extended Player interface to be the other player in the single player mode.
 * @author Gabsa5
 *
 */
public class AIPlayer extends Player {

	private NeuralNetwork neuralNetwork;

	public AIPlayer() {
		try {
			this.neuralNetwork = new NeuralNetwork();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The placeShip method will put the randomized ship to the player's board.
	 */
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

	/**
	 * The shoot method will the player's board to shoot. We change readyWhitShoot parameter for true for the other player's turn.
	 */
	@Override
	public void shoot(Board board) {
		int cellIndex = neuralNetwork.nextStep(board);
		board.shootShip(cellIndex);
		this.enemyBoard = board;
		this.readyWithShoot = true;
	}

	/**
	 * Setter for the machine's board.
	 */
	@Override
	public void updateMyBoardAfterShoot(Board board) {
		this.myBoard = board;
	}

	/**
	 * Setter for the the machine's enemy board which will be the person's who is playing.
	 */
	@Override
	public void updateEnemyBoardAfterShoot(Board board) {
		this.enemyBoard = board;
	}

	/**
	 * We controller will call this method if sure thats AIPlayer is ready with placing the ships.
	 */
	@Override
	public void onGameOver() {
		// Do nothing
	}

	@Override
	public boolean isReadyWithPlaceShips() {
		return true;
	}
}
