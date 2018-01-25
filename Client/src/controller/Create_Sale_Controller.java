package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.javafx.tk.Toolkit;

import entity.Msg;
import entity.Person;
import entity.Sale;
import entity.Survey;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Create_Sale_Controller implements Initializable,ControllerI{

		public TextArea description_Text;
		public Label invalid_detailsL_description, invalid_detailsL_Item;
		public Label store_ID_L,invalid_detailsL_Discount, invalid_detailsL_description_length, invalid_detailsL_Discount2;
		public ListView<String> item_list;
		public Button create_sale_B, back_B;
		public TextField discount_Text;
		public static ActionEvent event_log;

		/**
		 * handle with pressing "create sale"
		 * @param event
		 */
		public void create_Sale(ActionEvent event) {

			/*save the event*/
	    	event_log =new ActionEvent();		 
			event_log=event.copyFor(event.getSource(), event.getTarget());
			
			/*set the error label*/
	    	invalid_detailsL_description.setVisible(false);
	    	invalid_detailsL_description_length.setVisible(false);
	    	invalid_detailsL_Discount.setVisible(false);
	    	invalid_detailsL_Item.setVisible(false);
	    	invalid_detailsL_Discount2.setVisible(false);
	    	
	    	/*get the selected participants items from listview*/
	    	ObservableList<String> selected_items = item_list.getSelectionModel().getSelectedItems();
	    	
	    	/*set description/discount from user input*/
			String description , discount;
			description = description_Text.getText();
			discount=discount_Text.getText();
			
			/*check input from user*/
			if(!isInteger(discount))
			{
				invalid_detailsL_Discount.setVisible(true);
				return;
			}
			if(Integer.parseInt(discount)<=0 || Integer.parseInt(discount)>=100)
			{
				invalid_detailsL_Discount2.setVisible(true);
				return;
			}
			if(selected_items.isEmpty())
			{
				invalid_detailsL_Item.setVisible(true);
				return;
			}
			if(description.length() >= 200)
			{
				invalid_detailsL_description_length.setVisible(true);
				return;
			}
			if(description.length() == 0)
			{
		    	invalid_detailsL_description.setVisible(true);
				return;
			}
			
			/*convert ObservableList to ArrayList*/
			List<String> convert = selected_items;
		    ArrayList<String> showing;
		    if (convert instanceof ArrayList<?>) {
		        showing = (ArrayList<String>) convert;
		    } else {
		        showing = new ArrayList<>(convert);
		    }
			
		    /*create ArrayList by item id with the ArrayList of name item*/
		    ArrayList<String> itemID = new ArrayList<String>();
		    for(String id: Managment_Controller.items.keySet())
		    	for(String name: showing)
		    		if(Managment_Controller.items.get(id).equals(name))
		    			itemID.add(id);
		    
		    Sale sale = new Sale(Managment_Controller.storeID, description, discount);
			/*prepare msg to server*/
			Msg saleMsg = new Msg();
			saleMsg.setInsert();
			saleMsg.setRole("insert new sale");
			saleMsg.oldO = itemID;
			saleMsg.newO = sale;
			Login_win.to_Client.accept((Object) saleMsg);
	    }

		
    public void back(ActionEvent event) throws IOException {
    	move(event, main.fxmlDir+ "Managment_F.fxml");
    }

   
    /**
     * set the items in the list view and set up the listview
     * @param msg
     */
    public void set_items_list()
    {
    	/*set up for multiple selected item in listview*/
    	EventHandler<MouseEvent> eventHandler = ( event ) ->
    	{
    	    if ( !event.isShortcutDown() )
    	    {
    	        Event.fireEvent( event.getTarget(), cloneMouseEvent( event ) );
    	        event.consume();
    	    }
    	};

    	item_list.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
    	item_list.addEventFilter( MouseEvent.MOUSE_PRESSED, eventHandler );
    	item_list.addEventFilter( MouseEvent.MOUSE_RELEASED, eventHandler );
    	
    	/*set the items in listview*/
    	ArrayList<String> items = new ArrayList<String>();
    	for(String id: Managment_Controller.items.keySet())
    		items.add(Managment_Controller.items.get(id));
    	
    	ObservableList<String> list = FXCollections.observableArrayList(items);
    	item_list.setEditable(true);
    	item_list.setItems(list);
    }
    
    /**
     * handle when the create of sale success in DB
     * @param msg
     */
    public void create_sale_success(Object msg)
    {
    	/*the creating was successful -> run in new thread the new window*/
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				 	try {
				 	    Login_win.showPopUp("INFORMATION", "Message", "Your sale was submitted - have a GOOD day!", "Thank you!");
						move(event_log , main.fxmlDir+ "Managment_F.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
			}
		}); 
    }
    
    /**
     * check if the string is integer
     * @param input
     * @return
     */
    public boolean isInteger( String input ) 
    {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
    
    /**
     * General function for the movement between the different windows
     * @param event
     * @param next_fxml = string of the specific fxml
     * @throws IOException
     */
    public void move(ActionEvent event, String next_fxml)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(next_fxml));
		 Scene win1= new Scene(menu);
		 win1.getStylesheets().add(getClass().getResource("css/Managment.css").toExternalForm());
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
		 
		  //close window by X button
		 win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  Msg  msg=new Msg();
	      		Person user_logout=Login_win.current_user;
	      		msg.setRole("user logout");
	      		msg.setTableName("person");
	      		msg.setUpdate();
	      		msg.oldO=user_logout;
	      		Login_win.to_Client.accept(msg);
	          }
	      });        
	}
    
    /**
     * help function for allowed to user to select multiple item from listview without press and hold ctrl
     * @param event
     * @return
     */
    private MouseEvent cloneMouseEvent( MouseEvent event )
    {
        switch (Toolkit.getToolkit().getPlatformShortcutKey())
        {
            case SHIFT:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        true,
                        event.isControlDown(),
                        event.isAltDown(),
                        event.isMetaDown(),
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            case CONTROL:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        event.isShiftDown(),
                        true,
                        event.isAltDown(),
                        event.isMetaDown(),
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            case ALT:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        event.isShiftDown(),
                        event.isControlDown(),
                        true,
                        event.isMetaDown(),
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            case META:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        event.isShiftDown(),
                        event.isControlDown(),
                        event.isAltDown(),
                        true,
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            default: // well return itself then
                return event;

        }
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	/*update the current controller to be this controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	/*set the error label*/
    	invalid_detailsL_description.setVisible(false);
    	invalid_detailsL_description_length.setVisible(false);
    	invalid_detailsL_Discount.setVisible(false);
    	invalid_detailsL_Item.setVisible(false);
    	invalid_detailsL_Discount2.setVisible(false);
    	
    	/*set the details of survey in fields*/
    	store_ID_L.setText(Managment_Controller.storeID);
    	set_items_list();
	}
}