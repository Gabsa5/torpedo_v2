package AI;
import java.util.Random;

public class BattleshipAI {
	private int rowSize;
	private int tableLength;
	private int[] board;
	private Random rand = new Random();

	public BattleshipAI(int rowSize) {
		this.rowSize = rowSize;
		this.tableLength = rowSize*rowSize - 1;
		this.resetBoard();
	}

	public void resetBoard() {
		this.board = new int[rowSize*rowSize];
	}

	public void randomShips(int shipSize){
		boolean direct = rand.nextBoolean();
		int tryNumber = 0;
		while(true) {
			int resBefore = this.nonZeroNum();
			int[] tempBoard = this.board.clone();
			if (direct){
				int startPoint = (rand.nextInt(this.rowSize-1) * rowSize + rand.nextInt(this.rowSize-shipSize));
				for(int j = 0; j < shipSize; j++) {
					tempBoard[startPoint+j] = 1;
				}
			}
			else {
				int startPoint = (rand.nextInt(this.tableLength-(shipSize-1)*this.rowSize));
				for(int j = 0; j < shipSize; j++) {
					tempBoard[startPoint+j*rowSize] = 1;
				}
			}
			if ((this.nonZeroNum(tempBoard)-resBefore) == shipSize) {
				this.board = tempBoard;
				break;
			}

			tryNumber++;
			if(tryNumber > 100) {
				break;
			}
		}

	}

	private int nonZeroNum(int[] table) {
		int nonzeroNum = 0;
		for ( int element : table) {
			if (element != 0) {
				nonzeroNum ++;
			}
		}
		return nonzeroNum;
	}

	private int nonZeroNum() {
		int nonzeroNum = 0;
		for ( int element : this.board) {
			if (element != 0) {
				nonzeroNum ++;
			}
		}
		return nonzeroNum;
	}

	public void prettyPrint() {
		for (int j = 0; j < rowSize; j++) {
			for (int i = 1; i <= rowSize; i++) {
				System.out.print(board[j*rowSize+i-1] + " ");
			}
			System.out.println();
		}
	}

}


