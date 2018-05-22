package game;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.MenuView;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        MenuView menuView = new MenuView(primaryStage);
        menuView.build();

    }

	
	public static void main(String[] args) {
		launch(args);

	}

}
