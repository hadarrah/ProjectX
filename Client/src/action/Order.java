package action;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable{
	private String id, personid, status, payment, storeid,delivery1;
	private String createtime, createdate, requesttime, requestdate;
	private String card;
	private String refund_amount;
	
	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	float totPrice;
	//public static int id = lastID();
	boolean delivery;
	Delivery d;
	
	
	public Order(String id,String status)
	{
		this.id=id;
		this.status=status;
	}
	
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

	public String getDelivery1() {
		return delivery1;
	}

	public void setDelivery1(String delivery1) {
		this.delivery1 = delivery1;
	}

	public String getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}

	
}
