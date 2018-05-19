package game;

public class MultiPlayer implements Player{

	private boolean readyWithShipPlacement;

	@Override
	public void placeShips(Board board) {

	}

	@Override
	public void shoot(Board board) {

	}

	@Override
	public boolean isReadyWithPlaceShips() {
		return this.readyWithShipPlacement;
	}
}
