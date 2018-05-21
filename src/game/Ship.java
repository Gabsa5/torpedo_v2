package game;
import java.util.ArrayList;

/* ToDos
 * 10-es paramétereket kicserélni max board sizera
 * JavaDoc írás
 */

public class Ship {
	
	//Properties of Ship class
	private int shipStartIndex;
	private int	shipEndIndex;
	

	private int shipSizeDiff;
	private int shipSize;
	private ArrayList<ShipPart> shipParts;
	
	public Ship(){
		
	}
	
	public Ship(int shipStartIndex, int shipEndIndex){
		this.shipStartIndex =shipStartIndex;
		this.shipEndIndex = shipEndIndex;
		
		setShipSizeDiff();
		setShipSize();
		setShipParts();	
	}	
	
	//Getter for shipSizeDiff 
	public int getShipSizeDiff() {
		return shipSizeDiff;
	}
	
	/*Setter for shipSizeDiff
	 * shipEndIndex must be higher than shipStartIndex, must have an error handler!!!
	 */
	public void setShipSizeDiff() {
		this.shipSizeDiff = this.shipEndIndex - this.shipStartIndex;
	}
	
	//Getter for shipSize
	public int getShipSize() {
		return shipSize;
	}
	
	//Setter for shipSize
	public void setShipSize() {
		this.shipSize = this.shipSizeDiff/10+this.shipSizeDiff%10+1;
	}
	
	//Getter for shipParts
	public ArrayList<ShipPart> getShipParts() {
		return shipParts;
	}
	
	//Setter for shipParts
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

	//Gives back the number of the healthy parts of the ship 
	public int unshootedParts() {
		int unshootedParts = 0;
		for(int i=0; i<this.shipSize; i++)
		{
			if(this.shipParts.get(i).getIsUnshooted()==true)
				unshootedParts++;
		}			
		return unshootedParts;
	}
	
	public boolean isDead(){
		boolean deadShip=false;
		if(this.unshootedParts()==0)
			deadShip = true;
		return deadShip;
	}
	
	public void shootShip(int index){
		if(this.shipSizeDiff>=10)
			this.shipParts.get((index-this.shipStartIndex)/10).setIsUnshooted(false);
		else
			this.shipParts.get(index-this.shipStartIndex).setIsUnshooted(false);
	}
}
