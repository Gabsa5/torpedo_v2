package game;

import javafx.stage.Stage;
import network.NetworkServer;

public class MultiPlayerServer implements Player {

	private Board myBoard;
	private Board enemyBoard;

	private boolean readyWithShipPlacement = false;
	private boolean readyWithShoot = false;
	private int enemyLife = 19;
	private NetworkServer networkServer;
	private MultiPlayerStage stage;

	public MultiPlayerServer() {
		this.networkServer = new NetworkServer(this);
		networkServer.connect();
		stage = MultiPlayerStage.SHIP_PLACEMENT;
	}



	@Override
	public void placeShips(Board board) {
		// Do nothing
	}

	@Override
	public void shoot(Board board) {
		this.readyWithShoot = false;
		this.networkServer.sendBoard(board);
	}

	@Override
	public void setMyBoard(Board board) {
//		this.networkServer.sendBoard(board);
	}

	@Override
	public void setEnemyBoard(Board board) {
		this.networkServer.sendBoard(board);
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
