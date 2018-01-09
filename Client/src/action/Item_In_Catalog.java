package action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Item_In_Catalog implements Serializable{
	
	private String ID;		
	private String Name;
	private float Price ;
	private String Description;
	private MyFile Image;
	private int Amount;
	private Sale Sale_ID;
	
	/**Default constructor**/
	public  Item_In_Catalog()
	{
		this.ID=null;
		this.Name=null;
		this.Price=0;
		this.Description=null;
		this.Image=new MyFile();
		this.Amount=0;
		this.Sale_ID=new Sale();
	}
	
	/**Constructor with values**/
	public  Item_In_Catalog(String ID, String Name,float Price,String Description,MyFile Image)
	{
		this.ID=ID;
		this.Name=Name;
		this.Price=Price;
		this.Description=Description;
		this.Image=Image;
	}
	
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return Price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		Price = price;
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
	/**
	 * @return the image
	 */
	public MyFile getImage() {
		return Image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(MyFile image) {
		Image = image;
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
	 * @return the amount
	 */
	public int getAmount() {
		return Amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		Amount = amount;
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
