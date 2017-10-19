package backend.accessor.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.accessor.BudgetAccessor;
import backend.accessor.BudgetAccessorImpl;
import backend.container.Account;
import backend.container.Budget;

public class BudgetAccessorTest {

	public static void main(String[] args) throws Exception {
		// Get an account
		AccountAccessor accountAccessor = new AccountAccessorImpl();
		Account a = accountAccessor.getAccount("Betsy");
		accountAccessor.close();

		// Test create
		BudgetAccessor budgetAccessor = new BudgetAccessorImpl();
		//Calendar startDate = new GregorianCalendar(2012, 06, 06);
		//Calendar endDate = new GregorianCalendar(2017, 12, 25);
		//ArrayList<String> list = new ArrayList<String>();
		//list.add("Porn");
		//list.add("Anime");
		//list.add("Whips");
		//budgetAccessor.create(a, 1050.44, startDate, endDate, list);
		
		// Test get
		List<Budget> bList = budgetAccessor.getBudgets(a);
		Budget b = bList.get(0);
		//System.out.println(b.getGoalValue());
		
		// Test update
		//budgetAccessor.updateGoalValue(b, 69.69);
		//budgetAccessor.updateStartDate(b, new GregorianCalendar(2015, 1, 1));
		//budgetAccessor.updateEndDate(b, new GregorianCalendar(2015, 1, 1));
		
		// Test delete
		budgetAccessor.delete(b);
	}

}
