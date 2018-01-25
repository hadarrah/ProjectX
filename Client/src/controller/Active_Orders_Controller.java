package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Msg;
import entity.Order;
import entity.Person;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Active_Orders_Controller  implements ControllerI,Initializable {
public  static ArrayList<Order> order_history;
public Button back_to_profile,cancel_orderb;
public Label user_name;
public static Order orderID_to_cancel= new Order();
	  @FXML
	  TableView<Order_History> table = new TableView<Order_History>();	  
	  @FXML    
	  TableColumn<Order_History, String> OrderId; //  
	  @FXML
	  TableColumn<Order_History, String> Status;
	  @FXML
      TableColumn<Order_History, String> Price;
	  @FXML
      TableColumn<Order_History, String> Order_date;
	  @FXML
      TableColumn<Order_History, String> Delivery;
	  @FXML
      TableColumn<Order_History, String> Req_date;

	  
	      @SuppressWarnings("unchecked")
	      /**
	       * init the table according the user history orders including canceled orders
	       */
	   public void InitTable()
	      {
	    	  
	    	  OrderId.setCellValueFactory(new PropertyValueFactory<>("ID"));
	    	  Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
	    	  Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
	    	  Order_date.setCellValueFactory(new PropertyValueFactory<>("Date"));
	    	  Delivery.setCellValueFactory(new PropertyValueFactory<>("Delivery"));
	    	  Req_date.setCellValueFactory(new PropertyValueFactory<>("Requested_Date"));
	    	  
	      // Set Sort type for userName column
	    	  OrderId.setSortType(TableColumn.SortType.DESCENDING);

	  	ObservableList<Order_History> list = getUserList(order_history);
	      table.setItems(list);
	
 
	      }
	/**
	 * sets the details in the table
	 * @param orders
	 * @return
	 */
	  private ObservableList<Order_History> getUserList(ArrayList<Order> orders) {
		  
		  ArrayList<Order_History>to_ob=new  ArrayList<Order_History>();
		  /*according  the amount of the user orders*/
		  for(int i=0;i<orders.size();i++)
		  {
	      Order_History user1 = new Order_History(orders.get(i).getId(),
	      orders.get(i).getStatus(),Float.toString(orders.get(i).getTotprice())+"-"+ orders.get(i).getPayment(),orders.get(i).getCreatedate()+"-"+orders.get(i).getCreatetime(),
	      orders.get(i).getDelivery1(),orders.get(i).getRequestdate()+"-"+orders.get(i).getRequesttime()); //
	 
	      to_ob.add(user1);
		  }
	     
	      ObservableList<Order_History> list = FXCollections.observableArrayList(to_ob);
	      return list;
	  }
	
	 /**
	  * sends a request to the server-> DB
	  * to get this user order history
	  */
	  protected void getUserHistory() 
	  {
		  Msg msg= new Msg();
		  msg.setRole("get user active orders");
		  msg.setSelect();
		  Person cur=new Person(null,null);
		  cur=controller.Main_menu.current_user;
		  msg.freeField=controller.Login_win.chosen_store;
		  msg.oldO= cur;
		  Login_win.to_Client.accept(msg);

		}
	  
	 /**
	  * the return value from the DB 
	  * calling the initTable function to set the details in the table 
	  * @param o
	  */
	public void setUserHistory(Object o)
	{
		order_history= (ArrayList<Order>) ((Msg)o).newO;
	    InitTable();
	}
	/**
	 * get the order that the user chose
	 * saves the order id 
	 * @param event
	 */
 	@FXML
	public void clickItem(MouseEvent event)
	{
	    if (event.getClickCount() == 1) //Checking  click
	    { 
	    	String a=table.getSelectionModel().getSelectedItem().getStatus();
	    	if(a.equals("Active")) {
	      	orderID_to_cancel.setId(table.getSelectionModel().getSelectedItem().getID());
	    	}
	    	else {
	    		orderID_to_cancel.setId(null);
	    	}
	    }
	}
	/**
	 * back to the profile screen
	 * @param event
	 * @throws IOException
	 */
	public void back_to_profile(ActionEvent event)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Main_menu_F.fxml"));
		 Scene win1= new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("css/Main_menu.css").toExternalForm());

		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
	
	public void cancel_order(ActionEvent event)throws IOException 
	{
		if(	orderID_to_cancel.getId()==null)
		{
			controller.Login_win.showPopUp("ERROR", "Wrong choise", "You can only cancel Active orders", "Please try again!");
			return;
		}
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				  Parent menu;
		  try {
			menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Cancel_Order_F.fxml")); 
			Scene win1= new Scene(menu);
			 win1.getStylesheets().add(getClass().getResource("css/common.css").toExternalForm());
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			}
		});
		
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login_win.to_Client.setController(this);
		user_name.setText(controller.Main_menu.current_user.getUser_name()+"-"+"Active Orders");
		getUserHistory();
	}

	


}
