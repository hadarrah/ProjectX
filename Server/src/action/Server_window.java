package action;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Server_window  extends javafx.application.Application{

	
	public void start(Stage primaryStage) throws Exception {

		URL url = getClass().getResource("S.fxml");
	 	Pane pane = FXMLLoader.load( url );
	  	Scene scene = new Scene( pane );
	  	primaryStage.setResizable(false);
		
	    // setting the stage
	    primaryStage.setScene( scene );
	    primaryStage.setTitle("ZerLi X-System");
	    primaryStage.show();
	  }
		
	}

 