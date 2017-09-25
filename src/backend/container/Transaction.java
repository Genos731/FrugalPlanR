package backend.container;

import java.sql.Date;

public class Transaction {
	private int id;
	private double value;
	private String name;
	private Date date;
	private int accountID;
	
	public Transaction(int id, double value, String name, Date date, int accountID) {
		this.id = id;
		this.value = value;
		this.name = name;
		this.date = date;
		this.accountID = accountID;
	}

	public int getId() {
		return id;
	}

	public double getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public int getAccountID() {
		return accountID;
	}
	
}
