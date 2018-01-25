package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import entity.Msg;
import entity.Person;
import entity.Survey;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Add_Conclusion_Controller implements Initializable,ControllerI{

	    public Button add_conclusion_B;
	    public TextArea conclusion_Text;
	    public Label participant_L;
	    public ComboBox<String> survey_ID_combo;
	    public Button back_B;
	    public Label date_L, invalid_detailsL_conclusion, invalid_detailsL_conclusion_length;
		public static ActionEvent event_log;
		public ArrayList<Survey> noActive_survey = new ArrayList<Survey>();
		
		/**
		 * handle with pressing "add comment"
		 * @param event
		 */
		public void add_Conclusion(ActionEvent event) {

			/*save the event*/
	    	event_log =new ActionEvent();		 
			event_log=event.copyFor(event.getSource(), event.getTarget());
			
			/*set the error label*/
	    	invalid_detailsL_conclusion.setVisible(false);
	    	invalid_detailsL_conclusion_length.setVisible(false);

			String conclusion = "";
			conclusion = conclusion_Text.getText();
			
			/*check input from user*/
			if(conclusion.length() == 0)
			{
		    	invalid_detailsL_conclusion.setVisible(true);
				return;
			}
			if(conclusion.length() >= 200)
			{
				invalid_detailsL_conclusion_length.setVisible(true);
				return;
			}
			
			/*prepare msg to server*/
			Msg conclusionToSet = new Msg();
			conclusionToSet.setUpdate();
			conclusionToSet.setTableName("survey");
			conclusionToSet.setRole("set conclusion survey");
			conclusionToSet.oldO = conclusion;
			conclusionToSet.freeField = survey_ID_combo.getValue();
			Login_win.to_Client.accept((Object) conclusionToSet);
	    }
    public void back(ActionEvent event) throws IOException {
    
    	 Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Managment_F.fxml"));
		 Scene win1= new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("css/Managment.css").toExternalForm());
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		 
    }
    
    
    public void check_SelecetdID(ActionEvent event) {
    	
    	String survey_seleceted = survey_ID_combo.getValue();
    	
    	for(Survey survey : noActive_survey)
    		if(survey.getID().equals(survey_seleceted))
    		{
    			date_L.setText(survey.getDate());
    	    	participant_L.setText(survey.getNumOfParticipant());
    	    	if(survey.getConclusion()!=null)
    	    		conclusion_Text.setText(survey.getConclusion());
    	    	else
    	    		conclusion_Text.clear();
    		}
    	
    }
    
    
    /**
     * handle when the update comment success in DB
     * @param msg
     */
    public void update_conclusion_survey_success(Object msg)
    {
    	/*the creating was successful -> run in new thread the new window*/
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
				 	    Login_win.showPopUp("INFORMATION", "Message", "Your conclusion was submitted - have a GOOD day!", "Thank you!");
				 		 Parent menu;
						  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Managment_F.fxml"));
						 Scene win1= new Scene(menu);
						 win1.getStylesheets().add(getClass().getResource("css/Managment.css").toExternalForm());
						 Stage win_1= (Stage) ((Node) (event_log.getSource())).getScene().getWindow();
						 win_1.setScene(win1);
						 win_1.show();
				 	    
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
			}
		}); 
    }
   
    /**
     * get customer id for combobox
     */
    public void get_Survey_ID()
    {
    	Msg getSurvey = new Msg();
    	getSurvey.setSelect();
    	getSurvey.setTableName("survey");
    	getSurvey.setRole("get combo survey ID");
		Login_win.to_Client.accept((Object) getSurvey);
    }
   
    /**
     * set the customer id in combobox
     * @param msg
     */
    public void setCombo(Object msg)
    {
    	ArrayList<String> surveyID = new ArrayList<String>();
    	noActive_survey = (ArrayList<Survey>) (((Msg) msg).newO);
    	if(noActive_survey == null)
    	{
	 	    Login_win.showPopUp("ERROR", "Message", "There is no survey to display", "");
	 	    return;
    	}
    	for(Survey survey : noActive_survey)
    	{
    		surveyID.add(survey.getID());
    	}
    	ObservableList<String> list = FXCollections.observableArrayList(surveyID);
    	survey_ID_combo.setItems(list);
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
		 
		  //close window by X button
		 win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  Msg  msg=new Msg();
	      		Person user_logout=Login_win.current_user;
	      		msg.setRole("user logout");
	      		msg.setTableName("person");
	      		msg.setUpdate();
	      		msg.oldO=user_logout;
	      		Login_win.to_Client.accept(msg);
	          }
	      });        
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	/*update the current controller to be management controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	/*set the error label*/
    	invalid_detailsL_conclusion.setVisible(false);
    	invalid_detailsL_conclusion_length.setVisible(false);
    	
    	get_Survey_ID();
	}
}