package game;

public class ShipPart {
	
	//Properties of ShipPart
	private boolean isUnshooted;
	private int shipPartIndex;
	private Ship ship;
	
	public ShipPart(Ship ship, int shipPartIndex){
		this.ship = ship;
		this.shipPartIndex=shipPartIndex;
		this.isUnshooted=true;
	}
	
	//Getter for shipPartIndex
	public int getShipPartIndex() {
		return shipPartIndex;
	}
	
	public void setShipPartIndex(int shipPartIndex) {
		this.shipPartIndex = shipPartIndex;
	}


	//Getter for isUnshooted
	public boolean getIsUnshooted() {
		return isUnshooted;
	}

	//Setter for isUnshooted
	public void setIsUnshooted(boolean isUnshooted) {
		this.isUnshooted = isUnshooted;
	}

	public Ship getShip() {
		return ship;
	}
}
