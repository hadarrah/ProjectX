package entity;

import java.io.*;
import java.util.ArrayList;
import client.*;
import common.*;
import controller.Login_win;
import controller.Menu_controller;
import entity.Msg;

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
	public controller.ControllerI mc;

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
	public ClientConsole(String host, int port, controller.ControllerI login_win) {

		this.mc = login_win;

		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

	/**
	 * Set the current controller for the client
	 */
	public void setController(controller.ControllerI cont) {
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
					((controller.View_Catalog_Controller) mc).initCatalog(message);	
				}
				else if (check.getRole().equals("get user orders history"))
					((controller.Purchase_History_Controller)mc).setUserHistory(message);
				else if (check.getRole().equals("get customres id"))
					((controller.Answer_Survey_Controller)mc).setIdInCombO(message);
			//	else if (check.getRole().equals("get orders id"))
				//	((gui.Cancel_Order_Controller)mc).SetOrdersIds(message);
				else if (check.getRole().equals("verify user details")) {
					((controller.Login_win) mc).get_comfirmation(message);		
				}
				else if(check.getRole().equals("get user active orders"))
					((controller.Active_Orders_Controller)mc).setUserHistory(message);
				else if (check.getRole().equals("check if user already did this survey")) {
					//((gui.Main_menu)mc).survey_premession(message);
				}
				else if (check.getRole().equals("get payment account for personID")) {
					((controller.Order_Controller) mc).get_payment_account(message);
				}
				else if (check.getRole().equals("check if there is active survey for insert")) {
					((controller.Managment_Controller) mc).check_if_survey_active(message);
				}
				else if (check.getRole().equals("check if there is active survey for close")) {
					((controller.Managment_Controller) mc).check_if_survey_active(message);
				}
				else if (check.getRole().equals("check if there is active survey for add comment")) {
					((controller.Managment_Controller) mc).check_if_survey_active(message);
				}
				else if (check.getRole().equals("find items color-type-price")) {
					((controller.SI_Add_Item_Controller) mc).setReturnedItems(message);
				}
 				else if (check.getRole().equals("get survey qustion")) {
					((controller.Answer_Survey_Controller) mc).set_survey_question(message);
				}	
 				else if (check.getRole().equals("get combo colors")) {
					((controller.SI_Add_Item_Controller) mc).setCombo(message);
				}
				else if (check.getRole().equals("get combo type")) {
					((controller.SI_Add_Item_Controller) mc).setCombo(message);
				}
				else if(check.getRole().equals("check if there is active survey for add answer")) {
					 
					 ((controller.Managment_Controller)mc).check_if_survey_active(message);
					}
				else if(check.getRole().equals("get combo survey ID")) {					 
					 ((controller.Add_Conclusion_Controller)mc).setCombo(message);
				}
				else if (check.getRole().equals("get combo all customers")) {
					((controller.Edit_Customer_Profile_Man_Controller) mc).set_combobox(message);
				}
				else if (check.getRole().equals("get combo customer ID for answer complaint")) {
					try {
						((controller.Managment_Controller) mc).check_for_complaint(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("check if there is active sale for insert")) {
					try {
						((controller.Managment_Controller) mc).check_for_sale(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("check if there is active sale for close")) {
					try {
						((controller.Managment_Controller) mc).check_for_sale(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("check for pending complaints")) {
					((controller.Main_menu) mc).get_answer_if_exist_complaint(message);
				}
				else if (check.getRole().equals("get the stores for report")) {
					try {
						((controller.Managment_Controller) mc).get_stores_for_report(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("get the stores for report compare")) {
					try {
						((controller.Managment_Controller) mc).get_stores_for_report(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if (check.getRole().equals("get report for display"))
					((controller.Display_Report_Controller) mc).get_report(message);
				else if (check.getRole().equals("get reports for compare"))
					((controller.Compare_Report_Controller) mc).get_report(message);
				else if (check.getRole().equals("check for start date subscription"))
					((controller.Main_menu) mc).get_answer_if_start_date_change(message);
				else if (check.getRole().equals("get combo customer ID for create payment account"))
					((controller.Managment_Controller) mc).receive_from_server_payment_account(message);
				else if (check.getRole().equals("check if already exist complaint"))
					((controller.Main_menu) mc).get_answer_of_complaint(message);
				
				//else if (check.getRole().equals("check if the user already did this survey")) {
 				//	((gui.Main_menu)mc).survey_premession(message);
				//}
			}
			 if(check.getType().equals("UPDATE"))
			 {
				 if (check.getRole().equals("update amount"))
					 ((controller.View_Catalog_Controller) mc).set_item_amount_success();
				 else if(check.getRole().equals("update refund"))
					 ((controller.Payment_Controller) mc).update_refund_success(message);
				 else if(check.getRole().equals("delete item from catalog"))
					 ((controller.View_Catalog_Controller) mc).delete_item_success();
				 else if (check.getRole().equals("update user details")) 
					  ((controller.Update_Personal_Info_Controller)mc).get_new_user_details(message);
				 else if(check.getRole().equals("update item in catalog"))
					  ((controller.View_Catalog_Controller) mc).update_item_success();
				 else if(check.getRole().equals("close survey"))
					 ((controller.Close_Survey_Controller)mc).close_survey_success(message);
				 else if(check.getRole().equals("set conclusion survey"))
					 ((controller.Add_Conclusion_Controller)mc).update_conclusion_survey_success(message);
				 else if(check.getRole().equals("set edit profile manager"))
					 ((controller.Edit_Customer_Profile_Man_Controller)mc).update_details_success(message);
				 else if(check.getRole().equals("set answer complaint"))
					 ((controller.Answer_Complaint_Controller)mc).update_answer_success(message);
				 else if(check.getRole().equals("close sale"))
					 ((controller.Close_Sale_Controller)mc).close_sale_success(message);
				 else if(check.getRole().equals("change order status"))
					 ((controller.Cancel_Order_Controller)mc).show_cancel_msg(message);
			 }
			else if (check.getType().equals("INSERT")) {
				if (check.getRole().equals("insert a new item in catalog"))
					 ((controller.View_Catalog_Controller) mc).insertNewItemInCatalogSuccess();
				else if (check.getRole().equals("insert survey"))
					((controller.Survey_Controller) mc).create_survey_success(message);
				else if (check.getRole().equals("insert a new complain"))  
					((controller.Post_Complain_Controller)mc).get_submit_approved(message);
				else if (check.getRole().equals("insert new sale"))  
					((controller.Create_Sale_Controller)mc).create_sale_success(message);
				else if(check.getRole().equals("insert order")) 
					((controller.Payment_Controller) mc).create_order_success(message);
				else if(check.getRole().equals("insert delivery")) 
					((controller.Payment_Controller) mc).create_delivery_success(message);
				else if(check.getRole().equals("insert items in order")) 
					((controller.Payment_Controller) mc).insert_items_success(message);
				else if(check.getRole().equals("insert card")) 
					((controller.Payment_Controller) mc).insert_card_success(message);
				else if (check.getRole().equals("insert new payment account")) 
					((controller.Create_PaymentAccount_Controller) mc).check_if_create_success(message);
			}
			
			
		}
		} // else its an update query

		



	}
	// Class methods ***************************************************

// End of ConsoleChat class
