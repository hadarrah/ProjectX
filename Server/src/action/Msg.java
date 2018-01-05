package action;

import java.io.Serializable;

import javafx.event.ActionEvent;

public class Msg implements Serializable {
	
		private String role;
		public TYPE type = TYPE.NULL; // Default priority	    
		public String Table_name;
	    public Object newO;
	    public Object oldO;
	    public Object freeUse;
	    public ActionEvent event;
	    public float num1;
	    public float num2;
	    public String freeField;

	    public enum TYPE { NULL  ,SELECT, SELECTALL,  UPDATE, INSERT;}
	    
	   public Msg()
	   {
		   this.newO=null;
		   this.oldO=null;
		 
	   }
	   public void setTableName(String table_name)
	   {
		   this.Table_name=table_name;
	   }
	   public String getTableName( )
	   {
		   return this.Table_name;
	   }
		
		
	public void setSelect()
	{
		 this.type=TYPE.SELECT;
	}
	public void setSelectAll()
	{
		 this.type=TYPE.SELECTALL;
	}
	
	public void setUpdate()
	{
		this.type=TYPE.UPDATE;
	}
	
	public void setInsert()
	{
		 this.type=TYPE.INSERT;
	}
	
	public String getType()
	{
		switch(this.type)
		{
			case UPDATE:
				return "UPDATE";
			case SELECT:
				return "SELECT";
			case SELECTALL:
				return "SELECTALL";
			case INSERT:
				return "INSERT";
			default:
				return null;
			
		}
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
