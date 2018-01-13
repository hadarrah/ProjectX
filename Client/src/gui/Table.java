package gui;

import javafx.beans.property.SimpleFloatProperty;

import javafx.beans.property.SimpleStringProperty;

public class Table {
private  SimpleStringProperty rID;
private  SimpleStringProperty rStatus;
private  SimpleStringProperty rDate;
 

public Table(String id,String status,String date)
{
	this.rID=new SimpleStringProperty(id);
	this.rStatus=new SimpleStringProperty(status);
	this.rDate=new SimpleStringProperty(date);
 
}

public String getrID() {
	return rID.get();
}
public String getrStatus() {
	return rStatus.get();
}
public String getrDate() {
	return rDate.get();
}
 
public void setrID(String id)
{
	rID.set(id);
}
public void setrName(String name)
{
	rStatus.set(name);
}
public void setrDate(String Date)
{
	rDate.set(Date);
}
 
}
