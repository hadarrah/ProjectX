package gui;
import java.io.IOException;
	import java.net.URL;

import javax.swing.JOptionPane;

import javafx.application.Application;
	import javafx.fxml.FXMLLoader;
	import javafx.scene.Scene;
	import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class main extends Application {
	
	public static String user_port;
	public  static String user_host;
	public static String fxmlDir = "fxml/";
	public static Stage primary;

	
	/**Initial page configuration*/
	  public void start(Stage primaryStage) throws IOException
	  {

		URL url = getClass().getResource(fxmlDir+ "Login_F.fxml");
	 	Pane pane = FXMLLoader.load( url );
	  	Scene scene = new Scene( pane );
	  	scene.getStylesheets().add(getClass().getResource("css/Login.css").toExternalForm());
	  	primaryStage.setResizable(false);
		
	    // setting the stage
	    primaryStage.setScene( scene );
	    primaryStage.setTitle("ZerLi X-System");
	    primaryStage.show();
	    primary = primaryStage;
	  }
	  /**
	   * run the main program 
	   * check if the IP that was insert is vaild->if not->show error msg
	   * @param args
	   */
   public static void main(String[] args) 
   {
	  
	   launch(args);
			
	 }
	}

 