package game;

import java.util.ArrayList;

import gui.GameView;
import javafx.stage.Stage;

public class SinglePlayer implements Player {

	private Board board;
	private GameView gameView;
	private boolean readyWithShipPlacement = false;

	public SinglePlayer(Stage stage) {

		gameView = new GameView(stage, this);
		gameView.build();
	}

	//	public void shoot(int index){
//
//		BoardCell cell = this.playerBoard.getCells().get(index);
//
//		if(cell.getIsEmptyCell())
//		{
//			cell.setIsShootedCell(true);
//			System.out.println("Miss");
//		}else{
//			if(cell.getIsShootedCell()){
//				System.out.println("Already shooted");
//			}else{
//				System.out.println("Hit");
//				cell.setIsShootedCell(true);
//				Ship shootedShip = cell.getShipPart().getShip();
//				shootedShip.shootShip(index);
//				this.setLife(this.getLife()-1);
//			}
//		}
//	}

	@Override
	public void placeShips(Board board) {
		this.board = board;
		gameView.createUnPlacedShip(2);
		gameView.createUnPlacedShip(2);
		gameView.createUnPlacedShip(3);
		gameView.createUnPlacedShip(3);
		gameView.createUnPlacedShip(4);
		gameView.createUnPlacedShip(5);
	}

	@Override
	public void shoot(Board board) {
 		// TODO
	}

	@Override
	public boolean isReadyWithPlaceShips() {
		return this.readyWithShipPlacement;
	}

	public void afterShipSelection(ArrayList<Ship> ships) {
		for (Ship ship: ships) {
			board.addShip(ship.getShipStartIndex(), ship.getShipEndIndex());
		}
		this.readyWithShipPlacement = true;
	}
}
