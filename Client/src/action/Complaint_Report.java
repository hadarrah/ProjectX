package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class Complaint_Report extends Report{

	private ArrayList<Complain> complaints;
	private int month1, month2, month3;
	
	public Complaint_Report(String name, String quarter, String store, String year) 
	{
		super(name, quarter, store, year);
		this.month1 = 0;
		this.month2 = 0;
		this.month3 = 0;
	}

	public ArrayList<Complain> getComplaints() {
		return complaints;
	}

	public void setComplaints(ArrayList<Complain> complaints) {
		this.complaints = complaints;
	}

	public int getMonth1() {
		return month1;
	}

	public void setMonth1(int month1) {
		this.month1 = month1;
	}

	public int getMonth2() {
		return month2;
	}

	public void setMonth2(int month2) {
		this.month2 = month2;
	}

	public int getMonth3() {
		return month3;
	}

	public void setMonth3(int month3) {
		this.month3 = month3;
	}
	
	public void calculateReport()
	{
		int month;
		this.month1 = 0;
		this.month2 = 0;
		this.month3 = 0;

		for(Complain c: complaints)
		{
			month = Integer.parseInt(c.getDate().substring(3, 5));
			
			if(month%3 == 1)
				month1++;
			else if(month%3 == 2)
				month2++;
			if(month%3 == 0)
				month3++;
		}
	}
}
