package action;

import java.io.Serializable;

public class Product implements Serializable{
	private String ID;
	private String Name;
	private String Type;
	
	
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
