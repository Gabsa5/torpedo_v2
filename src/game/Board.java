package game;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

	//Properties of Board class
	private int boardSize;
	private int boardSizeSQ;
	private ArrayList<BoardCell> cells;
	private ArrayList<Ship> ships = new ArrayList<>();
	private boolean shotSuccessful;
	
	public Board() {
		
	}

	//Constructor
	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.boardSizeSQ = this.boardSize * this.boardSize;
		this.shotSuccessful = false;

		this.initializeCells();

	}

	//Initialize the cells of the board
	private void initializeCells() {
		this.cells = new ArrayList<>();
		for (int i = 0; i < this.boardSizeSQ; i++) {
			cells.add(new BoardCell(i));
		}
	}

	//Add ship to the board
	public void addShip(int shipStartIndex, int shipEndIndex) {
		Ship actualShip = new Ship(shipStartIndex, shipEndIndex);
		ships.add(actualShip);
		for (int i = 0; i < actualShip.getShipSize(); i++) {
			ShipPart shipPart = actualShip.getShipParts().get(i);
			BoardCell actualCell = this.cells.get(shipPart.getShipPartIndex());
			actualCell.setShipPart(shipPart);
		}
	}

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

	//Getter for boardSize
	public int getBoardSize() {
		return boardSize;
	}

	//Getter for boardSizeSQ
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
