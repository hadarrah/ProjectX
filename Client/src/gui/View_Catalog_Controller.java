package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import action.Msg;
import action.Person;
import action.Item_In_Catalog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class View_Catalog_Controller implements ControllerI,Initializable{
	
	public static Person current_user;
	public Label lblID;
	public Label lblName;
	public Label lblPrice;
	public Label lblAmount;
	public Label lblDescription;
	public Button back_B;
	public TextArea txtDescription;
	public Pagination pagPic;
	public  VBox vbLabels;
	public Text txtCatalog;

	 public void back(ActionEvent event) throws IOException {
	    	move(event, main.fxmlDir+ "Main_menu_F.fxml");
	    }
	 
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
		      		Person user_logout=current_user;
		      		msg.setRole("user logout");
		      		msg.setTableName("person");
		      		msg.setUpdate();
		      		msg.oldO=user_logout;
		      		Login_win.to_Client.accept(msg);
		          }
		      });        
		}
	 /*init the object with SELECTALL query*/ 
	 public void init(Object message)
	 {
		 Msg tmp= (Msg)message;
		 tmp.setRole("View all catalog items");	
		tmp.setSelectAll();	
		tmp.setTableName("item_in_catalog");
		System.out.println(tmp.getRole()+" "+tmp.getType());
	 }
	 
	 /*display items detail int catalog_giu*/
	 public void ViewCatalog(Object message)
	 {
		 Msg msg1= (Msg)message;
		 ArrayList<Item_In_Catalog> L= (ArrayList<Item_In_Catalog>)msg1.newO;
		lblID.setText(L.get(0).getID());
	 }
	@Override
	 public void initialize(URL location, ResourceBundle resources) {  
		/*update the current controller to be view catalog controller in general ClientConsole instance*/
		Login_win.to_Client.setController(this);
		Msg msg=new Msg();
		init(msg);		
    	Login_win.to_Client.accept(msg);
	}

}
