package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import action.Msg;
import action.Order;
import action.Person;
import action.Self_Item;
import action.Cart;
import action.Delivery;
import action.Item;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.ComboBoxListCell;
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

	// Current user cart
	public static Cart userCart = Cart_Controller.userCart;
	public Person customer = Login_win.current_user;

	public float totalPrice = 0;

	String name, address, phone, time, date;
	
	Order order;

	public void doPay(ActionEvent event) {
		checkUserOrder(event);
	}

	public void checkUserOrder(ActionEvent event) {

		// no order type selected
		if (selectdelivery_TG.getSelectedToggle() == null) {
			Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
					"Please select order type");
			
			return;
		}
		
		else if (selectdelivery_TG.getSelectedToggle() == delivery_R) {
			
			 if (this.name_TF.getText().trim().isEmpty()) {
				Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
						"Please input name");
			}

			else if (this.address_TF.getText().trim().isEmpty()) {
				Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
						"Please input delivery address");
				return;
			}
			 
			else if ((this.phoneShort_TF.getText()).trim().isEmpty() || !(this.phoneShort_TF.getText()).matches("^[0-9]+$")
						|| (this.phoneLong_TF.getText()).trim().isEmpty()
						|| !(this.phoneLong_TF.getText()).matches("^[0-9]+$")) {
					Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
							"Please check phone number for missing fields or use of letters");
					return;
				}
			 	
			}

		if ((this.day_TF.getText()).trim().isEmpty() || !(this.day_TF.getText()).matches("^[0-9]+$")
				|| (this.month_TF.getText()).trim().isEmpty() || !(this.month_TF.getText()).matches("^[0-9]+$")) {
			Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
					"Please check date for missing fields or use of letters");
		} else if (paymentmethod_TG.getSelectedToggle() == null)
			Login_win.showPopUp("ERROR", "Error", "One of the details werent filled correctly",
					"Please select Payment Method");

		// No errors
		else {
			//if(Login_win.showPopUp("CONFIRMATION", "Payment", "Are you ok with paying for the current order?",
			//		"select yes to pay for the deal, select no to get back to the order window") != true)
			
			String p;
			order = new Order();
			Msg msg = new Msg();
			
			
			/***/
			Random r = new Random();
			order.setId("113");
			/***/
			//order.setOrderid(static id++);
			
			
			order.TimeNow(); //set current time for order starting time.
			order.setPersonid(customer.getUser_ID());
			order.setBoolDelivery(false);
			this.time = hour_TF.getText() +":"+ min_TF.getText();  // 14:36
			this.date = day_TF.getText() +"."+ month_TF.getText(); //02.01
			order.setRequestdate(date);
			order.setRequesttime(time);
			//order.setTotprice();
			if(this.paymentmethod_TG.getSelectedToggle() == cash_R)	p="Cash";
			else p="Credit";
			order.setPayment(p);
			//order.setStoreid(storeid); -> need to add sid to person
			
			//if user selected the delivery option
			if(selectdelivery_TG.getSelectedToggle() == delivery_R) {
			this.name = name_TF.getText();
			this.address = address_TF.getText();
			this.phone = phoneShort_TF.getText() + "-" + phoneLong_TF.getText();
			
			/*New Delivery Request*/
			Delivery delivery = new Delivery(name, address, phone);
			delivery.setOrder(order);
			order.setDelivery(delivery);
			
			System.out.println(order.TimeNow());
			
			msg.oldO=delivery;
			msg.setRole("insert delivery");

			

			//Login_win.to_Client.accept(msg);
			}
			

			
			
			msg.oldO=order;
			msg.setInsert();
			msg.setRole("insert order");
			msg.event=event;
			
			System.out.println("CTP1");
			
			Login_win.to_Client.accept((Object)msg);

		}
	}
	
	public void create_order_success(Object msg) {
		Order order = (Order)msg;
		
		Login_win.showPopUp("INFORMATION", "Order Created Successfully", "Order Number "+order.getId()+" has been succesffully"
				+ " created", "");
	}

	public void insertOrderToDB() {
	}

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

		setRadioB();
		setTextF();
		delivery_P.setVisible(false);
		self_P.setVisible(false);

		// setTotalPrice();
	}

}