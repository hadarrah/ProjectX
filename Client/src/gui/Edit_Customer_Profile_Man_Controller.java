package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import action.Msg;
import action.Payment_Account;
import action.Person;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Edit_Customer_Profile_Man_Controller implements Initializable, ControllerI{

	public Button edit_B, back_B;
	public Label invalid_detailsL_ID;
	public ComboBox<String> id_combo, privilege_combo, status_combo, subscription_combo, store_combo;
	public ObservableList<String> list;
	public static ActionEvent event_log;
	public TreeMap<String, String> customer_privilege;
	public TreeMap<String, ArrayList<String>> status_subscription;

	
	/**
	 * return to the previous window
	 * @param event
	 * @throws IOException
	 */
    public void back(ActionEvent event) throws IOException {

    	move(event,main.fxmlDir+ "Managment_F.fxml");
    }
    
    /**
     * handle when edit button was pressed
     * @param event
     */
    public void edit_Profile(ActionEvent event) {
    	
    	/*save the event*/
    	event_log =new ActionEvent();		 
		event_log=event.copyFor(event.getSource(), event.getTarget());
		
		/*turn off the error label*/
    	invalid_detailsL_ID.setVisible(false);
    	
    	/*check if customer was selected*/
		if(id_combo.getValue() == null)
		{
			invalid_detailsL_ID.setVisible(true);
			return;
		}

		/*prepare ArrayList object to set in msg*/
		ArrayList<String> toSend = new ArrayList<String>();
		toSend.add(id_combo.getValue());
		toSend.add(privilege_combo.getValue());
		toSend.add(status_combo.getValue());
		toSend.add(subscription_combo.getValue());
		toSend.add(store_combo.getValue());
		
		/*prepare msg to server*/
		Msg editToSet = new Msg();
		editToSet.setUpdate();
		editToSet.setRole("set edit profile manager");
		editToSet.oldO = toSend;
		Login_win.to_Client.accept((Object) editToSet);
		
    }
    
    /**
     * send responde to server to get details to all the combobox
     */
   public void get_combobox()
   {
		Msg combo= new Msg();
		combo.setSelect();
		combo.oldO = Main_menu.current_user.getUser_ID();
		combo.setRole("get combo all customers");
		Login_win.to_Client.accept((Object)combo);
   }

   /**
    * handle with the receive details from server to combobox
    * @param message
    */
   public void set_combobox(Object message)
   {
	   /*save the details in local structure*/
	customer_privilege = (TreeMap<String, String>) (((Msg) message).newO);
	status_subscription = (TreeMap<String, ArrayList<String>>) (((Msg) message).oldO);

	/*prepare the array list for customers id*/
   	ArrayList<String> customerID = new ArrayList<String>();
   	for(String ID : customer_privilege.keySet()) 
   		customerID.add(ID);
	   
   	/*set users id combo*/
   	ObservableList<String> list = FXCollections.observableArrayList(customerID);
   	id_combo.setItems(list);
   	
   	/*set privilege combo*/
   	ArrayList<String> privilege = new ArrayList<String>();
   	privilege.add("Chain Employee");
   	privilege.add("Chain Manager");
   	privilege.add("Customer");
   	privilege.add("Customer Service Employee");
   	privilege.add("Service Expert");
   	privilege.add("Store Employee");
   	privilege.add("Store Manager");
   	list = FXCollections.observableArrayList(privilege);
   	privilege_combo.setItems(list);
   	
   	/*set status of payment combo*/
   	ArrayList<String> status = new ArrayList<String>();
   	status.add("Active");
   	status.add("Block");
   	list = FXCollections.observableArrayList(status);
   	status_combo.setItems(list);
   	
   	/*set subscription of payment combo*/
   	ArrayList<String> subscription = new ArrayList<String>();
   	subscription.add("Year");
   	subscription.add("Month");
   	subscription.add("Per Order");
   	list = FXCollections.observableArrayList(subscription);
   	subscription_combo.setItems(list);
   	
   	/*set store  combo*/
   	ArrayList<String> store =  (ArrayList<String>)(((Msg) message).freeUse);
   	list = FXCollections.observableArrayList(store);
   	store_combo.setItems(list);
   }
   
   /**
    * set the rest of combobox regarding the customer id combobox
    * @param event
    */
   public void check_SelecetdID(ActionEvent event) 
   {
   	String customer_seleceted = id_combo.getValue();
   	boolean payment_exist = false;
   	
   	/*allowed to the other combobox to be able*/
   	status_combo.setDisable(false);
	subscription_combo.setDisable(false);
	store_combo.setDisable(false);

	/*set the privilege combobox by the specific customer id*/
   	for(String ID : customer_privilege.keySet())
   		if(ID.equals(customer_seleceted))
   			privilege_combo.setValue(customer_privilege.get(ID));	
   	
	/*set the status/subscription/store combobox by the specific customer id*/
   	for(String ID : status_subscription.keySet())
   		if(ID.equals(customer_seleceted))
   		{
   			payment_exist = true;
   			status_combo.setValue(status_subscription.get(ID).get(0));	
   			subscription_combo.setValue(status_subscription.get(ID).get(1));	
   			store_combo.setValue(status_subscription.get(ID).get(2));
   		}
   	
   	/*disable status/subscription/store -> in use when the last loop above didn't find match id*/
   	if(!payment_exist)
   	{
   		status_combo.setDisable(true);
   		subscription_combo.setDisable(true);
   		store_combo.setDisable(true);
   	}
   		

   }
   
   /**
    * handle when the update details success in DB
    * @param msg
    */
   public void update_details_success(Object msg)
   {
   	/*the update was successful -> run in new thread the new window*/
   	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
				 	    Login_win.showPopUp("INFORMATION", "Message", "Your changes was submitted - have a GOOD day!", "Thank you!");
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
	public void initialize(URL location, ResourceBundle resources) {
		 
	    /*update the current controller to be this controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	/*turn off the error label*/
    	invalid_detailsL_ID.setVisible(false);
    	
    	/*set comboboxes*/
    	get_combobox();
    }
		
}
