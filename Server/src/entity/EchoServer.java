package entity;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	public static String user_pass;
	public static String table_name;
	public static String schema_name;
	public static String user_name;
 
	//protected static Logger logger = Logger.getLogger("MyLog");
	protected FileHandler fh;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port
	 *            The port number to connect on.
	 */
	@SuppressWarnings("deprecation")
	public EchoServer(int port) {
		super(port);
		/* adding a log file */
		/*
		try {
			fh = new FileHandler("Zerli-LogFile.log", true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException e) {
			 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		 */
	 
		 
	/*	user_name = JOptionPane.showInputDialog("Enter User name  ");
		if (user_name.equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid name");
			System.exit(0);
		}
		user_pass = JOptionPane.showInputDialog("Enter Password  ");
		schema_name = JOptionPane.showInputDialog("Enter Schema Name ");*/

	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client. This method check
	 * which kind of query arrived from client
	 * 
	 * @param msg
	 *            The message received from the client.
	 * @param client
	 *            The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		Msg msg1 = (Msg) msg;
		String query_type = msg1.getType();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/" + schema_name, user_name,
					user_pass);
			//logger.info("SQL connection succeed");
			System.out.println("SQL connection succeed");
			/* Define which kind the message the server got */
			/**
			 * first find the kind of the query then, check the role of the msg (the role is
			 * a simple short string)
			 */
			switch (query_type) {
			case "SELECT": {
				
				if (msg1.getRole().equals("View all catalog items"))
					ViewItems(msg1, conn, client);
				else if (msg1.getRole().equals("check if user already did this survey")) {
					// check_if_user_took_this_survey(msg1, conn, client);
				}
				else if (msg1.getRole().equals("verify user details"))
					check_user_details(msg1, conn, client);
				else if(msg1.getRole().equals("get user active orders"))
					get_user_active_orders(msg1, conn, client);
				else if (msg1.getRole().equals("get user orders history"))
					get_user_orders_history(msg1,conn,client);
				else if (msg1.getRole().equals("get payment account for personID"))
					get_payment_account(msg1, conn, client);
				else if (msg1.getRole().equals("check if there is active survey for insert"))
					check_survey_exist(msg1, conn, client);
				else if (msg1.getRole().equals("check if there is active survey for close"))
					check_survey_exist(msg1, conn, client);
				else if (msg1.getRole().equals("check if there is active survey for add answer"))
					check_survey_exist(msg1, conn, client);
				else if (msg1.getRole().equals("check if there is active survey"))
					check_survey_exist(msg1, conn, client);
				else if (msg1.getRole().equals("find items color-type-price"))
					SelectItemsCTP(msg1, conn, client);
				else if (msg1.getRole().equals("get survey qustion"))
					get_survey_question(msg1, conn, client);
				else if (msg1.getRole().equals("get combo colors"))
					GetComboForSelfItem(msg1, conn, client);
				else if (msg1.getRole().equals("get combo type"))
					GetComboForSelfItem(msg1, conn, client);
				else if (msg1.getRole().equals("get combo customer ID"))
					GetComboForAddComment(msg1, conn, client);
				else if (msg1.getRole().equals("get combo survey ID"))
					GetComboForAddConclusion(msg1, conn, client);
				else if (msg1.getRole().equals("get combo survey ID"))
					GetComboForAddConclusion(msg1, conn, client);
				else if (msg1.getRole().equals("get combo all customers"))
					GetComboForEditManProfile(msg1, conn, client);
				else if (msg1.getRole().equals("get the current survey id"))
					get_the_current_survey_id(msg1, conn, client);
				else if (msg1.getRole().equals("get combo customer ID for answer complaint"))
					GetComboForAnsComplaint(msg1, conn, client);
				else if (msg1.getRole().equals("check for pending complaints"))
					GetComboForAnsComplaint(msg1, conn, client);
				else if (msg1.getRole().equals("check if there is active sale for insert"))
					CheckForActiveSale(msg1, conn, client);
				else if (msg1.getRole().equals("check if there is active sale for close"))
					CheckForActiveSale(msg1, conn, client);
				else if (msg1.getRole().equals("get orders id"))
					get_user_order(msg1, conn, client);
				else if (msg1.getRole().equals("get customres id"))
					get_customres_id(msg1, conn, client);
				else if (msg1.getRole().equals("get the stores for report"))
					get_stores_id(msg1, conn, client);
				else if (msg1.getRole().equals("get the stores for report compare"))
					get_stores_id(msg1, conn, client);
				else if (msg1.getRole().equals("get report for display"))
					get_report_to_display(msg1, conn, client);
				else if (msg1.getRole().equals("get reports for compare"))
					get_report_to_display(msg1, conn, client);
				else if (msg1.getRole().equals("check for start date subscription"))
					check_start_date_paymentAccount(msg1, conn, client);
				else if (msg1.getRole().equals("get combo customer ID for create payment account"))
					get_customerID_for_payment_account(msg1, conn, client);
				else if (msg1.getRole().equals("check if already exist complaint"))
					check_complaint_exist(msg1, conn, client);
			}
			case "UPDATE": {
				// System.out.println("in server- update case: "+msg1.getRole());
				 if (msg1.getRole().equals("update amount"))
					updateAmount(msg1, conn, client);
				 else if(msg1.getRole().equals("update refund"))
					 updateRefund(msg1,conn,client);
				else if (msg1.getRole().equals("delete item from catalog"))
					DeleteItem(msg1, conn, client);
				else if (msg1.getRole().equals("update item in catalog"))
					UpdateItem(msg1, conn, client);
				else if (msg1.getRole().equals("user logout"))
					change_online_status(msg1, conn, "0");
				else if (msg1.getRole().equals("update user details"))
					Update_user_details(msg1, conn, client);
				else if (msg1.getRole().equals("close survey"))
					Close_survey(msg1, conn, client);
				else if (msg1.getRole().equals("update survey answers"))
					update_survey_answers(msg1, conn, client);
				else if (msg1.getRole().equals("set conclusion survey"))
					update_conclusion_survey(msg1, conn, client);
				else if (msg1.getRole().equals("set edit profile manager"))
					update_profile_by_manager(msg1, conn, client);
				else if (msg1.getRole().equals("set answer complaint"))
					update_answer_complain(msg1, conn, client);
				else if (msg1.getRole().equals("close sale"))
					close_sale(msg1, conn, client);
				else if (msg1.getRole().equals("change order status"))
					change_order_status(msg1, conn, client);
			}

			case "INSERT": {
				
				if (msg1.getRole().equals("insert a new item in catalog"))
					insertNewItemInCatalog(msg1, conn, client);
				else if (msg1.getRole().equals("insert survey"))
					insert_survey(msg1, conn, client);
				else if (msg1.getRole().equals("insert card"))
					insert_card(msg1, conn, client);
				else if (msg1.getRole().equals("insert customer id to survey")) {
					// set_customer_in_survey_answered(msg1, conn, client);
				}

				else if (msg1.getRole().equals("insert a new complain"))
					insert_new_complain(msg1, conn, client);
				else if (msg1.getRole().equals("insert new sale"))
					insert_new_sale(msg1, conn, client);
				else if (msg1.getRole().equals("insert order")) 
					insert_order(msg1, conn, client);
				else if (msg1.getRole().equals("insert items in order"))
					insert_items_in_order(msg1, conn, client);
				else if (msg1.getRole().equals("insert delivery"))
					insert_delivery(msg1, conn, client);
				else if (msg1.getRole().equals("insert new payment account"))
					insert_paymentAccount(msg1, conn, client);
			}
			}// end switch
		} // end try

		catch (SQLException ex) {
			/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception ex) {
			/* handle the errort */
		}

	}
	
	/**
	 * Get active orders per customer and his paying account.
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static  void get_user_active_orders(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		Person cur_p = (Person) msg.oldO;
		ArrayList<Order> orders_history = new ArrayList<Order>();

		try {
			/* Building the query */

			PreparedStatement ps = conn.prepareStatement(" SELECT * FROM  orders  where Person_ID=? and Status='Active' and Store_ID=?;");
			ps.setString(1, cur_p.getUser_ID());
			ps.setString(2, msg.freeField);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order temp = new Order();
				temp.setId(rs.getString(1));
				temp.setDelivery1(rs.getString(3));
				temp.setStatus(rs.getString(4));
				temp.setPayment(rs.getString(5));
				temp.setTotprice(rs.getFloat(6));
				temp.setStoreid(rs.getString(7));
				temp.setCreatetime(rs.getString(8));
				temp.setCreatedate(rs.getString(9));
				temp.setRequesttime(rs.getString(10));
				temp.setRequestdate(rs.getString(11));

				orders_history.add(temp);

			}
			msg1.newO = orders_history;

			client.sendToClient(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
 

	/**
	 * update item amount in DB
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static  void updateAmount(Msg msg1, Connection conn, ConnectionToClient client) throws IOException {
		Msg msg = (Msg) msg1;
		Item_In_Catalog itc=(Item_In_Catalog) msg.newO;	
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE store SET Amount=? WHERE ID=? AND Item_ID=?");
			ps.setString(1, ""+itc.getAmount());
			ps.setString(2, msg.freeField);
			ps.setString(3, itc.getID());
			ps.executeUpdate();
			client.sendToClient(msg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * update refund for customer
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static void updateRefund(Msg msg1, Connection conn, ConnectionToClient client) throws IOException {
		Msg msg = (Msg) msg1;
		Payment_Account acc = (Payment_Account)msg.oldO;
		String newRefund = (String)msg.newO;
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE payment_account" +
						" SET Refund=? WHERE ID=? AND Store_ID=?");
			ps.setString(1, newRefund);
			ps.setString(2, acc.getID());
			ps.setString(3, acc.getStoreID());
			ps.executeUpdate();
			
			msg.freeField=newRefund;
			
			client.sendToClient(msg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
		/**
	 * get the user order history including orders that were canceled
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static  void get_user_orders_history(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		Person cur_p = (Person) msg.oldO;
		ArrayList<Order> orders_history = new ArrayList<Order>();

		try {
			/** Building the query */

			PreparedStatement ps = conn.prepareStatement(" SELECT * FROM  orders  where Person_ID=? ; ");
			ps.setString(1, cur_p.getUser_ID());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order temp = new Order();
				temp.setId(rs.getString(1));
				temp.setDelivery1(rs.getString(3));
				temp.setStatus(rs.getString(4));
				temp.setPayment(rs.getString(5));
				temp.setTotprice(rs.getFloat(6));
				temp.setStoreid(rs.getString(7));
				temp.setCreatetime(rs.getString(8));
				temp.setCreatedate(rs.getString(9));
				temp.setRequesttime(rs.getString(10));
				temp.setRequestdate(rs.getString(11));

				orders_history.add(temp);

			}
			msg1.newO = orders_history;

			client.sendToClient(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * insert new item to item_in_catalog table and create item id by max value in the table
	 * @param msg1
	 * @param conn
	 * @param client
	 **/
	public static void insertNewItemInCatalog(Msg msg1, Connection conn, ConnectionToClient client) {		
		Msg msg = (Msg) msg1;
		
		Item_In_Catalog tmp = (Item_In_Catalog) msg1.newO;
		PreparedStatement ps,ps1;
		ResultSet rs,rs1;
		int new_id;
		try {
			/* get the last ID of sale */
			ps = conn.prepareStatement("SELECT max(ID) FROM item_in_catalog;");
			rs = ps.executeQuery();
			rs.next();

			/* execute the insert query */
			ps = conn.prepareStatement("INSERT INTO item_in_catalog (ID, Name, Price, Description, Status)" + " VALUES (?, ?, ?, ?, ?)");
			new_id = Integer.parseInt(rs.getString(1)) + 1;
			
			ps.setString(1, "" + new_id); // insert the last id + 1
			ps.setString(2, tmp.getName());
			ps.setString(3, ""+tmp.getPrice());
			ps.setString(4, tmp.getDescription());
			ps.setString(5, "Active");
			ps.executeUpdate();			
			tmp.setID(""+new_id);
			tmp.getImage().setFileName(tmp.getID()+".jpg");
			CreateImage(tmp.getImage());
			System.out.println(tmp.getImage());
			msg.newO = tmp;
			
			ps=conn.prepareStatement("SELECT * FROM store GROUP BY ID");
			rs=ps.executeQuery();
			while(rs.next()) {
				ps1 = conn.prepareStatement("INSERT INTO store (ID, Location, Open_Hours, Manager_ID, Item_ID, Type, Amount)" + " VALUES (?, ?, ?, ?, ?,'Catalog',0)");
				ps1.setString(1, rs.getString(1));
				ps1.setString(2, rs.getString(2));
				ps1.setString(3, rs.getString(3));
				ps1.setString(4, rs.getString(4));
				ps1.setString(5, tmp.getID());				
				ps1.executeUpdate();			
			}
			client.sendToClient(msg);			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert card to customer's order
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static void insert_card(Msg msg1, Connection conn, ConnectionToClient client) {

		String oid = (String) msg1.freeField;
		String type = (String) msg1.oldO;
		String text = (String) msg1.newO;
		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = conn.prepareStatement("INSERT INTO card (Order_ID, Type, Text)" + " VALUES (?, ?, ?);");

			ps.setString(1, oid);
			ps.setString(2, type);
			ps.setString(3, text);

			ps.executeUpdate();

			msg1.num1 = 1;
			client.sendToClient((Msg) msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get the Customers id who didnt took this survey yet and they have a payment account in this store
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void get_customres_id(Msg msg1, Connection conn, ConnectionToClient client) {
		Msg msg = (Msg) msg1;
		ArrayList<String> id = new ArrayList<String>();
		ArrayList<String> account_id = new ArrayList<String>();
		String survey = (String)msg.oldO;
		String temp;

		try {
			/** Building the query */

			PreparedStatement ps = conn.prepareStatement(" SELECT ID "
					+ "FROM person where Privilege='Customer' and ID not in ( SELECT  Customer_ID FROM comments_survey WHERE ID=?);");
			ps.setString(1, survey);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				temp = rs.getString(1);
				id.add(temp);

			}
			
			PreparedStatement ps2 = conn.prepareStatement(" SELECT ID FROM payment_account where Store_ID=?;");
			ps2.setString(1, msg.freeField);
			ResultSet rs2 = ps2.executeQuery();
			
			while (rs2.next()) {
				temp = rs2.getString(1);
				if(id.contains(temp))
					account_id.add(temp);
			}

			msg.newO = account_id;
			
			client.sendToClient(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * get the Customers id for create payment account
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void get_customerID_for_payment_account(Msg msg1, Connection conn, ConnectionToClient client) {
		ArrayList<String> customers = new ArrayList<String>();
		String store = (String)msg1.oldO;
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			/** Building the query */

			 ps = conn.prepareStatement("SELECT * FROM payment_account WHERE Store_ID != ?;");
			 ps.setString(1, store);
			 rs = ps.executeQuery();
			 while(rs.next())
				 if(!customers.contains(rs.getString("ID")))
					 customers.add(rs.getString("ID"));
			 
			 /*remove customer with many stores that include our relevant store*/
			 ps = conn.prepareStatement("SELECT * FROM payment_account WHERE Store_ID = ?;");
			 ps.setString(1, store);
			 rs = ps.executeQuery();
			 while(rs.next())
				 if(customers.contains(rs.getString("ID")))
					 customers.remove(rs.getString("ID"));
			 
			 ps = conn.prepareStatement("SELECT * FROM person WHERE ID not in (SELECT ID FROM payment_account);");
			 rs = ps.executeQuery();
			 while(rs.next())
				 if(!customers.contains(rs.getString("ID")))
					 customers.add(rs.getString("ID"));
			 
			 msg1.newO = customers;
			client.sendToClient(msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * check if there is already complaint from this user from this store
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void check_complaint_exist(Msg msg1, Connection conn, ConnectionToClient client) {
		String store = (String)msg1.oldO;
		String customer = (String)msg1.newO;

		PreparedStatement ps;
		ResultSet rs;
		
		try {
			/** Building the query */

			 ps = conn.prepareStatement("SELECT * FROM complaint WHERE Customer_ID = ? AND Store_Id = ? AND Status='Pending';");
			 ps.setString(1, customer);
			 ps.setString(2, store);
			 rs = ps.executeQuery();
			if(rs.next())
				msg1.newO = "Exist complaint";
			else
				msg1.newO = "No complaint";
			 
			client.sendToClient(msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * get the Stores ids
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void get_stores_id(Msg msg1, Connection conn, ConnectionToClient client) {

		ArrayList<String> stores = new ArrayList<String>();
		String temp;
		ResultSet rs;

		try {
			/* set up and execute the select query from store table to get all the stores */
			rs = conn.createStatement().executeQuery("SELECT * FROM store GROUP BY ID;");

			while (rs.next())
				stores.add(rs.getString("ID"));
			msg1.oldO = stores;

			client.sendToClient(msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get the the relevant report
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public void get_report_to_display(Msg msg1, Connection conn, ConnectionToClient client) {

		Report report = (Report) msg1.oldO;
		String quarter, year, storeID, name, quarter2 = "", year2 = "", store2 = "";
		int i, compare = 1;
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<String> allStores = new ArrayList<String>();
		Incomes_Report incomes = null;
		ArrayList<Order> orders = new ArrayList<Order>();;
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Item_In_Catalog> items_in_catalog = new ArrayList<Item_In_Catalog>();
		ArrayList<Order> orders_store = new ArrayList<Order>();
		ArrayList<Item_In_Order> items_in_order = new ArrayList<Item_In_Order>();
		Reservation_Report reservation = null;
		ArrayList<Complain> complaints = new ArrayList<Complain>();
		Complaint_Report ComplaintsReport = null;
		ArrayList<Survey> surveys = new ArrayList<Survey>();
		Satisfaction_Report Satisfaction = null;
		
		/* save details */
		quarter = report.getQuarter();
		year = report.getYear();
		storeID = report.getStore();
		name = report.getName();
		
		/*get all the stores*/
		if(storeID.equals("All"))
		{
			try 
			{
				ps = conn.prepareStatement("SELECT * FROM store GROUP BY ID;");
				rs = ps.executeQuery();
				while(rs.next())
					allStores.add(rs.getString("ID"));
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
			allStores.add(storeID);
		
		if (msg1.getRole().equals("get reports for compare")) {
			Report report2 = (Report) msg1.newO;
			quarter2 = report2.getQuarter();
			year2 = report2.getYear();
			store2 = report2.getStore();
			compare = 2;
		}

		
		try {
			for(String store: allStores)
			{
				for (i = 0; i < compare; i++) {
					/* check which report was selected */
					switch (name) {
					case "Incomes":
						if(incomes == null || i==1)
						{
							incomes = new Incomes_Report(name, quarter, store, year);
							if(storeID.equals("All"))
								incomes.setStore("All Stores");
						}
						/* build query for orders from specific store */
						ps = conn.prepareStatement("SELECT * FROM orders WHERE Store_ID=?;");
						ps.setString(1, store);
						rs = ps.executeQuery();

						if(i==1)
							orders.clear();
						/* get the relevant order */
						while (rs.next()) {
							if (checkDate(rs.getString("Date"), quarter, year) && rs.getString("Status").equals("Paid")) {
								Order order = new Order(rs.getString("ID"), rs.getString("Status"));
								order.setTotprice(Float.parseFloat(rs.getString("Price")));
								orders.add(order);
							}
						}
						incomes.setOrders(orders);
						incomes.calculateReport();
						if (i == 1)
							msg1.newO = incomes;
						else
							msg1.oldO = incomes;
						break;
					case "Reservations":
						if(reservation == null || i==1)
						{
							reservation = new Reservation_Report(name, quarter, store, year);
							if(storeID.equals("All"))
								reservation.setStore("All Stores");
							
							/* build query for get all items */
							ps = conn.prepareStatement("SELECT * FROM item;");
							rs = ps.executeQuery();

							items.clear();
							/* insert to the ArrayList */
							while (rs.next()) {
								Item item = new Item();
								item.setID(rs.getString("ID"));
								item.setName(rs.getString("Name"));
								items.add(item);
							}

							/* build query for get all items in catalog */
							ps = conn.prepareStatement("SELECT * FROM item_in_catalog;");
							rs = ps.executeQuery();

							items_in_catalog.clear();
							/* insert to the ArrayList */
							while (rs.next()) {
								Item_In_Catalog item = new Item_In_Catalog();
								item.setID(rs.getString("ID"));
								item.setName(rs.getString("Name"));
								items_in_catalog.add(item);
							}
							
							items_in_order.clear();
							/* build query for item in order from specific store */
							ps = conn.prepareStatement("SELECT * FROM item_in_order;");
							rs = ps.executeQuery();
							/* get the relevant order */
							while (rs.next()) {
								Item_In_Order iio = new Item_In_Order(rs.getString("Order_ID"), rs.getString("Item_ID"),
										rs.getString("Type"), Integer.parseInt(rs.getString("Amount")));
								items_in_order.add(iio);
							}
						}
						

						/* build query for orders from specific store */
						ps = conn.prepareStatement("SELECT * FROM orders WHERE Store_ID=?;");
						ps.setString(1, store);
						rs = ps.executeQuery();

						if(i==1)
							orders_store.clear();
						/* get the relevant order */
						while (rs.next()) {
							if (checkDate(rs.getString("Date"), quarter, year) && rs.getString("Status").equals("Paid")) {
								Order order = new Order(rs.getString("ID"), rs.getString("Status"));
								order.setTotprice(Float.parseFloat(rs.getString("Price")));
								orders_store.add(order);
							}
						}

						reservation.setItems(items);
						reservation.setItems_catalog(items_in_catalog);
						reservation.setOrders(orders_store);
						if(reservation.getItem_in_order() == null)
							reservation.setItem_in_order(items_in_order);
						
						if(allStores.get(allStores.size()-1).equals(store) || i==1) //only if we in the last store
						{
							reservation.filterRelevantItemInOrder();
							reservation.calculateReport();
						}

						if (i == 1)
							msg1.newO = reservation;
						else
							msg1.oldO = reservation;
						break;
					case "Complaints":
						if(ComplaintsReport == null || i==1)
						{
							ComplaintsReport = new Complaint_Report(name, quarter, store, year);
							if(storeID.equals("All"))
								ComplaintsReport.setStore("All Stores");
						}

						/* build query for complaints */
						ps = conn.prepareStatement("SELECT * FROM complaint WHERE Store_Id=?;");
						ps.setString(1, store);
						rs = ps.executeQuery();

						if(i == 1)complaints.clear();
						/* get all the complaints that appropriate to the relevant date */
						while (rs.next()) 
						{
							if (checkDate(rs.getString("Date"), quarter, year)) 
							{
								Complain com = new Complain();
								com.setDate(rs.getString("Date"));
								com.setCustomer_ID(rs.getString("Customer_ID"));
								complaints.add(com);
							}
						}
						
						ComplaintsReport.setComplaints(complaints);
						ComplaintsReport.calculateReport();
						if (i == 1)
							msg1.newO = ComplaintsReport;
						else
							msg1.oldO = ComplaintsReport;
						break;
					case "Satisfaction":
						if(Satisfaction == null || i==1)
						{
							Satisfaction = new Satisfaction_Report(name, quarter, store, year);
							if(compare != 2)
								Satisfaction.setStore("All Stores");
						

						/* build query for survey */
						ps = conn.prepareStatement(
								"SELECT * FROM survey WHERE Status='No Active' AND Conclusion is not null;");
						rs = ps.executeQuery();

						while (rs.next()) {
							if (checkDate(rs.getString("Date"), quarter, year)) {
								Survey survey = new Survey(rs.getString("ID"), rs.getString("Date"),
										rs.getString("Num_Of_Participant"), rs.getString("Conclusion"));
								survey.setA1(Float.parseFloat(rs.getString("A1")));
								survey.setA2(Float.parseFloat(rs.getString("A2")));
								survey.setA3(Float.parseFloat(rs.getString("A3")));
								survey.setA4(Float.parseFloat(rs.getString("A4")));
								survey.setA5(Float.parseFloat(rs.getString("A5")));
								survey.setA6(Float.parseFloat(rs.getString("A6")));
								survey.setQ1(rs.getString("Q1"));
								survey.setQ2(rs.getString("Q2"));
								survey.setQ3(rs.getString("Q3"));
								survey.setQ4(rs.getString("Q4"));
								survey.setQ5(rs.getString("Q5"));
								survey.setQ6(rs.getString("Q6"));
								surveys.add(survey);
							}
						}
						Satisfaction.setSurveys(surveys);
						Satisfaction.calculateReport();
						if (i == 1)
							msg1.newO = Satisfaction;
						else
							msg1.oldO = Satisfaction;
						}
						break;
					}
					if (compare == 2) {
						quarter = quarter2;
						year = year2;
						store = store2;
					}
				}
			}

			client.sendToClient(msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * help function to determine if the date within relevant range of year and
	 * quarter
	 * @param date
	 * @param quarter
	 * @param yearSpecific
	 * @return
	 */
	public boolean checkDate(String date, String quarter, String yearSpecific) {
		String month, year;

		/* parse the date */
		month = date.substring(3, 5);
		year = date.substring(6);

		/* check year */
		if (!year.equals(yearSpecific))
			return false;

		/* check quarter vs month */
		switch (quarter) {
		case "1":
			if (month.equals("01") || month.equals("02") || month.equals("03"))
				return true;
			break;
		case "2":
			if (month.equals("04") || month.equals("05") || month.equals("06"))
				return true;
			break;
		case "3":
			if (month.equals("07") || month.equals("08") || month.equals("09"))
				return true;
			break;
		case "4":
			if (month.equals("10") || month.equals("11") || month.equals("12"))
				return true;
			break;
		}

		return false;
	}
/**
 * if the user canceled his  order - change the status order and set the correct refund amount
 * if the changes succeed- take the items in the canceled order and update the item amount in the store
 * @param msg1
 * @param conn
 * @param client
 */
	public static void change_order_status(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		Order order = (Order) msg.oldO;
		Person user=(Person)msg.newO;
		PreparedStatement ps;
		ResultSet rs;
		String refund,compensation = null;
		 
		/*calc the refund amount */
		 float price = order.getTotprice();
		 if(order.getRefund_amount().equals("full"))
		 {
			 order.setRefund_amount(Float.toString( order.getTotprice() ));
			 compensation=Float.toString( order.getTotprice() );
		 }
			
		 else if(order.getRefund_amount().equals("half"))
		 {
			 order.setRefund_amount(Float.toString( order.getTotprice()/2  ));
			 compensation=Float.toString( order.getTotprice()/2 );
		 }
			 
		 else if(order.getRefund_amount().equals("none"))
		 {
			 order.setRefund_amount("0");
			 compensation="0";
		 }
			 

		try {
			/* set up and execute the update query */
			ps = conn.prepareStatement("UPDATE orders SET Status=? , Refund=? WHERE ID=?;");
			ps.setString(1, "Canceled");
			ps.setString(2, order.getRefund_amount());
			ps.setString(3, order.getId());
			ps.executeUpdate();

			
		 

			/* check if the user has payment account*/
			ps = conn.prepareStatement("SELECT * FROM payment_account WHERE ID=? AND Store_ID=?;");
			ps.setString(1, user.getUser_ID());
			ps.setString(2, order.getStoreid());
			rs = ps.executeQuery();
			if(rs.next())
			{
				refund = rs.getString("Refund");
				if(refund != null)
					compensation = String.valueOf(Float.parseFloat(refund) + Float.parseFloat(compensation));
				ps = conn.prepareStatement("UPDATE payment_account SET Refund=? WHERE ID=? AND Store_ID=?;");
				ps.setString(1, compensation);
				ps.setString(2,  user.getUser_ID());
				ps.setString(3, order.getStoreid());
				ps.executeUpdate();
			}
			
			//*get the items in the order*//
			ps = conn.prepareStatement("SELECT Item_ID , Amount FROM item_in_order WHERE Order_ID=? ;");
			ps.setString(1, order.getId());
			ResultSet rs2=ps.executeQuery();
			 
			
			while(rs2.next())
			{
 				ps = conn.prepareStatement("Update store SET Amount=Amount+? where Item_ID=? and ID=?;");
				ps.setString(1, rs2.getString(2));
				ps.setString(2, rs2.getString(1));
				ps.setString(3, order.getStoreid());
				
				ps.executeUpdate();
				
				
			}
 
			msg.freeField = "succeed";

			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the details of the user active orders according the user id
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void get_user_order(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		Person cur_p = (Person) msg.oldO;
		ArrayList<Order> id = new ArrayList<Order>();

		try {
			/** Building the query */

			PreparedStatement ps = conn.prepareStatement(" SELECT * FROM  orders  where Person_ID=? and status=?; ");
			ps.setString(1, cur_p.getUser_ID());
			ps.setString(2, "Active");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order temp = new Order();
				temp.setId(rs.getString(1));
				temp.setDelivery1(rs.getString(3));
				temp.setStatus(rs.getString(4));
				temp.setPayment(rs.getString(5));
				temp.setTotprice(rs.getFloat(6));
				temp.setStoreid(rs.getString(7));
				temp.setCreatetime(rs.getString(8));
				temp.setCreatedate(rs.getString(9));
				temp.setRequesttime(rs.getString(10));
				temp.setRequestdate(rs.getString(11));

				id.add(temp);

			}
			msg1.newO = id;

		//	client.sendToClient(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		}  

	}

	/**
	 * Get all customer's orders
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static void get_user_orders_id(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		Person cur_p = (Person) msg.oldO;
		ArrayList<String> id = new ArrayList<String>();

		try {
			/** Building the query */

			PreparedStatement ps = conn.prepareStatement("SELECT ID FROM orders where Person_ID=? ,status=?;");
			ps.setString(1, cur_p.getUser_ID());
			ps.setString(2, "Active");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				id.add(rs.getString(1));
			}
			msg1.newO = id;

			client.sendToClient((Msg) msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Insert the delivery requested by the customer
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static void insert_delivery(Msg msg1, Connection conn, ConnectionToClient client) {

		Delivery d = (Delivery) msg1.oldO;
		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = conn.prepareStatement(
					"INSERT INTO delivery (Order_ID, Address, RecieverName, Phone)" + " VALUES (?, ?, ?, ? );");

			ps.setString(1, Integer.toString(d.getOrderid()));
			ps.setString(2, d.getAddress());
			ps.setString(3, d.getName());
			ps.setString(4, d.getPhone());

			ps.executeUpdate();
			// insert deliv after order created -> no deliveries without orders.

			msg1.newO = d;
			client.sendToClient((Msg) msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Insert the customer's order & get new order ID
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static void insert_order(Msg msg1, Connection conn, ConnectionToClient client) {
		Order order = (Order) msg1.oldO;
		PreparedStatement ps;
		ResultSet rs;
		try {
			/* get the last ID */
			ps = conn.prepareStatement("SELECT max(ID) FROM orders;");
			rs = ps.executeQuery();
			rs.next();
			int newid = Integer.parseInt(rs.getString(1));
			/* execute the insert query */
			ps = conn.prepareStatement(
					"INSERT INTO orders (ID, Person_ID, Delivery, Status, Payment_Type, Price, Store_ID, Time, Date, Requested_Time, Requested_Date)"
							+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

			order.setId(Integer.toString(newid + 1)); // max oid+1
			ps.setString(1, order.getId()); // insert the last id + 1
			ps.setString(2, order.getPersonid());
			String deliv = "No";
			if (order.haveDelivery() == true)
				deliv = "Yes";

			ps.setString(3, deliv);
			ps.setString(4, order.getStatus());
			ps.setString(5, order.getPayment());
			ps.setString(6, Float.toString(order.getTotprice()));
			ps.setString(7, order.getStoreid());
			ps.setString(8, order.getCreatetime());
			ps.setString(9, order.getCreatedate());
			ps.setString(10, order.getRequesttime());
			ps.setString(11, order.getRequestdate());
			ps.executeUpdate();

			msg1.newO = order;
			client.sendToClient((Msg) msg1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert relevant items in customer's order into DB
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException 
	 */
	public static void insert_items_in_order(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		ArrayList<Item> items = (ArrayList<Item>) msg1.oldO; // arrives as individual items
		HashMap<String, Integer> amounts = (HashMap<String, Integer>) msg1.newO; // amounts.(Item_ID)==amount
		int orderID = (int) msg.num1;
		String oid = msg1.freeField;

		PreparedStatement ps;

		try {

			// insert all the items
			for (Item t : (ArrayList<Item>) items) {
				// if self_item then add it to self tables with new id
				int newid = -1;
				if (t instanceof Self_Item) {
					PreparedStatement ps1 = conn.prepareStatement("SELECT max(ID) FROM self_item;");
					ResultSet rs = ps1.executeQuery();
					rs.next();
					newid = (rs.getInt(1)) + 1;
					
					Self_Item si = (Self_Item)t;

					for(Item item_in_self : si.items) {
					ps = conn.prepareStatement("INSERT INTO self_item (ID, Item_Id, Type, Amount, OrderID)" + " VALUES (?,?,?,?,?);");
					ps.setString(1, Integer.toString(newid));
					ps.setString(2, item_in_self.getID());
					ps.setString(3, t.getType());
					ps.setString(4, Integer.toString(si.getItemAmount(item_in_self)) );
					ps.setString(5, oid );

					ps.executeUpdate();
				}
					}
				
				if(!(t instanceof Self_Item)) {

				// then associate the item with the order.
				ps = conn.prepareStatement(
						"INSERT INTO `item_in_order` (Order_ID, Item_ID, Type, Amount)" + " VALUES (?, ?, ?, ? );");

				String id;

				ps.setString(1, Integer.toString(orderID));
				if (newid > -1)
					id = Integer.toString(newid);
				else
					id = t.getID();

				ps.setString(2, id);
				String type = "Catalog";
				if(!(t.getType().equals("Catalog")) )
					type="Item";
				ps.setString(3, type);
				ps.setString(4, Integer.toString(amounts.get(t.getID())));

				ps.executeUpdate();

			}
				}

			client.sendToClient(msg);

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

	}
/**
 * get the payment account details according the current user 
 * @param msg1
 * @param conn
 * @param client
 */
	public static void get_payment_account(Msg msg1, Connection conn, ConnectionToClient client) {


		Msg msg = (Msg) msg1;
		Payment_Account acc = new Payment_Account();
		String uid=(String)msg.oldO;
		String sid=(String)msg.newO;

		try {
			/** Building the query */

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM payment_account WHERE ID=? AND Store_ID=?;");
			ps.setString(1, uid);
			ps.setString(2, sid);

			ResultSet rs = ps.executeQuery();

			if (!(rs.next())) {
				msg.newO = null;
				client.sendToClient(msg);
				return;
			}

			
				acc.setID(rs.getString(1));
				acc.setCreditCard(rs.getString(2));
				acc.setStatus(rs.getString(3));
				acc.setSubscription(rs.getString(4));
				acc.setStoreID(rs.getString(5));
				acc.setDate(rs.getString(6));
				if(rs.getString(7) != null)
				acc.setRefund_sum(Float.parseFloat(rs.getString(7)));
				else acc.setRefund_sum((float)0.0);
			


			msg.newO = acc;
			client.sendToClient(msg);


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			System.err.println("unable to send msg to client");
		}

	}

	/**
	 * insert a new complain to the system, generate the number of the complain id
	 * the init complain status is pending 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void insert_new_complain(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		Complain com = (Complain) msg1.oldO;
		/** to create a random number for the complain id */
		Random rand = new Random();
		int new_id;
		try {
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT max(ID) FROM " + msg.getTableName() + ";");
			ResultSet rs = ps1.executeQuery();
			rs.next();
			new_id = (rs.getInt(1));
			if(new_id==0) {
				 new_id= rand.nextInt(5000) + 1;
			}
			else 
				new_id+=1;
			
			/** Building the query */

			PreparedStatement ps = conn.prepareStatement("INSERT INTO " + msg.getTableName()
					+ "(`ID`, `Customer_ID`, `Text`, `Status`, `Date`, `Hour`, `Store_Id`) VALUES (?,?,?,?,?,?,?);");
			ps.setString(1, Integer.toString(new_id));
 
			ps.setString(2, com.getCustomer_ID());
			ps.setString(3, com.getUser_txt());
			ps.setString(4, "Pending");
			ps.setString(5, com.getDate());
			ps.setString(6, com.getHour());
			ps.setString(7, com.getStore());

			ps.executeUpdate();

			msg.freeField = "insert succeed";
			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			System.err.println("unable to send msg to client");
		}
	}

	/**
	 * insert a new sale
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void insert_new_sale(Msg msg1, Connection conn, ConnectionToClient client) {

		/* set variables */
		ArrayList<String> items_ID = (ArrayList<String>) msg1.oldO;
		Sale sale = (Sale) msg1.newO;
		String description = sale.getDescription();
		String discount = sale.getDiscount();
		String store = sale.getStoreID();
		String saleID = "";

		PreparedStatement ps;
		ResultSet rs;
		int new_id;
		try {
			/* get the last ID of sale */
			ps = conn.prepareStatement("SELECT max(ID) FROM sales;");
			rs = ps.executeQuery();
			rs.next();

			/* execute the insert query */
			ps = conn.prepareStatement("INSERT INTO sales (ID, Description, Discount) VALUES (?, ?, ?);");
			new_id = Integer.parseInt(rs.getString(1)) + 1;
			ps.setString(1, "" + new_id); // insert the last id + 1
			ps.setString(2, description);
			ps.setString(3, discount);
			ps.executeUpdate();

			saleID = "" + new_id;
			/* update in store table */
			for (String item : items_ID) {
				ps = conn.prepareStatement("UPDATE store SET Sale_ID=? WHERE ID=? and Item_ID =?;");
				ps.setString(1, saleID);
				ps.setString(2, store);
				ps.setString(3, item);
				ps.executeUpdate();
			}

			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the number of the current active survey
	 * if there is no active survey at all-> return with No active survey msg
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException, SQLException 
	 */
	public static void get_the_current_survey_id(Msg msg1, Connection conn, ConnectionToClient client) {

		Msg msg = (Msg) msg1;
		Survey survey = new Survey();

		try {
			/** Building the query */

			PreparedStatement ps = conn.prepareStatement("SELECT ID FROM survey where Status='Active';");
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				msg.newO = null; // this is mean that there is no active survey
				client.sendToClient(msg);
				return;
			}
			rs.beforeFirst();
			while (rs.next()) {
				survey.setID(rs.getString(1));

			}
			msg.newO = survey;
			client.sendToClient(msg);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			System.err.println("unable to send msg to client");
		}

	}

	/**
	 * Return an ArrayList<Item> with Customer's selected items for self item
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException, SQLException 
	 */
	public static void SelectItemsCTP(Msg msg, Connection con, ConnectionToClient client) {

		Msg msg1 = (Msg) msg;
		Item p = (Item) msg1.oldO;

		String type = p.getType();
		String color = p.getColor();
		String minprice = Float.toString(msg1.num1);
		String maxprice = Float.toString(msg1.num2);

		Object products = new ArrayList<Object>(); // arraylist of products
													// back from query

		try {
			/* Building the query */
			PreparedStatement ps = con
					.prepareStatement(" SELECT * FROM item WHERE Color=? AND Type=? " + "AND Price BETWEEN ? AND ?");
			ps.setString(1, color);
			ps.setString(2, type);
			ps.setString(3, minprice);
			ps.setString(4, maxprice);

			/* Results */
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Item returnproduct = new Item(); // create new product
				returnproduct.setID(rs.getString(1)); // set details as needed
				returnproduct.setName(rs.getString(2));
				returnproduct.setPrice(Float.parseFloat(rs.getString(4)));
				MyFile f = getFileInfo(rs.getString(1), "It");
				returnproduct.setImage(f);
				// insert to array (cast from Object)
				((ArrayList<Item>) products).add(returnproduct);
				System.out.println("in SelectItemsCTP changed");
			}

			/* back to client */
			msg1.newO = products;

			client.sendToClient(msg);
			rs.close();

		} catch (IOException x) {
			System.err.println("unable to send msg to client");
		} catch (SQLException ex)

		{/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Check if user already took the current survey
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException, SQLException 
	 */
	public static void check_if_user_took_this_survey(Msg msg1, Connection conn, ConnectionToClient client) {
		Msg msg = (Msg) msg1;
		Person user = (Person) msg.newO;
		Survey survey = (Survey) msg.oldO;
		int flag = 0;

		try {

			PreparedStatement ps = conn.prepareStatement(" SELECT Customer_ID FROM  comments_survey WHERE ID=?;");
			ps.setString(1, survey.getID());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				if (user.getUser_ID().equals(rs.getString(1))) {
					msg.freeField = "false";
					flag = 1;
				}
			}
			if (flag == 0) {
				msg.freeField = "true";
			}

			client.sendToClient(msg);

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Check all relevant user details and returns his Payment_Account and Person with relevant info
	 * @param msg1
	 * @param conn
	 * @param client
	 * @throws IOException, SQLException 
	 */
	public static void check_user_details(Msg msg1, Connection conn, ConnectionToClient client) {
		Person user = (Person) msg1.oldO;
		String a;
		//logger.info("user" + " " + user.getUser_ID() + "got in to the system");
		ArrayList<String> store = new ArrayList<String>();
		ArrayList<Payment_Account> pay_account_arr = new ArrayList<Payment_Account>();

		try {
			PreparedStatement ps = conn
					.prepareStatement(" SELECT * FROM " + msg1.getTableName() + " " + "WHERE ID=? and Password=?;");

			/* insert the names to the query */
			ps.setString(1, user.getUser_ID());
			ps.setString(2, user.getUser_password());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				a = rs.getString(1);

				// if the user exist
				if (a!=null) {
					Payment_Account pay_account = new Payment_Account();
					user.setIsExist("1");
					user.setUser_name(rs.getString(2));
					user.setPrivilege(rs.getString(5));
					user.setUser_last_name(rs.getString(3));
					user.setWWID(rs.getString(7));
					user.setIsOnline("1");
					msg1.oldO = user;

					PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM payment_account where ID=?;");
					/* get the account details for this user */
					ps2.setString(1, user.getUser_ID());
					ResultSet rs2 = ps2.executeQuery();
					while (rs2.next()) {
						int flag = 0;
						pay_account.setID(rs2.getString(1));
						pay_account.setCreditCard(rs2.getString(2));
						pay_account.setStatus(rs2.getString(3));
						pay_account.setSubscription(rs2.getString(4));
						pay_account.setStoreID(rs2.getString(5));
						if(rs2.getString(7)!=null)
						pay_account.setRefund_sum(Float.parseFloat(rs2.getString(7)));
						pay_account_arr.add(pay_account);
						PreparedStatement ps3 = conn.prepareStatement("SELECT ID,Location FROM store where ID=?;");
						ps3.setString(1, rs2.getString(5));

						ResultSet rs3 = ps3.executeQuery();
						while (rs3.next()) {
							for (int i = 0; i < store.size(); i++) {
								if (store.get(i).equals(rs3.getString(2) + "-" + rs3.getString(1)))
									flag = 1;
							}
							if (flag == 0)
								store.add(rs3.getString(2) + "-" + rs3.getString(1));
							else
								flag = 0;
						}
					}
					user.setStore(store);

					/* check if it is possible to change the status of the user */
					if (change_online_status(msg1, conn, "1") == true) {
						msg1.newO = (Person) user;
						msg1.oldO = (Payment_Account) pay_account;
						client.sendToClient(msg1);
						return;
					} else {// if the user is already connected

						msg1.newO = (Person) user;
						msg1.oldO = (Payment_Account) pay_account;
						user.setAlreadyConnected(true);
						client.sendToClient(msg1);
						return;
					}
				} // end if

			} // while rs

			if (rs.next() == false) /* if the user dosent exist in the system */
			{
				Person user_not_exist = new Person(null, null);
				user_not_exist.setIsExist("0");
				msg1.newO = user_not_exist;
				client.sendToClient(msg1);
				return;

			}
		} // try

		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException x) {
			System.err.println("unable to send msg to client");
		}
	}

	/**
	 * Change's user's status upon login or logout
	 * @param msg1
	 * @param conn
	 * @param new_status
	 * @throws SQLException 
	 */
	public static boolean change_online_status(Msg msg1, Connection conn, String new_status) {

		Person user = (Person) msg1.oldO;
		boolean answer;
		answer = isConnected(msg1, conn);
		if (answer == true && new_status.equals("1"))
			return false;

		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE person SET Online=? WHERE ID=?;");
			ps.setString(1, new_status);
			ps.setString(2, user.getUser_ID());

			ps.executeUpdate();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * run tests to check if all details are correct and then create the new Payment
	 * Account in DB
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void insert_paymentAccount(Msg msg1, Connection conn, ConnectionToClient client) {

		Payment_Account user = (Payment_Account) msg1.oldO;
		Person manager = (Person) msg1.newO;
		String store = (String) msg1.freeUse;
		PreparedStatement ps;
		
		try {
				ps = conn.prepareStatement(
						"INSERT INTO payment_account (ID, CreditCard, Status, Subscription, Store_ID, Start_Date) VALUES (?, ?, ?, ?, ?, ?);");
				ps.setString(1, user.getID());
				ps.setString(2, user.getCreditCard());
				ps.setString(3, user.getStatus());
				ps.setString(4, user.getSubscription());
				ps.setString(5, store);
				ps.setString(6, user.getDate());

				ps.executeUpdate();
				msg1.newO = user;
				client.sendToClient(msg1);
				return;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if survey exists and returns the latest active one if exists
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void check_survey_exist(Msg msg1, Connection conn, ConnectionToClient client) {
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = conn.prepareStatement(" SELECT * FROM survey WHERE Status = 'Active';");
			rs = ps.executeQuery();
			if (!rs.next()) {
				/*get the last survey*/
				ps = conn.prepareStatement(" SELECT * FROM survey WHERE Status = 'No Active' AND ID=(SELECT MAX(ID) FROM survey);");
				rs = ps.executeQuery();
				rs.next();
				Survey oldSurvey = new Survey();
				oldSurvey.setQ1(rs.getString("Q1"));
				oldSurvey.setQ2(rs.getString("Q2"));
				oldSurvey.setQ3(rs.getString("Q3"));
				oldSurvey.setQ4(rs.getString("Q4"));
				oldSurvey.setQ5(rs.getString("Q5"));
				oldSurvey.setQ6(rs.getString("Q6"));

				msg1.newO = null; // this is mean that there is no active survey
				msg1.oldO = oldSurvey;
				msg1.freeField="No Active";
				client.sendToClient(msg1);
				return;
			}
			Survey toSend = new Survey();
			toSend.setID(rs.getString("ID"));
			toSend.setNumOfParticipant(rs.getString("Num_Of_Participant"));
			toSend.setDate(rs.getString("Date"));

			msg1.newO = toSend; // this is mean that there is active server
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * check if there is already active sale in the user's store
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void CheckForActiveSale(Msg msg1, Connection conn, ConnectionToClient client) 
	{
		Person employee = (Person)msg1.oldO;
		PreparedStatement ps, psItem;
		ResultSet rs, rsItem, rsClose;
		String store;
		Sale sale = null;
		SortedMap<String , String> items = new TreeMap<String , String>();
		ArrayList<String> items_in_sale = new ArrayList<String>();
		boolean exist = false;
		
		try {
			/*get the store id of the employee (every employee has a payment account)*/
			ps = conn.prepareStatement(" SELECT * FROM payment_account WHERE ID = ?;");
			ps.setString(1, employee.getUser_ID());
			rs = ps.executeQuery();
			rs.next();
			store = rs.getString("Store_ID");
			
			/*check if there is sale by checking all the items*/
			ps = conn.prepareStatement(" SELECT * FROM store WHERE ID = ?;");
			ps.setString(1, store);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				if(rs.getString("Sale_ID") != null)
				{
					exist=true;
					
					if(sale == null)
					{
						/*get the details of sale*/
						ps = conn.prepareStatement(" SELECT * FROM sales WHERE ID = ?;");
						ps.setString(1,rs.getString("Sale_ID"));
						rsClose = ps.executeQuery();
						rsClose.next();
						sale = new Sale(store,rsClose.getString("Description"),rsClose.getString("Discount"));
						sale.setID(rsClose.getString("ID"));
					}
					
					/*get the name of each item from the origin table that participant in the sale*/
					psItem = conn.prepareStatement(" SELECT * FROM item_in_catalog WHERE ID = ?;");
					psItem.setString(1, rs.getString("Item_ID"));
					rsItem = psItem.executeQuery();
					rsItem.next();
					items_in_sale.add(rsItem.getString("Name"));
				}
				else //get the name of the item
				{
					if(rs.getString("Type").equals("Catalog"))	//only for item from catalog
					{
						/*get the name of each item from the origin table*/
						psItem = conn.prepareStatement(" SELECT * FROM item_in_catalog WHERE ID = ?;");
						psItem.setString(1, rs.getString("Item_ID"));
						rsItem = psItem.executeQuery();
						rsItem.next();
						if(rsItem.getString("Status").equals("Active"))
							items.put(rsItem.getString("ID"), rsItem.getString("Name"));	
					}
				}
			}
			
			if(exist)
			{
				msg1.freeField = "There is sale"; // this is mean that there is active sale
				msg1.oldO = sale;
				msg1.newO = store;
				msg1.freeUse = items_in_sale;
				client.sendToClient(msg1);
			}
			else
			{
				msg1.freeField = "There is no sale"; // this is mean that there is no active sale
				msg1.oldO = store; // save the store id for the future
				msg1.freeUse = items;
				client.sendToClient(msg1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * insert the survey to survey table with new id
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void insert_survey(Msg msg1, Connection conn, ConnectionToClient client) {

		Survey survey = (Survey) msg1.oldO;
		PreparedStatement ps;
		ResultSet rs;
		int new_id;
		try {
			/* get the last ID */
			ps = conn.prepareStatement("SELECT max(ID) FROM survey;");
			rs = ps.executeQuery();
			rs.next();
			/* execute the insert query */
			ps = conn.prepareStatement(
					"INSERT INTO survey (ID, Date, Q1, Q2, Q3, Q4, Q5, Q6, A1, A2, A3, A4, A5, A6, Status, Num_Of_Participant) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			new_id = Integer.parseInt(rs.getString(1)) + 1;
			ps.setString(1, "" + new_id); // insert the last id + 1
			ps.setString(2, survey.getDate());
			ps.setString(3, survey.getQ1());
			ps.setString(4, survey.getQ2());
			ps.setString(5, survey.getQ3());
			ps.setString(6, survey.getQ4());
			ps.setString(7, survey.getQ5());
			ps.setString(8, survey.getQ6());
			ps.setString(9, Float.toString(survey.getA1()));
			ps.setString(10, Float.toString(survey.getA2()));
			ps.setString(11, Float.toString(survey.getA3()));
			ps.setString(12, Float.toString(survey.getA4()));
			ps.setString(13, Float.toString(survey.getA5()));
			ps.setString(14, Float.toString(survey.getA6()));
			ps.setString(15, "Active");
			ps.setString(16, survey.getNumOfParticipant());
			ps.executeUpdate();

			msg1.newO = survey;
			//logger.info("a new survey was insert with " + survey.getID() + "ID");
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * replace the status of specific survey (by id from msg1) to be "No Active"
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void Close_survey(Msg msg1, Connection conn, ConnectionToClient client) {
		Survey survey = (Survey) msg1.oldO;
		PreparedStatement ps;
		ResultSet rs;

		try {
			/* set up and execute the update query */
			ps = conn.prepareStatement("UPDATE survey SET Status=? WHERE ID=?;");
			ps.setString(1, "No Active");
			ps.setString(2, survey.getID());
			ps.executeUpdate();

			msg1.newO = survey;
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * close the specific sale
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void close_sale(Msg msg1, Connection conn, ConnectionToClient client) {
		String sale = (String) msg1.oldO;
		String store = (String) msg1.newO;
		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = conn.prepareStatement("SELECT * FROM store WHERE ID=?;");
			ps.setString(1, store);
			rs = ps.executeQuery();

			/* set up and execute the update query */
			while (rs.next()) {
				ps = conn.prepareStatement("UPDATE store SET Sale_ID=NULL WHERE ID=? AND Item_ID=?;");
				ps.setString(1, store);
				ps.setString(2, rs.getString("Item_ID"));
				ps.executeUpdate();
			}

			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if user is online (connected from another machine)
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static boolean isConnected(Msg msg1, Connection conn) {
		boolean isAlreadyCon = false;
		Person user = (Person) msg1.oldO;
		String current_status;

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT Online FROM person WHERE ID=?;");
			ps.setString(1, user.getUser_ID());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				current_status = rs.getString(1);
				if (current_status.equals("1")) {
					isAlreadyCon = true;
					// System.out.println("cannot change the user status- user is already online");
					return isAlreadyCon;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAlreadyCon;
	}

	/**
	 * Get the questions which correlates with the current active survey
	 * @param msg
	 * @param con
	 * @param client
	 */
	public static void get_survey_question(Object msg, Connection con, ConnectionToClient client) {

		Msg msg1 = (Msg) msg;
		Survey s = (Survey) msg1.oldO;
		try {

			PreparedStatement ps = con
					.prepareStatement(" SELECT * FROM " + msg1.getTableName() + " " + "WHERE Status=?;");
			/* insert the names to the query */
			ps.setString(1, "Active");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				s.setID(rs.getString(1));
				s.setDate(rs.getString(2));
				s.setQ1(rs.getString(3));
				s.setQ2(rs.getString(4));
				s.setQ3(rs.getString(5));
				s.setQ4(rs.getString(6));
				s.setQ5(rs.getString(7));
				s.setQ6(rs.getString(8));
				s.setNumOfParticipant("0");
			}
			msg1.newO = s;

			try {
				client.sendToClient(msg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * get the customer id for combobox in add comments to survey
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void GetComboForAddComment(Msg msg1, Connection conn, ConnectionToClient client) {
		PreparedStatement ps;
		ResultSet rs;
		Map<String, String> forCombo = new HashMap<String, String>();
		String surveyID = (String) msg1.oldO;

		try {
			/* set up and execute the update query */
			ps = conn.prepareStatement("SELECT * FROM comments_survey WHERE ID = ?;");
			ps.setString(1, surveyID);
			rs = ps.executeQuery();

			while (rs.next())
				forCombo.put(rs.getString("Customer_ID"), rs.getString("comment"));

			msg1.newO = forCombo;
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the customer id for combobox in answer complaint
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public void GetComboForAnsComplaint(Msg msg1, Connection conn, ConnectionToClient client) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Complain> complaint = new ArrayList<Complain>();
		String id, customer_id, user_text, date, hour, store;
		try {
			/* set up and execute the select query in order to get the pending complaint */
			ps = conn.prepareStatement("SELECT * FROM complaint WHERE Status=?;");
			ps.setString(1, "Pending");
			rs = ps.executeQuery();

			/* insert the results to the ArrayList */
			while (rs.next()) {
				if(check_24_hours(rs.getString("Hour"), rs.getString("Date")))
					msg1.oldO = "24 over";

				id = rs.getString("ID");
				customer_id = rs.getString("Customer_ID");
				user_text = rs.getString("Text");
				date = rs.getString("Date");
				hour = rs.getString("Hour");
				store = rs.getString("Store_Id");
				Complain com = new Complain(id, customer_id, user_text, date, hour);
				com.setStore(store);
				complaint.add(com);
			}

			msg1.newO = complaint;
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * check if 24 hours over for complaint
	 * @param time
	 * @param date
	 * @return
	 */
	public boolean check_24_hours(String time, String date)
	{
		int com_hour = Integer.parseInt(time.substring(0, 2));
		int com_minutes = Integer.parseInt(time.substring(3, 5));
		int sum_hour;
		
		Calendar myCalendar = new GregorianCalendar(Integer.parseInt(date.substring(6)), Integer.parseInt(date.substring(3,5))-1, Integer.parseInt(date.substring(0,2)), com_hour, com_minutes);
		Date myDate = myCalendar.getTime();
		Date date2 = new Date();
		sum_hour = (int)((date2.getTime()-myDate.getTime())/(1000*60*60));

		if(sum_hour>=24)
			return true;
		return false;
	}
	
	/**
	 * set the new conclusion in survey table
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void update_conclusion_survey(Msg msg1, Connection conn, ConnectionToClient client) {
		String conclusion = (String) msg1.oldO;
		String surveyID = (String) msg1.freeField;

		PreparedStatement ps;
		ResultSet rs;

		try {
			/* set up and execute the update query */
			ps = conn.prepareStatement("UPDATE survey SET Conclusion=? WHERE ID=?;");
			ps.setString(1, conclusion);
			ps.setString(2, surveyID);
			ps.executeUpdate();

			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get all customers id
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void GetComboForEditManProfile(Msg msg1, Connection conn, ConnectionToClient client) {
		PreparedStatement ps;
		ResultSet rs;
		SortedMap<String, String> privilegeCombo = new TreeMap<String, String>();
		SortedMap<String, ArrayList<String>> paymentCombo = new TreeMap<String, ArrayList<String>>();
		ArrayList<String> stores = new ArrayList<String>();
		String current_id = (String) msg1.oldO;

		try {
			/*
			 * set up and execute the select query from person table to get all the
			 * customers
			 */
			ps = conn.prepareStatement("SELECT * FROM person WHERE ID != ?;");
			ps.setString(1, current_id);
			rs = ps.executeQuery();

			while (rs.next())
				privilegeCombo.put(rs.getString("ID"), rs.getString("Privilege"));

			msg1.newO = privilegeCombo;

			/* set up and execute the select query from payment_account table */
			ps = conn.prepareStatement("SELECT * FROM payment_account WHERE ID != ?;");
			ps.setString(1, current_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				ArrayList<String> status_subscription = new ArrayList<String>();
				status_subscription.add(rs.getString("Status"));
				status_subscription.add(rs.getString("Subscription"));
				status_subscription.add(rs.getString("Store_ID"));
				paymentCombo.put(rs.getString("ID") + " " + rs.getString("Store_ID"), status_subscription);
			}

			msg1.oldO = paymentCombo;


			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the customer id for combobox in add comments to survey
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void GetComboForAddConclusion(Msg msg1, Connection conn, ConnectionToClient client) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Survey> forCombo = new ArrayList<Survey>();

		try {
			/* set up and execute the update query */
			ps = conn.prepareStatement("SELECT * FROM survey WHERE Status = ? AND Conclusion is null;");
			ps.setString(1, "No Active");
			rs = ps.executeQuery();
			while (rs.next()) 
				forCombo.add(new Survey(rs.getString("ID"), rs.getString("Date"), rs.getString("Num_Of_Participant"),
						rs.getString("Conclusion")));
			
			if (forCombo.isEmpty())
				msg1.newO = null;
			else
				msg1.newO = forCombo;

			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * set the new details of customer by system manager
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void update_profile_by_manager(Msg msg1, Connection conn, ConnectionToClient client) {
		String customerID = ((ArrayList<String>) msg1.oldO).get(0);
		String privilege = ((ArrayList<String>) msg1.oldO).get(1);
		String status = ((ArrayList<String>) msg1.oldO).get(2);
		String subscription = ((ArrayList<String>) msg1.oldO).get(3);
		String store = ((ArrayList<String>) msg1.oldO).get(4);

		PreparedStatement ps, _ps;
		ResultSet rs;

		try {
			/* set up and execute the update privilege in person table */
			ps = conn.prepareStatement("UPDATE person SET Privilege=? WHERE ID=?;");
			ps.setString(1, privilege);
			ps.setString(2, customerID);
			ps.executeUpdate();

			if(privilege.equals("Store Manager"))
			{
				/*check if the privilege changed by check if the current user is already manager*/
				ps = conn.prepareStatement("SELECT * FROM store WHERE Manager_ID=? GROUP BY ID;");
				ps.setString(1, customerID);
				rs = ps.executeQuery();
				if(!rs.next())
				{
					String saveManager;
					/*we need to replace the current manager with this user*/
					ps = conn.prepareStatement("SELECT * FROM store WHERE ID=?;");
					ps.setString(1, store);
					rs = ps.executeQuery();
					rs.next();
					saveManager = rs.getString("Manager_ID");
					
					/*change to this user*/
					ps = conn.prepareStatement("UPDATE store SET Manager_ID=? WHERE ID=?;");
					ps.setString(1, customerID);
					ps.setString(2, store);
					ps.executeUpdate();
					
					/*change the privilege to the exit manager*/
					ps = conn.prepareStatement("UPDATE person SET Privilege='Store Employee' WHERE ID=?;");
					ps.setString(1, saveManager);
					ps.executeUpdate();
				}
			}
			
			/*check if subscription was change*/
			ps = conn.prepareStatement("SELECT * FROM payment_account WHERE ID=?;");
			ps.setString(1, customerID);
			rs = ps.executeQuery();
			rs.next();
			if(subscription != null && rs.getString("Subscription").equals(subscription))
			{
				/* set up and execute the update status in payment account table */
				ps = conn.prepareStatement("UPDATE payment_account SET Status=?, Subscription=? WHERE ID=? AND Store_ID=?;");

				ps.setString(1, status);
				ps.setString(2, subscription);
				ps.setString(3, customerID);
				ps.setString(4, store);
				ps.executeUpdate();
			}
			else if(subscription != null && store != null && status != null) //we need to reset the start date
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				String start_date = dateFormat.format(date);
				/* set up and execute the update status in payment account table */
				_ps = conn.prepareStatement("UPDATE payment_account SET Status=?, Subscription=?, Start_Date=? WHERE ID=? AND Store_ID=?;");
				_ps.setString(1, status);
				_ps.setString(2, subscription);
				_ps.setString(3, start_date);
				_ps.setString(4, customerID);
				_ps.setString(5, store);
				_ps.executeUpdate();
			}
			

			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * set the new details of customer by system manager
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void update_answer_complain(Msg msg1, Connection conn, ConnectionToClient client) {
		String complainID = ((Complain) msg1.oldO).getComplain_ID();
		String answer = ((Complain) msg1.oldO).getAnswer();
		String compensation = ((Complain) msg1.oldO).getCompensation();
		String customer_ID = ((Complain) msg1.oldO).getCustomer_ID();
		String store_ID = ((Complain) msg1.oldO).getStore();
		String refund;
		
		PreparedStatement ps;
		ResultSet rs;

		try {
			/* set up and execute the update complaint answer in complaint table */
			ps = conn.prepareStatement("UPDATE complaint SET Answer=?, Compensation=?, Status=? WHERE ID=? AND Store_Id=?;");
			ps.setString(1, answer);
			ps.setString(2, compensation);
			ps.setString(3, "Closed");
			ps.setString(4, complainID);
			ps.setString(5, store_ID);
			ps.executeUpdate();

			/* check if the user has payment account*/
			ps = conn.prepareStatement("SELECT * FROM payment_account WHERE ID=? AND Store_ID=?;");
			ps.setString(1, customer_ID);
			ps.setString(2, store_ID);
			rs = ps.executeQuery();
			if(rs.next())
			{
				refund = rs.getString("Refund");
				if(refund != null)
					compensation = String.valueOf(Float.parseFloat(refund) + Float.parseFloat(compensation));
				ps = conn.prepareStatement("UPDATE payment_account SET Refund=? WHERE ID=? AND Store_ID=?;");
				ps.setString(1, compensation);
				ps.setString(2, customer_ID);
				ps.setString(3, store_ID);
				ps.executeUpdate();
			}
			
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * check if we need to reset the start sate of subscription 
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public void check_start_date_paymentAccount(Msg msg1, Connection conn, ConnectionToClient client)
	{
		Person customer = (Person) msg1.oldO;
		String store = (String)msg1.newO;
		boolean change = false;
		PreparedStatement ps;
		ResultSet rs , rsP;

		try {
			/*check if there is a payment account for this user*/
			ps = conn.prepareStatement("SELECT * FROM person WHERE ID=? AND ID IN (SELECT ID FROM payment_account WHERE Store_ID=?);");
			ps.setString(1, customer.getUser_ID());
			ps.setString(2, store);
			rs = ps.executeQuery();
			if(rs.next())
			{
				/*get the details from payment account*/
				ps = conn.prepareStatement("SELECT * FROM payment_account WHERE ID=? AND Store_ID=?;");
				ps.setString(1, customer.getUser_ID());
				ps.setString(2, store);
				rsP = ps.executeQuery();
				rsP.next();
				if(!check_start_date(rsP.getString("Start_Date"), rsP.getString("Subscription")))
				{
					/*update the subscription to be Per Order*/
					ps = conn.prepareStatement("UPDATE payment_account SET Subscription='Per Order' WHERE ID=? AND Store_ID=?;");
					ps.setString(1, customer.getUser_ID());
					ps.setString(2, store);
					ps.executeUpdate();
					change = true;
				}
			}
			msg1.newO = change;
			
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * check if the number of days between current day to the start date of subscription is ok
	 * @param start_date
	 * @param subscription
	 * @return
	 */
	public boolean check_start_date(String start_date, String subscription)
	{		
		int day, month, year, days_toCheck;
		int sum_days;
		/*parse the date in DB*/
		day = Integer.parseInt(start_date.substring(0, 2));
		month = Integer.parseInt(start_date.substring(3, 5));
		year = Integer.parseInt(start_date.substring(6));
	
		Calendar myCalendar = new GregorianCalendar(year, month-1, day);
		Date myDate = myCalendar.getTime();
		Date date = new Date();
		
		if(subscription.equals("Per Order"))
			return true;
		else if(subscription.equals("Month"))
			days_toCheck = 30;
		else
			days_toCheck = 365;
		
		/*calculate the days between to dates*/
		sum_days = (int)((date.getTime()-myDate.getTime())/(1000*60*60*24));
		if(sum_days>days_toCheck)
			return false;
		return true;
	}

	/**
	 * this function update the survey answers after each customer that took the
	 * survey updated the Num_Of_Participant in the survey by on +1
	 * 
	 * @param msg
	 * @param con
	 * @param client
	 */
	public static void update_survey_answers(Object msg, Connection con, ConnectionToClient client) {
		Msg msg1 = (Msg) msg;
		Survey survey_answers = (Survey) msg1.oldO;
		Person person = (Person) msg1.newO;
		PreparedStatement ps;

		try {
			ps = con.prepareStatement("UPDATE " + msg1.getTableName()
					+ " SET A1=A1+?, A2=A2+? , A3=A3+? ,A4=A4+? , A5=A5+? , A6=A6+?, Num_Of_Participant =Num_Of_Participant+1  WHERE ID=?;");
			ps.setString(1, String.valueOf(survey_answers.getA1()));
			ps.setString(2, String.valueOf(survey_answers.getA2()));
			ps.setString(3, String.valueOf(survey_answers.getA3()));
			ps.setString(4, String.valueOf(survey_answers.getA4()));
			ps.setString(5, String.valueOf(survey_answers.getA5()));
			ps.setString(6, String.valueOf(survey_answers.getA6()));
			ps.setString(7, survey_answers.getID());
			ps.executeUpdate();

			msg1.newO = survey_answers;

			// add the user to the list of the comment survey
			PreparedStatement ps2 = con
					.prepareStatement("INSERT INTO comments_survey (`ID`, `Customer_ID`, `comment`) VALUES (?, ?, ?);");
			ps2.setString(1, survey_answers.getID());
			ps2.setString(2, person.getUser_ID());
			ps2.setString(3, msg1.freeField);
			ps2.executeUpdate();

			// client.sendToClient(msg1);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the different color/type for combobox in self item
	 * 
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void GetComboForSelfItem(Msg msg1, Connection conn, ConnectionToClient client) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<String> forCombo = new ArrayList<String>();
		String field = "";

		/* set the specific column in item table */
		if (msg1.getRole().equals("get combo colors"))
			field = "Color";
		else if (msg1.getRole().equals("get combo type"))
			field = "Type";
		try {
			/* set up and execute the select query */
			rs = conn.createStatement().executeQuery("SELECT * FROM item GROUP BY " + field + ";");

			while (rs.next())
				forCombo.add(rs.getString(field));

			msg1.newO = forCombo;
			client.sendToClient(msg1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Query for updating a given user's details and info
	 * @param msg1
	 * @param conn
	 * @param client
	 */
	public static void Update_user_details(Object msg, Connection con, ConnectionToClient client) {
		String ans = "Update done";
		Msg msg1 = (Msg) msg;
		Person old_d = (Person) msg1.oldO;
		Person new_d = (Person) msg1.newO;
		try {
			PreparedStatement ps = con.prepareStatement(
					"UPDATE " + msg1.getTableName() + " " + "SET FirstName=? , LastName=? , Password=? WHERE ID=?");

			/* insert the names to the query */
			ps.setString(1, new_d.getUser_name());
			ps.setString(2, new_d.getUser_last_name());
			ps.setString(3, new_d.getUser_password());
			ps.setString(4, old_d.getUser_ID());

			ps.executeUpdate();

			client.sendToClient(msg);

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (IOException x) {
			System.err.println("unable to send msg to client");
		}

	}

	
	/**
	 * executing all items details to set in catalog
	 * @param msg
	 * @param con
	 * @param client
	 */
	public static void ViewItems(Object msg, Connection con, ConnectionToClient client) {
		Msg msg1 = (Msg) msg;

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM payment_account WHERE ID=? AND Store_ID=?");
			ps.setString(1, msg1.freeField);
			ps.setString(2, msg1.freeField2);
			ResultSet rs = ps.executeQuery();
			if ((rs.next()))
				ViewItemsWithPaymentAccount(msg1, con, client);
			else
				ViewItemsWithoutPaymentAccount(msg1, con, client);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Show catalog for a customer without payment account,with no sales stores
	 * @param msg
	 * @param con
	 * @param client
	 */
	public static void ViewItemsWithoutPaymentAccount(Object msg, Connection con, ConnectionToClient client) {
		Msg msg1 = (Msg) msg;
		Statement stmt1;

		ArrayList<Item_In_Catalog> Itc_arr = new ArrayList<Item_In_Catalog>();
		try {
			stmt1 = con.createStatement();
			ResultSet rs1;
			rs1 = stmt1.executeQuery("SELECT * FROM item_in_catalog WHERE Status='Active'");
			while (rs1.next()) {
				Item_In_Catalog Itc = new Item_In_Catalog();
				MyFile f = new MyFile();
				Itc.setID(rs1.getString(1));
				Itc.setName(rs1.getString(2));
				Itc.setPrice(rs1.getFloat(3));
				Itc.setDescription(rs1.getString(4));
				f = getFileInfo(Itc.getID(),"Itc");
				Itc.setImage(f);
				Itc.setAmount(-1);
				Itc_arr.add(Itc);
			}
			rs1.close();
			msg1.newO = Itc_arr;
			client.sendToClient(msg1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Show catalog for a customer with payment account of specific store stock and sales
	 * @param msg
	 * @param con
	 * @param client
	 */
	public static void ViewItemsWithPaymentAccount(Object msg, Connection con, ConnectionToClient client) {

		Msg msg1 = (Msg) msg;
		Statement stmt1;
		ArrayList<Item_In_Catalog> Itc_arr = new ArrayList<Item_In_Catalog>();
		try {
			stmt1 = con.createStatement();
			ResultSet rs1, rs2, rs3;

			PreparedStatement ps = con
					.prepareStatement("SELECT Item_ID,Amount,Sale_ID FROM store WHERE ID=? AND Type=?");
			ps.setString(1, msg1.freeField2);
			ps.setString(2, "Catalog");
			rs1 = ps.executeQuery();

			while (rs1.next()) {
				PreparedStatement ps1 = con.prepareStatement("SELECT * FROM item_in_catalog WHERE ID=? AND Status=?");
				ps1.setString(1, rs1.getString(1));
				ps1.setString(2, "Active");
				rs2 = ps1.executeQuery();

				while (rs2.next()) {
					Item_In_Catalog Itc = new Item_In_Catalog();
					MyFile f = new MyFile();
					Itc.setID(rs2.getString(1));
					Itc.setName(rs2.getString(2));
					Itc.setPrice(rs2.getFloat(3));
					Itc.setDescription(rs2.getString(4));
					f = getFileInfo(Itc.getID(),"Itc");
					Itc.setImage(f);
					Itc.setAmount(rs1.getInt(2));
					Sale S = new Sale();
					if (!(rs2.getString(3) == null)) {
						PreparedStatement ps2 = con.prepareStatement("SELECT * FROM sales WHERE ID=?");
						ps2.setString(1, rs1.getString(3));
						rs3 = ps2.executeQuery();
						while (rs3.next()) {
							S.setID(rs3.getString(1));
							S.setDescription(rs3.getString(2));
							S.setDiscount(rs3.getString(3));
							Itc.setSale(S);
						} // while rs3
						rs3.close();
						ps2.close();
					} // if

					if (!(Itc.getSale().getID() == null)) {
						Itc_arr.add(0, Itc);
					} else
						Itc_arr.add(Itc);
				}
				rs2.close();
				ps1.close();
			}
			rs1.close();
			msg1.newO = Itc_arr;
			client.sendToClient(msg1);
		} // try

		catch (SQLException e) {
			e.printStackTrace();
		}

		catch (IOException x) {
			System.err.println("unable to send msg to client");
		}
	}

	/**
	 * convert image path to MyFile object
	 * @param id
	 * @return
	 */
	public static MyFile getFileInfo(String id,String type) {

		String fileLocation;		
		fileLocation = System.getProperty("user.dir") + File.separator + "Pictures" + File.separator + type + id
				+ ".jpg";
		
		MyFile to_send = new MyFile(id + ".jpg");
		to_send.setDescription(fileLocation);
		try {

			File newFile = new File(to_send.getDescription());

			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);

			to_send.initArray(mybytearray.length);
			to_send.setSize(mybytearray.length);

			bis.read(to_send.getMybytearray(), 0, mybytearray.length);

			bis.close();
			fis.close();

		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}

		return to_send;
	}

	/**
	 * set details update from gui to DB
	 * 
	 * @param con
	 * @param msg
	 * @param client
	 * @throws IOException
	 */
	public static void UpdateItem(Object msg, Connection con, ConnectionToClient client) throws IOException {

		Msg msg1 = (Msg) msg;
		Item_In_Catalog tmp = (Item_In_Catalog) msg1.newO;

		try {
			PreparedStatement ps = con
					.prepareStatement("UPDATE item_in_catalog SET Name=?,Price=?,Description=? WHERE ID=?");

			/* insert the names to the query */
			ps.setString(1, tmp.getName());
			ps.setString(2, "" + tmp.getPrice());
			ps.setString(3, tmp.getDescription());
			ps.setString(4, tmp.getID());
			ps.executeUpdate();
			ps.close();
			if (!(tmp.getImage() == null)) {

				CreateImage(tmp.getImage());
			}

			client.sendToClient(msg1);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				client.sendToClient("UPDATE faild");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * create image with MyFile object
	 * @param msg
	 */
	public static void CreateImage(MyFile msg) {
		MyFile mf = (MyFile) msg;
		System.out.println(mf);
		// Image img = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(System.getProperty("user.dir") + File.separator + "Pictures" + File.separator
					+ "Itc" + mf.getFileName());// (System.getProperty("user.dir")+"/Pic/" +

		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedOutputStream in = new BufferedOutputStream(fos);

		try {
			// +img = new Image(new ByteArrayInputStream(mf.getMybytearray()));

			in.write(mf.getMybytearray());
			in.close();
			fos.close();

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		int fileSize = ((MyFile) msg).getSize();
		System.out.println("Message received: " + msg);
		System.out.println("length " + fileSize);
	}

	/**
	 * change item status to deleted with update query
	 * @param msg
	 * @param con
	 * @param client
	 * @throws IOException
	 */
	public static void DeleteItem(Object msg, Connection con, ConnectionToClient client) throws IOException {
		Msg msg1 = (Msg) msg;
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE item_in_catalog SET Status=? WHERE ID=?");
			ps.setString(1, "Deleted");
			ps.setString(2, msg1.freeField);
			ps.executeUpdate();
			client.sendToClient(msg1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0]
	 *            The port number to listen on. Defaults to 5555 if no argument is
	 *            entered.
	 */
	public static void main  (String[] args)    {
		int port = 0; // Port to listen on
		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);
	 
 
		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
 
		
	}

 
// End of EchoServer class
 