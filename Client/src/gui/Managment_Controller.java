package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Managment_Controller implements Initializable,ControllerI {

    public Button create_Survey_B;
    public Button update_Catalog_B;
    public Button answer_Complaint_B;
    public Button create_Sale_B;
    public Button back_B;
    public Button create_PaymentAccount_B;
    public Button compare_Reports_B;
    public Button conclusion_Survey_B;
    public Button edit_CustomersProfile_B;
    public Button display_Reports_B;

    public void update_Catalog(ActionEvent event) {

    }

    public void back(ActionEvent event) throws IOException {
    	move(event, "Main_menu_F.fxml");
    }

    public void create_Sale(ActionEvent event) {

    }

    public void create_Survey(ActionEvent event) {

    }

    public void answer_Complaint(ActionEvent event) {

    }

    public void conclusion_Survey(ActionEvent event) {
    	
    }

    public void create_PaymentAccount(ActionEvent event) throws IOException {
    	move(event , "Create_PaymentAccount_F.fxml");
    }

    public void edit_CustomersProfile(ActionEvent event) {

    }

    public void display_Reports(ActionEvent event) {

    }

    public void compare_Reports(ActionEvent event) {

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
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	/*set the buttons not visible*/
    	create_Survey_B.setVisible(false);
    	update_Catalog_B.setVisible(false);
    	answer_Complaint_B.setVisible(false);
    	create_Sale_B.setVisible(false);
    	create_PaymentAccount_B.setVisible(false);
    	compare_Reports_B.setVisible(false);
    	conclusion_Survey_B.setVisible(false);
    	edit_CustomersProfile_B.setVisible(false);
    	display_Reports_B.setVisible(false);

    	/*update the current controller to be management controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	/*check which privilege has the user*/
    	String privilege = Main_menu.current_user.getPrivilege();
    	
    	switch(privilege)
    	{
    		case "Chain Employee":
    			update_Catalog_B.setVisible(true);
    			break;
    		case "Customer Service Employee":
    			create_Survey_B.setVisible(true);
    			answer_Complaint_B.setVisible(true);
        		break;
    		case "Chain Manager":
    			display_Reports_B.setVisible(true);
    			compare_Reports_B.setVisible(true);
        		break;
    		case "Store Manager":
    			create_PaymentAccount_B.setVisible(true);
    			edit_CustomersProfile_B.setVisible(true);
    			display_Reports_B.setVisible(true);
        		break;
    		case "Service Expert":
    			conclusion_Survey_B.setVisible(true);
        		break;
    		case "Store Employee":
    			create_Sale_B.setVisible(true);
        		break;
    	}
	}
	

}
