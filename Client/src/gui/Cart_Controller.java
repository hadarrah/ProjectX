package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import action.Msg;
import action.Person;
import action.Self_Item;
import action.Cart;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Cart_Controller implements Initializable, ControllerI {

	public Button remove_items_B, order_B, back_B, undo_remove_B, clear_B;
	public TextField total_price_TF;
	public ListView<Item> items_selected_LV;
	public static ActionEvent event_log;

	public static Cart userCart = Main_menu.userCart;

	// "Cart" for items selected so far
	public ArrayList<Item> selectedItemsArr = userCart.selectedItemsArr;

	// A map that wires an item for the selected amount. ( map.get(item)==amount )
	public Map<Item, Integer> itemToAmount = userCart.itemToAmount;

	Item selectedFromLV = null;
	ArrayList<Item> removedItems = new ArrayList<Item>();

	public float totalPrice = 0;

	public void addItemToCart(Item t) {

		// Add the item
		this.selectedItemsArr.add(t);

		// If item is self item, input it with the value of 1;
		if (t instanceof Self_Item)
			this.itemToAmount.put(t, 1);
	}

	public void removeFromSelected(ActionEvent event) {

		if (selectedFromLV != null) {

			removedItems.add(new Self_Item((Self_Item) selectedFromLV));
			selectedItemsArr.remove(selectedFromLV);
			selectedFromLV = null;

			setSelected();
			setTotalPrice();

		}
	}

	public void undoRemove(ActionEvent event) {
		if (!(removedItems.isEmpty())) {
			// Add last item which has been removed
			selectedItemsArr.add(removedItems.get(removedItems.size() - 1));
			// remove the un-removed item from the list
			removedItems.remove(removedItems.size() - 1);

			// set view
			setSelected();
			setTotalPrice();
		}
	}

	public void clearAll(ActionEvent event) {

		while (selectedItemsArr.size() > 0) {
			this.selectedFromLV = this.selectedItemsArr.get(0);
			removeFromSelected(event);
		}
	}

	public void setSelected() {

		String pnames[] = new String[selectedItemsArr.size()];
		System.out.println("items size:   " + selectedItemsArr.size());

		// Fill names array
		for (int i = 0; i < selectedItemsArr.size(); i++) {
			pnames[i] = selectedItemsArr.get(i).getName();
			System.out.println(i + "\t");
		}

		if (pnames.length <= 0)
			clear_B.setDisable(true);
		else
			clear_B.setDisable(false);
		ObservableList<Item> items = FXCollections.observableArrayList(selectedItemsArr);
		items_selected_LV.setItems(items);

	}

	public void setTotalPrice() {
		total_price_TF.setText(Float.toString(userCart.calcTotalPrice()));
	}

	public void getSelectedFromLV(ActionEvent event) {
		Item pr = items_selected_LV.getSelectionModel().getSelectedItem();

		System.out.println(pr.getName() + " is selected");

	}

	public void order(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Order_F.fxml");
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

	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Update current controller (Log_win.toClient == user's ClientConsole)
		 */
		Login_win.to_Client.setController(this);
		userCart = new Cart();

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

				if (empty || item == null) {
					setText(null);
				} else {
					if (item instanceof Self_Item)
						if (item.getName() != null)
							setText(item.getName() + "  x 1");
						else
							setText("Self Item  x 1");
				}
			}
		});

		if (selectedItemsArr.size() > 0)
			setSelected(); // Present selected item list from Items arraylist;

		setTotalPrice();
	}

}