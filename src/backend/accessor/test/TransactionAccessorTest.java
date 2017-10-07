package backend.accessor.test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.InvalidTransactionException;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.accessor.TransactionAccessor;
import backend.accessor.TransactionAccessorImpl;
import backend.container.Account;
import backend.container.Repeating;
import backend.container.Transaction;
import backend.exception.InvalidAccountException;

public class TransactionAccessorTest {

	public static void main(String[] args) {
		// Get an account
		AccountAccessor accountAccessor = new AccountAccessorImpl();
		Account account = null;
		try {
			account = accountAccessor.getAccount("Cockless");
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

		// Create transaction accessor
		TransactionAccessor accessor = new TransactionAccessorImpl();

		/**
		// Test create
		try {
			Calendar cal = new GregorianCalendar(2012, 12, 25);
			accessor.create(account, false, 69.69, cal, "hihi", "Antartica", Repeating.FORTNIGHTLY, "Lucky Stuff");
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
		

		/**
		// Test get
		List<Transaction> transactionList = null;
		try {
			transactionList = accessor.getTransaction(account);
			
			if (transactionList == null) {
				System.out.println("returned null");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
		
		/**
		
		// Print transaction
		Transaction first = transactionList.get(0);
		System.out.println("Transaction:");
		System.out.println(first.getId());
		if (first.isIncome())
			System.out.println("Income");
		else
			System.out.println("Expense");
		System.out.println(first.getValue());
		System.out.println(first.getCalendar());
		System.out.println(first.getDescription());
		System.out.println(first.getLocation());
		System.out.println(first.getRepeating());
		System.out.println(first.getCategory());
		System.out.println(first.getAccountID());
		**/
		
		/**
		// Test delete
		try {
			accessor.delete(transactionList.get(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
		
		
		// Test get category
		try {
			List<String> list = accessor.getCategories(account);
			
			for (String s: list) {
				System.out.println(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
