package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import entity.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Login_win  implements ControllerI,Initializable  {
	public static String user_name;
	public static String user_pass;
	public Button Blogin,Bmanngment,Bquit, BOK, Bconnect;
	public TextField user_IdT,user_passT, destination_IdT;
	public Label invalid_detailsL, invalid_IPL;
	public Label welcomeL,already_conL,user_not_existL, must_storeL;
	public ComboBox<String> cbxStore;
	public static int login_counter=0;
	public static entity.ClientConsole to_Client;
	public static Person current_user;
	public static Payment_Account current_user_pay_account;
	public static ActionEvent event_log;
	public static String chosen_store;
	public static String titelStore;
	 
	
	/**
	 * Login button action function
	 * @param event
	 * @throws IOException, InterruptedException
	 */
	public void hit_login(ActionEvent event) throws IOException, InterruptedException
	{
		chosen_store=null;
		titelStore=null;
		/*saves the login button event*/	 
		 event_log =new ActionEvent();		 
		 event_log=event.copyFor(event.getSource(), event.getTarget());
	
		/**check if the details that were entered is valid*/
		if(user_IdT.getText().equals("") ||user_passT.getText().equals("") )
		{
			invalid_detailsL.setVisible(true);
			 return;
		}
		/*user details are valid -> check if the user exist in the system*/
		/*set the user online- */
		
		Msg check_user_details= new Msg();
		Person user= new Person(user_IdT.getText(),user_passT.getText());
		check_user_details.setSelect();
		check_user_details.setTableName("person");
		check_user_details.oldO=user;
		check_user_details.setRole("verify user details");
		check_user_details.event=event;
		to_Client.accept((Object)check_user_details);	
	}
	
	
	/**
	 * Connect button action function
	 * @param event
	 */
	public void hit_connect(ActionEvent event)
	{
		if(destination_IdT.getText().equals(""))
		{
			invalid_IPL.setVisible(true);
			return;
		}
		invalid_IPL.setVisible(false);
		controller.main.user_host = destination_IdT.getText();
		ClientConsole toClient = new ClientConsole(controller.main.user_host,5555,this);
		to_Client=toClient;
		
		Blogin.setVisible(true);
		user_IdT.setVisible(true);
		user_passT.setVisible(true);
		Bconnect.setVisible(false);
		destination_IdT.setVisible(false);
		return;
	}
	
	/**
	 * Confirm user details in the DB
	 * @param obj
	 */
	public void get_comfirmation(Object obj) 
	{	

		Msg msg=(Msg) obj;
		Person user=(Person) msg.newO;	
		
		if(user.isAlreadyConnected()==true) {
			already_conL.setVisible(true);
			user_not_existL.setVisible(false);
			return;
		}
			
		else if(user.getIsExist().equals("0")) {
			user_not_existL.setVisible(true);
			already_conL.setVisible(false);
			return;
		}
			
		else if(user.getIsExist().equals("1") && user.getIsOnline().equals("1")&& !(user.isAlreadyConnected()==true))
		  {
			  user_not_existL.setVisible(false);			  
				already_conL.setVisible(false);				
				ObservableList<String> list = FXCollections.observableArrayList(user.getStore());
				cbxStore.setItems(list);		
				current_user_pay_account=(Payment_Account)msg.oldO;
			  /*save the details on the entered user*/
			  current_user=user;
			
			  /*choosing store option*/
			  if (user.getStore().size()>1)
				{	
				  	cbxStore.setVisible(true);				  
					BOK.setVisible(true);					
					Blogin.setVisible(false);
					while(cbxStore.getSelectionModel().getSelectedItem()==null)
					{
						BOK.setDisable(true);
					}
					BOK.setDisable(false);
					titelStore=cbxStore.getValue();
					chosen_store=cbxStore.getValue().substring(cbxStore.getValue().lastIndexOf("-")+1);
									
				}
			  
			  /*if user has one payment account, set it automatically*/
			  else if(user.getStore().size()==1){
				  String s=user.getStore().get(0);
				 // System.out.println(s);
				  titelStore=s;
				  System.out.println(titelStore);
				  chosen_store=s.substring(s.lastIndexOf("-")+1);
				//  System.out.println(chosen_store);
				 
			   /**open the main menu window**/			 
			   Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					 	try {
					 		 welcomeL.setVisible(true);
							move(event_log);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					
				}
			}); 	  
			  }
			  /*user does'nt have a payment account*/
			  else {
				  Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							 	try {
							 		 welcomeL.setVisible(true);
									move(event_log);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  							
						}
					}); 	  
			  }
		  }
	 
	}
	
	/**open the next window after choosing a store**/
	public void Ok(ActionEvent event)throws IOException 
	{
		 Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					 	try {
					 		 welcomeL.setVisible(true);
							move(event_log);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 					
				}
			}); 	  
	}
	
	/**
	 * General function for the movement between the different windows
	 * 
	 * @param event
	 * @param next_fxml
	 *            = string of the specific fxml
	 * @throws IOException
	 */   
	public void move(ActionEvent event)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Main_menu_F.fxml"));
		//  to_Client.setController(new Main_menu());
		 Scene win1= new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("css/Main_menu.css").toExternalForm());

		 Stage win_1= (Stage) ((Node) (event_log.getSource())).getScene().getWindow();
		 win_1.setResizable(false);
		 win_1.setScene(win1);
		 win_1.show();
		 //close window by X button
		 win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  Msg  msg=new Msg();
	      		Person user_logout=current_user;
	      		msg.setRole("user logout");
	      		msg.setTableName("person");
	      		msg.setUpdate();
	      		msg.oldO=user_logout;
	      		Login_win.to_Client.accept(msg);
	          }
	      });        
	}
	
	/**
	 * Static pop-up function which can be called from every fxml following login
	 * 
	 * @param typeOfPopUpString
	 * @param title
	 * @param header
	 * @param content
	 */
	public static Optional<ButtonType> showPopUp(String typeOfPopUpString, String title, String header, String content)
	{
		Alert alert = null;
		
		
		switch(typeOfPopUpString)
		{
		case "CONFIRMATION":
			alert = new Alert(AlertType.CONFIRMATION);
			break;
		case "INFORMATION":
			alert = new Alert(AlertType.INFORMATION);
			break;
		case "ERROR":
			alert = new Alert(AlertType.ERROR);
			break;
		}
		
		if(!title.isEmpty())
			alert.setTitle(title);
		if(!header.isEmpty())
			alert.setHeaderText(header);
		if(!content.isEmpty())
			alert.setContentText(content);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		return result;
	}
 
	/**Quit button action function*/
	public void Quit_app()
	 {
		 /*saving data */
		 System.exit(0); 
	 }
	
 /**
  * Initialize login: creating a new client, saves the ref of this gui screen
  */
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		if(to_Client == null)	//new connection
		{
			Blogin.setVisible(false);
			user_IdT.setVisible(false);
			user_passT.setVisible(false);
		}
		else	//connection exist
		{
	    	to_Client.setController(this);
			Blogin.setVisible(true);
			user_IdT.setVisible(true);
			user_passT.setVisible(true);
			Bconnect.setVisible(false);
			destination_IdT.setVisible(false);
		}
	}
}
