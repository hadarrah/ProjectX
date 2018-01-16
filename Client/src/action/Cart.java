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
	
	public void cleanCart() {
		Cart.selectedItemsArr.clear();
		Cart.itemToAmount.clear();
		this.removedItems.clear();
	}
	
	public boolean isEmpty() {
		return selectedItemsArr.isEmpty();
	}
	
	public void addItemToCart(Item t) {

		// Add the item
		this.selectedItemsArr.add(t);

		// If item is self item, input it with the value of 1;
		if (t instanceof Self_Item)
			this.itemToAmount.put(t, 1);
	}
	
	public float calcTotalPrice() {
		totalPrice = 0;
		
		System.out.println("Calculating final price");
		
		for (Item p : selectedItemsArr) {
			if(p instanceof Self_Item)
			totalPrice += p.getPrice();
			
			if(p instanceof Item_In_Catalog) {
				Item_In_Catalog pc = (Item_In_Catalog)p;
				if(pc.getSale()!=null) {
					System.out.println("Calculating final price");
					totalPrice += pc.getAmount()* (pc.getPrice() - (pc.getPrice()*Integer.parseInt(pc.getSale().getDiscount()))*0.01); 
				}
			}
		}
		
		return totalPrice;

	}
}
