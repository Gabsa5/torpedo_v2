package gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class MultiplayerSettingsView {
	private Stage stage;
	private Pane root;

	public MultiplayerSettingsView(Stage s) {
		this.stage = s;
	}
	private Parent createContent() {
		root = new Pane();
		root.setPrefSize(800, 600);

		Label someLabel = new Label("Some label:");
		someLabel.setTranslateX(10);
		someLabel.setTranslateY(5);
		someLabel.setTextFill(Color.BLACK);
		someLabel.setFont(Font.font("", FontWeight.BOLD, 15));

		root.getChildren().addAll(someLabel);

		return root;
	}
	
	public void build() {
		Scene scene = new Scene(createContent());
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
