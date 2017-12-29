package gui;
import action.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
import action.ClientConsole;
import action.Msg;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Login_win  implements ControllerI,Initializable  {
	public static String user_name;
	public static String user_pass;
	public Button Blogin,Bmanngment,Bquit;
	public TextField user_nameT,user_passT;
	public Label invalid_detailsL;
	public Label welcomeL,already_conL,user_not_existL;
	public static int login_counter=0;
	public static action.ClientConsole to_Client;
	public static Person current_user;
	public static ActionEvent event_log;
	 
	 
	
	 
	
	public void hit_login(ActionEvent event) throws IOException, InterruptedException
	{
		 
		 event_log =new ActionEvent();		 
		 event_log=event.copyFor(event.getSource(), event.getTarget());
		// System.out.println("1rs "+event_log.getSource() );

		/**check if the details that were entered is valid*/
		if(user_nameT.getText().equals("") ||user_passT.getText().equals("") )
		{
			invalid_detailsL.setVisible(true);
			login_counter++;
			if(login_counter>2)//user had tried 3 times
			{
				System.exit(1);
			}
		}
		/*user details are valid -> check if the user exist in the system*/
		/*set the user online- */
		
		Msg check_user_details= new Msg();
		Person user= new Person(user_nameT.getText(),null,user_passT.getText());
		check_user_details.setSelect();
		check_user_details.setTableName("person");
		check_user_details.oldO=user;
		check_user_details.setRole("verify user details");
		check_user_details.event=event;
		to_Client.accept((Object)check_user_details);	
	}
	
	
	public void get_comfirmation(Object obj) 
	{
	 		Msg msg=(Msg) obj;
		Person user=(Person) msg.newO;
		if(user.isAlreadyConnected()==true)
			already_conL.setVisible(true);
		 
	 
		if(user.getIsExist().equals("0"))
			user_not_existL.setVisible(true);
			
			
		  if(user.getIsExist().equals("1") && user.getIsOnline().equals("1")&& !(user.isAlreadyConnected()==true))
		  {
			  user_not_existL.setVisible(false);
			  welcomeL.setVisible(true);
			 
			  /*save the details on the entered user */
			  current_user=user;				   
			   /*opne the main menu window*/
			   Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					 	try {
							move(event_log);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					
				}
			}); 
				 
			  
			     
		  }
	 
	}
	     
	public void move(ActionEvent event)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource("Main_menu_F.fxml"));
		//  to_Client.setController(new Main_menu());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event_log.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
	
	
	
 
	
	public void Quit_app()
	 {
		 /*saving data */
		 System.exit(0); 
	 }
	
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientConsole toClient = new ClientConsole(gui.main.user_host,5555,this);
		to_Client=toClient;
		
		// TODO Auto-generated method stub
		
	}


 


	 
}
