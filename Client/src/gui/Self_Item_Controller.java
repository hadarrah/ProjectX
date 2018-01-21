package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Self_Item_Controller implements Initializable, ControllerI {

	public Button add_items_B, remove_items_B, add_to_cart_B, back_B;
	public TextField total_price_TF;
	public ListView<Item> items_selected_LV;
	public ComboBox<String> si_type_CB;
	public Label cbX;

	public String type = null;

	// Pointer to user's cart page
	public static Cart userCart = Main_menu.userCart;

	// Previously added item
	public Self_Item addedItem;

	// "Cart" for items selected so far
	public static ArrayList<Item> selectedProductsArr = new ArrayList<Item>();

	// A map that wires an item for the selected amount. ( map.get(item)==amount )
	public static Map<Item, Integer> itemToAmount = new HashMap<Item, Integer>();

	Item selectedFromLV = null;

	public float totalPrice;
	
	/**Moves the current self item to cart*/
	public void moveItemToCart() {
		//If user hasn't selected any items.
		if(si_type_CB.getValue()==null)
		{
			cbX.setVisible(true);
			return;
		}
		
		if(this.selectedProductsArr.size()<=0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("There are no items in your cart");
			alert.setHeaderText("No items added to the cart\n");
			alert.setContentText("\nPlease select items through 'Add Item' button options\n"+
								 "For more information, please contact Netanel Azulai\n");
			Optional<ButtonType> result = alert.showAndWait();
			
			return;			
		}
		
		Self_Item selfi = new Self_Item(this.selectedProductsArr, this.itemToAmount, this.type);
		selfi.setPrice(totalPrice);
		userCart.addItemToCart(selfi);
		this.addedItem = selfi;
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Item was successfully added to your Cart!");
		alert.setHeaderText("The requested item is now in your cart, you can create a new item now.\n");
		alert.setContentText("\nYou can access your cart from the Main Menu\n"+
							 "For more information, please contact Netanel Azulai\n");
		Optional<ButtonType> result = alert.showAndWait();
		
		clearList();
	}

	/**Clears current ListView*/
	public void clearList() {
		selectedProductsArr.clear();
		itemToAmount.clear();
		setSelected();
		setTotalPrice();
		this.type=null;
		si_type_CB.setValue(null);
	}
	
	/**Removes the selected items from ListView*/
	public void removeFromSelected(ActionEvent event) {

		if (selectedFromLV != null) {
			selectedProductsArr.remove(selectedFromLV);
			selectedFromLV = null;
			setSelected();
			setTotalPrice();
		}
	}

	/**Sets the selected items in the list view*/
	public void setSelected() {

		String pnames[] = new String[selectedProductsArr.size()];

		// Fill names array
		for (int i = 0; i < selectedProductsArr.size(); i++)
			pnames[i] = selectedProductsArr.get(i).getName();

		ObservableList<Item> items = FXCollections.observableArrayList(selectedProductsArr);
		items_selected_LV.setItems(items);

	}

	/**Action for when a type is selected from CB*/
	public void typeSelected(ActionEvent e) {
		if(si_type_CB.getValue()!=null) {
		this.type=si_type_CB.getValue();
		cbX.setVisible(false);
		}
	}
	
	/**Set total price in TextField*/
	public void setTotalPrice() {
		totalPrice = 0;
		for (Item p : selectedProductsArr) {
			float itemsprice = p.getPrice() * itemToAmount.get(p);
			totalPrice += itemsprice;
		}

		total_price_TF.setText(Float.toString(totalPrice));
	}

	/**Get currently selected item from ListView*/
	public void getSelectedFromLV(ActionEvent event) {
		Item pr = items_selected_LV.getSelectionModel().getSelectedItem();
	}
	
	/**Set type ComboBox when query returns from server*/
	public void setTypeCB() {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("As Is");
		arr.add("Flower Arrangement");
		arr.add("Flower Pot");
		arr.add("Bride's Bouquet");
		arr.add("Ordinary Bouquet");
		
		ObservableList<String> list = FXCollections.observableArrayList(arr);
		si_type_CB.setItems(list);

	}

	/**Back to main Menu*/
	public void back(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Main_Menu_F.fxml");
	}

	/**Move to Add Items page*/
	public void add_Items(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Self_Item_Add_Items_F.fxml");
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

		/* Setting custom listener to ListView of selected items. */
		items_selected_LV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
			@Override // What happens if an item is selected from the ListView:
			public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
				// set selected item as newVal
				selectedFromLV = newValue;
			}

		});

		/* Setting custom cell factory to present products in listview */
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

		if (selectedProductsArr.size() > 0)
			setSelected(); // Present selected item list from products arraylist;

		setTotalPrice();
		setTypeCB();
		cbX.setVisible(false);
	}

}