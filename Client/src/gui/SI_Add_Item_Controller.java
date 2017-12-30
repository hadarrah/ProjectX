package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SI_Add_Item_Controller implements Initializable,ControllerI{

	public Button add_items_B;
	public Button present_items_B;
	public Button back_B;
	public TextField min_TF,max_TF;
	public TextArea item_list_TA;
	public ComboBox<String> color_CB, type_CB;
	
	
	public void setType(){
		String colors[] = {"bouqet", "vase", "flower"};
	    ArrayList<String> al= new ArrayList<String>(Arrays.asList(colors));
	    ObservableList<String> list = FXCollections.observableArrayList(al); 
	    type_CB.setItems(list);
	}
	
	public void setColor(){
		String colors[] = {"red", "green", "blue", "yellow", "purple"};
	    ArrayList<String> al= new ArrayList<String>(Arrays.asList(colors));
	    ObservableList<String> list = FXCollections.observableArrayList(al); 
	    color_CB.setItems(list);		
	}
	
	
	
    public void back(ActionEvent event) throws IOException{
    	move(event,main.fxmlDir+ "Self_Item_F.fxml");
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setType();
		setColor();
	}

}
