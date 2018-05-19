package game;

import java.io.IOException;

import AI.BattleshipAI;
import AI.NeuralNetwork;

public class AIPlayer implements Player {

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
	}

	@Override
	public void shoot(Board board) {
		int cellIndex = neuralNetwork.nextStep(this.boardToAIBoard(board));
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

	private Float[] boardToAIBoard(Board board) {
		Float[] aiBoard = new Float[board.getBoardSizeSQ()];

		for(BoardCell cell: board.getCells()) {
			if (cell.getIsShootedCell() && !cell.getIsEmptyCell()) {
				aiBoard[cell.getCellIndex()] = 1f;
			} else if (!cell.getIsShootedCell()) {
				aiBoard[cell.getCellIndex()] = 0f;
			} else {
				aiBoard[cell.getCellIndex()] = -1f;
			}
		}

		return aiBoard;
	}
}
