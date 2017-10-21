package backend.accessor;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import backend.container.Account;
import backend.container.Budget;
import backend.exception.InvalidAccountException;
import backend.exception.InvalidBudgetException;

public interface BudgetAccessor extends AutoCloseable {
	// need to add name to database :))

	/**
	 * Given a valid account and budget details, create a new budget and place
	 * into DB
	 * 
	 * @param Account
	 *            associated with budget
	 * @param goalValue
	 *            Goal value of budget
	 * @param startDate
	 *            Start date of budget
	 * @param endDate
	 *            End date of budget
	 * @param categoryList
	 *            List of categories associated with budget
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If dates are invalid
	 * @throws InvalidAccountException
	 *             If account is invalid
	 */
	public void create(Account a, String description, double goalValue,
			Calendar startDate, Calendar endDate, List<String> categoryList)
			throws SQLException, IllegalArgumentException,
			InvalidAccountException;

	/**
	 * Delete a budget from the database
	 * 
	 * @param b
	 *            Budget to delete
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws InvalidBudgetException
	 *             If the budget given is invalid
	 */
	public void delete(Budget b) throws SQLException, InvalidBudgetException;

	/**
	 * Updates the goal value of a budget
	 * 
	 * @param b
	 *            Budget to update
	 * @param goalValue
	 *            The new goal value
	 * @throws SQLException
	 *             If a database error occurs
	 */
	public void updateGoalValue(Budget b, double goalValue)
			throws SQLException, InvalidBudgetException;

	/**
	 * Updates the start date of a budget
	 * 
	 * @param b
	 *            Budget to update
	 * @param startDate
	 *            The new start date
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If the start date is invalid (after end date)
	 */
	public void updateStartDate(Budget b, Calendar startDate)
			throws SQLException, IllegalArgumentException,
			InvalidBudgetException;

	/**
	 * Updates the end date of a budget
	 * 
	 * @param b
	 *            Budget to update
	 * @param endDate
	 *            The new end date
	 * @throws SQLException
	 *             If a database error occurs
	 * @throws IllegalArgumentException
	 *             If the end date is invalid (before start date)
	 */
	public void updateEndDate(Budget b, Calendar endDate) throws SQLException,
			IllegalArgumentException, InvalidBudgetException;

	/**
	 * Returns a list of all budgets associated with the account Returns null if
	 * no budgets exist with account
	 * 
	 * @param a
	 *            Account that budgets are associated with
	 * @return A list of budgets, null if list is empty
	 * @throws SQLException
	 *             If a database error occurs
	 */
	public List<Budget> getBudgets(Account a) throws SQLException;
}