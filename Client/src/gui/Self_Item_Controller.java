package gui;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Self_Item_Controller implements Initializable,ControllerI{

    public Button add_items_B;
    public Button remove_items_B;
    public Button order_B;
    public Button cancel_B;
    public TextField items_selected_TF;
    public TextField description_TF;
    public TextField total_price_TF;
    
    
    public void cancel(ActionEvent event) throws IOException{
    	move(event,main.fxmlDir+ "Main_Menu_F.fxml");
    }
    
    
    public void move(ActionEvent event, String next_fxml)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(next_fxml));
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
    
	public void initialize(URL location, ResourceBundle resources)
	{}
	
}
