package action;

import java.util.ArrayList;

public class Incomes_Report extends Report {

	private ArrayList<Order> orders;
	private int total_price;
	
	public Incomes_Report(String name, String quarter, String store, String year) 
	{
		super(name, quarter, store, year);
		total_price = 0;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	
	public void calculateReport()
	{
		for(Order o: orders)
		{
			total_price += o.getTotprice();
		}
		
		super.setDetails("\tTotal price: " + total_price);
	}

}
