package game;

import java.util.ArrayList;

public class Board {

	//Properties of Board class
	private int boardSize;
	private int boardSizeSQ;
	private ArrayList<BoardCell> cells;
	private ArrayList<Ship> ships = new ArrayList<>();

	//Constructor
	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.boardSizeSQ = this.boardSize * this.boardSize;

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

	public void shootShip(int cellIndex) {
		System.out.println("Lövés: " + cellIndex);
		BoardCell targetCell = this.cells.get(cellIndex);
		targetCell.setIsShootedCell(true);
		if (targetCell.getIsEmptyCell()) {
			System.out.println("Nem talált!");
		} else {
			System.out.println("Talált!");
			targetCell.getShipPart().setIsUnshooted(false);
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
}
