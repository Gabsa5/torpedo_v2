package game;

public class BoardCell {
	
	//Properties of BoardCell
	private int cellIndex;
	private boolean isShootedCell;
	private ShipPart shipPart;
	
	public BoardCell() {
		
	}

	
	public BoardCell(int cellIndex){
		this.cellIndex=cellIndex;
		this.setIsShootedCell(false);	
	}
	
	//Getter for cellIndex
	public int getCellIndex() {
		return cellIndex;
	}
	
	

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}



	//Getter for isEmptyCell
	public boolean getIsEmptyCell() {
		return this.shipPart == null;
	}
	
	//Getter for isShootedCell
	public boolean getIsShootedCell() {
		return isShootedCell;
	}

	//Setter for isShootedCell
	public void setIsShootedCell(boolean isShootedCell) {
		this.isShootedCell = isShootedCell;
	}

	public ShipPart getShipPart() {
		return shipPart;
	}

	public void setShipPart(ShipPart shipPart) {
		this.shipPart = shipPart;
	}


	public void setShootedCell(boolean isShootedCell) {
		this.isShootedCell = isShootedCell;
	}
}
