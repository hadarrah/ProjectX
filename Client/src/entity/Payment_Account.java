package entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment_Account implements Serializable {

	private String ID, CreditCard, Subscription, Status, StoreID;
	private Float refund_sum;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Payment_Account() {}
	
	public Payment_Account(String id,String creditcard, String subscription, String status)
	{
		this.ID = id;
		this.CreditCard = creditcard;
		this.Subscription = subscription;
		this.Status = status;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		this.date = dateFormat.format(date);
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	public void setID(String id)
	{
		this.ID = id;
	}
	
	public String getCreditCard()
	{
		return this.CreditCard;
	}
	
	public void setCreditCard(String creditcard)
	{
		this.CreditCard = creditcard;
	}
	
	public String getSubscription()
	{
		return this.Subscription;
	}
	
	public void setSubscription(String subscription)
	{
		this.Subscription = subscription;
	}
	
	public String getStatus()
	{
		return this.Status;
	}
	
	public void setStatus(String status)
	{
		this.Status = status;
	}

	public String getStoreID() {
		return StoreID;
	}

	public void setStoreID(String storeID) {
		StoreID = storeID;
	}

	public Float getRefund_sum() {
		return refund_sum;
	}

	public void setRefund_sum(Float refund_sum) {
		this.refund_sum = refund_sum;
	}
}
