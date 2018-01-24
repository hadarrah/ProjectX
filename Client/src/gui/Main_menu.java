package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

//import com.jfoenix.controls.JFXButton;

import action.Complain;
import action.Cart;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main_menu implements Initializable, ControllerI {

	public Button mannag_B, back_B, user_profile_B;
	public Label main_label, lblStore;
	public JFXButton tryb;
	public static Person current_user;
	public boolean logout_flag;
	public Button view_catalog_B, cancel_order_B, self_item_B, complain_B;
	public static ActionEvent event_l,event_n;
	public static Cart userCart;
	public Label inCart_L;

	/**Logout button function - get back to login initial window*/
	public void back_logOut(ActionEvent event) throws IOException {
		logout_flag = false;
		lblStore.setVisible(false);
		show_logout_msg();

		/* logout process */

		if (logout_flag == true) {
			Login_win.to_Client.setController(this);
			/* creating and sending a logout msg */
			Msg msg = new Msg();
			Person user_logout = current_user;
			msg.setRole("user logout");
			msg.setTableName("person");
			msg.setUpdate();
			msg.oldO = user_logout;
			Login_win.to_Client.accept(msg);
			/* back to the log_in menu */
			Parent menu;
			menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Login_F.fxml"));
			// to_Client.setController(new Login_win());
			Scene win1 = new Scene(menu);
			 win1.getStylesheets().add(getClass().getResource("/gui/Login.css").toExternalForm());

			Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
			win_1.setScene(win1);
			win_1.show();
		}
	}

	public void catalog(ActionEvent event) throws IOException {

		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "View_Catalog1.fxml"));
		// to_Client.setController(new Managment_Controller());
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}

	public void complain(ActionEvent event) throws IOException {

		/*save the event*/
		event_l =new ActionEvent();		 
		event_l=event.copyFor(event.getSource(), event.getTarget());
		
		 /*prepare msg to server*/
		Msg msg = new Msg();
		msg.setSelect();
		msg.setRole("check if already exist complaint");
		msg.oldO = Login_win.chosen_store;
		msg.newO = Login_win.current_user.getUser_ID();

		Login_win.to_Client.accept((Object) msg);
	}

	public void managment(ActionEvent event) throws IOException {

		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Managment_F.fxml"));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}

	public void self_item(ActionEvent event) throws IOException {

		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Self_Item_F.fxml"));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}

	public void cancel_order(ActionEvent event) throws IOException {
		
		 event_n =new ActionEvent();		 
		 event_n=event.copyFor(event.getSource(), event.getTarget());

		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Active_Orders_F.fxml"));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}

	public void to_user_profile(ActionEvent event) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Profile_F.fxml"));
		Scene win1 = new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("/gui/Profile.css").toExternalForm());

		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}

	/* /* check if the user already took this survey *//*
														   /public void take_survey(ActionEvent event) throws
														   IOException {
														   
														  event_l = new ActionEvent(); event_l =
														   event.copyFor(event.getSource(), event.getTarget());
														   
														   Msg msg = new Msg(); msg.setSelect();
														  msg.setRole("check if user already did this survey");
														   msg.setTableName("comments_survey"); msg.newO = current_user;
														 * msg.oldO = current_survey; Login_win.to_Client.accept(msg);
														 * 
														 * }
														 */
	public void survey_premession(Object o) {
		Msg msg = (Msg) o;

		System.out.println(msg.freeField);

		if (msg.freeField.equals("true")) {

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						move(event_l);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}

		else {

			Platform.runLater(new Runnable() {
				@Override
				public void run() {

					Optional<ButtonType> result = Login_win.showPopUp("ERROR", "Message",
							"Sorry,you already took this survey", "Thank you!");

				}
			});
		}
	}

	public void cart(ActionEvent event) throws IOException {

		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Cart_F.fxml"));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}

	/**Show confirmation msg upon logout*/
	private void show_logout_msg() {

		Optional<ButtonType> option = Login_win.showPopUp("CONFIRMATION", "LogOut",
				"Are you sure you want to Logout ZerLi system?", "");

		if (option.get() == ButtonType.OK)
			logout_flag = true;

		else if (option.get() == ButtonType.CANCEL)
			logout_flag = false;
		if (option.get() == null)
			logout_flag = false;

	}
	
	/**
	 * check if the start date of subscription is ok
	 */
	public void check_start_date_subscription() {
		Msg getCustomer = new Msg();
		getCustomer.setSelect();
		getCustomer.oldO = current_user;
		getCustomer.newO = Login_win.chosen_store;
		getCustomer.setRole("check for start date subscription");
		Login_win.to_Client.accept((Object) getCustomer);
	}
	
	/**
	 * handle with the result of checking the start date of subscription
	 */
	public void get_answer_if_start_date_change(Object msg) 
	{
		boolean answer =  (boolean) (((Msg) msg).newO);
		if(answer)
		{
			/* there are pending complaints -> run in new thread the new window */
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					Login_win.showPopUp("INFORMATION", "Important Message", "Your subscription was reset", "for renew the membership please talk with your store manager");
					return;
				}
			});
		}
	}
	
	/**
	 * handle with the result of checking if there is complaint
	 */
	public void get_answer_of_complaint(Object msg) 
	{
		String answer = (String)((Msg)msg).newO;
		if(answer.equals("No complaint"))
		{
			/* there are pending complaints -> run in new thread the new window */
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					Parent menu;
					try {
						menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Post_Complain_F.fxml"));
						Scene win1 = new Scene(menu);
						Stage win_1 = (Stage) ((Node) (event_l.getSource())).getScene().getWindow();
						win_1.setScene(win1);
						win_1.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		}
		else
		{
			/*the creating was successful -> run in new thread the new window*/
        	Platform.runLater(new Runnable() {
    			
    			@Override
    			public void run() {
			 	    Login_win.showPopUp("INFORMATION", "Message", "You already have a complaint in this store", "Please wait for answer...");
    			 		return;

    			}
    		}); 
		}
	}
	
	/**
	 * check if there is pending complaints 
	 */
	public void check_for_complaints() {
		Msg getCustomer = new Msg();
		getCustomer.setSelect();
		getCustomer.setTableName("complaint");
		getCustomer.setRole("check for pending complaints");
		Login_win.to_Client.accept((Object) getCustomer);
	}
	/**Check if there are items in cart and present label under icon
	 * with the amount of items currently in cart*/
	public void check_items_in_cart() {
		if(Cart_Controller.userCart.selectedItemsArr.size()>0) {
			inCart_L.setVisible(true);
			inCart_L.setText(Cart_Controller.userCart.selectedItemsArr.size()+" items in cart");
		}
			else
			inCart_L.setVisible(false);
	}

	/**
	 * handle with the result if there is pending complaints
	 * @param msg
	 */
	public void get_answer_if_exist_complaint(Object msg) {
		ArrayList<Complain> toCheck = (ArrayList<Complain>) (((Msg) msg).newO);
		if (!toCheck.isEmpty()) {
			/* there are pending complaints -> run in new thread the new window */
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					Login_win.showPopUp("INFORMATION", "Important Message", "There are pending complaints",
							"Please response to the complaints immediately");
					return;
				}
			});
		}
	}

	/**General movement between pages function*/
	public void move(ActionEvent event) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(main.fxmlDir + "Answer_Survey_F.fxml"));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Login_win.to_Client.setController(this);
		userCart = new Cart();
		current_user = gui.Login_win.current_user;
		if (!(Login_win.titelStore == null)) {
			lblStore.setText("Welcome "+Login_win.current_user.getUser_name()+"\n"+Login_win.titelStore);

			lblStore.setVisible(true);
		}
		if (!(current_user.getPrivilege().equals("Customer"))) {
			mannag_B.setVisible(true);
		}

		if (current_user.getPrivilege().equals("Customer Service Employee"))
			check_for_complaints();
		if (current_user.getPrivilege().equals("Customer")) {
			check_start_date_subscription();
		}
		check_items_in_cart();

	}

}
