package action;

import java.io.Serializable;

public class Item implements Serializable{
	
	private String ID;
	private String Name;
	private String Color;
	private float Price;
	private String Type;	
	private int Amount;
	private String Sale_ID;
	private String Image;
	
	/**constructor**/
	public Item()
	{
		this.ID=null;
		this.Name=null;
		this.Color=null;
		this.Price=0;
		this.Type=null;
		this.Amount=0;
		this.Sale_ID=null;
		
	}
	
	public Item(Item t)
	{
		this.ID=t.ID;
		this.Name=t.Name;
		this.Color=t.Color;
		this.Price=t.Price;
		this.Type=t.Type;
		this.Amount=t.Amount;
		this.Sale_ID=t.Sale_ID;
		
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
	 * @return the color
	 */
	public String getColor() {
		return Color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		Color = color;
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
	 * @return the type
	 */
	public String getType() {
		return Type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}
	/**
	 * @return the amount
	 */
	public float getAmount() {
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
	public String getSale_ID() {
		return Sale_ID;
	}
	/**
	 * @param sale the sale to set
	 */
	public void setSale_ID(Sale Sale_ID) {
		Sale_ID = Sale_ID;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return Image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		Image = image;
	}
	
	public Item getCopy(Item t) {
		return new Item(t);
	}
	
	public String toString()
	{
		return("\nID: "+this.getID()+"\n"+"Name: "+this.getName()+"\n"+"Color: "+this.getColor()+"\n"+"Price: "+this.getPrice()
				+"\n"+"Type: "+this.getType()+"\n"+"Amount: "+this.getAmount()+"\n"+"Sale: "+this.getSale_ID());
	}
	
}
