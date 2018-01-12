package action;

import java.io.Serializable;

public class Report implements Serializable{

	private String name, quarter, store, details, year;

	public Report(String name, String quarter, String store, String year) {
		this.name = name;
		this.quarter = quarter;
		this.store = store;
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	public String toString()
	{
		String report;
		report = name + "\nQuarter: " + quarter + "\nStore: " + store + "\nDetails:\n\n" + details;  
		return report;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
}
