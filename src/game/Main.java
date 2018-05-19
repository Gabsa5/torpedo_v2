package game;

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
		// TODO Auto-generated method stub

		/*
		Game game = new Game(10);
		game.shipPlacement();
		try {
			FileOutputStream fos1 = new FileOutputStream(new File("./board1.xml"));
			XMLEncoder encoder1 = new XMLEncoder(fos1);
			encoder1.writeObject(game.getPlayers().get(0).getPlayerBoard());
			FileOutputStream fos2 = new FileOutputStream(new File("./board2.xml"));
			XMLEncoder encoder2 = new XMLEncoder(fos2);
			encoder2.writeObject(game.getPlayers().get(1).getPlayerBoard());
			
			FileOutputStream fos3 = new FileOutputStream(new File("./ship.xml"));
			XMLEncoder encoder3 = new XMLEncoder(fos3);
			encoder3.writeObject(game.getPlayers().get(0).getPlayerBoard().getShips().get(0));
			
			encoder1.close();
			encoder2.close();
			encoder3.close();
			fos1.close();
			fos2.close();
			fos3.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		game.gamePlay();
		
		try {
			FileInputStream fis = new FileInputStream(new File("./board1.xml"));
			XMLDecoder decoder = new XMLDecoder(fis);
			
			game.getPlayers().get(0).setPlayerBoard((Board)decoder.readObject());
			
			FileInputStream fis2= new FileInputStream(new File("./board2.xml"));
			XMLDecoder decoder2 = new XMLDecoder(fis2);
			
			game.getPlayers().get(1).setPlayerBoard((Board)decoder2.readObject());
			
			decoder.close();
			fis.close();
			decoder2.close();
			fis2.close();
			
			
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		game.getPlayers().get(0).setLife(5);
		game.getPlayers().get(1).setLife(5);
		game.gamePlay();
		
		*/
		
		
		
		
		
		
	}

}
