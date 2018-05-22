package game;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class describes the logic behind a ship, defines its properties and methotds which are required for the game logic.
 * The Ship is written according to JavaBeans standard
 * 
 * @author Peter Lindner
 * @version 1.0
 */

public class Ship implements Serializable {
	
	/**
	 * shipStartIndex: It is an integer value, define the first index of the ship
	 */
	
	 
	
	
	private int shipStartIndex;
	/** shipEndIndex: It is an integer value, define the last index of the ship
	 */ 
	private int	shipEndIndex;
	/** shipSizeDiff: It is an integer value, it is calculated from the shipStartIndex and the shipEndIndex
	 * <p> shipSizeDiff = shipEndIndex - shipStartIndex
	 */
	private int shipSizeDiff;
	/** shipSize: It is an integer value, it is calculated from the shipSizeDiff. 
	 * <p> shipSize = shipSizeDiff/10+shipSizeDiff%10+1
	 */ 
	private int shipSize;
	
	/** ArrayList<ShipPart> shipParts: It contains shipParts which is the building unit of a ship
	 * @see shipParts
	 */
	private ArrayList<ShipPart> shipParts;
	
	/**
	 * Empty constructor of Ship to ensure the JavaBeans standard
	 */
	
	public Ship(){
		
	}
	
	/**
	 * Contrsturctor of Ship with two integer parameters.
	 * 
	 * @param shipStartIndex is the first index of the Ship
	 * @param shipEndIndex is the last index of the Ship
	 */
	
	public Ship(int shipStartIndex, int shipEndIndex){
		this.shipStartIndex =shipStartIndex;
		this.shipEndIndex = shipEndIndex;
		
		setShipSizeDiff();
		setShipSize();
		setShipParts();	
	}	
	
	/**
	 * Getter for shipSizeDiff
	 * Return with an integer value, equal with the shipSizeDiff
	 */
	public int getShipSizeDiff() {
		return shipSizeDiff;
	}
	
	/**
	 * Setter for shipSizeDiff
	 * <p> shipSizeDiff = shipEndIndex - shipStartIndex
	 */
	public void setShipSizeDiff() {
		this.shipSizeDiff = this.shipEndIndex - this.shipStartIndex;
	}
	
	/**
	 * Getter for shipSize
	 * <p> Return with an integer value, equal with the shipSize
	 */
	public int getShipSize() {
		return shipSize;
	}
	
	
	
	/**
	 * Setter for shipSize
	 * <p> shipSize = shipSizeDiff/10+shipSizeDiff%10+1
	 */
	public void setShipSize() {
		this.shipSize = this.shipSizeDiff/10+this.shipSizeDiff%10+1;
	}
	
	/**
	 * Getter for shipParts
	 * <p> return with an ArrayList<ShipPart>
	 */
	public ArrayList<ShipPart> getShipParts() {
		return shipParts;
	}
	
	/**
	 * Setter for shipParts
	 * <p> Initialize a new ArrayList and add elements to it. The number of elements is equal with the shipSize
	 */
	public void setShipParts() {
		this.shipParts = new ArrayList<>();
		for(int i=0; i<this.shipSizeDiff%10+1; i++){
			
			for(int j=0; j<this.shipSizeDiff/10+1; j++){
				this.shipParts.add(new ShipPart(this, shipStartIndex+i+j*10));
			}		
		}
	}
	
	
	
	public int getShipStartIndex() {
		return shipStartIndex;
	}

	public void setShipStartIndex(int shipStartIndex) {
		this.shipStartIndex = shipStartIndex;
	}

	public int getShipEndIndex() {
		return shipEndIndex;
	}

	public void setShipEndIndex(int shipEndIndex) {
		this.shipEndIndex = shipEndIndex;
	}


	public void setShipSizeDiff(int shipSizeDiff) {
		this.shipSizeDiff = shipSizeDiff;
	}

	public void setShipSize(int shipSize) {
		this.shipSize = shipSize;
	}

	public void setShipParts(ArrayList<ShipPart> shipParts) {
		this.shipParts = shipParts;
	}

	/**Gives back the number of the healthy parts of the ship
	 * 
	 */
	public int unshootedParts() {
		int unshootedParts = 0;
		for(int i=0; i<this.shipSize; i++)
		{
			if(this.shipParts.get(i).getIsUnshooted()==true)
				unshootedParts++;
		}			
		return unshootedParts;
	}
	
	/**
	 * Gives back a boolean value wether the ship is alive or dead. It returns with true if it is dead
	 * which means there is no unshooted shipParts, and with false if the ship is still alive
	 */
	
	public boolean isDead(){
		boolean deadShip=false;
		if(this.unshootedParts()==0)
			deadShip = true;
		return deadShip;
	}
	
	/**
	 * It sets the isUnshooted parameter of the shipPart with the same index as the parameter of the function to false
	 * @param index: It determines which shipPart's isUnshooted property has to be set to false
	 * 
	 * @see ShipPart, isUnshooted
	 */
	
	public void shootShip(int index){
		if(this.shipSizeDiff>=10)
			this.shipParts.get((index-this.shipStartIndex)/10).setIsUnshooted(false);
		else
			this.shipParts.get(index-this.shipStartIndex).setIsUnshooted(false);
	}
}
