package game;

import network.NetworkClient;

public class MultiPlayerClient implements Player {

	private Board myBoard;
	private Board enemyBoard;

	private boolean readyWithShipPlacement = false;
	private boolean readyWithShoot = false;
	private int enemyLife = 19;
	private NetworkClient networkClient;
	private MultiPlayerStage stage;

	public MultiPlayerClient(String serverIp) {
		this.networkClient = new NetworkClient(this, serverIp);
		this.stage = MultiPlayerStage.SHIP_PLACEMENT;
		networkClient.connect();
	}

	@Override
	public void placeShips(Board board) {
		// Do nothing
	}

	@Override
	public void shoot(Board board) {
		this.readyWithShoot = false;
	}

	@Override
	public void setMyBoard(Board board) {
		this.networkClient.sendBoard(board);
	}

	@Override
	public void setEnemyBoard(Board board) {
		this.enemyBoard = board;
		networkClient.sendBoard(board);
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
	public void updateEnemyLife() {
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

	@Override
	public Board getMyBoard() {
		return this.myBoard;
	}

	@Override
	public Board getEnemyBoard() {
		return this.enemyBoard;
	}

	public void onBoardReceive(Board board) {
		switch (this.stage) {
			case SHIP_PLACEMENT:
				this.readyWithShipPlacement = true;
				this.myBoard = board;
				this.stage = MultiPlayerStage.SHOOT;
				break;
			case SHOOT:
				this.enemyBoard = board;
				this.readyWithShoot = true;
				break;
		}
	}
}
