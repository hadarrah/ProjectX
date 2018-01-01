package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import action.Msg;
import action.Product;
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

public class Self_Item_Controller implements Initializable, ControllerI {

	public Button add_items_B;
	public Button remove_items_B;
	public Button order_B;
	public Button cancel_B;
	// public TextArea items_selected_TA;
	public TextField description_TF;
	public TextField total_price_TF;
	public ListView<Product> items_selected_LV;

	public static ActionEvent event_log;

	public static ArrayList<Product> selectedProductsArr = new ArrayList<Product>();
	
	Product selectedFromLV = null;

	public void removeFromSelected(ActionEvent event) {
		
		if(selectedFromLV!=null) {
			selectedProductsArr.remove(selectedFromLV);
			selectedFromLV=null;
			setSelected();
		}
	}
	
	public void setSelected() {


		String pnames[] = new String[selectedProductsArr.size()];

		// Fill names array
		for (int i = 0; i < selectedProductsArr.size(); i++)
			pnames[i] = selectedProductsArr.get(i).GetName();

		ObservableList<Product> items = FXCollections.observableArrayList(selectedProductsArr);
		items_selected_LV.setItems(items);
		

	}
	
	public void getSelectedFromLV(ActionEvent event) {
		Product pr=items_selected_LV.getSelectionModel().getSelectedItem();
		
		System.out.println(pr.GetName() +" is selected");
		
	}

	public void cancel(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Main_Menu_F.fxml");
	}

	public void add_Items(ActionEvent event) throws IOException {
		move(event, main.fxmlDir + "Self_Item_Add_Items_F.fxml");
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
		
		/*Setting custom listener to ListView of selected items.*/
		items_selected_LV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
		    @Override
		    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
		        // Your action here
		        System.out.println("Selected item: " + newValue);
		        selectedFromLV=newValue;
		    }

		});
		
		/*Setting custom cell factory to present products in listview*/
		items_selected_LV.setCellFactory(param -> new ListCell<Product>() {
			@Override
			protected void updateItem(Product item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.GetName() == null) {
					setText(null);
				} else {
					setText(item.GetName());
				}
			}
		});


		if (selectedProductsArr.size() > 0)
			setSelected(); // Present selected item list from products arraylist;
	}

}
