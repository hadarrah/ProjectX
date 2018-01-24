package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import action.Msg;
import action.Person;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Update_Personal_Info_Controller implements ControllerI,Initializable {

	public JFXTextField new_name_T,new_last_name_T;
	public JFXPasswordField  new_pass_T;
	public Button back_to_user_details_B,update_info_B;
	
	
	/**Display current user's information on TextFields*/
	public void display_cur_info(Person cur_user)
	{
		Person user=cur_user;
		 new_name_T.setText( user.getUser_name());
	 	new_last_name_T.setText( user.getUser_last_name());
		 new_pass_T.setText( user.getUser_password());
		
		
	}
	
	/**Back button function*/
	 public void back_to_personal_info (ActionEvent event)throws IOException 
		{
			  Parent menu;
			  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Profile_F.fxml"));
			//  to_Client.setController(new Main_menu());
			 Scene win1= new Scene(menu);
			 win1.getStylesheets().add(getClass().getResource("css/Profile.css").toExternalForm());

			 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
			 win_1.setScene(win1);
			 win_1.show();
		}
	
	 /**Update user's details*/
	 public void update_details()
	 {
		
		 Msg msg=new Msg();		 
		 
		 msg.setRole("update user details");
		 msg.setUpdate();
		 msg.setTableName("person");
		 msg.oldO=gui.Login_win.current_user;
	
		 
		 Person new_user_details=gui.Login_win.current_user; 
		 new_user_details.setUser_name(new_name_T.getText());
		 new_user_details.setUser_last_name(new_last_name_T.getText());
		 new_user_details.setUser_password(new_pass_T.getText());
		 
		 
		 msg.newO=new_user_details;
		 
		 Login_win.to_Client.accept(msg);
		 
	 }
	 
	 /**
	  * after the answer to the update was accepted(updated was done successfully or not)
	  * a confirmation window us shown to the user /
	  * the new user details are saved in the system as current user.
	  * @param message
	  */
	 public void get_new_user_details(Object message)
	 {
		Msg msg= (Msg)message;
		System.out.println(msg.getRole());
		Person new_user_details=(Person) msg.newO;
		gui.Login_win.current_user=(Person) msg.newO;
		
	Platform.runLater(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			gui.Login_win.showPopUp("INFORMATION", "UpDate was done!", "Your details were Successfully updated !", "");
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
		 Login_win.to_Client.setController(this);
		display_cur_info(gui.Login_win.current_user);
		// TODO Auto-generated method stub
		
	}

}
