package backend.accessor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import backend.container.Account;
import backend.container.Budget;
import backend.database.FrugalDBConnection;
import backend.exception.InvalidAccountException;
import backend.exception.InvalidBudgetException;

public class BudgetAccessorImpl implements BudgetAccessor {

	private static final int MAX_STRING = 45;

	Connection dbConnection;

	public BudgetAccessorImpl() {
		dbConnection = FrugalDBConnection.getConnection();
	}

	@Override
	public void create(Account a, String description, double goalValue,
			Calendar startDate, Calendar endDate, List<String> categoryList)
			throws SQLException, IllegalArgumentException,
			InvalidAccountException {
		if (!isValidLength(description)) {
			throw new IllegalArgumentException("Strings must be less than "
					+ MAX_STRING + " characters");
		}

		// If startDate > endDate, throw error
		if (startDate.after(endDate)) {
			throw new IllegalArgumentException(
					"Start date should be less than end date");
		}

		// Check categoryList is validLength
		for (String s : categoryList)
			if (!isValidLength(s))
				throw new IllegalArgumentException("Strings must be less than "
						+ MAX_STRING + " characters");

		// Check that account is valid
		AccountAccessorImpl accountImpl = new AccountAccessorImpl();
		if (!accountImpl.isValidAccount(a)) {
			accountImpl.close();
			throw new InvalidAccountException("Account is invalid");
		}
		accountImpl.close();

		// Convert calendar to sqlDate
		Date sqlStartDate = new Date(startDate.getTimeInMillis());
		Date sqlEndDate = new Date(endDate.getTimeInMillis());

		// Prepare sql statement for budget insert
		String sqlQuery = "INSERT INTO budget "
				+ "(description, goalValue, startDate, endDate, account_id) "
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery,
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, description);
		statement.setDouble(2, goalValue);
		statement.setDate(3, sqlStartDate);
		statement.setDate(4, sqlEndDate);
		statement.setInt(5, a.getId());

		// Execute, throws error if failed
		statement.executeUpdate();

		// Retrieve row id
		ResultSet generatedKeys = statement.getGeneratedKeys();
		int budgetId = 0;
		if (generatedKeys.next())
			budgetId = generatedKeys.getInt(1);
		statement.close();

		// Insert categoryList into db
		for (String category : categoryList) {
			int categoryId = getCategoryID(category, a.getId());
			if (categoryId == 0)
				categoryId = createCategory(category, a.getId());

			sqlQuery = "INSERT INTO category_list "
					+ "(budget_id, category_id) " + "VALUES (?, ?)";
			statement = dbConnection.prepareStatement(sqlQuery);
			statement.setInt(1, budgetId);
			statement.setInt(2, categoryId);

			// Execute, throws error if failed
			statement.executeUpdate();
			statement.close();
		}
	}

	@Override
	public void delete(Budget b) throws SQLException, InvalidBudgetException {
		// If budget does not exist throw error
		if (!isValidBudget(b))
			throw new InvalidBudgetException("Invalid Budget");

		// Prepare SQL
		String sqlQuery = "DELETE FROM budget " + "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, b.getID());
		statement.executeUpdate();
		statement.close();

		// Check if category_list references this categoryId anymore
		sqlQuery = "SELECT * " + "FROM category_list "
				+ "WHERE category_id = ?";
		statement = dbConnection.prepareStatement(sqlQuery);
		int categoryId = getCategoryID(b.getID());
		statement.setInt(1, categoryId);

		ResultSet result = statement.executeQuery();
		// If no row in cateogry_list references this categoryId
		if (!result.next()) {
			String sqlQuery2 = "SELECT * " + "FROM transaction "
					+ "WHERE category_id = ?";
			PreparedStatement statement2 = dbConnection
					.prepareStatement(sqlQuery2);
			statement2.setInt(1, categoryId);

			ResultSet result2 = statement2.executeQuery();
			// If no row in transaction references this categoryid
			// delete the category
			if (!result2.next()) {
				sqlQuery2 = "DELETE FROM category " + "WHERE id = ?";
				statement2 = dbConnection.prepareStatement(sqlQuery2);
				statement2.setInt(1, categoryId);
				statement2.executeUpdate();
			}
			statement2.close();
			result2.close();
		}
	}

	@Override
	public void updateGoalValue(Budget b, double goalValue)
			throws SQLException, InvalidBudgetException {
		// If budget does not exist throw error
		if (!isValidBudget(b))
			throw new InvalidBudgetException("Invalid Budget");

		String sqlQuery = "UPDATE budget " + "SET goalValue = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDouble(1, goalValue);
		statement.setInt(2, b.getID());
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateStartDate(Budget b, Calendar startDate)
			throws SQLException, IllegalArgumentException,
			InvalidBudgetException {
		// If budget does not exist throw error
		if (!isValidBudget(b))
			throw new InvalidBudgetException("Invalid Budget");

		// do some checking here
		if (startDate.after(b.getEndDate()))
			throw new IllegalArgumentException(
					"Start date should be less than end date");

		String sqlQuery = "UPDATE budget " + "SET startDate = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);

		statement.setDate(1, new Date(startDate.getTimeInMillis()));
		statement.setInt(2, b.getID());
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateEndDate(Budget b, Calendar endDate) throws SQLException,
			IllegalArgumentException, InvalidBudgetException {
		// If budget does not exist throw error
		if (!isValidBudget(b))
			throw new InvalidBudgetException("Invalid Budget");

		if (endDate.before(b.getStartDate()))
			throw new IllegalArgumentException(
					"Start date should be less than end date");

		String sqlQuery = "UPDATE budget " + "SET endDate = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDate(1, new Date(endDate.getTimeInMillis()));
		statement.setInt(2, b.getID());
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public List<Budget> getBudgets(Account a) throws SQLException {
		List<Budget> budgetList = new ArrayList<Budget>();

		String sqlQuery = "SELECT * " + "FROM budget " + "WHERE account_id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, a.getId());
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			int id = result.getInt("id");
			String description = result.getString("description");
			double goalValue = result.getDouble("goalValue");

			Calendar startDate = new GregorianCalendar();
			startDate.setTimeInMillis(result.getDate("startDate").getTime());
			Calendar endDate = new GregorianCalendar();
			endDate.setTimeInMillis(result.getDate("endDate").getTime());

			// Get assocaited categoryList
			String sqlQuery2 = "SELECT category.name " + "FROM category "
					+ "INNER JOIN category_list "
					+ "ON category_list.category_id = category.id "
					+ "WHERE category_list.budget_id = ?";
			PreparedStatement statement2 = dbConnection
					.prepareStatement(sqlQuery2);
			statement2.setInt(1, id);
			ResultSet result2 = statement2.executeQuery();

			ArrayList<String> categoryList = new ArrayList<String>();
			while (result2.next()) {
				categoryList.add(result2.getString("name"));
			}
			statement2.close();
			result2.close();

			Budget b = new Budget(id, description, goalValue, startDate,
					endDate, a.getId(), categoryList);
			budgetList.add(b);
		}

		statement.close();
		result.close();

		return budgetList;
	}

	@Override
	public void close() throws SQLException {
		dbConnection.close();
	}

	private boolean isValidBudget(Budget b) throws SQLException {
		String sqlQuery = "SELECT * FROM budget "
				+ "WHERE id = ? AND goalValue = ? AND "
				+ "startDate like ? AND endDate like ? AND " + "account_id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, b.getID());
		statement.setDouble(2, b.getGoalValue());
		statement.setDate(3, new Date(b.getStartDate().getTimeInMillis()));
		statement.setDate(4, new Date(b.getEndDate().getTimeInMillis()));
		statement.setInt(5, b.getAccountID());
		ResultSet result = statement.executeQuery();
		// if there is a match
		if (result.next()) {
			result.close();
			return true;
		}
		statement.close();
		result.close();

		return false;
	}

	/**
	 * Return true if s is smaller than MAX_STRING
	 * 
	 * @param s
	 *            String to check
	 * @return true if s is smaller than MAX_STRING
	 */
	private boolean isValidLength(String s) {
		if (s.length() > MAX_STRING)
			return false;
		return true;
	}

	private int getCategoryID(int budgetId) throws SQLException {
		String sqlQuery = "SELECT * " + "FROM category_list "
				+ "WHERE budget_id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, budgetId);

		ResultSet result = statement.executeQuery();

		if (result.next())
			return result.getInt("category_id");
		return 0;
	}

	private int getCategoryID(String category, int accountID)
			throws SQLException {
		// Prepare sql query
		String sqlQuery = "SELECT id " + "FROM category "
				+ "WHERE name LIKE ? " + "AND account_id = ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, category);
		statement.setInt(2, accountID);

		// Execute and retrieve result
		ResultSet result = statement.executeQuery();

		// If results found, retrieve and return id
		// Otherwise return 0;
		if (result.next()) {
			int id = result.getInt("id");

			statement.close();
			result.close();
			return id;
		}

		statement.close();
		result.close();
		return 0;
	}

	private int createCategory(String category, int accountID)
			throws SQLException {
		// Prepare sql query
		String sqlQuery = "INSERT INTO category (name, account_id) "
				+ "VALUES (?, ?)";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery,
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, category);
		statement.setInt(2, accountID);

		// Execute, throws error if failed
		statement.executeUpdate();

		// Retrieve row id
		ResultSet generatedKeys = statement.getGeneratedKeys();
		int rowId = 0;
		if (generatedKeys.next())
			rowId = generatedKeys.getInt(1);
		statement.close();

		if (rowId == 0)
			throw new SQLException("Error retrieve row id");
		return rowId;
	}
}
