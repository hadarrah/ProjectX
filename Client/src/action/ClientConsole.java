package action;

import action.Msg;
import java.io.*;
import java.util.ArrayList;
import client.*;
import common.*;
import gui.Login_win;
import gui.Menu_controller;
import gui.win2_Controller;

//import controllers.*; //Future import

/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 */
public class ClientConsole implements ChatIF {
	/*
	 * public gui.Menu_controller mc; public gui.win2_Controller win2; public
	 * gui.Login_win log; public gui.Main_menu main; public gui.Managment_Controller
	 * managment_c; public gui.Create_PaymentAccount_Controller create_paymentA;
	 * 
	 */

	// Just one main controller.
	public gui.ControllerI mc;

	// Class variables *************************************************
	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;
	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	ChatClient client;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host
	 *            The host to connect to.
	 * @param port
	 *            The port to connect on.
	 */
	public ClientConsole(String host, int port, gui.ControllerI login_win) {

		this.mc = login_win;

		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

	public void setController(gui.ControllerI cont) {
		this.mc = cont;
	}

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler with the Msg(object).
	 */
	public void accept(Object msg) {
	
		try {
			client.handleMessageFromClientUI(msg);
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface. It sends the
	 * results to the Client this method finds out who send the request and sends
	 * back.
	 * 
	 * @throws Exception
	 * 
	 * @param.
	 */
	public void displaytoGUI(Object message) {	
		
	if (message instanceof Msg)
		{
		Msg check = (Msg) message;
			
			if (check.getType().equals("SELECT")) 
				
			{
				
				if(check.getRole().equals("View all catalog items")) {				
					((gui.View_Catalog_Controller) mc).initCatalog(message);	
				}
				else if (check.getRole().equals("get user orders history"))
					((gui.Purchase_History_Controller)mc).setUserHistory(message);
				else if (check.getRole().equals("get customres id"))
					((gui.Answer_Survey_Controller)mc).setIdInCombO(message);
			//	else if (check.getRole().equals("get orders id"))
				//	((gui.Cancel_Order_Controller)mc).SetOrdersIds(message);
				else if (check.getRole().equals("verify user details")) {
					((gui.Login_win) mc).get_comfirmation(message);		
				}
				else if (check.getRole().equals("check if user already did this survey")) {
					//((gui.Main_menu)mc).survey_premession(message);
				}
				else if (check.getRole().equals("check if ID exist and add payment account")) {
					((gui.Create_PaymentAccount_Controller) mc).check_if_create_success(message);
				}
				else if (check.getRole().equals("get payment account for personID")) {
					((gui.Order_Controller) mc).get_payment_account(message);
				}
				else if (check.getRole().equals("check if there is active survey for insert")) {
					((gui.Managment_Controller) mc).check_if_survey_active(message);
				}
				else if (check.getRole().equals("check if there is active survey for close")) {
					((gui.Managment_Controller) mc).check_if_survey_active(message);
				}
				else if (check.getRole().equals("check if there is active survey for add comment")) {
					((gui.Managment_Controller) mc).check_if_survey_active(message);
				}
				else if (check.getRole().equals("find items color-type-price")) {
					((gui.SI_Add_Item_Controller) mc).setReturnedItems(message);
				}
 				else if (check.getRole().equals("get survey qustion")) {
					((gui.Answer_Survey_Controller) mc).set_survey_question(message);
				}	
 				else if (check.getRole().equals("get combo colors")) {
					((gui.SI_Add_Item_Controller) mc).setCombo(message);
				}
				else if (check.getRole().equals("get combo type")) {
					((gui.SI_Add_Item_Controller) mc).setCombo(message);
				}
				else if(check.getRole().equals("get the current survey id")) {
				 
				 ((gui.Managment_Controller)mc).set_current_survey_and_chek_customer_survey(message);
				}
				else if(check.getRole().equals("get combo survey ID")) {					 
					 ((gui.Add_Conclusion_Controller)mc).setCombo(message);
				}
				else if (check.getRole().equals("get combo all customers")) {
					((gui.Edit_Customer_Profile_Man_Controller) mc).set_combobox(message);
				}
				else if (check.getRole().equals("get combo customer ID for answer complaint")) {
					try {
						((gui.Managment_Controller) mc).check_for_complaint(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("check if there is active sale for insert")) {
					try {
						((gui.Managment_Controller) mc).check_for_sale(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("check if there is active sale for close")) {
					try {
						((gui.Managment_Controller) mc).check_for_sale(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("check for pending complaints")) {
					((gui.Main_menu) mc).get_answer_if_exist_complaint(message);
				}
				else if (check.getRole().equals("get the stores for report")) {
					try {
						((gui.Managment_Controller) mc).get_stores_for_report(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("get the stores for report compare")) {
					try {
						((gui.Managment_Controller) mc).get_stores_for_report(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("get report for display")) {
					((gui.Display_Report_Controller) mc).get_report(message);
				}
				else if (check.getRole().equals("get reports for compare")) {
					((gui.Compare_Report_Controller) mc).get_report(message);
				}
				else if (check.getRole().equals("check for start date subscription")) {
					((gui.Main_menu) mc).get_answer_if_start_date_change(message);
				}
				
				//else if (check.getRole().equals("check if the user already did this survey")) {
 				//	((gui.Main_menu)mc).survey_premession(message);
				//}
			}
			 if(check.getType().equals("UPDATE"))
			 {
				 if (check.getRole().equals("update amount"))
					 ((gui.View_Catalog_Controller) mc).set_item_amount_success();
				 else if(check.getRole().equals("delete item from catalog"))
					 ((gui.View_Catalog_Controller) mc).delete_item_success();
				 else if (check.getRole().equals("update user details")) 
					  ((gui.Update_Personal_Info_Controller)mc).get_new_user_details(message);
				 else if(check.getRole().equals("update item in catalog"))
						((gui.View_Catalog_Controller) mc).update_item_success();
				 else if(check.getRole().equals("close survey"))
					 ((gui.Close_Survey_Controller)mc).close_survey_success(message);
				 else if(check.getRole().equals("set conclusion survey"))
					 ((gui.Add_Conclusion_Controller)mc).update_conclusion_survey_success(message);
				 else if(check.getRole().equals("set edit profile manager"))
					 ((gui.Edit_Customer_Profile_Man_Controller)mc).update_details_success(message);
				 else if(check.getRole().equals("set answer complaint"))
					 ((gui.Answer_Complaint_Controller)mc).update_answer_success(message);
				 else if(check.getRole().equals("close sale"))
					 ((gui.Close_Sale_Controller)mc).close_sale_success(message);
				 else if(check.getRole().equals("change order status"))
					 ((gui.Cancel_Order_Controller)mc).show_cancel_msg(message);
			 }
			else if (check.getType().equals("INSERT")) {
				if (check.getRole().equals("insert a new item in catalog"))
					 ((gui.View_Catalog_Controller) mc).insertNewItemInCatalogSuccess();
				else if (check.getRole().equals("insert survey"))
					((gui.Survey_Controller) mc).create_survey_success(message);
				else if (check.getRole().equals("insert a new complain"))  
						((gui.Post_Complain_Controller)mc).get_submit_approved(message);
				else if (check.getRole().equals("insert new sale"))  
					((gui.Create_Sale_Controller)mc).create_sale_success(message);
				else if(check.getRole().equals("insert order")) 
					((gui.Order_Controller) mc).create_order_success(message);
				else if(check.getRole().equals("insert delivery")) 
					((gui.Order_Controller) mc).create_delivery_success(message);
				else if(check.getRole().equals("insert items in order")) 
					((gui.Order_Controller) mc).insert_items_success(message);
				else if(check.getRole().equals("insert card")) 
				((gui.Order_Controller) mc).insert_card_success(message);
				
			}
			
			
		}
		} // else its an update query

		



	}
	// Class methods ***************************************************

// End of ConsoleChat class
