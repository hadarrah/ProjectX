package action;

import java.io.Serializable;

public class Msg implements Serializable {
	
		public TYPE type = TYPE.NULL; // Default priority	    
	    public Object newO;
	    public Object oldO;
	    public enum TYPE { NULL  ,SELECT, SELECTALL,  UPDATE;}
	    
	   public Msg()
	   {
		   this.newO=null;
		   this.oldO=null;
		 
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
}
