package game;

public interface Player {
	void placeShips(Board board);
	void shoot(Board board);
	void updateMyBoard(Board board);
	void updateEnemyBoard(Board board);
	boolean isReadyWithPlaceShips();
	boolean isReadyWithShoot();
}