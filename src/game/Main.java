package game;

import AI.BattleshipAI;
import game.Game;
import game.Ship;
import game.Player;
import game.Board;
import game.BoardCell;
import game.ShipPart;
import javafx.application.Application;
import javafx.stage.Stage;
import gui.MenuView;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FilterOutputStream;
import java.util.Scanner;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        MenuView menuView = new MenuView(primaryStage);
        menuView.build();

    }

	
	public static void main(String[] args) {
		launch(args);

//		Board board = new Board(10);
//
//		BattleshipAI battleshipAI = new BattleshipAI(board);
//
//		battleshipAI.addRandomShip(5);
//		battleshipAI.addRandomShip(4);
//		battleshipAI.addRandomShip(3);
//		battleshipAI.addRandomShip(3);
//		battleshipAI.addRandomShip(2);
//		battleshipAI.addRandomShip(2);
//
//		board.prettyPrint();
//
//		Player player = new AIPlayer();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
//
//		player.shoot(board);
//		board.prettyPrint();
	}

}
