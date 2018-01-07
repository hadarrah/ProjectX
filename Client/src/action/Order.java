package action;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable{
	String id, personid, status, payment, storeid;
	String createtime, createdate, requesttime, requestdate;
	
	float totPrice;
	
	//public static int id = lastID();
	
	boolean delivery;
	Delivery d;
	
	public Order() {}
	
	public String getId() {
		return id;
	}

	
	
	public void setId(String id) {
		this.id = id;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public boolean haveDelivery() {
		return delivery;
	}

	public void setBoolDelivery(boolean delivery) {
		this.delivery = delivery;
	}
	
	public void setDelivery(Delivery d) {
		this.d=d;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public float getTotprice() {
		return totPrice;
	}

	public void setTotprice(float totprice) {
		this.totPrice = totprice;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public void setRequesttime(String rqt) {
		this.requesttime=rqt;
	}
	
	public String getRequesttime() {
		return requesttime;
	}

	public String TimeNow() {
		String time = LocalDateTime.now().toString();
		this.createdate=time.substring(0, 10);
		setCreatedate(createdate);
		this.createtime=time.substring(11,16);
		setCreatetime(createtime);
		return createdate + " " +createtime;
	}

	public String getRequestdate() {
		return requestdate;
	}

	public void setRequestdate(String requestdate) {
		this.requestdate = requestdate;
	}

	
}
