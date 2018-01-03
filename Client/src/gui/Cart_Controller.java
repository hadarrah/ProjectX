package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import action.Msg;
import action.Item;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.Stage;

public class Cart_Controller implements Initializable, ControllerI {

	public Button remove_items_B, order_B, cancel_B;
	public TextField total_price_TF;
	public ListView<Item> items_selected_LV;
	public static ActionEvent event_log;

	// "Cart" for items selected so far
	public static ArrayList<Item> selectedItemsArr = new ArrayList<Item>();

	// A map that wires an item for the selected amount. ( map.get(item)==amount )
	public static Map<Item, Integer> itemToAmount = new HashMap<Item, Integer>();

	Item selectedFromLV = null;
	
	public float totalPrice;

	
	
	public void removeFromSelected(ActionEvent event) {

		if (selectedFromLV != null) {
			selectedItemsArr.remove(selectedFromLV);
			selectedFromLV = null;
			setSelected();
			setTotalPrice();
		}
	}

	public void setSelected() {

		String pnames[] = new String[selectedItemsArr.size()];

		// Fill names array
		for (int i = 0; i < selectedItemsArr.size(); i++)
			pnames[i] = selectedItemsArr.get(i).getName();

		ObservableList<Item> items = FXCollections.observableArrayList(selectedItemsArr);
		items_selected_LV.setItems(items);

	}
	
	public void setTotalPrice() {
		totalPrice=0;
		for(Item p : selectedItemsArr)
			totalPrice+=p.getPrice();
		
		total_price_TF.setText(Float.toString(totalPrice));
	}

	public void getSelectedFromLV(ActionEvent event) {
		Item pr = items_selected_LV.getSelectionModel().getSelectedItem();

		System.out.println(pr.getName() + " is selected");

	}

	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Main_Menu_F.fxml");
	}

	public void move(ActionEvent event, String next_fxml) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(next_fxml));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();
	}

	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Update current controller (Log_win.toClient == user's ClientConsole)
		 */
		Login_win.to_Client.setController(this);

		/* Setting custom listener to ListView of selected items. */
		items_selected_LV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
			@Override // What happens if an item is selected from the ListView:
			public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
				// set selected item as newVal
				selectedFromLV = newValue;
			}

		});

		/* Setting custom cell factory to present Items in listview */
		items_selected_LV.setCellFactory(param -> new ListCell<Item>() {
			@Override
			protected void updateItem(Item item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + " x " + itemToAmount.get(item));
				}
			}
		});

		if (selectedItemsArr.size() > 0)
			setSelected(); // Present selected item list from Items arraylist;
		
		setTotalPrice();
	}

}