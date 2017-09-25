package backend.accessor.test;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.accessor.BudgetAccessor;
import backend.accessor.BudgetAccessorImpl;
import backend.container.Account;
import backend.container.Budget;
import backend.exception.InvalidAccountException;

public class BudgetAccessorTest {

	public static void main(String[] args) {
		// Get an account
		AccountAccessor accountAccessor = new AccountAccessorImpl();
		Account account = null;
		try {
			account = accountAccessor.getAccount("Bobby");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			accountAccessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create budget accessor
		BudgetAccessor accessor = new BudgetAccessorImpl();

		// Test create
		try {
			Date startDate = java.sql.Date.valueOf("2013-01-01");
			Date endDate = java.sql.Date.valueOf("2012-12-31");
			accessor.create(account, 6969, startDate, endDate);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Test get
		List<Budget> budgetList = null;
		try {
			budgetList = accessor.getBudgets(account);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// Test updateGoalValue
		/**
		try {
			//accessor.updateGoalValue(budgetList.get(0), 2017);	
			
			// Date needs to be in the format java.sql.Date.valueOf("2013-09-04")
			//Date startDate = java.sql.Date.valueOf("2013-09-04");
			//accessor.updateStartDate(budgetList.get(0), startDate);
			
			Date endDate = java.sql.Date.valueOf("2012-09-04");
			accessor.updateEndDate(budgetList.get(0), endDate);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		**/
		
		/**
		// Test delete
		try {
			accessor.delete(budgetList.get(0));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		**/
		
		try {
			accessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
