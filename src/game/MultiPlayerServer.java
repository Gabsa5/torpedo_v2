package game;

import network.NetworkServer;

public class MultiPlayerServer extends Player {
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
	public void updateMyBoardAfterShoot(Board board) {
		this.myBoard = board;
		this.networkServer.sendBoard(board);
	}

	@Override
	public void updateEnemyBoardAfterShoot(Board board) {
		this.enemyBoard = board;
		this.networkServer.sendBoard(board);
	}

	public void onBoardReceive(Board board) {
		switch (this.stage) {
			case SHIP_PLACEMENT:
				this.readyWithShipPlacement = true;
				this.myBoard = board;
				this.stage = MultiPlayerStage.AFTER_NETWORK_SHOT;
				break;
			case AFTER_NETWORK_SHOT:
				this.enemyBoard = board;
				this.readyWithShoot = true;
				break;
		}
	}
}
