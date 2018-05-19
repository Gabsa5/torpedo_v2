package game;

public class BoardCell {
	
	//Properties of BoardCell
	private int cellIndex;
	private boolean isEmptyCell;
	private boolean isShootedCell;
	private int shipIndex;
	
	//Constructor
	public BoardCell(){
		
	}
	
	public BoardCell(int cellIndex){
		this.cellIndex=cellIndex;
		this.setIsEmptyCell(true);
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
		return isEmptyCell;
	}

	//Setter for isEmptyCell
	public void setIsEmptyCell(boolean isEmptyCell) {
		this.isEmptyCell = isEmptyCell;
	}
	
	//Getter for isShootedCell
	public boolean getIsShootedCell() {
		return isShootedCell;
	}

	//Setter for isShootedCell
	public void setIsShootedCell(boolean isShootedCell) {
		this.isShootedCell = isShootedCell;
	}

	//Getter for shipIndex
	public int getShipIndex() {
		return shipIndex;
	}
	
	//Setter for shipIndex
	public void setShipIndex(int shipIndex) {
		this.shipIndex = shipIndex;
	}
}
