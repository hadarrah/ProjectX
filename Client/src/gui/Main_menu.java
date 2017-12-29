package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import action.Msg;
import action.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main_menu  implements Initializable,ControllerI{

	public ColorPicker color_PICK;
	public Button mannag_B,back_B;
	public Label main_label;
	public static Person current_user;
	public  static action.ClientConsole to_Client;

 
	public void back_logOut(ActionEvent event) throws IOException
	{
		
		/*logout process*/
		
		 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource("Login_F.fxml"));
	//	 to_Client.setController(new Login_win());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
	
	
    public void managment(ActionEvent event) throws IOException {
    	
    	 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource("Managment_F.fxml"));
		// to_Client.setController(new Managment_Controller());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
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
