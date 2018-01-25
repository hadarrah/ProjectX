package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Self_Item extends Item {

	public ArrayList<Item> items = new ArrayList<Item>();
	public Map<Item, Integer> amounts = new HashMap<Item, Integer>();

	public String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float totPrice;

	public Self_Item(ArrayList<Item> itemsarr, Map<Item, Integer> amountmap, String type) {

		for(Item t:itemsarr) {
			Item in = new Item();
			in.setID(t.getID());
			in.setName(t.getName());
			in.setPrice(t.getPrice());
			in.setType(t.getType());
			items.add(in);
			amounts.put(in, amountmap.get(t));
		}

		this.setType(type);

		setPrice();
	}

	public Self_Item(Self_Item st) {
		this.items = st.items;
		this.amounts = st.amounts;
		this.type= st.getType();
		setPrice();
	}

	// Add one item to the existing self item
	public void addItem(Item t, int amt) {
		items.add(t);
		amounts.put(t, amt);
		setPrice();
	}

	// Add array of items (including a mapping of item->amount)
	public void addAll(ArrayList<Item> items, Map<Item, Integer> amounts) {
		this.items.addAll(items);
		this.amounts.putAll(amounts);
		setPrice();
	}


	/** Calculates total price of the item */
	public void setPrice() {
		float price = 0;
		for (Item t : items) {
			price += t.getPrice() * amounts.get(t);
		}
		this.totPrice = price;
	}

	public float getPrice() {
		return this.totPrice;
	} // Total price.

	public ArrayList<Item> getItems() {
		return this.items;
	} // Items arraylist.

	public int getItemAmount(Item t) {
		return this.amounts.get(t);
	} // Amount of item t.

	public Self_Item getCopy(Self_Item t) {
		return new Self_Item(t);
	}
	
	public String toString()
	{
		return("\nID: "+this.getID()+"\n"+"Name: "+this.getName()+"\n"+"Color: "+this.getColor()+"\n"+"Price: "+this.getPrice()
				+"\n"+"Type: "+this.getType()+"\n"+"Amount: "+this.getAmount());
	}
}
