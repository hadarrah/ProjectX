package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import com.sun.media.jfxmediaimpl.platform.Platform;

import action.Msg;
import action.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Purchase_History_Controller implements ControllerI,Initializable {
	
	// define table
	 @FXML
	  TableView<Table> tableID;
	 @FXML
	  TableColumn<Table,String> ID;
	 @FXML
	 TableColumn<Table,String> Status;
	 @FXML
	 TableColumn<Table,String>  Date;
	 @FXML
	  TableColumn<Table,String>  Price;
	
	// define var
 public float  var=(float) 0.9;
	public Button back_to_profile;
	//table data 
	final ObservableList<Table> data= FXCollections.observableArrayList(new Table("1","a","d"));
	
	
	public void back_to_profile (ActionEvent event) throws IOException 
	{
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Profile_F.fxml"));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}
	public  void getData()
	{	 System.out.println("i got to fuc in purc ");

		Msg msg= new Msg();
		msg.setSelect();
		msg.setRole("get user order history");
		msg.oldO=gui.Login_win.current_user;
		Login_win.to_Client.accept(msg);
		
	}
	
	public void setData(Object o)
	{	

		Msg msg=(Msg) o;
		ArrayList<Order>orders=new ArrayList<Order>();
		orders=(ArrayList<Order>) msg.newO;
	
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Login_win.to_Client.setController(this);
		ID.setCellValueFactory(new PropertyValueFactory<Table,String>("rID"));
		Status.setCellValueFactory(new PropertyValueFactory<Table,String>("rStatus"));
		Date.setCellValueFactory(new PropertyValueFactory<Table,String>("rDate"));
	//	Price.setCellValueFactory(new PropertyValueFactory<Table,float>("rPrice"));
		//getData();
		tableID.setItems(data);
		tableID.setVisible(true);
	 
	}


}
