package action;

import java.io.Serializable;

public class Item_In_Catalog implements Serializable{
	private String ID;
	private String Item_ID;
	private int Amount;
	private String Name;
	private float Price ;
	private String Description;
	private String Image;
	
	/**Default constructor**/
	public  Item_In_Catalog()
	{
		this.ID=null;
		this.Item_ID=null;
		this.Amount=0;
		this.Name=null;
		this.Price=0;
		this.Description=null;
		this.Image=null;
	}
	
	/**Constructor with values**/
	public  Item_In_Catalog(String ID,String Item_ID,int Amount,String Name,float Price,String Description,String Image)
	{
		this.ID=ID;
		this.Item_ID=Item_ID;
		this.Amount=Amount;
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
	 * @return the product_ID
	 */
	public String getItem_ID() {
		return Item_ID;
	}
	/**
	 * @param product_ID the product_ID to set
	 */
	public void setItem_ID(String Item_ID) {
		this.Item_ID = Item_ID;
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
	public String getImage() {
		return Image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		Image = image;
	}
	public String toString()
	{
		return("\nID: "+this.getID()+"\n"+"Item_Id: "+this.getItem_ID()+"\n"+"Amount: "+this.getAmount()+"\n"
				+"Name: "+this.getName()+"\n"+"Price: "+this.getPrice()+"\n"+"Description: "+this.getDescription());
	}

}
