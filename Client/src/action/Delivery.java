package action;

import java.io.Serializable;

public class Delivery implements Serializable {
	
	//Order
	private String address;
	private String name;
	private String phone;

	private Order order;
	private int orderid;
	
	public Delivery() {
		this.setName(null);
		this.setAddress(null);
		this.setPhone(null);

	}
	
	public Delivery(String name, String address, String phone) {
		this.setName(name);
		this.setAddress(address);
		this.setPhone(phone);
	}
	
	
	public void setOrderid(int id) {
		this.orderid=id;
	}
	public int getOrderid() {
		return this.orderid;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
