package action;

import java.io.Serializable;

public class Product implements Serializable{
	
	private String ID;
	private String Name;
	private String Type;
	private float Price;
	private String Color;
	
	
	/*--CONSTRUCTOR1-*/
	public Product()
	{
		this.ID=null;
		this.Name=null;
		this.Type=null;
		
	}
	
	/*--CONSTRUCTOR2-*/
	public Product(String id, String name, String Type)
	{
		this.ID=id;
		this.Name=name;
		this.Type=Type;
		
	}
	
	/*--SET ID--*/
	public void SetID(String id)
	{
		this.ID=id;
	}
	
	/*--GET ID--*/
	public String GetID()
	{
		return this.ID;
	}
	
	/*--SET NAME--*/
	public void SetName(String name)
	{
		this.Name=name;
	}
	
	/*--GET NAME--*/
	public String GetName()
	{
		return this.Name;
	}
	
	/*--SET TYPE--*/
	public void SetType(String type)
	{
		this.Type=type;
	}
	
	/*--SET PRICE--*/
	public void SetPrice(float price)
	{
		this.Price=price;
	}
	
	/*--SET COLOR--*/
	public void SetColor(String color)
	{
		this.Color=color;
	}
	
	/*--GET COLOR--*/
	public String GetColor()
	{
		return this.Color;
	}
	
	
	/*--GET PRICE--*/
	public float GetPrice()
	{
		return this.Price;
	}
	
	
	/*--GET TYPE--*/ 
	public String GetType()
	{
		return this.Type;
	}
	
		
	public String toString()
	{
		return "Product ID="+" "+"'"+this.ID+"'"+" "+"\nProduct Name="+" "+"'"+this.Name+"'"+" "+"\nProduct Type="+" "+"'"+this.Type+"'";
	}
	
	
}
