package action;

import java.io.Serializable;

import javafx.event.ActionEvent;

public class Msg implements Serializable {
	
		private String role;
		public TYPE type = TYPE.NULL; // Default priority	    
		public String Table_name;
	    public Object newO;
	    public Object oldO;
	    public ActionEvent event;

	    public enum TYPE { NULL  ,SELECT, SELECTALL,  UPDATE;}
	    
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
	
	public String getType()
	{
		if(this.type==TYPE.UPDATE)
			return "UPDATE";
		else if(this.type==TYPE.SELECT)
			return "SELECT";
		else if(this.type==TYPE.SELECTALL)
			return "SELECTALL";
		return null;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
