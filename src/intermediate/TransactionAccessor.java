package intermediate;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import container.Account;
import container.Transaction;
import exception.InvalidAccountException;

public interface TransactionAccessor {
	
	// Should we be able to update transactions?
	// Transaction name or description?
	
	/**
	 * Given a valid account and transaction details, create a new transaction and place it into the DB
	 * @param a Account associated with transaction
	 * @param value Value of transaction
	 * @param name Name of transaction
	 * @param date  Date of transaction
	 * @throws SQLException If a database error occurs
	 * @throws InvalidAccountException If the account given does not exists
	 */
	public void create(Account a, double value, String name, Date date) throws SQLException, InvalidAccountException;
	
	/**
	 * Delete a transaction from the database
	 * @param t Transaction to delete
	 * @throws SQLException If a database error occurs
	 */
	public void delete(Transaction t) throws SQLException; // If we delete, should we throw or return nothing?
	
	/**
	 * Return a list of all transactions associated with the account.
	 * Returns null if the no transaction exists.
	 * @param a Account transactions are associated with
	 * @return A list of transactions, null if list is empty.
	 * @throws SQLException If a database error occurs
	 */
	public List<Transaction> getTransaction(Account a) throws SQLException;
	
	/**
	 * Closes the database connection
	 * @throws SQLException If a database error occurs
	 */
	public void close() throws SQLException;
}
