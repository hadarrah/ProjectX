package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import action.Complain;
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

public class Answer_Complaint_Controller implements Initializable,ControllerI{

		public TextArea answer_Text;
		public Button submit_answer_B, back_B;
		public Label complaint_ID_L, hour_L, date_L, invalid_detailsL_com;
		public Label invalid_detailsL_ID, invalid_detailsL_comment, invalid_detailsL_comment_length;
		public TextField compensation_Text;
		public ComboBox<String> customer_ID_combo, store_ID_combo;
		public static ActionEvent event_log;

		
		/**
		 * handle with pressing "submit answer"
		 * @param event
		 */
		public void submit_Answer(ActionEvent event) {

			/*save the event*/
	    	event_log =new ActionEvent();		 
			event_log=event.copyFor(event.getSource(), event.getTarget());
			
			/*set the error label*/
	    	invalid_detailsL_comment.setVisible(false);
	    	invalid_detailsL_ID.setVisible(false);
	    	invalid_detailsL_comment_length.setVisible(false);
	    	invalid_detailsL_com.setVisible(false);
	    	
			String answer = "";
			answer = answer_Text.getText();
			
			/*check input from user*/
			if(customer_ID_combo.getValue() == null)
			{
				invalid_detailsL_ID.setVisible(true);
				return;
			}
			if(!isFloat(compensation_Text.getText()))
			{
				invalid_detailsL_com.setVisible(true);
				return;
			}
			if(answer.length() == 0)
			{
		    	invalid_detailsL_comment.setVisible(true);
				return;
			}
			if(answer.length() >= 200)
			{
				invalid_detailsL_comment_length.setVisible(true);
				return;
			}
			
			/*find the specific complaint to update*/
			for(Complain com : Managment_Controller.complaint)
				if(com.getCustomer_ID().equals(customer_ID_combo.getValue()))
					{
						com.setAnswer(answer);
						com.setCompensation(compensation_Text.getText());
						com.setCustomer_ID(customer_ID_combo.getValue());
						com.setStore(store_ID_combo.getValue());
						/*prepare msg to server*/
						Msg commentToSet = new Msg();
						commentToSet.setUpdate();
						commentToSet.setRole("set answer complaint");
						commentToSet.oldO = com;
						Login_win.to_Client.accept((Object) commentToSet);
					}
			
	    }

		/**
		 * update the rest of the fields regarding to the selected customer id
		 * @param event
		 */
	public void check_SelecetdID(ActionEvent event) 
	{
		String customer_seleceted = customer_ID_combo.getValue();
		ArrayList<String> stores = new ArrayList<String>();
		
		for(Complain com : Managment_Controller.complaint)
			if(com.getCustomer_ID().equals(customer_seleceted))
				stores.add(com.getStore());
		
		ObservableList<String> list = FXCollections.observableArrayList(stores);
		store_ID_combo.setItems(list);
		store_ID_combo.setDisable(false);
	}
	
	
	/**
	 * update the rest of the fields regarding to the selected store id
	 * @param event
	 */
	public void check_SelecetdStoreID(ActionEvent event) 
	{
		String store_seleceted = store_ID_combo.getValue();
	    	
		for(Complain com : Managment_Controller.complaint)
			if(com.getStore().equals(store_seleceted) && com.getCustomer_ID().equals(customer_ID_combo.getValue()))
				{
					complaint_ID_L.setText(com.getComplain_ID());
					hour_L.setText(com.getHour());
					date_L.setText(com.getDate());
				}
	}
	
    public void back(ActionEvent event) throws IOException {
    	move(event, main.fxmlDir+ "Managment_F.fxml");
    }

   
    /**
     * set the customer id in combobox
     * @param msg
     */
    public void set_Customer_ID()
    {
    	ArrayList<String> customerID = new ArrayList<String>();
    	
    	for(Complain com: Managment_Controller.complaint)
    		if(!customerID.contains(com.getCustomer_ID()))
    			customerID.add(com.getCustomer_ID());
    	
    	ObservableList<String> list = FXCollections.observableArrayList(customerID);
    	customer_ID_combo.setItems(list);
    }
    
    /**
     * handle when the update answer success in DB
     * @param msg
     */
    public void update_answer_success(Object msg)
    {
    	/*the submit was successful -> run in new thread the new window*/
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
				 	    Login_win.showPopUp("INFORMATION", "Message", "Your answer to the complaint was submitted - have a GOOD day!", "Thank you!");
						move(event_log , main.fxmlDir+ "Managment_F.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
			}
		}); 
    }
    
    /**
     * check if the string is integer
     * @param input
     * @return
     */
    public boolean isFloat( String input ) 
    {
        try {
            Float.parseFloat(input);
            return true;
        }
        catch( Exception e ) {
            return false;
        }
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
    	/*update the current controller to be this controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	/*set the error label*/
    	invalid_detailsL_comment.setVisible(false);
    	invalid_detailsL_ID.setVisible(false);
    	invalid_detailsL_comment_length.setVisible(false);
    	invalid_detailsL_com.setVisible(false);
    	store_ID_combo.setDisable(true);
    	
    	/*set the details of survey in fields*/
    	set_Customer_ID();
	}
}