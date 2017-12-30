package action;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Survey implements Serializable {

	private String q1, q2, q3, q4, q5, q6;
	private String a1, a2, a3, a4, a5, a6;
	private String date, conclusion, ID, Num_Of_Participant;
	
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
		
		this.a1 = "0";
		this.a2 = "0";
		this.a3 = "0";
		this.a4 = "0";
		this.a5 = "0";
		this.a6 = "0";
		this.Num_Of_Participant = "0";
		
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
	
	public String getA1()
	{
		return this.a1;
	}
	
	public String getA2()
	{
		return this.a2;
	}
	
	public String getA3()
	{
		return this.a3;
	}
	
	public String getA4()
	{
		return this.a4;
	}
	
	public String getA5()
	{
		return this.a5;
	}
	
	public String getA6()
	{
		return this.a6;
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
	
	public String getNumOfParticipant()
	{
		return this.Num_Of_Participant;
	}
	
	public void setDate(String _date)
	{
		this.date = _date;
	}
	
	public void setID(String id)
	{
		this.ID = id;
	}
	
	public void setNumOfParticipant(String num)
	{
		this.Num_Of_Participant = num;
	}
	
}
