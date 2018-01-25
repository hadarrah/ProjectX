package action;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Server_Controller  implements Initializable  {

	public TextField scheme_T;
	public Button Bquit;
	public Button Bconnect;
	public PasswordField pass_T;
	public Button Bdisconnect;
	public TextField name_T;
	public Label disconnected_L, invalid_scheme, invalid_pass, invalid_name;
	public Label connected_L;
	public  EchoServer es;
	
	   public void Quit_app(ActionEvent event) 
	    {
			 System.exit(0); 

	    }

	   /**
	    * handle when pressed connect button
	    * @param event
	    */
	   public void hit_connect(ActionEvent event) 
	   {
		   /*set off invalid labels*/
		   invalid_scheme.setVisible(false);
		   invalid_pass.setVisible(false);
		   invalid_name.setVisible(false);
		   
		   /*check input*/
		   if(name_T.getText().equals(""))
		   {
				invalid_name.setVisible(true);
				return;
		   }
		   if(pass_T.getText().equals(""))
		   {
			   invalid_pass.setVisible(true);
				return;
		   }if(scheme_T.getText().equals(""))
		   {
			   invalid_scheme.setVisible(true);
				return;
		   }
		   
		   /*create new connection*/
		   EchoServer.user_name = name_T.getText();
		   EchoServer.user_pass = pass_T.getText();
		   EchoServer.schema_name = scheme_T.getText();

		   es = new EchoServer(EchoServer.DEFAULT_PORT);
		   
		   /*set the new labels and buttons*/
		   connected_L.setVisible(true);
		   Bdisconnect.setVisible(true);
		   disconnected_L.setVisible(false);
		   Bconnect.setVisible(false);
		   
		   try {
				es.listen(); // Start listening for connections
			} catch (Exception ex) {
				System.out.println("ERROR - Could not listen for clients!");
			}
	    }

	   /**
	    * handle when pressed disconnect button
	    * @param event
	    * @throws IOException
	    */
	  public  void hit_disconnect(ActionEvent event) throws IOException
	  {
		  /*remove last connection*/
		  es.stopListening();
		  es.close();
		  es = null;
		  System.gc();
		  
		   /*set the new labels and buttons*/
		  connected_L.setVisible(false);
		   Bdisconnect.setVisible(false);
		   disconnected_L.setVisible(true);
		   Bconnect.setVisible(true);
	    }
	
 
	
 /**
  * Initialize login: creating a new client, saves the ref of this gui screen
  */
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		/*set label and relevant buttons off*/
		connected_L.setVisible(false);
		Bdisconnect.setVisible(false);
		invalid_scheme.setVisible(false);
		invalid_pass.setVisible(false);
		invalid_name.setVisible(false);
	}
}
