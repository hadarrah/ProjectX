package gui;

import java.io.File;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Profile_Controller implements ControllerI,Initializable {
	public JFXTextArea details_txt;
	public Button back_to_main_B,edit_details_B,history;
	public VBox v_details;
	
	
	/**Move to Purchase History button function*/
	public void to_purchase_history(ActionEvent event)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Purchase_History_F.fxml"));
		 Scene win1= new Scene(menu);
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
		details_txt.setEditable(false);
		details_txt.setFont(new Font("SansSerif", 17));
		//details_txt.setStyle("-fx-background-color:orangered;");
				 
	  	String details="Personal Details";
		 details_txt.appendText("\t\t\t"+details+"\n\n");
	  
		 details="ID:"+current_user.getUser_ID();
		 details_txt.appendText(details+"\n\n");
		 details="Name:"+current_user.getUser_name();
		 details_txt.appendText(details+"\n\n");
		 details="Last Name:"+current_user.getUser_last_name();
		 details_txt.appendText(details+"\n\n");
		  details="Type:"+current_user.getPrivilege();
		 details_txt.appendText(details+"\n\n");
		 if(!(current_user.getPrivilege().equals("Customer"))) {
		 details="WWID:"+current_user.getWWID();
		 details_txt.appendText(details+"\n\n\n");
		  }
		 
		
		 
		 
		 
		 
		if(!(gui.Login_win.current_user_pay_account.getCreditCard()==null)) 
		{
			
		
		 details="Account Details";
		 details_txt.appendText("\t\t\t"+details+"\n\n");
		 details_txt.setFont(new Font("SansSerif", 17));
		 details="Credit Card:"+gui.Login_win.current_user_pay_account.getCreditCard();
		 details_txt.appendText(details+"\n\n");
		 details="Subscription:"+gui.Login_win.current_user_pay_account.getSubscription();
		 details_txt.appendText(details+"\n\n");
		 details="Status:"+gui.Login_win.current_user_pay_account.getStatus();
		 details_txt.appendText(details+"\n\n");
		 }
		else {
			 details="Payment Account wasn't set yet- Please Contact the store manager !";
			 details_txt.setFont(new Font("Verdana", 12));
			 details_txt.appendText("\t\t\t"+details+"\n\n");
			
		}
		 
 	}
	
	
	
	  public void back_to_main(ActionEvent event)throws IOException 
		{
			  Parent menu;
			  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Main_menu_F.fxml"));
			//  to_Client.setController(new Main_menu());
			 Scene win1= new Scene(menu);
			 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
			 win_1.setScene(win1);
			 win_1.show();
		}
	  
	  
	  public void to_update_info(ActionEvent event)throws IOException 
			{
				  Parent menu;
				  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Update_Personal_Info_F.fxml"));
				//  to_Client.setController(new Main_menu());
				 Scene win1= new Scene(menu);
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
