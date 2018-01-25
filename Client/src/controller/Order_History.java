 package controller;

public class Order_History {
	   private String ID;
	   private String Delivery;
	   private String Price;
	   private String Payment_Type;
	   private String Status;
	   private String Time;
	   private String Requested_Time;
	   private String Requested_Date;
	   private String Date;
	   private String Store_ID;
	  
	 
	   public Order_History(String Id, String status,String price,String order_date,String del,String req_date)  
	   {
	       this.ID = Id;
	       this.Status=status;
	       this.Price=price;
	       this.Date=order_date;
	       this.Delivery=del;
	       this.Requested_Date=req_date;
	     
	   }
	  

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPayment_Type() {
		return Payment_Type;
	}

	public void setPayment_Type(String payment_Type) {
		Payment_Type = payment_Type;
	}

	public String getRequested_Date() {
		return Requested_Date;
	}

	public void setRequested_Date(String requested_Date) {
		Requested_Date = requested_Date;
	}

	public String getRequested_Time() {
		return Requested_Time;
	}

	public void setRequested_Time(String requested_Time) {
		Requested_Time = requested_Time;
	}

	public String getDelivery() {
		return Delivery;
	}

	public void setDelivery(String delivery) {
		Delivery = delivery;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getStore_ID() {
		return Store_ID;
	}

	public void setStore_ID(String store_ID) {
		Store_ID = store_ID;
	}
	 
	}