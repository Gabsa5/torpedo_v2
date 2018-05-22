package game;

public abstract class Player {
	Board myBoard;
	Board enemyBoard;
	boolean readyWithShipPlacement = false;
	boolean readyWithShoot = false;

	public abstract void placeShips(Board board);
	public abstract void shoot(Board board);
	public abstract void updateMyBoardAfterShoot(Board board);
	public abstract void updateEnemyBoardAfterShoot(Board board);
	public abstract void onGameOver();

	public Board getMyBoard() {
		return myBoard;
	}

	public Board getEnemyBoard() {
		return enemyBoard;
	}

	public boolean isReadyWithPlaceShips() {
		return this.readyWithShipPlacement;
	}

	public boolean isReadyWithShoot() {
		return this.readyWithShoot;
	}

	public int getLife() {
		int life = 0;
		for (Ship ship : this.myBoard.getShips()) {
			for (ShipPart part : ship.getShipParts()) {
				if (part.getIsUnshooted()) {
					life++;
				}
			}
		}
		return life;
	}
}