package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class View_Catalog_Controller implements ControllerI,Initializable{
	
	public Label lblID;
	public Label lblName;
	public Label lblPrice;
	public Label lblAmount;
	public Label lblDescription;
	public TextArea txtDescription;
	public Pagination pagPic;
	public  VBox vbLabels;
	public Text txtCatalog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		/*update the current controller to be view catalog controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
	}

}
