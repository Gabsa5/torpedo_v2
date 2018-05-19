package game;

public interface Player {
	void placeShips(Board board);
	void shoot(Board board);
	boolean isReadyWithPlaceShips();
}