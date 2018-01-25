package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Item_In_Catalog extends Item {
	

	private String Description;
	private Sale Sale_ID;
	
	/**Default constructor**/
	public  Item_In_Catalog()
	{
		super();		
		this.Description=null;	
		this.Sale_ID=new Sale();
		
	}
	
	/**Copy constructor**/
	public Item_In_Catalog(Item_In_Catalog Itc)
	{
		super.setID(Itc.getID());
		super.setName(Itc.getName());
		super.setAmount(Itc.getAmount());
		super.setPrice(Itc.getPrice());
		super.setType("Catalog");
	}
	
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	
	/**print all Item_id**/
	public Set<String> PrintOnlyItemID(Set<Item>Item_ID)
	{
		Set<String> id_to_print= new HashSet<String>();
		for(Item i:Item_ID)
		{
			id_to_print.add(i.getID());
		}
		return id_to_print;
	}
	

	/**
	 * @return the sale
	 */
	public Sale getSale() {
		return Sale_ID;
	}

	/**
	 * @param sale the sale to set
	 */
	public void setSale(Sale sale) {
		Sale_ID = sale;
	}

	
	
	/**toString**/
	public String toString()
	{		
		return("\nID: "+this.getID()+"\n"+"Name: "+this.getName()+"\n"+"Price: "+
				this.getPrice()+"\n"+"Description: "+this.getDescription()+"\n"+"Sale_Id: "+this.getSale().getID());
	}

	
}