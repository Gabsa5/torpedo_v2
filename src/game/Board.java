package game;
import java.util.ArrayList;

public class Board {

	//Properties of Board class
	private int boardSize;
	private int boardSizeSQ;
	private ArrayList<BoardCell> cells;
	private ArrayList<Ship> ships;

	//Constructor
	public Board(int boardSize){
		this.boardSize=boardSize;
		this.setBoardSizeSQ();
		
		this.initializeShips();
		this.initializeCells();
		
		}
	
	public void initializeShips() {
		this.ships = new ArrayList<Ship>();
		
	}

	//Getter for boardSize
	public int getBoardSize() {
		return boardSize;
	}

	//Getter for boardSizeSQ
	public int getBoardSizeSQ() {
		return boardSizeSQ;
	}

	//Setter for boardSizeSQ
	public void setBoardSizeSQ() {
		this.boardSizeSQ = this.boardSize*this.boardSize;
	}

	//Getter for Cells of the board
	public ArrayList<BoardCell> getCells() {
		return cells;
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

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	//Initialize the cells of the board
	public void initializeCells() {
		this.cells = new ArrayList<BoardCell>();
		for(int i=0; i<this.boardSizeSQ;i++){
			cells.add(new BoardCell(i));
		}
	}
	
	//Add ship to the board
	public void addShip(int shipStartIndex, int shipEndIndex){
		Ship actualShip = new Ship(shipStartIndex, shipEndIndex);
		ships.add(actualShip);
		for(int i=0; i<actualShip.getShipSize(); i++){
			ShipPart shipPart = actualShip.getShipParts().get(i);
			BoardCell actualCell=this.cells.get(shipPart.getShipPartIndex());
			actualCell.setShipPart(shipPart);
		}
	}
	
	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	public void shootShip(int cellIndex){
		if(this.cells.get(cellIndex).getIsEmptyCell()){
			System.out.println("Nem talált!");
			}
		else{
			System.out.println("Talált!");
			}
	}

	public void prettyPrint() {
		for (int j = 0; j < boardSize; j++) {
			for (int i = 1; i <= boardSize; i++) {
				BoardCell cell = cells.get(j * boardSize + i - 1);
				if (cell.getIsEmptyCell() && !cell.getIsShootedCell()) {
					System.out.print("0");
				} else if (!cell.getIsEmptyCell() && !cell.getIsShootedCell()) {
					System.out.print(cell.getShipPart().getShip().getShipSize());
				} else if (!cell.getIsEmptyCell() && cell.getIsShootedCell()) {
					System.out.print("X");
				} else {
					System.out.print("U");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}
