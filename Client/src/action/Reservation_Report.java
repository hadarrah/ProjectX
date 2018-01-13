package action;

import java.util.ArrayList;

public class Reservation_Report extends Report{

	private ArrayList<Item> items;
	private ArrayList<Item_In_Catalog> items_catalog;
	private ArrayList<Order> orders;
	private ArrayList<Item_In_Order> item_in_order;

	public Reservation_Report(String name, String quarter, String store, String year) 
	{
		super(name, quarter, store, year);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Item_In_Catalog> getItems_catalog() {
		return items_catalog;
	}

	public void setItems_catalog(ArrayList<Item_In_Catalog> items_catalog) {
		this.items_catalog = items_catalog;
	}
	
	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	

	public ArrayList<Item_In_Order> getItem_in_order() {
		return item_in_order;
	}

	public void setItem_in_order(ArrayList<Item_In_Order> item_in_order) {
		this.item_in_order = item_in_order;
	}
	
	public void calculateReport()
	{
		String report= String.format("\t%-10s | %-20s | %s\n", "Item ID", "Name", "Count of ordered"); 
		report +="-----------------------------------------------------------\n";
		for(Item i: items)
		{
			report += String.format("\t%-10s | %-20s | %s\n", i.getID(), i.getName(), checkHowManyOrder(i.getID()));
		}
		for(Item_In_Catalog ic: items_catalog)
		{
			report += String.format("\t%-10s | %-20s | %s\n", ic.getID(), ic.getName(), checkHowManyOrder(ic.getID()));
		}
		
		super.setDetails(report);

	}
	
	public int checkHowManyOrder(String id)
	{
		int count=0;
		for(Item_In_Order iio: item_in_order)
		{
			if(iio.getItem().equals(id))
				count++;
		}
		return count;
	}
	
	public void filterRelevantItemInOrder()
	{
		boolean exist = false;
		int i;
		for(i=0 ; i<item_in_order.size();i++)
		{
			for(Order o: orders)
				if(o.getId().equals(item_in_order.get(i).getOrder()))
				{
					exist = true;
					break;
				}
			if(!exist) {
				item_in_order.remove(i);
				i--;
			}
			
			exist = false;
		}
	}
	
}
