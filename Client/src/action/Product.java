package action;

import java.io.Serializable;

public class Product implements Serializable{
	
	private String ID;
	private String Name;
	private String Color;
	private float Price;
	private String Type;	
	private int Amount;
	private Sale Sale;
	
	/**constructor**/
	public Product()
	{
		this.ID=null;
		this.Name=null;
		this.Color=null;
		this.Price=0;
		this.Type=null;
		this.Amount=0;
		this.Sale=null;
		
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
	public Sale getSale() {
		return Sale;
	}
	/**
	 * @param sale the sale to set
	 */
	public void setSale(Sale sale) {
		Sale = sale;
	}
	
	
	
	
	
		
	
	
	
}
