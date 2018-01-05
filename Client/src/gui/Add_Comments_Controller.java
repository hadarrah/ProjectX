package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import action.Msg;
import action.Person;
import action.Survey;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Add_Comments_Controller implements Initializable,ControllerI{

	    public Button add_comment_B;
	    public TextArea comment_Text;
	    public Label survey_ID_L;
	    public ComboBox<String> customer_ID_combo;
	    public Button back_B;
	    public Label date_L, invalid_detailsL_comment, invalid_detailsL_ID, invalid_detailsL_comment_length;
		public static ActionEvent event_log;

		/**
		 * handle with pressing "add comment"
		 * @param event
		 */
		public void add_Comment(ActionEvent event) {

			/*save the event*/
	    	event_log =new ActionEvent();		 
			event_log=event.copyFor(event.getSource(), event.getTarget());
			
			/*set the error label*/
	    	invalid_detailsL_comment.setVisible(false);
	    	invalid_detailsL_ID.setVisible(false);
	    	invalid_detailsL_comment_length.setVisible(false);

			String comment = "";
			comment = comment_Text.getText();
			
			/*check input from user*/
			if(customer_ID_combo.getValue() == null)
			{
				invalid_detailsL_ID.setVisible(true);
				return;
			}
			if(comment.length() == 0)
			{
		    	invalid_detailsL_comment.setVisible(true);
				return;
			}
			if(comment.length() >= 200)
			{
				invalid_detailsL_comment_length.setVisible(true);
				return;
			}
			
			/*prepare msg to server*/
			Msg commentToSet = new Msg();
			commentToSet.setUpdate();
			commentToSet.setTableName("comments_survey");
			commentToSet.setRole("set comment survey");
			commentToSet.oldO = customer_ID_combo.getValue();
			commentToSet.newO = comment;
			commentToSet.freeField = survey_ID_L.getText();
			Login_win.to_Client.accept((Object) commentToSet);
	    }

    public void back(ActionEvent event) throws IOException {
    	move(event, main.fxmlDir+ "Managment_F.fxml");
    }

    /**
     * get customer id for combobox
     */
    public void get_Customer_ID()
    {
    	Msg getCustomer = new Msg();
    	getCustomer.setSelect();
    	getCustomer.setTableName("comments_survey");
    	getCustomer.setRole("get combo customer ID");
    	getCustomer.oldO = Managment_Controller.active_survey.getID();
		Login_win.to_Client.accept((Object) getCustomer);
    }
   
    /**
     * set the customer id in combobox
     * @param msg
     */
    public void setCombo(Object msg)
    {
    	ObservableList<String> list = FXCollections.observableArrayList((ArrayList<String>) (((Msg) msg).newO));
    	customer_ID_combo.setItems(list);
    }
    
    /**
     * handle when the update comment success in DB
     * @param msg
     */
    public void update_comment_survey_success(Object msg)
    {
    	/*the creating was successful -> run in new thread the new window*/
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
						move(event_log , main.fxmlDir+ "Managment_F.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
			}
		}); 
    }
    
    /**
     * handle when the update comment faild in DB
     * @param msg
     */
    public void update_comment_survey_faild(Object msg)
    {
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
				 		

				    	Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR");
						alert.setHeaderText("There was problem when the system try to insert the comment\ndue to the length of entire comments");
						alert.showAndWait();
				 		
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
    	
    	/*set the error label*/
    	invalid_detailsL_comment.setVisible(false);
    	invalid_detailsL_ID.setVisible(false);
    	invalid_detailsL_comment_length.setVisible(false);
    	
    	/*set the details of survey in fields*/
    	survey_ID_L.setText(Managment_Controller.active_survey.getID());
    	date_L.setText(Managment_Controller.active_survey.getDate());
    	get_Customer_ID();
	}
}
