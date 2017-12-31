package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import action.ClientConsole;
import action.Msg;
import action.Person;
import action.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SI_Add_Item_Controller implements Initializable, ControllerI {

	public Button add_items_B;
	public Button present_items_B;
	public Button back_B;
	public TextField min_TF, max_TF, amount_wanted_TF, in_stock_TF, unit_price_TF;
	// public TextArea item_list_TA;
	// public ListView<String> selected_items_LV;
	public ComboBox<String> color_CB, type_CB, select_item_CB;
	public Label select_item_L, selection_missing_L, added_L;

	// public Msg msg;

	ControllerI prevPage;
	public String type, color;
	public float minp, maxp;

	public static ActionEvent event_log;

	ArrayList<Product> products = null; // Items from the most recent query.
	public Product p; // The attributes selected so far.

	public void addSelectedItem(ActionEvent event) {
		if (p!=null) {
	//	float amt = Float.parseFloat(amount_wanted_TF.toString());
		
		((Self_Item_Controller)prevPage).selectedProductsArr.add(p);
	
		String add = "You have added " + p.GetName();
		added_L.setText(add);
		added_L.setVisible(true);
		}
		}

	public void itemSelected(ActionEvent event) {

		String name = select_item_CB.getValue();
		float price = -1;
		// int id;
		System.out.println("You have selected: " + name);

		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).GetName().equals(name)) {
				// id = i;
				price = products.get(i).GetPrice();
				p = products.get(i);
				break;
			}
			// if(in_stock>0)
			this.amount_wanted_TF.setText("1");
		}

		unit_price_TF.setText(Float.toString(price));
		// in_stock_TF.setText();

	}

	public void findItemsByAttributes(ActionEvent event) {

		/* save the event */
		event_log = new ActionEvent();
		event_log = event.copyFor(event.getSource(), event.getTarget());

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

		/* set product type and color */
		Product psearch = new Product();
		psearch.SetColor(color);
		psearch.SetType(type);

		/* create the message to server */
		Msg msg = new Msg();

		msg.num1 = minp;
		msg.num2 = maxp;

		msg.setRole("find items color-type-price");
		msg.setSelect();
		msg.setTableName("item");
		msg.event = event;

		/* fillers */
		// msg.oldO = "hi";
		// msg.newO = "bye";

		msg.oldO = psearch;

		Login_win.to_Client.accept(msg);
	}

	public void setReturnedItems(Object message) {

		products = (ArrayList<Product>) ((Msg) message).newO;
		String ids[] = new String[products.size()];
		String names[] = new String[products.size()];
		float price[] = new float[products.size()];
		// int instock[] = new int[products.size()];

		for (int i = 0; i < products.size(); i++) {
			ids[i] = products.get(i).GetID();
			names[i] = products.get(i).GetName();
			price[i] = products.get(i).GetPrice();
		}

		ArrayList<String> al = new ArrayList<String>(Arrays.asList(names));
		ObservableList<String> ObsList = FXCollections.observableArrayList(al);

		select_item_CB.setItems(ObsList);

		if (products.size() > 0)
			select_item_L.setVisible(true);

	}

	public void setType() {
		String colors[] = { "Flower", "Vase", "Tape" };
		ArrayList<String> al = new ArrayList<String>(Arrays.asList(colors));
		ObservableList<String> list = FXCollections.observableArrayList(al);
		type_CB.setItems(list);
	}

	public void setColor() {
		String colors[] = { "Red", "Green", "Blue", "Yellow", "Purple", "Pink", "Orange" };
		ArrayList<String> al = new ArrayList<String>(Arrays.asList(colors));
		ObservableList<String> list = FXCollections.observableArrayList(al);
		color_CB.setItems(list);
	}

	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Self_Item_F.fxml");
	}

	public void move(ActionEvent event, String next_fxml) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(next_fxml));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
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
		selection_missing_L.setVisible(false);
		added_L.setVisible(false);
	}

}
