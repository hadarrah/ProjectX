package gui;
import action.ClientConsole;
import action.Msg;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

 

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Menu_controller implements ControllerI, Initializable {
	public Button view_info,exit_menu;
	public Label no_item_select_label;
	public ComboBox<String> product_list;
	public ObservableList<String> list;
	public static String choosen_name;
	public static action.ClientConsole toClient;

	/**
	 	the call to this function is made by the client 
		 the func gets the products names and set it in the comboBox 
	 * @param message
	 */
	public void setNames(Object message)
	 {
	    ArrayList<String> al= (ArrayList<String>)message;
	    list = FXCollections.observableArrayList(al); 
	   product_list.setItems(list);
	      
	 }
	/**
	 * after the user click on VIEW_INFO button -> a new window is shown
	 * If the user didn't choose a product -> show error msg 
	 * @param event
	 * @throws IOException
	 */
		 public void view_info(ActionEvent event) throws IOException 
		 { 
			
			 if(product_list.getValue()==null)
			 {
				 no_item_select_label.setVisible(true);
			 }
			 else {
					/*Saving the product name that has been chosen by the user*/
					 choosen_name=(String)product_list.getValue();	
					
					 Parent menu= FXMLLoader.load(getClass().getResource(main.fxmlDir+ "win_2.fxml"));// go to next window
					 Scene next_win= new Scene(menu);
					 Stage next_win2= (Stage) ((Node) (event.getSource())).getScene().getWindow();
					 next_win2.setScene(next_win);
					 next_win2.show();
			 }
		 }

		public void Quit_from_menu()
		 {
			 /*saving data */
			 System.exit(0); 
		 }
 
		@Override
		public void initialize(URL location, ResourceBundle resources)
		{
			no_item_select_label.setVisible(false);
			// create a new client - the main client
			//ClientConsole toClient = new ClientConsole(main.user_host,5555,this);
			/*set the client */
			Menu_controller.toClient=toClient;
			/*creating & sending a new message */
			Msg msg=new Msg();
			msg.setSelectAll();
			toClient.accept(msg);  //send the first Msg to server - Select All- to show the names in comboBox
			
		}
	 /**
	  * return the Client that has been created by the main window 
	  * @return
	  */
		public Object getClient()
		{
			return Menu_controller.toClient;
			
		}
	}

		 
 
	
	
	
	
 
