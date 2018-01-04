package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	// "Cart" for items selected so far
	public static ArrayList<Item> selectedItemsArr = new ArrayList<Item>();

	// A map that wires an item for the selected amount. ( map.get(item)==amount )
	public static Map<Item, Integer> itemToAmount = new HashMap<Item, Integer>();

	ArrayList<Item> removedItems = new ArrayList<Item>();

	public float totalPrice = 0;
	
	
	public void addItemToCart(Item t) {

		// Add the item
		this.selectedItemsArr.add(t);

		// If item is self item, input it with the value of 1;
		if (t instanceof Self_Item)
			this.itemToAmount.put(t, 1);
	}
	
	public float calcTotalPrice() {
		totalPrice = 0;
		for (Item p : selectedItemsArr)
			totalPrice += p.getPrice();
		
		return totalPrice;

	}
}
