package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import action.Item_In_Catalog;
import javafx.application.Platform;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

public class Payment_Controller implements Initializable, ControllerI {

	public TextField totp_TF, refund_TF, subscribtion_TF,sur_TF;
	public Label pmX_L, refund_L, card1_L,cardt_L, name_L, address_L, phone_L, store_add_L, disc_amn_L,sur_L;
	public Label del_p_L, del_pn_L;
	public RadioButton cash_R, creditcard_R;
	public ToggleGroup paymentmethod_TG;
	public Pane delivery_P, self_P;

	// Current user cart, synch in initialize().
	public Cart userCart = Cart_Controller.userCart;
	public Person customer = Main_menu.current_user;
	public Order orderP = Order_Controller.order;
	public Payment_Account accP = Order_Controller.acc;

	public float totalPrice = 0;
	public float deliveryPrice = 20;

	public static int orderid;

	public String name, address, phone, time, date, storeLoc;

	public Order order = Order_Controller.order;

	private boolean backToMenu = false;
	private boolean noErrors = false;
	private boolean good = false;
	private boolean orderDone = false;
	private boolean deliveryDone = false;
	private boolean itemsInDB = false;
	private boolean cardInDB = false;

	public void doPay(ActionEvent event) {

		// Initialize flags
		good = false;
		noErrors = false;
		orderDone = false;
		deliveryDone = false;
		itemsInDB = false;

		// Check for correct user input & send query

		checkUserOrder();

		if (noErrors == true) {
			insertOrderToDB();

			// Wait in incremets of 10ms for thread to finish the order process
			while (!orderDone)
				try {
					Thread.sleep(10);// wait
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if (order.haveDelivery()) {
				insertDeliveryToDB();

				// Wait in incremets of 10ms for thread to finish the delivery process
				while (!deliveryDone)
					try {
						Thread.sleep(10);// wait
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			insertItemsToDB();

			while (!itemsInDB)
				try {
					Thread.sleep(10);// wait
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if (order.getCard() != null && order.getCard() != "") {
				insertCardToDB();

				while (!cardInDB)
					try {
						Thread.sleep(10);// wait
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}


			good = true;

		}

		// Process successful
		// If everything went well -> order, delivery and items are in database
		if (good) {
			pmX_L.setVisible(false);

			String content;
			if (order.haveDelivery())
				content = "Your delivery should arrive up to 3 hours from the order request";
			else
				content = "Your items will be waiting for you at the requested order date and time";
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Order was created successfully!");
			alert.setHeaderText("Order done: " + Order_Controller.orderid);
			alert.setContentText("For more information, please contact Netanel Azulai\n" + "\n" + content
					+ "\n\nYou will be taken back to the main menu after pressing OK");
			Optional<ButtonType> result = alert.showAndWait();

			userCart.cleanCart();

			try {
				backMenu(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			System.out.println("There has been some problem in creating the order");
		}
	}

	/** Check if the current users order details are valid */
	public void checkUserOrder() {

		if (paymentmethod_TG.getSelectedToggle() == null) {
			pmX_L.setVisible(true);
		} else
			pmX_L.setVisible(false);
		// No errors
		if (pmX_L.isVisible())
			return;
		else {
			noErrors = true;
		}

	}

	/** request query to insert the new order */
	public void insertOrderToDB() {
		String p;
		Msg msg = new Msg();

		order.TimeNow(); // set current time for order starting time.
		// order.setPersonid(customer.getUser_ID());

		// if (isDelivery)
		// order.setBoolDelivery(true); // insert delivery after creation of order
		// else
		// order.setBoolDelivery(false);

		order.setTotprice(Float.parseFloat(totp_TF.getText()));

		order.setCard(Cart_Controller.cardType);

		// order.setStoreid(acc.getStoreID()); // user's store id by payment_account
		// registration

		order.setRequestdate(date);
		order.setRequesttime(time);
		if (this.paymentmethod_TG.getSelectedToggle() == cash_R)
			p = "Cash";
		else
			p = "Credit";
		order.setPayment(p);

		msg.setRole("insert order");
		msg.setInsert();
		order.setStatus("Active");
		msg.oldO = order;
		

		Login_win.to_Client.accept(msg);

		// Order has been created
		// we insert the delivery (if exists) from within create_order_success
		// we insert all the items to DB from within create_order_success

	}

	/** insert the selected card to DB */
	public void insertCardToDB() {
		Msg msg = new Msg();

		String oid = order.getId();
		String type = Cart_Controller.cardType;
		String text = Cart_Controller.cardDesc;

		msg.oldO = (Object) type;
		msg.newO = (Object) text;
		msg.freeField = oid;

		msg.setRole("insert card");
		msg.setInsert();

		Login_win.to_Client.accept(msg);

	}

	/** insert the selected delivery to DB */
	public void insertDeliveryToDB() {
		Msg msg = new Msg();
		Delivery d = order.getDelivery();

		this.name = d.getName();
		this.address = d.getAddress();
		this.phone = d.getPhone();
		d.setOrderid(Integer.parseInt(order.getId()));

		msg.oldO = d;
		msg.setRole("insert delivery");
		msg.setInsert();

		Login_win.to_Client.accept(msg);

	}

	/** Inserting all the items related to the order into the DB */
	public void insertItemsToDB() {

		ArrayList<Item> items = Main_menu.userCart.selectedItemsArr;
		ArrayList<Item> newarr = new ArrayList<Item>();

		Map<Item, Integer> amounts = Main_menu.userCart.itemToAmount;
		Map<String, Integer> newamounts = new HashMap<String, Integer>();

		for (int i = 0; i < items.size(); i++) {
			boolean newItem = true; // item is not in newarr->true;

			Item itemincart = items.get(i);


			if (itemincart instanceof Self_Item) { // if item is self_item
				Self_Item st = (Self_Item) itemincart;
				if (st.getType() != "As Is")
					newarr.add(st);
				newamounts.put(st.getID(), 1);

				for (int j = 0; j < st.items.size(); j++) { // go through all items in st

					Item t = st.items.get(j);

					for (int k = 0; k < newarr.size(); k++) { // for every existing item in order, check if it is in

						Item check = newarr.get(k);
						if (t.getID().equals(check.getID())) // equals->compares ID
						{
							int amnt = st.amounts.get(t); // how many of this item in the self item
							int have = newamounts.get(t.getID()); // how many we have in new order amounts

							int newtotal = amnt + have; // new amount

							newamounts.put(t.getID(), newtotal); // update the map
							newItem = false;
							k = newarr.size(); // exit the loop
						}
					}

					// else if (itemincart instanceof Item_In_Catalog) {}
					// else { //if it is an ordinary item should not happen

					if (newItem) { // if item does not appear;
						int amnt = st.amounts.get(t);
						newarr.add(t);
						newamounts.put(t.getID(), amnt);
					}
				}
			} else {

				for (int k = 0; k < newarr.size(); k++) { // for every existing item in order, check if it is in

					Item check = newarr.get(k);
					if (itemincart.getID().equals(check.getID())) // equals->compares ID
					{
						int amnt = itemincart.getAmount(); // how many of this item in the self item
						int have = newamounts.get(itemincart.getID()); // how many we have in new order amounts
						int newtotal = amnt + have; // new amount
						newamounts.put(itemincart.getID(), newtotal); // update the map
						newItem = false;
						k = newarr.size(); // exit the loop
					}
				}
				if (newItem) { // if item does not appear;
					int amnt = itemincart.getAmount();
					newarr.add(itemincart);
					newamounts.put(itemincart.getID(), amnt);
				}
			}
		}

		Msg msg = new Msg();

		msg.num1 = Integer.parseInt(order.getId());
		msg.setInsert();
		msg.setRole("insert items in order");

		msg.oldO = newarr;
		msg.newO = newamounts;

		Login_win.to_Client.accept(msg);
	}

	/** Successful item insert to DB */
	public void insert_items_success(Object msg) {
		int oid = (int) ((Msg) msg).num1;
		System.out.println("Items for order " + oid + " " + "been added!");
		itemsInDB = true;
	}

	/** Successful card insert to DB */
	public void insert_card_success(Object msg) {
		float num1 = ((Msg) msg).num1;
		if (num1 == 1)
			System.out.println("Card has been inserted");
		cardInDB = true;
	}

	/**
	 * A function which receives the returned msg from server after inserting an /*
	 * order.
	 */
	public void create_order_success(Object msg) {
		Order created = (Order) (((Msg) msg).newO);

		order.setId(created.getId());

		Order_Controller.orderid = Integer.parseInt(order.getId());

		System.out.println("order number: " + order.getId() + " has been created");

		orderDone = true;

	}

	/** Successful delivery insert to DB */
	public void create_delivery_success(Object msg) {
		Delivery d = (Delivery) (((Msg) msg).newO);
		System.out.println(d.toString() + "\n for Order:" + d.getOrderid());
		order.setDelivery(d);

		deliveryDone = true;
	}

	/** Set total price including delivery and subscription rates. */
	public void setTotalPrice() {
		float price = userCart.calcTotalPrice();

		double disc = 0;

		switch (accP.getSubscription()) {
		case "Year":
			disc = 1;
		case "Month":
			disc = 0.9;
		case "Per Order":
			disc = 0.65;
		}

		price *= disc;
		disc=100-disc*100;
		if(accP.getSubscription()!="Per Order")
		disc_amn_L.setText("You get "+disc+"% discount");

		if (accP.getRefund_sum() > 0) {
			if(price>accP.getRefund_sum()) {
			price -= accP.getRefund_sum();
			sur_TF.setText("0.0");
			}
			else {
				sur_TF.setText(Float.toString(accP.getRefund_sum()-price));
				price=0;
				/*Don't forget to update refund afterwards*/
			}
			refund_L.setVisible(true);
			refund_TF.setVisible(true);
			refund_TF.setText(Float.toString(accP.getRefund_sum()));
			sur_L.setVisible(true);
			sur_TF.setVisible(true);
		} else {
			refund_L.setVisible(false);
			refund_TF.setVisible(false);
			sur_L.setVisible(false);
			sur_TF.setVisible(false);
		}

		if (orderP.haveDelivery())
			price += deliveryPrice;

		totp_TF.setText(Float.toString(price));
	}

	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Order_F.fxml");
	}
	
	public void backMenu(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Main_menu_F.fxml");
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

	/** Set String for Javafx Labels */
	public void setString() {
		this.name = Order_Controller.name;
		this.address = Order_Controller.address;
		this.phone = Order_Controller.phone;
		this.time = Order_Controller.time;
		this.date = Order_Controller.date;
		this.storeLoc = Login_win.current_user.getStore().get(0);
	}

	/** Set Labels Javafx */
	public void setLabels() {
		// show delivery pane with relevant info (name, address, phone)
		if (order.haveDelivery()) {
			self_P.setVisible(false);
			delivery_P.setVisible(true);
			name_L.setText(name);
			address_L.setText(address);
			phone_L.setText(phone);
			del_pn_L.setText(Float.toString(deliveryPrice)+"  (is not included in discount or refund)");
			
		}

		else {
			delivery_P.setVisible(false);
			self_P.setVisible(true);
		}
		order.setCard(Cart_Controller.cardType);
		
		if(order.getCard()!="" && order.getCard()!=null)
		cardt_L.setText(order.getCard());
		else
			cardt_L.setText("No Card");
		
		store_add_L.setText(storeLoc);
	}

	/** Set RadioButton Javafx */
	public void setRadioB() {

		paymentmethod_TG = new ToggleGroup();

		cash_R.setToggleGroup(paymentmethod_TG);
		creditcard_R.setToggleGroup(paymentmethod_TG);

		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				payingMethod(e);
			}
		};

		cash_R.setOnAction(handler);
		creditcard_R.setOnAction(handler);

	}

	public void payingMethod(ActionEvent e) {
		if (paymentmethod_TG.getSelectedToggle() == cash_R) {
			order.setPayment("Cash");
			pmX_L.setVisible(false);
			return;
		} else if (paymentmethod_TG.getSelectedToggle() == creditcard_R) {
			order.setPayment("Credit");
			pmX_L.setVisible(false);
			return;
		}

		pmX_L.setVisible(true);
		return;
	}

	/** Set TextFields Javafx */
	public void setTextF() {
		pmX_L.setVisible(false);
		refund_TF.setDisable(true);

		refund_L.setVisible(false);
		refund_TF.setVisible(false);
		
		sur_L.setVisible(false);
		sur_TF.setVisible(false);
		sur_TF.setDisable(true);

		subscribtion_TF.setDisable(false);
		subscribtion_TF.setText(Order_Controller.acc.getSubscription());

		setTotalPrice();
	}

	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Update current controller (Log_win.toClient == user's ClientConsole)
		 */
		Login_win.to_Client.setController(this);

		setRadioB();
		setString();
		setLabels();
		setTextF();
	}

}