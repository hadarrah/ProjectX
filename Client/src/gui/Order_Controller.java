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

public class Order_Controller implements Initializable, ControllerI {

	public Button back_B, payment_B;
	public RadioButton delivery_R, self_R, creditcard_R, cash_R;
	public ToggleGroup selectdelivery_TG, paymentmethod_TG;
	public TextField total_price_TF, name_TF, address_TF, phoneShort_TF, phoneLong_TF, hour_TF, min_TF,totp_TF;
	public Pane delivery_P; // Pane which appears after the user selects delivery_R
	public Pane self_P, time_P;
	public Label store_L,otX_L,dX_L,tX_L,pmX_L,nameX_L,phX_L,addX_L;
	public DatePicker datePick;
	
	public int lday, lmonth, lyear, lhour, lmin;	
    public String pattern = "dd/MM/yyyy";
    public DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

	// Current user cart, synch in initialize().
	public Cart userCart = Cart_Controller.userCart;
	public Person customer = Main_menu.current_user;
	private Payment_Account acc;

	public static ActionEvent event;

	public float totalPrice = 0;
	public float deliveryPrice=20;
	public String card = Cart_Controller.cardType;
	public String cardDesc = Cart_Controller.cardDesc;

	public static int orderid;

	String name, address, phone, time, date;

	private boolean isDelivery = false;
	private boolean backToMenu = false;
	private boolean noErrors = false;
	private boolean good = false;
	private boolean orderDone = false;
	private boolean deliveryDone = false;
	private boolean itemsInDB = false;
	private boolean cardInDB = false;

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
			
			System.out.println("after insert card");
			
			
			good = true;

		}
			
		

		//Process successful
		//If everything went well -> order, delivery and items are in database
		if (good) {
			nameX_L.setVisible(false);
			addX_L.setVisible(false);
			phX_L.setVisible(false);
			otX_L.setVisible(false);
			dX_L.setVisible(false);
			tX_L.setVisible(false);
			pmX_L.setVisible(false);
			
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


	/** Check if the current users order details are valid */
	public void checkUserOrder() {

		// no order type selected
		if (selectdelivery_TG.getSelectedToggle() == null) {
			otX_L.setVisible(true);
			isDelivery = false;
		}
		else otX_L.setVisible(false);

		// delivery
		if (selectdelivery_TG.getSelectedToggle() == delivery_R) {
			// flag to indicate delivery wanted
			isDelivery = true;

			// empty name
			if (this.name_TF.getText().trim().isEmpty()) {
				nameX_L.setVisible(true);
			}
			else nameX_L.setVisible(false);

			// empty address
			if (this.address_TF.getText().trim().isEmpty()) {
				addX_L.setVisible(true);
			}
			else addX_L.setVisible(false);

			// emtpy or non-numeric phone
			if ((this.phoneShort_TF.getText()).trim().isEmpty()
					|| !(this.phoneShort_TF.getText()).matches("^[0-9]+$")
					|| (this.phoneLong_TF.getText()).trim().isEmpty()
					|| !(this.phoneLong_TF.getText()).matches("^[0-9]+$")) {
				phX_L.setVisible(true);
			}
			else phX_L.setVisible(false);

			// self collect
		} else if (selectdelivery_TG.getSelectedToggle() == this.self_R)
			isDelivery = false;

		// empty or non-numeric date & if 1>day>31 || 1>month>12
		if (this.date==null) { dX_L.setVisible(true); }
		else {
		     int day = Integer.parseInt(date.substring(0, 2));
		     int month = Integer.parseInt(date.substring(3, 5));
		     int year = Integer.parseInt(date.substring(6, 10));
		     System.out.println("day "+lday+" month "+lmonth+" year "+lyear);
		     
		     dX_L.setVisible(false);
		     
		     if(lyear+2<=year) dX_L.setVisible(true);
		     else if(lyear==year) {
		    	 if(month<lmonth) dX_L.setVisible(true);
		    	 else if(month==lmonth) {
		    		 if(day<lday) dX_L.setVisible(true);}
		     }
		} 
		
		if(hour_TF.getText()==null || min_TF.getText()==null
			||  hour_TF.getText().equals("") || min_TF.getText().equals("") )
			tX_L.setVisible(true);
		else {
			tX_L.setVisible(false);
			if(!(dX_L.isVisible())) {
			int day = Integer.parseInt(date.substring(0, 2));
			if(day==lday) {
				int omin=Integer.parseInt(min_TF.getText());
				int ohour=Integer.parseInt(hour_TF.getText());
				if(( (60*ohour - 60*lhour) + (omin-lmin) ) < 180 ) {
					tX_L.setVisible(true);
					Login_win.showPopUp("INFORMATION", "Cannot Provide Order", "We can deliver an order with a minimum of 3 hours since request",
							"");
				}}
					
			}
			
		}
		
		if (paymentmethod_TG.getSelectedToggle() == null) {
			pmX_L.setVisible(true);
		}
		else pmX_L.setVisible(false);
		// No errors
		if(		nameX_L.isVisible()
			||  addX_L.isVisible()
			||	phX_L.isVisible()
			||	otX_L.isVisible()
			||	dX_L.isVisible()
			||	tX_L.isVisible()
			||	pmX_L.isVisible() )
			return;
		else {
			noErrors = true;
		}

	}

	/** Get person's payment account*/
	public void getPaymentAcc(String personid) {
		Msg msg = new Msg();

		msg.setRole("get payment account for personID");
		msg.setTableName("payment_account");
		msg.setSelect();
		msg.oldO = personid;

		Login_win.to_Client.accept(msg);
	}

	/**request query to insert the new order*/
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

		order.setCard(card);

		order.setStoreid(acc.getStoreID()); // user's store id by payment_account registration
		
		this.time = hour_TF.getText() + ":" + min_TF.getText(); // 14:36

		order.setRequestdate(date);
		order.setRequesttime(time);
		if (this.paymentmethod_TG.getSelectedToggle() == cash_R)
			p = "Cash";
		else
			p = "Credit";
		order.setPayment(p);

		msg.setRole("insert order");
		msg.setInsert();
		order.setStatus("Requested");
		msg.oldO = order;

		Login_win.to_Client.accept(msg);

		// Order has been created
		// we insert the delivery (if exists) from within create_order_success
		// we insert all the items to DB from within create_order_success

	}

	/**insert the selected card to DB*/
	public void insertCardToDB() {
		Msg msg = new Msg();

		String oid = Integer.toString(this.orderid);
		String type = Cart_Controller.cardType;
		String text = Cart_Controller.cardDesc;

		msg.oldO = (Object) type;
		msg.newO = (Object) text;
		msg.freeField = oid;

		msg.setRole("insert card");
		msg.setInsert();

		
		Login_win.to_Client.accept(msg);

	}

	/**insert the selected delivery to DB*/
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

	/**Inserting all the items related to the order into the DB*/
	public void insertItemsToDB() {

		ArrayList<Item> items = Main_menu.userCart.selectedItemsArr;
		ArrayList<Item> newarr = new ArrayList<Item>();

		Map<Item,Integer> amounts = Main_menu.userCart.itemToAmount;
		Map<String,Integer> newamounts = new HashMap<String, Integer>();
		
		for(int i=0; i<items.size(); i++) {
			boolean newItem=true; //item is not in newarr->true;
			
			Item itemincart = items.get(i);

			System.out.println("curr item: "+itemincart.getType());
			
			if (itemincart instanceof Self_Item) { // if item is self_item
				System.out.println("inside SI"+itemincart.getType());
				Self_Item st = (Self_Item) itemincart;
				System.out.println("inside SI"+st.getType());
				if(st.getType()!="As Is")
				newarr.add(st);
				newamounts.put(st.getID(), 1);

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
			else {
				
				
				for(int k=0; k<newarr.size(); k++) {	//for every existing item in order, check if it is in
					
					Item check = newarr.get(k);
					if(itemincart.getID().equals(check.getID()))	//equals->compares ID
					{
						int amnt = itemincart.getAmount();	//how many of this item in the self item
						int have = newamounts.get(itemincart.getID());	//how many we have in new order amounts						
						int newtotal = amnt+have;		//new amount						
						newamounts.put(itemincart.getID(), newtotal);	//update the map
						newItem=false;
						k=newarr.size();					//exit the loop
					}
				}
				if(newItem) { //if item does not appear;
					int amnt = itemincart.getAmount();
					newarr.add(itemincart);
					newamounts.put(itemincart.getID(), amnt);
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

	/**Get user's payment account*/
	public void get_payment_account(Object msg) {
		Msg msg1 = (Msg) msg;

		if (msg1.newO == null) {
			System.out.println("ERROR, No payment account");
			return;
		}

		this.acc = (Payment_Account) msg1.newO;
	}
	
	/**Successful item insert to DB*/
	public void insert_items_success(Object msg) {
		int oid = (int) ((Msg) msg).num1;
		System.out.println("Items for order " + oid + " " + "been added!");
		itemsInDB = true;
	}
	/**Successful card insert to DB*/
	public void insert_card_success(Object msg) {
		float num1 = ((Msg) msg).num1;
		if (num1 == 1)
			System.out.println("Card has been inserted");
		cardInDB = true;
	}

	/** A function which receives the returned msg from server after inserting an
	/* order. */
	public void create_order_success(Object msg) {
		Order created = (Order) (((Msg) msg).newO);

		order.setId(created.getId());

		Order_Controller.orderid = Integer.parseInt(order.getId());

		System.out.println("order number: " + order.getId() + " has been created");

		orderDone = true;

	}

	/**Successful delivery insert to DB*/
	public void create_delivery_success(Object msg) {
		Delivery d = (Delivery) (((Msg) msg).newO);
		System.out.println(d.toString() + "\n for Order:" + d.getOrderid());
		order.setDelivery(d);
		
		deliveryDone=true;
	}

	/**Action for delivery & self collect radio buttons*/
	public void wantDelivery(ActionEvent e) {
		if (selectdelivery_TG.getSelectedToggle() == delivery_R) {
			if (self_P.isVisible())
				self_P.setVisible(false);
			delivery_P.setVisible(true);
			setTotalPrice();
			return;
		} else if (selectdelivery_TG.getSelectedToggle() == self_R)
			if (delivery_P.isVisible())
				delivery_P.setVisible(false);
			
			String storeLoc="";
			if(Login_win.current_user.getStore().size()==1)
				storeLoc=Login_win.current_user.getStore().get(0);
			else
				System.out.println("correct chosen store: "+Login_win.chosen_store);
				for(String s : Login_win.current_user.getStore()) {
					System.out.println("s: "+s);
					if(s.endsWith(Login_win.chosen_store)) {
						storeLoc=s;
						break;
					}
				}
			
		store_L.setText(storeLoc);
		self_P.setVisible(true);
		setTotalPrice();
		return;
		}
	/**Get date from DatePicker*/
	public void getDate(ActionEvent e) {
	     this.date = datePick.getValue().format(dateFormatter);
	}

	/**Set total price including delivery and subscription rates.*/
	public void setTotalPrice() {
		float price = userCart.calcTotalPrice();
		//price*=subscription
		if(delivery_P.isVisible())
			price+=deliveryPrice;
		
		totp_TF.setText(Float.toString(price));
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
		nameX_L.setVisible(false);
		addX_L.setVisible(false);
		phX_L.setVisible(false);
		otX_L.setVisible(false);
		dX_L.setVisible(false);
		tX_L.setVisible(false);
		pmX_L.setVisible(false);
		
		addTextLimiter(hour_TF, 2);
		addTextLimiter(min_TF, 2);
	}
	
	public void setDatePicker() {
		
		LocalDateTime now = LocalDateTime.now();
		lyear = now.getYear();
		lmonth = now.getMonthValue();
		lday = now.getDayOfMonth();
		lhour = now.getHour();
		lmin = now.getMinute();
		
		 datePick.setConverter(new StringConverter<LocalDate>() {
		     String pattern = "dd/MM/yyyy";
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		     {
		         datePick.setPromptText(pattern.toLowerCase());
		     }

		     @Override public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 });
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
		setDatePicker();
		setTotalPrice();
		delivery_P.setVisible(false);
		self_P.setVisible(false);
	}

}