package game;

import network.NetworkClient;
/**
 * MultiPlayerClient is also and extended player who will be the client in the multiplayer game.
 * @author Gabsa5
 *
 */
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

	/**
	 * The shoot method changes the readyWithShoot flag to false.
	 */
	@Override
	public void shoot(Board board) {
		this.readyWithShoot = false;
	}

	/**
	 * The updateMyBoardAfterShoot is a method to update the client's board and sends it to the other player.
	 */
	@Override
	public void updateMyBoardAfterShoot(Board board) {
		this.networkClient.sendBoard(board);
	}

	/**
	 * The updateEnemyBoardAfterShoot updates the client's board and sends it to the other player.
	 */
	@Override
	public void updateEnemyBoardAfterShoot(Board board) {
		this.networkClient.sendBoard(board);
	}

	/**
	 * The MultiPlayerStage enum gives the cases of the happenings when the MultiPlayerClient receives a board.
	 * In SHIP_PLACEMENT he is becomes ready with ship placement, set his board and change the enum type.
	 * In AFTER_NETWORK_SHOT he set the enemyboard with the received one and he change the enum type.
	 * In SHOOT he becomes ready with shooting, set his board and change the enum type.
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
