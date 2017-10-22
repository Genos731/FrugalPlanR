package backend.container;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Budget {
	private int id;
	private String description;
	private double goalValue;
	private Calendar startDate;
	private Calendar endDate;
	private int accountID;
	private List<String> categoryList;
	
	public Budget(int id, String description, double goalValue, Calendar startDate, Calendar endDate, int accountID, List<String> categoryList){
		this.id = id;
		this.description = description;
		this.goalValue = goalValue;
		this.startDate = startDate;
		this.endDate = endDate;
		this.accountID = accountID;
		this.categoryList = categoryList;
	}

	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public double getGoalValue() {
		return goalValue;
	}
	
	public void setGoalValue(double goalValue) {
		this.goalValue = goalValue;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public String getStartDateForPrint() {
		Calendar cal = this.startDate;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(cal.getTime());
 	}
	
	public String getEndDateForPrint() {
		Calendar cal = this.endDate;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(cal.getTime());
 	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	public int getAccountID() {
		return accountID;
	}
	
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	
	public List<String> getCategoryList() {
		return categoryList;
	}
	
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	
	//NOTE ASSUMES LIST OF EXPENSES NOT ALL TRANSACTIONS
	public int getBudgetExpensesTotal(List<Transaction> transactions){
		int totalExpenses = 0;
		if (transactions == null){
			System.out.println("que pasa");
			return 0;
			
		}
		for (int counter = 0; counter < transactions.size(); counter++){
			for (int categoryCounter = 0; categoryCounter < categoryList.size(); categoryCounter++){
				if (transactions.get(counter).getCategory().equals(categoryList.get(categoryCounter))){
					totalExpenses += transactions.get(counter).getValue();
				}
			}
		}
		return totalExpenses;
	}
}
