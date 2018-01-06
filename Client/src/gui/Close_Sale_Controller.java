package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.javafx.tk.Toolkit;

import action.Msg;
import action.Person;
import action.Sale;
import action.Survey;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Close_Sale_Controller implements Initializable,ControllerI{

		public static ActionEvent event_log;
		public TextField ID_text;
		public Button close_Sale_B;
		public ListView<String> items_list;
		public Button back_B;
		public TextArea description_Text;
		public TextField discount_text;
		
		/**
		 * handle with pressing "create sale"
		 * @param event
		 */
		public void close_Sale(ActionEvent event) {

			/*save the event*/
	    	event_log =new ActionEvent();		 
			event_log=event.copyFor(event.getSource(), event.getTarget());
			
			
			/*prepare msg to server*/
			Msg saleMsg = new Msg();
			saleMsg.setUpdate();
			saleMsg.setRole("close sale");
			saleMsg.oldO = ID_text.getText();
			saleMsg.newO = Managment_Controller.storeID; 
			Login_win.to_Client.accept((Object) saleMsg);
	    }

		 /**
	     * return to previous window after the "closing sale" was success
	     * @param msg
	     */
	    public void close_sale_success(Object msg)
	    {
	    	/*the creating was successful -> run in new thread the new window*/
	    	Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					 	try {
					 	    Login_win.showPopUp("INFORMATION", "Message", "Your sale was closed - have a GOOD day!", "Thank you!");
							move(event_log , main.fxmlDir+ "Managment_F.fxml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					
				}
			}); 
	    }
		
    public void back(ActionEvent event) throws IOException {
    	move(event, main.fxmlDir+ "Managment_F.fxml");
    }

   
    /**
     * set the items in the list view and set up the listview
     * @param msg
     */
    public void set_items_list()
    {
    	
    	/*set the items in listview*/
    	ObservableList<String> list = FXCollections.observableArrayList(Managment_Controller.item_in_sale);
    	items_list.setItems(list);
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
    	
    	/*set the details of sale in fields*/
    	ID_text.setText(Managment_Controller.sale.getID());
    	discount_text.setText(Managment_Controller.sale.getDiscount());
    	description_Text.setText(Managment_Controller.sale.getDescription());
    	set_items_list();
	}
}