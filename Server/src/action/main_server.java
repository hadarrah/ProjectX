package action;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class main_server extends Application {
	
	
	/**Initial page configuration*/
	  public void start(Stage primaryStage) throws IOException
	  {

		URL url = getClass().getResource("Server_F.fxml");
	 	Pane pane = FXMLLoader.load( url );
	  	Scene scene = new Scene( pane );
	  	primaryStage.setResizable(false);
		
	    // setting the stage
	    primaryStage.setScene( scene );
	    primaryStage.setTitle("ZerLi Server");
	    primaryStage.show();
	  }
	  /**
	   * run the main program 
	   * @param args
	   */
   public static void main(String[] args) 
   {
	   launch(args);
	 }
	}

 