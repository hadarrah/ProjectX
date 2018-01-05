package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import action.Msg;
import action.Person;
import action.Survey;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main_menu  implements Initializable,ControllerI{

	public ColorPicker color_PICK;
	public Button mannag_B,back_B,user_profile_B;
	public Label main_label;
	public static Person current_user;
	public   boolean logout_flag;
	public Button view_catalog_B,cancel_order_B,self_item_B,complain_B,take_survey_B;
	public static Survey current_survey;
	public static ActionEvent event_l;
	
	//public  static action.ClientConsole to_Client;

 
	public void back_logOut(ActionEvent event) throws IOException
	{
		logout_flag=false;
		show_logout_msg();
			
		/*logout process*/
	
		if(logout_flag==true)
		{
		Login_win.to_Client.setController(this);
		/*creating and sending a logout msg*/
		Msg  msg=new Msg();
		Person user_logout=current_user;
		msg.setRole("user logout");
		msg.setTableName("person");
		msg.setUpdate();
		msg.oldO=user_logout;
		Login_win.to_Client.accept(msg);
		/*back to the log_in menu*/
		 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Login_F.fxml"));
	//	 to_Client.setController(new Login_win());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		}
	}
	 public void catalog(ActionEvent event) throws IOException {
	    	
    	 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "View_Catalog.fxml"));
		// to_Client.setController(new Managment_Controller());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);		 
		 win_1.show();
	 }
	
	 
	 public void complain(ActionEvent event) throws IOException {
	    	
 	 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Post_Complain_F.fxml"));
		// to_Client.setController(new Managment_Controller());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);		 
		 win_1.show();
	 }
    public void managment(ActionEvent event) throws IOException {
    	
    	 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Managment_F.fxml"));
		// to_Client.setController(new Managment_Controller());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
    }
    
    public void self_item(ActionEvent event) throws IOException {
    	
      	 Parent menu;
   		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Self_Item_F.fxml"));
   		 Scene win1= new Scene(menu);
   		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
   		 win_1.setScene(win1);
   		 win_1.show();
      }
    
    
    public void to_user_profile(ActionEvent event)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+"Profile_F.fxml"));
		//  to_Client.setController(new Main_menu());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
    

    public void take_survey(ActionEvent event) throws IOException
    {
    		
    		event_l=new ActionEvent();		 
    		event_l=event.copyFor(event.getSource(), event.getTarget());
    		/*check if the user already took this survey*/
    		Msg msg= new Msg();
    		msg.setSelect();
    		msg.setRole("check if user already did this survey");	 
    		msg.setTableName("comments_survey");
    		msg.newO=current_user;
    		msg.oldO=current_survey;
    	 	 Login_win.to_Client.accept(msg);
	
    }
    
     public void survey_premession(Object o)
     {
    	 Msg msg=(Msg) o;
    	 
    	 System.out.println(msg.freeField);
    	 
    	 if(msg.freeField.equals("true")) {
    	  
    	 Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					move(event_l);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    	 }
    	 
    	 else {
    		 
        	 Platform.runLater(new Runnable() {
    			@Override
    			public void run() {
    				// TODO Auto-generated method stub
    				 
    		    		 Optional<ButtonType> result = Login_win.showPopUp("ERROR", "Message", "Sorry,you already took this survey", "Thank you!");

    				 
    			}
    		});
    	 }
     }
    
    
    
    public void cart(ActionEvent event) throws IOException
     {
    	
   	 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Cart_F.fxml"));
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
   }
    
    private void show_logout_msg() 
    {
        
	      Optional<ButtonType> option = Login_win.showPopUp("CONFIRMATION", "LogOut", "Are you sure you want to Logout ZerLi system?", "");

         if (option.get() == ButtonType.OK)
         logout_flag=true;
         
         else if (option.get() == ButtonType.CANCEL)  
       	 logout_flag=false;
         if (option.get() == null)
             logout_flag=false;

    }
    
    public void set_current_survey_and_chek_customer_survey(Object o)
    {
    Msg msg=(Msg) o;
   	Survey survey=(Survey) msg.newO;
   	current_survey=survey;
   	System.out.println(current_survey.getID());
    
  
    }
 
    public void move(ActionEvent event)throws IOException 
	{
		 Parent menu;
		 menu = FXMLLoader.load(getClass().getResource(main.fxmlDir+ "Answer_Survey_F.fxml"));
		// to_Client.setController(new Managment_Controller());
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		 
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Login_win.to_Client.setController(this);
		current_user=gui.Login_win.current_user;
		if(!(current_user.getPrivilege().equals("Customer")))
		{
			mannag_B.setVisible(true);
		}
		Msg msg= new Msg();
		msg.setSelect();
		msg.setRole("get the current survey id");
		msg.setTableName("survey");
	 	Login_win.to_Client.accept(msg);
		 
		
		 
	}

}
