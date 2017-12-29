package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import action.Msg;
import action.Payment_Account;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Create_PaymentAccount_Controller implements Initializable, ControllerI{

    public TextField cc_1_text;
    public TextField cc_2_text;
    public Label invalid_detailsL_credit, invalid_detailsL_subscription, invalid_detailsL_ID;
    public TextField customr_ID_text;
    public Button back_B;
    public ComboBox<String> subscription_combo;
    public TextField cc_3_text;
    public TextField cc_4_text;
    public Button create_paymentAccount_B;
	public ObservableList<String> list;
	public static ActionEvent event_log;

	/**
	 * return to the previous window
	 * @param event
	 * @throws IOException
	 */
    public void back(ActionEvent event) throws IOException {

    	move(event,main.fxmlDir+ "Managment_F.fxml");
    }
    
    /**
     * handle for pressing on "create" button
     * @param event
     */
    public void create_PaymentAccount(ActionEvent event) {

    	/*get the details from input fields*/
    	String ID = customr_ID_text.getText();
    	String CreditCard = cc_1_text.getText() + cc_2_text.getText() + cc_3_text.getText() + cc_4_text.getText();
    	String Subscription = subscription_combo.getValue();
    	
    	/*initialize the error label to be no visible*/
		invalid_detailsL_credit.setVisible(false);
		invalid_detailsL_ID.setVisible(false);
		invalid_detailsL_subscription.setVisible(false);

    	/*save the event*/
    	event_log =new ActionEvent();		 
		event_log=event.copyFor(event.getSource(), event.getTarget());
		 
    	/*check the inputs*/
		if(ID.isEmpty()) 
    	{
    		invalid_detailsL_ID.setVisible(true);
    		return;
    	}
    	if(CreditCard.length()!=16) 
    	{
    		invalid_detailsL_credit.setVisible(true);
    		return;
    	}
    	if(Subscription == null) 
    	{
    		invalid_detailsL_subscription.setVisible(true);
    		return;
    	}
    	
    	/*insert the input from user to instance of Payment Account"*/
    	Payment_Account new_paymentAccount = new Payment_Account(ID, CreditCard, Subscription, "Active");
		Msg check_user_details= new Msg();
		check_user_details.setSelect();
		check_user_details.setTableName("person");
		check_user_details.oldO=new_paymentAccount;
		check_user_details.setRole("check if ID exist and add payment account");
		check_user_details.event=event;
		Login_win.to_Client.accept((Object)check_user_details);
    }

    /**
     * check the return message from server
     * @param message
     */
    public void check_if_create_success(Object message)
    {
    	Payment_Account new_payment_account = (Payment_Account)(((Msg) message).newO);
    	/*check if the create Payment Account was collapsed*/
    	if(new_payment_account == null)
    	{
    		JOptionPane.showMessageDialog(null, "There was a problem when the system try to add this account, please try again...");
    		return;
    	}
    	
    	/*the creating was successful -> run in new thread the new window*/
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
						move(event_log , main.fxmlDir+ "Managment_F.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
			}
		}); 
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
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		 
    	/*set the subscription combobox*/
    	ArrayList<String> al= new ArrayList<String>();
    	al.add("Year");
    	al.add("Month");
    	al.add("Per Order");
	    list = FXCollections.observableArrayList(al); 
	    subscription_combo.setItems(list);
	    
	    /*update the current controller to be management controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    }
		
}
