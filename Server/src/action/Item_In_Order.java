package action;

import java.io.Serializable;

public class Item_In_Order implements Serializable{
	
	private String order, item, type;
	private int amount;
	
	public Item_In_Order(String order, String item, String type, int amount) 
	{
		this.order = order;
		this.item = item;
		this.type = type;
		this.amount = amount;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
