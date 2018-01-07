package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import action.Msg;
import action.Order;
import action.Payment_Account;
import action.Person;
import action.Self_Item;
import action.Cart;
import action.Delivery;
import action.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Order_Controller implements Initializable, ControllerI {

	public Button back_B, payment_B;
	public RadioButton delivery_R, self_R, creditcard_R, cash_R;
	public ToggleGroup selectdelivery_TG, paymentmethod_TG;
	public TextField total_price_TF, name_TF, address_TF, phoneShort_TF, phoneLong_TF, day_TF, month_TF, hour_TF,
			min_TF;
	public Pane delivery_P; // Pane which appears after the user selects delivery_R
	public Pane self_P, time_P;

	// Current user cart, synch in initialize().
	public Cart userCart = Cart_Controller.userCart;
	public Person customer = Main_menu.current_user;
	private Payment_Account acc;

	public static ActionEvent event;

	public float totalPrice = 0;

	public static int orderid;

	String name, address, phone, time, date;

	private boolean isDelivery = false;
	private boolean backToMenu = false;
	private boolean noErrors = false;
	private boolean itemsInOrder = false;
	private boolean good = false;
	private boolean orderDone = false;
	private boolean deliveryDone = false;
	private boolean itemsInDB = false;

	public Order order = null;

	/**Action function for Pay button
	/* 1)Initialise flags
	/* 2)Check for correct user input & send query
	/* 3)Process successful			*/
	public void doPay(ActionEvent event) {
		this.event = event;
		
		//Initialize flags

		good = false;
		order = null;
		noErrors = false;
		orderDone = false;
		deliveryDone = false;
		itemsInDB = false;
		
		
		//Check for correct user input & send query

		checkUserOrder();

		if (noErrors == true) {
			insertOrderToDB();

			//Wait in incremets of 10ms for thread to finish the order process
			while (!orderDone)
				try {
					Thread.sleep(10);// wait
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(order.haveDelivery()) {
				insertDeliveryToDB();
				
				//Wait in incremets of 10ms for thread to finish the delivery process	
				while(!deliveryDone)
					try {
					Thread.sleep(10);// wait
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			insertItemsToDB();
			
			good = true;

		}

		//Process successful
		//If everything went well -> order, delivery and items are in database
		if (good) {
			String content;
			if (order.haveDelivery())
				content = "Your delivery should arrive up to 3 hours from the order request";
			else
				content = "Your items will be waiting for you in 3 hours from the order request";
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Order was created successfully!");
			alert.setHeaderText("Order done: " + orderid);
			alert.setContentText("For more information, please contact Netanel Azulai\n" + "\n" + content
					+ "\n\nYou will be taken back to the main menu after pressing OK");
			Optional<ButtonType> result = alert.showAndWait();

			userCart.cleanCart();

			try {
				back(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			System.out.println("There has been some problem in creating the order");
		}
	}

	public void checkUserOrder() {

		// no order type selected
		if (selectdelivery_TG.getSelectedToggle() == null) {
			Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
					"Please select order type");
			isDelivery = false;

			return;
		}

		// delivery
		else if (selectdelivery_TG.getSelectedToggle() == delivery_R) {
			// flag to indicate delivery wanted
			isDelivery = true;

			// empty name
			if (this.name_TF.getText().trim().isEmpty()) {
				Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
						"Please input name");
				return;
			}

			// empty address
			else if (this.address_TF.getText().trim().isEmpty()) {
				Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
						"Please input delivery address");
				return;
			}

			// emtpy or non-numeric phone
			else if ((this.phoneShort_TF.getText()).trim().isEmpty()
					|| !(this.phoneShort_TF.getText()).matches("^[0-9]+$")
					|| (this.phoneLong_TF.getText()).trim().isEmpty()
					|| !(this.phoneLong_TF.getText()).matches("^[0-9]+$")) {
				Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
						"Please check phone number for missing fields or use of letters");
				return;
			}

			// self collect
		} else if (selectdelivery_TG.getSelectedToggle() == this.self_R)
			isDelivery = false;

		// empty or non-numeric date & if 1>day>31 || 1>month>12
		if ((this.day_TF.getText()).trim().isEmpty() || !(this.day_TF.getText()).matches("^[0-9]+$")
				|| (Integer.parseInt(this.day_TF.getText())) > 31 || (Integer.parseInt(this.day_TF.getText())) < 1
				|| (Integer.parseInt(this.month_TF.getText())) > 12 || (Integer.parseInt(this.month_TF.getText())) < 1
				|| (this.month_TF.getText()).trim().isEmpty() || !(this.month_TF.getText()).matches("^[0-9]+$")) {
			Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
					"Please check date for missing fields or use of letters");
			return;

		} else if (paymentmethod_TG.getSelectedToggle() == null) {
			Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
					"Please select Payment Method");
			return;
		}
		// No errors
		else {
			noErrors = true;

			// try {
			// Thread.sleep(100);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			/** insertOrderToDB(); */

		}

	}

	// Get person's payment account(input: person's id)
	public void getPaymentAcc(String personid) {
		Msg msg = new Msg();

		msg.setRole("get payment account for personID");
		msg.setTableName("payment_account");
		msg.setSelect();
		msg.oldO = personid;

		Login_win.to_Client.accept(msg);
	}

	// request query to insert the new order
	public void insertOrderToDB() {
		String p;
		order = new Order();
		Msg msg = new Msg();

		// check if it is not frozen or blocked
		if (acc != null) {
			if (acc.getStatus() == "Block" || acc.getStatus() == "Frozen") {
				System.out.println("ERROR, Account is blocked or frozen (insertOrderToDB)");
				return;
			}
		}

		order.TimeNow(); // set current time for order starting time.
		order.setPersonid(customer.getUser_ID());

		if (isDelivery)
			order.setBoolDelivery(true); // insert delivery after creation of order
		else
			order.setBoolDelivery(false);

		order.setTotprice(userCart.totalPrice);

		order.setStoreid(acc.getStoreID()); // user's store id by payment_account registration

		this.time = hour_TF.getText() + ":" + min_TF.getText(); // 14:36
		this.date = day_TF.getText() + "." + month_TF.getText(); // 02.01
		order.setRequestdate(date);
		order.setRequesttime(time);
		if (this.paymentmethod_TG.getSelectedToggle() == cash_R)
			p = "Cash";
		else
			p = "Credit";
		order.setPayment(p);

		// order.setStoreid(storeid); -> need to add sid to person

		msg.setRole("insert order");
		msg.setInsert();
		order.setStatus("Requested");
		msg.oldO = order;

		Login_win.to_Client.accept(msg);

		// Order has been created
		// we insert the delivery (if exists) from within create_order_success
		// we insert all the items to DB from within create_order_success

	}

	public void insertDeliveryToDB() {
		Msg msg = new Msg();
		Delivery delivery = new Delivery();

		this.name = name_TF.getText();
		this.address = address_TF.getText();
		this.phone = phoneShort_TF.getText() + "-" + phoneLong_TF.getText();

		/* New Delivery Request */
		delivery = new Delivery(name, address, phone);
		delivery.setOrder(order);
		delivery.setOrderid(orderid);
		order.setDelivery(delivery);

		msg.oldO = delivery;
		msg.setRole("insert delivery");
		msg.setInsert();

		Login_win.to_Client.accept(msg);

	}

	// Inserting all the items related to the order into the DB
	public void insertItemsToDB() {

		ArrayList<Item> items = Main_menu.userCart.selectedItemsArr;
		ArrayList<Item> newarr = new ArrayList<Item>();

		Map<Item,Integer> amounts = Main_menu.userCart.itemToAmount;	/**We will need this for catalog items-> if more than one bought*/
		Map<String,Integer> newamounts = new HashMap<String, Integer>();
		
		for(int i=0; i<items.size(); i++) {
			boolean newItem=true; //item is not in newarr->true;
			
			Item itemincart = items.get(i);

			if(itemincart instanceof Self_Item) {	//if item is self_item
				Self_Item st = (Self_Item)itemincart;
				//System.out.println("st size: "+st.items.size());

				for(int j=0; j<st.items.size();j++) {	//go through all items in st
					
					Item t = st.items.get(j);

					for(int k=0; k<newarr.size(); k++) {	//for every existing item in order, check if it is in
												
						Item check = newarr.get(k);
						if(t.getID().equals(check.getID()))	//equals->compares ID
						{
							int amnt = st.amounts.get(t);	//how many of this item in the self item
							int have = newamounts.get(t.getID());	//how many we have in new order amounts
							
							int newtotal = amnt+have;		//new amount
							
							newamounts.put(t.getID(), newtotal);	//update the map
							newItem=false;
							k=newarr.size();					//exit the loop
						}
					}
					
//					else if (itemincart instanceof Item_In_Catalog) {}
//					else { //if it is an ordinary item should not happen
					
					if(newItem) { //if item does not appear;
						int amnt = st.amounts.get(t);
						newarr.add(t);
						newamounts.put(t.getID(), amnt);			
				}
				}
			}
		}
		
		
		Msg msg = new Msg();

		msg.num1 = orderid;
		msg.setInsert();
		msg.setRole("insert items in order");

		msg.oldO = newarr;
		msg.newO = newamounts;

		for(Item t:newarr) System.out.println(t.getName()+" "+newamounts.get(t.getID()));

		Login_win.to_Client.accept(msg);
	}

	public void get_payment_account(Object msg) {
		Msg msg1 = (Msg) msg;

		if (msg1.newO == null) {
			System.out.println("ERROR, No payment account");
			return;
		}

		this.acc = (Payment_Account) msg1.newO;
	}

	public void insert_items_success(Object msg) {
		int oid = (int) ((Msg) msg).num1;
		System.out.println("Items for order " + oid +" " + "been added!");
		itemsInOrder = true;
	}

	// A function which receives the returned msg from server after inserting an
	// order.
	public void create_order_success(Object msg) {
		Order created = (Order) (((Msg) msg).newO);

		order.setId(created.getId());

		Order_Controller.orderid = Integer.parseInt(order.getId());

		System.out.println("order number: " + order.getId() + " has been created");

		orderDone = true;

	}

	public void create_delivery_success(Object msg) {
		Delivery d = (Delivery) (((Msg) msg).newO);
		System.out.println(d.toString() + "\n for Order:" + d.getOrderid());
		order.setDelivery(d);
		
		deliveryDone=true;
	}

	// Action for delivery & self collect radio buttons
	public void wantDelivery(ActionEvent e) {
		if (selectdelivery_TG.getSelectedToggle() == delivery_R) {
			if (self_P.isVisible())
				self_P.setVisible(false);
			delivery_P.setVisible(true);
			return;
		} else if (selectdelivery_TG.getSelectedToggle() == self_R)
			if (delivery_P.isVisible())
				delivery_P.setVisible(false);
		self_P.setVisible(true);
		return;
	}

	public void setTotalPrice() {
		// Cart.calcTotalPrice() calculates according to subscription
		total_price_TF.setText(Float.toString(userCart.calcTotalPrice()));
	}

	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Main_Menu_F.fxml");
	}

	/**
	 * General function for the movement between the different windows
	 * 
	 * @param event
	 * @param next_fxml
	 *            = string of the specific fxml
	 * @throws IOException
	 */
	public void move(ActionEvent event, String next_fxml) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(next_fxml));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();

		// close window by X button
		win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Msg msg = new Msg();
				Person user_logout = Login_win.current_user;
				msg.setRole("user logout");
				msg.setTableName("person");
				msg.setUpdate();
				msg.oldO = user_logout;
				Login_win.to_Client.accept(msg);
			}
		});
	}

	public void setRadioB() {

		this.delivery_R.setToggleGroup(this.selectdelivery_TG);
		this.self_R.setToggleGroup(this.selectdelivery_TG);

		this.cash_R.setToggleGroup(paymentmethod_TG);
		this.creditcard_R.setToggleGroup(paymentmethod_TG);

		// Handler which calls wantDelivery whenever a type of delivery is selected.
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				wantDelivery(e);
			}
		};

		delivery_R.setOnAction(handler);
		self_R.setOnAction(handler);
	}

	public void setTextF() {
		addTextLimiter(day_TF, 2);
		addTextLimiter(month_TF, 2);
		addTextLimiter(hour_TF, 2);
		addTextLimiter(min_TF, 2);
	}

	// Limit character length in TextFields
	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
	}

	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Update current controller (Log_win.toClient == user's ClientConsole)
		 */
		

		Login_win.to_Client.setController(this);


		this.selectdelivery_TG = new ToggleGroup();
		this.paymentmethod_TG = new ToggleGroup();

		// get the payment acc
		getPaymentAcc(customer.getUser_ID());
		// Waiting for getpayment to get acc

		try {
			while (acc == null)
				Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		setRadioB();
		setTextF();
		delivery_P.setVisible(false);
		self_P.setVisible(false);

		// setTotalPrice();
	}

}