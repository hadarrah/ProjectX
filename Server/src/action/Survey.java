package action;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Survey implements Serializable {

	private String q1, q2, q3, q4, q5, q6;
	private String a1, a2, a3, a4, a5, a6;
	private String date, conclusion, ID;
	
	public Survey()
	{
		this.ID = null;
	}
	
	public Survey(String q1,String q2, String q3, String q4, String q5, String q6)
	{
		this.q1 = q1;
		this.q2 = q2;
		this.q3 = q3;
		this.q4 = q4;
		this.q5 = q5;
		this.q6 = q6;
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		
		this.date = dateFormat.format(date);
	}
	
	public String getQ1()
	{
		return this.q1;
	}
	
	public String getQ2()
	{
		return this.q2;
	}
	
	public String getQ3()
	{
		return this.q3;
	}
	
	public String getQ4()
	{
		return this.q4;
	}
	
	public String getQ5()
	{
		return this.q5;
	}
	
	public String getQ6()
	{
		return this.q6;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	public String getConclusion()
	{
		return this.conclusion;
	}
	
	public void setID(String id)
	{
		this.ID = id;
	}
	
}
