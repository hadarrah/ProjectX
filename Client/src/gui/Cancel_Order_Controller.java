package gui;
import   java.util.Date;
import java.util.Optional;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import action.Msg;
import action.Order;
import action.Person;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Cancel_Order_Controller  implements ControllerI,Initializable{
 
    public ComboBox<String> order_ids ;
    public Button back,cancel_order_b,view_details_b;
    public Label calc;
    public TextArea order_details;
    ObservableList<String> list;
    /*a struck that contains the cur_user orders*/
    public static ArrayList<Order> orders;
    public int half=0,full=0,non=0,cant=0;
    public static ActionEvent event_log;
    
    
	public void back_to_main(ActionEvent event) throws IOException
	{
		
		Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Main_menu_F.fxml"));
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
    
    /**
     * set the initial info in the form according the current user
     * if there is no order for this user shows a msg and return to the main menu
     * @param o
     */
    public void SetOrdersIds(Object o)
    {
    	Msg msg=new Msg();
    	msg=(Msg)o;
    	orders=(ArrayList<Order>) msg.newO;
    	/*this user has no order*/
    	if(orders.size()<1)
    	{
    		
    		Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Login_win.showPopUp("ERROR", "Message", "You dont have any Orders in your account", "");
				}});
    	}
    	ArrayList<String>orders_id=new ArrayList<String>();
    	
    	
    	for(int i=0;i<orders.size();i++)    		
    	{
    		orders_id.add(orders.get(i).getId());
    	}
    	list = FXCollections.observableArrayList(orders_id); 
    	 order_ids.setItems(list);
    }
    
   /**
    * set the details in the main form 
    * after the user chose his order (include validate)
    * details are shown & calculation is made
    * @param event
    * @throws IOException
    */
    public void setOrderdetails(ActionEvent event) throws IOException
    {
    	
    	
    	non=0; half=0; full=0;cant=0;
    	order_details.setVisible(true);
    	order_details.clear();
    	String date,hour;
    	String txt;
    	if(order_ids.getValue()==null)
    	{
    		calc.setTextFill(Color.web("#ed0b31"));
    		  Login_win.showPopUp("ERROR", "Message", "You have to choose an Order ID", "Thank you!");
    	}
    	
    	else {
    	for(int i=0;i<orders.size();i++)
    	{
    		/*set the details in the text are*/
    		if(order_ids.getValue().equals(orders.get(i).getId()))
    		{
    			
    					txt="Order ID:"+orders.get(i).getId();
    				order_details.appendText(txt+"\n\n");
    				
    				txt="Delivery Chosen:"+orders.get(i).getDelivery1();
    				order_details.appendText(txt+"\n\n");
    				
    				
    				txt="Payment Type:"+orders.get(i).getPayment();
    				order_details.appendText(txt+"\n\n");
    			 
    				txt="Price"+orders.get(i).getTotprice();
    				order_details.appendText(txt+"\n\n");
    			 
    				txt="Date of Order"+orders.get(i).getCreatedate()+"-" +orders.get(i).getCreatetime();
    				order_details.appendText(txt+"\n\n");
    			 
    				date=orders.get(i).getRequestdate();
    				hour=orders.get(i).getRequesttime();
    			 txt="Requsted time :"+orders.get(i).getRequestdate()+"-" +orders.get(i).getRequesttime();
    			 order_details.appendText(txt+"\n\n\n");
    			
    			calcCompensation(date,hour);
    		}
    	}
    	
    
    	}
    }
     
    
    
    
    public void show_cancel_msg(Object o)
    {
    	System.out.println("hjkhjk");
    	Msg msg=(Msg)o;
    	
    	if(msg.freeField.equals("succeed"))
    	{ Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
				Login_win.showPopUp("INFORMATION", "Message", "Your order has been canceled", "");
				Parent menu;
				  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Main_menu_F.fxml"));
				 Scene win1= new Scene(menu);
				 Stage win_1= (Stage) ((Node) (event_log.getSource())).getScene().getWindow();
				 win_1.setScene(win1);
				 win_1.show();
				}
			  catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

				
			}
		});
    		
    	}
    		
    	else {
    		Login_win.showPopUp("ERROR", "Message", "at this moment we this action cant be done", "");
    	}
    }
    
    /**
     * check if the form is valid
     * send a msg to server-> Db to change the order status
     * @param event
     * @throws IOException
     */
    public void CancelOrder(ActionEvent event) throws IOException
    {
    	event_log =new ActionEvent();		 
		event_log=event.copyFor(event.getSource(), event.getTarget());
    	if(cant==1) {
    	Login_win.showPopUp("ERROR", "Message", "You cant cancel this order", "Please try again!");
    	return;}
    	else {
    		
    		Msg msg= new Msg();
    		msg.setUpdate();
    		msg.setTableName("order");
    		msg.setRole("change order status");
    		if(full==1)
    			msg.freeField="full";
    		else 
    			msg.freeField="half";
    		Order o =new Order();
    		o.setId(order_ids.getValue());
    		msg.oldO=o;
    		 Login_win.to_Client.accept(msg);
    		
    		
    		
    	}
    }
    /**
     * calculate the compensation time according the user story
     * @param date2
     * @param wanted_h
     */
 private void calcCompensation(String date2, String wanted_h)
 {
	 int wantedH,curH,wantedM,curM;
	 	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	 	Date date = new Date();
    	String date1=(dateFormat.format(date).toString()); 
    		
    		
    		/*check if the date is today*/
    		if(date2.equals(date1))
    		{
    			 DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
    			 Date hour = new Date();
    			 String cur_hour=(dateFormat2.format(hour).toString());
    			 wantedM= Integer.parseInt(wanted_h.substring(3, 5)); 
    			 curM=Integer.parseInt(cur_hour.substring(3, 5)); 
    			 
    			 
    			 curH= Integer.parseInt(cur_hour.substring(0, 2)); 
    			 wantedH=Integer.parseInt(wanted_h.substring(0, 2));
    			 
    			 /*last hour*/
    			    if( curH== (wantedH-1) &&  wantedM<=curM)
    			    {
    			    	calc.setTextFill(Color.web("#ed0b31"));
    			    	calc.setText("0% Companstaion");
    			    	non=1;
    			    }
    			    /*more then an hour 	*/
    			    else if( curH== (wantedH-1) &&  wantedM>curM)
    			    {
    			    
    			     
    			    	calc.setTextFill(Color.web("#edda0b"));
    			     	calc.setText("You will get 50%");
    			     	half=1;
    			    }
    			 /*more then 3 hours*/
    			    else if(curH>wantedH || curH==wantedH)
    			    {
    			    	calc.setTextFill(Color.web("#ed0b31"));
    			     	calc.setText("You canot cancel on this order");
    			     	cant=1;
    			    }
    			   
    			    else  if( curH<= (wantedH-3) && wantedM<=curM)
    			     {	
    			    	calc.setTextFill(Color.web("#ed0b31"));
    			    	calc.setText("You will get 50%");
    			    	 half=1;
    			    }
    			    else  if( curH<= (wantedH-3) )
   			     {	
   			    	calc.setTextFill(Color.web("#31ed0b"));
   			    	calc.setText("You will get 100%");
   			    	full=1;
   			    	 
   			    }
   			    

    			    /*2 hours left*/
    			    else if( (curH ==(wantedH-2)))
    			    { 
    			    	calc.setTextFill(Color.web("#edda0b"));

    			    	calc.setText("You will get 50%");
    			    	half=1;
    			    }
    			    
    		}
    		else {
    			calc.setTextFill(Color.web("#31ed0b"));
    			calc.setText("You will get 100%");
    			full=1;
    			 
    		}
    		/*if the date in tomorow..*/
    			
	}

 /**
  * get all the orders of this user
  * in order to save unnecessary DB access
  */
private void getOrdersId()
 	{
	 Msg msg= new Msg();
	 msg.setSelect();
	 msg.setTableName("order");
	 msg.setRole("get orders id");
	 Person current= gui.Main_menu.current_user;
	 msg.oldO=(Person)current;
	 Login_win.to_Client.accept(msg);
	 
		 
		
	}
	public void initialize(URL location, ResourceBundle resources) {
		 Login_win.to_Client.setController(this);
		 getOrdersId();
	}

	

}
