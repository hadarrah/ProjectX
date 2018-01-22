package gui;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.sun.corba.se.impl.ior.GenericTaggedProfile;
import action.Msg;
import action.MyFile;
import action.Person;
import action.Survey;
import action.Item;
import action.Item_In_Catalog;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class View_Catalog_Controller implements ControllerI, Initializable {

	public Label lblSale;
	public Label lblAmount;
	public Label lblOutOfStock;
	public Label xName;
	public Label xDescription;
	public Label xPrice;
	public Label xImage;
	public Button Prev_B;
	public Button Save_B;
	public Button AddSave_B;
	public Button back_B;
	public Button Next_B;
	public Button Edit_B;
	public Button Add_B;
	public Button Delete_B;
	public Button AddToCart_B;
	public Button OK_B;
	public Button Pic_B;
	public Button AddCancel_B;
	public Button SetAmount_B;
	public Button OkSaveAmount_B;
	public ComboBox<String> cbxAmount;
	public TextField txtID;
	public TextField txtName;
	public TextField txtPrice;
	public TextField txtAmount;
	public TextArea txtDescription;
	public Text txtCatalog;
	public Text txtCounter;
	public Pane borderPane;
	public Pane ManagePane;
	public ImageView Itemimg;
	public ImageView PrimPane;
	public static ArrayList<Item_In_Catalog> Itc;
	public int Itc_counter = 0;
	public int view_counter = 0;
	public int ChangePicture = 0;
	public static Person current_user;
	public static ObservableList<String> list;
	public static String chosenStore;
	public static Item_In_Catalog tmp = new Item_In_Catalog();
	public File f = null;

	/**
	 * set which window return to
	 * 
	 * @param event
	 * @throws IOException
	 */

	public void back(ActionEvent event) throws IOException {
		if (Managment_Controller.ManagmentFlage == 1) {
			Login_win.chosen_store = chosenStore;
			ResetCatalog();
		}
		Itc.clear();
		Itc_counter = 0;
		view_counter = 0;
		if (Managment_Controller.ManagmentFlage == 2)
			Managment_Controller.ManagmentFlage = 0;
		move(event, main.fxmlDir + "Main_menu_F.fxml");
	}

	/**
	 * how to open another window
	 * 
	 * @param event
	 * @param next_fxml
	 * @throws IOException
	 */
	public void move(ActionEvent event, String next_fxml) throws IOException {
		Parent menu;
		menu = FXMLLoader.load(getClass().getResource(next_fxml));
		Scene win1 = new Scene(menu);
		Stage win_1 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		win_1.setScene(win1);
		win_1.show();

		// close window by X button //
		win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Msg msg = new Msg();
				Person user_logout = current_user;
				msg.setRole("user logout");
				msg.setTableName("person");
				msg.setUpdate();
				msg.oldO = user_logout;
				Login_win.to_Client.accept(msg);
			}
		});
	}

	/**
	 * init msg with "select" query
	 */
	public void init() {
		Next_B.setDisable(false);
		ResetStyleLable();
		ResetXlable();
		AddCancel_B.setVisible(false);
		AddSave_B.setVisible(false);
		if (Managment_Controller.ManagmentFlage == 1) { //if chain employee
			Edit_B.setDisable(false);
			Delete_B.setDisable(false);
		}
		if (Managment_Controller.ManagmentFlage == 2) {//if store employee
			SetAmount_B.setVisible(true);
		}
		Msg msg = new Msg();
		msg.setRole("View all catalog items");
		msg.setSelect();
		msg.setTableName("item_in_catalog");
		msg.freeField = current_user.getUser_ID();// save current user id
		msg.freeField2 = gui.Login_win.chosen_store; // save chosen store id
		Login_win.to_Client.accept(msg);
	}

	/**
	 * set amount to specific item in catalog
	 */
	public void SetAmount() {
		SetAmount_B.setVisible(false);
		OkSaveAmount_B.setVisible(true);
		txtAmount.setEditable(true);
		txtAmount.setStyle("-fx-border-color: red ;");

	}

	/**
	 * after setting amount, check input and create update query
	 */
	public void SaveSetAmount() {
		try {
			Msg msg = new Msg();
			Item_In_Catalog itc = new Item_In_Catalog();
			int amount;
			amount = Integer.parseInt(txtAmount.getText());				
			itc.setID(txtID.getText());
			if(lblSale.isVisible()&&amount==0)
				Login_win.showPopUp("ERROR", "Error Message", "Please delete sale first or set amount greater than 0", "");
			else {
			txtAmount.setStyle("");
			txtAmount.setEditable(false);
			itc.setAmount(amount);
			msg.setUpdate();
			msg.setRole("update amount");
			msg.newO = itc;
			msg.freeField = Login_win.chosen_store;
			Login_win.to_Client.accept(msg);
			}
		} catch (NumberFormatException e) {
			Login_win.showPopUp("ERROR", "", "Wrong Amount input", "");
		}
	}

	/** 
	 * set catalog to update  mode
	 */
	public void UpdateCatalog() {
		chosenStore = Login_win.chosen_store;
		Login_win.chosen_store = null; // show catalog with no dependency on specific store
		ManagePane.setVisible(true);
		init();
	}

	/**
	 * set catalog to edit mode
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void EditCatalog(ActionEvent event) throws IOException {
		ResetXlable();
		SetRedStyle();
		EditableTextField();
		txtID.setDisable(false);
		Save_B.setVisible(true);
		Pic_B.setVisible(true);
		Delete_B.setDisable(true);
		Add_B.setDisable(true);
	}

	/**
	 * choose a picture to specific item
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void ChangePicture(ActionEvent event) throws IOException {
		ChangePicture = 1;
		FileChooser.ExtensionFilter i = new FileChooser.ExtensionFilter("Image Files", "*.jpg");// option to choose only
																								// jpg files.
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(i);
		fileChooser.setTitle("Choose a picture");
		f = fileChooser.showOpenDialog(null);
		if (!(f == null)) {
			String s = f.getPath();
			MyFile mf = new MyFile(txtID.getText() + ".jpg");
			mf.setDescription(s);
			mf = getFileInfo(mf);
			tmp.setImage(mf);
		} else
			ChangePicture = 0;
	}

	/**
	 * convert image to MyFile object
	 * 
	 * @param mf
	 * @return
	 */
	public static MyFile getFileInfo(MyFile mf) {

		try {
			File newFile = new File(mf.getDescription());
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			mf.initArray(mybytearray.length);
			mf.setSize(mybytearray.length);
			bis.read(mf.getMybytearray(), 0, mybytearray.length);
			bis.close();
			fis.close();

		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}
		return mf;
	}

	/**
	 * save changes after editing item
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void Save(ActionEvent event) throws IOException {

		Msg msg = new Msg();
		if (txtPrice.getText().equals(""))
			txtPrice.setText("0.0");
		if (txtName.getText().isEmpty() || txtDescription.getText().equals("") || txtPrice.getText().equals("0.0")) {
			Login_win.showPopUp("ERROR", "", "Empty Fields", "");
			if (txtName.getText().equals(""))
				xName.setVisible(true);
			else {
				xName.setVisible(false);
			}
			if (txtDescription.getText().equals(""))
				xDescription.setVisible(true);
			else {
				xDescription.setVisible(false);
			}
			if (txtPrice.getText().equals("0.0"))
				xPrice.setVisible(true);
			else {
				xPrice.setVisible(false);
			}

		} else {
			if (!(txtPrice.getText().equals("0.0"))) {
				try {
					tmp.setPrice(Float.parseFloat(txtPrice.getText()));
					xPrice.setVisible(false);
					ResetStyleLable();
					ResetXlable();
					ResetEditableTextField();
					if (ChangePicture == 0)
						tmp.setImage(null);
					tmp.setID(txtID.getText());
					tmp.setName(txtName.getText());
					tmp.setDescription(txtDescription.getText());
					msg.setUpdate();
					msg.setRole("update item in catalog");
					msg.newO = tmp;
					Login_win.to_Client.accept(msg);
				} catch (NumberFormatException e) {
					Login_win.showPopUp("ERROR", "", "Wrong Price input", "");
				}
			}
		}
	}

	/**
	 * show message after update
	 * 
	 * @param msg
	 */
	public void update_item_success() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Login_win.showPopUp("INFORMATION", "Message", "Update Done successfully", "");
				Save_B.setVisible(false);
			}
		});
		Pic_B.setVisible(false);
		ChangePicture = 0;
		view_counter=0;
		Delete_B.setDisable(false);
		Add_B.setDisable(false);
		init();

	}

	/**
	 * change item status to deleted
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void DeleteCatalog(ActionEvent event) throws IOException {
		Msg msg = new Msg();
		msg.freeField = txtID.getText();
		msg.setRole("delete item from catalog");
		msg.setUpdate();
		Login_win.to_Client.accept(msg);

	}

	/**
	 * show message after amount update
	 * @param msg
	 */
	public void set_item_amount_success() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Login_win.showPopUp("INFORMATION", "Message", "Update Done successfully", "");
				OkSaveAmount_B.setVisible(false);				
				init();				
			}
		});

	}

	/**
	 * show message after delete
	 * 
	 * @param msg
	 */
	public void delete_item_success() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Login_win.showPopUp("INFORMATION", "Message", "Delete Done successfully", "");
				if (!(view_counter == 0))
					view_counter=0;			
				init();				
				}
		});		
		
	}

	/**
	 * show message after insert new item to catalog
	 * 
	 * @param msg
	 */
	public void insertNewItemInCatalogSuccess() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Login_win.showPopUp("INFORMATION", "Message", "Item successfully added", "");
			}
		});
		Delete_B.setDisable(false);
		Edit_B.setDisable(false);
		init();
	}

	/**
	 * Add a new item to catalog
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void AddNewItemToCatalog(ActionEvent event) throws IOException {
		Delete_B.setDisable(true);
		Edit_B.setDisable(true);
		ClearText();
		txtAmount.setVisible(false);
		EditableTextField();
		Itemimg.setImage(null);
		Pic_B.setVisible(true);
		AddSave_B.setVisible(true);
		AddCancel_B.setVisible(true);
		txtName.setEditable(true);
		SetRedStyle();
		EditableTextField();
	}

	/**
	 * save new item, with consideration if picture was added
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void SaveNewItem(ActionEvent event) throws IOException {

		Msg msg = new Msg();
		if (txtPrice.getText().equals(""))
			txtPrice.setText("0.0");
		if (txtName.getText().isEmpty() || txtDescription.getText().equals("") || txtPrice.getText().equals("0.0")
				|| f == null) {
			Login_win.showPopUp("ERROR", "", "Empty Fields", "");
			if (f == null)
				xImage.setVisible(true);
			else
				xImage.setVisible(false);

			if (txtName.getText().equals(""))
				xName.setVisible(true);
			else
				xName.setVisible(false);

			if (txtDescription.getText().equals(""))
				xDescription.setVisible(true);
			else
				xDescription.setVisible(false);

			if (txtPrice.getText().equals("0.0"))
				xPrice.setVisible(true);
			else
				xPrice.setVisible(false);
		} else {
			if (!(txtPrice.getText().equals("0.0"))) {
				try {
					tmp.setPrice(Float.parseFloat(txtPrice.getText()));
					xPrice.setVisible(false);
					ResetStyleLable();
					ResetXlable();
					ResetEditableTextField();
					tmp.setID(txtID.getText());
					tmp.setName(txtName.getText());
					tmp.setDescription(txtDescription.getText());
					msg.setInsert();
					msg.setRole("insert a new item in catalog");
					msg.newO = tmp;
					Login_win.to_Client.accept(msg);
				} catch (NumberFormatException e) {
					Login_win.showPopUp("ERROR", "", "Wrong Price input", "");
				}
			}
		}
	}

	/**
	 * set text field border in red, to mark required filed
	 */
	public void SetRedStyle() {
		txtName.setStyle("-fx-border-color: red ;");
		txtDescription.setStyle("-fx-border-color: red ;");
		txtPrice.setStyle("-fx-border-color: red ;");
	}

	/**
	 * clear fields for add a new item
	 */
	public void ClearText() {
		txtID.clear();
		txtName.clear();
		txtDescription.clear();
		txtPrice.clear();
	}

	/**
	 * change editable mode for updating and inserting item
	 */
	public void EditableTextField() {
		txtName.setEditable(true);
		txtDescription.setEditable(true);
		txtPrice.setEditable(true);
	}

	/**
	 * reset editable mode to false(Uneditable)
	 */
	public void ResetEditableTextField() {
		txtName.setEditable(false);
		txtDescription.setEditable(false);
		txtPrice.setEditable(false);
	}

	/**
	 * reset X labels to false
	 */
	public void ResetXlable() {
		xName.setVisible(false);
		xDescription.setVisible(false);
		xPrice.setVisible(false);
		xImage.setVisible(false);
	}

	/**
	 * reset text field border to default(with no color)
	 */
	public void ResetStyleLable() {
		txtName.setStyle("");
		txtDescription.setStyle("");
		txtPrice.setStyle("");
	}

	/**
	 * Reset catalog view, reload catalog
	 */
	public void ResetCatalog() {
		Login_win.chosen_store = chosenStore;
		Managment_Controller.ManagmentFlage = 0;
		AddToCart_B.setVisible(true);
		ManagePane.setVisible(false);

	}

	/**
	 * setting first item in catalog values for opening the catalog
	 * 
	 * @param message
	 */
	public void initCatalog(Object message) {
		Msg tmp = (Msg) message;
		Itc = (ArrayList<Item_In_Catalog>) tmp.newO;
		Itc_counter = Itc.size();
		if (Itc_counter == 1) {
			Prev_B.setDisable(true);
			Next_B.setDisable(true);
		}		
		Prev_B.setDisable(true);
		SetCounter(view_counter + 1, Itc_counter);

		SetDetailsGui(Itc.get(view_counter)); // default view is the first item in the array
	}

	/**
	 * setting the required item into the catalog
	 * 
	 * @param It-
	 *            Item_In_Catalog Object
	 */
	public void SetDetailsGui(Item_In_Catalog It) {
		if (Managment_Controller.ManagmentFlage == 1) {
			AddToCart_B.setVisible(false);
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				if (It.getAmount() == -1) {
					txtAmount.setVisible(false);
					lblAmount.setVisible(false);
				}
				txtID.setText(It.getID());
				txtName.setText(It.getName());
				txtPrice.setText("" + It.getPrice());
				txtDescription.setText(It.getDescription());
				txtAmount.setText("" + It.getAmount());
				if (It.getAmount() == 0) {
					lblOutOfStock.setVisible(true);
					AddToCart_B.setDisable(true);
				} else {
					lblOutOfStock.setVisible(false);
					AddToCart_B.setDisable(false);
				}
				Image img = CreateImage(It.getImage());
				Itemimg.setImage(img);
				if (!(It.getSale().getID() == null)) {

					lblSale.setVisible(true);
					lblSale.setText(" Sale: " + (It.getSale().getDiscount() + "%"));

				} else
					lblSale.setVisible(false);
				AddToCart_B.setVisible(true);
				if (!(It.getAmount() == -1) && !(It.getAmount() == 0)) {
					ArrayList<String> Amount = new ArrayList<String>();
					for (int i = 1; i <= Itc.get(view_counter).getAmount(); i++) {
						Amount.add("" + i);
					}
					list = FXCollections.observableArrayList(Amount);
				}
			}
		});

	}

	/**
	 * create an image with byte array
	 * 
	 * @param msg
	 * @return
	 */
	public Image CreateImage(Object msg) {
		MyFile mf = (MyFile) msg;
		System.out.println(mf);
		Image img = null;
		img = new Image(new ByteArrayInputStream(mf.getMybytearray()));
		int fileSize = ((MyFile) msg).getSize();
		System.out.println("Message received: " + msg);
		System.out.println("length " + fileSize);
		return img;
	}

	/**
	 * show the next item into the catalog by clicking next button
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void nextItem(ActionEvent event) throws IOException {
		if (!((Itc.get(view_counter).getAmount()) == -1))
			list.clear();
		cbxAmount.setVisible(false);
		OK_B.setVisible(false);
		Prev_B.setDisable(false);
		view_counter++;
		SetCounter(view_counter + 1, Itc_counter);
		if (view_counter == Itc_counter - 1)
			Next_B.setDisable(true);
		SetDetailsGui(Itc.get(view_counter));
	}

	/**
	 * show the preview item into the catalog by clicking prev button
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void prevItem(ActionEvent event) throws IOException {
		if (!(Itc.get(view_counter).getAmount() == -1))
			list.clear();
		cbxAmount.setVisible(false);
		OK_B.setVisible(false);
		if (view_counter < Itc_counter)
			Next_B.setDisable(false);
		view_counter--;
		SetCounter(view_counter + 1, Itc_counter);
		if (view_counter == 0)
			Prev_B.setDisable(true);
		SetDetailsGui(Itc.get(view_counter));
	}

	/**
	 * set catalog's counters
	 * 
	 * @param view
	 * @param Itc
	 */
	public void SetCounter(int view, int Itc) {
		txtCounter.setText("<" + (view_counter + 1) + "/" + Itc_counter + ">");
	}

	/**
	 * Add current item from catalog to cart by clicking add button and choose
	 * amount
	 */
	public void AddToCart() {

		// if user does'nt have payment account
		if (Itc.get(view_counter).getAmount() == -1) {
			Login_win.showPopUp("INFORMATION", "Message", "You have to creat a payment account",
					"Please contact your store manager");
			// if user has payment account->select amount from combo box
		} else {
			cbxAmount.setItems(list);
			cbxAmount.getSelectionModel().selectFirst();
			cbxAmount.setVisible(true);
			AddToCart_B.setVisible(false);
			OK_B.setVisible(true);
		}
	}

	/**
	 * after select amount, add item to cart
	 */
	public void OK() {
		Login_win.showPopUp("CONFIRMATION", "Message", "Item successfully added", " ");
		OK_B.setVisible(false);
		AddToCart_B.setVisible(true);
		cbxAmount.setVisible(false);
		Item_In_Catalog it = Itc.get(view_counter);
		String value;
		value = cbxAmount.getValue();
		// it.setID(txtID.getText());
		it.setAmount(Integer.parseInt(value));
		// it.setPrice(Float.parseFloat(txtPrice.getText()));
		it.setType("Catalog");
		// it.setName(txtName.getText());
		Main_menu.userCart.addItemToCart(it);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * update the current controller to be view catalog controller in general
		 * ClientConsole instance
		 */
		current_user = gui.Login_win.current_user;
		Login_win.to_Client.setController(this);
		if (Managment_Controller.ManagmentFlage == 1) {
			UpdateCatalog();
		} else
			init();
	}

}
