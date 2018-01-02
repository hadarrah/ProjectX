package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SI_Add_Item_Controller implements Initializable, ControllerI {

	public Button add_items_B;
	public Button present_items_B;
	public Button back_B;
	public TextField min_TF, max_TF, amount_wanted_TF, in_stock_TF;
	//public TextArea item_list_TA;
	//public ListView<String> selected_items_LV;
	public ComboBox<String> color_CB, type_CB, select_item_CB;

	// public Msg msg;

	public String type, color;
	public float minp, maxp;

	public static ActionEvent event_log;

	public Product p; // The attributes selected so far.

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

	public void findItemsByAttributes(ActionEvent event) {

		/* save the event */
		event_log = new ActionEvent();
		event_log = event.copyFor(event.getSource(), event.getTarget());

		/* get values from CBs and TFs */
		type = type_CB.getValue().toString();
		color = color_CB.getValue().toString();
		minp = Float.parseFloat(min_TF.getText());
		maxp = Float.parseFloat(max_TF.getText());

		/* set product type and color */
		Product psearch = new Product();
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

		/* fillers */
		msg.oldO = "hi";
		msg.newO = "bye";

		msg.oldO = psearch;

		Login_win.to_Client.accept(msg);
	}

	public void setReturnedItems(Object message) {

		ArrayList<Product> products = (ArrayList<Product>) ((Msg) message).newO;
		String names[] = new String[products.size()];
		float price[] = new float[products.size()];
		
		for(int i=0; i<products.size(); i++) names[i]=products.get(i).getName();
		for(int i=0; i<products.size(); i++) price[i]=products.get(i).getPrice();
		
		ArrayList<String> al = new ArrayList<String>(Arrays.asList(names));
		ObservableList<String> ObsList = FXCollections.observableArrayList(al);

		select_item_CB.setItems(ObsList);

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
		Login_win.to_Client.setController(this);

		/* Set ComboBox */
		setType();
		setColor();
		/* Initialize listview */
		//selected_items_LV = new ListView<String>();

	}

}
