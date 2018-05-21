package game;

import network.NetworkClient;

public class MultiPlayerClient extends Player {
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
	public void updateMyBoardAfterShoot(Board board) {
		this.networkClient.sendBoard(board);
	}

	@Override
	public void updateEnemyBoardAfterShoot(Board board) {
		this.networkClient.sendBoard(board);
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
				this.stage = MultiPlayerStage.SHOOT;
				break;
			case SHOOT:
				this.readyWithShoot = true;
				this.myBoard = board;
				this.stage = MultiPlayerStage.AFTER_NETWORK_SHOT;
				break;
		}
	}
}
