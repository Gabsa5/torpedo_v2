package game;

public class MultiPlayer implements Player{

	private boolean readyWithShipPlacement = false;
	private boolean readyWithShoot = false;
	private int enemyLife = 19;

	@Override
	public void placeShips(Board board) {
		// TODO place multi
	}

	@Override
	public void shoot(Board board) {
		// TODO shoot multi
	}

	@Override
	public void updateMyBoard(Board board) {
		// TODO
	}

	@Override
	public void updateEnemyBoard(Board board) {
		// TODO
	}


	@Override
	public boolean isReadyWithPlaceShips() {
		return this.readyWithShipPlacement;
	}

	@Override
	public boolean isReadyWithShoot() {
		return this.readyWithShoot;
	}

	@Override
	public void setEnemyLife() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getEnemyLife() {
		return this.enemyLife;
		
	}
	
	@Override
	public void createEndButton(boolean isWin) {
		
	}

	@Override
	public void setEnemyLife(int life) {
		// TODO Auto-generated method stub
		
	}
}
