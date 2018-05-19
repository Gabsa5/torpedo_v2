package game;

import java.util.ArrayList;

public class Player{
	
	/**
	 * Name of the player.
	 */
	private String name;
	
	/**
	 * Flag to know whose turn in multiplayer mode.
	 */
	private boolean turnFlag;
	
	/**
	 * Flag to know if the player is the server.
	 */
	private boolean serverFlag;
	
	/**
	 * Array of the the player's ships.
	 */
	private Board playerBoard;
	
	private int life;
	
	/**
	 * Create a player.
	 * @param name
	 * @param serverFlag
	 */
	public Player(String name, boolean serverFlag, int boardSize) {
		
		this.name = name;
		this.serverFlag = serverFlag;
		this.life=6;
		this.playerBoard=new Board(boardSize);
		
		
		
	}
	
	/**
	 * Getter of the name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Check if the player is on turn.
	 * @return flag of the player's turn
	 */
	public boolean playerIsActive() {
		return turnFlag;
	}
	
	/**
	 * Set the players turn.
	 * @param set turnFlag true if the player is on turn
	 */
	public void setPlayerActive(boolean turn) {
		this.turnFlag = turn;
	}
	
	/**
	 * Check if the player is server.
	 * @return true if the player is server
	 */
	public boolean isServer() {
		return serverFlag;
	}
	
	public void createBoard(int size){
		this.playerBoard=new Board(size);
	}
	
	public void placeShip(int startIndex, int stopIndex){
		this.playerBoard.addShip(startIndex, stopIndex);
	}
	
	public void shoot(int index){
		
		if(this.playerBoard.getCells().get(index).getIsEmptyCell())
		{
			this.playerBoard.getCells().get(index).setIsShootedCell(true);
			System.out.println("Miss");
		}else{
			if(this.playerBoard.getCells().get(index).getIsShootedCell()){
				System.out.println("Already shooted");
			}else{
			System.out.println("Hit");
			this.playerBoard.getCells().get(index).setIsShootedCell(true);
			int shipindex=this.playerBoard.getCells().get(index).getShipIndex();
			Ship shootedShip=this.playerBoard.getShips().get(shipindex);
			shootedShip.shootShip(index);
			this.setLife(this.getLife()-1);
			}
		}
	}
	
	public Board getPlayerBoard() {
		return this.playerBoard;
	}
	
	public void setPlayerBoard(Board playerBoard) {
		this.playerBoard = playerBoard;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

}