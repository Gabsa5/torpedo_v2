package game;

import java.io.Serializable;

/**
 * This class determines the building unit of the Board class.
 * The BoardCell is written according to JavaBeans standard
 * 
 * @author Peter Lindner
 * @version 1.0
 */

public class BoardCell implements Serializable {
	
	//Properties of BoardCell
	/**
	 * cellIndex: It is an integer value, determines the index of BoardCell on the Board
	 */
	private int cellIndex;
	/**
	 * isShootedCell: It is a boolean value, determines wether this cell has been already shooted or has not.
	 */
	private boolean isShootedCell;
	/**
	 * shipPart: It is a ShipPart object, it connects the cell with a shipPart if there is a Ship in that cell
	 */
	private ShipPart shipPart;
	
	/**
	 * Empty constructor of the BoardCell
	 */
	public BoardCell() {
		
	}

	/**
	 * Constructor of the BoardCell with an integer value
	 */
	public BoardCell(int cellIndex){
		this.cellIndex=cellIndex;
		this.setIsShootedCell(false);	
	}
	
	/**
	 * Getter for cellIndex
	 */
	public int getCellIndex() {
		return cellIndex;
	}
	
	/**
	 * Setter for cellIndex
	 */

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}



	/**
	 * Gives back a boolean value which determines if there is a shipPart in this cell. It is true if there is no shipPart and false otherwise
	 */
	public boolean getIsEmptyCell() {
		return this.shipPart == null;
	}
	
	/**
	 * Getter for isShootedCell
	 */
	public boolean getIsShootedCell() {
		return isShootedCell;
	}

	/**
	 * Setter for isShootedCell
	 */
	public void setIsShootedCell(boolean isShootedCell) {
		this.isShootedCell = isShootedCell;
	}

	/**
	 * Getter for shipPart
	 */
	public ShipPart getShipPart() {
		return shipPart;
	}

	/**
	 * Setter for shipPart
	 */
	public void setShipPart(ShipPart shipPart) {
		this.shipPart = shipPart;
	}

	/**
	 * Setter for isShootedCell
	 */
	public void setShootedCell(boolean isShootedCell) {
		this.isShootedCell = isShootedCell;
	}
}
