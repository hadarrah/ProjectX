package entity;

public class Store {
	private String ID;
	private String Location;
	private String Open_Hours;
	private String Manager_ID;
	private String Item_ID;
	private String Type;
	private int Amount;
	
	/*constructor*/
	public Store()
	{
		this.setID(null);
		this.setLocation(null);
		this.setOpen_Hours(null);
		this.setManager_ID(null);
		this.setItem_ID(null);
		this.setType(null);
		this.setAmount(0);
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return Location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		Location = location;
	}

	/**
	 * @return the open_Hours
	 */
	public String getOpen_Hours() {
		return Open_Hours;
	}

	/**
	 * @param open_Hours the open_Hours to set
	 */
	public void setOpen_Hours(String open_Hours) {
		Open_Hours = open_Hours;
	}

	/**
	 * @return the manager_ID
	 */
	public String getManager_ID() {
		return Manager_ID;
	}

	/**
	 * @param manager_ID the manager_ID to set
	 */
	public void setManager_ID(String manager_ID) {
		Manager_ID = manager_ID;
	}

	/**
	 * @return the item_ID
	 */
	public String getItem_ID() {
		return Item_ID;
	}

	/**
	 * @param item_ID the item_ID to set
	 */
	public void setItem_ID(String item_ID) {
		Item_ID = item_ID;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return Amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		Amount = amount;
	}

	
}
