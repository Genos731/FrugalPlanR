package backend.container;

import java.sql.Date;

public class Transaction {
	private int id;
	private boolean type;
	private double value;
	private Date date;
	private String name; // remove
	private String description;
	private String location;
	private String repeating;
	private int accountID;
	
	// remove
	public Transaction(int id, double value, String name, Date date, int accountID) {
		this.id = id;
		this.value = value;
		this.name = name;
		this.date = date;
		this.accountID = accountID;
	}
	
	public Transaction(int id, boolean type, double value, Date date, String description, String location, String repeating, int accountID) {
		this.id = id;
		this.value = value;
		this.date = date;
		this.description = description;
		this.location = location;
		this.repeating = repeating;
		this.accountID = accountID;
	}

	public int getId() {
		return id;
	}

	public boolean isType() {
		return type;
	}

	public double getValue() {
		return value;
	}

	// remove
	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}

	public String getRepeating() {
		return repeating;
	}

	public int getAccountID() {
		return accountID;
	}
	
}
