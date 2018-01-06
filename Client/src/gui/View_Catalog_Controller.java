package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import action.Msg;
import action.Person;
import action.Survey;
import action.Item_In_Catalog;
import javafx.application.Platform;
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
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class View_Catalog_Controller  implements ControllerI, Initializable  {

	public Button Prev_B;
	public Button back_B;
	public Button Next_B;	
	public Text txtID;	
	public Text txtName;	
	public Text txtPrice;	
	public Text txtAmount;	
	public Text txtDescription;	
	public Text txtCatalog;
	public Text txtCounter;
	public Pane borderPane;	
	public ImageView Itemimg;
	public ImageView PrimPane;
	public static ArrayList<Item_In_Catalog> Itc;
	public static int Itc_counter=0;
	public static int view_counter=0;
	public static Person current_user;
	
	
	

	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Main_menu_F.fxml");
	}

	public void move(ActionEvent event, String next_fxml) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(next_fxml));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();

		// close window by X button
		win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Msg msg = new Msg();
				Person user_logout = current_user;
				msg.setRole("user logout");
				msg.setTableName("person");
				msg.setUpdate();
				msg.oldO = user_logout;
				Login_win.to_Client.accept(msg);
			}
		});
	}

	/*--init the object with SELECTALL query--*/
	public void init() {
		Msg msg = new Msg();
		msg.setRole("View all catalog items");
		msg.setSelect();
		msg.setTableName("item_in_catalog");
		msg.freeField=current_user.getUser_ID();//save current user id 
		Login_win.to_Client.accept(msg); 
	}

	/*--setting default values by opening the catalog--*/
	public void initCatalog(Object message)  {
		Msg tmp = (Msg) message;
		Itc = (ArrayList<Item_In_Catalog>) tmp.newO;
		Itc_counter=Itc.size();	
		Prev_B.setDisable(true);
		SetCounter(view_counter+1,Itc_counter);
		SetDetailsGui(Itc.get(0)); //default view is the first item in the array
	}
	
	/*--Setting the current item details in gui--*/			
	public void SetDetailsGui(Item_In_Catalog Itc)
	{
		//Platform.runLater(new Runnable() {

		//	@Override
			//public void run() {					
				txtID.setText(Itc.getID());
				txtName.setText(Itc.getName());
				txtPrice.setText(""+Itc.getPrice());
				txtDescription.setText(Itc.getDescription());
				txtAmount.setText(""+Itc.getAmount());
				File f=new File(Itc.getImage());				
		        Image image =new Image(f.toURI().toString());								
				Itemimg.setImage(image);				
			//}
		//});		
	}	
	
	/*--set the next item--*/
	public void nextItem(ActionEvent event)throws IOException 
	{
		  Prev_B.setDisable(false);
		  view_counter++; 		
		  SetCounter(view_counter+1,Itc_counter);
		  if(view_counter==Itc_counter-1)
			  Next_B.setDisable(true);
		  SetDetailsGui(Itc.get(view_counter));		  
	}
	
	/*--set the previews item--*/
	public void prevItem(ActionEvent event)throws IOException 
	{
		if(view_counter<Itc_counter)
			 Next_B.setDisable(false);
		  view_counter--; 		
		  SetCounter(view_counter+1,Itc_counter);
		  if(view_counter==0)
			  Prev_B.setDisable(true);
		  SetDetailsGui(Itc.get(view_counter));		  
	}
	
	/*--set counters values--*/
	public void SetCounter(int view,int Itc)
	{
		txtCounter.setText(view_counter+1+"/"+Itc_counter);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * update the current controller to be view catalog controller in general
		 * ClientConsole instance
		 */
		current_user=gui.Login_win.current_user;
		Login_win.to_Client.setController(this);
		init();
		
	}

}
