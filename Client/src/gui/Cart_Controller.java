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
import action.Item_In_Catalog;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Cart_Controller implements Initializable, ControllerI {

	public Button remove_items_B, order_B, back_B, undo_remove_B, clear_B;
	public TextField total_price_TF;
	public ListView<Item> items_selected_LV;
	public RadioButton yes_card_R, no_card_R;
	public ToggleGroup card_TG;
	public ComboBox<String> card_CB;
	public TextArea cardDesc_TA;
	public Label cbX_L,cselX_L;
	
	
	public boolean card=false;	//does the user want a card?
	public static String cardType=null;		//which type of card has the user selected
	public static String cardDesc;
	
	public static ActionEvent event_log;

	public static Cart userCart = Main_menu.userCart;

	// "Cart" for items selected so far
	public ArrayList<Item> selectedItemsArr = userCart.selectedItemsArr;

	// A map that wires an item for the selected amount. ( map.get(item)==amount )
	public Map<Item, Integer> itemToAmount = userCart.itemToAmount;

	Item selectedFromLV = null;
	ArrayList<Item> removedItems = new ArrayList<Item>();

	public float totalPrice = 0;

	
	/* Fucnctions */

	/**
	 * func: toggleCard 
	 * desc: function for radio buttons for selecting cards 
	 * yes-> allow CB, add to order. 
	 * no-> disable CB.
	 */
	public void toggleCard(ActionEvent e) {
		cselX_L.setVisible(false);
		if (card_TG.getSelectedToggle() == no_card_R) {
			card_CB.setDisable(true);
			this.setTextA();
			cbX_L.setVisible(false);
			card=false;
			return;
		} else if (card_TG.getSelectedToggle() == yes_card_R) {
			card_CB.setDisable(false);
			card_CB.setValue(this.cardType);
			
			if(cardType==null) {
			cardDesc_TA.setDisable(true);
			cardDesc_TA.setText("Select A Card type");
			cbX_L.setVisible(true);
			
			}
			else {
				if(this.cardDesc!=null)
					cardDesc_TA.setText(null);
				else {
					cardDesc_TA.setText(null);
					cardDesc_TA.setPromptText("Insert text");
					cardDesc_TA.setDisable(false);
				}
			card=true;
			}
		}

		return;
	}
		
	/**
	 * func: addItemToCart 
	 * desc: add an Item to the current user's cart
	 */
	public void addItemToCart(Item t) {

		// Add the item
		this.selectedItemsArr.add(t);

		// If item is self item, input it with the value of 1;
		if (t instanceof Self_Item)
			this.itemToAmount.put(t, 1);
		if (t instanceof Item_In_Catalog)
			this.itemToAmount.put(t, 1);

	}

	/**
	 * func: removeFromSelected 
	 * desc: remove the currently selected item from the
	 * ListView.
	 */
	public void removeFromSelected(ActionEvent event) {

		if (selectedFromLV != null) {
			if (selectedFromLV instanceof Self_Item) {
				removedItems.add(new Self_Item((Self_Item) selectedFromLV));
			} else {
				removedItems.add(selectedFromLV);
			}

			selectedItemsArr.remove(selectedFromLV);
			selectedFromLV = null;

			setSelected();
			setTotalPrice();
		}
	}

	/**
	 * func: undoRemove 
	 * desc: undo last remove action.
	 */
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

	/**
	 * func: clearAll 
	 * desc: clear current contents of cart and adding them to
	 * recently removed array.
	 */
	public void clearAll(ActionEvent event) {

		while (selectedItemsArr.size() > 0) {
			this.selectedFromLV = this.selectedItemsArr.get(0);
			removeFromSelected(event);
		}
	}

	/**
	 * func: setSelected 
	 * desc: set the selected items it the JavaFX ListView.
	 */
	public void setSelected() {

		String pnames[] = new String[selectedItemsArr.size()];

		// Fill names array
		for (int i = 0; i < selectedItemsArr.size(); i++) {
			pnames[i] = selectedItemsArr.get(i).getName();
		}

		if (pnames.length <= 0)
			clear_B.setDisable(true);
		else
			clear_B.setDisable(false);
		ObservableList<Item> items = FXCollections.observableArrayList(selectedItemsArr);
		items_selected_LV.setItems(items);

	}

	/**
	 * func: setTotalPrice desc: set the TextField with total price of the items in
	 * cart
	 */
	public void setTotalPrice() {
		try{
			total_price_TF.setText(Float.toString(userCart.calcTotalPrice()));
		}
		catch(NumberFormatException e) {
			System.out.println("problem with price calculation");
		total_price_TF.setText("0.0");
		}
	}

	/**
	 * func: getSelectedFromLV
	 * desc: get the item currently selected at the ListView
	 */
	public void getSelectedFromLV(ActionEvent event) {
		Item pr = items_selected_LV.getSelectionModel().getSelectedItem();
	}

	/**
	 * func: cardSelected
	 * desc: set the Card selected by the user
	 * */	
	public void cardSelected() {
		if( (String)card_CB.getValue() !=null) {
			cardType = (String) card_CB.getValue();
			cbX_L.setVisible(false);
			cardDesc_TA.setDisable(false);
			cardDesc_TA.setText("");
			cardDesc_TA.setPromptText("Insert text");
		}
	}
	
	/**
	 * func: setCardCB
	 * desc: sets the Card ComboBox
	 * */	
	public void setCardCB() {
		String[] types = {"Wedding","Birthday","Feel Well","Mourn","Funny"};
		ObservableList<String> list = FXCollections.observableArrayList(types);
		card_CB.setItems(list);
		card_CB.setDisable(true);
	}
	
	
	/**
	 * func: setTextA
	 * desc: Disables the TF, sets the prompt text and delimits the card description TF to 200 chars.
	 * */
	public void setTextA() {
		cardDesc_TA.setWrapText(true);
		cardDesc_TA.setDisable(true);
		cardDesc_TA.setText("No Card option is selected");
		cardDesc_TA.setTextFormatter(new TextFormatter<String>(change -> 
        change.getControlNewText().length() <= 45 ? change : null));//addTextLimiter(cardDesc_TA, 200);
	}
	
	/**
	 * func: setRadioB 
	 * desc: sets the radio buttons for yes/no for greeting card
	 */
	public void setRadioB() {

		card_TG = new ToggleGroup();
		this.yes_card_R.setToggleGroup(card_TG);
		this.no_card_R.setToggleGroup(card_TG);

		// Handler which calls wantDelivery whenever a type of delivery is selected.
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				toggleCard(e);
			}
		};

		yes_card_R.setOnAction(handler);
		no_card_R.setOnAction(handler);
	}

	/**
	 * func: order 
	 * desc: check if cart is empty, if empty then throw a pop up; if
	 * not empty move to order.
	 */
	public void order(ActionEvent event) throws IOException {
		if (this.userCart.isEmpty()) {

			Login_win.showPopUp("ERROR", "Empty Cart", "Your Cart is empty",
					"You can add product and items to your cart" + " using the options in the main menu");
		}
		else if (this.card_TG.getSelectedToggle()==null) {
			cselX_L.setVisible(true);
		}
		else {
			this.cardDesc=cardDesc_TA.getText();
			System.out.println("in Cart_Controller.order");
			move(event, main.fxmlDir + "Order_F.fxml");
		}
	}

	/**
	 * func: back desc: Move back to the main menu
	 */ 
	public void back(ActionEvent event) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource( main.fxmlDir + "Main_Menu_F.fxml"));
		Scene win1 = new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("/gui/Main_menu.css").toExternalForm());

		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();

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
	
	
	/**
	 * func: initialize desc: initialize all the relevant javafx and values.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Update current controller (Log_win.toClient == user's ClientConsole)
		 */
		Login_win.to_Client.setController(this);
		userCart = new Cart();
		setTotalPrice();	
		setCardCB();
		setRadioB();
		setTextA();
		cbX_L.setVisible(false);
		cselX_L.setVisible(false);
		
		

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
					if (item instanceof Self_Item) {
						if (item.getName() != null)
							setText(item.getName() + "  x 1");
						else
							setText("Self Item - "+((Self_Item)item).getType()+" x 1");
					} else
						setText(item.getName() + "  x " + item.getAmount());

				}
			}
		});

		if (selectedItemsArr.size() > 0)
			setSelected(); // Present selected item list from Items arraylist;

		setTotalPrice();
	}

}