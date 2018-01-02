package gui;
import action.Item;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import action.ClientConsole;
import action.Msg;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import gui.Menu_controller;
public class win2_Controller implements ControllerI,Initializable {

	 public Button back_to_main,update_name;
	 public TextField product_type_label,product_id_label;
	 public String product_type,product_id,product_name;
	 public TextField old_product_name,new_product_name;
	 public Label update_done_label,unvalid_update_name;
	 ObservableList<String> list;
	 public action.ClientConsole toClient;
	 
	 
 /**
  * The user click on Back Button -> back to the main menu
  * @param event
  * @throws IOException
  */
	public void back_to_main(ActionEvent event) throws IOException 
	 {
		 Parent menu= FXMLLoader.load(getClass().getResource(main.fxmlDir+ "main_win.fxml"));// go to next window
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		 
	 } 
	
	/**
	 *get the same Client from the first window 
	 *send a request to server with the name of the product
	 *set the query to SELECT 
	 * @param Client
	 */
	public void requsetForDetails(ClientConsole Client )
	{  
		this.toClient=Client;
		update_done_label.setVisible(false);
		/*creating a new msg */	 
		 Msg fromGui=new Msg();
		 Item ProductDisplyDetails= new Item();
		 
		 fromGui.setSelect(); 
		 /*get the name of the chosen item */
		 ProductDisplyDetails.setName(Menu_controller.choosen_name);
		 
		  fromGui.oldO=(Object)ProductDisplyDetails;
		  /*update the client who send the msg */
		 // toClient.SetWin2_Controller(this);
		  /*send msg with the item name*/
		  toClient.accept((Object)fromGui );
	}

	/**
	 *gets the details from the DB about the wanted item
	 * @param msg
	 */
	public void setDetails(Object msg)
	{
		
		Msg msgFromClient=(Msg)msg;
		Item productDetails=(Item) msgFromClient.oldO;
		/*set the details in the fields*/
		product_id_label.setText(productDetails.getID());
		product_type_label.setText(productDetails.getType());
		/*set the details on this product -> needed in update func*/
		product_id=productDetails.getID();
		product_type=productDetails.getType();

	}
 /**
  * When the user click on UPDATE button
  * sends a msg with the products details
  * @param event
  * @throws IOException
  */
	public void updateName(ActionEvent event) throws IOException 
	{
		/*if the user entered an empty  string*/
		if(new_product_name.getText().equals(""))
		{
			unvalid_update_name.setVisible(true); 
		}
		else if(!(new_product_name.getText().equals(""))) 
		{
		unvalid_update_name.setVisible(false);
		Msg msg=new Msg();
		Item Oldproduct= new Item();
		Item productToUpdate=new Item();
		msg.setUpdate();
		
		/*set the details of the  old product*/
	    Oldproduct.setName(old_product_name.getText());
	    Oldproduct.setType(product_type);
	    Oldproduct.setID(product_id);
	   
	    /*set the new object details -> now-> only the name field is new*/
		productToUpdate.setName(new_product_name.getText());
		productToUpdate.setID(product_id); 
		productToUpdate.setType(product_type);
		
			msg.oldO=Oldproduct;
			msg.newO=productToUpdate;
 		 /*send the msg with the old & new objects*/
			toClient.accept((Object)msg);
		
		 /*if the there is no input in the new Field*/ 
		   update_done_label.setVisible(true);
		}
	}
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*set the name in the  current name txtField*/ 
		old_product_name.setText(Menu_controller.choosen_name);
		unvalid_update_name.setVisible(false);
		
		requsetForDetails(Menu_controller.toClient );
		
	}
	
	
}
