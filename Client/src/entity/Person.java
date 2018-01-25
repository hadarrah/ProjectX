package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Person implements Serializable {
	private String user_name;
	private String user_ID;
	private String user_last_name;
	private String user_password;
	private String Privilege;
	private String IsOnline;//0//1
	private String IsExist;//0//1
	private String WWID;
	private boolean isAlreadyConnected;
	private ArrayList<String> Store=new ArrayList<String>();	
	
	
	public Person(String id,String pass)
	{
		
		this.user_ID=id;
		this.user_password=pass;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getIsOnline() {
		return IsOnline;
	}
	public void setIsOnline(String isOnline) {
		IsOnline = isOnline;
	}
	public String getIsExist() {
		return IsExist;
	}
	public void setIsExist(String isExist) {
		IsExist = isExist;
	}
	public String getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}
	public String getPrivilege() {
		return Privilege;
	}
	public void setPrivilege(String privilege) {
		Privilege = privilege;
	}
	public String getWWID() {
		return WWID;
	}
	public void setWWID(String wWID) {
		WWID = wWID;
	}
	public boolean isAlreadyConnected() {
		return isAlreadyConnected;
	}
	public void setAlreadyConnected(boolean isAlreadyConnected) {
		this.isAlreadyConnected = isAlreadyConnected;
	}
	public String getUser_last_name() {
		return user_last_name;
	}
	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}
	/**
	 * @return the store
	 */
	public ArrayList<String> getStore() {
		return Store;
	}
	/**
	 * @param store the store to set
	 */
	public void setStore(ArrayList<String> store) {
		Store = store;
	}
	
	
 
}
