package game;

import AI.BattleshipAI;

public class AIPlayer implements Player {
	private boolean readyWithShipPlacement = false;


	@Override
	public void placeShips(Board board) {
		BattleshipAI battleshipAI = new BattleshipAI(board);

		battleshipAI.addRandomShip(2);
		battleshipAI.addRandomShip(2);
		battleshipAI.addRandomShip(3);
		battleshipAI.addRandomShip(3);
		battleshipAI.addRandomShip(4);
		battleshipAI.addRandomShip(5);
		readyWithShipPlacement = true;
	}

	@Override
	public void shoot(Board board) {

	}

	@Override
	public boolean isReadyWithPlaceShips() {
		return this.readyWithShipPlacement;
	}
}
