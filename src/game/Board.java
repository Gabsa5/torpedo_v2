package game;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * This class determines the Board of the game.
 * The Board is written according to JavaBeans standard
 * 
 * @author Peter Lindner
 * @version 1.0
 */

public class Board implements Serializable {

	
	
	/**
	 *  boardSize: Property of the Board, integer value, determines the size of the Board. The Board surface is always n x n
	 *  where n is equal with the boardSize
	 */
	private int boardSize;
	/**
	 *  boardSizeSQ: Property of the Board, integer value, it is calculated from the boardSize
	 *  <p> boardSizeSQ = boardSize * boardSize
	 */
	private int boardSizeSQ;
	
	/**
	 *  cells: Property of the Board, ArrayList<BoardCell>, determines the cells of the Board.
	 *  @see BoardCell
	 */
	private ArrayList<BoardCell> cells;
	/**
	 *  ships: Property of the Board, ArrayList<Ship>, defines the ships of the Board.
	 *  @see Ship
	 */
	private ArrayList<Ship> ships = new ArrayList<>();
	/**
	 * shotSuccesful: Property of the Board, boolean value, it is true if the shoot hits a cell and false if it misses one
	 */
	private boolean shotSuccessful;
	/**
	 * Empty Constructor of the Board
	 */
	public Board() {
		
	}

	/**
	 * Contructor of the Board with an integer parameter
	 * @param boardSize: determines the size of the Board
	 */
	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.boardSizeSQ = this.boardSize * this.boardSize;
		this.shotSuccessful = false;

		this.initializeCells();

	}

	/**
	 * Initialize the cell of the boards.
	 * <p> Create a new ArrayList of BoardCell and add elements with default values.
	 */
	private void initializeCells() {
		this.cells = new ArrayList<>();
		for (int i = 0; i < this.boardSizeSQ; i++) {
			cells.add(new BoardCell(i));
		}
	}

	/**
	 * Add Ships to the Board by calling the constructor of the Ship then set the ShipParts of the Ship and connects the ShipPart with the BoardCell
	 * @see Ship
	 * @see ShipPart
	 * @see BoardCell
	 */
	
	public void addShip(int shipStartIndex, int shipEndIndex) {
		Ship actualShip = new Ship(shipStartIndex, shipEndIndex);
		ships.add(actualShip);
		for (int i = 0; i < actualShip.getShipSize(); i++) {
			ShipPart shipPart = actualShip.getShipParts().get(i);
			BoardCell actualCell = this.cells.get(shipPart.getShipPartIndex());
			actualCell.setShipPart(shipPart);
		}
	}
	
	/**
	 * Returns with a boolean value, if the index where the player shooted is already shooted then it returns with false, 
	 * else it examines wether there is a Ship in the cell and set the shotSuccesful parameter to true or false and the isUnshooted parameter is set 
	 * to false, then it returns with a true value
	 */

	public boolean shootShip(int cellIndex) {
		System.out.println("Lövés: " + cellIndex);
		BoardCell targetCell = this.cells.get(cellIndex);
		
		if(targetCell.getIsShootedCell()) {
			return false;
		}
		targetCell.setIsShootedCell(true);
		if (targetCell.getIsEmptyCell()) {
			System.out.println("Nem talált!");
			shotSuccessful = false;
			
		} else {
			System.out.println("Talált!");
			shotSuccessful = true;
			targetCell.getShipPart().setIsUnshooted(false);
		}
		return true;
	}

	/**
	 * It print out the Board to the console in a coded view
	 */
	public void prettyPrint() {
		System.out.println(this.toString());
	}

	/**
	 * It creates a String where the characters have a meaning
	 * <p> 0: If the cell is empty and not shooted //
	 * X: If the cell is not empty and shooted //
	 * U: If the cell is empty and not shooted //
	 * n: If the cell is not empty and not shooted, where n is the size of the actual Shipe in that cell 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int j = 0; j < boardSize; j++) {
			for (int i = 1; i <= boardSize; i++) {
				BoardCell cell = cells.get(j * boardSize + i - 1);
				if (cell.getIsEmptyCell() && !cell.getIsShootedCell()) {
					builder.append("0");
				} else if (!cell.getIsEmptyCell() && !cell.getIsShootedCell()) {
					builder.append(cell.getShipPart().getShip().getShipSize());
				} else if (!cell.getIsEmptyCell() && cell.getIsShootedCell()) {
					builder.append("X");
				} else {
					builder.append("U");
				}
				builder.append(" ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}


	public int getBoardSize() {
		return boardSize;
	}

	public int getBoardSizeSQ() {
		return boardSizeSQ;
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public boolean isShotDone() {
		return shotSuccessful;
	}

	public void setShotDone(boolean shotSuccessful) {
		this.shotSuccessful = shotSuccessful;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	public boolean isShotSuccessful() {
		return shotSuccessful;
	}

	public void setShotSuccessful(boolean shotSuccessful) {
		this.shotSuccessful = shotSuccessful;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public void setBoardSizeSQ(int boardSizeSQ) {
		this.boardSizeSQ = boardSizeSQ;
	}

	public void setCells(ArrayList<BoardCell> cells) {
		this.cells = cells;
	}

}
