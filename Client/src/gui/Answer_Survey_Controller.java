package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import action.Msg;
import action.Survey;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Answer_Survey_Controller implements ControllerI,Initializable {
public RadioButton R1_1,R1_2 ,R1_3 ,R1_4 ,R1_5 ,R1_6,R1_7,R1_8,R1_9,R1_10;
public RadioButton R2_1,R2_2 ,R2_3 ,R2_4 ,R2_5 ,R2_6,R2_7,R2_8,R2_9,R2_10;
public RadioButton R3_1,R3_2 ,R3_3 ,R3_4 ,R3_5 ,R3_6 ,R3_7 ,R3_8 ,R3_9 ,R3_10 ;
public RadioButton R4_1,R4_2 ,R4_3 ,R4_4 ,R4_5 ,R4_6 ,R4_7,R4_8,R4_9,R4_10;
public RadioButton R5_1,R5_2 ,R5_3 ,R5_4 ,R5_5 ,R5_6 ,R5_7,R5_8,R5_9,R5_10;
public RadioButton R6_1,R6_2 ,R6_3 ,R6_4 ,R6_5 ,R6_6 ,R6_7,R6_8,R6_9,R6_10;
public Button Back_to_main,submit_survey;
public ToggleGroup a1,a2,a3,a4,a5,a6 ;
public Label q1,q2,q3,q4,q5,q6;
public Label survey_id;
public ComboBox<String> users_id;
public static Survey current_survey;
ObservableList<String> list;
 /**
  * need to be 10 radio button 
  */

public void back_to_main(ActionEvent event) throws IOException {


	 Parent menu;
	 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Managment_F.fxml"));
	// to_Client.setController(new Managment_Controller());
	 Scene win1= new Scene(menu);
	 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
	 win_1.setScene(win1);
	 win_1.show();
}

public void form_submit(ActionEvent event) throws IOException {
	int validity_flag = 0;
	 if(users_id.getValue()==null)
	 {
		   Optional<ButtonType> r = Login_win.showPopUp("ERROR", "Message", "You must choose a Customer!", "Thank you!"); 
		   return;
	 }
	
	  /*send answers of the client to DB*/
		if (check_user_form() )
		{
			validity_flag=1;
			 get_customer_answers();
			 update_survey_answers_inDB();
			 set_customerId_in_survey_list();
			 
		}
	
	if(validity_flag==1) {
      
      Optional<ButtonType> result = Login_win.showPopUp("INFORMATION", "Message", "Your answers were submitted - have a GOOD day!", "Thank you!");

      if (result.get() == ButtonType.OK)
      {
    	  Parent menu;
    		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Main_menu_F.fxml"));
    		// to_Client.setController(new Managment_Controller());
    		 Scene win1= new Scene(menu);
    		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
    		 win_1.setScene(win1);
    		 win_1.show();
      	 
      }
	}
	else {
		/*Alert alert = new Alert(AlertType.INFORMATION);
	      alert.setTitle("Message");
	      alert.setContentText("In order to complete the survey to will have to answers all 6 Q");
	      alert.setHeaderText("Invalid fields!");


	      Optional<ButtonType> result = alert.showAndWait();*/
		
	      Optional<ButtonType> result = Login_win.showPopUp("INFORMATION", "Message", "In order to complete the survey to will have to answers all 6 Q", "Invalid fields!");

	      if (result.get() == ButtonType.OK)
	      {
	    	  System.out.println("");
	      }
		
		
	}
      
    
      
      
        
}
/**
 * check if the user did answer all the six Q
 * if not , show Invalid details msg AND mark the Invalid fields in red
 * @return
 */
private boolean check_user_form() {
	
	 boolean answer=true;
	/*check q1*/
	 
	
	if(a1.getSelectedToggle() == null) {
		System.out.println("nothing is selected in q1  ");
		q1.setTextFill(Color.web("#ed0b31"));
		 answer= false;}
	if(a2.getSelectedToggle() == null) {
		System.out.println("nothing is selected in q2  ");
		q2.setTextFill(Color.web("#ed0b31"));
		 answer= false;}
	
	if(a3.getSelectedToggle() == null) {
		System.out.println("nothing is selected in q3 ");
		q3.setTextFill(Color.web("#ed0b31"));
		 answer=false;}
	if(a4.getSelectedToggle() == null) {
		System.out.println("nothing is selected in q4 ");
		q4.setTextFill(Color.web("#ed0b31"));
		 answer= false;}
	if(a5.getSelectedToggle() == null) {
		System.out.println("nothing is selected in q5  ");
		q5.setTextFill(Color.web("#ed0b31"));
		 answer= false;}
	
	if(a6.getSelectedToggle() == null) {
		System.out.println("nothing is selected in q6  ");
		q6.setTextFill(Color.web("#ed0b31"));
		 answer=false;}
	
	return answer;
}

public void get_customer_answers()
{
	 
	
	if(a1.getSelectedToggle() ==R1_1)
	{
		current_survey.setA1(1);
	}
	if(a1.getSelectedToggle() ==R1_2)
	{
		current_survey.setA1(2);
	}
	if(a1.getSelectedToggle() ==R1_3)
	{
		current_survey.setA1(3);
	}
	if(a1.getSelectedToggle() ==R1_4)
	{
		current_survey.setA1(4);
	}
	if(a1.getSelectedToggle() ==R1_5)
	{
		current_survey.setA1(5);
	}
	if(a1.getSelectedToggle() ==R1_6)
	{
		current_survey.setA1(6);
	}
	
	if(a1.getSelectedToggle() ==R1_7)
	{
		current_survey.setA1(7);
	}
	if(a1.getSelectedToggle() ==R1_8)
	{
		current_survey.setA1(8);
	}
	if(a1.getSelectedToggle() ==R1_9)
	{
		current_survey.setA1(9);
	}
	if(a1.getSelectedToggle() ==R1_10)
	{
		current_survey.setA1(10);
	}
	
	System.out.println(current_survey.getA1());
	
	
	if(a2.getSelectedToggle() ==R2_1)
	{
		current_survey.setA2(1);
	}
	if(a2.getSelectedToggle() ==R2_2)
	{
		current_survey.setA2(2);
	}
	if(a2.getSelectedToggle() ==R2_3)
	{
		current_survey.setA2(3);
	}
	if(a2.getSelectedToggle() ==R2_4)
	{
		current_survey.setA2(4);
	}
	if(a2.getSelectedToggle() ==R2_5)
	{
		current_survey.setA2(5);
	}
	if(a2.getSelectedToggle() ==R2_6)
	{
		current_survey.setA2(6);
	}
		
	
	if(a2.getSelectedToggle() ==R2_7)
	{
		current_survey.setA2(7);
	}
	if(a2.getSelectedToggle() ==R2_8)
	{
		current_survey.setA2(8);
	}
	if(a2.getSelectedToggle() ==R2_9)
	{
		current_survey.setA2(9);
	}
	if(a2.getSelectedToggle() ==R2_10)
	{
		current_survey.setA2(10);
	}
 
	System.out.println(current_survey.getA2());
	
	
	
	if(a3.getSelectedToggle() ==R3_1)
	{
		current_survey.setA3(1);
	}
	if(a3.getSelectedToggle() ==R3_2)
	{
		current_survey.setA3(2);
	}
	if(a3.getSelectedToggle() ==R3_3)
	{
		current_survey.setA3(3);
	}
	if(a3.getSelectedToggle() ==R3_4)
	{
		current_survey.setA3(4);
	}
	if(a3.getSelectedToggle() ==R3_5)
	{
		current_survey.setA3(5);
	}
	if(a3.getSelectedToggle() ==R3_6)
	{
		current_survey.setA3(6);
	}
		
	
	

	if(a3.getSelectedToggle() ==R3_7)
	{
		current_survey.setA3(7);
	}
	if(a3.getSelectedToggle() ==R3_8)
	{
		current_survey.setA3(8);
	}
	if(a3.getSelectedToggle() ==R3_9)
	{
		current_survey.setA3(9);
	}
	if(a3.getSelectedToggle() ==R3_10)
	{
		current_survey.setA3(10);
	}
	
	
	System.out.println(current_survey.getA3());
	
	
	
	if(a4.getSelectedToggle() ==R4_1)
	{
		current_survey.setA4(1);
	}
	if(a4.getSelectedToggle() ==R4_2)
	{
		current_survey.setA4(2);
	}
	if(a4.getSelectedToggle() ==R4_3)
	{
		current_survey.setA4(3);
	}
	if(a4.getSelectedToggle() ==R4_4)
	{
		current_survey.setA4(4);
	}
	if(a4.getSelectedToggle() ==R4_5)
	{
		current_survey.setA4(5);
	}
	if(a4.getSelectedToggle() ==R4_6)
	{
		current_survey.setA4(6);
	}
	
	

	if(a4.getSelectedToggle() ==R4_7)
	{
		current_survey.setA4(7);
	}
	if(a4.getSelectedToggle() ==R4_8)
	{
		current_survey.setA4(8);
	}
	if(a4.getSelectedToggle() ==R4_9)
	{
		current_survey.setA4(9);
	}
	if(a4.getSelectedToggle() ==R4_10)
	{
		current_survey.setA4(10);
	}
	
	
	
	
	System.out.println(current_survey.getA4());

	
	if(a5.getSelectedToggle() ==R5_1)
	{
		current_survey.setA5(1);
	}
	if(a5.getSelectedToggle() ==R5_2)
	{
		current_survey.setA5(2);
	}
	if(a5.getSelectedToggle() ==R5_3)
	{
		current_survey.setA5(3);
	}
	if(a5.getSelectedToggle() ==R5_4)
	{
		current_survey.setA5(4);
	}
	if(a5.getSelectedToggle() ==R5_5)
	{
		current_survey.setA5(5);
	}
	if(a5.getSelectedToggle() ==R5_6)
	{
		current_survey.setA5(6);
	}
	
	

	if(a5.getSelectedToggle() ==R5_7)
	{
		current_survey.setA5(7);
	}
	if(a5.getSelectedToggle() ==R5_8)
	{
		current_survey.setA5(8);
	}
	if(a5.getSelectedToggle() ==R5_9)
	{
		current_survey.setA5(9);
	}
	if(a5.getSelectedToggle() ==R5_10)
	{
		current_survey.setA5(10);
	}
	
	
	
	
	
	
	
	
	
	System.out.println(current_survey.getA5());

	
	if(a6.getSelectedToggle() ==R6_1)
	{
		current_survey.setA6(1);
	}
	if(a6.getSelectedToggle() ==R6_2)
	{
		current_survey.setA6(2);
	}
	if(a6.getSelectedToggle() ==R6_3)
	{
		current_survey.setA6(3);
	}
	if(a6.getSelectedToggle() ==R6_4)
	{
		current_survey.setA6(4);
	}
	if(a6.getSelectedToggle() ==R6_5)
	{
		current_survey.setA6(5);
	}
	if(a6.getSelectedToggle() ==R6_6)
	{
		current_survey.setA6(6);
	}
		
	if(a6.getSelectedToggle() ==R6_7)
	{
		current_survey.setA6(7);
	}
	if(a6.getSelectedToggle() ==R6_8)
	{
		current_survey.setA6(8);
	}
	if(a6.getSelectedToggle() ==R6_9)
	{
		current_survey.setA6(9);
	}
	if(a6.getSelectedToggle() ==R6_10)
	{
		current_survey.setA6(10);
	}
	
	
	System.out.println(current_survey.getA6());
 
	
	 
	 
}


public void update_survey_answers_inDB() 
{
	Msg msg= new Msg();
	Survey update_survey=current_survey;
	
	msg.setUpdate();
	msg.setRole("update survey answers");
	msg.setTableName("survey");
	msg.oldO=update_survey;
	Login_win.to_Client.accept(msg);

	
	
	
}
public void get_survey_qustion()
{
	Msg get_survey_q= new Msg();
	get_survey_q.setSelect();
	get_survey_q.setRole("get survey qustion");
	get_survey_q.setTableName("survey");
	Survey survey=new Survey();
	get_survey_q.oldO=survey;
  Login_win.to_Client.accept(get_survey_q);
	
}

public void set_survey_question(Object msg) 
	{
	
	/*set the question from the Db to the user screen s*/
	 Platform.runLater(new Runnable() {
		
		@Override
		public void run() {
			Msg msg1=(Msg) msg;
	Survey survey= new Survey();
	current_survey=(Survey) msg1.newO;
	survey=(Survey) msg1.newO;
	survey_id.setText(survey.getDate());
	q1.setText(survey.getQ1());
	q2.setText(survey.getQ2());
	q3.setText(survey.getQ3());
	q4.setText(survey.getQ4());
	q5.setText(survey.getQ5());
	q6.setText(survey.getQ6()); 
	current_survey=survey;
			
		}
	});
	 

	/*set the radio buttons by groups*/
		 setRadioB();
		 setCustomersId();
	}

public void setCustomersId() {
	Msg msg= new Msg();
	msg.setSelect();
	msg.setRole("get customres id");
	msg.setTableName("person");
	Login_win.to_Client.accept(msg);
	
	
}
/**
 * set the customers id in the comboBox
 */
public void setIdInCombO(Object o)
{
	Msg msg=(Msg) o;
	ArrayList<String>id =(ArrayList<String>) msg.newO;
	list = FXCollections.observableArrayList(id); 
	users_id.setItems(list);
	
}

public void set_customerId_in_survey_list()
{
	Msg msg= new Msg();
	Survey update_survey=current_survey;
	
	msg.setInsert();
	msg.setRole("insert customer id to survey");
	msg.setTableName("comments_survey");
	msg.oldO=update_survey;
	/*also set the customer id to the "already took this survey" list*/
	msg.newO=gui.Login_win.current_user;
	Login_win.to_Client.accept(msg);
 
}

public void setRadioB()
{ 
	  	a1 = new ToggleGroup();
	    RadioButton button1 = R1_1;
	    button1.setToggleGroup(a1);
 	    RadioButton button2 =R1_2;
	    button2.setToggleGroup(a1);
	    RadioButton button3 =R1_3;
	    button3.setToggleGroup(a1);
	    RadioButton button4 =R1_4;
	    button4.setToggleGroup(a1);
	    RadioButton button5 =R1_5;
	    button5.setToggleGroup(a1);
	    RadioButton button6 =R1_6;
	    button6.setToggleGroup(a1);
	    RadioButton button7 =R1_7;
	    button7.setToggleGroup(a1);
	    RadioButton button8 =R1_8;
	    button8.setToggleGroup(a1);
	    RadioButton button9 =R1_9;
	    button9.setToggleGroup(a1);
	    RadioButton button10 =R1_10;
	    button10.setToggleGroup(a1);
	    
	    
	    
	    a2 = new ToggleGroup();
	    RadioButton button2_1 = R2_1;
	    button2_1.setToggleGroup(a2);
 	    RadioButton button2_2 =R2_2;
	    button2_2.setToggleGroup(a2);
	    RadioButton button2_3 =R2_3;
	    button2_3.setToggleGroup(a2);
	    RadioButton button2_4 =R2_4;
	    button2_4.setToggleGroup(a2);
	    RadioButton button2_5 =R2_5;
	    button2_5.setToggleGroup(a2);
	    RadioButton button2_6 =R2_6;
	    button2_6.setToggleGroup(a2);
	    
	    RadioButton button2_7 = R2_7;
	    button2_7.setToggleGroup(a2);
 	    RadioButton button2_8 =R2_8;
 	    button2_8.setToggleGroup(a2);
	    RadioButton button2_9 =R2_9;
	    button2_9.setToggleGroup(a2);
	    RadioButton button2_10 =R2_10;
	    button2_10.setToggleGroup(a2);
	    
	    
	    a3 = new ToggleGroup();
	    RadioButton button3_1 = R3_1;
	    button3_1.setToggleGroup(a3);
 	    RadioButton button3_2 =R3_2;
	    button3_2.setToggleGroup(a3);
	    RadioButton button3_3 =R3_3;
	    button3_3.setToggleGroup(a3);
	    RadioButton button3_4 =R3_4;
	    button3_4.setToggleGroup(a3);
	    RadioButton button3_5 =R3_5;
	    button3_5.setToggleGroup(a3);
	    RadioButton button3_6 =R3_6;
	    button3_6.setToggleGroup(a3);
	    
	    RadioButton button3_7 = R3_7;
	    button3_7.setToggleGroup(a3);
 	    RadioButton button3_8 =R3_8;
 	   button3_8.setToggleGroup(a3);
	    RadioButton button3_9 =R3_9;
	    button3_9.setToggleGroup(a3);
	    RadioButton button3_10 =R3_10;
	    button3_10.setToggleGroup(a3);
	    
	    
	    
	    
	    
	    a4 = new ToggleGroup();
	    RadioButton button4_1 = R4_1;
	    button4_1.setToggleGroup(a4);
 	    RadioButton button4_2 =R4_2;
	    button4_2.setToggleGroup(a4);
	    RadioButton button4_3 =R4_3;
	    button4_3.setToggleGroup(a4);
	    RadioButton button4_4 =R4_4;
	    button4_4.setToggleGroup(a4);
	    RadioButton button4_5 =R4_5;
	    button4_5.setToggleGroup(a4);
	    RadioButton button4_6 =R4_6;
	    button4_6.setToggleGroup(a4);
	    
	    
	    
	    
	    
	    RadioButton button4_7 = R4_7;
	    button4_7.setToggleGroup(a4);
 	    RadioButton button4_8 =R4_8;
 	   button4_8.setToggleGroup(a4);
	    RadioButton button4_9 =R4_9;
	    button4_9.setToggleGroup(a4);
	    RadioButton button4_10 =R4_10;
	    button4_10.setToggleGroup(a4);
	    
	    
	    
	    
	    
	    a5 = new ToggleGroup();
	    RadioButton button5_1 = R5_1;
	    button5_1.setToggleGroup(a5);
 	    RadioButton button5_2 =R5_2;
	    button5_2.setToggleGroup(a5);
	    RadioButton button5_3 =R5_3;
	    button5_3.setToggleGroup(a5);
	    RadioButton button5_4 =R5_4;
	    button5_4.setToggleGroup(a5);
	    RadioButton button5_5 =R5_5;
	    button5_5.setToggleGroup(a5);
	    RadioButton button5_6 =R5_6;
	    button5_6.setToggleGroup(a5);
	    
	    
	    RadioButton button5_7 = R5_7;
	    button5_7.setToggleGroup(a5);
 	    RadioButton button5_8 =R5_8;
 	   button5_8.setToggleGroup(a5);
	    RadioButton button5_9 =R5_9;
	    button5_9.setToggleGroup(a5);
	    RadioButton button5_10 =R5_10;
	    button5_10.setToggleGroup(a5);
	    
	    
	    
	    
	    a6 = new ToggleGroup();
	    RadioButton button6_1 = R6_1;
	    button6_1.setToggleGroup(a6);
 	    RadioButton button6_2 =R6_2;
	    button6_2.setToggleGroup(a6);
	    RadioButton button6_3 =R6_3;
	    button6_3.setToggleGroup(a6);
	    RadioButton button6_4 =R6_4;
	    button6_4.setToggleGroup(a6);
	    RadioButton button6_5 =R6_5;
	    button6_5.setToggleGroup(a6);
	    RadioButton button6_6 =R6_6;
	    button6_6.setToggleGroup(a6);
	    
	    
	    
	    RadioButton button6_7 = R6_7;
	    button6_7.setToggleGroup(a6);
 	    RadioButton button6_8 =R6_8;
 	   button6_8.setToggleGroup(a6);
	    RadioButton button6_9 =R6_9;
	    button6_9.setToggleGroup(a6);
	    RadioButton button6_10 =R6_10;
	    button6_10.setToggleGroup(a6);
	    
	    
	    
	    
	    
}



@Override
public void initialize(URL location, ResourceBundle resources) 
{
	 Login_win.to_Client.setController(this);
	 
	 get_survey_qustion();
	 

}

}





