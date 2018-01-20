package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import action.Complaint_Report;
import action.Incomes_Report;
import action.Msg;
import action.Order;
import action.Person;
import action.Report;
import action.Reservation_Report;
import action.Sale;
import action.Satisfaction_Report;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Display_Report_Controller implements Initializable, ControllerI{

    public Label invalid_detailsL_ID, invalid_detailsL_Quarter, invalid_detailsL_Report,invalid_detailsL_Year;
    public ComboBox<String> report_combo,store_ID_combo, quarter_combo, year_combo;
    public Button back_B, viewReport_B, saveReport_B;
    public TextArea report_Text;
    public BarChart<String, Number> histogram;
	public static ActionEvent event_log;
	public CategoryAxis xAxis;
    public NumberAxis yAxis;
    public int current_year;
	public String current_month;
    /**
	 * return to the previous window
	 * @param event
	 * @throws IOException
	 */
    public void back(ActionEvent event) throws IOException {

    	move(event,main.fxmlDir+ "Managment_F.fxml");
    }

    /**
     * handle function when "view report" was pressed
     * @param event
     */
    public void view_Report(ActionEvent event)
    {
    	/*save the event*/
    	event_log =new ActionEvent();		 
		event_log=event.copyFor(event.getSource(), event.getTarget());
		
		/*set the error label*/
		invalid_detailsL_ID.setVisible(false);
		invalid_detailsL_Quarter.setVisible(false);
		invalid_detailsL_Report.setVisible(false);
		invalid_detailsL_Year.setVisible(false);
		
		/*get inputs*/
		String store, report, quarter, year;
		store = store_ID_combo.getValue();
		report = report_combo.getValue();
		quarter = quarter_combo.getValue();
		year = year_combo.getValue();
		
		/*check input from user*/
		if(store == null)
		{
			if(!(report != null && report.equals("Satisfaction")))
			{
				invalid_detailsL_ID.setVisible(true);
				return;
			}
		}
		if(quarter == null)
		{
			invalid_detailsL_Quarter.setVisible(true);
			return;
		}
		if(year == null)
		{
			invalid_detailsL_Year.setVisible(true);
			return;
		}
		if(report == null)
		{
			invalid_detailsL_Report.setVisible(true);
			return;
		}

		/*check if the quarter is possible*/
		if(String.valueOf(current_year).equals(year) && (Integer.parseInt(current_month)/4 +1)<= Integer.parseInt(quarter))
		{
	 	    Login_win.showPopUp("INFORMATION", "Message", "Your choosen quarter is unavialable yet", "Please select other quarter...");
	 	    return;
		}
		
		/*change output windows regarding to the type report*/
		if(report.equals("Complaints"))
		{
			histogram.setVisible(true);
			report_Text.setVisible(false);
		}
		else
		{
			histogram.setVisible(false);
			report_Text.setVisible(true);
		}
		
		Report reportToSend = new Report(report, quarter, store, year);
		/*prepare msg to server*/
		Msg reportMsg = new Msg();
		reportMsg.setSelect();
		reportMsg.setRole("get report for display");
		reportMsg.oldO = reportToSend;
		Login_win.to_Client.accept((Object) reportMsg);
    }
    
    /**
     * function that set off the store id combobox if satisfied report was selected
     * @param event
     */
    public void check_selected_type(ActionEvent event)
    {
    	if(report_combo.getValue().equals("Satisfaction"))
    		store_ID_combo.setDisable(true);
    	else
    		store_ID_combo.setDisable(false);
    	if(report_combo.getValue().equals("Complaints"))
    		saveReport_B.setDisable(true);
    	else
    		saveReport_B.setDisable(false);
    }
    
    /**
     * handle with save report to txt file
     * @param event
     */
    public void save_report(ActionEvent event)
    {
    	if(report_Text.getText().isEmpty())
    	{
	 	    Login_win.showPopUp("INFORMATION", "Message", "You must to choose report before save it!", "Please select report...");
	 	    return;
    	}
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    	fileChooser.getExtensionFilters().add(extFilter);
 	    fileChooser.setTitle("Save file");
 	    
 	    File dest = fileChooser.showSaveDialog(main.primary);
 	    if(dest != null)
 	    {
 	    	PrintWriter writer;
 			try 
 			{
 				writer = new PrintWriter(dest);
 				writer.println(report_Text.getText());
 				writer.close();
 			} catch (FileNotFoundException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	    }
 	   
	   
    }
    
    /**
     * Receive the details from server
     * @param message
     */
    public void get_report(Object message)
    {
    	Report report = (Report)(((Msg) message).oldO);
    	
    	
    	/*the creating was successful -> run in new thread the new window*/
    	Platform.runLater(new Runnable() 
    	{
			@Override
			public void run() 
			{
				/*check which report was selected*/
		    	switch(report.getName())
		    	{
		    	case "Incomes":
		    		Incomes_Report incomes = (Incomes_Report)(((Msg) message).oldO);
		    		report_Text.setText(incomes.toString());
					break;
				case "Reservations":
					Reservation_Report reservation = (Reservation_Report)(((Msg) message).oldO);
		    		report_Text.setText(reservation.toString());
					break;
				case "Complaints":
					Complaint_Report complaint = (Complaint_Report)(((Msg) message).oldO);
					String month1, month2, month3;
					
					/*set the x parameter regarding to the quarter*/
					if(quarter_combo.getValue().equals("1"))
					{
						month1 = "January";
						month2 = "February";
						month3 = "March";
					}
					else if(quarter_combo.getValue().equals("2"))
					{
						month1 = "April";
						month2 = "May";
						month3 = "June";
					}
					else if(quarter_combo.getValue().equals("3"))
					{
						month1 = "July";
						month2 = "August";
						month3 = "September";
					}
					else
					{
						month1 = "October";
						month2 = "November";
						month3 = "December";
					}
					
					/*insert the number of complaint on which month*/
			        XYChart.Series<String , Number> series1 = new XYChart.Series<String , Number>();
			        series1.getData().add(new XYChart.Data<String, Number>(month1, complaint.getMonth1()));
			        series1.getData().add(new XYChart.Data<String, Number>(month2, complaint.getMonth2()));
			        series1.getData().add(new XYChart.Data<String, Number>(month3, complaint.getMonth3()));
			        histogram.setTitle("Store " + complaint.getStore());
			        if(histogram.getData().isEmpty())
						histogram.getData().add(series1);  
					else
				        histogram.getData().set(0, series1);
					break;
				case "Satisfaction":
					Satisfaction_Report satisfaction = (Satisfaction_Report)(((Msg) message).oldO);
		    		report_Text.setText(satisfaction.toString());
					break;
		    	}
			}
		}); 
    }
    
    /**
     * set all combobox
     */
    public void setCombo()
    {
    	int i;
    	
    	/*set the quarter combobox*/
    	ArrayList<String> al= new ArrayList<String>();
    	al.add("1");
    	al.add("2");
    	al.add("3");
    	al.add("4");
    	ObservableList<String> list = FXCollections.observableArrayList(al); 
	    quarter_combo.setItems(list);
	    
	    /*get current year*/
	    DateFormat dateFormatYear = new SimpleDateFormat("yyyy");
		Date dateYear = new Date();
		current_year = Integer.parseInt(dateFormatYear.format(dateYear)); 
		
	    /*get current month*/
		DateFormat dateFormatMonth = new SimpleDateFormat("MM");
		Date dateMonth = new Date();
		current_month = dateFormatMonth.format(dateMonth);
		
	    /*set the year combobox*/
	    al.clear();
	    for(i=2017; i<=current_year;i++)
	    	al.add(""+i);
    	list = FXCollections.observableArrayList(al); 
    	year_combo.setItems(list);
	    
	    /*set the report combobox*/
	    al.clear();
	    al.add("Incomes");
    	al.add("Reservations");
    	al.add("Complaints");
    	al.add("Satisfaction");
    	list = FXCollections.observableArrayList(al); 
    	report_combo.setItems(list);
	    
    	/*set the store combobox*/
    	al.clear();
    	/*check privilege user*/
    	if(Login_win.current_user.getPrivilege().equals("Store Manager"))
    		al.add(Login_win.chosen_store);
    	else
    	{
    		for(String store: Managment_Controller.stores)
        		al.add(store);
    		al.add("All");
    	}
    	list = FXCollections.observableArrayList(al); 
    	store_ID_combo.setItems(list);
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
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		 
    	/*update the current controller to be this controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	setCombo();
    	
    	/*initialize the bar chart*/
    	histogram.setVisible(false);
    	xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        histogram.setAnimated(false);
        histogram.setLegendVisible(false);

        report_Text.setStyle("-fx-font-family: monospace");
    	/*set the error label off*/
    	invalid_detailsL_ID.setVisible(false);
    	invalid_detailsL_Quarter.setVisible(false);
    	invalid_detailsL_Report.setVisible(false);
    	invalid_detailsL_Year.setVisible(false);
    }
}
