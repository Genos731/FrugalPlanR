package backend.accessor.test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.InvalidTransactionException;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.accessor.TransactionAccessor;
import backend.accessor.TransactionAccessorImpl;
import backend.container.Account;
import backend.container.Transaction;
import backend.exception.InvalidAccountException;

public class TransactionAccessorTest {

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
		TransactionAccessor accessor = new TransactionAccessorImpl();

		// Test create
		/**
		try {
			Date date = java.sql.Date.valueOf("2017-06-06");
			accessor.create(account, 1234, "Horse Party", date);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/

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
		
		
		// Print transaction
		Transaction first = transactionList.get(0);
		System.out.println("Transaction:");
		System.out.println(first.getId());
		System.out.println(first.getValue());
		System.out.println(first.getName());
		System.out.println(first.getDate());
		System.out.println(first.getAccountID());
		
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
	}

}
