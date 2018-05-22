package game;

import java.io.Serializable;

/**
 * This class determines the building unit of the Ship class.
 * The ShipPart is written according to JavaBeans standard
 * 
 * @author Peter Lindner
 * @version 1.0
 */

public class ShipPart implements Serializable {
	
	//Properties of ShipPart
	/**
	 * inUnshooted: Property of the ShipPart, boolean value, true if the ShipPart is not shooted and false if it is already shooted
	 */
	private boolean isUnshooted;
	/**
	 * shipPartIndex: Property of the ShipPart, integer value, defines the index of the ShipPart on the Board
	 */
	private int shipPartIndex;
	/**
	 * ship: Property of the ShipPart, Ship object, connects the ShipPart with the ship where it belongs
	 */
	private Ship ship;
	
	/**
	 * Empty constructor of the ShipPart 
	 */
	public ShipPart() {
		
	}
	
	/**
	 * Constructor of the ShipPart with a Ship and an integer parameter
	 * @param ship: determines which Ship contains it
	 * @param shipPartIndex: defines the cell index where it belongs to
	 */
	
	public ShipPart(Ship ship, int shipPartIndex){
		this.ship = ship;
		this.shipPartIndex=shipPartIndex;
		this.isUnshooted=true;
	}
	/**
	 * Getter for shipPartIndex
	 */
	public int getShipPartIndex() {
		return shipPartIndex;
	}
	
	/**
	 * Setter for shipPartIndex
	 */
	public void setShipPartIndex(int shipPartIndex) {
		this.shipPartIndex = shipPartIndex;
	}


	/**
	 * Getter for isUnshooted
	 */
	public boolean getIsUnshooted() {
		return isUnshooted;
	}

	/**
	 * Setter for isUnshooted
	 */
	public void setIsUnshooted(boolean isUnshooted) {
		this.isUnshooted = isUnshooted;
	}

	/**
	 * Getter for ship
	 */
	public Ship getShip() {
		return ship;
	}


	public void setUnshooted(boolean isUnshooted) {
		this.isUnshooted = isUnshooted;
	}

	/**
	 * Setter for ship
	 */
	public void setShip(Ship ship) {
		this.ship = ship;
	}
}
