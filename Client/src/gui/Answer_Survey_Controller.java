package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class Answer_Survey_Controller implements ControllerI,Initializable {
public RadioButton R3,R1;
public   ToggleGroup group;
public HBox root;
@Override
public void initialize(URL location, ResourceBundle resources) {
	ToggleGroup group = new ToggleGroup();
	 
    // Radio 1: Male
 
    R1.setToggleGroup(group);
    R1.setSelected(true);

    // Radio 3: Female.
    
    R3.setToggleGroup(group);

    HBox root = new HBox();
    root.setPadding(new Insets(10));
    root.setSpacing(5);
    root.getChildren().addAll(   R1, R3);
	
	// TODO Auto-generated method stub
	
}

}



