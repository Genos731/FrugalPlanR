package backend.container;

import java.sql.Date;

public class Budget {
	private int id;
	private double goalValue;
	private Date startDate;
	private Date endDate;
	private int accountID;
	
	public Budget(int id, double goalValue, Date startDate, Date endDate, int accountID){
		this.id = id;
		this.goalValue = goalValue;
		this.startDate = startDate;
		this.endDate = endDate;
		this.accountID = accountID;
	}

	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public double getGoalValue() {
		return goalValue;
	}
	public void setGoalValue(double goalValue) {
		this.goalValue = goalValue;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
}
