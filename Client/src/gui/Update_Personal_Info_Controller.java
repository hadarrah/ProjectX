package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import action.Msg;
import action.Person;
import javafx.event.ActionEvent;
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

public class Update_Personal_Info_Controller implements ControllerI,Initializable {

	public TextField new_name_T,new_last_name_T,new_pass_T;
	public Button back_to_user_details_B,update_info_B;
	
	
	public void display_cur_info(Person cur_user)
	{
		Person user=cur_user;
		 new_name_T.setText( user.getUser_name());
	 	new_last_name_T.setText( user.getUser_last_name());
		 new_pass_T.setText( user.getUser_password());
		
		
	}
	 public void back_to_personal_info (ActionEvent event)throws IOException 
		{
			  Parent menu;
			  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Profile_F.fxml"));
			//  to_Client.setController(new Main_menu());
			 Scene win1= new Scene(menu);
			 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
			 win_1.setScene(win1);
			 win_1.show();
		}
	
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
	 
	 
	 public void get_new_user_details(Object message)
	 {
		

	 }
	 
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 Login_win.to_Client.setController(this);
		display_cur_info(gui.Login_win.current_user);
		// TODO Auto-generated method stub
		
	}

}
