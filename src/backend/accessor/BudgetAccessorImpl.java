package backend.accessor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public void create(Account a, double goalValue, Date startDate, Date endDate) throws SQLException, IllegalArgumentException, InvalidAccountException {
		// Check startDate < endDate
		if (startDate.compareTo(endDate) >= 0) {
			throw new IllegalArgumentException("Start date should be less than end date");
		}
		
		// Create account accessor
		AccountAccessorImpl accountImpl = new AccountAccessorImpl();
		if (!accountImpl.isValidAccount(a)) {
			accountImpl.close();
			throw new InvalidAccountException("Account is invalid");
		}
		accountImpl.close();

		String sqlQuery = "INSERT INTO budget (goalValue, startDate, endDate, account_id) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDouble(1, goalValue);
		statement.setDate(2, startDate);
		statement.setDate(3, endDate);
		statement.setInt(4, a.getID());

		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void delete(Budget b) throws SQLException, InvalidBudgetException {
		// If budget does not exist do nothing
		if (isValidBudget(b))
			throw new InvalidBudgetException("Invalid Budget");

		// Receive budget id
		int id = b.getID();

		// Prepare SQL
		String sqlQuery = "DELETE FROM budget " + "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, id);
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateGoalValue(Budget b, double goalValue) throws SQLException{
		String sqlQuery = "UPDATE budget "
				+ "SET goalValue = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDouble(1, goalValue);
		statement.setInt(2, b.getID());
		statement.executeUpdate();
		statement.close();
	}
	
	@Override
	public void updateStartDate(Budget b, Date startDate) throws SQLException, IllegalArgumentException {
		// do some checking here
		if (startDate.compareTo(b.getEndDate()) >= 0) {
			throw new IllegalArgumentException("Start date should be less than end date");
		}
		
		String sqlQuery = "UPDATE budget "
				+ "SET startDate = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		
		statement.setDate(1, startDate);
		statement.setInt(2, b.getID());
		statement.executeUpdate();
		statement.close();
	}
	
	@Override
	public void updateEndDate(Budget b, Date endDate) throws SQLException, IllegalArgumentException {
		if (b.getStartDate().compareTo(endDate) >= 0) {
			throw new IllegalArgumentException("Start date should be less than end date");
		}
		
		String sqlQuery = "UPDATE budget "
				+ "SET endDate = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDate(1, endDate);
		statement.setInt(2, b.getID());
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public List<Budget> getBudgets(Account a) throws SQLException {
		List<Budget> budgetList = new ArrayList<Budget>();

		String sqlQuery = "SELECT * FROM budget " + "WHERE account_id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, a.getID());
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			int id = result.getInt("id");
			double goalValue = result.getDouble("goalValue");
			Date startDate = result.getDate("startDate");
			Date endDate = result.getDate("endDate");
			Budget b = new Budget(id, goalValue, startDate, endDate, a.getID());
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
				+ "startDate like ? AND endDate like ? AND "
				+ "account_id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, b.getID());
		statement.setDouble(2, b.getGoalValue());
		statement.setDate(3, b.getStartDate());
		statement.setDate(4, b.getEndDate());
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
	 * @param s String to check
	 * @return true if s is smaller than MAX_STRING
	 */
	private boolean isValidLength(String s) {
		if (s.length() > MAX_STRING)
			return false;
		return true;
	}
}
