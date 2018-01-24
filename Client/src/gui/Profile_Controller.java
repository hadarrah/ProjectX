package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import action.Msg;
import action.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Profile_Controller implements ControllerI,Initializable {
	public Button back_to_main_B,edit_details_B,history;
	public VBox v_details;
	public Label l_credit,l_subs,l_status,l_balance,l_id,l_name,l_last_name,l_type,l_wwid;
	
	
	/**Move to Purchase History button function*/
	public void to_purchase_history(ActionEvent event)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Purchase_History_F.fxml"));
		 Scene win1= new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("css/common.css").toExternalForm());
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
	/**
	 * set the current user details in the window 
	 * also present the account details
	 * @param user
	 */
	public void set_user_details(Person user)
	{
		Person current_user=user;
		 l_id.setText( "ID:"+ current_user.getUser_ID());
		 l_name.setText( "Name:"+current_user.getUser_name());
		 l_last_name.setText("Last Name:"+current_user.getUser_last_name());
 		 l_type.setText( "Type:"+current_user.getPrivilege());
		 if(!(current_user.getPrivilege().equals("Customer")))  
		 l_wwid.setText("WWID:"+current_user.getWWID());
		 else
			 l_wwid.setVisible(false);
		   
		if(!(gui.Login_win.current_user_pay_account.getCreditCard()==null)) 
		{
		 l_credit.setText("Credit Card:"+gui.Login_win.current_user_pay_account.getCreditCard());
		 l_subs.setText("Subscription:"+gui.Login_win.current_user_pay_account.getSubscription());
 		 l_status.setText("Status:"+gui.Login_win.current_user_pay_account.getStatus());
	 
		 if(gui.Login_win.current_user_pay_account.getRefund_sum()==null)
          l_balance.setText("Balance : 0");
		 else
		  l_balance.setText("Balance :"+Float.toString(gui.Login_win.current_user_pay_account.getRefund_sum())); 
		 }
		else {
			 l_credit.setText("Payment Account wasn't set yet- Please Contact the store manager !");
			 l_status.setVisible(false);
 			 l_balance.setVisible(false);
			 l_subs.setVisible(false);
		}	 
 	}
	  public void back_to_main(ActionEvent event)throws IOException 
		{
			  Parent menu;
			  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Main_menu_F.fxml"));
			//  to_Client.setController(new Main_menu());
			 Scene win1= new Scene(menu);
			 win1.getStylesheets().add(getClass().getResource("css/Main_menu.css").toExternalForm());

			 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
			 win_1.setScene(win1);
			 win_1.show();
		}
	  
	  
	  public void to_update_info(ActionEvent event)throws IOException 
			{
				  Parent menu;
				  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Update_Personal_Info_F.fxml"));
				 Scene win1= new Scene(menu);
				 win1.getStylesheets().add(getClass().getResource("css/common.css").toExternalForm());
				 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
				 win_1.setScene(win1);
				 win_1.show();
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
		
		set_user_details(gui.Login_win.current_user);
		// TODO Auto-generated method stub
		
		
	}

}
