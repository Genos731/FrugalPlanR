package backend.accessor;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.transaction.InvalidTransactionException;

import backend.container.Account;
import backend.container.Repeating;
import backend.container.Transaction;
import backend.exception.InvalidAccountException;

public interface TransactionAccessor extends AutoCloseable {

	/**
	 * Given a valid account and transaction details, create a new transaction
	 * and place it into the DB
	 * 
	 * @param a
	 *            Account associated with transaction
	 * @param isIncome
	 *            True if transaction is income type, otherwise is expense type.
	 * @param value
	 *            Value of transaction
	 * @param calendar
	 *            Date of transaction
	 * @param description
	 *            Description of transaction
	 * @param location
	 *            Location of transaction
	 * @param repeating
	 *            Repeating type of transaction
	 * @param category
	 *            Category of transaction
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws InvalidAccountException
	 *             If the account given does not exists
	 * @throws IllegalAccessException
	 *             If transaction variable strings are too long
	 */
	void create(Account a, boolean isIncome, double value, Calendar calendar,
			String description, String location, Repeating repeating,
			String category) throws SQLException, InvalidAccountException,
			IllegalAccessException;

	/**
	 * Delete a transaction from the database
	 * 
	 * @param t
	 *            Transaction to delete
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws InvalidTransactionException
	 *             If the transaction given is invalid
	 */
	public void delete(Transaction t) throws SQLException,
			InvalidTransactionException;

	/**
	 * Return a list of all transactions associated with the account. Returns
	 * null if list is empty or account doesnt exists
	 * 
	 * @param a
	 *            Account transactions are associated with
	 * @return A list of transactions, null if list is empty.
	 * @throws SQLException
	 *             If a database error occurs
	 */
	public List<Transaction> getTransaction(Account a) throws SQLException;

	/**
	 * Returns a list of categories associated with the account Returns null if
	 * list is empty or account doesnt exists
	 * 
	 * @param a
	 *            Account categories are associated with
	 * @return A list of categories, null if list is empty
	 * @throws SQLException
	 *             If a database error occurs
	 */
	
	public List<Transaction> getTransactionWithOptions(Account a, Calendar date, int option, int neutrality) throws SQLException;
	
	/**
	 * Returns a specified list of categories associated with the account
	 * Returns null if list is empty or account doesnt exists
	 * 
	 * @param a
	 *            Account categories are associated with
	 * @param date
	 * 			  date is the current date.           
	 * @param option
	 * 			  if option = 0 then returns all data
	 * 			  if option = 1 then returns within a day
	 * 			  if option = 2 then returns within a week
	 * 			  if option = 3 then returns within a month   
	 * @param neutrality
	 * 			  if neutrality = 0 then returns + and - results
	 * 			  if neutrality = 1 then returns + results
	 * 			  if neutrality = 2 then returns - results        
	 * @return A list of categories, null if list is empty
	 * @throws SQLException
	 *             If a database error occurs
	 */
	
	public List<String> getCategories(Account a) throws SQLException;

	/**
	 * Update the fields in transaction
	 * @param t Transaction to update
	 * @param newValue 
	 * @param newCalendar
	 * @param newDescription
	 * @param newLocation
	 * @param newCategory
	 * @throws SQLException If a database error occurs
	 * @throws IllegalArgumentException If updated transaction is invalid
	 */
	public void updateTransaction(Transaction t, double newValue,
			Calendar newCalendar, String newDescription, String newLocation,
			String newCategory) throws SQLException, IllegalArgumentException;

	/**
	 * Update the value for the transaction
	 * 
	 * @param t
	 *            Transaction to update
	 * @param newValue
	 *            New value for transaction
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If updated transaction is invalid
	 */
	public void updateValue(Transaction t, double newValue)
			throws SQLException, IllegalArgumentException;

	/**
	 * Update the calendar for the transaction
	 * 
	 * @param t
	 *            Transaction to update
	 * @param newCalendar
	 *            New calendar for transaction
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If updated transaction is invalid
	 */
	public void updateCalendar(Transaction t, Calendar newCalendar)
			throws SQLException, IllegalArgumentException;

	/**
	 * Update the description for the transaction
	 * 
	 * @param t
	 *            Transaction to update
	 * @param newDescription
	 *            New description for transaction
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If updated transaction is invalid
	 */
	public void updateDescription(Transaction t, String newDescription)
			throws SQLException, IllegalArgumentException;

	/**
	 * Update the location for the transaction
	 * 
	 * @param t
	 *            Transaction to update
	 * @param newLocation
	 *            New location for transaction
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If updated transaction is invalid
	 */
	public void updateLocation(Transaction t, String newLocation)
			throws SQLException, IllegalArgumentException;

	/**
	 * Update the category for the transaction
	 * 
	 * @param t
	 *            Transaction to update
	 * @param newCategory
	 *            New category for transaction
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If updated transaction is invalid
	 */
	public void updateCategory(Transaction t, String newCategory)
			throws SQLException, IllegalArgumentException;
}
