package game;

public interface Player {
	void placeShips(Board board);
	void shoot(Board board);
	void updateMyBoardAfterShoot(Board board);
	void updateEnemyBoardAfterShoot(Board board);
	boolean isReadyWithPlaceShips();
	boolean isReadyWithShoot();
	int getEnemyLife();
	void createEndButton(boolean isWin);
	void updateEnemyLife();
	void setEnemyLife(int life);
	Board getMyBoard();
	Board getEnemyBoard();

}