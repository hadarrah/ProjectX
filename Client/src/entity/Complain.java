package entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Complain  implements Serializable{
	
	private ArrayList<String> complain_topic;
	private String complain_id; 
	private String customer_id; 
	private String  status;
	private String answer; 
	private String compensation;
	private String user_txt;
	private String chosen_topic; 
	private String date, hour;
	private String store;
	
	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Complain() {
		 this.complain_topic=new ArrayList<String>() ;
		 setTopics();// set the defaults complain topics 
		 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			this.date = dateFormat.format(date);
			this.hour = ""+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
	}
	
	public Complain (String id,String Customer_id,String status,String answer,String comp,String user_text)
	{
		 this.complain_topic=new ArrayList<String>() ;
		this.complain_id=id;
		this.customer_id=Customer_id;
		this.status=status;
		this.compensation=comp;
		this.user_txt=user_text;
		setTopics();// set the defaults complain topics 
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		this.date = dateFormat.format(date);
		this.hour = ""+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
	}
	 
	public Complain(String id,String customer_id,String user_text,String date,String hour)
	{
		this.complain_id=id;
		this.customer_id=customer_id;
		this.user_txt=user_text;
		this.date = date;
		this.hour = hour;
	}
	public String getComplain_ID() {
		return complain_id;
	}
	public void setComplain_ID(String complain_ID) {
		complain_id = complain_ID;
	}
	public String getCustomer_ID() {
		return customer_id;
	}
	public void setCustomer_ID(String customer_ID) {
		customer_id = customer_ID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getCompensation() {
		return compensation;
	}
	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}

	public ArrayList<String> getComplain_topic() {
		return complain_topic;
	}

	public void setComplain_topic(ArrayList<String> complain_topic) {
		this.complain_topic = complain_topic;
	}

	 public  void setTopics()
	 {
		 
		 this.complain_topic.add("Functional problem in Zerli- System");
		 this.complain_topic.add("Order an item");
		 this.complain_topic.add("Shipping");
		 this.complain_topic.add("Payment");
		 this.complain_topic.add(" Zerli Staff");
		 this.complain_topic.add(" Other");

		 
		 
	 }

	public String getUser_txt() {
		return user_txt;
	}

	public void setUser_txt(String user_txt) {
		this.user_txt = user_txt;
	}

	public String getChosen_topic() {
		return chosen_topic;
	}

	public void setChosen_topic(String chosen_topic) {
		this.chosen_topic = chosen_topic;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
}
