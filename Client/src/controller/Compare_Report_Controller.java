package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entity.Complaint_Report;
import entity.Incomes_Report;
import entity.Msg;
import entity.Order;
import entity.Person;
import entity.Report;
import entity.Reservation_Report;
import entity.Sale;
import entity.Satisfaction_Report;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Compare_Report_Controller implements Initializable, ControllerI{

    public Label invalid_detailsL_Report, invalid_detailsL_Year1, invalid_detailsL_Year2, invalid_detailsL_Quarter1, invalid_detailsL_Quarter2, invalid_detailsL_ID1, invalid_detailsL_ID2, store1_label, store2_label;
    public CategoryAxis xAxis1, xAxis2;
    public NumberAxis yAxis1, yAxis2;
    public ComboBox<String> store_ID_combo1, store_ID_combo2, year_combo1, year_combo2, quarter_combo1, quarter_combo2, report_combo;
    public TextArea report_Text1, report_Text2;
    public BarChart<String, Number> histogram1, histogram2;
    public Button back_B, compareReport_B;
	public static ActionEvent event_log;
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
    public void compare_Report(ActionEvent event)
    {
    	/*save the event*/
    	event_log =new ActionEvent();		 
		event_log=event.copyFor(event.getSource(), event.getTarget());
		
		/*set the error label*/
		invalid_detailsL_ID1.setVisible(false);
		invalid_detailsL_Quarter1.setVisible(false);
		invalid_detailsL_Year1.setVisible(false);
		invalid_detailsL_ID2.setVisible(false);
		invalid_detailsL_Quarter2.setVisible(false);
		invalid_detailsL_Year2.setVisible(false);
		invalid_detailsL_Report.setVisible(false);
		
		/*get inputs*/
		String store1, quarter1, year1, store2, quarter2, year2, report;
		store1 = store_ID_combo1.getValue();
		year1 = year_combo1.getValue();
		quarter1 = quarter_combo1.getValue();
		store2 = store_ID_combo2.getValue();
		year2 = year_combo2.getValue();
		quarter2 = quarter_combo2.getValue();
		report = report_combo.getValue();

		/*check input from user*/
		if(store1 == null)
		{
			if(!(report != null && report.equals("Satisfaction")))
			{
				invalid_detailsL_ID1.setVisible(true);
				return;
			}
		}
		if(quarter1 == null)
		{
			invalid_detailsL_Quarter1.setVisible(true);
			return;
		}
		if(year1 == null)
		{
			invalid_detailsL_Year1.setVisible(true);
			return;
		}
		if(store2 == null)
		{
			if(!(report != null && report.equals("Satisfaction")))
			{
				invalid_detailsL_ID2.setVisible(true);
				return;
			}
		}
		if(quarter2 == null)
		{
			invalid_detailsL_Quarter2.setVisible(true);
			return;
		}
		if(year2 == null)
		{
			invalid_detailsL_Year2.setVisible(true);
			return;
		}
		if(report == null)
		{
			invalid_detailsL_Report.setVisible(true);
			return;
		}

		/*change output windows regarding to the type report*/
		if(report.equals("Complaints"))
		{
			report_Text1.setVisible(false);
			report_Text2.setVisible(false);
			store1_label.setVisible(false);
			store2_label.setVisible(false);
			histogram1.setVisible(true);
			histogram2.setVisible(true);
		}
		else
		{
			histogram1.setVisible(false);
			histogram2.setVisible(false);
			store1_label.setVisible(true);
			store2_label.setVisible(true);
			report_Text1.setVisible(true);
			report_Text2.setVisible(true);
		}
		if(report.equals("Satisfaction"))
		{
			store1_label.setVisible(false);
			store2_label.setVisible(false);
		}
		
		/*check if the quarter is possible*/
		if(String.valueOf(current_year).equals(year1) && (Integer.parseInt(current_month)/4 +1)<= Integer.parseInt(quarter1))
		{
	 	    Login_win.showPopUp("INFORMATION", "Message", "Your choosen quarter is unavialable yet", "Please select other quarter...");
	 	    return;
		}
		if(String.valueOf(current_year).equals(year2) && (Integer.parseInt(current_month)/4 +1)<= Integer.parseInt(quarter2))
		{
	 	    Login_win.showPopUp("INFORMATION", "Message", "Your choosen quarter is unavialable yet", "Please select other quarter...");
	 	    return;
		}
		Report reportToSend1 = new Report(report, quarter1, store1, year1);
		Report reportToSend2 = new Report(report, quarter2, store2, year2);

		/*prepare msg to server*/
		Msg reportMsg = new Msg();
		reportMsg.setSelect();
		reportMsg.setRole("get reports for compare");
		reportMsg.oldO = reportToSend1;
		reportMsg.newO = reportToSend2;
		Login_win.to_Client.accept((Object) reportMsg);
    }
    
    /**
     * function that set off the store id combobox if satisfied report was selected
     * @param event
     */
    public void check_selected_type(ActionEvent event)
    {
    	if(report_combo.getValue().equals("Satisfaction"))
    	{
    		store_ID_combo1.setDisable(true);
    		store_ID_combo2.setDisable(true);
    	}
    	else
    	{
    		store_ID_combo1.setDisable(false);
    		store_ID_combo2.setDisable(false);
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
		    		Incomes_Report incomes1 = (Incomes_Report)(((Msg) message).oldO);
		    		Incomes_Report incomes2 = (Incomes_Report)(((Msg) message).newO);
		    		report_Text1.setText(incomes1.toString());
		    		report_Text2.setText(incomes2.toString());
		    		store1_label.setText("Store: " + incomes1.getStore());
		    		store2_label.setText("Store: " + incomes2.getStore());
					break;
				case "Reservations":
					Reservation_Report reservation1 = (Reservation_Report)(((Msg) message).oldO);
					Reservation_Report reservation2 = (Reservation_Report)(((Msg) message).newO);
		    		report_Text1.setText(reservation1.toString());
		    		report_Text2.setText(reservation2.toString());
		    		store1_label.setText("Store: " + reservation1.getStore());
		    		store2_label.setText("Store: " + reservation2.getStore());
					break;
				case "Complaints":
					Complaint_Report complaint1 = (Complaint_Report)(((Msg) message).oldO);
					Complaint_Report complaint2 = (Complaint_Report)(((Msg) message).newO);

					String month1, month2, month3, month1_2, month2_2, month3_2;
					
					/*set the x parameter regarding to the quarter*/
					if(quarter_combo1.getValue().equals("1"))
					{
						month1 = "January";
						month2 = "February";
						month3 = "March";
					}
					else if(quarter_combo1.getValue().equals("2"))
					{
						month1 = "April";
						month2 = "May";
						month3 = "June";
					}
					else if(quarter_combo1.getValue().equals("3"))
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
					if(quarter_combo2.getValue().equals("1"))
					{
						month1_2 = "January";
						month2_2 = "February";
						month3_2 = "March";
					}
					else if(quarter_combo2.getValue().equals("2"))
					{
						month1_2 = "April";
						month2_2 = "May";
						month3_2 = "June";
					}
					else if(quarter_combo2.getValue().equals("3"))
					{
						month1_2 = "July";
						month2_2 = "August";
						month3_2 = "September";
					}
					else
					{
						month1_2 = "October";
						month2_2 = "November";
						month3_2 = "December";
					}
					
					/*insert the number of complaint on which month*/
			        XYChart.Series<String , Number> series1 = new XYChart.Series<String , Number>();
			        series1.getData().add(new XYChart.Data<String, Number>(month1, complaint1.getMonth1()));
			        series1.getData().add(new XYChart.Data<String, Number>(month2, complaint1.getMonth2()));
			        series1.getData().add(new XYChart.Data<String, Number>(month3, complaint1.getMonth3()));

			        XYChart.Series<String , Number> series2 = new XYChart.Series<String , Number>();
			        series2.getData().add(new XYChart.Data<String, Number>(month1_2, complaint2.getMonth1()));
			        series2.getData().add(new XYChart.Data<String, Number>(month2_2, complaint2.getMonth2()));
			        series2.getData().add(new XYChart.Data<String, Number>(month3_2, complaint2.getMonth3()));
			        
			        if(histogram1.getData().isEmpty() && histogram2.getData().isEmpty())
					{
						histogram1.getData().add(series1);  
					 	histogram2.getData().add(series2); 
					}
					else
					{
				        histogram1.getData().set(0, series1);
				        histogram2.getData().set(0, series2);
					}
			        histogram1.setTitle("Store " + complaint1.getStore());
			        histogram2.setTitle("Store " + complaint2.getStore());
					break;
				case "Satisfaction":
					Satisfaction_Report satisfaction1 = (Satisfaction_Report)(((Msg) message).oldO);
					Satisfaction_Report satisfaction2 = (Satisfaction_Report)(((Msg) message).newO);
		    		report_Text1.setText(satisfaction1.toString());
		    		report_Text2.setText(satisfaction2.toString());
		    		store1_label.setText("Store: " + satisfaction1.getStore());
		    		store2_label.setText("Store: " + satisfaction2.getStore());
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
	    quarter_combo1.setItems(list);
	    quarter_combo2.setItems(list);

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
    	year_combo1.setItems(list);
    	year_combo2.setItems(list);

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
    	
    	for(String store: Managment_Controller.stores)
        	al.add(store);
    	list = FXCollections.observableArrayList(al); 
    	store_ID_combo1.setItems(list);
    	store_ID_combo2.setItems(list);

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
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		 
    	/*update the current controller to be this controller in general ClientConsole instance*/
    	Login_win.to_Client.setController(this);
    	
    	setCombo();
    	
    	/*initialize the bar chart*/
    	histogram1.setVisible(false);
    	xAxis1 = new CategoryAxis();
        yAxis1 = new NumberAxis();
        histogram1.setAnimated(false);
        histogram1.setLegendVisible(false);
        histogram2.setVisible(false);
    	xAxis2 = new CategoryAxis();
        yAxis2 = new NumberAxis();
        histogram2.setAnimated(false);
        histogram2.setLegendVisible(false);

        report_Text1.setStyle("-fx-font-family: monospace");
        report_Text2.setStyle("-fx-font-family: monospace");
    	/*set the error label off*/
    	invalid_detailsL_ID1.setVisible(false);
    	invalid_detailsL_Quarter1.setVisible(false);
    	invalid_detailsL_Year1.setVisible(false);
    	invalid_detailsL_ID2.setVisible(false);
    	invalid_detailsL_Quarter2.setVisible(false);
    	invalid_detailsL_Year2.setVisible(false);
    	invalid_detailsL_Report.setVisible(false);
    }
}