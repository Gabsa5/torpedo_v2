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

	/**
	 * The shoot method send the board on network and turns the readyWithShoot parameter to false.
	 */
	@Override
	public void shoot(Board board) {
		this.readyWithShoot = false;
		this.networkServer.sendBoard(board);
	}

	/**
	 * The updateMyBoardAfterShoot sets the board with the received one and send it on the network.
	 */
	@Override
	public void updateMyBoardAfterShoot(Board board) {
		this.myBoard = board;
		this.networkServer.sendBoard(board);
	}

	/**
	 * The updateEnemyBoardAfterShoot receives and sets the enemy's board and send it on network.
	 */
	@Override
	public void updateEnemyBoardAfterShoot(Board board) {
		this.enemyBoard = board;
		this.networkServer.sendBoard(board);
	}

	@Override
	public void onGameOver() {
		this.networkServer.disconnect();
	}

	/**
	 * The MultiPlayerStage enum gives the cases of the happenings when the MultiPlayerServer receives a board.
	 * In SHIP_PLACEMENT he is becomes ready with ship placement, set his board and change the enum type.
	 * In AFTER_NETWORK_SHOT he set the enemyboard with the received one and he change the enum type.
	 * @param board
	 */
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
