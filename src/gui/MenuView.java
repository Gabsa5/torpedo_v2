package gui;

import gui.Controller;
import javafx.scene.Scene;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MenuView {
	
	protected VBox menuBox;
	protected int itemNumber = 0;
	protected static Stage s;
	protected final ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();
	protected static final Font FONT = Font.font("", FontWeight.BOLD, 20);
	
	
	public MenuView(Stage s) {
		this.s = s;
	}
	
	protected Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(800, 600);
		
		MenuItem itemExit = new MenuItem("EXIT");
		itemExit.setOnActivate(() -> System.exit(0));
		
		menuBox = new VBox(10,
				new MenuItem("SinglePlayer"),
				new MenuItem("MultiPlayer"),
				itemExit);
		menuBox.setAlignment(Pos.TOP_CENTER);
		menuBox.setTranslateX(350);
        menuBox.setTranslateY(230);

		
		getMenuItem(0).setActive(true);
		
		root.getChildren().add(menuBox);
		return root;
	}
	
	private MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }
	
	
    private static class MenuItem extends HBox{
    	private Runnable script;
    	private final Text text;
    	
    	public MenuItem(String name) {
    		setAlignment(Pos.CENTER);
    		
    		text = new Text(name);
    		text.setFont(FONT);
    		
    		getChildren().add(text);
    		setActive(false);
    		setOnActivate(() -> {
    			System.out.println(name + " activated");
    			switch(name) {
    			case "SinglePlayer":
    				Controller c = new Controller(s);
    				c.start();
    				break;
    			case "Multiplayer":
    				Controller c2 = new Controller(s);
    				c2.start();
    				break;
    			}
    		});
    	}
    	
    	public void setActive(boolean b) {
            text.setFill(b ? Color.AQUA : Color.BLUE);
    	}
    	
    	public void setOnActivate(Runnable r) {
	    	script = r;
	    }
	    
    	public void activate() {
            if (script != null)
                script.run();
    	}
    }
    
    public void build() throws Exception{
    	Scene scene = new Scene(createContent());
    	scene.setOnKeyPressed(event -> {
    		if (event.getCode()==KeyCode.UP) {
    			if(itemNumber>0) {
    				getMenuItem(itemNumber).setActive(false);
    				getMenuItem(--itemNumber).setActive(true);
    			}
    		}
    		
    		if (event.getCode() == KeyCode.DOWN) {
                if (itemNumber < menuBox.getChildren().size() - 1) {
                    getMenuItem(itemNumber).setActive(false);
                    getMenuItem(++itemNumber).setActive(true);
            }
    		}
    		
    		if (event.getCode() == KeyCode.ENTER) {
                getMenuItem(itemNumber).activate();
    		}
    		
    	});
    	
    	s.setScene(scene);
        s.setResizable(false);
        s.setOnCloseRequest(event -> bgThread.shutdownNow());
        s.show();
    	
    }

}
