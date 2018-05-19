package gui;

import game.Board;
import game.BoardCell;
import game.Ship;

import java.util.ArrayList;

import game.SinglePlayer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.input.*;


public class GameView {
	private SinglePlayer singlePlayer;

	private Stage stage;
	private Pane root;
	private static int boardSize = 100;
	private double offsetX;
	private double offsetY;
	private double nextShipY = 360;
	private ArrayList<Rectangle> myCellRects = new ArrayList<>();
	private ArrayList<Rectangle> enemyCellRects = new ArrayList<>();
	private ArrayList<Rectangle> ships = new ArrayList<>();
	private Button startGameButton;

	private String newLine = System.getProperty("line.separator");
	
	public GameView(Stage s, SinglePlayer singlePlayer) {
		this.stage = s;
		this.singlePlayer = singlePlayer;
	}


	private Parent createContent() {
		root = new Pane();
		root.setPrefSize(800, 600);
		
		Label myLabel = new Label("My field:");
		myLabel.setTranslateX(10);
		myLabel.setTranslateY(5);
		myLabel.setTextFill(Color.BLACK);
        myLabel.setFont(Font.font("", FontWeight.BOLD, 15));
		
        Label otherLabel = new Label("Enemy's field:");
		otherLabel.setTranslateX(450);
		otherLabel.setTranslateY(5);
		otherLabel.setTextFill(Color.BLACK);
		otherLabel.setFont(Font.font("", FontWeight.BOLD, 15));

		createMyBoard();
		createEnemyBoard();
		
		startGameButton = new Button();
		startGameButton.setText("Start the game!");
		startGameButton.setTranslateX(400-startGameButton.getWidth()/2);
		startGameButton.setTranslateY(400);
		startGameButton.setDisable(true);
		startGameButton.setOnAction(e -> {
			ArrayList<Ship> shipList = new ArrayList<> ();
			for(Rectangle ship : ships) {
				Ship newShip = new Ship(getCellNumber(ship.getX()+15, ship.getY()+15), getCellNumber(ship.getX()+ship.getWidth()-15, ship.getY()+ship.getHeight()-15));
				shipList.add(newShip);
				ship.setDisable(true);
			}
			singlePlayer.afterShipSelection(shipList);
			root.getChildren().remove(startGameButton);
		});
		
		root.getChildren().addAll(myLabel, otherLabel, startGameButton);
	
		return root;
	}

	public void createUnPlacedShip(int l) {
		int height = 30;
		int width = l*height;
		
		Rectangle ship = new Rectangle(50, nextShipY, width, height);
		nextShipY += 40;
		ship.setFill(Color.LIGHTGREEN);
		ship.setStroke(Color.DARKGREEN);
		
		ship.setOnMouseDragged(e -> {
			if (e.isPrimaryButtonDown()) {
				double pressedX = e.getSceneX();
				double pressedY = e.getSceneY();
				double posX = pressedX - offsetX;
				double posY = pressedY - offsetY;
				ship.setX(posX);
				ship.setY(posY);

				int cellNumber = getCellNumber(ship.getX(), ship.getY());

				for(Rectangle r : myCellRects) {
					if(r.getFill().equals(Color.DARKCYAN)) {
						r.setFill(Color.DARKBLUE);
					}
					if(r.getFill().equals(Color.LIGHTCYAN)) {
						r.setFill(Color.LIGHTBLUE);
					}
				}

				if(myCellRects.size() > cellNumber) {
					Rectangle rectangle = myCellRects.get(cellNumber);
					if(rectangle.getFill().equals(Color.DARKBLUE) || rectangle.getFill().equals(Color.DARKCYAN)) {
						rectangle.setFill(Color.DARKCYAN);
					} else {
						rectangle.setFill(Color.LIGHTCYAN);
					}
				}

			}
		});
		
		ship.setOnMouseReleased(e -> {
			int cellNumber = getCellNumber(ship.getX(), ship.getY());
			
			if(myCellRects.size() > cellNumber) {
				ship.setX(getMyCellNumberX(cellNumber));
				ship.setY(getMyCellNumberY(cellNumber));
			}
			
			if(isShipsAcceptable()) {
				startGameButton.setDisable(false);
			}else {
				startGameButton.setDisable(true);
			}
		});

		ship.setOnMousePressed(e -> {
			if (e.isSecondaryButtonDown()) {
				double temp = ship.getHeight();
				ship.setHeight(ship.getWidth());
				ship.setWidth(temp);
			}else if (e.isPrimaryButtonDown()) {
				double pressedX = e.getSceneX();
				double pressedY = e.getSceneY();
				offsetX = pressedX - ship.getX();
				offsetY = pressedY - ship.getY();
				System.out.print(offsetX);
				System.out.print(offsetY);
				System.out.print(newLine);
			}
		});
		
		ships.add(ship);
		
		root.getChildren().add(ship);
	}

	public void shoot() {
		for(Rectangle cell: this.enemyCellRects) {
			cell.setDisable(false);
		}
	}

	public void redrawMyBoard(Board board) {
		for(BoardCell boardCell: board.getCells()) {
			int cellIndex = boardCell.getCellIndex();
			Rectangle rect = this.myCellRects.get(cellIndex);

			if (boardCell.getIsShootedCell() && boardCell.getIsEmptyCell()) {
				rect.setFill(Color.YELLOW);
			} else if (boardCell.getIsShootedCell() && !boardCell.getIsEmptyCell()) {
				Rectangle shootedRect = new Rectangle();
				shootedRect.setX(rect.getX());
				shootedRect.setY(rect.getY());
				shootedRect.setWidth(rect.getWidth());
				shootedRect.setHeight(rect.getHeight());
				shootedRect.setStroke(Color.BLACK);
				shootedRect.setFill(Color.RED);


				Platform.runLater(() -> root.getChildren().add(shootedRect));
			}
		}
	}

	public void redrawEnemyBoard(Board board) {
		for(BoardCell boardCell: board.getCells()) {
			int cellIndex = boardCell.getCellIndex();
			Rectangle rect = this.enemyCellRects.get(cellIndex);

			if (boardCell.getIsShootedCell() && boardCell.getIsEmptyCell()) {
				rect.setFill(Color.YELLOW);
			} else if (boardCell.getIsShootedCell() && !boardCell.getIsEmptyCell()) {
				rect.setFill(Color.RED);
			}
		}
	}
	
	public void build() {
		Scene scene = new Scene(createContent());
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public int getCellNumber(double x, double y) {
		int number = 0;
		int coordX = 30;
		int coordY = 50;
		for(int i = 0; i < boardSize/10; ++i) {
			for(int j = 0; j < boardSize/10; ++j) {
				if(coordX <= x && x < coordX+30 && coordY <= y && y < coordY + 30) {
					return number;
				}
				number++;
				coordX = coordX + 30;
			}
			coordX = 30;
			coordY = coordY + 30;
		}
		return number;
	}
	
	private int getMyCellNumberX(int cellNumber) {
		int number = 0;
		int coordX = 30;
		int coordY = 50;
		for(int i = 0; i < boardSize/10; ++i) {
			for(int j = 0; j < boardSize/10; ++j) {
				
				if(number == cellNumber) {
					return coordX;
				}
				
				number++;
				coordX = coordX + 30;
			}
			coordX = 30;
			coordY = coordY + 30;
		}
		return coordX;
	}
	
	private int getMyCellNumberY(int cellNumber) {
		int number = 0;
		int coordX = 30;
		int coordY = 50;
		for(int i = 0; i < boardSize/10; ++i) {
			for(int j = 0; j < boardSize/10; ++j) {
				
				if(number == cellNumber) {
					return coordY;
				}
				
				number++;
				coordX = coordX + 30;
			}
			coordX = 30;
			coordY = coordY + 30;
		}
		return coordY;
	}
	
	private boolean isShipsAcceptable() {
		for(Rectangle ship : ships) {
			if(ship.getX() + ship.getWidth() > 331) {
				return false;
			}
			if(ship.getY() + ship.getHeight() > 351) {
				return false;
			}
			if(ship.getX() < 29) {
				return false;
			}
			if(ship.getY() < 49) {
				return false;
			}
			for(Rectangle otherShip : ships) {
				if(areShipsIntersect(ship, otherShip)){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean areShipsIntersect(Rectangle ship1, Rectangle ship2) {
		if(ship1.equals(ship2)) {
			return false;
		}
		

	    if (
	        (ship1.getX() > ship2.getX() + ship2.getWidth() - 1 ) ||
	        (ship1.getX() + ship1.getWidth() - 1 < ship2.getX()) || 
	        (ship1.getY() > ship2.getY() + ship2.getHeight() - 1 ) || 
	        (ship1.getY() + ship1.getHeight() - 1 < ship2.getY())){
	        return false;
	    } else {
	        return true;
	    }

	}

	private void createEnemyBoard() {
		int x;
		int y = 50;
		boolean dark = true;

		for(int m = 0; m < (boardSize/10); ++m) {

			x = 450;

			for(int n = 0; n < (boardSize/10); ++n) {

				Rectangle rect = new Rectangle(x, y, 30, 30);
				rect.setStroke(Color.BLACK);

				if(dark) {
					rect.setFill(Color.DARKBLUE);
				}else {
					rect.setFill(Color.LIGHTBLUE);
				}

				rect.setOnMouseEntered(e -> {
					if(rect.getFill().equals(Color.DARKBLUE)) {
						rect.setFill(Color.DARKCYAN);
					} else if (rect.getFill().equals(Color.LIGHTBLUE)){
						rect.setFill(Color.LIGHTCYAN);
					}
				});

				rect.setOnMouseExited(e -> {
					if(rect.getFill().equals(Color.DARKCYAN)) {
						rect.setFill(Color.DARKBLUE);
					} else if (rect.getFill().equals(Color.LIGHTCYAN)) {
						rect.setFill(Color.LIGHTBLUE);
					}
				});

				rect.setOnMouseClicked(e -> {
					int cellIndex = this.enemyCellRects.indexOf(rect);
					singlePlayer.afterShoot(cellIndex);
				});

				rect.setDisable(true);
				this.enemyCellRects.add(rect);
				root.getChildren().add(rect);

				dark = !dark;
				x = x + 30;
			}

			dark = !dark;
			y = y + 30;
		}

		createEnemyBoardAlphabet();

		createEnemyBoardNumbers();
	}

	private void createEnemyBoardNumbers() {
		int x = 435;
		int y = 54;
		int num = 1;
		for(int ii = 0; ii < boardSize/10; ++ii) {
			Label z = new Label(String.valueOf(num));
			z.setTranslateX(x);
			z.setTranslateY(y);
			num++;
			y = y + 30;
			root.getChildren().add(z);
		}
	}

	private void createEnemyBoardAlphabet() {
		int x = 462;
		int y = 35;
		char alpha = 'A';
		for(int ii = 0; ii < boardSize/10; ++ii) {
			Label z = new Label(String.valueOf(alpha));
			z.setTranslateX(x);
			z.setTranslateY(y);
			alpha++;
			x = x + 30;
			root.getChildren().add(z);
		}
	}

	private void createMyBoard() {
		boolean dark = true;
		int x;
		int y = 50;

		for(int i = 0; i < (boardSize/10); ++i) {

			x = 30;

			for(int j = 0; j < (boardSize/10); ++j) {

				Rectangle rect = new Rectangle(x, y, 30, 30);
				rect.setStroke(Color.BLACK);

				if(dark) {
					rect.setFill(Color.DARKBLUE);
				}else {
					rect.setFill(Color.LIGHTBLUE);
				}
				myCellRects.add(rect);
				root.getChildren().add(rect);

				dark = !dark;
				x = x + 30;
			}

			dark = !dark;
			y = y + 30;
		}

		createMyBoardAlphabet();
		createMyBoardNumbers();
	}

	private void createMyBoardNumbers() {
		int x;
		int y;

		x = 15;
		y = 54;
		int num = 1;
		for(int ii = 0; ii < boardSize/10; ++ii) {
			Label z = new Label(String.valueOf(num));
			z.setTranslateX(x);
			z.setTranslateY(y);
			num++;
			y = y + 30;
			root.getChildren().add(z);
		}
	}

	private void createMyBoardAlphabet() {
		int x = 42;
		int y = 35;
		char alpha = 'A';
		for(int ii = 0; ii < boardSize/10; ++ii) {
			Label z = new Label(String.valueOf(alpha));
			z.setTranslateX(x);
			z.setTranslateY(y);
			alpha++;
			x = x + 30;
			root.getChildren().add(z);
		}
	}
	

}
