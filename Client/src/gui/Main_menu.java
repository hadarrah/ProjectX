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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main_menu  implements Initializable,ControllerI{

	public ColorPicker color_PICK;
	public Button mannag_B,back_B,user_profile_B;
	public Label main_label;
	public static Person current_user;
	public   boolean logout_flag;
	//public  static action.ClientConsole to_Client;

 
	public void back_logOut(ActionEvent event) throws IOException
	{
		logout_flag=false;
		show_logout_msg();
			
		/*logout process*/
	
		if(logout_flag==true)
		{
		Login_win.to_Client.setController(this);
		/*creating and sending a logout msg*/
		Msg  msg=new Msg();
		Person user_logout=current_user;
		msg.setRole("user logout");
		msg.setTableName("person");
		msg.setUpdate();
		msg.oldO=user_logout;
		Login_win.to_Client.accept(msg);
		/*back to the log_in menu*/
		 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Login_F.fxml"));
	//	 to_Client.setController(new Login_win());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		}
	}
	
	
    public void managment(ActionEvent event) throws IOException {
    	
    	 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Managment_F.fxml"));
		// to_Client.setController(new Managment_Controller());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
    }
    
    public void self_item(ActionEvent event) throws IOException {
    	
      	 Parent menu;
   		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Self_Item_F.fxml"));
   		 Scene win1= new Scene(menu);
   		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
   		 win_1.setScene(win1);
   		 win_1.show();
      }
    
    
    public void to_user_profile(ActionEvent event)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource("Profile_F.fxml"));
		//  to_Client.setController(new Main_menu());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
    
    private void show_logout_msg() 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("Are you sure you want to Logout ZerLi system?");
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
         if (option.get() == ButtonType.OK)
         logout_flag=true;
         
         else if (option.get() == ButtonType.CANCEL)  
       	 logout_flag=false;
         if (option.get() == null)
             logout_flag=false;

    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		current_user=gui.Login_win.current_user;
		if(!(current_user.getPrivilege().equals("Customer")))
		{
			mannag_B.setVisible(true);
		}
		
	}

}
