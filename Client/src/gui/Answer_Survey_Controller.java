package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Answer_Survey_Controller implements ControllerI,Initializable {
public RadioButton R1_1,R1_2;
public Button Back_to_main,submit_survey;



public void back_to_main(ActionEvent event) throws IOException {


	 Parent menu;
	 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Main_menu_F.fxml"));
	// to_Client.setController(new Managment_Controller());
	 Scene win1= new Scene(menu);
	 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
	 win_1.setScene(win1);
	 win_1.show();
}

public void form_submit(ActionEvent event) throws IOException {
	
	  Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Thank you!");
      alert.setContentText("Your answers were submitted - have a GOOD day!");

      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK)
      {
      	System.out.println("after thr con");
      }
        
	
	
}








@Override
public void initialize(URL location, ResourceBundle resources) 
{
	ToggleGroup group = new ToggleGroup();

	RadioButton rb1 = new RadioButton("Size 9");
	rb1.setToggleGroup(group);
	rb1.setSelected(true);

	RadioButton rb2 = new RadioButton("Size 10");
	rb2.setToggleGroup(group);
	 
	RadioButton rb3 = new RadioButton("Size 11");
	rb3.setToggleGroup(group);
	
	rb1.setVisible(true);
	rb2.setVisible(true);
}

}





