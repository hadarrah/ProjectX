package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import action.ClientConsole;
import action.Item;
import action.Msg;
import action.Person;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SI_Add_Item_Controller implements Initializable, ControllerI {

	public Button add_items_B, present_items_B, back_B;
	public TextField min_TF, max_TF, amount_wanted_TF, in_stock_TF, unit_price_TF;
	public ComboBox<String> color_CB, type_CB, select_item_CB;
	public Label select_item_L, selection_missing_L, added_L;

	ControllerI prevPage;
	public String type, color;
	public float minp, maxp;

	public static ActionEvent event_log;

	ArrayList<Item> products = null; // Items from the most recent query.
	public Item p; // The attributes selected so far && selected item.

	/**Add button function*/
	public void addSelectedItem(ActionEvent event) {

		boolean addItem = true; // Add the item? t=yes
		String add = ""; // Label string to indicate if item has been added
		boolean exists=false;

		// If an item has been selected (null if user just entered the page)
		if (p != null) {

			ArrayList<Item> productsArr = Self_Item_Controller.selectedProductsArr;
			HashMap<Item, Integer> amountMap = (HashMap<Item, Integer>) Self_Item_Controller.itemToAmount;
			// If user pressed the Add Item button twice -> ask him if he is sure
			if (productsArr.size() > 0 && p == productsArr.get(productsArr.size() - 1)) {
				exists=true;
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm Additional Same Item");
				alert.setHeaderText("Confirmation of item addition");
				alert.setContentText("You have already added " + p.getName() + "\nAre you sure you want to add another "
						+ p.getName() + "?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

				} else {
					addItem = false;
					add = p.getName() + " has not been added";
				}
			}

			// if user select Ok->add the item, green label.
			if (addItem) {
				add = "You have added " + p.getName();
				int oldAmn=0;
				if(exists) {
					oldAmn=amountMap.get(p);
				}
				int newAmn=Integer.parseInt(this.amount_wanted_TF.getText()) + oldAmn;
				amountMap.put(p,newAmn);
				if(exists) productsArr.get(productsArr.size() - 1).setAmount(newAmn);
				else productsArr.add(p);
				added_L.setTextFill(Color.web("#25a829"));
			} else // else-> don't add item, red label.
				added_L.setTextFill(Color.web("#ab0909"));

			added_L.setText(add);
			added_L.setVisible(true);
		}
	}

	/**
	 * func: itemSelected
	 * desc: update screen when selecting an item from CB
	 * */
	public void itemSelected(ActionEvent event) {

		String name = select_item_CB.getValue();
		float price = -1;

//		System.out.println("You have selected: " + name);

		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getName().equals(name)) {
				price = products.get(i).getPrice();
				p = products.get(i);
				p.setType(type_CB.getValue().toString());
				this.amount_wanted_TF.setText("1");
				unit_price_TF.setText(Float.toString(price));
				break;
			}

		}

	}

	
	/**
	 * func: findItemsByAttributes
	 * desc: checking for input errors, if pass-> requesting server for items
	 * */
	public void findItemsByAttributes(ActionEvent event) {
		
		
		newSearch();
		/* Preparing label string to show incase of incorrect inpur */
		String selmis = "Selection Missing: ";
		/* Assuming the selection is good */
		boolean badSel = false;
		/* Try-Catch to present the current errors */
		try {
			type = type_CB.getValue().toString(); // trying to pull value
		} catch (NullPointerException e) { // if it was not input
			selmis += "Type"; // add error to label string
			badSel = true; // selection wen't bad
		}
		try {
			color = color_CB.getValue().toString();
		} catch (NullPointerException e) {
			if (badSel) // if it isn't the first error
				selmis += ", "; // string cosmetics
			selmis += "Color";
			badSel = true;
		}
		try {
			minp = Float.parseFloat(min_TF.getText());
		} catch (NumberFormatException e) {
			if (badSel)
				selmis += ", ";
			selmis += "Min Price";
			badSel = true;
		}
		try {
			maxp = Float.parseFloat(max_TF.getText());
		} catch (NumberFormatException e) {
			if (badSel)
				selmis += ", ";
			selmis += "Max Price";
			badSel = true;
		}
		if (badSel) {
			selmis += ".";
			selection_missing_L.setText(selmis);
			selection_missing_L.setVisible(true);
			return;
		}

		else { // Make sure that we dont see the label
			selection_missing_L.setVisible(false);
		}

		if (minp >= maxp) {
			selection_missing_L.setText("Bad price range");
			selection_missing_L.setVisible(true);
			if (products != null)
				products.clear();
			return;
		}

		/* set product type and color */
		Item psearch = new Item();
		psearch.setColor(color);
		psearch.setType(type);

		/* create the message to server */
		Msg msg = new Msg();

		msg.num1 = minp;
		msg.num2 = maxp;
		msg.setRole("find items color-type-price");
		msg.setSelect();
		msg.setTableName("item");
		msg.event = event;
		msg.oldO = psearch;

		Login_win.to_Client.accept(msg);
	}

	
	/**
	 * func: setReturnedItems
	 * desc: set items which fit the users description from server
	 * */
	public void setReturnedItems(Object message) {

		products = (ArrayList<Item>) ((Msg) message).newO;
		// String ids[] = new String[products.size()];
		String names[] = new String[products.size()];
		// float price[] = new float[products.size()];
		// float instock[] = new float[products.size()];

		for (int i = 0; i < products.size(); i++) {
			// ids[i] = products.get(i).getID();
			names[i] = products.get(i).getName();
			// price[i] = products.get(i).getPrice();
			// instock[i] = products.get(i).getAmount();
		}

		ArrayList<String> al = new ArrayList<String>(Arrays.asList(names));
		ObservableList<String> ObsList = FXCollections.observableArrayList(al);

		select_item_CB.setItems(ObsList);

		if (products.size() > 0)
			select_item_L.setVisible(true);


		if (ObsList.size() <= 0) {
			// if query is empty (no items with chosen attr)
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					selection_missing_L.setText("There are no items with chosen attributes\n" + "select another");
					selection_missing_L.setVisible(true);
					select_item_L.setVisible(false);
					added_L.setVisible(false);
				}
			});

			products.clear();
			return;

		}

	}
	
	/**Sets when a new search is requested*/
	public void newSearch() {
		select_item_CB.setValue(null);
		amount_wanted_TF.setText("");
		unit_price_TF.setText("");
	}

	/**
	 * set the fields for type combobox
	 */
	public void setType() {
		Msg getType = new Msg();
		getType.setSelect();
		getType.setTableName("item");
		getType.setRole("get combo type");

		Login_win.to_Client.accept((Object) getType);
	}

	/**
	 * set the fields for color combobox
	 */
	public void setColor() {
		Msg getColors = new Msg();
		getColors.setSelect();
		getColors.setTableName("item");
		getColors.setRole("get combo colors");

		Login_win.to_Client.accept((Object) getColors);
	}

	/**
	 * get the message(arraylist) from server and set in the relevant combobox
	 * 
	 * @param msg
	 */
	public void setCombo(Object msg) {
		if (((Msg) msg).getRole().equals("get combo colors")) {
			ObservableList<String> list = FXCollections.observableArrayList((ArrayList<String>) (((Msg) msg).newO));
			color_CB.setItems(list);
		}
		if (((Msg) msg).getRole().equals("get combo type")) {
			ObservableList<String> list = FXCollections.observableArrayList((ArrayList<String>) (((Msg) msg).newO));
			type_CB.setItems(list);
		}
	}

	/**Back button function*/
	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Self_Item_F.fxml");
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		 * Update current controller (Log_win.toClient == user's ClientConsole)
		 */
		prevPage = Login_win.to_Client.mc;
		Login_win.to_Client.setController(this);

		/* Set ComboBox */
		setType();
		setColor();

		select_item_L.setVisible(false);
		selection_missing_L.setText("No Items Selected.");
		selection_missing_L.setVisible(true);
		added_L.setVisible(false);
		/*return back via ESC*/
		this.back_B.setCancelButton(true);
		
		this.present_items_B.setTooltip(new Tooltip("Click me after selecting all the attributes"));
		

	}
}