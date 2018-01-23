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
	public RadioButton delivery_R, self_R;
	public ToggleGroup selectdelivery_TG;
	public TextField name_TF, address_TF, phoneShort_TF, phoneLong_TF, hour_TF, min_TF;
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
	public static Payment_Account acc;

	public String card = Cart_Controller.cardType;
	public String cardDesc = Cart_Controller.cardDesc;

	public static int orderid;

	public static String name, address, phone, time, date;

	private boolean isDelivery = false;
	private boolean backToMenu = false;
	private boolean noErrors = false;
	private boolean good = false;
	private boolean orderDone = false;
	private boolean deliveryDone = false;
	private boolean itemsInDB = false;
	private boolean cardInDB = false;
	private boolean badTime = false;

	public static Order order;

	/**Action function for Pay button
	/* 1)Initialise flags
	/* 2)Check for correct user input & send query
	/* 3)Process successful			*/
	public void goPay(ActionEvent event) throws IOException {
		
			//Initialize flags
		good = false;
		noErrors = false;
		orderDone = false;
		deliveryDone = false;
		itemsInDB = false;
		
		
		//Check for correct user input & send query

		checkUserOrder();
		
		if(isDelivery) {
			Delivery d = new Delivery();
			d.setAddress(this.address);
			d.setName(this.name);
			d.setOrder(this.order);
			d.setPhone(this.phone);
			order.setDelivery(d);
			order.setBoolDelivery(true);
		}
		
		// check if it is not frozen or blocked
		if (acc != null) {
			if (acc.getStatus() == "Block" || acc.getStatus() == "Frozen") {
				System.out.println("ERROR, Account is blocked or frozen (insertOrderToDB)");
				return;
			}
		}

		if (noErrors == true) {
				payment(event);
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
			else {
				nameX_L.setVisible(false);
				this.name = name_TF.getText();
			}

			// empty address
			if (this.address_TF.getText().trim().isEmpty()) {
				addX_L.setVisible(true);
			}
			else {
				addX_L.setVisible(false);
				this.address = address_TF.getText();
			}

			// emtpy or non-numeric phone
			if ((this.phoneShort_TF.getText()).trim().isEmpty()
					|| !(this.phoneShort_TF.getText()).matches("^[0-9]+$")
					|| (this.phoneLong_TF.getText()).trim().isEmpty()
					|| !(this.phoneLong_TF.getText()).matches("^[0-9]+$")) {
				phX_L.setVisible(true);
			}
			else {
				phX_L.setVisible(false);
				this.phone = phoneShort_TF.getText() + "-" + phoneLong_TF.getText();
			}
			
		
			// self collect
		} else if (selectdelivery_TG.getSelectedToggle() == this.self_R)
			isDelivery = false;

		// empty or non-numeric date & if 1>day>31 || 1>month>12
		if (this.date==null) { dX_L.setVisible(true); }
		else {
		     int day = Integer.parseInt(date.substring(0, 2));
		     int month = Integer.parseInt(date.substring(3, 5));
		     int year = Integer.parseInt(date.substring(6, 10));
		     
		     dX_L.setVisible(false);
		     
		     if(lyear+2<=year) dX_L.setVisible(true);
		     else if(lyear==year) {
		    	 if(month<lmonth) dX_L.setVisible(true);
		    	 else if(month==lmonth) {
		    		 if(day<lday) dX_L.setVisible(true);}
		     }
		     else 
		    	 this.date = datePick.getValue().format(dateFormatter);
		    	 
		} 
		
		if(hour_TF.getText()==null || min_TF.getText()==null
			||  hour_TF.getText().equals("") || min_TF.getText().equals("") )
			tX_L.setVisible(true);
		else {
			tX_L.setVisible(false);

			badTime=false;
			int day = Integer.parseInt(date.substring(0, 2));
			if(day==lday) {
				int omin=Integer.parseInt(min_TF.getText());
				int ohour=Integer.parseInt(hour_TF.getText());
				if(( (60*ohour - 60*lhour) + (omin-lmin) ) < 180 ) {
					tX_L.setVisible(true);
					Login_win.showPopUp("INFORMATION", "Cannot Provide Order", "We can deliver an order with a minimum of 3 hours since request",
							"");
					badTime=true;
				}
				
			}
			if(!badTime) {
				String hour = hour_TF.getText();
				String min = min_TF.getText();
				if(hour_TF.getText().length()<2) hour="0"+hour_TF.getText().length();
				if(min_TF.getText().length()<2) min="0"+min_TF.getText().length();
				this.time = hour + ":" + min; // 14:36
			}
		}
		

		if(		nameX_L.isVisible()
			||  addX_L.isVisible()
			||	phX_L.isVisible()
			||	otX_L.isVisible()
			||	dX_L.isVisible()
			||	tX_L.isVisible()  )
			return;
		// No errors
		else {
			noErrors = true;
		}

	}

	/** Get person's payment account*/
	public void getPaymentAcc() {
		Msg msg = new Msg();

		msg.setRole("get payment account for personID");
		msg.setTableName("payment_account");
		msg.setSelect();
		msg.oldO = this.customer.getUser_ID();
		msg.newO = Login_win.chosen_store;

		Login_win.to_Client.accept(msg);
	}
	
	/**Payment account info answer returned from server query*/
	public void get_payment_account(Object message) {
		Msg msg = (Msg) message;
		if(msg.newO == null)
			System.out.println("Problem with payment account (non-existing or incorrect in DB)");
		this.acc=(Payment_Account) msg.newO;

	}


	/**Action for delivery & self collect radio buttons*/
	public void wantDelivery(ActionEvent e) {
		if (selectdelivery_TG.getSelectedToggle() == delivery_R) {
			if (self_P.isVisible())
				self_P.setVisible(false);
			delivery_P.setVisible(true);
			order.setBoolDelivery(true);
//			setTotalPrice();
			return;
		} else if (selectdelivery_TG.getSelectedToggle() == self_R)
			if (delivery_P.isVisible()) {
				delivery_P.setVisible(false);
				order.setBoolDelivery(false);
				order.setDelivery(null);
			}
			
			String storeLoc="";
			if(Login_win.current_user.getStore().size()==1)
				storeLoc=Login_win.current_user.getStore().get(0);
			else
				for(String s : Login_win.current_user.getStore()) {
					System.out.println("s: "+s);
					if(s.endsWith(Login_win.chosen_store)) {
						storeLoc=s;
						break;
					}
				}
			
		store_L.setText(storeLoc);
		self_P.setVisible(true);
		return;
		}
	/**Get date from DatePicker*/
	public void getDate(ActionEvent e) {
	     this.date = datePick.getValue().format(dateFormatter);
	}

	/**Back button function*/
	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir+"Cart_F.fxml");
	}
	
	/**Payment button function*/
	public void payment(ActionEvent event) throws IOException {
		move(event, main.fxmlDir+"Payment_F.fxml");
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



	/**Set RadioButton Javafx*/
	public void setRadioB() {
		this.delivery_R.setToggleGroup(this.selectdelivery_TG);
		this.self_R.setToggleGroup(this.selectdelivery_TG);

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

	/**Set TextFields Javafx*/
	public void setTextF() {
		nameX_L.setVisible(false);
		addX_L.setVisible(false);
		phX_L.setVisible(false);
		otX_L.setVisible(false);
		dX_L.setVisible(false);
		tX_L.setVisible(false);
		
		delivery_P.setVisible(false);
		self_P.setVisible(false);

		addTextLimiter(hour_TF, 2);
		addTextLimiter(min_TF, 2);
	}
	
	/**Set DatePicker Javafx*/
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

	/**Text Limiters for max hour and min TextFields*/
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
	/**Initialize page function*/
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Update current controller (Log_win.toClient == user's ClientConsole)
		 */

		Login_win.to_Client.setController(this);

		this.selectdelivery_TG = new ToggleGroup();

		// get the payment acc
		getPaymentAcc();
		// Waiting for getpayment to get acc
		try {
			int i=0;
			while (acc == null) {
				Thread.sleep(10);
				i++;
				if(i>300) back(new ActionEvent());
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.order = new Order();
		order.setPersonid(customer.getUser_ID());
		order.setStatus("Active");
		order.setStoreid(acc.getStoreID());

		setRadioB();
		setTextF();
		setDatePicker();

	}

}