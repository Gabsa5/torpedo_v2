package gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

		Label ipLabel = new Label("Add ip adress:");
		ipLabel.setTranslateX(100);
		ipLabel.setTranslateY(250);
		ipLabel.setTextFill(Color.BLACK);
		ipLabel.setFont(Font.font("", FontWeight.BOLD, 30));
		
		TextField ipField = new TextField();
		ipField.setPrefSize(200, 30);
		ipField.setTranslateX(310);
		ipField.setTranslateY(260);
		
		Label portLabel = new Label("Add port:");
		portLabel.setTranslateX(100);
		portLabel.setTranslateY(300);
		portLabel.setTextFill(Color.BLACK);
		portLabel.setFont(Font.font("", FontWeight.BOLD, 30));
		
		TextField portField = new TextField();
		portField.setPrefSize(200, 30);
		portField.setTranslateX(240);
		portField.setTranslateY(310);
		
		Button submit = new Button("Submit");
		submit.setTranslateX(100);
		submit.setTranslateY(350);
		submit.setPrefSize(80, 20);
		

		root.getChildren().addAll(ipLabel, ipField, portLabel, portField, submit);

		return root;
	}
	
	public void build() {
		Scene scene = new Scene(createContent());
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
