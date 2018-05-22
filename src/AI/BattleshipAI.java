package AI;

import java.util.Random;

import game.Board;
import game.Ship;

/**
 * This class contains the AI's board and makes the random ship placement. It can be used for testing the AI.
 * 
 * @author Albert Gregus
 * @version 1.0
 */
public class BattleshipAI {
	private Random rand = new Random();
	private Board board;
	private int[] boardArray;

	public BattleshipAI(Board board) {
		this.board = board;
		boardArray = new int[board.getBoardSizeSQ()];
	}

	/**
	 * This function makes a random ship placement of the ship size into the class board. 
	 * 
	 * @param shipSize Size of the ship to be placed.
	 */	
	public void addRandomShip(int shipSize) {
		boolean direct = rand.nextBoolean();
		int tryNumber = 0;
		while (true) {
			int startPoint;
			int endpoint;
			int resBefore = this.nonZeroNum();
			int[] tempBoard = this.boardArray.clone();
			if (direct) {
				startPoint = (rand.nextInt(this.board.getBoardSize() - 1) * this.board.getBoardSize() + rand.nextInt(this.board.getBoardSize() - shipSize));
				endpoint = startPoint + (shipSize - 1);
				for (int j = 0; j < shipSize; j++) {
					tempBoard[startPoint + j] = 1;
				}
			} else {
				startPoint = (rand.nextInt((this.board.getBoardSizeSQ() - 1) - (shipSize - 1) * this.board.getBoardSize()));
				endpoint = startPoint + (shipSize - 1) * this.board.getBoardSize();
				for (int j = 0; j < shipSize; j++) {
					tempBoard[startPoint + j * this.board.getBoardSize()] = 1;
				}
			}
			if ((this.nonZeroNum(tempBoard) - resBefore) == shipSize) {
				this.boardArray = tempBoard;
				board.addShip(startPoint, endpoint);
				break;
			}

			tryNumber++;
			if (tryNumber > 100) {
				System.out.println("Cannot place the ship in 100 steps");
				break;
			}
		}

	}

	/**
	 * Gives number of the nonzero cells.
	 * 
	 * @param table Input array.
	 * @return Number of the nonzero cells.
	 */
	private int nonZeroNum(int[] table) {
		int nonzeroNum = 0;
		for (int element : table) {
			if (element != 0) {
				nonzeroNum++;
			}
		}
		return nonzeroNum;
	}

	/**
	 * Gives the number of the nonzero cells of the class' board.
	 * 
	 * @return Number of nonzero cells.
	 */
	private int nonZeroNum() {
		int nonzeroNum = 0;
		for (int element : this.boardArray) {
			if (element != 0) {
				nonzeroNum++;
			}
		}
		return nonzeroNum;
	}

}


