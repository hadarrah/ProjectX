package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import action.Msg;
import action.Person;
import action.Survey;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Close_Survey_Controller implements Initializable,ControllerI{

    public TextField ID_text;
    public TextField Date_text;
    public Button back_B;
    public TextField NumOfParticipant_text;
    public Button close_Survey_B;
	public static ActionEvent event_log;

    public void back(ActionEvent event) throws IOException {
    	move(event, main.fxmlDir+ "Managment_F.fxml");
    }

    /**
     * handle function for pressing "close survey" 
     * @param event
     */
    public void close_Survey(ActionEvent event) {
    	
    	/*save the event*/
    	event_log =new ActionEvent();		 
		event_log=event.copyFor(event.getSource(), event.getTarget());
		
    	/*insert the input from user to instance of Survey"*/
    	Survey survey_toDel = new Survey();
    	survey_toDel.setID(ID_text.getText());
		Msg check_user_details= new Msg();
		check_user_details.setUpdate();;
		check_user_details.setTableName("survey");
		check_user_details.oldO=survey_toDel;
		check_user_details.setRole("close survey");
		check_user_details.event=event;
		Login_win.to_Client.accept((Object)check_user_details);
    }

    /**
     * return to previous window after the "closing survey" was success
     * @param msg
     */
    public void close_survey_success(Object msg)
    {
    	/*the creating was successful -> run in new thread the new window*/
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
				 	    Login_win.showPopUp("INFORMATION", "Message", "Your survey was closed - have a GOOD day!", "Thank you!");
						move(event_log , main.fxmlDir+ "Managment_F.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
			}
		}); 
    }
    /**
     * General function for the movement between the different windows
     * @param event
     * @param next_fxml = string of the specific fxml
     * @throws IOException
     */
    public void move(ActionEvent event, String next_fxml)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(next_fxml));
		 Scene win1= new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("css/common.css").toExternalForm());

		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		 
		  //close window by X button
		 win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  Msg  msg=new Msg();
	      		Person user_logout=Login_win.current_user;
	      		msg.setRole("user logout");
	      		msg.setTableName("person");
	      		msg.setUpdate();
	      		msg.oldO=user_logout;
	      		Login_win.to_Client.accept(msg);
	          }
	      });        
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	/*update the current controller to be management controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	/*set the details of survey in fields*/
    	ID_text.setText(Managment_Controller.active_survey.getID());
    	Date_text.setText(Managment_Controller.active_survey.getDate());
    	NumOfParticipant_text.setText(Managment_Controller.active_survey.getNumOfParticipant());
	}
}
